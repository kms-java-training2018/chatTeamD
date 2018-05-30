package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DirectMessageServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
/*
* 画面表示処理
*/
		//データベースと接続
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";
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

			//セッションの存在チェック
			//sessionスコープを使う下準備
			HttpSession session = req.getSession();

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
	        //まずはsessionスコープに入っている値を取得
	        String userId = (String)session.getAttribute("userId");

	     	//sessionスコープに正しい値が入っていない場合はログインページに戻す

	        	//1.まず、照合のために、正しいIDをDBから取得する
	        	//SQLのSELECT文を準備
	        	String sql = "SELECT USER_ID,USER_NO FROM M_USER";
	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        	PreparedStatement pStmt = conn.prepareStatement(sql);
	        	//ResultSetインスタンスにSELECT文の結果を格納する
	        	ResultSet result = pStmt.executeQuery();
	        	//DBから出してきたID、Passwordを格納する変数を設定
	        	String db_userID = result.getString("USER_ID");
	        	String db_userNo = result.getString("USER_NO");






	        	//ログインしているユーザー自身の会員番号がのちに送信機能で必要となるので、
	        	//あらかじめIDを元に会員番号を取得しておく
	        	String sqlGetUserNo = "SELECT USER_NO FROM M_USER WHERE USER_ID = "+ userId +"";
	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        	PreparedStatement pStmtGetMyNo = conn.prepareStatement(sqlGetUserNo);
	        	//ResultSetインスタンスにSELECT文の結果を格納する
	        	ResultSet resultGetMyNo = pStmtGetMyNo.executeQuery();
	        	//取得したログインしているユーザー自身の会員番号を入れる変数を宣言
	        	String myNo = resultGetMyNo.getString("USER_NO");





	        	//2.Sessionスコープの中身とDBのID、Passwordが合っているか確認
	        	if(!db_userID.equals(userId) ) {
	                System.out.println("セッションがありません。");
	                session.invalidate();
	                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

	        	//パラメータのチェック
	        	//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
	            String userNo = req.getParameter("userNo");

	          //2.mainPage.jspから送られてきたuserNoとDBのuserNOを照合
	        	if(!db_userNo.equals(userNo)) {
	                System.out.println("パラメーターが不正");
	                session.invalidate();
	                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

	        	//会話情報取得処理
	        	//SQLのSELECT文を準備
	        	String sqlMes = "SELECT MESSAGE FROM T_MESSAGE_INFO WHERE USER_NO = " + userNo + "";
	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        	PreparedStatement pStmtMes = conn.prepareStatement(sqlMes);
	        	//ResultSetインスタンスにSELECT文の結果を格納する
	        	ResultSet resultMes = pStmtMes.executeQuery();
	        	String message = resultMes.getString("MESSAGE");

	        	//会話情報のレコードが取得できなかった場合
	        	if(message != null) {

	        		//変数に格納したデータをsessionスコープで保存
		            //messageという名前をそれぞれつける
		            session.setAttribute("message",resultMes);

		            req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);

	            }else {
	            System.out.println("会話情報のレコードが取得できません");
	            session.invalidate();
                req.getRequestDispatcher("/error.jsp").forward(req, res);
	            }

/*
* メッセージ送信処理
*/
	        	//パラメータチェック
	        	//directMessage.jspで指定されたsendMessageというパラメータを受け取り、変数に格納(データの降り口)
	        	String sendMessage = req.getParameter("sendMessage");
	        	//入力値のチェック
	        	if(sendMessage == null || sendMessage.length()>100) {
	        		System.out.println("パラメーターが不正");
	        	//エラーメッセージを表示し、メッセージ画面に遷移
	        		System.out.println("100字以内のメッセージを入力してください。");
	        		req.getRequestDispatcher("/directMessage.jsp").forward(req, res);
	        	}

	        	//会話情報登録処理

	        		//会話番号の自動採番処理
	        			//会話番号の最大値を持ってくるSQL文を送信する
	        			String sqlGetMax = "SELECT MAX(MESSAGE_NO) FROM  T_MESSAGE_INFO";
	        			//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        			PreparedStatement pStmtGetMax = conn.prepareStatement(sqlGetMax);
	        			//ResultSetインスタンスにSELECT文の結果を格納する
	    	        	ResultSet resultMax = pStmtGetMax.executeQuery();
	    	        	int n = resultMax.getInt("MESSAGE_NO");

	    	        	//会話番号の最大値+1を入れる変数を宣言
	    	        	int newMesNo = n++;

	        		try {
	        			//SQLのSELECT文を準備
	        			String sqlSendMes = "INSERT INTO T_MESSAGE_INFO(MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_USER_NO,DELETE_FLAG, REGIST_DATE)VALUES("+newMesNo+","+myNo+","+ sendMessage+ ","+ userNo+", 0, SYSDATE)";
	        			//SQLをDBに届けるPreparedStatementのインスタンスを取得
	        			PreparedStatement pStmtSendMes = conn.prepareStatement(sqlSendMes);

	        			//内容を登録できなかった場合、エラー画面に遷移する
	        		}catch(SQLException e) {
	        			System.out.println("会話内容が登録できません。");
	        			session.invalidate();
	        			req.getRequestDispatcher("/error.jsp").forward(req, res);
	        		}



/*
* メッセージ削除処理
*/
	        	//確認ダイアログ表示処理


	        	//会話情報論理削除処理
	        	//SQLのSELECT文を準備

	        	//SQLをDBに届けるPreparedStatementのインスタンスを取得

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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

