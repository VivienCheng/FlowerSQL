# FlowerSQL

一.概述

FlowerSQL是一款使用java编写的高性能数据库系统。系统支持部分标准SQL语句，支持创建复杂表，支持复杂表查询，支持创建和查询索引，支持更新、插入和删除表，内有用户表和权限表的设定，支持用户权限的授予和撤销。

二.FlowerSQL系统功能设定

● 基于json格式文件的java数据库系统，支持标准的SQL语句。

● 支持多数据库，支持一个数据库里面有多个表，多个索引，多个视图。 

● 支持用户的登录，支持用户名和密码的验证，有用户表。

● 支持用户权限的设定，支持用户权限的授予与撤销。

●	存储json格式的文件时，一个数据库一个文件夹。

●	针对数据表，将表的属性和表的数据分开储存。

●	针对索引，只存储索引的属性。

●	具有良好的提示和帮助信息。

●	支持CREATE、SELECT、INSERT、DELETE、UPDATE等SQL语句。

●	支持数据库表的not null、null、unique、primary key、foreign key等约束。

●	支持单元组和元组集合的insert语句，可检查实体完整性。

●	支持delete和update语句,并支持where子语句。

●	支持按条件select语句，支持group by、having和oreder by子句,支持where相关语句。

●	支持按索引读取数据，提高数据库性能。vZXvX

三.系统软件流程

![Aaron Swartz](https://raw.githubusercontent.com/VivienCheng/FlowerSQL/master/%E5%9B%BE%E7%89%87/%E7%B3%BB%E7%BB%9F%E8%BD%AF%E4%BB%B6%E6%B5%81%E7%A8%8B.jpg)

四.部分系统图解

帮助系统

![Aaron Swartz](https://github.com/VivienCheng/FlowerSQL/blob/master/%E5%9B%BE%E7%89%87/%E5%B8%AE%E5%8A%A9%E7%B3%BB%E7%BB%9F.jpg)

用户登录和系统

![Aaron Swartz](https://raw.githubusercontent.com/VivienCheng/FlowerSQL/master/%E5%9B%BE%E7%89%87/%E7%94%A8%E6%88%B7%E5%92%8C%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E7%A4%BA%E6%84%8F%E5%9B%BE.jpg)

FlowerSQL语句处理系统

![Aaron Swartz](https://raw.githubusercontent.com/VivienCheng/FlowerSQL/master/%E5%9B%BE%E7%89%87/SQL%E8%AF%AD%E5%8F%A5%E5%A4%84%E7%90%86%E7%B3%BB%E7%BB%9F%20.jpg)

五.FlowerSQL所支持的语句

[create]

create table tableName(attribute1 type1,attribute2 type2,……)

create index tableName on table(desc);

[help]

help database/table/index/view;

[select]

select (distinct) attribute1,attribute2……/* from tableName where xxxx;

select (distinct) attribute1,attribute2……/* from tableName group by attribute;

[insert]

insert into tableName values(value1,value2……); 

insert into tableName(attribute1,attribute2……) values(value1,value2……); 

[grant]

grant attribute1,attribute2…… to user1,user2……/public;

[revoke]

revoke attribute1,attribute2…… from user1,user2……/public;

[update]

update tableName set xxxx (where xxxx);

[delete]

delete from tableName (where xxxx);
