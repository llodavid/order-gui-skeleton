package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.stream.Collectors;

public class SearchItemView extends CustomComponent implements View {

    private ItemResource itemResource;
    private Grid grid;
    private TextField filter;


    public SearchItemView(ItemResource itemResource) {
        this.itemResource = itemResource;
        grid = new Grid();
        init();
    }

    protected void init() {
        VerticalLayout mainLayout = new VerticalLayout();
        renderItems(mainLayout);
        setCompositionRoot(mainLayout);
    }

    private void renderItems(VerticalLayout mainLayout) {
        populateGrid(itemResource.getItems());
        HorizontalLayout gridLayout = new HorizontalLayout(grid);

        gridLayout.setWidth("100%");

        HorizontalLayout titleLayout = new HorizontalLayout(addTitleLabel());
        HorizontalLayout filterLayout = new HorizontalLayout(createFilter());
        HorizontalLayout topLayout = new HorizontalLayout(titleLayout,filterLayout);
        topLayout.setComponentAlignment(titleLayout, Alignment.MIDDLE_LEFT);
        topLayout.setComponentAlignment(filterLayout, Alignment.BOTTOM_RIGHT);
        topLayout.setWidth("100%");
        VerticalLayout contentLayout = new VerticalLayout(topLayout, gridLayout);
        contentLayout.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);
        contentLayout.setComponentAlignment(topLayout, Alignment.TOP_RIGHT);
        contentLayout.setWidth("80%");

        mainLayout.addComponents(contentLayout);
        mainLayout.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
    }

    private CssLayout createFilter() {
        filter = new TextField("");
        filter.setWidth("300");
        filter.setInputPrompt("Search for an item by name...");

        filter.addTextChangeListener(text -> populateGrid(filterByName(itemResource.getItems(), text.getText())));

        Button btnClear = new Button("Clear");
        btnClear.addClickListener(click -> {
            filter.clear();
            populateGrid(itemResource.getItems());
        });
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filter, btnClear);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        filtering.addComponents(filter, btnClear);
        return filtering;
    }

    private List<Item> filterByName(List<Item> items, String textToFilter) {
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(textToFilter.toLowerCase()))
                .collect(Collectors.toList());
    }

    private void populateGrid(List<Item> items) {
        BeanItemContainer<Item> container = new BeanItemContainer<>(Item.class, items);
        grid.setContainerDataSource(container);
        grid.removeAllColumns();
        grid.setWidth("100%");
        grid.setHeightMode(HeightMode.CSS);
        grid.setColumns("name", "description", "price", "amountOfStock");
        grid.getColumn("amountOfStock").setHeaderCaption("Amount of Stock");
    }

    private Label addTitleLabel() {
        Label titleLabel = new Label("Items");
        titleLabel.setStyleName(ValoTheme.LABEL_HUGE);
        return titleLabel;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
