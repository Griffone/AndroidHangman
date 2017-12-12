package network;

import common.ServerAnswer;

/**
 * An interface to avoid upward dependency.
 *
 * @author Griffone
 */
public interface NetworkOutput {

    void onAnswerReceive(ServerAnswer answer);

    void onConnected();
    void onDisconnected();
    void exception(final Exception e);
}
