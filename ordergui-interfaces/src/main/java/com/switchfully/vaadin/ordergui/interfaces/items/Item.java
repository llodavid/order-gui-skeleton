package com.switchfully.vaadin.ordergui.interfaces.items;

public class Item {
    public String id;
    public String name;
    public String description;
    public float price;
    public int amountOfStock;
    public String stockUrgency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmountOfStock() {
        return amountOfStock;
    }

    public void setAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
    }

    public String getStockUrgency() {
        return stockUrgency;
    }

    public void setStockUrgency(String stockUrgency) {
        this.stockUrgency = stockUrgency;
    }
}
