package engine;


import component.*;
import table.*;
import java.util.ArrayList;
import view.*;
import index.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.reverse;

public class Engine {
    private Database database;
    private ReadFile readFile;
    private WriteFile writeFile;


    //Constructor
    public Engine(Database database) {
        this.database = database;
        this.readFile = new ReadFile();
        this.writeFile = new WriteFile();
    }

    public void loadTable() {
        ArrayList<StructOfTable> struct = readFile.getAllTableStruct();
        ArrayList<Data> data = readFile.getAllTableData();
        //ArrayList<StructOfIndex> index = readFile.getAllIndex();
        int len = data.size();
        for (int i = 0; i < len; ++i) {
            database.addSingleTable(struct.get(i), data.get(i));
        }


    }

    public void saveTable() {
        writeFile.saveAllData(database);
    }

    public void createTable(String nameOfTable, ArrayList<Field> fields, String pk, ArrayList<String> fk) {
        if (database.getSingleTable(nameOfTable) == null) {
            StructOfTable struct = new StructOfTable(nameOfTable, fields, pk, fk);
            Data data = new Data();
            database.addSingleTable(struct, data);
            saveTable();
            System.out.println("Create table success");
        } else {
            System.out.println("Table does already exist");
        }
    }

    //增加视表
    public void createView(String command,String[] arr){
        String viewName = arr[2];
        String[] fieldName = arr[5].split(",");

        ArrayList<String> fieldsName = new ArrayList<>();
        for(int  i = 0; i < fieldName.length; ++i){
            fieldsName.add(fieldName[i]);
        }
        View view = new View(viewName,fieldsName,command);
        database.addSingleView(view);

    }

    //得到用户表的具体数据，并且向里面添加新的用户
    public void createUser(String userName,String password){
        Table table = database.getSingleTable("User");
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        ArrayList<String> addContent = new ArrayList<>();
        addContent.add(userName);
        addContent.add(password);
        content.add(addContent);
        saveTable();

        printLine(26);
        for(ArrayList<String> c : content){
            for(String cell : c){
                System.out.print("| " + String.format("%-10s", cell));
            }
            System.out.println(" |");
            printLine(26);
        }
    }
    //判断当前用户是否有相应的权限
    public boolean judgeRight(String nowUser,String needed){
        Table table = database.getSingleTable("Authority");
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        for(ArrayList<String> x : content){
            if(x.get(0).equals(nowUser)){
                if(ifExistsRight(x.get(1),needed)){
                    return true;
                }
            }
        }
        return false;
    }
    //寻找是否存在相应的权限
    public boolean ifExistsRight(String authority,String standard){
        String[] arr = authority.split("\\s+");
        for(String a : arr){
            if(a.equals(standard)){
                return true;
            }
        }
        return false;
    }

    //实现创建Index的功能
    public boolean judgeIndexExist(String indexName){
        ArrayList<Index> indexes = database.getIndexs();
        for(Index index : indexes){
            if(index.getNameOfIndex().equals(indexName)){
                return true;
            }
        }
        return false;
    }

