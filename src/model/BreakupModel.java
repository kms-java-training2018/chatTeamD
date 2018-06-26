package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.BreakupBean;

public class BreakupModel {
	public BreakupBean breakup(BreakupBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		int groupNo = bean.getGroupNo();

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
			sb.append("UPDATE ");
			sb.append("M_GROUP ");
			sb.append("SET ");
			sb.append("DELETE_FLAG = '1'");
			sb.append("WHERE ");
			sb.append("GROUP_NO = ");
			sb.append(groupNo);


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
}
