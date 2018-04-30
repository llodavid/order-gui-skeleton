package com.switchfully.vaadin.ordergui.webapp.views.searchViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;

import java.util.List;

public class SearchItemPresenter implements SearchItemViewInterface.SearchItemViewListener, SearchItemModel.SearchItemModelListener {

    private SearchItemModel searchItemModel;
    private SearchItemViewInterface searchItemViewInterface;

    public SearchItemPresenter(SearchItemViewInterface searchItemViewInterface, SearchItemModel searchItemModel) {
        this.searchItemViewInterface = searchItemViewInterface;
        this.searchItemModel = searchItemModel;
        searchItemModel.addListener(this);
        searchItemViewInterface.addListener(this);
    }

    @Override
    public void filterChanged(String filter) {
        searchItemModel.setSearchFilter(filter);
    }

    @Override
    public void clearSearchFilter() {
        searchItemModel.resetList();
    }

    @Override
    public void viewEntered() {
        searchItemModel.resetList();
    }

    @Override
    public void itemResultListUpdated(List<Item> items) {
        searchItemViewInterface.setResultList(items);
    }
}
