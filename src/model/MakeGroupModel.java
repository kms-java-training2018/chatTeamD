package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.MakeGroupBean;

/**
 * 新しくグループを作成するモデル。
 * @author masuda-keito
 *
 */
public class MakeGroupModel {
	/**
	 * グループ名の最大長
	 */
	final int GROUP_NAME_LENGTH = 30;

	/**
	 * sesUserNoが作成者になり
	 * 配列groupMemberNoの人々との
	 * グループを作成するメソッド。
	 * グループにはgroupNameという名前をつける。
	 * エラー情報はbeanに格納し返される。
	 * @param bean	……エラー情報を入れるbean
	 * @param groupName	……作るグループの名前
	 * @param groupMemberNo	……グループの参加者
	 * @param sesUserNo	……グループの作成者
	 * @return MakeGroupBean型にエラー情報を入れて返す。
	 */
	public MakeGroupBean makeGroup(MakeGroupBean bean, String groupName, String[] groupMemberNo, String sesUserNo) {
		// ボッチグループ判定
		int soloGroupFlag = 0;
		if (groupName.equals("")) {
			// グループ名が空
			bean.setErrorMsg("グループ名が空です");
			bean.setErrorFlag(1);
		} else if (groupName.length() > GROUP_NAME_LENGTH) {
			// グループ名が所定の文字数より長い
			bean.setErrorMsg("グループ名が規定の30文字より長いです");
			bean.setErrorFlag(1);
		} else if (groupMemberNo == null) {
			// グループメンバーがいない
			soloGroupFlag = 1;
		}
		if (bean.getErrorFlag() == 1) {
			// エラーページへ

		} else {
			/**
			 * 2)グループ登録処理
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
				 * group_noの自動採番
				 */
				// 最大値取得
				sb.append("SELECT ");
				sb.append(" MAX(group_no) ");
				sb.append("FROM ");
				sb.append(" m_group ");
				// SQL実行
				ResultSet rs = stmt.executeQuery(sb.toString());
				// 結果を代入
				// ArrayList<Integer> maxGroupNoList = new ArrayList<>();
				int maxGroupNo = 0;
				while (rs.next()) {
					// 値を取得、その次の番号に
					maxGroupNo = rs.getInt("MAX(GROUP_NO)") + 1;
				}
				// 初期化
				sb.delete(0, sb.length());
				/*
				 * グループ作成
				 */
				sb.append("INSERT INTO ");
				sb.append(" m_group ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", group_name ");
				sb.append(", regist_user_no ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + groupName + "' , ");
				sb.append(" '" + sesUserNo + "' , ");
				sb.append(" sysdate ");
				sb.append(" ) ");
				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				// 初期化
				sb.delete(0, sb.length());

				/**
				 * 3)グループ会員登録処理
				 */
				// 一連の処理なのでメソッド分けない
				// 自分
				sb.append("INSERT INTO ");
				sb.append(" t_group_info ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", user_no ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + sesUserNo + "' , ");
				sb.append(" sysdate ");
				sb.append(" ) ");
				// SQL実行
				ResultSet rs3 = stmt.executeQuery(sb.toString());
				// 初期化
				sb.delete(0, sb.length());

				// 他メンバー
				if (soloGroupFlag != 1) {
					// おひとり様グループじゃなければ他メンバーも追加する
					for (int i = 0; i < groupMemberNo.length; i++) {
						sb.append("INSERT INTO ");
						sb.append(" t_group_info ");
						sb.append(" ( ");
						sb.append(" group_no ");
						sb.append(", user_no ");
						sb.append(", regist_date ");
						sb.append(" ) ");
						sb.append("VALUES ");
						sb.append(" ( ");
						sb.append(" '" + maxGroupNo + "' , ");
						sb.append(" '" + groupMemberNo[i] + "' , ");
						sb.append(" sysdate ");
						sb.append(" ) ");
						// SQL実行
						ResultSet rs4 = stmt.executeQuery(sb.toString());
						// 初期化
						sb.delete(0, sb.length());

					}
				}

			} catch (SQLException e) {
				// エラーはすべてここにくる
				e.printStackTrace();
				bean.setErrorMsg("データベース登録時にエラーが発生しました");
				bean.setErrorFlag(1);
				// SQLの接続は絶対に切断
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}
}
