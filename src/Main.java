import component.*;
import engine.*;
import table.*;
import service.*;
import java.util.ArrayList;
import flowerSql.*;

public class Main {


    public static void main(String[] args) {
        FlowerSQL flower = new FlowerSQL();
        flower.Init();
        flower.mainCircle();






        /*
        //学生表
        Database database = new Database();
        Table table = new Table();

        ArrayList<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "int", new ArrayList<>()));
        fields.add(new Field("name", "char(30)", new ArrayList<>()));
        fields.add(new Field("age", "int", new ArrayList<>()));
        fields.add(new Field("sex", "bool", new ArrayList<>()));


        ArrayList<String> temp = new ArrayList<>();
        temp.add("name");
        table.setStructOfTable(new StructOfTable("Student",fields, "id", temp));

        // Set Data
        Data data = new Data();

        ArrayList<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("DaMing");
        row1.add("18");
        row1.add("True");
        data.addSingleData(row1);


        ArrayList<String> row2 = new ArrayList<>();
        row2.add("2");
        row2.add("XiaoMing");
        row2.add("9");
        row2.add("True");
        data.addSingleData(row2);

        ArrayList<String> row3 = new ArrayList<>();
        row3.add("3");
        row3.add("DaHong");
        row3.add("18");
        row3.add("False");
        data.addSingleData(row3);

        table.setDataOfTable(data);



        ArrayList<Table> tables = new ArrayList<>();
        tables.add(table);

        //用户表
        Table userTable = new Table();

        ArrayList<Field> userFields = new ArrayList<>();

        ArrayList<String> idConstraints = new ArrayList<>();
        idConstraints.add("PrimaryKey");
        ArrayList<String> nameConstraints = new ArrayList<>();
        nameConstraints.add("ForeignKey");
        nameConstraints.add("Not Null");
        userFields.add(new Field("id", "int", idConstraints));
        userFields.add(new Field("name", "char", nameConstraints));
        userFields.add(new Field("password", "char", new ArrayList<>()));

        ArrayList<String> userTemp = new ArrayList<>();
        userTemp.add("name");
        userTable.setStructOfTable(new StructOfTable("User",userFields, "id", userTemp));

        Data userData = new Data();

        ArrayList<String> userRow1 = new ArrayList<>();
        userRow1.add("1");
        userRow1.add("DaMing");
        userRow1.add("123456");
        userData.addSingleData(userRow1);

        ArrayList<String> userRow2 = new ArrayList<>();
        userRow2.add("2");
        userRow2.add("XiaoMing");
        userRow2.add("qazwsx");
        userData.addSingleData(userRow2);

        userTable.setDataOfTable(userData);


        tables.add(userTable);
        database.setTables(tables);

        WriteFile writeFile = new WriteFile();
        writeFile.saveAllData(database);


        ReadFile readFile = new ReadFile();
        readFile.getAllTableData();
        readFile.getAllTableStruct();


        SubService subService = new SubService(database);

        subService.checkPassword("DaMing","123456");

        */
    }
}



