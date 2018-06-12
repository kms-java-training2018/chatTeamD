package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.GroupMessageBean;

public class GroupMessageModel {
	public ArrayList<GroupMessageBean> output(int groupNo) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		ArrayList<GroupMessageBean> output = new ArrayList<>();
		String exitUserName = "";
		String outFlag = "";
		int num = 0;
		int colorNo = 0;
		// CSSの種類
		final int css = 8;

		int j = 0;
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
			sb.append("M_USER.USER_NO ");
			sb.append("FROM ");
			sb.append("T_GROUP_INFO INNER JOIN M_USER ON T_GROUP_INFO.USER_NO = M_USER.USER_NO ");
			sb.append("WHERE ");
			sb.append("T_GROUP_INFO.GROUP_NO = " + groupNo);
			sb.append("AND T_GROUP_INFO.OUT_FLAG = '1'");
			ArrayList<String> outflag = new ArrayList<>();
			ResultSet rs2 = stmt.executeQuery(sb.toString());
			while (rs2.next()) {
				outflag.add(rs2.getString("USER_NO"));
			}

			sb.delete(0, sb.length());

			// -------------------------------------------------------------
			// SQL文作成
			// USER_NAME, MY_PAGE_TEXT取得
			sb.append("SELECT ");
			sb.append("USER_NAME, USER_NO, MESSAGE, MESSAGE_NO ");
			sb.append("FROM ");
			sb.append("T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO ");
			sb.append("INNER JOIN M_GROUP ON T_MESSAGE_INFO.TO_SEND_GROUP_NO = M_GROUP.GROUP_NO ");
			sb.append("WHERE ");
			sb.append("T_MESSAGE_INFO.TO_SEND_GROUP_NO = '" + groupNo + "' ");
			sb.append("AND T_MESSAGE_INFO.DELETE_FLAG = '0' ");
			sb.append("ORDER BY ");
			sb.append("T_MESSAGE_INFO.REGIST_DATE ");
			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			// -------------------------------------------------------------
			while (rs.next()) {
				int check = 0;
				GroupMessageBean bean = new GroupMessageBean();
				if (outflag == null || outflag.equals("")) {
					for (int i = 0; i < outflag.size(); i++) {
						if (rs.getString("USER_NO").equals(outflag.get(i))) {
							bean.setUserName("送信者不明");
						} else {
							bean.setUserName(rs.getString("USER_NAME"));
						}
						bean.setMessage(rs.getString("MESSAGE"));
						bean.setUserNo(rs.getString("USER_NO"));
						bean.setGroupNo(rs.getInt("MESSAGE_NO"));
					}
				} else {
					bean.setUserName(rs.getString("USER_NAME"));
					bean.setMessage(rs.getString("MESSAGE"));
					bean.setUserNo(rs.getString("USER_NO"));
					bean.setMessageNo(rs.getInt("MESSAGE_NO"));
				}

				for (int i = 0; i < output.size(); i++) {
					if (bean.getUserNo().equals(output.get(i).getUserNo())) {
						check = 1;
						bean.setColorNo(output.get(i).getColorNo());
						break;
					}
				}

				if(check == 0) {
					num = (int) (Math.random() * css +1);
					bean.setColorNo(num);
				}

				output.add(bean);

			}

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
		return output;
	}

	public GroupMessageBean send(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int groupNo = bean.getGroupNo();
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
			while (rs.next()) {
				max = rs.getInt("MAX(MESSAGE_NO)") + 1;
			}

			sb.setLength(0);

			// -------------------------------------------------------------
			// SQL文作成
			// USER_NAME, MY_PAGE_TEXT取得
			sb.append("INSERT INTO ");
			sb.append(
					"T_MESSAGE_INFO (MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_GROUP_NO, DELETE_FLAG, REGIST_DATE) ");
			sb.append("VALUES (");
			sb.append("'");
			sb.append(max);
			sb.append("', '");
			sb.append(userNo);
			sb.append("', '");
			sb.append(message);
			sb.append("', '");
			sb.append(groupNo);
			sb.append("', '");
			sb.append("0', sysdate)");
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

	public GroupMessageBean delete(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int messageNo = 0;
		messageNo = bean.getMessageNo();
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
			sb.append("UPDATE T_MESSAGE_INFO ");
			sb.append("SET DELETE_FLAG = '1' ");
			sb.append("WHERE MESSAGE_NO =");
			sb.append(messageNo);

			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

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

	public GroupMessageBean check(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int messageNo = 0;
		messageNo = bean.getMessageNo();
		String userNo = bean.getUserNo();
		int groupNo = bean.getGroupNo();
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
			sb.append("MESSAGE ");
			sb.append("FROM ");
			sb.append("T_MESSAGE_INFO ");
			sb.append("WHERE ");
			sb.append("SEND_USER_NO = ");
			sb.append("'" + userNo + "'");
			sb.append("AND ");
			sb.append("TO_SEND_GROUP_NO = ");
			sb.append("'" + groupNo + "'");

			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

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

	public GroupMessageBean author(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int messageNo;
		messageNo = bean.getMessageNo();
		int exitGroupNo = bean.getExitGroupNo();
		int registUserNo = 0;
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
			sb.append("REGIST_USER_NO ");
			sb.append("FROM ");
			sb.append("M_GROUP ");
			sb.append("WHERE ");
			sb.append("GROUP_NO = ");
			sb.append("'" + exitGroupNo + "'");

			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				registUserNo = rs.getInt("REGIST_USER_NO");
			}

			bean.setRegistUserNo(registUserNo);

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

	public GroupMessageBean exit(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		int exitGroupNo = bean.getExitGroupNo();
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
			sb.append("UPDATE ");
			sb.append("T_GROUP_INFO ");
			sb.append("SET ");
			sb.append("OUT_FLAG = '1' ");
			sb.append("WHERE ");
			sb.append("GROUP_NO = ");
			sb.append("'" + exitGroupNo + "'");
			sb.append("AND ");
			sb.append("USER_NO = ");
			sb.append("'" + userNo + "'");

			// -------------------------------------------------------------

			// SQL実行
			stmt.executeQuery(sb.toString());

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

	public GroupMessageBean groupName(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int groupNo = bean.getGroupNo();
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
			sb.append("GROUP_NAME, USER_NAME ");
			sb.append("FROM ");
			sb.append("M_GROUP INNER JOIN M_USER ");
			sb.append("ON ");
			sb.append("M_GROUP.REGIST_USER_NO ");
			sb.append("= ");
			sb.append("M_USER.USER_NO ");
			sb.append("WHERE ");
			sb.append("GROUP_NO ");
			sb.append("= ");
			sb.append(groupNo);

			// -------------------------------------------------------------

			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				bean.setGroupName(rs.getString("GROUP_NAME"));
				bean.setAuthorName(rs.getString("USER_NAME"));
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
		// -------------------------------------------------------------
		return bean;
	}

	public GroupMessageBean confirmation(GroupMessageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		int groupNo = bean.getGroupNo();
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
			sb.append("USER_NO ");
			sb.append("FROM ");
			sb.append("T_GROUP_INFO ");
			sb.append("WHERE ");
			sb.append("GROUP_NO ");
			sb.append("= ");
			sb.append(groupNo);
			sb.append(" ");
			sb.append("AND ");
			sb.append("OUT_FLAG = '0' ");
			// -------------------------------------------------------------

			ResultSet rs = stmt.executeQuery(sb.toString());

			ArrayList<String> list = new ArrayList<>();
			int i = 0;
			while (rs.next()) {
				list.add(rs.getString("USER_NO"));

			}
			if (!list.contains(bean.getMyNo())) {
				bean.setConMessage("エラー");
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
		// -------------------------------------------------------------
		return bean;
	}

}
