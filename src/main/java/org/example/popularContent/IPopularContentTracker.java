package org.example.popularContent;

public interface IPopularContentTracker {
    void increasePopularity(String contentId);
    void decreasePopularity(String contentId);

    String mostPopular();

}
