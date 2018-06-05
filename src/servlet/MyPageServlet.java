package servlet;

/**
 * @author hanawa-tomonori
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MyPageBean;
import bean.SessionBean;
import model.MyPageModel;

public class MyPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		MyPageBean bean = new MyPageBean();
		MyPageModel model = new MyPageModel();
		String direction = "/WEB-INF/jsp/myPage.jsp";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserId = sesBean.getUserId();
		String myPageText = (String) req.getAttribute("myPageText");
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}

		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// 自己紹介文(myPageText), ユーザID(ses)
		bean.setMyPageText(myPageText);
		bean.setUserId(sesUserId);

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		req.setAttribute("myName", bean.getUserName());
		req.setAttribute("myPageText", bean.getMyPageText());

		req.getRequestDispatcher(direction).forward(req, res);
	}

	/**
	 * 更新処理
	 * @author hanawa-tomonori
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		String msg = "";
		MyPageBean bean = new MyPageBean();
		MyPageModel model = new MyPageModel();
		SessionBean sessionBean = new SessionBean();
		String direction = "/main";
		String errorMsg = "エラー";
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// Sessionの取得
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserId = sesBean.getUserId();
		// -------------------------------------------------------------

		bean.setUserId(sesUserId);

		req.setCharacterEncoding("UTF-8");

		String sendDispName = req.getParameter("dispName");
		String sendMyPageText = req.getParameter("myPageText");

		// -------------------------------------------------------------
		// Sessionにユーザ情報がなければ、エラーページへ遷移
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// 文字数判定
		int dispName = sendDispName.length();
		int myPageText = sendMyPageText.length();

		// 表示名は30桁まで
		if (dispName > 30) {
			errorMsg = "表示名の文字数エラーです";
			// -------------------------------------------------------------
			// SQL実行
			try {
				bean = model.output(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// -------------------------------------------------------------
			req.setAttribute("myName", bean.getUserName());
			req.setAttribute("myPageText", bean.getMyPageText());
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			return;
		}

		// 自己紹介文は100桁まで
		if (myPageText > 100) {

			// -------------------------------------------------------------
			// SQL実行
			try {
				bean = model.output(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// -------------------------------------------------------------
			errorMsg = "自己紹介文の文字数エラーです";
			req.setAttribute("myName", bean.getUserName());
			req.setAttribute("myPageText", bean.getMyPageText());
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// beanへ自己紹介文(sendMyPageText), 表示名(sendDispName)をセット
		bean.setMyPageText(sendMyPageText);
		bean.setUserName(sendDispName);
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.update(bean);
		} catch (Exception e) {
			errorMsg = "文字数エラーです";
			req.setAttribute("myName", bean.getUserName());
			req.setAttribute("myPageText", bean.getMyPageText());
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			return;
		}

		if (!bean.getErrorMessage().equals("notError")){
			errorMsg = "文字数エラーです";
			req.setAttribute("myName", bean.getUserName());
			req.setAttribute("myPageText", bean.getMyPageText());
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp").forward(req, res);
			return;
		}
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// sessionへ表示名(userName)を再設定
		sessionBean = (SessionBean) session.getAttribute("session");
		sessionBean.setUserName(bean.getUserName());
		session.setAttribute("session", sessionBean);
		// -------------------------------------------------------------
		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------------------------------------------

		req.setAttribute("myName", bean.getUserName());
		req.setAttribute("myPageText", bean.getMyPageText());

		// メッセージ(msg)をセット
		req.setAttribute("msg", msg);

		// /mainへ遷移
		req.getRequestDispatcher(direction).forward(req, res);

	}
}
