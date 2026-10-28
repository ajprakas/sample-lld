package org.example.commonParentGroup;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Emp {
    String name;
    Integer id;
    Set<Group> parents;

    public Emp(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.parents = ConcurrentHashMap.newKeySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emp emp = (Emp) o;
        return Objects.equals(id, emp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
