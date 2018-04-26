package com.switchfully.vaadin.ordergui.interfaces.customers;

import com.switchfully.vaadin.ordergui.interfaces.items.Item;
import com.switchfully.vaadin.ordergui.interfaces.items.ItemResource;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemResourceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ItemResource itemResource;

    @Test
    public void getItems() {
        Item item1 = new Item();
        Item item2 = new Item();
        when(restTemplate.getForObject("http://localhost:9000/items", Item[].class))
                .thenReturn(new Item[]{item1, item2});

        List<Item> items = itemResource.getItems();

        Assertions.assertThat(items).containsExactlyInAnyOrder(item1, item2);
    }

}