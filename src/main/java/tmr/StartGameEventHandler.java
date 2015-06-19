package tmr;

import tmr.backend.events.GameEventHandler;
import tmr.backend.events.StartGameEvent;

/**
 *
 * @author Mindaugas Vosylius
 */
public class StartGameEventHandler extends GameEventHandler<StartGameEvent> {
    
    private final GameView view;

    public StartGameEventHandler(GameView view) {
        this.view = view;
    }

    @Override
    public void handle(StartGameEvent event) {
        view.getUI().access(new Runnable() {

            @Override
            public void run() {
                view.onGameStart();
            }
        });
    }

    @Override
    public Class<StartGameEvent> getEventTypeClass() {
        return StartGameEvent.class;
    }
}
