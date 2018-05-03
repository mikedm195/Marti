package com.mike.itesm.Objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 03/05/18.
 */

public class Categories {
    Map<Integer,String> categoryList = new HashMap<Integer, String>();

    private static final Categories holder = new Categories();

    public static Categories getInstance() {
        return holder;
    }
}
