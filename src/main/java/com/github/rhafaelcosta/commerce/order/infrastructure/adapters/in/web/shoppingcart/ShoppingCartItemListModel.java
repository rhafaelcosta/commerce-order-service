package com.github.rhafaelcosta.commerce.order.infrastructure.adapters.in.web.shoppingcart;

import com.github.rhafaelcosta.commerce.order.core.ports.in.shoppingcart.ShoppingCartItemOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemListModel {

    private List<ShoppingCartItemOutput> items = new ArrayList<>();

}