package com.switchfully.vaadin.ordergui.interfaces.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ItemResource {

    private RestTemplate restTemplate;
    private final String ITEM_URL = "http://localhost:9000/items";

    @Autowired
    public ItemResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Item> getItems() {
        Item[] items = restTemplate.getForObject(ITEM_URL, Item[].class);
        return Arrays.asList(items);
    }

    public Item getItem(String id){
        return restTemplate.getForObject(ITEM_URL + "/" + id, Item.class);
    }

    public Item save(Item item){
        return restTemplate.postForObject(ITEM_URL, item, Item.class);
    }

    public Item update(Item item) {
        return restTemplate.exchange(ITEM_URL + "/" + item.getId(), HttpMethod.PUT, new HttpEntity<>(item), Item.class).getBody();
    }
}