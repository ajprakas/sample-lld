package org.example.commodityPrice;

public interface ICommodityPriceUpdater {
    void upsetCommodityPrice(int timestamp, int price);
    int getMaxCommodityPrice();
    void deleteTimestamp(int timestamp);
}
