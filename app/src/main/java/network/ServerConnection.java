package network;

import common.Definitions;
import common.Message;
import common.MessageType;
import common.ServerAnswer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * A class responsible for the complete server connection.
 *
 * @author Griffone
 */
public class ServerConnection {

    public static final int MS_CONNECT_TIMEOUT = 30000; // 30 seconds

    protected final NetworkOutput handler;

    protected Socket socket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;

    protected boolean connected = false;

    protected ServerConnection(NetworkOutput output) {
        this.handler = output;
    }

    public boolean isConnected() {
        return connected;
    }

    public static ServerConnection connect(InetSocketAddress address, NetworkOutput output) throws IOException {
        ServerConnection con = new ServerConnection(output);
        con.connected = false;
        con.socket = new Socket();
        con.socket.connect(address, MS_CONNECT_TIMEOUT);
        con.socket.setSoTimeout(Definitions.MS_TIMEOUT);
        con.in = new ObjectInputStream(con.socket.getInputStream());
        con.out = new ObjectOutputStream(con.socket.getOutputStream());
        con.connected = true;
        new Thread(con.getThread()).start();
        con.handler.onConnected();
        return con;
    }

    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
        out.reset();
    }

    public void disconnect() throws IOException {
        connected = false;
        sendMessage(new Message(MessageType.MT_DISCONNECT, null));
        socket.close();
        handler.onDisconnected();
    }

    public class ConnectionThread implements Runnable {

        @Override
        public void run() {
            while (connected) {
                try {
                    Message msg = (Message) in.readObject();
                    if (msg.type == MessageType.MT_ANSWER) {
                        ServerAnswer answer = (ServerAnswer) msg.payload;
                        if (answer != null)
                            handler.onAnswerReceive(answer);
                    }
                } catch (SocketException ex) {
                    connected = false;
                    handler.onDisconnected();
                } catch (IOException | ClassNotFoundException ex) {
                    handler.exception(ex);
                }
            }
        }
    }

    public ConnectionThread getThread() {
        return new ConnectionThread();
    }
}