    public void createIndex(int type,String indexName,String tableName,String[] arr){

        ArrayList<ArrayList<String>> contentIndex = new ArrayList<>();
        ArrayList<String> nameOfFields = new ArrayList<>();

        ArrayList<ArrayList<String>> contentCol = exchangeCol(tableName);


        //得到字段名数组并且判断是否有需要找寻的列
        String colName = arr[5];
        for(ArrayList<String> s : contentCol) {
            int result = colName.indexOf(s.get(0));
            if (result == -1) continue;
            contentIndex.add(s);
        }

        int typeOfIndex = 0;
        Data data = new Data();
        if(type == 1){
            for(ArrayList<String> s : contentIndex) {
                nameOfFields.add(s.get(0));
            }
            data.setContent(contentIndex);
            typeOfIndex = 0;

        }else if(type == 2){
            nameOfFields.add(colName);

            String fieldName = contentIndex.get(0).get(0);
            contentIndex.get(0).remove(0);

            String order = arr[6].toLowerCase();
            if(order.equals("desc")){
                typeOfIndex = 2;
                Collections.sort(contentIndex.get(0),new SortAscending());
            }else if(order.equals("asc")){
                typeOfIndex = 1;
                Collections.sort(contentIndex.get(0),new SortDescending());
            }
            contentIndex.get(0).add(0,fieldName);
        }

        StructOfIndex structOfIndex = new StructOfIndex(tableName,typeOfIndex,indexName,nameOfFields);
        database.addSingleIndex(structOfIndex,data);

        saveTable();
        printLine(50);
        for(ArrayList<String> c : contentIndex){
            for(String cell : c){
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        printLine(50);

    }

    //实现插入功能
    public boolean judgeTableExist(String tableName){
        ArrayList<Table> tables = database.getTables();
        for (Table table : tables) {
            if (table.getNameOfTable().equals(tableName)) {
                return true;
            }
        }
        return false;
    }

    public void insertLineData(String tableName,String[] value){
        Table table = database.getSingleTable(tableName);
        Data data = table.getDataOfTable();
        ArrayList<String> model = new ArrayList<>();
        for(String s : value){
            model.add(s);
        }
        data.addSingleData(model);
        System.out.println("Insert success! You insert 1 tuple.");
        saveTable();
    }

    public void insertData(String tableName,String fieldName,String[] value) {
        String[] nameArr = fieldName.split(",");

        Table table = database.getSingleTable(tableName);
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        StructOfTable struct = table.getStructOfTable();
        ArrayList<Field> fields = struct.getFields();

        ArrayList<String> perLineData = new ArrayList<>();

        for (Field f : fields) {
            //每个字段对应的名字和约束
            String singleFieldName = f.getNameOfField();

            int index = 0;
            boolean flag = false;
            for (int i = 0; i < nameArr.length; ++i) {
                if (nameArr[i].equals(singleFieldName)) {
                    index = i;
                    flag = true;
                    break;
                }
            }

            /*如果该字段没有任何约束 分为两种情况
             一种是INSERT指定数据 则直接加入
             另一种情况是不INSERT任何数据 则直接加入空白
             */
            if(f.getConstraints().size() == 0){
                if(flag)  perLineData.add(value[index]);
                else perLineData.add(" ");
                continue;
            }

            ArrayList<String> singleConstraints = f.getConstraints();


            //检查实体完整性（唯一、非空、主键）
            int result1 = singleConstraints.indexOf("primarykey"); //主键：非空
            int result2 = singleConstraints.indexOf("unique");//唯一
            int result3 = singleConstraints.indexOf("notnull");//非空
            int result4 = fieldName.indexOf(singleFieldName);



            //要插入的不是该字段 对应的数据添入非空 需要检查该字段属性是不是主键或者非空
            if (result4 == -1) {
                if (result1 != -1 || result3 != -1) {
                    System.out.println("This \"" + singleFieldName + "\" field's value can't be empty.");
                    return;
                } else {
                    perLineData.add(" ");
                }
            }
            //要插入的是该字段 检查对应数据是否符合约束
            else {
                String indexOfValue = value[index];

                if (result2 != -1 || result1 != -1) {//要求唯一
                    //ArrayList<ArrayList<String>> colData = exchangeCol(tableName);
                    ArrayList<String> singleCol = singleColData(exchangeCol(tableName), singleFieldName);
                    for (String x : singleCol) {
                        if (x.equals(indexOfValue)) {
                            System.out.println("This \"" + singleFieldName + "\" field's value must be unique.");
                            return;
                        }
                    }
                }
                perLineData.add(indexOfValue);
            }
        }

        content.add(perLineData);
        saveTable();
        //updateIndex(tableName,perLineData);
        System.out.println("Insert success! You insert 1 tuple.");

    }

    //进行帮助操作
    public void helpOperation(String type) {
        if (type.equals("database")) {
            int maxNum = Math.max(database.getNumOfIndexs(), database.getNumOfTables());
            maxNum = Math.max(maxNum, database.getNumOfViews());

            System.out.println("\n" + "Database : ");
            printLine(43);
            System.out.println("| " + "table       " + "| " + "view        " + "| " + "index       " + "|");
            printLine(43);

            ArrayList<String> tables = database.getNameOfTables();
            ArrayList<String> views = database.getNameOfViews();
            ArrayList<String> indexes = database.getNameOfIndexs();

            for (int i = 0; i < maxNum; ++i) {
                if (i < tables.size()) {
                    printSentence(tables.get(i));
                } else {
                    System.out.print("|");
                    printSpace(13);
                }
                if (i < views.size()) {
                    printSentence(views.get(i));
                } else {
                    System.out.print("|");
                    printSpace(13);
                }
                if (i < indexes.size()) {
                    printSentence(indexes.get(i));
                } else {
                    System.out.print("|");
                    printSpace(13);
                }
                System.out.println("|");
            }
            printLine(43);
        } else if (type.equals("table")) {
            ArrayList<Table> tables = database.getTables();
            for (Table table : tables) {
                System.out.println("\n" + table.getNameOfTable() + " : ");
                printLine(57);
                System.out.println("|" + " name      " + "|" + " type      " + "|" + " constraints                   " + "|");
                printLine(57);

                ArrayList<Field> fields = table.getStructOfTable().getFields();
                for (Field field : fields) {
                    System.out.print("| " + String.format("%-10s", field.getNameOfField()));
                    System.out.print("| " + String.format("%-10s", field.getType()));
                    System.out.print("| ");
                    int lenOfConstraints = 0;
                    for (String x : field.getConstraints()) {
                        System.out.printf(x + ",");
                        lenOfConstraints += x.length() + 1;
                    }
                    lenOfConstraints = 30 - lenOfConstraints;
                    printSpace(lenOfConstraints);
                    System.out.println("|");
                }
                printLine(57);
            }

        } else if (type.equals("view")) {
            ArrayList<View> views = database.getViews();
            for (View view : views) {
                System.out.println(view.getDefineSentence());
            }
        } else if (type.equals("index")) {
            ArrayList<Index> indexes = database.getIndexs();
            for (Index index : indexes) {
                System.out.println();
                printLine(20);
                System.out.println("| " + String.format("%-16s", index.getNameOfIndex()) + " |");
                printLine(20);
                ArrayList<String> names = index.getStructOfIndex().getNameOfFields();
                for (String name : names) {
                    System.out.println("| " + String.format("%-16s", name) + " |");
                }
                printLine(20);
            }
        }
    }

    private void printLine(int x) {
        System.out.print("+");
        for (int i = 0; i < x - 2; ++i) {
            System.out.print("-");
        }
        System.out.println("+");
    }

    private void printSpace(int x) {
        for (int i = 0; i < x; ++i) {
            System.out.print(" ");
        }
    }

    private void printSentence(String name){
        System.out.printf("| " + String.format("%-12s", name));
    }


    //将Data中的数据按照同一字段在同一数组的形式转换
    public ArrayList<ArrayList<String>> exchangeCol(String nameOfTable){
        Table table = database.getSingleTable(nameOfTable);
        StructOfTable struct = table.getStructOfTable();
        ArrayList<String> nameOfFields = struct.getNameOfFields();
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        int col = struct.getNumOfFields();

        ArrayList<ArrayList<String>> contentCol = new ArrayList<>();
        for (int j = 0; j < col; ++j) {
            ArrayList<String> percolContent = new ArrayList<>();
            percolContent.add(nameOfFields.get(j));
            for (ArrayList<String> s : content) {
                percolContent.add(s.get(j));
            }
            contentCol.add(percolContent);
        }
        return contentCol;

        /*System.out.println("!!!!");
        for (ArrayList<String> s : contentCol) {
            for (String g : s) {
                System.out.print(g + " ");
            }
            System.out.println();
        }*/
    }
    //得到单列的数据
    public ArrayList<String> singleColData(ArrayList<ArrayList<String>> contentCol,String fieldName){
        ArrayList<String> singleColcontent = new ArrayList<>();
        for(ArrayList<String> s : contentCol){
            if(s.get(0).equals(fieldName)){
                int len = s.size();
                for(int i = 1; i < len; ++i){
                    singleColcontent.add(s.get(i));
                }
            }
        }
        return singleColcontent;
    }

    //将权限进行撤回
    public void revokeAuthority(String[] authority,String[] username) {
        Table table = database.getSingleTable("Authority");
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        if(username[0].toLowerCase().equals("public")) {
            for (ArrayList<String> c : content) {
                String x = subtractAuthority(c.get(1),authority);
                c.remove(1);
                c.add(x);
            }
        }else{
            for(String name : username){
                for(ArrayList<String> c : content){
                    if(c.get(0).equals(name)){
                        String x = subtractAuthority(c.get(1),authority);
                        c.remove(1);
                        c.add(x); }
                }
            }
        }
        saveTable();
        System.out.println("50");
        for(ArrayList<String> c : content){
            System.out.println(c.get(0) + " : " + c.get(1));
        }
    }

    private String subtractAuthority(String x,String[] arr){//已有的 需要删去的
        boolean flag = false;
        for(String s : arr){
            if(s.toLowerCase().equals("all")){
                flag = true;
                break;
            }
            x = x.replace(s,"");
        }
        if(flag){
            x = x.replace(x," ");
        }
        return x;
    }

    //为用户授予权限
    public void grantAuthority(String[] authority,String[] username){
        Table table = database.getSingleTable("Authority");
        Data data = table.getDataOfTable();
        ArrayList<ArrayList<String>> content = data.getContent();
        if(username[0].toLowerCase().equals("public")) {
            for (ArrayList<String> c : content) {
                String x = addAuthority(c.get(1),authority);
                c.remove(1);
                c.add(x);
            }
        }else{
            for(String name : username){
                for(ArrayList<String> c : content){
                    if(c.get(0).equals(name)){
                        String x = addAuthority(c.get(1),authority);
                        c.remove(1);
                        c.add(x); }
                }
            }
        }
        saveTable();
        System.out.println("50");
        for(ArrayList<String> c : content){
            System.out.println(c.get(0) + " : " + c.get(1));
        }
    }

    private String addAuthority(String x,String[] arr) {
        for(String s : arr){
            x += " ";
            x += s;
        }
        x = dealAllAuthority(x);
        x = removeDuplicate(x);
        return x;
    }

    private String dealAllAuthority(String s){
        String x = "select insert update delete grant revoke ";
        s = s.replace("all",x);
        return s;
    }

    private String removeDuplicate(String x){
        String[] arr = x.split("\\s+");
        Set<String> s = new TreeSet<>();
        for(String a : arr){ s.add(a); }
        s.addAll(Arrays.asList(arr));
        String b = "";
        for(String a : s){
            b += a;
            b += " ";
        }
        return b;
    }
    //实现select功能
    public void selectData(String command) {
        boolean distinct = false;
        if (command.contains("DISTINCT") || command.contains("distinct")) {
            distinct = true;
            command = command.replace("distinct", "");
        }

        Matcher matcher = Pattern.compile("(?<=from )\\w*").matcher(command);
        String tableName;
        if (matcher.find()) {
            tableName = matcher.group(0);
        } else {
            System.err.println("无法解析指令");
            return;
        }

        String command_copy = command;
        String[] column = command_copy.split("select")[1].split("from")[0].split(",");

        String conditions = "";
        if (command.contains("group")) {
            Matcher matcher1 = Pattern.compile("(?<=where ).*(?=group)").matcher(command);
            if (matcher1.find()) {
                conditions = matcher1.group(0);
            } else {
                System.err.println("Error 1: can't find conditions!");
            }
        } else if (command.contains("order")) {
            Matcher matcher1 = Pattern.compile("(?<=where ).*(?=order)").matcher(command);
            if (matcher1.find()) {
                conditions = matcher1.group(0);
            } else {
                System.err.println("Error 1: can't find conditions!");
            }
        } else {
            Matcher matcher1 = Pattern.compile("(?<=where ).*").matcher(command);
            if (matcher1.find()) {
                conditions = matcher1.group(0);
            } else {
                System.err.println("Error 1: can't find conditions!");
            }
        }

        if (command.contains("group") && command.contains("order")) {
            Matcher matcher1 = Pattern.compile("(?<=where ).*(?=group)").matcher(command);
            String A = "", B = "";
            if (matcher1.find()) {
                A = matcher1.group(0);
            }
            Matcher matcher2 = Pattern.compile("(?<=where ).*(?=order)").matcher(command);
            if (matcher2.find()) {
                B = matcher2.group();
            }
            if (A.length() > B.length()) {
                conditions = B;
            } else {
                conditions = A;
            }
        }

        if (conditions.length() == 0) {
            conditions = null;
        }

        String groupBy;
        matcher = Pattern.compile("(?<=group by )\\w*").matcher(command);
        if (matcher.find()) {
            groupBy = matcher.group(0);
        } else {
            groupBy = null;
        }
        String orderBy;
        matcher = Pattern.compile("(?<=order by ).*").matcher(command);
        if (matcher.find()) {
            orderBy = matcher.group(0);
        } else {
            orderBy = null;
        }
        Stack<String> stack = build_stack_by_conditions(conditions);

        //build table which match conditions in where
        Table table = this.database.getSingleTable(tableName);
        int tableRowNumber = table.getRowNumber();

        if (column.length == 1 && column[0].trim().equals("*")) {
            column = table.getAllFieldName();
        }

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < tableRowNumber; ++i) {
            ArrayList<String> row = new ArrayList<>();
            ArrayList<String> temp_row = new ArrayList<>();
            row.addAll(table.getRow(i));


            if (stack != null) {
                Stack<String> stack_copy = new Stack<>();
                stack_copy.addAll(stack);
                if (check_condition(table, row, stack_copy)) {
                    result.add(row);
                }
            } else {
                result.add(row);
            }
        }
        if (result.size() == 0) {
            System.err.println("No result!");
            return;
        }
        if (groupBy != null) {
            for (String columnName : column) {
                if (!table.check_if_field_type_is_int(columnName) && !column.equals(groupBy)) {
                    System.err.println("无法对非整型的数据进行Group操作");
                    return;
                }
            }
            int numOfGroupBy = table.getColumnNum(groupBy);
            for (int i = 0; i < result.size(); ++i) {
                ArrayList<String> row = result.get(i);
                for (int j = i + 1; j < result.size(); ++j) {
                    if (row.get(numOfGroupBy).equals(result.get(j).get(numOfGroupBy))) {
                        //combine two row
                        for (int t = 0; t < row.size(); ++t) {
                            if (t == numOfGroupBy)
                                continue;
                            ;
                            String temp = row.get(t);
                            temp += (',' + result.get(j).get(t));
                            row.set(t, temp);
                            result.get(j).set(t, "None");
                        }
                    }
                }
            }
            //clear unusable row
            for (int i = 0; i < result.size(); ++i) {
                int count = 0;
                ArrayList<String> row = result.get(i);
                for (String data : row) {
                    if (data.equals("None"))
                        count += 1;
                }
                if (count == row.size() - 1) {
                    result.remove(row);
                }
            }


            //add data
            for (ArrayList<String> row : result) {
                for (String data : row) {
                    String[] temp_data = data.split(",");
                    int temp_result = 0;
                    for (String each : temp_data) {
                        temp_result += Integer.parseInt(each);
                    }
                    row.set(row.indexOf(data), temp_result + "");
                }
            }

        }
        final int[] fieldNum = new int[1];
        String rule = new String();
        if (orderBy != null) {
            String[] order = orderBy.split(" ");
            String orderByField = order[0];
            if (order.length > 1)
                rule = order[1];
            else
                rule = "Default";
            fieldNum[0] = table.getColumnNum(orderByField);
        }
        //自定义Comparator对象，自定义排序
        String finalRule = rule;
        Comparator c = new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                int num = fieldNum[0];
                String rul = finalRule;
                if (Integer.parseInt(o1.get(num)) < Integer.parseInt(o2.get(num)))
                    if (rul.equals("dec"))
                        return 1;
                    else
                        return -1;

                else {
                    if (rul.equals("dec"))
                        return -1;
                    else
                        return 1;
                }
            }
        };
        result.sort(c);


