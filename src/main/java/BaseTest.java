import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;

public class BaseTest {

    public static void main(String[] args) throws IOException {
        // 建立连接
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "127.0.0.1");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.master", "127.0.0.1:60000");
        Connection conn = ConnectionFactory.createConnection(configuration);
        Admin admin = conn.getAdmin();

        TableName tableName = TableName.valueOf("user_t");
//        String colFamily = "User";
        ArrayList<String> colFamilies = new ArrayList<>();
        colFamilies.add("info");
        colFamilies.add("score");

//        int rowKey = 1;

        // 建表
        if (admin.tableExists(tableName)) {
            System.out.println("Table already exists");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for(String colFamily:colFamilies) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(colFamily);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
            System.out.println("Table create successful");
        }

        // 插入数据
        Put put = new Put(Bytes.toBytes("Jerry")); // row key
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("student_id"), Bytes.toBytes("2021000001")); // col1
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes("1")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("understanding"), Bytes.toBytes("75")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("programming"), Bytes.toBytes("82")); // col2

        conn.getTable(tableName).put(put);
        System.out.println("Data insert success");

        put = new Put(Bytes.toBytes("Tom")); // row key
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("student_id"), Bytes.toBytes("2021000002")); // col1
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes("1")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("understanding"), Bytes.toBytes("85")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("programming"), Bytes.toBytes("67")); // col2

        conn.getTable(tableName).put(put);
        System.out.println("Data insert success");

        put = new Put(Bytes.toBytes("Jack")); // row key
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("student_id"), Bytes.toBytes("2021000003")); // col1
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes("2")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("understanding"), Bytes.toBytes("80")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("programming"), Bytes.toBytes("80")); // col2

        conn.getTable(tableName).put(put);
        System.out.println("Data insert success");

        put = new Put(Bytes.toBytes("Rose")); // row key
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("student_id"), Bytes.toBytes("2021000004")); // col1
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes("2")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("understanding"), Bytes.toBytes("60")); // col2
        put.addColumn(Bytes.toBytes("score"), Bytes.toBytes("programming"), Bytes.toBytes("61")); // col2

        conn.getTable(tableName).put(put);
        System.out.println("Data insert success");

        put = new Put(Bytes.toBytes("Damon")); // row key
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("student_id"), Bytes.toBytes("20210760020018")); // col1
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes("1")); // col2

        conn.getTable(tableName).put(put);
        System.out.println("Data insert success");
//        // 查看数据
//        Get get = new Get(Bytes.toBytes(rowKey));
//        if (!get.isCheckExistenceOnly()) {
//            Result result = conn.getTable(tableName).get(get);
//            for (Cell cell : result.rawCells()) {
//                String colName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
//                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
//                System.out.println("Data get success, colName: " + colName + ", value: " + value);
//            }
//        }
//
//        // 删除数据
//        Delete delete = new Delete(Bytes.toBytes(rowKey));      // 指定rowKey
//        conn.getTable(tableName).delete(delete);
//        System.out.println("Delete Success");

        // 删除表
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("Table Delete Successful");
        } else {
            System.out.println("Table does not exist!");
        }
    }
}
