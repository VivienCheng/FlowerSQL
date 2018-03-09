package service;


import component.*;
import table.*;
import java.util.*;
public class SubService {
    private Database database;

    public SubService(Database da){
        this.database = da;
    }

    public boolean checkUserExist(String name){
        //找到"用户表"
        Table userTable = database.getSingleTable("User");

        //确定下来"name"属性所在的列
        ArrayList<String> colArray = userTable.getStructOfTable().getNameOfFields();
        int colOfName = assureCol(colArray,"name");

        //System.out.println(colOfName );


        Data data = userTable.getDataOfTable();
        for(ArrayList<String> row: data.getContent()){
            if(row.get(colOfName).equals(name)){
                return true;
            }
        }
        return false;
    }

    private int assureCol(ArrayList<String> colArray,String attribute){
        int result = 0;
        for(String colName: colArray){
            if(colName.equals(attribute)){
                break;
            }
            result++;
        }
        return result;
    }

    public boolean checkPassword(String name,String password) {
        Table userTable = database.getSingleTable("User");
        Data data = userTable.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        for (ArrayList<String> row : content) {
            if (row.get(0).equals(name)) {
                for (String cell : row) {
                    if (cell.equals(password)) {
                        //System.out.println(cell);
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
