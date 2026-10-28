package org.example.commonParentGroup;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
Find the closest Org for target Employees
a) Imagine you are the team that maintains the Atlassian employee directory.
At Atlassian – there are multiple groups, and each can have one or more groups.
Every employee is part of a group. You are tasked with designing a system that could find the closest common parent group giv
 a target set of employees in the organization.
b) The Atlassian hierarchy sometimes can have shared group across an org or employees shared across different groups
 – How will the code evolve n this case if the requirement is to provide ONE closest common group
 */
public class CommonParentGroup {

    private Map<Integer, Emp> idToEmpMap;
    private Map<Integer, Group> idToGrpMap;
    private Map<Group, Map<Emp, Integer>> ancestorGrpDistance;
    ReadWriteLock readWriteLock;

    public CommonParentGroup() {
        idToEmpMap = new ConcurrentHashMap<>();
        idToGrpMap = new ConcurrentHashMap<>();
        ancestorGrpDistance = new HashMap<>();
        readWriteLock = new ReentrantReadWriteLock();
    }

    /*
    E = no of emps
    G = no of grps
    K = no of emps in input
    TC = K*(E+G) -> worst case but average case would be mush less than
    SC = k*G - worst case
     */

    public Group findClosestGroup(Set<Integer> emps) {
       // traverse from employee and calculate distance of each group to emp into Map<Parent, Map<Emp,distance>>
        // traverse  Map<Parent, Map<Emp,distance>> and find out Parent with all emps in its value
        // and Parent with least max distance to empl will be LCA
        readWriteLock.readLock().lock();
        try {
            Group closestGrp = null;
            for (Integer empId : emps) {
                if (!idToEmpMap.containsKey(empId)) {
                    return closestGrp;
                }
                calculateDepthBfs(empId);
            }
            int target = emps.size();
            int minMaxEmpDistance = Integer.MAX_VALUE;

            for (Map.Entry<Group, Map<Emp, Integer>> groupMapEntry : ancestorGrpDistance.entrySet()) {
                Group parent = groupMapEntry.getKey();
                Map<Emp, Integer> empDistances = groupMapEntry.getValue();
                // node with all employees under it
                if (empDistances.size() == target) {
                    int maxDistance = 0;
                    for (Map.Entry<Emp, Integer> empDistanceEntry : empDistances.entrySet()) {
//                    Emp emp = empDistanceEntry.getKey();
                        int dist = empDistanceEntry.getValue();
                        maxDistance = Math.max(dist, maxDistance);
                    }

                    if (maxDistance < minMaxEmpDistance) {
                        minMaxEmpDistance = maxDistance;
                        closestGrp = parent;
                    }
                }
            }
            return closestGrp;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private void calculateDepthBfs(Integer empId) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int [] {empId, 0});
        Emp emp = idToEmpMap.get(empId);
        while(!queue.isEmpty()) {
            int[] entry = queue.poll();
            int id = entry[0];
            int dist = entry[1];
            Set<Group> parents = idToEmpMap.containsKey(id) ? idToEmpMap.get(id).parents : idToGrpMap.get(id).parent;
            int newDist = dist +1;
            for(Group parent: parents) {
                queue.add(new int[] {parent.id, newDist});
                ancestorGrpDistance.putIfAbsent(parent, new HashMap<>());
                int oldDist = ancestorGrpDistance.get(parent).getOrDefault(emp, 0);
                if(newDist> oldDist) {
                    ancestorGrpDistance.get(parent).put(emp, newDist);
                }
            }
        }
    }

    public void createEmp(Emp emp) {
        idToEmpMap.put(emp.id, emp);
    }

    public void createGroup(Group grp) {
        idToGrpMap.put(grp.id, grp);
    }

    public void addRelation(Emp emp, Group group) {
        readWriteLock.writeLock().lock();
        try {
            emp.parents.add(group);
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void addRelation(Group child, Group parent) {
        readWriteLock.writeLock().lock();
        try {
            child.parent.add(parent);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
