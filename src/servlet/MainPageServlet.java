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

import bean.SessionBean;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// loginからはpostで送られてくる

		// パラメータチェック
		SessionBean sessionBean = new SessionBean();
		String sesUserNo = sessionBean.getUserNo();

		// 2）他会員一覧取得処理
		StringBuilder sb = new StringBuilder();
		/*
		 * DBログイン
		 */
		Connection conn = null;
		String url = "192.168.51.67";
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
			sb.append("SELECT USER_NO,USER_ID,USER_NAME");
			sb.append("from DEV_TEAM_D.M_USER;");
			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());
			// それぞれArrayListに入れる
			ArrayList<Integer> userNo = new ArrayList<>();
			ArrayList<String> userID = new ArrayList<>();
			ArrayList<String> userName = new ArrayList<>();
			while (rs.next()) {
				userNo.add(rs.getInt("USER_NO"));
				userID.add(rs.getString("USER_ID"));
				userName.add(rs.getString("USER_NAME"));
			}
			// リクエストに送る
			req.setAttribute("userlist", userName);

			// 3）最新メッセージ取得処理
			/*
			 * ユーザ一覧取得
			 */
			ArrayList<Integer> directMessage = new ArrayList<>();
			for (int i = 0; i < userNo.size(); i++) {
				sb.append("select MESSAGE");
				sb.append("from DEV_TEAM_D.T_MESSAGE_INFO");
				sb.append("where SEND_USER_NO='" + sesUserNo + "'and");
				sb.append("TO_SEND_USER_NO='" + userNo.get(i) + "'and");
				sb.append("REGIST_DATE=( select MAX(REGIST_DATE)");
				sb.append("from DEV_TEAM_D.T_MESSAGE_INFO);");
				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				// それぞれArrayListに入れる
				directMessage.add(rs2.getInt("Message"));
			}
			// リクエストに送る
			req.setAttribute("directMessage", directMessage);

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

		// 4) 参加グループ一覧取得処理
		// 出力
		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}

}