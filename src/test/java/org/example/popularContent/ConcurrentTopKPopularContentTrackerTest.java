package org.example.popularContent;

import org.example.popularContent.ConcurrentTopKPopularContentTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentTopKPopularContentTrackerTest {
    ConcurrentTopKPopularContentTracker popularContentTracker = new ConcurrentTopKPopularContentTracker();

    @Test
    public void testTopKContent() {
        new Thread(() -> {
            for(int i=0;i<10;i++) {
                popularContentTracker.increasePopularity("ajay");
            }
        }).start();
        // ajay- 7, deepali- 8, chiku - 7, miku- 6

        new Thread(() -> {
            for(int i=0;i<9;i++) {
                popularContentTracker.increasePopularity("deepali");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<8;i++) {
                popularContentTracker.increasePopularity("chiku");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<7;i++) {
                popularContentTracker.increasePopularity("miku");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<3;i++) {
                popularContentTracker.decreasePopularity("ajay");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<1;i++) {
                popularContentTracker.decreasePopularity("deepali");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<1;i++) {
                popularContentTracker.decreasePopularity("chiku");
            }
        }).start();

        new Thread(() -> {
            for(int i=0;i<1;i++) {
                popularContentTracker.decreasePopularity("miku");
            }
        }).start();

        List<String> res = new ArrayList<>();
        res.addAll(popularContentTracker.topKPopularContent(2));

        Assert.assertEquals(res.size(), 2);
        Assert.assertTrue(res.get(0).equals("deepali"));
        System.out.println(res.get(1));
        Assert.assertTrue(res.get(1).equals("chiku"));

    }
}
