package component;

import java.util.*;
import table.*;
import index.*;
import view.*;

public class Database {
    private ArrayList<Table> tables;
    private ArrayList<View> views;
    private ArrayList<Index> indexes;
    private int numOfTables;
    private int numOfViews;
    private int numOfIndexes;
    private ArrayList<String> nameOfTables;
    private ArrayList<String> nameOfViews;
    private ArrayList<String> nameOfIndexes;



    //Add
    public void addSingleView(View view){
        views.add(view);
        this.numOfViews++;
    }
    public void addSingleIndex(Index index){
        indexes.add(index);
        this.numOfIndexes++;
    }
    public void addSingleIndex(StructOfIndex structOfIndex,Data data){
        Index index = new Index();
        index.setStructOfIndex(structOfIndex);
        index.setDataOfIndex(data);
        indexes.add(index);
        this.numOfIndexes++;
    }
    public void addSingleTable(Table table){
        tables.add(table);
        this.numOfTables++;
    }
    public void addSingleTable(StructOfTable struct, Data data){
        Table table = new Table();
        table.setStructOfTable(struct);
        table.setDataOfTable(data);
        tables.add(table);
        this.numOfTables++;
    }
    //Constructor
    public Database() {
        this.tables = new ArrayList<>();
        this.views = new ArrayList<>();
        this.indexes = new ArrayList<>();
        this.numOfTables = tables.size();
        this.numOfIndexes = indexes.size();
        this.numOfViews = views.size();
        this.nameOfTables = new ArrayList<>();
        this.nameOfViews = new ArrayList<>();
        this.nameOfIndexes = new ArrayList<>();
    }
    //Constructor
    public Database(ArrayList<Table> tables, ArrayList<View> views, ArrayList<Index> indexs) {
        this.tables = tables;
        this.views = views;
        this.indexes = indexs;
        getNameOfTables();
    }

    //Getter and setter

    public Table getSingleTable(String name){

        for(Table table: tables){
            if(table.getNameOfTable().equals(name)){
                return table;
            }
        }
        return null;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    public ArrayList<Index> getIndexs() {
        return indexes;
    }

    public void setIndexs(ArrayList<Index> indexs) {
        this.indexes = indexs;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public int getNumOfViews() {
        return numOfViews;
    }


    public int getNumOfIndexs() {
        return numOfIndexes;
    }


    public ArrayList<String> getNameOfTables(){
        for(Table table : tables){
            nameOfTables.add(table.getNameOfTable());
        }
        return nameOfTables;
    }
    public ArrayList<String> getNameOfViews(){
        for(View view : views){
            nameOfViews.add(view.getNameOfView());
        }
        return nameOfViews;
    }
    public ArrayList<String> getNameOfIndexs(){
        for(Index index : indexes){
            nameOfViews.add(index.getNameOfIndex());
        }
        return nameOfIndexes;
    }
}
