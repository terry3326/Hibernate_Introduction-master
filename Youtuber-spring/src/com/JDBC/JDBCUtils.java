package com.JDBC;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description 操作数据库的工具类
 * @author shkstart Email:shkstart@126.com
 * @version
 * @date 上午9:10:02
 *
 */
@Component
public class JDBCUtils {
	@Autowired
	private DruidDataSource dataSource;

	/**
	 *
	 * @Title: update @Description: 通用增刪改操作 @param sql @param args @return
	 *         void @throws
	 */
	public  int update(String sql, Object... args) {// sql中占位符的个数与可变形参的长度相同！
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 1.获取数据库的连接
			conn = dataSource.getConnection();
			// 2.预编译sql语句，返回PreparedStatement的实例
			ps = conn.prepareStatement(sql);
			// 3.填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);// 小心参数声明错误！！
			}
			// 4.执行
			int executeUpdate = ps.executeUpdate();
			return executeUpdate;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5.资源的关闭
			closeResource(conn, ps);

		}
		return 0;

	}

	/**
	 *
	 * @Title: getForList @Description: 針對於不同的表的通用的查询操作，返回表中的多条记录 @param <T> @param
	 * clazz @param sql @param args @return @return List<T> @throws
	 */
	public  <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			rs = ps.executeQuery();
			// 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			// 创建集合对象
			ArrayList<T> list = new ArrayList<T>();
			while (rs.next()) {
				T t = clazz.newInstance();// 獲取當前類的public空參構造器
				// 处理结果集一行数据中的每一个列:给t对象指定的属性赋值
				for (int i = 0; i < columnCount; i++) {
					// 获取列值
					Object columValue = rs.getObject(i + 1);

					// 获取每个列的列名
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);

					// 给t对象指定的columnName属性，赋值为columValue：通过反射
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				list.add(t);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(conn, ps, rs);

		}

		return null;
	}

	/**
	 * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
	 * @author shkstart
	 * @date 上午11:42:23
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public  <T> T getInstance( Class<T> clazz, String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			rs = ps.executeQuery();
			// 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {
				T t = clazz.newInstance();
				// 处理结果集一行数据中的每一个列
				for (int i = 0; i < columnCount; i++) {
					// 获取列值
					Object columValue = rs.getObject(i + 1);

					// 获取每个列的列名
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);

					// 给t对象指定的columnName属性，赋值为columValue：通过反射
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(conn, ps, rs);

		}

		return null;
	}



//	/**
//	 *
//	 * @Description 获取数据库的连接
//	 * @author shkstart
//	 * @date 上午9:11:23
//	 * @return
//	 * @throws Exception
//	 */
//	public  Connection getConnection() throws Exception {
//
//		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
////		FileInputStream is = new FileInputStream("jdbc.properties");
//		Properties pros = new Properties();
//		pros.load(is);
//
//		String user = pros.getProperty("user");
//		String password = pros.getProperty("password");
//		String url = pros.getProperty("url");
//		String driverClass = pros.getProperty("driverClass");
//
//		Class.forName(driverClass);
//
//		Connection conn = DriverManager.getConnection(url, user, password);
//		return conn;
//	}
//	public static Connection getConnection() {
//		Connection connect = null;
//		try {
//			// load MySQL JDBC Driver
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			// Setup the connection with the DB
//			connect = DriverManager.getConnection("jdbc:mysql://192.168.56.101/test", "terry", "89835487");
////			 connect = DriverManager.getConnection("jdbc:mysql://172.16.123.112/demo?serverTimezone=UTC&useSSL=false","julian","admin123");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return connect;
//	}

	/**
	 *
	 * @Description 關閉连接和Statement的操作
	 * @author shkstart
	 * @date 上午9:12:40
	 * @param conn
	 * @param ps
	 */
	public void closeResource(Connection conn, Statement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @Description 關閉资源操作
	 * @author shkstart
	 * @date 上午10:21:15
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public  void closeResource(Connection conn, Statement ps, ResultSet rs) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
