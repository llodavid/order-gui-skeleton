package com.switchfully.vaadin.ordergui.webapp;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
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

    private ItemResource itemResource;
    private Grid grid;
    private TextField filter;

    @Autowired
    public OrderGUI(ItemResource itemResource) {
        this.itemResource = itemResource;
        grid = new Grid();
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout();
        addTitleLabel(mainLayout);
        renderItems(mainLayout);
        setContent(mainLayout);
    }

    private void renderItems(VerticalLayout mainLayout) {
        populateGrid(itemResource.getItems());
        mainLayout.addComponents(createFilter(), grid );
    }

    private CssLayout createFilter() {

        filter = new TextField("");
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
        grid.setColumns("name", "description", "price", "amountOfStock");
        grid.getColumn("amountOfStock").setHeaderCaption("Amount of Stock");
    }

    private void addTitleLabel(VerticalLayout mainLayout) {
//        mainLayout.addComponent(
//                new HorizontalLayout(
//                        new Label("ITEMS:")
//                )
//        );
    }
}