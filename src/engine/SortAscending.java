package engine;

import java.util.*;

class SortAscending implements Comparator{
    public int compare(Object a,Object b){
        String s1 = (String) a;
        String s2 = (String) b;
        int result = s1.compareTo(s2);
        if( result > 0){
            return -1;
        }else if(result < 0){
            return 1;
        }else{
            return 0;
        }
    }
}