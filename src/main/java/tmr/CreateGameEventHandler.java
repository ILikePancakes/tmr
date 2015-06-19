package tmr;

import com.vaadin.ui.Notification;
import tmr.backend.events.CreateGameEvent;
import tmr.backend.events.GameEventHandler;

/**
 *
 * @author Mindaugas Vosylius
 */
public class CreateGameEventHandler extends GameEventHandler<CreateGameEvent> {
    
    private final GameListView view;
    
    public CreateGameEventHandler(GameListView view) {
        this.view = view;
    }

    @Override
    public void handle(CreateGameEvent event) {
        view.getUI().access(new Runnable() {

            @Override
            public void run() {
                Notification.show("refreshing games list");
                view.refreshGames();
            }
        });
    }

    @Override
    public Class<CreateGameEvent> getEventTypeClass() {
        return CreateGameEvent.class;
    }
}
