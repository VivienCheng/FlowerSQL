package engine;

import component.Database;
import table.*;
import java.util.*;
import net.sf.json.*;
import java.io.*;
import java.lang.*;
import index.*;


public class WriteFile {


    public WriteFile() {}

    public void saveAllData(Database db) {
        ArrayList<StructOfTable> structOfTable = new ArrayList<>();
        ArrayList<Data> dataOfTable = new ArrayList<>();

        ArrayList<Table> tables = db.getTables();
        for (Table table : tables) {
            structOfTable.add(table.getStructOfTable());
            dataOfTable.add(table.getDataOfTable());
        }

        ArrayList<StructOfIndex> structOfIndex = new ArrayList<>();
        ArrayList<Index> indexes = db.getIndexs();
        for(Index index : indexes) {
            structOfIndex.add(index.getStructOfIndex());
        }


        /*将对象转换成JSON格式*/
        //表结构
        JSONArray arrayOfStruct = JSONArray.fromObject(structOfTable);
        String structArray = arrayOfStruct.toString();

        //表数据
        JSONArray arrayOfData = JSONArray.fromObject(dataOfTable);
        String dataArray = arrayOfData.toString();

        //索引数据
        JSONArray arrayOfIndex = JSONArray.fromObject(structOfIndex);
        String indexArray = arrayOfIndex.toString();

        //this.structString = structArray;
        //this.dataString = dataArray;

//        System.out.println(structArray);
//        System.out.println(dataArray);

        String path1 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/DataAttributesOfFields.json";
        String path2 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/DataOfFields.json";
        String path3 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/StructOfIndex.json";

        dealSave(path1,structArray);
        dealSave(path2,dataArray);
        dealSave(path3,indexArray);

    }

    public void dealSave(String pathName,String neededArray) {
        try {
            File file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }

            java.io.FileWriter fw = new java.io.FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(neededArray);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
