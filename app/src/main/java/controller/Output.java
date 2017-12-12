package controller;

import common.ServerAnswer;

/**
 * An interface to avoid upward dependency.
 *
 * @author Griffone
 */

public interface Output {

    void handleServerAnswer(ServerAnswer answer);

    void onDisconnected();

    void onConnected();

    void onWrongAddress();

    void exception(Exception e);

    void onIllegalCharacter(String c);
}
