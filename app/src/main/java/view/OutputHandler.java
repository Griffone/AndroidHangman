package view;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.griffone.hangman.R;

import java.net.SocketException;

import common.GameStateSnapshot;
import common.ServerAnswer;
import controller.Output;

/**
 * An output handler.
 *
 * Avoids upwards dependency (from controller layer) and uses the correct thread for UI updates.
 *
 * @author Griffone
 */

public class OutputHandler implements Output {

    protected final Activity activity;

    protected final TextView outputMain;
    protected final TextView outputPoints;
    protected final TextView outputLives;
    protected final TextView outputHelp;
    protected final EditText input;


    public OutputHandler(Activity activity) {
        this.activity = activity;
        this.input = activity.findViewById(R.id.input);
        this.outputMain = activity.findViewById(R.id.output_main);
        this.outputPoints = activity.findViewById(R.id.output_points);
        this.outputLives = activity.findViewById(R.id.output_lives);
        this.outputHelp = activity.findViewById(R.id.output_help);
    }

    @Override
    public void handleServerAnswer(final ServerAnswer answer) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (answer.snapshot != null)
                    printGameState(answer.snapshot);

                if (answer.optional != null)
                    outputHelp.setText(answer.optional);
                else
                    outputHelp.setText(R.string.help_default);
            }
        });
    }

    @Override
    public void onWrongAddress() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputHelp.setText(R.string.unknown_address);
            }
        });
    }

    @Override
    public void onIllegalCharacter(final String c) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputHelp.setText(activity.getString(R.string.illegal_char_prefix) + c + activity.getString(R.string.illegal_char_postfix));
            }
        });
    }

    @Override
    public void exception(final Exception e) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e.getMessage() != null)
                    outputHelp.setText("An exception happened!\n" + e.getMessage());
                else
                    outputHelp.setText("An exception happened!\n" + e.getClass().getName());
            }
        });
        e.printStackTrace();
    }

    @Override
    public void onDisconnected() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputMain.setText(R.string.word_disconnected);
                outputLives.setText("");
                outputPoints.setText("");
                outputHelp.setText(R.string.help_disconnected);
                input.setHint(R.string.hint_disconnected);
            }
        });
    }

    @Override
    public void onConnected() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                input.setHint(R.string.hint_default);

                outputMain.setText("Connected!");
                outputHelp.setText("");
            }
        });
    }

    private void printGameState(GameStateSnapshot state) {
        outputMain.setText(state.word);
        outputLives.setText(activity.getString(R.string.lives_prefix) + state.remainingLives);
        outputPoints.setText(activity.getString(R.string.points_prefix) + state.points);
    }
}
