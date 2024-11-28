package com.for_comprehension.stream.domain.domain;

import java.util.List;
import java.util.Objects;

public class Customer {

    private final String name;
    private final Integer age;
    private final Integer budget;
    private final List<ItemEntry> wantToBuy;

    public Customer(String name, Integer age, Integer budget, List<ItemEntry> wantToBuy) {
        this.name = name;
        this.age = age;
        this.budget = budget;
        this.wantToBuy = wantToBuy;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getBudget() {
        return budget;
    }

    public List<ItemEntry> getWantToBuy() {
        return wantToBuy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
          Objects.equals(age, customer.age) &&
          Objects.equals(budget, customer.budget) &&
          Objects.equals(wantToBuy, customer.wantToBuy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, budget, wantToBuy);
    }

    @Override
    public String toString() {
        return "Customer{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", budget=" + budget +
          ", wantToBuy=" + wantToBuy +
          '}';
    }
}
