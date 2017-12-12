package view;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import controller.Controller;

/**
 * A listener for an entered string.
 *
 * Processes the entered string and calls the corresponding method in the controller layer.
 *
 * @author Griffone
 */

public class InputListener implements TextView.OnEditorActionListener {

    protected final Controller controller;
    protected final EditText input;

    public InputListener(Controller controller, EditText input) {
        this.controller = controller;
        this.input = input;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (controller.isConnected())
                parseCommand(textView.getText().toString());
            else
                controller.connect(textView.getText().toString());
            textView.setText("");
            return true;
        }
        return false;
    }

    protected void parseCommand(String command) {
        if (command.startsWith("*")) {
            command = command.toLowerCase();
            if (command.startsWith("*game"))
                controller.newGame();
            else if (command.startsWith("*disconnect"))
                controller.disconnect();
        } else {
            controller.guess(command.split(" ")[0]);
        }
    }
}
