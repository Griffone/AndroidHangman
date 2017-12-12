package common;

import java.io.Serializable;

/**
 * A message object that is sent over the network.
 *
 * @author Griffone
 */
public class Message implements Serializable {

    public final MessageType    type;
    public final Object         payload;

    public Message(MessageType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * Transform string to a json object
     *
     * {"type":"TYPE", "message":"MESSAGE"}
     *
     * @return json-represented string
     */
    @Override
    public String toString() {
        return '{' + "\"type\":\""  + type + "\", \"payload\":\"" + payload + "\"}";
    }
}