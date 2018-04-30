package com.switchfully.vaadin.ordergui.webapp.views.detailItemViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;

public class DetailItemViewPresenter implements DetailItemViewInterface.DetailItemViewListener, DetailItemModel.DetailModelListener {

    private DetailItemModel detailItemModel;
    private DetailItemViewInterface detailItemViewInterface;

    public DetailItemViewPresenter(DetailItemViewInterface detailItemViewInterface, DetailItemModel detailItemModel) {
        this.detailItemViewInterface = detailItemViewInterface;
        this.detailItemModel = detailItemModel;
        detailItemModel.addListener(this);
        detailItemViewInterface.addListener(this);
    }

    @Override
    public void savedButtonClicked(Item item) {
        detailItemModel.saveItem(item);
    }

    @Override
    public void updateButtonClicked(Item item) {
        detailItemModel.updateItem(item);
    }

    @Override
    public void viewEnteredWithParameters(String parameters) {
        detailItemModel.setItemParameters(parameters);
    }

    @Override
    public void setItem(Item item) {
        detailItemViewInterface.setItem(item);
    }
}
