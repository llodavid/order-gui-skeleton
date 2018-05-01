package com.switchfully.vaadin.ordergui.webapp.views.detailItemViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;

import java.util.ArrayList;
import java.util.List;

public class DetailItemModel {
    private List<DetailModelListener> detailModelListeners;

    private ItemResource itemResource;

    public DetailItemModel(ItemResource itemResource) {
        detailModelListeners = new ArrayList<>();
        this.itemResource = itemResource;
    }

    public void addListener(DetailModelListener detailModelListener){
        detailModelListeners.add(detailModelListener);
    }

    public void saveItem(Item item) {
        itemResource.save(item);
    }

    public void updateItem(Item item) {
        itemResource.update(item);
    }

    public void setItemParameters(String parameters) {
        detailModelListeners.forEach(lister -> lister.setItem(itemResource.getItem(parameters)));
    }

    public interface DetailModelListener {

        void setItem(Item item);
    }
}
