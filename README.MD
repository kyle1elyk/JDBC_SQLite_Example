# JDBC_SQLite example
Built for **CSE5260: Database Systems, Fall 2020**


### SQL statements

###### [JdbcSqliteRunner.java](src/main/java/JdbcSqliteRunner.java):62 
    CREATE TABLE IF NOT EXISTS `log`
    (  
        id            INTEGER PRIMARY KEY,  
        message       TEXT,  
        posted        INTEGER,  
        message_level INTEGER  
    );

###### [LiteLog.java](src/main/java/LiteLog.java):88 
    INSERT INTO log(message, posted, message_level) VALUES (?, ?, ?)
###### [LiteLog.java](src/main/java/LiteLog.java):113
    SELECT * FROM log 
###### [LiteLog.java](src/main/java/LiteLog.java):145
    SELECT * FROM log ORDER BY id DESC LIMIT 1


