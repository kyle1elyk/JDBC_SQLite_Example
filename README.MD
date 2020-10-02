# JDBC_SQLite example
Built for **CSE5260: Database Systems, Fall 2020**


### SQL statements

###### [JdbcSqliteRunner.java](src/main/java/JdbcSqliteRunner.java):62 
    create table log  
    (  
        id            INTEGER primary key,  
        message       TEXT,  
        posted        INTEGER,  
        message_level INTEGER  
    );

###### [LiteLog.java](src/main/java/LiteLog.java):86 
    INSERT INTO log(message, posted, message_level) VALUES (?, ?, ?)

###### [LiteLog.java](src/main/java/LiteLog.java):143
    SELECT * FROM log ORDER BY id DESC LIMIT 1
###### [LiteLog.java](src/main/java/LiteLog.java):111
    SELECT * FROM log 

