package common;

import java.io.Serializable;

/**
 * A DTE of a server answer.
 *
 * @author Griffone
 */
public class ServerAnswer implements Serializable {

    public final String optional;
    public final GameStateSnapshot snapshot;

    public ServerAnswer(GameStateSnapshot snapshot, String optional) {
        this.snapshot = snapshot;
        this.optional = optional;
    }
}
