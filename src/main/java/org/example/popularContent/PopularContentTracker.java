package org.example.popularContent;

import java.util.HashMap;
import java.util.Map;

/*
Implement a system to track the popularity of content based on user interactions (thumbs up or thumbs down). Your task is to design an interface called ContentPopularity with the following methods:

1. increasePopularity(contentId: int) -> None: Increases the popularity of the specified content ID by one (representing a thumbs up).

2. decreasePopularity(contentId: int) -> None: Decreases the popularity of the specified content ID by one (representing a thumbs down).

3. mostPopular() -> int: Returns the content ID with the highest popularity. If there are ties, return any one of them. -1 if no content.


doubts
        1. -ve popular possible -> dont include in answer
        2. 0 popularity possible -> no
        3. mostPopular if all -ve or 0 , more than 1 content with max count
 */
public class PopularContentTracker implements IPopularContentTracker{
    Map<String, LinkedNode> contentToNodeMap;
    Map<String, Integer> contentToCountMap;
    Map<Integer, LinkedNode> countToLinkedNodeMap;
    Map<Integer, Integer> countToFreq;
    int maxCount;


    public PopularContentTracker() {
        contentToCountMap = new HashMap<>();
        contentToNodeMap = new HashMap<>();
        countToLinkedNodeMap = new HashMap<>();
        countToFreq = new HashMap<>();
        maxCount = Integer.MIN_VALUE;
    }

    @Override
    public void increasePopularity(String contentId) {
        if(contentId == null || contentId =="") {
            throw new IllegalArgumentException("contentId is not valid");
        }
        int currCount = contentToCountMap.getOrDefault(contentId, 0);
        int newCount = currCount +1;
        LinkedNode currNode;
        if(currCount != 0) {
            currNode = contentToNodeMap.get(contentId);
            // remove from old count
            removeNodeFromList(currNode);
        } else {
            currNode = new LinkedNode(contentId);
            contentToNodeMap.put(contentId, currNode);
        }

        if(newCount != 0) {
            addNodeToList(newCount, currNode);
        }

        contentToCountMap.put(contentId, newCount);
        updateCountFreq(currCount, newCount);

        if(newCount > maxCount) {
            maxCount = newCount;
        }

    }



    @Override
    public void decreasePopularity(String contentId) {
        int currCount = contentToCountMap.getOrDefault(contentId, 0);
        int newCount = currCount - 1;
        LinkedNode currNode;
        if(currCount!=0) {
            currNode = contentToNodeMap.get(contentId);
            removeNodeFromList(currNode);
        } else {
            currNode = new LinkedNode(contentId);
            contentToNodeMap.put(contentId, currNode);
        }

        if(newCount!=0) {
            addNodeToList(newCount, currNode);
        }
        if(maxCount == currCount) {
            int maxCountFreq = countToFreq.getOrDefault(currCount, 0);
            if(maxCountFreq==1) {
                countToFreq.remove(maxCount);
                maxCount = newCount;
            }
        }

        updateCountFreq(currCount, newCount);

        contentToCountMap.put(contentId, newCount);

    }

    @Override
    public String mostPopular() {
        if(maxCount <=0) {
            return null;
        }
        return countToLinkedNodeMap.get(maxCount).next.data;

    }

    private static void removeNodeFromList(LinkedNode node) {
        LinkedNode prev = node.prev;
        LinkedNode next = node.next;
        prev.next = next;
        next.prev= prev;
    }

    private void addNodeToList(int newCount, LinkedNode node) {
       // int newCount = currCount +1;
        LinkedNode newHead = countToLinkedNodeMap.get(newCount);
        if(newHead == null) {
            newHead = new LinkedNode("");
            countToLinkedNodeMap.get(newCount);
            LinkedNode newTail = new LinkedNode("");
            newHead.next = newTail;
            newTail.prev = newHead;
            countToLinkedNodeMap.put(newCount, newHead);
        }
        LinkedNode newNext = newHead.next;
        newHead.next = node;
        node.next = newNext;
        node.prev = newHead;
        newNext.prev = node;
    }

    private void updateCountFreq(int currCount, int newCount) {
        int currCountFreq = countToFreq.getOrDefault(currCount,0);
        if(currCountFreq > 0) {
            countToFreq.put(currCount, currCountFreq-1);
        }
        if(currCountFreq == 1) {
            countToFreq.remove(currCount);
        }

        if(newCount!=0) {
            countToFreq.putIfAbsent(newCount, 0);
            countToFreq.put(newCount, countToFreq.get(newCount) + 1);
        }
    }

    private static class LinkedNode {
        String data;

        public LinkedNode(String data) {
            this.data = data;
        }

        LinkedNode prev;
        LinkedNode next;

    }
}
