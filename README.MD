# JDBC_SQLite example
Built for **CSE5260: Database Systems, Fall 2020**


### SQL statements

    -- auto-generated definition  
    create table log  
    (  
        id            INTEGER primary key,  
        message       TEXT,  
        posted        INTEGER,  
        message_level INTEGER  
    );

<!-- break -->

    INSERT INTO log(message, posted, message_level) VALUES (?, ?, ?)

<!-- break -->

    SELECT * FROM log ORDER BY id DESC LIMIT 1
    SELECT * FROM log 

