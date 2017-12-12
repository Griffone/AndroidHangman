package common;

/**
 * Possible message types for Networking
 *
 * @author Griffone
 */
public enum MessageType {

    MT_CONNECT,         // New connection message clinet->server
    MT_GUESS,           // New guess clinet->server
    MT_NEW_GAME,        // Start new game client->server
    MT_DISCONNECT,      // Client is dropping connection client->server
    MT_ANSWER;          // Full answer server->client contains: 1) guessed word, 2) remaining lives and 3) current score
}