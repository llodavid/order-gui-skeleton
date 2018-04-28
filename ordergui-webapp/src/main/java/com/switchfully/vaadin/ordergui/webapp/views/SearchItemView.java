package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.components.TopMenu;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class SearchItemView extends CustomComponent implements View {

    private ItemResource itemResource;
    private Grid grid;
    private TextField filter;
    private Button btnCreate;
    private Button btnEdit;
    private Item item;


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
        selectItem();

        gridLayout.setWidth("100%");

        VerticalLayout titleLayout = new VerticalLayout(addTitleLabel());
        HorizontalLayout filterLayout = new HorizontalLayout(createFilter());
        HorizontalLayout itemButtonLayout = new HorizontalLayout(itemButtonLayout());
        HorizontalLayout topLayout = new HorizontalLayout(filterLayout,itemButtonLayout);
        topLayout.setComponentAlignment(filterLayout, Alignment.BOTTOM_LEFT);
        topLayout.setComponentAlignment(itemButtonLayout, Alignment.BOTTOM_RIGHT);
        topLayout.setWidth("100%");

        VerticalLayout logoLayout = createLogo();
        VerticalLayout menuLayout = new VerticalLayout(new TopMenu(), titleLayout);
        menuLayout.setComponentAlignment(titleLayout,Alignment.BOTTOM_LEFT);
        HorizontalLayout bannerLayout = new HorizontalLayout(menuLayout,logoLayout);
        bannerLayout.setWidth("100%");
        bannerLayout.setHeight("80px");
        bannerLayout.setComponentAlignment(menuLayout, Alignment.TOP_LEFT);
        bannerLayout.setComponentAlignment(logoLayout, Alignment.TOP_RIGHT);

        VerticalLayout contentLayout = new VerticalLayout(bannerLayout,topLayout, gridLayout);
        contentLayout.setComponentAlignment(bannerLayout,Alignment.MIDDLE_CENTER);
        contentLayout.setComponentAlignment(topLayout,Alignment.TOP_CENTER);
        contentLayout.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);
        contentLayout.setWidth("80%");

        mainLayout.addComponents(contentLayout);
        mainLayout.setComponentAlignment(contentLayout, Alignment.TOP_CENTER);
    }

    private VerticalLayout createLogo() {
        VerticalLayout logoLayout = new VerticalLayout();
        FileResource logo = new FileResource(new File(OrderGUI.PICTURES_PATH+"/order.png"));

        Image orderImage = new Image();
        orderImage.setSource(logo);
        orderImage.setWidth("150px");

        logoLayout.addComponent(orderImage);
        logoLayout.setComponentAlignment(orderImage, Alignment.TOP_RIGHT);
        return logoLayout;
    }

    private HorizontalLayout itemButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        btnCreate = new Button("Create", click -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_CREATE_ITEM));
        btnCreate.setDescription("Create a new item");
        btnCreate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnEdit = new Button("Edit");
        btnEdit.setDescription("Edit an item");
        btnEdit.addClickListener(click -> getUI().getNavigator().navigateTo(OrderGUI.VIEW_CREATE_ITEM + "/" + item.getId()));
        btnEdit.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonLayout.addComponents(btnCreate, btnEdit);
        buttonLayout.setSpacing(true);
        return buttonLayout;
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

    private void selectItem(){
        grid.addSelectionListener(selection -> {
            if (selection.getSelected().isEmpty()) {
                btnEdit.setVisible(false);
            } else {
                btnEdit.setVisible(true);
                item = (Item)selection.getSelected().iterator().next();
            }
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        populateGrid(itemResource.getItems());
    }
}
