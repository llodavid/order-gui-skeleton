package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.views.detailItemViews.DetailItemModel;
import com.switchfully.vaadin.ordergui.webapp.views.detailItemViews.DetailItemView;
import com.switchfully.vaadin.ordergui.webapp.views.detailItemViews.DetailItemViewPresenter;
import com.switchfully.vaadin.ordergui.webapp.views.searchViews.SearchItemModel;
import com.switchfully.vaadin.ordergui.webapp.views.searchViews.SearchItemView;
import com.switchfully.vaadin.ordergui.webapp.views.searchViews.SearchItemPresenter;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class OrderGUI extends UI {

    public static final String VIEW_ORDER_HOME = "";
    public static final String VIEW_CREATE_ITEM = "detail";
    public static final String PICTURES_PATH = "C:/switchfully-repositories/order-gui-skeleton/pictures";

    private ItemResource itemResource;
    private Navigator navigator;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
    }

    @Override
    protected void init(VaadinRequest request) {
        navigator = new Navigator(this, this);

        SearchItemView searchItemView = new SearchItemView();
        SearchItemPresenter searchPresenter = new SearchItemPresenter(searchItemView, new SearchItemModel(itemResource));
        navigator.addView(VIEW_ORDER_HOME, searchItemView);
        DetailItemView detailItemView = new DetailItemView();
        DetailItemViewPresenter viewPresenter = new DetailItemViewPresenter(detailItemView, new DetailItemModel(itemResource));
        searchItemView.addDetailView(detailItemView);
        navigator.addView(VIEW_CREATE_ITEM, detailItemView);
    }
}