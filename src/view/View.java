package view;

import java.util.*;
import table.*;
/*
视图要与表相区别.
视图由视图名、字段这2部分构成
*/
public class View {
   private String nameOfView;
   private ArrayList<String> fieldsNameOfView;
   private String defineSentence;
   /*
   此处的二维字段 只有2列
   第一列对应的字段的名字 第二列对应的是对该字段的要求
   */

   //Constructor
   public View(){
      nameOfView = "";
      this.fieldsNameOfView = new ArrayList<>();
      this.defineSentence = "";
   }

   public View(String nameOfView, ArrayList<String> fieldsOfView, String defineSentence) {
      this.nameOfView = nameOfView;
      this.fieldsNameOfView = fieldsOfView;
      this.defineSentence = defineSentence;
   }

   public String getNameOfView() {
      return nameOfView;
   }

   public String getDefineSentence() {
      return defineSentence;
   }
}
