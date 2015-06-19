package tmr.backend.events;

import tmr.backend.Player;

/**
 *
 * @author Mindaugas Vosylius
 */
public class PlayerJoinedEvent extends GameEvent {
    
    private final Player player;

    public PlayerJoinedEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
