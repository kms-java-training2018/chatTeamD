package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.GroupListBean;

/**
 * 登録されているグループ一覧を取得するモデル。
 *
 * @author masuda-keito
 *
 */
public class GetGroupListModel {
	/**
	 *  引数beanに引数sesUserNoの参加しているグループ一覧をセットするメソッド。
	 *
	 * @param bean	……GroupListBean型。ここから何か呼んでいるわけではないから内部で作れば不要？
	 * @param sesUserNo	……参加しているグループを探したいユーザ。
	 * @return	sesUserNoの参加しているグループ一覧をUserListBean型に格納して返す
	 */
	public ArrayList<GroupListBean> getGroupList(String sesUserNo) {
		/**
		 *  参加グループ一覧取得処理
		 */
		ArrayList<GroupListBean> beanList = new ArrayList<>();
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
			 * 自分が参加しているグループ一覧取得
			 */
			sb.append("SELECT ");
			sb.append(" A.group_no , B.group_name ");
			sb.append("FROM ");
			sb.append(" t_group_info A full outer join m_group B on A.group_no = B.group_no ");
			sb.append("WHERE ");
			sb.append(" A.user_no = '" + sesUserNo + "' ");
			sb.append("AND ");
			sb.append(" out_flag = '0' ");
			sb.append(" ORDER BY A.REGIST_DATE ");
			// SQL実行
			ResultSet rs3 = stmt.executeQuery(sb.toString());
			while (rs3.next()) {
				// 結果beanに入れた後ArrayListに入れる
				GroupListBean bean = new GroupListBean();
				bean.setGroupNo(rs3.getInt("GROUP_NO"));
				bean.setGroupName(rs3.getString("GROUP_NAME"));
				beanList.add(bean);
			}
			// グループ0の場合メッセージ出す
			if (beanList.isEmpty()) {
				GroupListBean bean = new GroupListBean();
				bean.setGroupNullMes("グループに参加していません");
				beanList.add(bean);
			}
		} catch (SQLException e) {
			// SQLエラーはすべてここにくる
			e.printStackTrace();
			// beanList初期化
			beanList.clear();
			// エラー情報入れたbeanだけセット
			GroupListBean bean = new GroupListBean();
			bean.setErrorFlag(1);
			beanList.add(bean);
		} finally {
			// SQLの接続は絶対に切断
			try {
				// ここで接続切断に失敗する（そもそも接続できてない場合）とreturnされず外に出るみたい
				// その場合はbeanListがNullになるかも知れないのでサーブレットのエラー処理に任せる
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return beanList;
	}

	/**
	 * bean内のグループ一覧と引数のユーザ間での最新メッセージを取得し、beanに入れるメソッド。
	 *
	 * @param bean	……最新のメッセージを取得したいグループ一覧。入っていない場合sesUserNoを利用しgetGroupListメソッドを行う
	 * @param sesUserNo	……グループ一覧とメッセージをやり取りしたユーザ。
	 * @return	UserListBean型beanに最新メッセージを持たせて返す。
	 */
	public ArrayList<GroupListBean> getGroupLatestMessage(ArrayList<GroupListBean> beanList, String sesUserNo) {
		/*
		 * 最新メッセージ一覧取得
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
			for (int i = 0; i < beanList.size(); i++) {
				int gN = beanList.get(i).getGroupNo();
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
				sb.append(" to_send_group_no = '" + gN + "' ");
				sb.append(" AND delete_flag =  '0' )");
				sb.append(" ORDER BY REGIST_DATE ");
				// SQL実行
				ResultSet rs4 = stmt.executeQuery(sb.toString());
				if (rs4.next()) {
					// メッセージあり
					// そのまま対応するbeanに入れた後にArrayListに入れる
					beanList.get(i).setGroupMessage(rs4.getString("Message"));
				} else {
					// データなし
					// 会話を始めましょう！
					beanList.get(i).setGroupMessage("会話を始めましょう!");
				}
				// ループの為初期化
				sb.delete(0, sb.length());
			}
		} catch (SQLException e) {
			// SQLエラーはすべてここにくる
			e.printStackTrace();
			// beanList初期化
			beanList.clear();
			// エラー情報入れたbeanだけセット
			GroupListBean bean = new GroupListBean();
			bean.setErrorFlag(1);
			beanList.add(bean);
		} finally {
			try {
				// ここで接続切断に失敗する（そもそも接続できてない場合）とreturnされず外に出るみたい
				// その場合はbeanListがNullになるかも知れないのでサーブレットのエラー処理に任せる
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return beanList;
	}

}
