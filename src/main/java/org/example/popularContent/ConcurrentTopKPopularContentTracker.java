package org.example.popularContent;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentTopKPopularContentTracker implements ITopKPopularContentTracker{
    BucketedNode head;
    BucketedNode tail;
    Map<String, Integer> contentToCountMap;
    Map<Integer, BucketedNode> countToNodeMap;
    ReadWriteLock readWriteLock;

    public ConcurrentTopKPopularContentTracker() {
        head = new BucketedNode(0);
        readWriteLock = new ReentrantReadWriteLock();
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

        try {
            readWriteLock.writeLock().lock();
            int currCount = contentToCountMap.getOrDefault(contentId, 0);
            int newCount = currCount + 1;
            if (currCount >= 0) {
                BucketedNode currNode = countToNodeMap.get(currCount);
                currNode.contentIds.remove(contentId);
            }
            if (newCount >= 0) {
                countToNodeMap.putIfAbsent(newCount, new BucketedNode(newCount));
                BucketedNode prev = countToNodeMap.get(currCount);
                BucketedNode newNode = countToNodeMap.get(newCount);
                if (newNode.prev == null) {
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
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void decreasePopularity(String contentId) {
        if(contentId == null || contentId.isEmpty()) {
            throw new IllegalArgumentException("NO valid content id");
        }

        try {
            readWriteLock.writeLock().lock();
            int currCount = contentToCountMap.getOrDefault(contentId, 0);
            int newCount = currCount - 1;
            if (currCount >= 0) {
                BucketedNode currNode = countToNodeMap.get(currCount);
                currNode.contentIds.remove(contentId);
            }
            if (newCount >= 0) {
                BucketedNode newNode = countToNodeMap.get(newCount);
                newNode.contentIds.add(contentId);
            }
            contentToCountMap.put(contentId, newCount);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<String> topKPopularContent(int k) {
        if(k<=0) {
            throw new IllegalArgumentException("not valid k");
        }
        try {
            readWriteLock.readLock().lock();
            BucketedNode curr = tail.prev;
            List<String> result = new ArrayList<>();
            while (curr != head && result.size() < k) {
                for (String contentId : curr.contentIds) {
                    result.add(contentId);
                    if (result.size() == k) {
                        return result;
                    }
                }
                curr = curr.prev;
            }
            return result;
        } finally {
            readWriteLock.readLock().unlock();
        }


    }

    private static class BucketedNode {
        Set<String> contentIds;
        ConcurrentTopKPopularContentTracker.BucketedNode prev;
        ConcurrentTopKPopularContentTracker.BucketedNode next;
        int count;

        public BucketedNode(int count) {
            contentIds = new HashSet<>();
            this.count = count;
        }
    }
}
