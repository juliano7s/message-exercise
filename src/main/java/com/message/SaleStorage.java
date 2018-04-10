package com.message;

import com.message.vo.Message;
import com.message.vo.Sale;

import java.util.List;
import java.util.Set;

public interface SaleStorage {
    void add(Sale sale);
    void add(Message adjustment);
    Set<String> getProductsSold();
    List<Sale> getSalesByProduct(String product);
    List<Message> getAdjustmentsByProduct(String product);
    Double getTotalValue(String product);
    Integer getTotalSales(String product);
}
