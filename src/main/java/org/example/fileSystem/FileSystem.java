package org.example.fileSystem;


import java.util.*;

public class FileSystem {
    TrieNode root;

    public FileSystem() {
        root = new TrieNode("/");
    }

    public void mkdir(String dir) {
        String[] dirs = dir.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                curr.insert(d,true);
            }
            curr = curr.children.get(d);
        }
    }

    public boolean touch(String file, String dir) {
        String[] dirs = dir.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                System.out.println("dir not found");
                return false;
            }
            curr = curr.children.get(d);
        }
        curr.insert(file);
        return true;

    }

    public List<String> ls(String path) {
        String[] dirs = path.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                return new ArrayList<>();
            }
            curr = curr.children.get(d);
        }
        if(curr.isDir) {
            return new ArrayList<>(curr.children.keySet());
        } else {
            List<String> res = new ArrayList<>();
            res.add(curr.name);
            return res;
        }
    }

    public List<String> find(String file, String dir) {
        String[] dirs = dir.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                System.out.println("dir not found");
                return new ArrayList<>();
            }
            curr = curr.children.get(d);
        }

        // BFS
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(curr);

        List<String> result = new ArrayList<>();
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i=0;i<size;i++) {
                TrieNode node = queue.poll();
                assert node != null;
                result.addAll(node.search(file));
                for(Map.Entry<String, TrieNode> entry : node.children.entrySet()) {
                   // String key = entry.getKey();
                    TrieNode child = entry.getValue();
                    queue.offer(child);
                }
            }
        }

        return result;

    }

    public String readContentFromFile(String filePath) {
        String[] dirs = filePath.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                System.out.println("filePath oes ot exists "+filePath);
                return "";
            }
            curr = curr.children.get(d);
        }
        return curr.content;
    }

    public void addContentToFile(String filePath, String content) {
        String[] dirs = filePath.split("/");
        TrieNode curr = root;
        for(String d: dirs) {
            if(!curr.isPresent(d)) {
                curr.insert(d);
            }
            curr = curr.children.get(d);
        }
        curr.addContent(content);

    }

    private static class TrieNode {
        String name;
        Map<String, TrieNode> children = new HashMap<>();
        boolean isDir;
        String content= "";

        public TrieNode(String name) {
            this.name = name;
            this.isDir = false;
        }

        public TrieNode(String name, boolean isDir) {
            this.name = name;
            this.isDir = isDir;
        }

        public List<String> search(String child) {
            List<String> result = new ArrayList<>();
            for(Map.Entry<String, TrieNode> entry: children.entrySet()) {
                 if(entry.getKey().startsWith(child)) {
                     String dir = entry.getValue().isDir ? "D:":"F:";
                     result.add(dir+entry.getKey());
                 }
            }
            return result;
        }

        boolean isPresent(String child) {
            return children.containsKey(child);
        }

        void insert(String child) {
            children.put(child, new TrieNode(child));
        }

        void insert(String child, boolean isDir) {
            children.put(child, new TrieNode(child, isDir));
        }

        void addContent(String data) {
            if(isDir) {
                System.out.println("can not add content to dir");
            }
            content = content + data;
        }

    }
}
