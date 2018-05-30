package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// loginからはpostで送られてくる

		// パラメータチェック
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		// 2）他会員一覧取得処理
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

				} else {

					userNo.add(rs.getInt("USER_NO"));
					userID.add(rs.getString("USER_ID"));
					userName.add(rs.getString("USER_NAME"));
				}

			}
			// リクエストに送る
			req.setAttribute("userlist", userName);
			req.setAttribute("userNo", userNo);
			// 初期化
			sb.delete(0, sb.length());

			// 3）最新メッセージ取得処理
			/*
			 * メッセージ一覧取得
			 */
			ArrayList<String> directMessage = new ArrayList<>();
			for (int i = 0; i < userNo.size(); i++) {
				int uN = userNo.get(i);
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
				sb.append(" send_user_no = '" + sesUserNo + "' ");
				sb.append(" AND to_send_user_no = '" + uN + "' )");
				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				if (rs2.next()) {
					// データあり
					// そのままArrayListに入れる
					directMessage.add(rs2.getString("Message"));
				} else {
					// データなし
					// 会話を始めましょう！
					directMessage.add("会話を始めましょう");
				}
				// sb初期化
				sb.delete(0, sb.length());
			}
			// リクエストに送る
			req.setAttribute("directMessage", directMessage);

			// 4) 参加グループ一覧取得処理
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
			// リクエストに送る
			req.setAttribute("grouplist", groupName);
			req.setAttribute("groupNO", groupNo);
			/*
			 * メッセージ一覧取得
			 */
			ArrayList<String> groupMessage = new ArrayList<>();
			for (int i = 0; i < groupNo.size(); i++) {
				int gN = groupNo.get(i);
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
				sb.append(" to_send_group_no = '" + gN + "' )");
				// SQL実行
				ResultSet rs4 = stmt.executeQuery(sb.toString());
				if (rs4.next()) {
					// データあり
					// そのままArrayListに入れる
					groupMessage.add(rs4.getString("MESSAGE"));
				} else {
					// データなし
					// 会話を始めましょう！
					groupMessage.add("会話を始めましょう");
				}
				// sb初期化
				sb.delete(0, sb.length());
			}
			// リクエストに送る
			req.setAttribute("groupMessage", groupMessage);

		} catch (SQLException e) {
			// アクセスできるかで出るエラー
			e.printStackTrace();
			// SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 出力
		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}

}