package cn.jike.hbase;

import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

public class HbaseTest {
    public static void main(String[] args) throws IOException {
        Connection connection = HbaseInit.createConnection();
        // create namespace luruyi
        CreateNameSpace.createNamespace(connection, "luruyi");
        // create table luruyi:student
        CreateTable.create(connection, "luruyi:student", "name", "info", "score");
        // insert data to table
        InsertRow.insert(connection, "luruyi:student", "stu1", "name", "", "Tom");
        InsertRow.insert(connection, "luruyi:student", "stu1", "info", "student_id", "20210000000001");
        InsertRow.insert(connection, "luruyi:student", "stu1", "info", "class", "1");
        InsertRow.insert(connection, "luruyi:student", "stu1", "score", "understanding", "75");
        InsertRow.insert(connection, "luruyi:student", "stu1", "score", "programming", "82");

        InsertRow.insert(connection, "luruyi:student", "stu2", "name", "", "Jerry");
        InsertRow.insert(connection, "luruyi:student", "stu2", "info", "student_id", "20210000000002");
        InsertRow.insert(connection, "luruyi:student", "stu2", "info", "class", "1");
        InsertRow.insert(connection, "luruyi:student", "stu2", "score", "understanding", "85");
        InsertRow.insert(connection, "luruyi:student", "stu2", "score", "programming", "67");


        InsertRow.insert(connection, "luruyi:student", "stu3", "name", "", "Jack");
        InsertRow.insert(connection, "luruyi:student", "stu3", "info", "student_id", "20210000000003");
        InsertRow.insert(connection, "luruyi:student", "stu3", "info", "class", "2");
        InsertRow.insert(connection, "luruyi:student", "stu3", "score", "understanding", "80");
        InsertRow.insert(connection, "luruyi:student", "stu3", "score", "programming", "80");

        InsertRow.insert(connection, "luruyi:student", "stu4", "name", "", "Rose");
        InsertRow.insert(connection, "luruyi:student", "stu4", "info", "student_id", "20210000000004");
        InsertRow.insert(connection, "luruyi:student", "stu4", "info", "class", "2");
        InsertRow.insert(connection, "luruyi:student", "stu4", "score", "understanding", "60");
        InsertRow.insert(connection, "luruyi:student", "stu4", "score", "programming", "61");

        InsertRow.insert(connection, "luruyi:student", "stu5", "name", "", "Luruyi");
        InsertRow.insert(connection, "luruyi:student", "stu5", "info", "student_id", "G20220735040057");
        InsertRow.insert(connection, "luruyi:student", "stu5", "info", "class", "3");
        InsertRow.insert(connection, "luruyi:student", "stu5", "score", "understanding", "100");
        InsertRow.insert(connection, "luruyi:student", "stu5", "score", "programming", "100");

        // select table
        SelectTable.scan(connection, "luruyi:student");
        // get record by row key
        SelectTable.getRecordByRowKey(connection, "luruyi:student", "stu1");

        // delete data by row key
        DeleteRow.deleteRow(connection, "luruyi:student", "stu1");

        // delete table
        DropTable.drop(connection, "luruyi:student");

    }
}
