package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GroupListBean;
import bean.SessionBean;
import bean.UserListBean;
import model.GetGroupListModel;
import model.GetUserListModel;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/WEB-INF/jsp/mainPage.jsp").forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// loginからはpostで送られてくる
		// 初期化
		UserListBean userListBean = new UserListBean();
		GetUserListModel userListModel = new GetUserListModel();
		GroupListBean groupListBean = new GroupListBean();
		GetGroupListModel groupListModel = new GetGroupListModel();
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
				// 2）他会員一覧取得処理
				userListBean = userListModel.getUserList(userListBean, sesUserNo);
				// 3）最新メッセージ取得処理
				userListBean = userListModel.getUserLatestMessage(userListBean, sesUserNo);
				// 4) 参加グループ一覧取得処理
				// グループ一覧取得
				groupListBean = groupListModel.getGroupList(groupListBean, sesUserNo);
				// グループメッセージ取得
				groupListBean = groupListModel.getGroupLatestMessage(groupListBean, sesUserNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 途中でエラーはいている場合
		if (userListBean.getErrorFlag() == 1 || groupListBean.getErrorFlag() == 1) {
			// 行き先をエラーページに
			direction = "/errorPage";
		} else {
			// リクエストに送る
			req.setAttribute("userbean", userListBean);
			req.setAttribute("groupbean", groupListBean);
		}

		// 出力
		req.getRequestDispatcher(direction).forward(req, res);
	}

}