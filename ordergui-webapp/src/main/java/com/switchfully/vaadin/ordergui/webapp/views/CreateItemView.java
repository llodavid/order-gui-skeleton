package com.switchfully.vaadin.ordergui.webapp.views;

import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CreateItemView extends CustomComponent implements View {

    private VerticalLayout mainLayout;

    public CreateItemView() {
        Label titleLabel = new Label("New Item");
        titleLabel.setStyleName(ValoTheme.LABEL_HUGE);
        TextField txtName = new TextField("Name");
        txtName.setInputPrompt("Enter the name of your item...");
        txtName.setWidth("70%");
        TextArea txtDescription = new TextArea("Description");
        txtDescription.setInputPrompt("Enter your item description...");
        txtDescription.setWidth("85%");
        txtDescription.setHeight("150px");
        TextField txtPrice = new TextField("Price");
        txtPrice.setNullRepresentation("0.00");
        txtPrice.setWidth("75px");
        TextField txtStock = new TextField("Amount of Stock");
        txtStock.setNullRepresentation("0");
        txtStock.setWidth("75px");
        Button btnCreate = new Button("Create");
        btnCreate.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        btnCreate.setWidth("100px");
        btnCreate.setHeight("50px");
        Button btnCancel = new Button("Cancel");
        btnCancel.setStyleName(ValoTheme.BUTTON_PRIMARY);

        VerticalLayout bigTxt = new VerticalLayout(txtName, txtDescription);
        bigTxt.setSpacing(true);
        HorizontalLayout smallTxt = new HorizontalLayout(txtPrice, txtStock);
        smallTxt.setSpacing(true);
        HorizontalLayout buttons = new HorizontalLayout(btnCreate, btnCancel);
        buttons.setSpacing(true);
        VerticalLayout contentLayout = new VerticalLayout(titleLabel, bigTxt, smallTxt, buttons);
        contentLayout.setWidth("60%");
        contentLayout.setComponentAlignment(bigTxt, Alignment.MIDDLE_LEFT);
        contentLayout.setSpacing(true);
        mainLayout = new VerticalLayout(contentLayout);
        mainLayout.setComponentAlignment(contentLayout, Alignment.BOTTOM_CENTER);
        setCompositionRoot(mainLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
