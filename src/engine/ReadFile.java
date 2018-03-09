package engine;


import index.StructOfIndex;
import net.sf.json.*;
import table.*;

import java.io.*;
import java.util.*;


public class ReadFile {


    //Constructor
    public ReadFile(){

    }

    public ArrayList<StructOfTable> getAllTableStruct() {
        //根据路径读取文件
        String path1 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/DataAttributesOfFields.json";
        String structArray = readFile(path1);
        //json-lib 复杂数据类型的转换
        Map<String,Class> classMap = new HashMap<>();
        classMap.put("fields",Field.class);

        //将json对象存到json对象数组中
        ArrayList<StructOfTable> results = new ArrayList<>();
        JSONArray toArray = JSONArray.fromObject(structArray);
        for(int i = 0; i < toArray.size(); ++i){
            JSONObject jsonObject = (JSONObject)toArray.get(i);
            StructOfTable structOfTable = (StructOfTable)JSONObject.toBean(jsonObject,StructOfTable.class,classMap);
            results.add(structOfTable);
        }
        /*ArrayList<Field> fields = results.get(0).getFields();
        for(Field field: fields){
            System.out.println(field);

        }*/
        /*System.out.println(results.get(0).getFields());*/
        return results;
    }

    public ArrayList<Data> getAllTableData() {
        String path2 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/DataOfFields.json";
        String dataArray = readFile(path2);

        //String转成JSON对象数组
        JSONArray toArray = JSONArray.fromObject(dataArray);
        //将JSON对象数组转成ArrayList<Data>类型
        ArrayList<Data> dt = (ArrayList<Data>) JSONArray.toCollection(toArray, Data.class);
        /*System.out.println(dt.get(0).getContent());*/
        return dt;
    }

    public ArrayList<StructOfIndex> getAllIndex(){
        String path3 = "/Users/chengxiaohua/IdeaProjects/FlowerSQL/src/sumData/StructOfIndex.json";
        String structArray = readFile(path3);

        JSONArray toArray = JSONArray.fromObject(structArray);
        ArrayList<StructOfIndex> si = (ArrayList<StructOfIndex>) JSONArray.toCollection(toArray, StructOfIndex.class);
        return si;
    }

    private String readFile(String pathName){
        File file = new File(pathName);
        BufferedReader reader = null;
        String sumString = "";

        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString = "";

            while((tempString = reader.readLine()) != null){
                sumString += tempString;
                //   System.out.println(tempString);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DataBase store file doesn\\'t exist!");
        }finally{
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e1){
                }
            }
        }
        // System.out.println(sumString);
        return sumString;
    }



}