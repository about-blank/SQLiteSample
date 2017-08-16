package com.vishal.sqlitesample.model;

/**
 * Created by Vishal Aroor on 16-Aug-17.
 */

public class Employee {

    Long _id;
    String ename;
    Long age;

    public Employee() {}
    public Employee(Long _id, String ename, Long age) {
        this._id = _id;
        this.ename = ename;
        this.age = age;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "_id=" + _id +
                ", ename='" + ename + '\'' +
                ", age=" + age +
                '}';
    }
}
