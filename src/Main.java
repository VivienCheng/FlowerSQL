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
    }
}
/*
【create】
create table tableName(
attribute1 type1,
attribute2 type2,
……
)
create index tableName on table(desc);
例：
create index stub on Student(attribute desc/asc);

【help】
help database/table/index/view;

【select】
select (distinct) attribute1,attribute2……/* from tableName where xxxx;
select (distinct) attribute1,attribute2……/* from tableName group by attribute;
例：
select id,chinese,math,english from leeTest where id>=1 order by id dec;
select * from tableName group by id;
select distinct * from leeTest;

【insert】
insert into tableName values(value1,value2……); 
insert into tableName(attribute1,attribute2……) values(value1,value2……); 
例如：
insert into Student values(99,Tom,17,true); 
insert into Student(id,name,age,sex) values(34,Lily,15,false);

【grant】
grant attribute1,attribute2…… to user1,user2……/public;
例如：
grant insert,delete,select to public;
grant delete to root;

【revoke】
revoke attribute1,attribute2…… from user1,user2……/public;
例如：
revoke select from flower;

【update】
update tableName set xxxx (where xxxx);
例如：
update leeTest set chinese=chinese*2;
update leeTest set chinese=1 where id=1 or id=2;

【delete】
delete from tableName (where xxxx);
例如：
delete from leeTest where id=1;
delete from leeTest;
*/



