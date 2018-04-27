package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.switchfully.vaadin.ordergui.webapp.OrderGUI;
import com.switchfully.vaadin.ordergui.webapp.components.TopMenu;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.core.annotation.Order;

public class CreateItemView extends CustomComponent implements View {

    private VerticalLayout mainLayout;
    private Label titleLabel;
    private TextField name;
    private TextArea description;
    private TextField price;
    private TextField amountOfStock;
    private Item item;
    private ItemResource itemResource;
    private BeanFieldGroup<Item> itemBeanFieldGroup;
    private HorizontalLayout createButtons;
    private HorizontalLayout updateButtons;

    public CreateItemView(ItemResource itemResource) {
        this.itemResource = itemResource;
        titleLabel = createTitle();
        name = createItemName();
        description = createDescription();
        price = createTxtPrice();
        amountOfStock = createStockField();

        setItem(new Item());

        VerticalLayout contentLayout = createContent();

        mainLayout = new VerticalLayout(contentLayout);
        mainLayout.setComponentAlignment(contentLayout, Alignment.BOTTOM_CENTER);
        setCompositionRoot(mainLayout);
    }

    private VerticalLayout createContent() {
        VerticalLayout nameAndDescriptionLayout = new VerticalLayout(name, description);
        nameAndDescriptionLayout.setSpacing(true);

        HorizontalLayout priceAndStockLayout = new HorizontalLayout(price, amountOfStock);
        priceAndStockLayout.setSpacing(true);

        createButtons = createItemButtons();
        updateButtons = updateItemButtons();
        VerticalLayout contentLayout = new VerticalLayout(new TopMenu(), titleLabel, nameAndDescriptionLayout, priceAndStockLayout, createButtons, updateButtons);
        contentLayout.setWidth("60%");
        contentLayout.setComponentAlignment(nameAndDescriptionLayout, Alignment.MIDDLE_LEFT);
        contentLayout.setSpacing(true);
        return contentLayout;
    }

    private HorizontalLayout createItemButtons() {
        Button btnCreate = new Button("Create", click -> save());
        btnCreate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnCreate.setWidth("100px");
        btnCreate.setHeight("50px");
        Button btnCancel = new Button("Cancel",click->getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME));
        btnCancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(btnCreate, btnCancel);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    private HorizontalLayout updateItemButtons() {
        Button btnCreate = new Button("Update", click -> update());
        btnCreate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnCreate.setWidth("100px");
        btnCreate.setHeight("50px");
        Button btnCancel = new Button("Cancel",click->getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME));
        btnCancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(btnCreate, btnCancel);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    private void save() {
        try {
            itemBeanFieldGroup.commit();
            itemResource.save(item);
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME);
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    }

    private void update(){
        try {
            itemBeanFieldGroup.commit();
            itemResource.update(item);
            getUI().getNavigator().navigateTo(OrderGUI.VIEW_ORDER_HOME);
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    }

    private TextField createStockField() {
        TextField txtStock = new TextField("Amount of Stock");
        txtStock.setNullRepresentation("0");
        txtStock.setWidth("75px");
        return txtStock;
    }

    private TextField createTxtPrice() {
        TextField txtPrice = new TextField("Price");
        txtPrice.setNullRepresentation("0.00");
        txtPrice.setWidth("75px");
        txtPrice.setIcon(FontAwesome.EURO);
        return txtPrice;
    }

    private TextArea createDescription() {
        TextArea txtDescription = new TextArea("Description");
        txtDescription.setInputPrompt("Enter your item description...");
        txtDescription.setWidth("85%");
        txtDescription.setHeight("150px");
        return txtDescription;
    }

    private TextField createItemName() {
        TextField txtName = new TextField("Name");
        txtName.setInputPrompt("Enter the name of your item...");
        txtName.setWidth("70%");
        return txtName;
    }

    private Label createTitle() {
        Label titleLabel = new Label("New Item");
        titleLabel.setStyleName(ValoTheme.LABEL_HUGE);
        return titleLabel;
    }

    private void setItem(Item item){
        this.item = new Item();
        this.item.setId(item.getId());
        this.item.setName(item.getName());
        this.item.setDescription(item.getDescription());
        this.item.setPrice(item.getPrice());
        this.item.setAmountOfStock(item.getAmountOfStock());
        itemBeanFieldGroup = BeanFieldGroup.bindFieldsBuffered(this.item, this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters().isEmpty()) {
            setItem(new Item());
            createButtons.setVisible(true);
            updateButtons.setVisible(false);
        } else {
            setItem(itemResource.getItem(event.getParameters()));
            updateButtons.setVisible(true);
            createButtons.setVisible(false);
        }
    }
}
