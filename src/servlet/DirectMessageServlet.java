package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/*
		* 画面表示処理
		*/
			//データベースと接続
			Connection conn = null;
			String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
			String user = "DEV_TEAM_D";
			String dbPassword = "D_DEV_TEAM";

			DirectMessageBean bean = new DirectMessageBean();

		try {
			//JDBCドライバーのロード
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			//データベースへの接続作成
				try {
					conn = DriverManager.getConnection(url, user, dbPassword);
					Statement stmt = conn.createStatement();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}


		//(1)パラメーターチェック
			//(1)-1セッション確認
			//sessionスコープを使う下準備
			HttpSession session = req.getSession();

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
			//まずはsessionスコープに入っている値を取得
			String userId = (String) session.getAttribute("userId");
			try {
				//SQLのSELECT文を準備
				String sql = "SELECT USER_NO FROM M_USER WHERE USER_ID='" + userId + "'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmt = conn.prepareStatement(sql);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmt.executeQuery();

				while (result.next()) {
					bean.setMyNo(result.getInt("USER_NO"));
				}

				//sessionスコープに正しい値が入っていない場合はログインページに戻す
			} catch (SQLException e) {
				bean.setErrorMsg("セッションがありません");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			//(1)-2パラメータチェック
			try {
				//パラメータのチェック
				//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
				bean.setUserNo(Integer.parseInt(req.getParameter("userNo")));
				String sqlGetuserNo = "SELECT USER_NO FROM M_USER WHERE USER_NO = '" + bean.getUserNo() + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmtGetuserNo.executeQuery();

				while (result.next()) {
					bean.setUserNo(result.getInt("USER_NO"));
				}

				//相手のユーザー番号をPOSTメソッドでも利用するために、sessionスコープに格納する。
				session.setAttribute("userNo", bean.getUserNo());



				//(1)-3		チェックでエラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("不正な遷移です。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			//(2)会話情報取得処理
			//(2)-1会話情報取得

			try {
				//相手か自分のユーザー名とメッセージをとってくるSQLのSELECT文を準備
				String sqlMes = "SELECT MESSAGE, USER_NAME, USER_NO, MESSAGE_NO FROM T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO WHERE (SEND_USER_NO = '"+bean.getUserNo() +"' or SEND_USER_NO='"+bean.getMyNo()+"')AND (TO_SEND_USER_NO = '"+bean.getMyNo()+"'or TO_SEND_USER_NO='"+bean.getUserNo()+"')AND DELETE_FLAG='0'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtMes = conn.prepareStatement(sqlMes);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet resultMes = pStmtMes.executeQuery();
				//結果をリストに格納する
				ArrayList<String> userNo = new ArrayList<>();
				ArrayList<String> userName = new ArrayList<>();
				ArrayList<String> message = new ArrayList<>();
				ArrayList<String> messageNo = new ArrayList<>();
				while (resultMes.next()) {
					userNo.add(resultMes.getString("USER_NO"));
					userName.add(resultMes.getString("USER_NAME"));
					message.add(resultMes.getString("MESSAGE"));
					messageNo.add(resultMes.getString("MESSAGE_NO"));
				}
				bean.setListUserNo(userNo);
				bean.setListUserName(userName);
				bean.setListMessage(message);
				bean.setListMsgNo(messageNo);



			} catch (SQLException e) {
				System.out.println("会話情報取得できません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			//取得した情報をdirectMessage.jspに送る
			Integer myNum=bean.getMyNo();
			req.setAttribute("messageNo", bean.getListMsgNo());
			req.setAttribute("myNo", myNum);
			req.setAttribute("userNo", bean.getListUserNo());
			req.setAttribute("singleUserNO", req.getParameter("userNo"));
			session.setAttribute("message", bean.getListMessage());
			session.setAttribute("username", bean.getListUserName());
			req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

		}finally {
			try {
			    conn.close();
		    } catch (SQLException e) {
			    e.printStackTrace();
		    }
		}
		}






	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
			/*
			* メッセージ送信処理
			*/

		//データベースと接続
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";

		DirectMessageBean bean = new DirectMessageBean();

	try {
		//JDBCドライバーのロード
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		//データベースへの接続作成
			conn = DriverManager.getConnection(url, user, dbPassword);
			Statement stmt = conn.createStatement();


			String check = req.getParameter("check");

			//sessionスコープを使う下準備
		    HttpSession session = req.getSession();

			if(check.equals("2")) {


			//(1)パラメータチェック


		  //LoginServletでsessionスコープに入れたuserIdから、ログインユーザーの会員番号を取得
			//まずはsessionスコープに入っている値を取得
			String userId = (String) session.getAttribute("userId");
			try {
				//SQLのSELECT文を準備
				String sql = "SELECT USER_NO FROM M_USER WHERE USER_ID='" + userId + "'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmt = conn.prepareStatement(sql);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmt.executeQuery();

				while (result.next()) {
					bean.setMyNo(result.getInt("USER_NO"));
				}
				//エラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("セッションがありません");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}


			//相手のユーザー番号を取得
			try {
				//まずはsessionスコープに入っているuserNoというパラメータを受け取り、変数に格納(データの降り口)
				int user_No = (int) session.getAttribute("userNo");
				String sqlGetuserNo = "SELECT USER_NO FROM M_USER WHERE USER_NO = '" + user_No + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmtGetuserNo.executeQuery();

				while (result.next()) {
					bean.setUserNo(result.getInt("USER_NO"));
				}
				//エラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("不正な遷移です。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}





			//directMessage.jspで指定されたsendMessageというパラメータを受け取り、変数に格納(データの降り口)
			String sendMessage = req.getParameter("sendMessage");
			//(1)-1入力値のチェック
			if (sendMessage == null || sendMessage.length() > 100) {
				System.out.println("パラメーターが不正");
				//エラーメッセージを表示し、メッセージ画面に遷移
				System.out.println("100字以内のメッセージを入力してください。");
				req.getRequestDispatcher("/directMessage.jsp").forward(req, res);
			}
			session.setAttribute("sendMessage", sendMessage);






			//会話情報登録処理

			//会話番号の自動採番処理
			//会話番号の最大値を持ってくるSQL文を送信する
			try {
			String sqlGetMax = "SELECT MAX(MESSAGE_NO) FROM  T_MESSAGE_INFO";
			//SQLをDBに届けるPreparedStatementのインスタンスを取得
			PreparedStatement pStmtGetMax = conn.prepareStatement(sqlGetMax);
			//ResultSetインスタンスにSELECT文の結果を格納する
			ResultSet resultMax = pStmtGetMax.executeQuery();
			while (resultMax.next()) {
				bean.setN(resultMax.getInt("MAX(MESSAGE_NO)"));
			}
			}catch (SQLException e) {
				bean.setErrorMsg("会話情報の自動採番ができません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			//会話番号の最大値+1を入れる変数を宣言
			int newMesNo = bean.getN() + 1;

			try {
				//SQLのINSERT文を準備
				String sqlSendMes = "INSERT INTO T_MESSAGE_INFO(MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_USER_NO,DELETE_FLAG, REGIST_DATE)VALUES('"
						+ newMesNo + "','" + bean.getMyNo() + "','" + sendMessage + "','" + bean.getUserNo() + "', '0', SYSDATE)";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtSendMes = conn.prepareStatement(sqlSendMes);


				//ResultSetインスタンスにINSERT文の結果を格納する
				ResultSet resultSendMes = pStmtSendMes.executeQuery();

				//さらに、送ったメッセージをリストに格納して画面に表示していく
					//送ったメッセージを取得するSELECT文を準備
					String sqlSentMes = "SELECT MESSAGE, USER_NAME FROM T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO WHERE  =MESSAGE_NO '" +  newMesNo + "'";
					//SQLをDBに届けるPreparedStatementのインスタンスを取得
					PreparedStatement pStmtSentMes = conn.prepareStatement(sqlSentMes);
					//ResultSetインスタンスにINSERT文の結果を格納する
					ResultSet resultSentMes = pStmtSentMes.executeQuery();
					//結果をリストに格納する
					ArrayList<String> userName = new ArrayList<>();
					ArrayList<String> message = new ArrayList<>();
					while (resultSentMes.next()) {
						userName.add("USER_NAME");
						message.add(resultSentMes.getString("MESSAGE"));
					}
					bean.setListUserName(userName);
					bean.setListMessage(message);


				//登録後、メッセージ画面に遷移
				req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

				//内容を登録できなかった場合、エラー画面に遷移する
			} catch (SQLException e) {
				bean.setErrorMsg("会話内容が登録できません。");
				e.printStackTrace();
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			}else {


			/*
			* メッセージ削除処理
			*/
			//確認ダイアログ表示処理(これはjspで行う)から、OKが押されたら送られてくるパラメータを取り出す

				//取り出せたら以下の処理を行う
				if(req.getParameter("deleteMessageNo") != null) {
					//会話情報論理削除処理
					try {
						int deleteMessageNo = (Integer.parseInt(req.getParameter("deleteMessageNo")));
						//SQLのUPDATE文を準備(さっきのsendMessageのところの削除フラグを1にする)
						String sqlDelete ="UPDATE T_MESSAGE_INFO SET DELETE_FLAG = '1' WHERE MESSAGE_NO = '"+ deleteMessageNo +"'";
						//SQLをDBに届けるPreparedStatementのインスタンスを取得
						PreparedStatement pStmtDelete = conn.prepareStatement(sqlDelete);
						//ResultSetインスタンスにINSERT文の結果を格納する
						ResultSet resultDelete = pStmtDelete.executeQuery();
						//メッセージ送信ページに戻る

					}catch (SQLException e) {
						bean.setErrorMsg("会話情報の論理削除ができません。");
						session.invalidate();
						req.setAttribute("errorMsg", bean.getErrorMsg());
						req.getRequestDispatcher("/errorPage").forward(req, res);
					}

				}
			}





/*
 * 以下、Model利用ができなかったために、GETメソッドの中身をコピーした部分です。
 */
			//(1)パラメーターチェック
			//(1)-1セッション確認

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
			//まずはsessionスコープに入っている値を取得
			String userId = (String) session.getAttribute("userId");
			try {
				//SQLのSELECT文を準備
				String sql = "SELECT USER_NO FROM M_USER WHERE USER_ID='" + userId + "'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmt = conn.prepareStatement(sql);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmt.executeQuery();

				while (result.next()) {
					bean.setMyNo(result.getInt("USER_NO"));
				}

				//sessionスコープに正しい値が入っていない場合はログインページに戻す
			} catch (SQLException e) {
				bean.setErrorMsg("セッションがありません");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			//(1)-2パラメータチェック
			try {
				//パラメータのチェック
				//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
				bean.setUserNo(Integer.parseInt(req.getParameter("userNo")));
				String sqlGetuserNo = "SELECT USER_NO FROM M_USER WHERE USER_NO = '" + bean.getUserNo() + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmtGetuserNo.executeQuery();

				while (result.next()) {
					bean.setUserNo(result.getInt("USER_NO"));
				}

				//相手のユーザー番号をPOSTメソッドでも利用するために、sessionスコープに格納する。
				session.setAttribute("userNo", bean.getUserNo());



				//(1)-3		チェックでエラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("不正な遷移です。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}

			//(2)会話情報取得処理
			//(2)-1会話情報取得

			try {
				//相手か自分のユーザー名とメッセージをとってくるSQLのSELECT文を準備
				String sqlMes = "SELECT MESSAGE, USER_NAME, USER_NO, MESSAGE_NO FROM T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO WHERE (SEND_USER_NO = '"+bean.getUserNo() +"' or SEND_USER_NO='"+bean.getMyNo()+"')AND (TO_SEND_USER_NO = '"+bean.getMyNo()+"'or TO_SEND_USER_NO='"+bean.getUserNo()+"')AND DELETE_FLAG='0'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtMes = conn.prepareStatement(sqlMes);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet resultMes = pStmtMes.executeQuery();
				//結果をリストに格納する
				ArrayList<String> userNo = new ArrayList<>();
				ArrayList<String> userName = new ArrayList<>();
				ArrayList<String> message = new ArrayList<>();
				ArrayList<String> messageNo = new ArrayList<>();
				while (resultMes.next()) {
					userNo.add(resultMes.getString("USER_NO"));
					userName.add(resultMes.getString("USER_NAME"));
					message.add(resultMes.getString("MESSAGE"));
					messageNo.add(resultMes.getString("MESSAGE_NO"));
				}
				bean.setListUserNo(userNo);
				bean.setListUserName(userName);
				bean.setListMessage(message);
				bean.setListMsgNo(messageNo);



			} catch (SQLException e) {
				System.out.println("会話情報取得できません。");
				session.invalidate();
				req.setAttribute("errorMsg", bean.getErrorMsg());
				req.getRequestDispatcher("/errorPage").forward(req, res);
			}
			//取得した情報をdirectMessage.jspに送る
			Integer myNum=bean.getMyNo();
			req.setAttribute("messageNo", bean.getListMsgNo());
			req.setAttribute("myNo", myNum);
			req.setAttribute("userNo", bean.getListUserNo());
			session.setAttribute("message", bean.getListMessage());
			session.setAttribute("username", bean.getListUserName());
			req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);


/*
 * 以上、Model利用ができなかったために、GETメソッドの中身をコピーした部分です。
 */






		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//SQLの接続は絶対に切断
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	//	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	//
	//		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	//
	//	}
	//branch 'feature-chat-teamD' of https://github.com/kms-java-training2018/chatteamD
}
