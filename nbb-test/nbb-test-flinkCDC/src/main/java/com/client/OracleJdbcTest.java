package com.client;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleJdbcTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //注册驱动
        Class.forName("oracle.jdbc.driver.OracleDriver");
        //2.获取连接
        String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
        String username = "root";
        String password = "root";
        Connection conn = DriverManager.getConnection(url, username, password);
        String sql = "select * from PATIENT";
        CallableStatement state = conn.prepareCall(sql);


        List<Map<String, Object>> list = new ArrayList();
        ResultSet rs = state.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }

        System.out.println(list);
        state.close();
        conn.close();
    }
}
