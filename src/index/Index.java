package index;

import java.util.*;
import table.*;

/*
索引由索引名、字段名、数据这3部分组成
*/
public class Index {
    private StructOfIndex structOfIndex;
    private Data dataOfIndex;


    //Constructor
    public Index() {
        this.structOfIndex = new StructOfIndex();
        this.dataOfIndex = new Data();
    }

    /*
    索引的字段 还需要有排序、去重等操作
    ASC:升序 DESC:降序 UNIQUE:去重
    */
    //Getter and Setter
    public Data getDataOfIndex() {
        return dataOfIndex;
    }

    public StructOfIndex getStructOfIndex() {
        return structOfIndex;
    }

    public void setStructOfIndex(StructOfIndex structOfIndex) {
        this.structOfIndex = structOfIndex;
    }

    public String getNameOfIndex(){
        String nameOfIndex = this.structOfIndex.getNameOfIndex();
        return nameOfIndex;

    }

    public void setNameOfIndex(String name){
        this.structOfIndex.setNameOfIndex(name);
    }

    public void setDataOfIndex(Data dataOfTableOfIndex) {
        this.dataOfIndex = dataOfTableOfIndex;
        /*ArrayList<ArrayList<String>> content = this.dataOfIndex.getContent();
        ArrayList<String> nameOfFields = new ArrayList<>();
        for(ArrayList<String> s : content) {
            nameOfFields.add(s.get(0));
        }
        structOfIndex.setNameOfFields(nameOfFields);*/
    }

    public void nameOfTable(String nameOfTable){
        this.structOfIndex.setNameOfTable(nameOfTable);
    }

    public String getNameOfTable(){
        return this.structOfIndex.getNameOfTable();
    }


}
