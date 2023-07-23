package com.nbb;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiClient {

    public static void main(String[] args) {
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = s.iterator();
        while (iterator.hasNext()) {
            Search search =  iterator.next();
            String s1 = search.searchDoc();
            System.out.println(s1);
        }
    }
}
