package servlet;

import java.io.IOException;
import java.util.ArrayList;

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
		HttpSession session = req.getSession();
		String direction = "/WEB-INF/jsp/mainPage.jsp";
		// 初期化
		GetUserListModel userListModel = new GetUserListModel();
		GroupListBean groupListBean = new GroupListBean();
		GetGroupListModel groupListModel = new GetGroupListModel();
		ArrayList<UserListBean> userListBeanList = new ArrayList<>();
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {
			// セッションからユーザーNo取得
			SessionBean sesBean = (SessionBean) session.getAttribute("session");
			String sesUserNo = sesBean.getUserNo();
			// 2～3処理
			try {
				// 2）他会員一覧取得処理
				userListBeanList = userListModel.getUserList(sesUserNo);
				// 3）最新メッセージ取得処理
				userListBeanList = userListModel.getUserLatestMessage(userListBeanList, sesUserNo);
				// 4) 参加グループ一覧取得処理
				// グループ一覧取得
				groupListBean = groupListModel.getGroupList(groupListBean, sesUserNo);
				// グループメッセージ取得
				groupListBean = groupListModel.getGroupLatestMessage(groupListBean, sesUserNo);
			} catch (Exception e) {
				// 諸々のエラーはここに来る
				e.printStackTrace();
				// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
				userListBeanList.clear();
				// エラー情報入れたbeanだけセット
				UserListBean bean = new UserListBean();
				bean.setErrorFlag(1);
				userListBeanList.add(bean);
			}
		}

		// 途中でエラーはいている場合
		if (userListBeanList.isEmpty() ||userListBeanList.get(0).getErrorFlag() == 1 || groupListBean.getErrorFlag() == 1) {
			// エラーメッセージ送りつつ行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "DB接続に失敗しました");
		} else {
			// リクエストに送る
			req.setAttribute("userbean", userListBeanList);
			req.setAttribute("groupbean", groupListBean);
		}
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// loginからはpostで送られてくる
		// 初期化
		GetUserListModel userListModel = new GetUserListModel();
		GroupListBean groupListBean = new GroupListBean();
		GetGroupListModel groupListModel = new GetGroupListModel();
		ArrayList<UserListBean> userListBeanList = new ArrayList<>();
		String direction = "/WEB-INF/jsp/mainPage.jsp";
		/**
		 *  1）パラメータチェック
		 */
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {
			// セッションからユーザーNo取得
			SessionBean sesBean = (SessionBean) session.getAttribute("session");
			String sesUserNo = sesBean.getUserNo();
			// 2～3処理
			try {
				// 2）他会員一覧取得処理
				userListBeanList = userListModel.getUserList(sesUserNo);
				// 3）最新メッセージ取得処理
				userListBeanList = userListModel.getUserLatestMessage(userListBeanList, sesUserNo);
				// 4) 参加グループ一覧取得処理
				// グループ一覧取得
				groupListBean = groupListModel.getGroupList(groupListBean, sesUserNo);
				// グループメッセージ取得
				groupListBean = groupListModel.getGroupLatestMessage(groupListBean, sesUserNo);
			} catch (Exception e) {
				// 諸々のエラーはここに来る
				e.printStackTrace();
				// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
				userListBeanList.clear();
				// エラー情報入れたbeanだけセット
				UserListBean bean = new UserListBean();
				bean.setErrorFlag(1);
				userListBeanList.add(bean);
			}
		}

		// 途中でエラーはいている場合
		if (userListBeanList.isEmpty() ||userListBeanList.get(0).getErrorFlag() == 1 || groupListBean.getErrorFlag() == 1) {
			// エラーメッセージ送りつつ行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "DB接続に失敗しました");
		} else {
			// リクエストに送る
			req.setAttribute("userbean", userListBeanList);
			req.setAttribute("groupbean", groupListBean);
		}

		// 出力
		req.getRequestDispatcher(direction).forward(req, res);
	}

}