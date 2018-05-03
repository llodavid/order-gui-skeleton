package com.switchfully.vaadin.ordergui.webapp.views.searchViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchItemModel {

    private ItemResource itemResource;
    private List<Item> resultList;
    private List<SearchItemModelListener> searchItemModelListeners;

    public SearchItemModel(ItemResource itemResource) {
        this.itemResource = itemResource;
        resultList = itemResource.getItems();
        searchItemModelListeners = new ArrayList<>();
    }

    public void setSearchFilter(String textToFilter) {
        resultList = itemResource.getItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(textToFilter.toLowerCase()))
                .collect(Collectors.toList());
        searchItemModelListeners.forEach(listener -> listener.itemResultListUpdated(resultList));
    }

    public void addListener(SearchItemModelListener searchItemModelListener){
        searchItemModelListeners.add(searchItemModelListener);
    }

    public void resetList() {
        resultList = itemResource.getItems();
        searchItemModelListeners.forEach(listener -> listener.itemResultListUpdated(resultList));
    }


    public interface SearchItemModelListener {

        void itemResultListUpdated(List<Item> items);
    }
}
