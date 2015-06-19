package tmr;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import tmr.backend.Game;
import tmr.backend.events.GameEventBroadcaster;
import tmr.backend.GameManager;
import tmr.backend.Player;
import tmr.backend.events.GameEventListener;

/**
 *
 * @author Mindaugas Vosylius
 */
@UIScope
@SpringView(name = GameListView.VIEW_NAME)
public class GameListView extends HorizontalLayout implements View {
    
    public static final String VIEW_NAME = "";
    
    private Grid gamesGrid = new Grid();
    
    private Button newGame = new Button("New game");
    
    private Button joinGame = new Button("Join game");
    
    @Autowired
    private GameManager gameManager;
    
    @Autowired
    private GameEventBroadcaster broadcaster;
    
    @Autowired
    private GameEventListener listener;

    @PostConstruct
    public void init() {
        System.out.println("game list listener: " + listener);
        initGameEventListener();
        setSizeFull();
        
        HorizontalLayout actions = new HorizontalLayout(newGame, joinGame);

        VerticalLayout left = new VerticalLayout(actions, gamesGrid);
        left.setSizeFull();
        gamesGrid.setSizeFull();
        left.setExpandRatio(gamesGrid, 1);
        
        addComponent(left);
        
        newGame.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Player player = new Player(listener);
                player.setId(getSession().getSession().getId());
                player.setName("Player_"+player.getId());
                
                Game game = new Game(player.getName() + "'s game", broadcaster);
                
                gameManager.createGame(game, player);
                String msg = String.format("Saved '%s'.", game);
                Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);
                
                // show game view
                Navigator navigator = getUI().getNavigator();
                navigator.navigateTo(GameView.VIEW_NAME);
            }
        });
        
        joinGame.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Player player = new Player(listener);
                player.setId(getSession().getSession().getId());
                player.setName("Player_"+player.getId());
                
                Game game = (Game) gamesGrid.getSelectedRow();
                
                if (game != null) {
                    game.addPlayer(player);
                    String msg = String.format("Joining '%s'.", game);
                    Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);

                    // show game view
                    Navigator navigator = getUI().getNavigator();
                    navigator.navigateTo(GameView.VIEW_NAME);
                }
            }
        });
        
        configureComponents();
    }
    
    private void initGameEventListener() {
        listener.addHandlers(new CreateGameEventHandler(this));
        listener.start();
        
        addDetachListener(new DetachListener() {

            @Override
            public void detach(DetachEvent event) {
                listener.stop();
            }
        });
    }
    
    private void configureComponents() {
        gamesGrid.setContainerDataSource(new BeanItemContainer<>(Game.class));
        gamesGrid.setColumnOrder("name");
        gamesGrid.removeColumn("players");
        gamesGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        gamesGrid.addSelectionListener(new SelectionEvent.SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
            }
        });
        refreshGames();
    }
    
    public void refreshGames() {
        gamesGrid.setContainerDataSource(new BeanItemContainer<>(Game.class, gameManager.getGames()));
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}
