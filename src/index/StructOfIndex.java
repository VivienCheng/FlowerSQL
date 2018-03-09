package index;

import java.util.ArrayList;

public class StructOfIndex {
    private String nameOfIndex;
    private ArrayList<String> nameOfFields;
    private int numOfFields;
    private int type;
    //0：无要求 1：升序 2：降序
    private String nameOfTable;
    //Constructor


    public StructOfIndex(String nameOfTable,int type,String nameOfIndex, ArrayList<String> nameOfFields) {
        this.nameOfTable = nameOfTable;
        this.type = type;
        this.nameOfIndex = nameOfIndex;
        this.nameOfFields = nameOfFields;
    }



    public StructOfIndex() {
        this.nameOfTable = "";
        this.type = 0;
        this.nameOfIndex = "";
        this.nameOfFields = new ArrayList<>();
        this.numOfFields = 0;
    }

    //Getter and Setter


    public void setType(int type) {
        this.type = type;
    }
    public String getNameOfIndex() {
        return nameOfIndex;
    }

    public int getNumOfFields() {
        return numOfFields;
    }

    public ArrayList<String> getNameOfFields() {
        this.numOfFields = nameOfFields.size();
        return nameOfFields;
    }

    public void setNameOfIndex(String nameOfIndex) {
        this.nameOfIndex = nameOfIndex;
    }

    public void setNameOfFields(ArrayList<String> nameOfFields) {
        this.nameOfFields = nameOfFields;
    }

    public void setNumOfFields() {
        this.numOfFields = nameOfFields.size();
    }

    public int getType() {
        return type;
    }

    public void setNameOfTable(String nameOfTable) {
        this.nameOfTable = nameOfTable;
    }

    public String getNameOfTable() {

        return nameOfTable;
    }
}
