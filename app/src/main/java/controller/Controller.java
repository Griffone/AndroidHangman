package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import common.Definitions;
import common.Message;
import common.MessageType;
import network.ServerConnection;

/**
 * The main class of the controller layer.
 *
 * Provides non-blocking methods for the view layer.
 *
 * @author Griffone
 */

public class Controller {

    protected static Output output;
    protected final NetworkOutputHandler outputHandler;
    protected ServerConnection connection;

    public Controller(Output output) {
        this.output = output;
        this.outputHandler = new NetworkOutputHandler(output, this);
    }

    public boolean isConnected() {
        return (connection != null) ? connection.isConnected() : false;
    }

    /**
     * Try to connect
     *
     * @param address
     */
    public void connect(final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isConnected())
                    return;

                try {
                    InetAddress inetAddress = InetAddress.getByName(address);
                    connection = ServerConnection.connect(new InetSocketAddress(inetAddress, Definitions.PORT), outputHandler);
                } catch (UnknownHostException ex) {
                    output.onWrongAddress();
                } catch (IOException ex) {
                    output.exception(ex);
                }
            }
        }).start();
    }

    public void guess(final String guess) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isConnected())
                    return;

                char[] chars = new char[1];
                for (int i = 0; i < guess.length(); i++) {
                    chars[0] = guess.charAt(i);
                    if (!Definitions.LEGAL_CHARS.contains(new String(chars))) {
                        output.onIllegalCharacter(new String(chars));
                        return;
                    }
                }

                try {
                    connection.sendMessage(new Message(MessageType.MT_GUESS, guess));
                } catch (IOException ex) {
                    output.exception(ex);
                }
            }
        }).start();
    }

    public void newGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isConnected())
                    return;

                try {
                    connection.sendMessage(new Message(MessageType.MT_NEW_GAME, null));
                } catch (IOException ex) {
                    output.exception(ex);
                }
            }
        }).start();
    }

    public void disconnect() {
        if (!isConnected())
            return;

        try {
            connection.disconnect();
        } catch (IOException ex) {
            output.exception(ex);
        }
    }

}
