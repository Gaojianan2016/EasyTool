package com.gjn.easytool;


import com.gjn.easytool.easysqlite.SqlData;

/**
 * @author gjn
 * @time 2019/6/19 14:04
 */

public class UserData{
    private int _ID;
    private String name;
    private String sex;
    private int age;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "_ID=" + _ID +
                " name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
