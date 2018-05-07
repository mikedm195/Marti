package com.mike.itesm.Objects;

/**
 * Created by mike on 05/05/18.
 */

public class Category {

    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
        this.id = 0;
        this.name = "";
    }

    @Override
    public String toString() {
        return name;
    }
}
