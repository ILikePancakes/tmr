package tmr;

import com.vaadin.ui.Notification;
import tmr.backend.events.GameEventHandler;
import tmr.backend.events.PlayerJoinedEvent;

/**
 *
 * @author Mindaugas Vosylius
 */
public class PlayerJoinedEventHandler extends GameEventHandler<PlayerJoinedEvent> {

    private final GameView view;

    public PlayerJoinedEventHandler(GameView view) {
        this.view = view;
    }

    @Override
    public void handle(final PlayerJoinedEvent event) {
        view.getUI().access(new Runnable() {

            @Override
            public void run() {
                Notification.show("new player joined: " + event.getPlayer().getName(), Notification.Type.TRAY_NOTIFICATION);
            }
        });
    }

    @Override
    public Class<PlayerJoinedEvent> getEventTypeClass() {
        return PlayerJoinedEvent.class;
    }
}
