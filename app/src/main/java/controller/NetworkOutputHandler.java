package controller;

import common.ServerAnswer;
import network.NetworkOutput;

/**
 * A handler (implementation) of NetworkOutput to avoid upwards dependency.
 *
 * @author Griffone
 */
public class NetworkOutputHandler implements NetworkOutput {

    protected final Output output;
    protected final Controller controller;

    public NetworkOutputHandler(Output output, Controller controller) {
        this.output = output;
        this.controller = controller;
    }

    @Override
    public void onConnected() {
        output.onConnected();
        controller.newGame();
    }

    @Override
    public void onDisconnected() {
        output.onDisconnected();
    }

    @Override
    public void onAnswerReceive(ServerAnswer answer) {
        if (answer == null)
            return;

        output.handleServerAnswer(answer);
    }

    @Override
    public void exception(final Exception e) {
        output.exception(e);
    }
}
