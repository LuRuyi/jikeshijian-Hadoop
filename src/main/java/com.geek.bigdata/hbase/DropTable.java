package com.geek.bigdata.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

public class DropTable {
    public static void drop(Connection connection, String tableName) throws IOException {
        Admin admin = connection.getAdmin();
        if (admin.isTableDisabled(TableName.valueOf(tableName))) {
            System.out.printf("The table [%s] does not exists.\n", tableName);
            return;
        }
        if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
            admin.disableTable(TableName.valueOf(tableName));
        }
        admin.deleteTable(TableName.valueOf(tableName));
        System.out.printf("Delete table [%s] success.\n", tableName);
    }
}
