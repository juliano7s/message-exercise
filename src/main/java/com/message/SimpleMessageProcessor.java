package com.message;

import com.message.vo.Message;
import com.message.vo.MessageType;
import com.message.vo.Sale;

/**
 * Process each passed Message into the saleStorage object. The SimpleMessageProcessor adds a number of sales to the storage or
 * performs adjustment to sales of the same product.
 */
public class SimpleMessageProcessor implements MessageProcessor {

    private SaleStorage saleStorage;

    public SimpleMessageProcessor(SaleStorage saleStorage) {
        this.saleStorage = saleStorage;
    }

    public void process(Message message) {
        addSales(message.getSale(), message.getNumberOfSales());

        if (message.getType() == MessageType.ADJUSTMENT) {
            adjustSales(message);
        }
    }

    private void addSales(Sale sale, Integer numberOfSales) {
        for (int i = 0; i < numberOfSales; i++) {
            saleStorage.add(sale.clone());
        }
    }

    private void adjustSales(Message adjustment) {
        for (Sale sale : saleStorage.getSalesByProduct(adjustment.getSale().getProduct())) {
            switch (adjustment.getOperation()) {
                case ADD:
                    sale.setValue(sale.getValue() + adjustment.getAdjustment());
                    break;
                case SUBTRACT:
                    Double finalValue = sale.getValue();
                    finalValue = finalValue > 0D ? finalValue - adjustment.getAdjustment() : 0D;
                    sale.setValue(finalValue);
                    break;
                case MULTIPLY:
                    sale.setValue(sale.getValue() * adjustment.getAdjustment());
                    break;
            }
        }
        saleStorage.add(adjustment);
    }
}
