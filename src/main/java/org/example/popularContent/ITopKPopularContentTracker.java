package org.example.popularContent;

import java.util.List;

public interface ITopKPopularContentTracker {
    void increasePopularity(String contentId);
    void decreasePopularity(String contentId);
    List<String> topKPopularContent(int k);
}
