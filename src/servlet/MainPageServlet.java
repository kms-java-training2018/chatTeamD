package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MainPageBean;
import bean.SessionBean;
import model.MainPageModel;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// loginからはpostで送られてくる
		// 初期化
		MainPageBean bean = new MainPageBean();
		MainPageModel model = new MainPageModel();
		String direction = "/WEB-INF/jsp/mainPage.jsp";
		/**
		 *  1）パラメータチェック
		 */
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		if (sesUserNo.equals(null)) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
		} else {
			// 2～3処理
			try {
				bean = model.dispInfo(bean, sesUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 途中でエラーはいている場合
		if (bean.getErrorFlag() == 1) {
			// 行き先をエラーページに
			direction = "/errorPage";
		} else {
			// リクエストに送る
			req.setAttribute("bean", bean);
		}

		// 出力
		req.getRequestDispatcher(direction).forward(req, res);
	}

}