package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.AddGroupMemberBean;

public class AddGroupMemberModel {
	public ArrayList<AddGroupMemberBean> userList(int groupNo) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		ArrayList<AddGroupMemberBean> userList = new ArrayList<>();

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
			sb.append("USER_NAME, ");
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("M_USER ");
			sb.append("WHERE NOT USER_NO IN( ");
			sb.append("SELECT ");
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("T_GROUP_INFO ");
			sb.append("WHERE ");
			sb.append("GROUP_NO = ");
			sb.append(groupNo + " ");
			sb.append("AND NOT ");
			sb.append("OUT_FLAG = 1 ");
			sb.append(") ");
			sb.append("ORDER BY ");
			sb.append("USER_NO");

			// SQL実行

			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				AddGroupMemberBean bean = new AddGroupMemberBean();
				bean.setUserName(rs.getString("USER_NAME"));
				bean.setUserNo(rs.getString("USER_NO"));
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
	public AddGroupMemberBean check(AddGroupMemberBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		int userNo = Integer.parseInt(bean.getUserNo());
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
			sb.append("SELECT ");
			sb.append("USER_ID ");
			sb.append("FROM ");
			sb.append("M_USER inner join t_group_info on m_user.user_no = t_group_info.user_no ");
			sb.append("WHERE ");
			sb.append("T_GROUP_INFO.USER_NO =");
			sb.append(userNo);
			sb.append(" ");
			sb.append("AND ");
			sb.append("GROUP_NO = ");
			sb.append(groupNo);

			// SQL実行

			ResultSet rs = stmt.executeQuery(sb.toString());
			while(rs.next()) {
				bean.setResult(rs.getString("USER_ID"));
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
	public AddGroupMemberBean insert(AddGroupMemberBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		int userNo = Integer.parseInt(bean.getUserNo());
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
			sb.append("INSERT INTO ");
			sb.append("T_GROUP_INFO( ");
			sb.append("GROUP_NO, USER_NO, OUT_FLAG, REGIST_DATE");
			sb.append(") ");
			sb.append("VALUES( ");
			sb.append(groupNo);
			sb.append(", ");
			sb.append(userNo);
			sb.append(", ");
			sb.append("0");
			sb.append(", ");
			sb.append("SYSDATE)");

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

	public AddGroupMemberBean update(AddGroupMemberBean bean) {
		// 初期化
		StringBuilder sb = new StringBuilder();
		int userNo = Integer.parseInt(bean.getUserNo());
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
			sb.append("T_GROUP_INFO ");
			sb.append("SET ");
			sb.append("OUT_FLAG = '0' ");
			sb.append("WHERE ");
			sb.append("USER_NO = ");
			sb.append(userNo);
			sb.append(" ");
			sb.append("AND ");
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
