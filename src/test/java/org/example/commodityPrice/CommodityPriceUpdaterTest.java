package org.example.commodityPrice;

import org.junit.Assert;
import org.junit.Test;

public class CommodityPriceUpdaterTest {
    CommodityPriceUpdater commodityPriceUpdater = new CommodityPriceUpdater();

    @Test(expected = IllegalArgumentException.class)
    public void testUpsetCommodityPriceInvalidInput() {
//        commodityPriceUpdater.upsetCommodityPrice(-1, 1);
        commodityPriceUpdater.upsetCommodityPrice(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTimestampInvalidInput() {
        commodityPriceUpdater.deleteTimestamp(0);
//        commodityPriceUpdater.deleteTimestamp(-1);
    }

    @Test
    public void testUpsetCommodityPrice() {
        commodityPriceUpdater.upsetCommodityPrice(1,2);
        commodityPriceUpdater.upsetCommodityPrice(3,5);
        commodityPriceUpdater.upsetCommodityPrice(5,10);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 10);
        commodityPriceUpdater.upsetCommodityPrice(7,8);
        commodityPriceUpdater.upsetCommodityPrice(6,10);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 10);
        commodityPriceUpdater.upsetCommodityPrice(5,5);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 10);
        commodityPriceUpdater.upsetCommodityPrice(4,14);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 14);
        commodityPriceUpdater.upsetCommodityPrice(6,9);
        commodityPriceUpdater.upsetCommodityPrice(4,8);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 9);
    }

    @Test
    public void testDeleteTs() {
        commodityPriceUpdater.upsetCommodityPrice(1,2);
        commodityPriceUpdater.upsetCommodityPrice(3,5);
        commodityPriceUpdater.upsetCommodityPrice(5,10);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 10);
        commodityPriceUpdater.deleteTimestamp(5);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 5);
        commodityPriceUpdater.upsetCommodityPrice(7,10);
        commodityPriceUpdater.upsetCommodityPrice(6,10);
        commodityPriceUpdater.deleteTimestamp(6);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 10);
        commodityPriceUpdater.upsetCommodityPrice(7,4);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 5);
        commodityPriceUpdater.upsetCommodityPrice(4,14);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 14);
        commodityPriceUpdater.deleteTimestamp(7);
        Assert.assertEquals(commodityPriceUpdater.getMaxCommodityPrice(), 14);
    }


}
