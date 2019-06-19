package com.gjn.easytool;


import com.gjn.easytool.easysqlite.SqlData;

/**
 * @author gjn
 * @time 2019/6/19 14:04
 */

public class UserData2{
    private int _ID;
    private String name;
    private String sex;
    private int age;
    private boolean isRich;

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

    public boolean isRich() {
        return isRich;
    }

    public void setRich(boolean rich) {
        isRich = rich;
    }

    @Override
    public String toString() {
        return "UserData2{" +
                "_ID=" + _ID +
                " name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", isRich=" + isRich +
                '}';
    }
}
