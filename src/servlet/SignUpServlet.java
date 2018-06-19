package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.SignUpBean;
import model.SignUpModel;

public class SignUpServlet extends HttpServlet {
	/**
	 * 初期表示
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 初期化
		SignUpBean bean = new SignUpBean();
		bean.setUserId("");
		bean.setUserPw("");
		req.setAttribute("SignUpBean", bean);
		req.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// -------------------------------------------------------------
		// TODO 【同じIDをはじく処理を作る】
		// Modelでユーザー一覧を取得し、同じIDがないかを検索
		// 同じIDがなければ問題なし
		// -------------------------------------------------------------
		// TODO 【ID/PWの最小文字数の設定】
		// 単純に、入力文字の文字数を調べて、○○文字以上であれば問題ない
		// という、処理を加える
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// 初期化
		SignUpBean bean = new SignUpBean();
		SignUpModel model = new SignUpModel();
		String direction = "/WEB-INF/jsp/login.jsp";
		String errormsg = "";
		// -------------------------------------------------------------

		// 文字化け
		req.setCharacterEncoding("UTF-8");

		// -------------------------------------------------------------
		// パラメータの取得
		String userId = (String) req.getParameter("userId");
		String password = (String) req.getParameter("password");
		String userName = (String) req.getParameter("userName");
		// -------------------------------------------------------------


		int userIdLen = userId.length();
		int passwordLen = password.length();
		byte[] userIdBytes = userId.getBytes();
		byte[] passwordBytes = password.getBytes();
		byte[] userNameBytes = userName.getBytes();

		errormsg = "エラー:";

		// 入力があるかのチェック
		if(userId == "" || password == "" || userName == "" || userName.equals("表示名")) {
			// エラー
			errormsg = errormsg + "ID/パスワード/ユーザー名を入力してください";
		}

		// 半角で入力されているかのチェック
		if (userIdBytes.length != userIdLen || passwordBytes.length != passwordLen) {
			// エラー
			errormsg = errormsg + "　半角英数字で入力してください";
		}

		// 文字数が適正かのチェック
		if (userIdBytes.length > 20 || passwordBytes.length > 20 || userNameBytes.length > 30) {
			errormsg =  errormsg + "　規定の桁数を超えて入力しています";
		}

		if (errormsg.equals("エラー:")) {
			direction = "/WEB-INF/jsp/login.jsp";
		} else {
			// エラーメッセージの表示
			req.setAttribute("errormsg", errormsg);
			direction = "/WEB-INF/jsp/signUp.jsp";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		// -------------------------------------------------------------
		// userId, passwordをbeanへセット
		bean.setUserId(userId);
		bean.setUserPw(password);
		bean.setUserName(userName);
		// -------------------------------------------------------------



		// -------------------------------------------------------------
		// SQL実行
		// 認証処理
		try {
			bean = model.signup(bean);
		} catch (Exception e) {
			errormsg = "DBに接続できません";
			direction = "/WEB-INF/jsp/login.jsp";
			req.setAttribute("errorMessage", errormsg);
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		req.getRequestDispatcher(direction).forward(req, res);
	}
}
