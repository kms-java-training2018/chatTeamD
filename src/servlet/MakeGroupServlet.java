package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MakeGroupBean;
import bean.SessionBean;
import bean.UserListBean;
import model.GetUserListModel;
import model.MakeGroupModel;

public class MakeGroupServlet extends HttpServlet {
	/**
	 * グループ名の最大長
	 */
	final int GROUP_NAME_LENGTH = 30;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成ページに移動する処理
		// 初期化
		UserListBean bean = new UserListBean();
		GetUserListModel model = new GetUserListModel();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";
		/**
		 * 1)パラメータチェック
		 */
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();

		if (sesUserNo.equals(null)) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
		} else {
			/**
			 * 1)会員一覧取得処理
			 */
			try {
				bean = model.getUserList(bean, sesUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (bean.getErrorFlag() == 1) {
			// 途中でエラーはいている場合
			// 行き先をエラーページに
			direction = "/errorPage";
		} else {
			// 正常に一覧取得できた場合
			// リクエストに送る
			req.setAttribute("bean", bean);
		}
		// 出力
		req.getRequestDispatcher(direction).forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成を行いメインページに移動する処理
		// 初期化
		MakeGroupBean bean = new MakeGroupBean();
		MakeGroupModel model = new MakeGroupModel();
		String direction = "/WEB-INF/jsp/makeGroup.jsp";
		/**
		 * 1)パラメータチェック
		 */
		req.setCharacterEncoding("UTF-8");
		// セッション情報確認
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		// 入力値変数に渡す
		String groupName = req.getParameter("groupName");
		String[] groupMemberNo = req.getParameterValues("userNo");
		if (sesUserNo.equals(null)) {
			// セッション情報なし
			// エラーページへ
			direction = "/errorPage";
		} else {
			// それ以外の場合処理続ける
			try {
				/**
				 * 2)グループ登録処理
				 * 3)グループ会員登録処理
				 */
				bean = model.makeGroup(bean, groupName, groupMemberNo, sesUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bean.getErrorFlag() == 1) {
				// モデル内でエラーを吐いていた場合
				req.setAttribute("errorMsg", bean.getErrorMsg());
				// 行き先をエラーページに
				direction = "/errorPage";
			} else {
				// 正常にグループ作成できた場合
				// 行き先をメインページに
				direction = "/main";

			}
			// 出力
			req.setAttribute("bean", bean);
			req.setAttribute("errorMsg", bean.getErrorMsg());
			req.getRequestDispatcher(direction).forward(req, res);

		}
	}
}
