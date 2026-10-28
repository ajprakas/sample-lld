package org.example.popularContent;

import java.util.*;
import java.util.stream.Collectors;

public class TopKPopularContentTracker implements ITopKPopularContentTracker{

    BucketedNode head;
    BucketedNode tail;
    Map<String, Integer> contentToCountMap;
    Map<Integer, BucketedNode> countToNodeMap;

    public TopKPopularContentTracker() {
        head = new BucketedNode(0);
        tail = new BucketedNode(Integer.MAX_VALUE);
        contentToCountMap = new HashMap<>();
        countToNodeMap = new HashMap<>();
        head.next = tail;
        tail.prev = head;
        countToNodeMap.put(0, head);
    }

    @Override
    public void increasePopularity(String contentId) {
        if(contentId == null || contentId.isEmpty()) {
            throw new IllegalArgumentException("NO valid content id");
        }
        int currCount = contentToCountMap.getOrDefault(contentId, 0);
        int newCount = currCount +1;
        if(currCount >= 0) {
            BucketedNode currNode = countToNodeMap.get(currCount);
            currNode.contentIds.remove(contentId);
        }
        if(newCount>=0) {
            countToNodeMap.putIfAbsent(newCount, new BucketedNode(newCount));
            BucketedNode prev = countToNodeMap.get(currCount);
            BucketedNode newNode = countToNodeMap.get(newCount);
            if(newNode.prev == null) {
                BucketedNode next = prev.next;
                prev.next = newNode;
                newNode.next = next;
                next.prev = newNode;
                newNode.prev = prev;

            }
            newNode.contentIds.add(contentId);
        }
        contentToCountMap.put(contentId, newCount);
    }

    @Override
    public void decreasePopularity(String contentId) {
        if(contentId == null || contentId.isEmpty()) {
            throw new IllegalArgumentException("NO valid content id");
        }
        int currCount = contentToCountMap.getOrDefault(contentId, 0);
        int newCount = currCount - 1;
        if(currCount >= 0) {
            BucketedNode currNode = countToNodeMap.get(currCount);
            currNode.contentIds.remove(contentId);
        }
        if(newCount>=0) {
            BucketedNode newNode = countToNodeMap.get(newCount);
            newNode.contentIds.add(contentId);
        }
        contentToCountMap.put(contentId, newCount);
    }

    @Override
    public List<String> topKPopularContent(int k) {
        if(k<=0) {
            throw new IllegalArgumentException("not valid k");
        }
        BucketedNode curr = tail.prev;
        List<String> result = new ArrayList<>();
        while(curr!=head && result.size() <k) {
            for (String contentId : curr.contentIds) {
                result.add(contentId);
                if(result.size() == k) {
                    return result;
                }
            }
            curr = curr.prev;
        }

        return result;
    }

    private static class BucketedNode {
        Set<String> contentIds;
        BucketedNode prev;
        BucketedNode next;
        int count;

        public BucketedNode(int count) {
            contentIds = new HashSet<>();
            this.count = count;
        }
    }
}
