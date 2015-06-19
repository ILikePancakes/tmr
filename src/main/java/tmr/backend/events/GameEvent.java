package tmr.backend.events;

/**
 * Represents a game event with a specific type.
 * 
 * @author Mindaugas Vosylius
 */
public abstract class GameEvent {

    public String getType() {
        return getClass().getSimpleName();
    }
}
