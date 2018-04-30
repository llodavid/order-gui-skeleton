package com.switchfully.vaadin.ordergui.webapp.views.searchViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.webapp.views.detailItemViews.DetailItemView;

import java.util.List;

public interface SearchItemViewInterface {
    void addListener(SearchItemViewListener searchItemViewListener);

    void setResultList(List<Item> items);

    void addDetailView(DetailItemView detailItemView);

    interface SearchItemViewListener {
        void filterChanged(String filter);

        void clearSearchFilter();

        void viewEntered();
    }
}
