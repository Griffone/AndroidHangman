package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.griffone.hangman.R;

import controller.Controller;


/**
 * The main class of the application.
 *
 * Performs the startup code and leaves the rest to InputListener and OutputHandler.
 *
 * @author Griffone
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the view on the screen
        setContentView(R.layout.activity_main);

        // Hook up to the view widgets
        EditText input = findViewById(R.id.input);

        // Initialize output listener
        OutputHandler handler = new OutputHandler(this);

        // Initialize controller layer
        Controller controller = new Controller(handler);

        // Initialize input listener
        InputListener listener = new InputListener(controller, input);

        // Hook up the listener to the input
        input.setOnEditorActionListener(listener);

    }
}
