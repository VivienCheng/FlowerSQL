package table;

import java.lang.*;
import java.util.*;
/*
字段由字段的名字、类型和约束组成
*/
public class Field {

    private String nameOfField;
    private String type;
    private ArrayList<String> constraints;

    //Constructor
    public Field(){
        this.nameOfField = "";
        this.type = "";
        this.constraints = new ArrayList<>();
    }

    public Field(String nameOfField, String type, ArrayList<String> constraints) {
        this.nameOfField = nameOfField;
        this.type = type;
        this.constraints = constraints;
    }

    //Add
    public void addConstraints(String x){
        constraints.add(x);
    }

    //getter and setter

    public String getSingleConstraint(String name){
        for(String s : constraints){
            if(s.equals(name)){
                return name;
            }
        }
        return null;
    }
    public String getNameOfField() {
        return nameOfField;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<String> constraints) {
        this.constraints = constraints;
    }


    /*
    关于类型：
    有int、char、double这3种
    */

    /*
    关于约束：
    有notnull、unique、primarykey、foreignkey、check这5种
    */


}
