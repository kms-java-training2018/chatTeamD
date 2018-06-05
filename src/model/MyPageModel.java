package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.MyPageBean;

public class MyPageModel {

	/**
	 * @author hanawa-tomonori
	 * 自分のユーザ情報取得ロジック
	 */

	public MyPageBean output(MyPageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
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
			sb.append("USER_NAME, ");
			sb.append("MY_PAGE_TEXT ");
			sb.append("FROM ");
			sb.append("M_USER ");
			sb.append("WHERE ");
			sb.append(" user_id = '" + userId + "' ");
			// -------------------------------------------------------------

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			// -------------------------------------------------------------
			// 取得した情報をbeanへセット
			while (rs.next()) {
				bean.setMyPageText(rs.getString("MY_PAGE_TEXT"));
				bean.setUserName(rs.getString("USER_NAME"));
			}
			// レコードが取得できなければ、エラーメッセージをセット
			if (bean.getMyPageText() == "") {
				bean.setErrorMessage("レコードが取得できませんでした");
				return bean;
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
		return bean;
	}

	/**
	 * @author hanawa-tomonori
	 * 自分のユーザ情報更新ロジック
	 */

	public MyPageBean update(MyPageBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userId = bean.getUserId();
		String sendDispName = bean.getUserName();
		String sendMyPageText = bean.getMyPageText();
		bean.setErrorMessage("notError");
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
			// SQL作成
			if (sendDispName != "" && sendMyPageText != "") {

				// -------------------------------------------------------------
				// プロフィール情報更新
				// ・表示名(USER_NAME)
				// ・自己紹介文(MY_PAGE_TEXT)
				sb.append("UPDATE ");
				sb.append("M_USER ");
				sb.append("SET ");
				sb.append("USER_NAME = '" + sendDispName + "' ");
				sb.append(", MY_PAGE_TEXT = '" + sendMyPageText + "' ");
				sb.append("WHERE ");
				sb.append(" user_id = '" + userId + "' ");
				ResultSet rs = stmt.executeQuery(sb.toString());
				// -------------------------------------------------------------

				// SQL初期化
				sb.delete(0, sb.length());

				// -------------------------------------------------------------
				// 更新後の表示名(USER_NAME)取得
				sb.append("SELECT ");
				sb.append("USER_NAME ");
				sb.append("FROM ");
				sb.append("M_USER ");
				sb.append("WHERE ");
				sb.append(" user_id = '" + userId + "' ");
				rs = stmt.executeQuery(sb.toString());
				// -------------------------------------------------------------

				while(rs.next()) {
					// beanへ、表示名をセット
					bean.setUserName(rs.getString("USER_NAME"));
				}

			}
		} catch (SQLException e) {
			bean.setErrorMessage("文字数が多すぎます");

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