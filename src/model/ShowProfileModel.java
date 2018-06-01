package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.ShowProfileBean;

public class ShowProfileModel {
	public ShowProfileBean output(ShowProfileBean bean) {

		// -------------------------------------------------------------
		// 初期化
		StringBuilder sb = new StringBuilder();
		String userNo = bean.getUserNo();
		//-------------------------------------------------------------

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
			// SQL文作成
			// USER_NAME, MY_PAGE_TEXT取得
			sb.append("SELECT ");
			sb.append("USER_NAME, ");
			sb.append("MY_PAGE_TEXT ");
			sb.append("FROM ");
			sb.append("M_USER ");
			sb.append("WHERE ");
			sb.append("USER_NO = '" + userNo + "' ");

			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());

			// -------------------------------------------------------------
			// 取得した情報をbeanへセット
			// レコードが取得できなければ、エラーメッセージをセット
			while (rs.next()) {
				bean.setMyPageText(rs.getString("MY_PAGE_TEXT"));
				bean.setUserName(rs.getString("USER_NAME"));
			}

			// -------------------------------------------------------------

		} catch (SQLException e) {
			bean.setErrorMessage("レコードが取得できませんでした");
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
