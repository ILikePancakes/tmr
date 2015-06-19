package tmr.backend;

import com.vaadin.spring.annotation.SpringComponent;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import tmr.backend.events.CreateGameEvent;
import tmr.backend.events.GameEventBroadcaster;

/**
 *
 * @author Mindaugas Vosylius
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameManager {

    private final List<Game> games = new ArrayList<>();
    
    @Autowired
    private GameEventBroadcaster broadcaster;
    
    public Game createGame(Game game, Player player) {
        game.getPlayers().add(player);
        
        games.add(game);
        
        game.setHost(player);
        
        broadcaster.broadcast(new CreateGameEvent());
        
        return game;
    }
    
    public Game findGameByPlayerId(String id) {
        for (Game g : games) {
            for (Player p : g.getPlayers()) {
                if (p.getId().equals(id)) {
                    return g;
                }
            }
        }
        return null;
    }
    
    public Player findPlayerByPlayerId(String id) {
        for (Game g : games) {
            for (Player p : g.getPlayers()) {
                if (p.getId().equals(id)) {
                    return p;
                }
            }
        }
        return null;
    }

    public List<Game> getGames() {
        return games;
    }
}
