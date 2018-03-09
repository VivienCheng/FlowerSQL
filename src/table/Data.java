package table;

import java.lang.*;
import java.util.*;


public class Data {
    private ArrayList<ArrayList<String>> content;

    //add
    public void addSingleData(ArrayList<String> s){
        content.add(s);
    }
    //Constructor

    public Data() {
        //this.nameOfTable = "";
        this.content = new ArrayList<>();
    }

    //getter and setter
    public ArrayList<ArrayList<String>> getContent() {
        return content;
    }

    public void setContent(ArrayList<ArrayList<String>> content) {
        this.content = content;
    }

    //针对授权
    /*public void setSingleContent(ArrayList<String> authority){
       ArrayList<String> singleContent = content.get(3);
    }*/
}
