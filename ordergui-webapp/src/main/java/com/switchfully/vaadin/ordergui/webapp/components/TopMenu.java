package com.switchfully.vaadin.ordergui.webapp.components;


import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TopMenu extends CustomComponent {

    private MenuBar menuBar;
    public TopMenu() {
        VerticalLayout mainLayout = new VerticalLayout();
        menuBar = new MenuBar();
        menuBar.addItem("Home", selectedItem -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME));
        menuBar.addItem("Items", selectedItem -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME));
        menuBar.addItem("Customers", selectedItem -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME));
        menuBar.addStyleName(ValoTheme.MENUBAR_SMALL);
        mainLayout.addComponent(menuBar);
        setCompositionRoot(mainLayout);
    }
}
