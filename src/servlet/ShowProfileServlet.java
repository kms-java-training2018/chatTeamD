package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.LoginBean;

public class ShowProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// beanの初期化
		LoginBean bean = new LoginBean();
		String direction = "/WEB-INF/jsp/showProfile.jsp";

		// パラメータの取得
		String userNo = (String) req.getParameter("userId");
		// デバッグ用userNo設定
		userNo = "13";

		// 取得したユーザーナンバーをセット
		bean.setUserNo(userNo);

		// 初期化
		StringBuilder sb = new StringBuilder();
		Connection conn = null;
		// URL
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		// ユーザーネーム
		String user = "DEV_TEAM_D";
		// パスワード
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

			/*
			 * SQL SELECT文
			 * SELECT USER_NAME, MY_PAGE_TEXT
			 * FROM M_USER
			 * WHERE USER_NO = 'userNo'
			 */
			// SQL作成
			sb.append("SELECT ");
			sb.append(" user_name ");
			sb.append(", my_page_text ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append("WHERE ");
			sb.append(" user_no = '" + userNo + "' ");

			// SQL実行

			ResultSet rs = stmt.executeQuery(sb.toString());
			String showMyName = "";
			String showMyPageText = "";

			while(rs.next()) {
			showMyPageText = rs.getString("my_page_text");
			showMyName = rs.getString("user_name");
			}
			req.setAttribute("showMyPageText", showMyPageText);
			req.setAttribute("showName", showMyName);
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


		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {


		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);

	}
}
