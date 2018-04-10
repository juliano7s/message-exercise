package com.message.vo;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaleTest {

    @Test
    public void testEquals() {
        Sale sale1 = new Sale("apple", 5D);
        Sale sale2 = new Sale("apple", 5D);
        Assert.assertEquals(sale1, sale2);
        Assert.assertNotEquals(sale1, null);
        Assert.assertNotEquals(sale1, new Object());
    }

    @Test
    public void testClone() {
        Sale sale1 = new Sale("apple", 5D);
        Sale cloned = sale1.clone();
        Assert.assertEquals(sale1, cloned);
        Assert.assertFalse(sale1 == cloned);
    }
}