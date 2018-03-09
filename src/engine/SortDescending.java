package engine;

import java.util.*;




class SortDescending implements Comparator{
    public int compare(Object a,Object b){
        String s1 = (String) a;
        String s2 = (String) b;
        return s1.compareTo(s2);
    }
}