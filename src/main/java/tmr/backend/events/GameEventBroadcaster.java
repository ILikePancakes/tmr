package tmr.backend.events;

import com.vaadin.spring.annotation.SpringComponent;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Broadcasts events to registered listeners. Vaadin takes care of client notification.
 * 
 * @author Mindaugas Vosylius
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameEventBroadcaster {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Collection<GameEventListener> listeners = new LinkedList<>();
    
    public synchronized void register(GameEventListener listener) {
        listeners.add(listener);
    }

    public synchronized void unregister(GameEventListener listener) {
        listeners.remove(listener);
    }
    
    public synchronized void broadcast(GameEvent event) {
        broadcast(event, listeners);
    }
    
    public synchronized void broadcast(GameEvent event, GameEventListener listener) {
        broadcast(event, Arrays.asList(listener));
    }
    
    public synchronized void broadcast(
            final GameEvent event, Collection<GameEventListener> specifiedListeners) {
        for (final GameEventListener listener : specifiedListeners) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    listener.receiveEvent(event);
                }
            });
        }
    }
}
