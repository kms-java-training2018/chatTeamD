package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.GroupMessageBean;

public class GroupMessageModel {
	public GroupMessageBean output(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
		String groupNo = bean.getGroupNo();
		String myPageText = "";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
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

			// -------------------------------------------------------------
			// SQL文作成
			// USER_NAME, MY_PAGE_TEXT取得
			sb.append("SELECT ");
			sb.append("USER_NAME, MESSAGE ");
			sb.append("FROM ");
			sb.append("T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO ");
			sb.append("INNER JOIN M_GROUP ON T_MESSAGE_INFO.TO_SEND_GROUP_NO = M_GROUP.GROUP_NO ");
			sb.append("WHERE ");
			sb.append("T_MESSAGE_INFO.TO_SEND_GROUP_NO = '" + groupNo + "' ");
			sb.append("AND T_MESSAGE_INFO.DELETE_FLAG = '0' ");
			sb.append("ORDER BY ");
			sb.append("MESSAGE_NO ");
			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());
			ArrayList<String> userName = new ArrayList<>();
			ArrayList<String> message = new ArrayList<>();
			// -------------------------------------------------------------
			// 取得した情報をbeanへセット
			while (rs.next()) {
				userName.add(rs.getString("USER_NAME"));
				message.add(rs.getString("MESSAGE"));
			}

			bean.setListUserName(userName);
			bean.setListMessage(message);

			// -------------------------------------------------------------

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
		// -------------------------------------------------------------
		return bean;
	}

	public GroupMessageBean send(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
		String groupNo = bean.getGroupNo();
		String myPageText = "";
		int max = 0;
		String userNo = bean.getUserNo();
		String message = bean.getMessage();
		// -------------------------------------------------------------

		// -------------------------------------------------------------
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

			sb.append("SELECT ");
			sb.append("MAX(");
			sb.append("MESSAGE_NO) ");
			sb.append("FROM ");
			sb.append("T_MESSAGE_INFO ");

			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				max = rs.getInt("MAX(MESSAGE_NO)") + 1;
			}


			// -------------------------------------------------------------
			// SQL文作成
			// USER_NAME, MY_PAGE_TEXT取得
			sb.append("INSERT INTO ");
			sb.append("T_MESSAGE_INFO (");
			sb.append("MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_GROUP_NO, DELETE_FLAG, REGIST_DATE ");
			sb.append(") ");
			sb.append("VALUES (");
			sb.append("'" +max +"', '" + userNo + "', '" + message + "', '" + groupNo +"', '0', 'SYSDATE'");
			// -------------------------------------------------------------

			// SQL実行
			rs = stmt.executeQuery(sb.toString());

			// -------------------------------------------------------------

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
		// -------------------------------------------------------------
		return bean;
	}

}
