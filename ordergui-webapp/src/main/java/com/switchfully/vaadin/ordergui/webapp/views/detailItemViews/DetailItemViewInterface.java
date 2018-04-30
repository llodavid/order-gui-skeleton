package com.switchfully.vaadin.ordergui.webapp.views.detailItemViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;

public interface DetailItemViewInterface {

    void addListener(DetailItemViewListener detailItemViewListener);

    void setItem(Item item);


    interface DetailItemViewListener{

        void savedButtonClicked(Item item);

        void updateButtonClicked(Item item);

        void viewEnteredWithParameters(String parameters);
    }
}
