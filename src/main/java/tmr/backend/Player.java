package tmr.backend;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import tmr.backend.events.GameEventListener;

/**
 *
 * @author Mindaugas Vosylius
 */
@SpringComponent
@VaadinSessionScope
public class Player {
    
    private String id;
    private String name;
    
    private GameEventListener listener;
    
    public Player(GameEventListener listener) {
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameEventListener getListener() {
        return listener;
    }
}
