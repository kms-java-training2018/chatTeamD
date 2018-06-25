package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.WithdrawalBean;

public class WithdrawalModel {
	public ArrayList<WithdrawalBean> withdrawal(int groupNo) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		ArrayList<WithdrawalBean> userList = new ArrayList<>();

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
			sb.append("USER_ID, ");
			sb.append("USER_NAME, ");
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("M_USER ");
			sb.append("WHERE ");
			sb.append("USER_NO ");
			sb.append("IN ");
			sb.append("( ");
			sb.append("SELECT ");
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("T_GROUP_INFO ");
			sb.append("WHERE ");
			sb.append("GROUP_NO = ");
			sb.append("'" + groupNo + "' ");
			sb.append("AND ");
			sb.append("OUT_FLAG = '0' ");
			sb.append(")");
			sb.append("ORDER BY ");
			sb.append("USER_NO");


			// SQL実行

			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				WithdrawalBean bean = new WithdrawalBean();
				bean.setUserName(rs.getString("USER_NAME"));
				bean.setUserNo(Integer.parseInt(rs.getString("USER_NO")));
				userList.add(bean);
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

		return userList;
	}
}
