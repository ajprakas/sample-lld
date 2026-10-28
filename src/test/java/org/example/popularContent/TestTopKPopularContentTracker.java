package org.example.popularContent;

import org.example.popularContent.TopKPopularContentTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestTopKPopularContentTracker {
    TopKPopularContentTracker topKPopularContentTracker = new TopKPopularContentTracker();

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidContentId() {
        topKPopularContentTracker.increasePopularity("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullContentId() {
        topKPopularContentTracker.increasePopularity(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidK() {
        topKPopularContentTracker.topKPopularContent(-1);
    }

    @Test
    public void testIncreasePopularity_withNoEntry() {
        Assert.assertEquals(topKPopularContentTracker.topKPopularContent(10).size(), 0);
    }

    @Test
    public void testIncreasePopularity_onlyIncrease() {
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("chiku");
        topKPopularContentTracker.increasePopularity("miku");
        List<String> res = topKPopularContentTracker.topKPopularContent(2);
        Assert.assertEquals(res.size(), 2);
        Assert.assertEquals(res.get(0), "ajay");
        Assert.assertEquals(res.get(1), "deepali");
    }


    @Test
    public void testIncreasePopularity_onlyDecrease() {
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("deepali");
        topKPopularContentTracker.decreasePopularity("deepali");
        topKPopularContentTracker.decreasePopularity("chiku");
        topKPopularContentTracker.decreasePopularity("miku");
        List<String> res = topKPopularContentTracker.topKPopularContent(2);
        Assert.assertEquals(res.size(), 0);
    }

    @Test
    public void testTopKPopularContent_both_increase_decrease() {
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("chiku");
        topKPopularContentTracker.increasePopularity("miku");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("deepali");
        topKPopularContentTracker.decreasePopularity("chiku");
        topKPopularContentTracker.decreasePopularity("miku");
        List<String> res = topKPopularContentTracker.topKPopularContent(2);
        Assert.assertEquals(res.size(), 2);
        Assert.assertEquals(res.get(0), "ajay");
        Assert.assertEquals(res.get(1), "deepali");
    }

    @Test
    public void testTopKPopularContent_both_increase_decrease_zerocount() {
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("chiku");
        topKPopularContentTracker.increasePopularity("miku");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("deepali");
        topKPopularContentTracker.decreasePopularity("chiku");
        topKPopularContentTracker.decreasePopularity("miku");
        List<String> res = topKPopularContentTracker.topKPopularContent(2);
        Assert.assertEquals(res.size(), 1);
        Assert.assertEquals(res.get(0), "deepali");
    }

    @Test
    public void testTopKPopularContent_both_negative_zerocount() {
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("ajay");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("deepali");
        topKPopularContentTracker.increasePopularity("chiku");
        topKPopularContentTracker.increasePopularity("miku");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("ajay");
        topKPopularContentTracker.decreasePopularity("deepali");
        topKPopularContentTracker.decreasePopularity("chiku");
        topKPopularContentTracker.decreasePopularity("chiku");
        topKPopularContentTracker.decreasePopularity("miku");
        topKPopularContentTracker.decreasePopularity("miku");
        List<String> res = topKPopularContentTracker.topKPopularContent(2);
        Assert.assertEquals(res.size(), 2);
        Assert.assertTrue(res.get(0).equals("deepali") || res.get(0).equals("ajay"));
        Assert.assertTrue(res.get(1).equals("ajay") || res.get(1).equals("deepali"));
       // Assert.assertEquals(res.get(0), "ajay");
    }
}
