package com.client;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * https://blog.csdn.net/qq_36039236/article/details/124235751
 *
 * oracle安装前置条件
 * -- 启用日志归档（类似于mysql的binLog）
 * alter system set db_recovery_file_dest_size = 10G;
 * alter system set db_recovery_file_dest = 'E:\app2\oracle11g\dbhome\database\oracle-data-test' scope=spfile;
 * shutdown immediate;
 * startup mount;
 * alter database archivelog;
 * alter database open;
 *
 * -- 检查日志归档是否开启
 * archive log list;
 *
 * -- 为捕获的数据库启用补充日志记录,以便数据更改捕获更改的数据库行之前的状态,下面说明了如何在数据库级别进行配置。
 * ALTER DATABASE ADD SUPPLEMENTAL LOG DATA;
 *
 * -- 创建表空间my_ts
 * CREATE TABLESPACE my_ts DATAFILE 'E:\app2\oracle11g\dbhome\database\my_ts.dbf' SIZE 25M REUSE AUTOEXTEND ON MAXSIZE UNLIMITED;
 *
 * -- 创建用户hupeng（密码为hupeng）my_ts
 * CREATE USER hupeng IDENTIFIED BY hupeng DEFAULT TABLESPACE my_ts QUOTA UNLIMITED ON my_ts;
 *
 * -- 授予hupeng用户dba的权限
 * grant connect,resource,dba to hupeng;
 *
 * -- 并授予权限
 * GRANT CREATE SESSION TO hupeng;
 * GRANT SELECT ON V_$DATABASE to hupeng;
 * GRANT FLASHBACK ANY TABLE TO hupeng;
 * GRANT SELECT ANY TABLE TO hupeng;
 * GRANT SELECT_CATALOG_ROLE TO hupeng;
 * GRANT EXECUTE_CATALOG_ROLE TO hupeng;
 * GRANT SELECT ANY TRANSACTION TO hupeng;
 * GRANT EXECUTE ON SYS.DBMS_LOGMNR TO hupeng;
 * GRANT SELECT ON V_$LOGMNR_CONTENTS TO hupeng;
 * GRANT CREATE TABLE TO hupeng;
 * GRANT LOCK ANY TABLE TO hupeng;
 * GRANT ALTER ANY TABLE TO hupeng;
 * GRANT CREATE SEQUENCE TO hupeng;
 * GRANT EXECUTE ON DBMS_LOGMNR TO hupeng;
 * GRANT EXECUTE ON DBMS_LOGMNR_D TO hupeng;
 * GRANT SELECT ON V_$LOG TO hupeng;
 * GRANT SELECT ON V_$LOG_HISTORY TO hupeng;
 * GRANT SELECT ON V_$LOGMNR_LOGS TO hupeng;
 * GRANT SELECT ON V_$LOGMNR_CONTENTS TO hupeng;
 * GRANT SELECT ON V_$LOGMNR_PARAMETERS TO hupeng;
 * GRANT SELECT ON V_$LOGFILE TO hupeng;
 * GRANT SELECT ON V_$ARCHIVED_LOG TO hupeng;
 * GRANT SELECT ON V_$ARCHIVE_DEST_STATUS TO hupeng;
 */
public class OracleCDCOracleClient {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.disableOperatorChaining();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        tableEnv.executeSql("CREATE TABLE student_info (\n" +
                "     SID INT NOT NULL,\n" +                   // 注意字段名要大写
                "     SNAME STRING,\n" +
                "     SEX STRING,\n" +
                "     PRIMARY KEY(SID) NOT ENFORCED\n" +
                "     ) WITH (\n" +
                "     'connector' = 'oracle-cdc',\n" +
                "     'hostname' = 'localhost',\n" +
                "     'port' = '1521',\n" +
                "     'username' = 'hupeng',\n" +
                "     'password' = 'hupeng',\n" +
                "     'database-name' = 'orcl',\n" +
                "     'schema-name' = 'HUPENG',\n" +           // 注意这里要大写
                "     'table-name' = 'STUDENT_INFO',\n" +
                "     'debezium.log.mining.continuous.mine'='true',\n"+
                "     'debezium.log.mining.strategy'='online_catalog',\n" +
                "     'debezium.database.tablename.case.insensitive'='false',\n"+
                "     'scan.startup.mode' = 'initial')");

        TableResult tableResult = tableEnv.executeSql("select * from student_info");
        tableResult.print();

        System.out.println("cao nima ");
        env.execute();
    }
}
