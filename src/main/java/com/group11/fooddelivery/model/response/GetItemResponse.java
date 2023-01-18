package com.group11.fooddelivery.model.response;

import com.group11.fooddelivery.model.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetItemResponse extends Response{
    private List<Item> allItems;
}
