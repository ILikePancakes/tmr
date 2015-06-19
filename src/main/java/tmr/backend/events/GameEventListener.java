package tmr.backend.events;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Listens for a single game events and delegates to specific handlers.
 * 
 * @author Mindaugas Vosylius
 */
@SpringComponent
@UIScope
public class GameEventListener {
    
    private final Map<String, GameEventHandler> handlers = new HashMap<>();
    
    @Autowired
    private GameEventBroadcaster broadcaster;
    
    public void addHandlers(GameEventHandler... handlers) {
        for (GameEventHandler handler : handlers) {
            this.handlers.put(handler.getEventType(), handler);
        }
    }
    
    public void start() {
        broadcaster.register(this);
    }
    
    public void stop() {
        broadcaster.unregister(this);
    }
    
    @SuppressWarnings("unchecked")
    public void receiveEvent(GameEvent event) {
        GameEventHandler handler = handlers.get(event.getType());
        if (handler != null) {
            handler.handle(event);
        } else {
            System.out.println("unknown event type");
        }
    }
}
