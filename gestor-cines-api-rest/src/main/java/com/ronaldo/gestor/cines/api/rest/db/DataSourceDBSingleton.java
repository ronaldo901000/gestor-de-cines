package com.ronaldo.gestor.cines.api.rest.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author ronaldo
 */
public class DataSourceDBSingleton {

       private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/cinesdb";
       private static final String USER = "root";
       private static final String PASSWORD = "123456789";

       private static DataSourceDBSingleton UNICA_INSTANCIA_DE_DATASOURCE;
       private DataSource datasource;

       private DataSourceDBSingleton() {
              try {
                     Class.forName("com.mysql.cj.jdbc.Driver");
                     PoolProperties p = new PoolProperties();
                     p.setUrl(URL_MYSQL);
                     p.setDriverClassName("com.mysql.cj.jdbc.Driver");
                     p.setUsername(USER);
                     p.setPassword(PASSWORD);
                     p.setJmxEnabled(true);
                     p.setTestWhileIdle(false);
                     p.setTestOnBorrow(true);
                     p.setValidationQuery("SELECT 1");
                     p.setTestOnReturn(false);
                     p.setValidationInterval(30000);
                     p.setTimeBetweenEvictionRunsMillis(30000);
                     p.setMaxActive(200);
                     p.setInitialSize(10);
                     p.setMaxWait(10000);
                     p.setRemoveAbandonedTimeout(60);
                     p.setMinEvictableIdleTimeMillis(30000);
                     p.setMinIdle(10);
                     p.setLogAbandoned(true);
                     p.setRemoveAbandoned(true);
                     p.setJdbcInterceptors(
                             "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                             + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
                     datasource = new DataSource(p);
              } catch (ClassNotFoundException e) {
                     e.printStackTrace();
              }
       }

       public static DataSourceDBSingleton getInstance() {
              if (UNICA_INSTANCIA_DE_DATASOURCE == null) {
                     UNICA_INSTANCIA_DE_DATASOURCE = new DataSourceDBSingleton();
              }

              return UNICA_INSTANCIA_DE_DATASOURCE;
       }

       public DataSource getDatasource() {
              return datasource;
       }

       public Connection getConnection() throws SQLException {
              return datasource.getConnection();
       }
}
