package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.SignUpBean;

/**
 * ログイン画面ビジネスロジック
 */
public class SignUpModel {

	public SignUpBean signup(SignUpBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
		String password = bean.getUserPw();
		String userName = bean.getUserName();
		int maxNum = 0;

		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";
		// JDBCドライバーのロード
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 接続作成
		try {

			conn = DriverManager.getConnection(url, user, dbPassword);

			Statement stmt = conn.createStatement();


			// 自動采番
			sb.append("SELECT ");
			sb.append("MAX(");
			sb.append("USER_NO) ");
			sb.append("FROM ");
			sb.append("M_USER ");

			ResultSet max = stmt.executeQuery(sb.toString());
			while(max.next()) {
				maxNum = max.getInt("MAX(USER_NO)") + 1;
			}

			sb.delete(0, sb.length());


			// SQL作成
			sb.append("INSERT INTO ");
			sb.append("M_USER ");
			sb.append("( ");
			sb.append("USER_NO, USER_ID, PASSWORD, USER_NAME, REGIST_DATE ");
			sb.append(") ");
			sb.append("VALUES ");
			sb.append("( ");
			sb.append("'" + maxNum + "', ");
			sb.append("'"+ userId +"', ");
			sb.append("'" + password + "', ");
			sb.append("'" + userName + "', ");
			sb.append("SYSDATE " );
			sb.append(") ");


			// SQL実行

			ResultSet rs = stmt.executeQuery(sb.toString());

		} catch (SQLException e) {
			e.printStackTrace();
			// SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return bean;
	}
	public SignUpBean userList(SignUpBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
		String db = "";

		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";
		// JDBCドライバーのロード
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 接続作成
		try {

			conn = DriverManager.getConnection(url, user, dbPassword);

			Statement stmt = conn.createStatement();


			// 自動采番
			sb.append("SELECT ");
			sb.append("USER_NAME ");
			sb.append("FROM ");
			sb.append("M_USER ");
			sb.append("Where ");
			sb.append("user_id = ");
			sb.append("'"+userId+ "'");

			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				bean.setResult(rs.getString("USER_NAME"));
				db = rs.getString("USER_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return bean;
	}
}
