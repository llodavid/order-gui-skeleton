package com.switchfully.vaadin.ordergui.webapp.views.searchViews;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.components.TopMenu;
import com.switchfully.vaadin.ordergui.webapp.views.detailItemViews.DetailItemView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.switchfully.vaadin.ordergui.webapp.OrderGUI.VIEW_CREATE_ITEM;

public class SearchItemView extends CustomComponent implements View, SearchItemViewInterface {

    private Grid grid;
    private TextField filter;
    private Button btnCreate;
    private Button btnEdit;
    private Item item;
    private List<SearchItemViewListener> searchItemViewListeners;
    private VerticalLayout mainLayout;
    private DetailItemView detailItemView;
    private Navigator navigator;

    public SearchItemView() {
        searchItemViewListeners = new ArrayList<>();
        grid = new Grid();
        init();
        System.out.println(System.getProperty("user.dir"));
    }

    protected void init() {
        mainLayout = new VerticalLayout();
        renderItems(mainLayout);
        setCompositionRoot(mainLayout);
    }

    private void renderItems(VerticalLayout mainLayout) {
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
        btnCreate = new Button("Create", click -> {
            getUI().addWindow(detailItemView);
            detailItemView.setVisible(true);
        });
        btnCreate.setDescription("Create a new item");
        btnCreate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnEdit = new Button("Edit");
        btnEdit.setDescription("Edit an item");
        btnEdit.setVisible(false);
        btnEdit.addClickListener(click -> getUI().getNavigator().navigateTo(VIEW_CREATE_ITEM + "/" + item.getId()));
        btnEdit.setStyleName(ValoTheme.BUTTON_PRIMARY);
        buttonLayout.addComponents(btnCreate, btnEdit);
        buttonLayout.setSpacing(true);
        return buttonLayout;
    }

    private CssLayout createFilter() {
        filter = new TextField("");
        filter.setWidth("300");
        filter.setInputPrompt("Search for an item by name...");

        filter.addTextChangeListener(text -> searchItemViewListeners.forEach(
                searchItemViewListener -> searchItemViewListener.filterChanged(text.getText())
                ));
                //populateGrid(filterByName(itemResource.getItems(), text.getText())));

        Button btnClear = new Button("Clear");
        btnClear.addClickListener(click -> {
            filter.clear();
            searchItemViewListeners.forEach(searchItemViewListener -> searchItemViewListener.clearSearchFilter());
//            populateGrid(itemResource.getItems());
        });
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filter, btnClear);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        filtering.addComponents(filter, btnClear);
        return filtering;
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
        searchItemViewListeners.forEach(listener -> listener.viewEntered());
        filter.clear();
    }

    @Override
    public void addListener(SearchItemViewListener searchItemViewListener) {
        searchItemViewListeners.add(searchItemViewListener);
    }

    @Override
    public void setResultList(List<Item> items) {
        populateGrid(items);
    }

    @Override
    public void addDetailView(DetailItemView detailItemView) {
        detailItemView.center();
        detailItemView.setHeight("550px");
        detailItemView.setWidth("500px");
        //detailItemView.setContent(detailItemView);
        detailItemView.setModal(true);
        this.detailItemView = detailItemView;
        detailItemView.addCloseListener(close -> searchItemViewListeners.forEach(
                searchItemViewListener -> searchItemViewListener.viewEntered()));

//        navigator = new Navigator(UI.getCurrent(),new Panel());
//        navigator.addView(VIEW_CREATE_ITEM,detailItemView);
//        btnCreate.addClickListener( click -> { navigator.navigateTo(VIEW_CREATE_ITEM);
//        detailItemView.setVisible(true);
//            getUI().addWindow(detailItemView);
//        });
    }
}
