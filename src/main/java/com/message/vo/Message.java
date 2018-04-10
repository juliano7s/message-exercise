package com.message.vo;

import java.util.Objects;

public class Message {

    private MessageType type;
    private Sale sale;
    private Integer numberOfSales;
    private SaleOperation operation;
    private Double adjustment;

    protected Message(MessageType type, Sale sale, Integer numberOfSales, SaleOperation operation, Double adjustment) {
        this.type = type;
        this.sale = sale;
        this.numberOfSales = numberOfSales;
        this.operation = operation;
        this.adjustment = adjustment;
    }

    public Message(String product, Double value) {
        this(MessageType.SINGLE, new Sale(product, value), 1, null,0D);
    }

    public Message(String product, Double value, Integer numberOfSales) {
        this(MessageType.MULTI, new Sale(product, value), numberOfSales, null, 0D);
    }

    public Message(String product, Double value, String operation, Double adjustment) {
        this(MessageType.ADJUSTMENT, new Sale(product, value), 1,
                SaleOperation.valueOf(operation.toUpperCase()), adjustment);
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(Integer numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public SaleOperation getOperation() {
        return operation;
    }

    public void setOperation(SaleOperation operation) {
        this.operation = operation;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return type == message.type &&
                Objects.equals(sale, message.sale) &&
                Objects.equals(numberOfSales, message.numberOfSales) &&
                operation == message.operation &&
                Objects.equals(adjustment, message.adjustment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, sale, numberOfSales, operation, adjustment);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", sale=" + sale +
                ", numberOfSales=" + numberOfSales +
                ", operation=" + operation +
                ", adjustment=" + adjustment +
                '}';
    }
}
