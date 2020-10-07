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

###### [LiteLog.java](src/main/java/LiteLog.java):98 
    INSERT INTO log(message, posted, message_level) VALUES (?, ?, ?)
###### [LiteLog.java](src/main/java/LiteLog.java):124
    SELECT * FROM log 
###### [LiteLog.java](src/main/java/LiteLog.java):158
    SELECT * FROM log ORDER BY id DESC LIMIT 1


---

##### Usage

*(Prebuilt jar available under action artifacts)*

###### Build
    mvn -B clean package
###### Run
    java -jar target/JDBC_SQLite-1.0-SNAPSHOT-jar-with-dependencies.jar

