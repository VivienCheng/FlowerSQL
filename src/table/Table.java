package table;


/*
表由表结构、数据组成
*/
import java.util.*;
public class Table {
    private StructOfTable structOfTable;
    private Data dataOfTable;


    //Constructor
    public Table() {
        this.structOfTable = null;
        this.dataOfTable = null;
    }

    public Table(Table table){
        this.structOfTable = table.getStructOfTable();
        this.dataOfTable = table.getDataOfTable();
    }

    public Table(StructOfTable structOfTable, String nameOfTable, Data dataOfTable) {
        this.structOfTable = structOfTable;
        this.dataOfTable = dataOfTable;
        setNameOfTable(nameOfTable);
    }

    //Getter and setter
    public void setNameOfTable(String name){
        structOfTable.setNameOfTable(name);
    }
    public String getNameOfTable(){
        return structOfTable.getNameOfTable();
    }
    public StructOfTable getStructOfTable() {
        return structOfTable;
    }

    public void setStructOfTable(StructOfTable structOfTable) {
        this.structOfTable = structOfTable;
    }

    public Data getDataOfTable() {
        return dataOfTable;
    }

    public void setDataOfTable(Data dataOfTable) {
        this.dataOfTable = dataOfTable;
    }

    public int getRowNumber(){
        return this.dataOfTable.getContent().size();
    }



    public String[] getAllFieldName(){
        ArrayList<String> result = new ArrayList<>();
        for(Field field:structOfTable.getFields()){
            result.add(field.getNameOfField());
        }
        String []result_array = new String[result.size()];
        for(int i =0; i<result.size();++i){
            result_array[i]=result.get(i);
        }
        return result_array;
    }

    public  ArrayList<String> getRow(int rowNumber){
        return this.dataOfTable.getContent().get(rowNumber);
    }

    public int getColumnNum(String fieldName){
        for(int i = 0; i < this.structOfTable.getFields().size(); ++i){
            if(fieldName.equals(this.structOfTable.getFields().get(i).getNameOfField())){
                return i;
            }
        }
        return -1;
    }

    public boolean check_if_field_type_is_int(String fieldName){
        for(int i = 0; i < this.structOfTable.getFields().size(); ++i){
            if(fieldName.trim().equals(this.structOfTable.getFields().get(i).getNameOfField())){
                if(this.structOfTable.getFields().get(i).getType().equals("int")){
                    return true;
                }
                else
                    return false;
            }
        }
        return false;
    }
}
