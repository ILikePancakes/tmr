package tmr.backend.events;

/**
 * Event handler of a specific event type.
 * 
 * @author Mindaugas Vosylius
 * @param <T> event type
 */
public abstract class GameEventHandler<T extends GameEvent> {
    
    public String getEventType() {
        return getEventTypeClass().getSimpleName();
    }
    
    public abstract void handle(T event);
    
    public abstract Class<T> getEventTypeClass();
}
