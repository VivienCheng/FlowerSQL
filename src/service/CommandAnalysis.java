package service;

import component.*;
import engine.*;
import table.*;
import java.util.*;

public class CommandAnalysis {
    private Engine engine;
    private String nowUser;

    //Constructor
    public CommandAnalysis(Engine engine) {
        this.nowUser = "";
        this.engine = engine;
    }

    public String getNowUser() { return nowUser; }

    public void setNowUser(String nowUser) { this.nowUser = nowUser; }

    public boolean parse(String nowUser, String command){
        setNowUser(nowUser);
        //利用正则表达式找出第一个单词
        String[] arr = command.split("\\s+");
        String keyword = arr[0].toLowerCase();
        //针对create语句 进行创建类型的解析  view,table,index,user
        String type = arr[1].toLowerCase();



        if(keyword.equals("create")){
            parseCreate(command,type);
        } else if(keyword.equals("select")){
            parseSelect(command);
        }else if(keyword.equals("insert")){
            parseInsert(command);
        }else if(keyword.equals("delete")){
            parseDelete(command);
        }else if(keyword.equals("update")){
            parseUpdate(command);
        } else if(keyword.equals("help")) {
            parseHelp(type);
        }else if(keyword.equals("grant")) {
            parseGrant(command);
        }else if(keyword.equals("revoke")) {
            parseRevoke(command);
        }else if(keyword.equals("analysis")){
            parseAnalysis(arr[2]);
        } else if(keyword.equals("quit")){
            engine.saveTable();
            return true;
        }
        return false;
    }
    public void parseGrant(String command){
        if(engine.judgeRight(getNowUser(),"grant")) {
            String[] arr = command.split("\\s+");
            String[] op = arr[1].split(",");
            String[] user = arr[3].split(",");
            engine.grantAuthority(op, user);
            System.out.println("Grant success!");
        }else{
            System.out.println("You don't have the grant authority.");
        }

    }

    public void parseRevoke(String command){
        if(engine.judgeRight(getNowUser(),"revoke")) {
            String[] arr = command.split("\\s+");
            String[] op = arr[1].split(",");
            String[] user = arr[3].split(",");
            engine.revokeAuthority(op, user);
            System.out.println("Revoke success!");
        }else{
            System.out.println("You don't have the revoke authority.");
        }

    }

    public void parseHelp(String type){
        engine.helpOperation(type);
    }

    public void parseAnalysis(String tableName){
       // engine.analysisConstraints(tableName);
    }

    public void parseCreate(String command,String type) {
        if (type.equals("table")) {
            command = command.replace('(', ',');
            command = command.replace(')', ',');
            String[] arr = command.split(",");

            //确定表名
            String[] firstString = arr[0].split("\\s+");
            String nameOfTable = firstString[2];

            //得到主键,外键，字段
            ArrayList<Field> fields = new ArrayList<>();
            String pk = "";

            ArrayList<String> fk = new ArrayList<>();
            for (int i = 1; i < arr.length; ++i) {
                 String[] singleString = arr[i].split("\\s+");
                //得到约束
                ArrayList<String> constraints = new ArrayList<>();
                 for (int j = 2; j < singleString.length; ++j) {

                     if (singleString[j].toLowerCase().equals("primarykey")) {
                         pk = singleString[0];
                     } else if (singleString[j].toLowerCase().equals("foreignkey")) {
                         fk.add(singleString[0]);
                     }
                     constraints.add(singleString[j]);
                 }
                fields.add(new Field(singleString[0], singleString[1], constraints));
            }
            engine.createTable(nameOfTable, fields, pk, fk);

        }else if(type.equals("user")){
            String[] arr = command.split("\\s+");
            String userName = arr[2];
            String password = arr[5];
            engine.createUser(userName,password);
        }else if(type.equals("index")){
            command = command.replace("("," ");
            command = command.replace(")"," ");
            String[] arr = command.split("\\s+");
            String indexName = arr[2];
            String tableName = arr[4];
            if(!engine.judgeIndexExist(indexName)) {
                if (arr.length == 6) {
                    engine.createIndex(1, indexName, tableName, arr);
                } else {
                    engine.createIndex(2, indexName, tableName, arr);
                }
            }else{
                System.out.println("This index \"" + indexName + "\" has already existed.");
            }
        }else if(type.equals("view")){
            String[] arr = command.split("\\s+");

            engine.createView(command,arr);
            System.out.println("Create view success!");
        }
    }

    public void parseSelect(String command){
        engine.selectData(command);
    }

    public void parseInsert(String command){
        command = command.replace("("," ");
        command = command.replace(")"," ");
        String[] arr = command.split("\\s+");
       // System.out.println("!!!" + engine.judgeTableExist(arr[2]));
        if(engine.judgeTableExist(arr[2])) {
            if (arr[3].toLowerCase().equals("values")) {
                String[] content = arr[4].split(",");
                engine.insertLineData(arr[2],content);
            }else{
                String[] value = arr[5].split(",");
                engine.insertData(arr[2],arr[3],value);
            }
        }else{
            System.out.println("This table \"" + arr[2] + "\" doesn't exist!");
        }

    }

    public void parseDelete(String command){
        engine.deleteData(command);
    }

    public void parseUpdate(String command){
        engine.updateData(command);
    }



}
