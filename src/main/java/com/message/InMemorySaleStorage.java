package com.message;

import com.message.vo.Message;
import com.message.vo.Sale;

import java.util.*;

/**
 * Stores and retrieves sale information:
 *  - sales by product
 *  - list of adjustment made by product
 *  - total value sold
 *  - total number of sales made
 */
public class InMemorySaleStorage implements SaleStorage {

    private Map<String, List<Sale>> sales;
    private Map<String, List<Message>> adjustments;

    public InMemorySaleStorage() {
        this.sales = new HashMap<>();
        this.adjustments = new HashMap<>();
    }

    public void add(Sale sale) {
        String product = sale.getProduct();
        sales.computeIfAbsent(product, k -> new ArrayList<>());
        sales.get(product).add(sale);
    }

    public void add(Message adjustment) {
        String product = adjustment.getSale().getProduct();
        adjustments.computeIfAbsent(product, k -> new ArrayList<>());
        adjustments.get(product).add(adjustment);
    }

    public Set<String> getProductsSold() {
        return sales.keySet();
    }

    public List<Sale> getSalesByProduct(String product) {
        return sales.get(product);
    }

    public List<Message> getAdjustmentsByProduct(String product) {
        return adjustments.get(product);
    }


    public Double getTotalValue(String product) {
        Double total = 0.0;
        for (Sale sale : sales.get(product)) {
            total += sale.getValue();
        }
        return total;
    }

    public Integer getTotalSales(String product) {
        return sales.get(product).size();
    }
}
