package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.UserListBean;

public class GetUserListModel {
	/**
	 * 登録されているユーザー一覧を取得するクラス
	 * 引数ありで自分を抜いたユーザー一覧を取得します
	 */
	public UserListBean getUserList(UserListBean bean,String sesUserNo) {
		int errorFlag = 0;
		/**
		 *  2）他会員一覧取得処理
		 */
		StringBuilder sb = new StringBuilder();
		/*
		 * DBログイン
		 */
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
			/*
			 * ユーザ一覧取得
			 */
			sb.append("SELECT ");
			sb.append(" user_no ");
			sb.append(", user_id ");
			sb.append(", user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append(" ORDER BY ");
			sb.append(" user_no ");
			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());
			// それぞれArrayListに入れる
			ArrayList<Integer> userNo = new ArrayList<>();
			ArrayList<String> userID = new ArrayList<>();
			ArrayList<String> userName = new ArrayList<>();
			while (rs.next()) {
				// ログインしている自分自身は除く
				int check = rs.getInt("USER_NO");
				int check2 = Integer.parseInt(sesUserNo);
				if (check == check2) {
					// 自分なので追加しない
				} else {
					// 自分以外なのでリストに追加
					userNo.add(rs.getInt("USER_NO"));
					userID.add(rs.getString("USER_ID"));
					userName.add(rs.getString("USER_NAME"));
				}
			}
			// beanのフィールドにset
			bean.setUserNo(userNo);
			bean.setUserNo(userNo);
			bean.setUserName(userName);
			// 初期化
			sb.delete(0, sb.length());
		} catch (SQLException e) {
			// エラーはすべてここにくる
			e.printStackTrace();
			errorFlag = 1;
			bean.setErrorFlag(errorFlag);
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
