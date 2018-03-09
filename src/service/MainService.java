package service;


import component.*;
import engine.*;
public class MainService {
    private SubService subService;
    private boolean quitStatus;
    private CommandAnalysis commandAnalysis;
    private Database database;
    private String userName;


    public MainService(Database da,Engine engine){
        this.userName = "";
        this.database = da;
        this.subService = new SubService(da);
        this.quitStatus = false;
        this.commandAnalysis = new CommandAnalysis(engine);
    }

    public boolean checkUser(String name,String password){
        if(!subService.checkUserExist(name)){
            System.out.println("Username doesn't exist!");
            return false;
        }
        if(!subService.checkPassword(name,password)){
            System.out.println("Password is not correct!");
            return false;
        }
        setUserName(name);
        System.out.println("Welcome to the FlowerSQL monitor. Commands end with ; ");
        return true;
    }

    public void analysisCommand(String command){
        this.quitStatus = commandAnalysis.parse(getUserName(),command);
    }
    //Getter and Setter
    public boolean isQuitStatus() {
        return quitStatus;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
