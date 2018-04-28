package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.CreateItemView;
import com.switchfully.vaadin.ordergui.webapp.views.SearchItemView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    public static final String VIEW_ORDER_HOME = "";
    public static final String VIEW_CREATE_ITEM = "create";
    public static final String PICTURES_PATH = "C:/Users/7515770/switchfully-repositories/codelabs/vaadin/order-gui/order-gui-skeleton/pictures";

    private ItemResource itemResource;
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this, this);

        navigator.addView(VIEW_ORDER_HOME, new SearchItemView(itemResource));
        navigator.addView(VIEW_CREATE_ITEM, new CreateItemView(itemResource));
    }

}