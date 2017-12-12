package common;

import java.io.Serializable;

/**
 * A DTE of a GameState.
 *
 * @author Griffone
 */
public class GameStateSnapshot implements Serializable {

    public final int points;
    public final String word;
    public final int remainingLives;

    public GameStateSnapshot(int points, String word, int remainingLives) {
        this.points = points;
        this.word = word;
        this.remainingLives = remainingLives;
    }

    public static GameStateSnapshot fromString(String string) {
        if (string.startsWith("{\"points\":")) {
            int endIndex = string.indexOf(", ");
            String sub = string.substring(10, endIndex);
            int points = Integer.parseInt(sub);
            int wordEnd = string.indexOf("\", ", endIndex + 10);
            String word = string.substring(endIndex + 10, wordEnd);
            sub = string.substring(wordEnd + 11, string.length() - 1);
            int lives = Integer.parseInt(sub);
            return new GameStateSnapshot(points, word, lives);
        }
        return null;
    }

    /**
     * Transforms the GameState into a json-like string
     *
     * @return
     */
    @Override
    public String toString() {
        return "{\"points\":" + String.valueOf(points) + ", \"word\":\"" + word + ", \"lives\":" + String.valueOf(remainingLives) + '}';
    }
}
