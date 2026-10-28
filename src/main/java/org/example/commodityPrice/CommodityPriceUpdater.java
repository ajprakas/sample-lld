package org.example.commodityPrice;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CommodityPriceUpdater implements ICommodityPriceUpdater {
    Map<Integer, Integer> timeStampToPriceMap;
    TreeMap<Integer, Integer> priceToFreqMap;
    int maxPrice;

    public CommodityPriceUpdater() {
        timeStampToPriceMap = new HashMap<>();
        priceToFreqMap = new TreeMap<>();
        maxPrice = Integer.MIN_VALUE;
    }

    @Override
    public void upsetCommodityPrice(int timestamp, int price) {
        if(timestamp<=0 || price <=0) {
            throw new IllegalArgumentException("invalid input");
        }
        if(timeStampToPriceMap.containsKey(timestamp)) {
            int oldPrice = timeStampToPriceMap.get(timestamp);
            int oldFreq = priceToFreqMap.get(oldPrice);
            priceToFreqMap.put(oldPrice, oldFreq-1);
            if(priceToFreqMap.get(oldPrice)==0) {
                priceToFreqMap.remove(oldPrice);
            }
        }

        timeStampToPriceMap.put(timestamp, price);
        priceToFreqMap.putIfAbsent(price, 0);
        priceToFreqMap.put(price, priceToFreqMap.get(price)+1);

        // update max
        if(price > maxPrice) {
            maxPrice = price;
        } else {
            maxPrice = priceToFreqMap.lastKey();
        }

    }

    @Override
    public int getMaxCommodityPrice() {
        return maxPrice;
    }

    @Override
    public void deleteTimestamp(int timestamp) {
        if(timestamp<=0) {
            throw new IllegalArgumentException("invalid input");
        }
        if(timeStampToPriceMap.containsKey(timestamp)) {
            int oldPrice = timeStampToPriceMap.get(timestamp);
            int oldFreq = priceToFreqMap.get(oldPrice);
            timeStampToPriceMap.remove(timestamp);
            priceToFreqMap.put(oldPrice, oldFreq-1);
            if(priceToFreqMap.get(oldPrice) == 0) {
                priceToFreqMap.remove(oldPrice);
            }
            if(oldPrice == maxPrice) {
                maxPrice = priceToFreqMap.lastKey();
            }
        }

    }
}
