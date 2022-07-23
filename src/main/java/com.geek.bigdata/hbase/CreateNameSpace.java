package com.geek.bigdata.hbase;


import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public class CreateNameSpace {
    public static void createNamespace(Connection connection, String tablespace) throws IOException {
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        try {
            admin.getNamespaceDescriptor(tablespace);
            System.out.printf("Namespace [%s] already exists.\n", tablespace);
        } catch (NamespaceNotFoundException exception) {
            admin.createNamespace(NamespaceDescriptor.create(tablespace).build());
            System.out.printf("Create namespace: [%s] success.\n", tablespace);
        }
    }
}
