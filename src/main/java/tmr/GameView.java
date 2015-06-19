package tmr;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import tmr.backend.Game;
import tmr.backend.GameManager;
import tmr.backend.Player;
import tmr.backend.events.GameEventListener;

/**
 *
 * @author Mindaugas Vosylius
 */
@UIScope
@SpringView(name = GameView.VIEW_NAME)
public class GameView extends VerticalLayout implements View {
    
    public static final String VIEW_NAME = "game";
    
    private Game game;
    
    private Player player;
    
    @Autowired
    private GameEventListener listener;
    
    @Autowired
    private GameManager gameManager;
    
    private HorizontalLayout actions;
    
    private HorizontalLayout display;
    
    private HorizontalLayout cards;
    
    private Button startGame;
    
    @PostConstruct
    public void init() {
        System.out.println("game listener: " + listener);
        
        initGameEventListener();
        
        startGame = new Button("Start game");
        startGame.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                game.start();
            }
        });


        actions = new HorizontalLayout(startGame);
        actions.setSizeFull();
        addComponent(actions);
        
        display = new HorizontalLayout();
        display.setSizeFull();
        addComponent(display);
        
        cards = new HorizontalLayout();
        cards.setSizeFull();
        addComponent(cards);
        
        setSizeFull();
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        game = gameManager.findGameByPlayerId(getSession().getSession().getId());
        player = gameManager.findPlayerByPlayerId(getSession().getSession().getId());
        
        if (!player.equals(game.getHost())) {
            actions.setVisible(false);
            actions.setEnabled(false);
        }
        
        
        System.out.println("enter");
        display.addComponent(new Label("this is the view for game: " + (game != null ? game.getName() : null)));
    }
    
    private void initGameEventListener() {
        listener.addHandlers(new PlayerJoinedEventHandler(this), new StartGameEventHandler(this), 
                new PlayerTzarEventHandler(this));
        listener.start();
        
        addDetachListener(new DetachListener() {

            @Override
            public void detach(DetachEvent event) {
                listener.stop();
            }
        });
    }
    
    public void onGameStart() {
        startGame.setEnabled(false);
        refreshCards();
    }
    
    private void refreshCards() {
        cards.addComponent(newCard("Card1"));
        cards.addComponent(newCard("Card2"));
        cards.addComponent(newCard("Card3"));
    }
    
    private Panel newCard(String text) {
        final Panel panel = new Panel(text);
        
        panel.addClickListener(new com.vaadin.event.MouseEvents.ClickListener() {

            @Override
            public void click(MouseEvents.ClickEvent event) {
                panel.setCaption(panel.getCaption() + " (selected)");
                game.playCard(player);
            }
        });
        
        return panel;
    }

    void onCardTzar() {
        Notification.show("You are the card tzar");
    }
}
