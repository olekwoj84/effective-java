package com.for_comprehension.stream.domain.dataset;

import com.for_comprehension.stream.domain.domain.Customer;
import com.for_comprehension.stream.domain.domain.Item;
import com.for_comprehension.stream.domain.domain.ItemEntry;
import com.for_comprehension.stream.domain.domain.Mall;
import com.for_comprehension.stream.domain.domain.Shop;

import java.util.Arrays;

public class Store {
    private final Mall mall;

    public Store() {
        Shop whiteFuniture = new Shop("White Furniture",
          Arrays.asList(
            new Item("chair", 2000),
            new Item("table", 5500),
            new Item("small chair", 1800),
            new Item("small table", 2800)
          ));

        Shop dishDevices = new Shop("Dish Devices",
          Arrays.asList(
            new Item("cup", 380),
            new Item("plate", 680),
            new Item("fork", 210),
            new Item("spoon", 210),
            new Item("chopsticks", 180)
          ));

        Shop doItOurselves = new Shop("The Do It Ourselves",
          Arrays.asList(
            new Item("rope", 800),
            new Item("saw", 1400),
            new Item("bond", 480),
            new Item("plane", 2200),
            new Item("screwdriver", 600)
          ));

        Shop electrics = new Shop("Electrics",
          Arrays.asList(
            new Item("chair", 600),
            new Item("desk", 1800),
            new Item("cable", 230),
            new Item("speaker", 19000),
            new Item("headphone", 8800),
            new Item("earphone", 7800)
          ));

        Shop apothecary = new Shop("Amazing Apothecary",
          Arrays.asList(
            new Item("cold medicine", 800),
            new Item("ointment", 500),
            new Item("eye-drops", 600),
            new Item("poultice", 900)
          ));

        Shop supermarket = new Shop("The Rapid Supermarket",
          Arrays.asList(
            new Item("spinach", 100),
            new Item("ginseng", 120),
            new Item("onion", 160),
            new Item("crisps", 80),
            new Item("ice cream", 200)
          ));

        Customer joe = new Customer("Joe", 22, 8000,
          Arrays.asList(
            new ItemEntry("small table"),
            new ItemEntry("plate"),
            new ItemEntry("fork")
          ));

        Customer steven = new Customer("Steven", 27, 2000,
          Arrays.asList(
            new ItemEntry("ice cream"),
            new ItemEntry("screwdriver"),
            new ItemEntry("cable"),
            new ItemEntry("earphone")
          ));

        Customer patrick = new Customer("Patrick", 28, 4000,
          Arrays.asList(
            new ItemEntry("onion"),
            new ItemEntry("ice cream"),
            new ItemEntry("crisps"),
            new ItemEntry("chopsticks")
          ));

        Customer diana = new Customer("Diana", 38, 12000,
          Arrays.asList(
            new ItemEntry("cable"),
            new ItemEntry("speaker"),
            new ItemEntry("headphone")
          ));

        Customer chris = new Customer("Chris", 26, 9000,
          Arrays.asList(
            new ItemEntry("saw"),
            new ItemEntry("bond"),
            new ItemEntry("plane"),
            new ItemEntry("bag")
          ));

        Customer kathy = new Customer("Kathy", 22, 6000,
          Arrays.asList(
            new ItemEntry("cold medicine")
          ));

        Customer alice = new Customer("Alice", 32, 2500,
          Arrays.asList(
            new ItemEntry("chair"),
            new ItemEntry("desk")
          ));

        Customer andrew = new Customer("Andrew", 35, 11000,
          Arrays.asList(
            new ItemEntry("pants"),
            new ItemEntry("coat")
          ));

        Customer martin = new Customer("Martin", 21, 1000,
          Arrays.asList(
            new ItemEntry("cup"),
            new ItemEntry("plate"),
            new ItemEntry("fork"),
            new ItemEntry("spoon")
          ));

        Customer amy = new Customer("Amy", 36, 2000,
          Arrays.asList(
            new ItemEntry("ointment"),
            new ItemEntry("poultice"),
            new ItemEntry("spinach"),
            new ItemEntry("ginseng"),
            new ItemEntry("onion")
          ));

        mall = new Mall(Arrays.asList(
          whiteFuniture, dishDevices, doItOurselves, electrics, apothecary, supermarket
        ), Arrays.asList(
          joe, steven, patrick, diana, alice, andrew, martin, amy, chris, kathy
        ));
    }

    protected Mall getMall() {
        return mall;
    }
}
