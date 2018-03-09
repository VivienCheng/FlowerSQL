package table;


import java.lang.*;
import java.util.*;

/*
表结构由表名、字段、字段数量、字段名、主键名、外键名组成
*/
public class StructOfTable {
    private String nameOfTable;
    private ArrayList<Field> fields;
    private String nameOfPrimaryKey;
    private ArrayList<String> nameOfForeignKey;

    private Integer numOfFields;
    private ArrayList<String> nameOfFields;


    //Constructor
    public StructOfTable(){
        this.nameOfTable = "";
        this.fields = new ArrayList<>();
        this.numOfFields = 0;
        this.nameOfFields = new ArrayList<>();
        this.nameOfPrimaryKey = "";
        this.nameOfForeignKey = new ArrayList<>();

    }
    public StructOfTable(String name,ArrayList<Field> fields, String nameOfPrimaryKey, ArrayList<String> nameOfForeignKey){
        this.nameOfTable = name;
        this.fields = fields;
        this.numOfFields = fields.size();
        this.nameOfFields = new ArrayList<>();
        for(Field field : fields){
            this.nameOfFields.add(field.getNameOfField());
        }
        this.nameOfPrimaryKey = nameOfPrimaryKey;
        this.nameOfForeignKey = nameOfForeignKey;
    }


    //Add
    public void addSingleField(String fieldName,String fieldType,ArrayList<String> constraints){
        fields.add(new Field(fieldName, fieldType, constraints));
        nameOfFields.add(fieldName);
        numOfFields++;
    }
    public void addSingleField(Field field){
        fields.add(field);
        nameOfFields.add(field.getNameOfField());
        numOfFields++;
    }

    //Getter and setter
    public Field getSingleField(String name){
        for(Field field : fields){
            if(field.getNameOfField().equals(name)){
                return field;
            }
        }
        return null;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public Integer getNumOfFields() {
        return numOfFields;
    }

    public void setNumOfFields(Integer numOfFields) {
        this.numOfFields = numOfFields;
    }

    public ArrayList<String> getNameOfFields() {
        return nameOfFields;
    }

    public void setNameOfFields(ArrayList<String> nameOfFields) {
        this.nameOfFields = nameOfFields;
    }

    public String getNameOfPrimaryKey() {
        return nameOfPrimaryKey;
    }

    public void setNameOfPrimaryKey(String nameOfPrimaryKey) {
        this.nameOfPrimaryKey = nameOfPrimaryKey;
    }

    public ArrayList<String> getNameOfForeignKey() {
        return nameOfForeignKey;
    }

    public void setNameOfForeignKey(ArrayList<String> nameOfForeignKey) {
        this.nameOfForeignKey = nameOfForeignKey;
    }

    public String getNameOfTable() {
        return nameOfTable;
    }

    public void setNameOfTable(String nameOfTable) {
        this.nameOfTable = nameOfTable;
    }
}
