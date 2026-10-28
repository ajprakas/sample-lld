package org.example.commonParentGroup;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Group {
    String name;
    Integer id;
    Set<Group> subGroups;
    Set<Emp> emps;
    Set<Group> parent;

    public Group(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.subGroups = new HashSet<>();
        this.emps = ConcurrentHashMap.newKeySet();
        this.parent = ConcurrentHashMap.newKeySet();
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
