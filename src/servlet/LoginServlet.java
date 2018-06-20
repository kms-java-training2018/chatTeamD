package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.LoginBean;
import bean.SessionBean;
import model.LoginModel;

/**
 * ログイン画面用サーブレット
 */

public class LoginServlet extends HttpServlet {

	/**
	 * 初期表示
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 初期化
		LoginBean bean = new LoginBean();
		bean.setErrorMessage("");
		bean.setUserId("");
		bean.setPassword("");

		req.setAttribute("loginBean", bean);
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// -------------------------------------------------------------
		// 初期化
		LoginBean bean = new LoginBean();
		LoginModel model = new LoginModel();
		String direction = "/WEB-INF/jsp/login.jsp";
		String errormsg = "";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// パラメータの取得
		String userId = (String) req.getParameter("userId");
		String password = (String) req.getParameter("password");
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// userId, passwordをbeanへセット
		bean.setUserId(userId);
		bean.setPassword(password);
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		// 認証処理
		try {
			bean = model.authentication(bean);
		} catch (Exception e) {
			errormsg = "DBに接続されていません。";
			req.setAttribute("errorMessage", errormsg);
			req.getRequestDispatcher(direction).forward(req, res);
		}
		// -------------------------------------------------------------

		int userIdLen = userId.length();
		int passwordLen = password.length();
		byte[] userIdBytes = userId.getBytes();
		byte[] passwordBytes = password.getBytes();

		errormsg = bean.getErrorMessage();

		if(bean.getUserId() == "" || bean.getPassword() == "") {
			// エラー
			errormsg = errormsg + "　ID/パスワードを入力してください。";
//			req.setAttribute("errorMessage", errormsg);
//			direction = "WEB-INF/jsp/login.jsp";
//			req.getRequestDispatcher(direction).forward(req, res);
//			return;
		}

		if (userIdBytes.length != userIdLen || passwordBytes.length != passwordLen) {
			// エラー
			errormsg = errormsg + "　半角で入力してください。";
//			req.setAttribute("errorMessage", errormsg);
//			direction = "WEB-INF/jsp/login.jsp";
//			req.getRequestDispatcher(direction).forward(req, res);
//			return;
		}

		if (userIdBytes.length > 20 || passwordBytes.length > 20) {
			errormsg =  errormsg + "　半角20文字までで入力してください。";
//			req.setAttribute("errorMessage", errormsg);
//			direction = "WEB-INF/jsp/login.jsp";
//			req.getRequestDispatcher(direction).forward(req, res);
//			return;
		}

		// -------------------------------------------------------------
		// 取得に成功した場合セッション情報をセット
		if ("".equals(errormsg)) {
			SessionBean sessionBean = new SessionBean();
			sessionBean.setUserName(bean.getUserName());
			sessionBean.setUserNo(bean.getUserNo());
			sessionBean.setUserId(bean.getUserId());
			HttpSession session = req.getSession();
			session.setAttribute("session", sessionBean);
			session.setAttribute("userId", userId);
			// 行き先を次の画面に
			direction = "/main";
		} else {
			// エラーメッセージの表示
			req.setAttribute("errorMessage", errormsg);
		}
		// -------------------------------------------------------------

		req.getRequestDispatcher(direction).forward(req, res);
	}
}