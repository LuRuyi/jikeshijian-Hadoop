package com.geek.bigdata.hbase;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;
import java.util.Arrays;

public class CreateTable {
    public static void create(Connection connection, String tableName, String... columnFamilies) throws IOException {
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        if (tableName == null || columnFamilies == null) {
            return;
        }
        if (admin.tableExists(tableName)) {
            System.out.printf("Table [%s] already exists.\n", tableName);
        } else {
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
            for (String columnFamily : columnFamilies) {
                if (columnFamily == null)
                    continue;
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(columnFamily);
                columnDescriptor.setMaxVersions(1);
                table.addFamily(columnDescriptor);
            }
            admin.createTable(table);
            System.out.printf("Create table: [%s] success. Column family: %s \n", table, Arrays.toString(columnFamilies));
        }
    }
}
