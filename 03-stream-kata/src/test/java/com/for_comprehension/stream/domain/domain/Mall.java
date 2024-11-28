package com.for_comprehension.stream.domain.domain;

import java.util.List;

public class Mall {

    private final List<Shop> shopList;
    private final List<Customer> customerList;

    public Mall(List<Shop> shopList, List<Customer> customerList) {
        this.shopList = shopList;
        this.customerList = customerList;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}
