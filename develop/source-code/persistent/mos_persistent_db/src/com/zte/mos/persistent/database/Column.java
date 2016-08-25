package com.zte.mos.persistent.database;

import java.sql.ResultSet;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by xy on 14-12-30.
 */
public class Column {
    private final String name;

    public Column(String column_name) {
        this.name = column_name;
    }

    public String name() {
        return name;
    }

    public Object read(ResultSet resultSet) throws Exception {
        logger(this.getClass()).debug("read column : " + this.name);
        return resultSet.getObject(name);
    }
}
