package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.UserListBean;

/**
 * 登録されているユーザー一覧を取得するモデル。
 *
 * @author masuda-keito
 *
 */
public class GetUserListModel {
	/**
	 *  引数beanに引数sesUserNo以外のユーザ一覧をセットするメソッド。
	 *
	 * @param bean	……UserListBean型。ここから何か呼んでいるわけではないから内部で作れば不要？
	 * @param sesUserNo	……リストから外したいユーザー。
	 * @return	sesUserNo以外のユーザをUserListBean型に格納して返す
	 */
	public UserListBean getUserList(UserListBean bean, String sesUserNo) {
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
			bean.setUserID(userID);
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

	/**
	 * bean内のユーザ一覧と引数のユーザ間で最新のDMを取得し、beanに入れるメソッド。
	 *
	 * @param bean	……最新のメッセージを取得したいユーザ一覧。入っていない場合sesUserNoを利用しgetUserListメソッドを行う
	 * @param sesUserNo	……一覧のユーザとメッセージをやり取りしたユーザ。
	 * @return	UserListBean型beanに最新メッセージを持たせて返す。
	 */
	public UserListBean getUserLatestMessage(UserListBean bean, String sesUserNo) {

		// beanにユーザー一覧入っているか確認
		if (bean.getUserID() == null) {
			// 入っていない場合
			// 取得する
			GetUserListModel model = new GetUserListModel();
			bean = model.getUserList(bean, sesUserNo);
		}
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
			ArrayList<String> directMessage = new ArrayList<>();
			for (int i = 0; i < bean.getUserNo().size(); i++) {
				int uN = bean.getUserNo().get(i);
				sb.append("SELECT ");
				sb.append(" message ");
				sb.append("FROM ");
				sb.append(" t_message_info ");
				sb.append("WHERE ");
				sb.append(" regist_date = ( ");
				sb.append(" SELECT ");
				sb.append(" MAX(regist_date) ");
				sb.append("FROM ");
				sb.append(" t_message_info ");
				sb.append("WHERE ");
				sb.append(" ( send_user_no = '" + sesUserNo + "' ");
				sb.append(" or send_user_no = '" + uN + "' ) ");
				sb.append(" AND ( to_send_user_no = '" + sesUserNo + "' ");
				sb.append(" or to_send_user_no = '" + uN + "' )");
				sb.append(" AND delete_flag = '0' )");
				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				if (rs2.next()) {
					// メッセージあり
					// そのままArrayListに入れる
					directMessage.add(rs2.getString("Message"));
				} else {
					// メッセージなし
					// 会話を始めましょう！
					directMessage.add("会話を始めましょう!");
				}
				// 初期化
				sb.delete(0, sb.length());
			}
			// beanのフィールドにset
			bean.setDirectMessage(directMessage);
		} catch (SQLException e) {
			// エラーはすべてここにくる
			e.printStackTrace();
			bean.setErrorFlag(1);
			// SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		// sb初期化
		sb.delete(0, sb.length());

		return bean;
	}

}
