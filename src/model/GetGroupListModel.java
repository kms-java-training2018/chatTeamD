package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.GroupListBean;
import bean.UserListBean;

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
	public GroupListBean getGroupList(GroupListBean bean, String sesUserNo) {
		/**
		 *  4) 参加グループ一覧取得処理
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
			UserListBean uLBean = new UserListBean();
			GetUserListModel model = new GetUserListModel();
			uLBean = model.getUserList(uLBean, sesUserNo);
			/*
			 * 自分が参加しているグループ一覧取得
			 */
			ArrayList<Integer> groupNo = new ArrayList<>();
			ArrayList<String> groupName = new ArrayList<>();
			sb.append("SELECT ");
			sb.append(" A.group_no , B.group_name ");
			sb.append("FROM ");
			sb.append(" t_group_info A full outer join m_group B on A.group_no = B.group_no ");
			sb.append("WHERE ");
			sb.append(" A.user_no = '" + sesUserNo + "' ");
			// SQL実行
			ResultSet rs3 = stmt.executeQuery(sb.toString());
			// それぞれArrayListに入れる
			while (rs3.next()) {
				groupNo.add(rs3.getInt("GROUP_NO"));
				groupName.add(rs3.getString("GROUP_NAME"));
			}
			// sb初期化
			sb.delete(0, sb.length());
			// グループ0の場合メッセージ出す
			String groupNullMes = "";
			if (groupNo.isEmpty()) {
				groupNullMes = "グループに参加していません";
			}
			// beanのフィールドにset
			bean.setGroupNo(groupNo);
			bean.setGroupName(groupName);
			bean.setGroupNullMes(groupNullMes);
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

	/**
	 * bean内のユーザ一覧と引数のユーザ間で最新のDMを取得し、beanに入れるメソッド。
	 *
	 * @param bean	……最新のメッセージを取得したいユーザ一覧。入っていない場合sesUserNoを利用しgetUserListメソッドを行う
	 * @param sesUserNo	……一覧のユーザとメッセージをやり取りしたユーザ。
	 * @return	UserListBean型beanに最新メッセージを持たせて返す。
	 */
	public GroupListBean getGroupLatestMessage(GroupListBean bean, String sesUserNo) {

		return bean;
	}

}
