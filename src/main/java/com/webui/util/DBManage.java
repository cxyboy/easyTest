package com.webui.util;

import java.util.*;

public class DBManage {

    String id;
    Date date;
    public String[] queryConditions;


    public void addQu(Object... map) {
        queryConditions = new String[map.length];

        for (int i = 0; i < map.length; i++) {
            queryConditions[i] = String.valueOf(map[i]);
        }
    }

    public void query() {
        String sql = "select * from ";
        String table = this.getClass().getSimpleName();
        if (queryConditions != null && queryConditions.length > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(" ").append("where").append(" ");
            for (String queryCondition : queryConditions) {
                builder.append(queryCondition).append(" ").append("and").append(" ");
            }
            builder.delete(builder.lastIndexOf("a"), builder.length());
            sql = sql + table + builder;
        } else {
            sql = sql + " " + table;
        }
        execute(sql);

    }

    //insert  into <表名> (列名) values (列值)
    public void insert() {
        String sql = "inset into ";
    }

    //delete from <表名> [where <删除条件>]
    public void clear() {
        String sql = "delete from";
    }

    //update <表名> set <列名=更新值> [where <更新条件>]
    public void update() {
        String sql = "update";
    }


    public void execute(String sql) {
        System.out.println("执行sql：" + sql);
    }

    public static void main(String[] args) {

        DBManage dbManage = new DBManage();
//        dbManage.queryConditions = new HashMap<>();
        dbManage.addQu(dbManage.id = "12345", dbManage.date = new Date());
        System.out.println(Arrays.toString(dbManage.queryConditions));
dbManage.query();
    }
}