        ArrayList<ArrayList<String>> result_selected = new ArrayList<>();
        for (ArrayList<String> row : result) {
            ArrayList<String> row_selected = new ArrayList<>();
            for (String col : column) {
                row_selected.add(row.get(table.getColumnNum(col.trim())));
            }
            result_selected.add(row_selected);
        }

        if (distinct) {
            for (int i = 0; i < result_selected.size(); ++i) {
                for (int j = i + 1; j < result_selected.size(); ++j) {
                    boolean same = true;
                    for (int k = 0; k < result_selected.get(i).size(); ++k) {
                        if (!result_selected.get(i).get(k).equals(result_selected.get(j).get(k))) {
                            same = false;
                        }
                    }
                    if (same) {
                        result_selected.remove(j);
                    }
                }
            }
        }

        // dispaly
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(column));
        result_selected.add(0, temp);
        int sumWidth = 0;
        for (int i = 0; i < result_selected.get(0).size(); ++i) {
            int width = 0;
            for (int j = 0; j < result_selected.size(); ++j) {
                if (width < result_selected.get(j).get(i).length()) {
                    width = result_selected.get(j).get(i).length();
                }
            }
            sumWidth += width;
            for (ArrayList<String> row : result_selected) {
                StringBuilder ori = new StringBuilder(row.get(i));
                int length = ori.length();
                for (int j = 0; j < width - length; ++j) {
                    ori.append(" ");
                }
                row.set(i, ori.toString());
            }
        }

        String tempString = " ";
        for (int i = 0; i < sumWidth + result_selected.get(0).size() - 1; ++i) {
            tempString += "-";
        }
        System.out.println(tempString);
        for (ArrayList<String> row : result_selected) {
            System.out.print("|");
            for (String cell : row) {
                System.out.print(cell + "|");
            }
            System.out.println();
        }
        System.out.println(tempString);


    }
    //实现update功能
    public void updateData(String command) {
        Matcher matcher = Pattern.compile("(?<=update )\\w*").matcher(command);
        String tableName;
        if (matcher.find()) {
            tableName = matcher.group(0);
        } else {
            System.err.println("无法解析指令");
            return;
        }

        String value = "";
        if (command.contains("where"))
            matcher = Pattern.compile("(?<=set).*(?=where)").matcher(command);
        else
            matcher = Pattern.compile("(?<=set).*").matcher(command);
        if (matcher.find()) {
            value = matcher.group(0);
        }

        String where = null;
        if (command.contains("where")) {
            matcher = Pattern.compile("(?<=where).*").matcher(command);
            if (matcher.find()) {
                where = matcher.group(0);
            }
        } else {
            where = null;
        }

        Table table = database.getSingleTable(tableName.trim());

        int columnNum = table.getColumnNum(value.split("=")[0].trim());

        for (int i = 0; i < table.getDataOfTable().getContent().size(); ++i) {
            ArrayList<String> row = table.getDataOfTable().getContent().get(i);
            if (check_condition(table, row, build_stack_by_conditions(where))) {
                String[] values = value.split("=");
                String field = values[0];
                String right = values[1];
//                String[] cal = right.split("\\+|-|\\*|/");
                ArrayList<String> cal = new ArrayList<>();
                String tempS = "";
                for(int j = 0; j < right.length(); ++j){
                    char p = right.charAt(j);
                    if(p=='+'){
                        cal.add(tempS);
                        cal.add("+");
                        tempS = "";
                    }
                    else if(p=='-'){
                        cal.add(tempS);
                        cal.add("-");
                        tempS = "";
                    }
                    else if(p=='*'){
                        cal.add(tempS);
                        cal.add("*");
                        tempS = "";
                    }
                    else if(p=='/'){
                        cal.add(tempS);
                        cal.add("/");
                        tempS = "";
                    }
                    else{
                        tempS += p;
                    }
                }
                if(tempS.length() >= 1){
                    cal.add(tempS);
                }

                int result = 0;
                char pre_operator = 'S';
                for (int j = 0; j < cal.size(); ++j) {
                    if (cal.get(j).equals("+")) {
                        pre_operator = '+';
                    } else if (cal.get(j).equals("-")) {
                        pre_operator = '-';
                    } else if (cal.get(j).equals("*")) {
                        pre_operator = '*';
                    } else if (cal.get(j).equals("/")) {
                        pre_operator = '/';
                    } else if (Pattern.compile("^[-\\+]?[\\d]*$").matcher(cal.get(j).trim()).matches()) {
                        if (pre_operator == 'S') {
                            result = Integer.parseInt(cal.get(j).trim());
                        } else {
                            if (pre_operator == '+') {
                                result = result + Integer.parseInt(cal.get(j).trim());
                            } else if (pre_operator == '-') {
                                result = result - Integer.parseInt(cal.get(j).trim());
                            } else if (pre_operator == '*') {
                                result = result * Integer.parseInt(cal.get(j).trim());
                            } else if (pre_operator == '/') {
                                result = result / Integer.parseInt(cal.get(j).trim());
                            }
                        }
                    } else {
                        int temp = Integer.parseInt(row.get(table.getColumnNum(cal.get(j).trim())));
                        if (pre_operator == '+') {
                            result = result + temp;
                        } else if (pre_operator == '-') {
                            result = result - temp;
                        } else if (pre_operator == '*') {
                            result = result * temp;
                        } else if (pre_operator == '/') {
                            result = result / temp;
                        }else if (pre_operator == 'S') {
                            result = result + temp;
                        }

                    }
                    row.set(table.getColumnNum(field.trim()), result + "");

                }
                table.getDataOfTable().getContent().set(i,row);
            }
        }
    }
    //实现delete功能
    public void deleteData(String command){
        Matcher matcher = Pattern.compile("(?<=from )\\w*").matcher(command);
        String tableName;
        if (matcher.find()) {
            tableName = matcher.group(0);
        } else {
            System.err.println("无法解析指令");
            return;
        }

        String where = null;
        if (command.contains("where")) {
            matcher = Pattern.compile("(?<=where).*").matcher(command);
            if (matcher.find()) {
                where = matcher.group(0);
            }
        } else {
            where = null;
        }

        Table table = database.getSingleTable(tableName.trim());
        ArrayList<ArrayList<String>> delete_index = new ArrayList<>();
        for (int i = 0; i < table.getDataOfTable().getContent().size(); ++i) {
            ArrayList<String> row = table.getDataOfTable().getContent().get(i);
            if (check_condition(table, row, build_stack_by_conditions(where))) {
                delete_index.add(table.getDataOfTable().getContent().get(i));
            }
        }
        for(ArrayList<String> index: delete_index){
            table.getDataOfTable().getContent().remove(index);
        }
    }

    private boolean check_condition(Table table, ArrayList<String> row, Stack<String> conditions) {
        boolean status = true;
        while (!conditions.empty()) {
            String expression = conditions.peek();
            if (expression.contains(">=")) {
                String[] temp = expression.split(">=");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1].trim()) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) >= Integer.parseInt(right));
            } else if (expression.contains("<=")) {
                String[] temp = expression.split("<=");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1].trim()) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) <= Integer.parseInt(right));
            } else if (expression.contains("!=")) {
                String[] temp = expression.split("!=");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1].trim()) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) != Integer.parseInt(right));
            } else if (expression.contains("<")) {
                String[] temp = expression.split("<");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1].trim()) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) < Integer.parseInt(right));
            } else if (expression.contains(">")) {
                String[] temp = expression.split(">");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1].trim()) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) > Integer.parseInt(right));
            } else if (expression.contains("=")) {
                String[] temp = expression.split("=");
                String left = row.get(table.getColumnNum(temp[0].trim())).trim();
                String right;
                if (table.getColumnNum(temp[1]) != -1) {
                    right = row.get(table.getColumnNum(temp[1].trim())).trim();
                } else {
                    right = temp[1].trim();
                }
                status = status && (Integer.parseInt(left) == Integer.parseInt(right));
            } else if (expression.contains("or")) {
                if (status)
                    return true;
                else
                    status = true;
            } else if (expression.contains(")")) {
                Stack<String> sub_condition = new Stack<>();
                conditions.pop();
                while (conditions.peek() != "(") {
                    sub_condition.add(conditions.peek());
                    conditions.pop();
                }
                conditions.pop();
                reverse(sub_condition);
                status = status && check_condition(table, row, sub_condition);
            }
            conditions.pop();
        }
        return status;

    }

    private Stack<String> build_stack_by_conditions(String conditions) {
        Stack<String> stack;
        if (conditions != null) {
            stack = new Stack<>();
            String conditions_handle = "";
            for (int i = 0; i < conditions.length(); ++i) {
                char each = conditions.charAt(i);
                if (each == '(') {
                    conditions_handle += (each + ' ');
                } else if (each == ')') {
                    conditions_handle += (' ' + each);
                } else {
                    conditions_handle += each;
                }
            }
            String[] conditions_array = conditions_handle.split(" ");

            for (String each : conditions_array) {
                if (each.equals("and") || each.equals("or")) {
                    stack.push(each);
                } else if (each.equals("(") || each.equals(")")) {
                    stack.push(each);
                } else {
                    if (stack.size() > 0) {
                        if (stack.peek().equals("and") || stack.peek().equals("or") ||
                                stack.peek().equals("(") || stack.peek().equals(")")) {
                            stack.push(each);
                        } else {
                            String top = stack.peek();
                            stack.pop();
                            stack.push(top += (' ' + each));
                        }
                    } else {
                        stack.push(each);
                    }
                }
            }

        } else {
            stack = new Stack<>();
        }
        return stack;
    }


}

