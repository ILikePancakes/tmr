package tmr.backend;

import tmr.backend.events.GameEventBroadcaster;
import tmr.backend.events.PlayerJoinedEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import tmr.backend.events.PlayCardEvent;
import tmr.backend.events.PlayerTzarEvent;
import tmr.backend.events.StartGameEvent;

/**
 *
 * @author Mindaugas Vosylius
 */
//@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Game implements Serializable {
    
    private Object roundTimerLock = new Object();
    
    private final GameEventBroadcaster broadcaster;
    
    private String name;
    
    private Player host;

    private final List<Player> players = new ArrayList<>();
    
    private List<Player> roundPlayers = new ArrayList<>();
    
    private List<String> playedCards = new ArrayList<>();
    
    private Player tzar;
    
    public Game(String name, GameEventBroadcaster broadcaster) {
        this.name = name;
        this.broadcaster = broadcaster;
    }
    
    public void setHost(Player host) {
        this.host = host;
    }
    
    public Player getHost() {
        return host;
    }
    
    public void addPlayer(Player player) {
        getPlayers().add(player);
        broadcaster.broadcast(new PlayerJoinedEvent(player));
    }
    
    public void start() {
        assignPlayerRoles();
        dealCardsStarting();
        
        broadcaster.broadcast(new StartGameEvent());
        
        waitForPlayerTurns();
        
        waitForTzarDecision();
    }
    
    public void newRound() {
        assignPlayerRoles();
        dealCards();
        
//        broadcaster.broadcast(new StartGameEvent());
        
        waitForPlayerTurns();
        
        waitForTzarDecision();
    }
    
    private void dealCardsStarting() {
    }
    
    private void dealCards() {
    }
    
    private void assignPlayerRoles() {
        tzar = players.get(0);
        roundPlayers = players.subList(1, players.size());
        
        // notify tzar ui about update
        broadcaster.broadcast(new PlayerTzarEvent(), tzar.getListener());
    }
    
    private void waitForPlayerTurns() {
        // wait for player turns
        
        synchronized (roundTimerLock) {
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("time ran out");
                }
            };
            // 10 second warning
            ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(1);
            timer.schedule(task, 10 * 1000, TimeUnit.MILLISECONDS);
        }
        
        // if a player takes too long, forfeit their turn
    }
    
    private void waitForTzarDecision() {
        // if tzar takes too long, forfeit their turn
    }
    
    public synchronized void playCard(Player player) {
        playedCards.add(player.getName());
        
        // notify other players about played card
        broadcaster.broadcast(new PlayCardEvent());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
