package org.example.commonParentGroup;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class CommonParentGroupTest {
    CommonParentGroup commonParentGroup = new CommonParentGroup();

    @Test
    public void testNotPresentEmp() {
        Group root = new Group("company", 1);
        Group engg= new Group("engg", 2);
        Group ops = new Group("ops", 3);
        Group backedn =  new Group("backend", 4);
        Group frontend = new Group("frontend", 5);
        Group finOps = new Group("finOps", 6);
        Group admin= new Group("admin", 7);;
        commonParentGroup.createGroup(root);
        commonParentGroup.createGroup(engg);
        commonParentGroup.createGroup(ops);
        commonParentGroup.createGroup(finOps);
        commonParentGroup.createGroup(backedn);
        commonParentGroup.createGroup(frontend);
        commonParentGroup.createGroup(admin);
        Emp ajay = new Emp("ajay",8);
        Emp deepali =  new Emp("deepali" ,9);
        Emp chiku =  new Emp("chiku", 10);
        Emp miku =  new Emp("miku", 11);
        commonParentGroup.createEmp(ajay);
        commonParentGroup.createEmp(deepali);
        commonParentGroup.createEmp(chiku);
        commonParentGroup.createEmp(miku);

        commonParentGroup.addRelation(engg, root);
        commonParentGroup.addRelation(ops, root);
        commonParentGroup.addRelation(admin, root);
        commonParentGroup.addRelation(backedn, engg);
        commonParentGroup.addRelation(frontend, engg);
        commonParentGroup.addRelation(admin, ops);
        //commonParentGroup.addRelation(ops, root);
        commonParentGroup.addRelation(finOps, ops);
        commonParentGroup.addRelation(ajay, admin);
        commonParentGroup.addRelation(ajay, engg);
        commonParentGroup.addRelation(ajay, backedn);
        commonParentGroup.addRelation(deepali, frontend);
        commonParentGroup.addRelation(chiku, ops);
        commonParentGroup.addRelation(miku, finOps);

        Set<Integer> emps = new HashSet<>();
        emps.add(8);
        emps.add(10);

        System.out.println(commonParentGroup.findClosestGroup(emps));



    }
}
