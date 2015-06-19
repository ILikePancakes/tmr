package tmr;

import com.vaadin.ui.Notification;
import tmr.backend.events.GameEventHandler;
import tmr.backend.events.PlayerTzarEvent;

/**
 *
 * @author Mindaugas Vosylius
 */
public class PlayerTzarEventHandler extends GameEventHandler<PlayerTzarEvent> {
    
    private GameView view;
    
    public PlayerTzarEventHandler(GameView view) {
        this.view = view;
    }

    @Override
    public void handle(PlayerTzarEvent event) {
        view.getUI().access(new Runnable() {

            @Override
            public void run() {
                view.onCardTzar();
            }
        });
    }

    @Override
    public Class<PlayerTzarEvent> getEventTypeClass() {
        return PlayerTzarEvent.class;
    }

}
