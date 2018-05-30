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
import javax.servlet.http.HttpSession;

import bean.LoginBean;
import bean.SessionBean;

public class ShowProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String errorMsg = "aiueo";
		// beanの初期化
		LoginBean bean = new LoginBean();
		String direction = "/WEB-INF/jsp/showProfile.jsp";

		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean)session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();

		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if(sesUserNo == null) {
			errorMsg = "セッションが切れました";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
			return;
			}

		// パラメータの取得
		String userNo = (String) req.getParameter("userId");

		// デバッグ用userNo設定
		userNo = "1";

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
			String errorMsg1 = "";
			while(rs.next()) {
			showMyPageText = rs.getString("my_page_text");
			showMyName = rs.getString("user_name");
			}

			// showMyPageTextに何も入っていない場合、エラーページへ遷移する
			if(showMyPageText == "") {
				errorMsg1 ="レコードが取得できませんでした";
				req.setAttribute("errorMsg", errorMsg1);
				req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
				return;
			}

			req.setAttribute("showMyPageText", showMyPageText);
			req.setAttribute("showName", showMyName);
		} catch (SQLException e) {
			e.printStackTrace();
			// レコードが取得できない場合はエラー画面へ。
			errorMsg = "レコードが取得できませんでした";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
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


		req.getRequestDispatcher("/main").forward(req, res);

	}
}
