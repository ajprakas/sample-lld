package org.example.popularContent;

import org.example.popularContent.PopularContentTracker;
import org.junit.Assert;
import org.junit.Test;

public class PopularContentTrackerTest {

    @Test
    public void testIncreasePopularity_whenNoIncreaseOrDecrease() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        Assert.assertNull(popularContentTracker.mostPopular());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncreasePopularity_whenIllegalContentId() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
       popularContentTracker.increasePopularity("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncreasePopularity_whenNullContentId() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        popularContentTracker.increasePopularity(null);
    }

    @Test
    public void testIncreasePopularity_whenOnlyIncrease() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("chiku");
        popularContentTracker.increasePopularity("miku");
        Assert.assertEquals(popularContentTracker.mostPopular(), "miku");
    }

    @Test
    public void testIncreasePopularity_whenOnlyDecreaseForAll() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("deepali");
        popularContentTracker.decreasePopularity("chiku");
        popularContentTracker.decreasePopularity("miku");
        Assert.assertNull(popularContentTracker.mostPopular());
    }

    @Test
    public void testIncreasePopularity_whenOnlyDecrease() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("deepali");
        popularContentTracker.decreasePopularity("chiku");
        Assert.assertNull(popularContentTracker.mostPopular());
    }

    @Test
    public void testIncreasePopularity_whenBothIncreaseAndDecrease() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        // ajay- 3, deepali - 2, chiku - 1 , miku - 1
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("chiku");
        popularContentTracker.increasePopularity("miku");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("chiku");
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("deepali");
        popularContentTracker.decreasePopularity("chiku");
        Assert.assertEquals(popularContentTracker.mostPopular(),"ajay");
    }

    @Test
    public void testIncreasePopularity_whenBothIncreaseAndDecreaseAndTie() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        // ajay- 3, deepali - 2, chiku - 1 , miku - 1
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("chiku");
        popularContentTracker.increasePopularity("miku");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("deepali");
        popularContentTracker.increasePopularity("chiku");
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("deepali");
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("chiku");
        Assert.assertEquals(popularContentTracker.mostPopular(),"ajay");

        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.increasePopularity("chiku");
        Assert.assertEquals(popularContentTracker.mostPopular(),"chiku");
    }


    @Test
    public void testIncreasePopularity_whenBothIncreaseAndDecreaseAndOnlyZero() {
        PopularContentTracker popularContentTracker = new PopularContentTracker();
        // ajay- 3, deepali - 2, chiku - 1 , miku - 1
        popularContentTracker.increasePopularity("ajay");
        popularContentTracker.decreasePopularity("ajay");
        popularContentTracker.decreasePopularity("chiku");
        Assert.assertNull(popularContentTracker.mostPopular());
    }

}
