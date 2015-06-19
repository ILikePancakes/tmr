package tmr;


import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("tmr")
@Widgetset("tmr.TMRWidgetset")
@SpringUI
@Push
public class ApplicationUI extends UI {
    
    @Autowired
    private SpringViewProvider viewProvider;
    
    private Navigator navigator;
    
    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        setContent(mainLayout);
        
        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        
        mainLayout.addComponent(viewContainer);
        
        navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
    }
}
