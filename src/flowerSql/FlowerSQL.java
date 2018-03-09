package flowerSql;

import component.Database;
import engine.*;
import service.MainService;
import java.util.*;
public class FlowerSQL {
    private boolean userLogin;
    private Database database;
    private Engine engine;
    private MainService service;

    public FlowerSQL() {
        //this.userLogin = true;
        //别忘了之后改回来 改成TRUE是为了便于测试
         this.userLogin = false;
        this.database = new Database();
        this.engine = new Engine(database);
        this.service = new MainService(database,engine);
    }

    public void Init(){
        engine.loadTable();
    }

    public void mainCircle(){
        while(!service.isQuitStatus()){
            if(userLogin){
                waitUserCommand();
            }else if(!userLogin){
                waitUserLogin();
            }
        }
        engine.saveTable();
    }

    public void waitUserCommand(){
        Scanner sc = new Scanner(System.in);
        System.out.print("FlowerSQL-> ");
        String command = "";
        while(true) {
            String line = sc.nextLine();
            int len = line.length();
            command += line;
            if (line.contains(";")) {    //等同于： if(line.indexOf(";") != -1)
                break;
            }
            System.out.print("         -> ");
        }
        String result = command.trim();//去掉字符串首尾的空格
        result = result.substring(0,result.length() - 1);//去掉最后';'
        service.analysisCommand(result);
    }

    private void waitUserLogin(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input username : ");
        String userName = sc.nextLine();
        System.out.print("Please input password : ");
        String userPassword = sc.nextLine();
        boolean result = service.checkUser(userName,userPassword);
        if(result){
            this.userLogin = true;
        }
    }



}
