package com.message.vo;

import java.util.Objects;

public class Sale {
    private String product;
    private Double value;

    public Sale(String product, Double value) {
        this.product = product;
        this.value = value;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(product, sale.product) &&
                Objects.equals(value, sale.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, value);
    }


    @Override
    public Sale clone() {
        Sale cloned = new Sale(this.product, this.value);
        return cloned;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "product='" + product + '\'' +
                ", value=" + value +
                '}';
    }
}
