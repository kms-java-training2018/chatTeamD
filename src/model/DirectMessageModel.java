package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DirectMessageBean;

public class DirectMessageModel {
	/**
	 * 引数beanに引数userIdとbeanにセット済みのuserNoとの会話一覧を取得するメソッド
	 * @param bean	……DirectMessageBean型。先にuserNoをセットしておく必要がある
	 * @param userId	……String型。主にログインしているユーザーを想定
	 * @return	userIdとbean内userNoとのメッセージ一覧をDirectMessageBean型に格納して返す
	 */
	public DirectMessageBean dispDM(DirectMessageBean bean, String userId) {
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
			try {
				conn = DriverManager.getConnection(url, user, dbPassword);
				Statement stmt = conn.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			//userIdの人(ログインしているユーザーを想定)のuserNo取得
			try {
				//SQLのSELECT文を準備
				String sql = "SELECT USER_NO FROM M_USER WHERE USER_ID='" + userId + "'";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmt = conn.prepareStatement(sql);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmt.executeQuery();

				while (result.next()) {
					// beanに自分のuserNoセット
					bean.setMyNo(result.getInt("USER_NO"));
				}

				//sessionスコープに正しい値が入っていない場合はエラーメッセージ入れる
			} catch (SQLException e) {
				bean.setErrorMsg("自分の情報が取得できません");
			}
			//(1)-2パラメータチェック
			try {
				//DM相手のパラメータチェック、ユーザーネーム取得
				String sqlGetuserNo = "SELECT USER_NO,USER_NAME FROM M_USER WHERE USER_NO = '" + bean.getUserNo() + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmtGetuserNo.executeQuery();

				while (result.next()) {
					// beanに会話相手のuserNoセット
					bean.setUserNo(result.getInt("USER_NO"));
					bean.setUsername(result.getString("USER_NAME"));
				}
				//(1)-3		チェックでエラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("相手の情報が取得できません");

			}

			//(2)会話情報取得処理
			//(2)-1会話情報取得

			try {
				//相手か自分のユーザー名とメッセージをとってくるSQLのSELECT文を準備
				String sqlMes = "SELECT MESSAGE, USER_NAME, USER_NO, MESSAGE_NO FROM T_MESSAGE_INFO INNER JOIN M_USER ON T_MESSAGE_INFO.SEND_USER_NO = M_USER.USER_NO WHERE (SEND_USER_NO = '"
						+ bean.getUserNo() + "' or SEND_USER_NO='" + bean.getMyNo() + "')AND (TO_SEND_USER_NO = '"
						+ bean.getMyNo() + "'or TO_SEND_USER_NO='" + bean.getUserNo() + "')AND DELETE_FLAG='0'";
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

			}
			//取得した情報をdirectMessage.jspに送る

		} finally {
			try {
				// DB接続終了
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	/**
	 * 引数userIdから引数bean内userNoへの新しいDM"sendMessage"をDBに登録するメソッド
	 * @param bean
	 * @param userId
	 * @param sendMessage
	 * @return
	 */
	public DirectMessageBean setNewDirectMessage(DirectMessageBean bean, String userId, String sendMessage) {
		//データベースと接続
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";

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
		try {
			// 引数からログインユーザーの会員番号を取得
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
				bean.setErrorMsg("自分の会員番号が取得できません");
			}

			//相手のユーザー番号を取得
			try {
				//まずはbeanに入っているuserNoというパラメータを受け取り、変数に格納(データの降り口)
				int user_No = (int) bean.getUserNo();
				String sqlGetuserNo = "SELECT USER_NO FROM M_USER WHERE USER_NO = '" + user_No + "'";
				PreparedStatement pStmtGetuserNo = conn.prepareStatement(sqlGetuserNo);
				//ResultSetインスタンスにSELECT文の結果を格納する
				ResultSet result = pStmtGetuserNo.executeQuery();

				while (result.next()) {
					bean.setUserNo(result.getInt("USER_NO"));
				}
				//エラーが発生した場合の処理
			} catch (SQLException e) {
				bean.setErrorMsg("相手の会員番号が取得できません");
			}

			//sendMessageというパラメータをチェック
			//(1)-1入力値のチェック
			int messageLen = sendMessage.length();
			if (sendMessage != null) {
				if ( messageLen > 100) {
					System.out.println("パラメーターが不正");
					bean.setErrorMsg("メッセージが長すぎます");
					//エラーメッセージを表示し、メッセージ画面に遷移
					System.out.println("100字以内のメッセージを入力してください。");
				}
			}

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
			} catch (SQLException e) {
				bean.setErrorMsg("会話情報の自動採番ができません。");
			}
			//会話番号の最大値+1を入れる変数を宣言
			int newMesNo = bean.getN() + 1;
			/*
			 * メッセージをDBに登録
			 */
			try {
				//SQLのINSERT文を準備
				String sqlSendMes = "INSERT INTO T_MESSAGE_INFO(MESSAGE_NO, SEND_USER_NO, MESSAGE, TO_SEND_USER_NO,DELETE_FLAG, REGIST_DATE)VALUES('"
						+ newMesNo + "','" + bean.getMyNo() + "','" + sendMessage + "','" + bean.getUserNo()
						+ "', '0', SYSDATE)";
				//SQLをDBに届けるPreparedStatementのインスタンスを取得
				PreparedStatement pStmtSendMes = conn.prepareStatement(sqlSendMes);

				//ResultSetインスタンスにINSERT文の結果を格納する
				ResultSet resultSendMes = pStmtSendMes.executeQuery();

				//内容を登録できなかった場合、エラー画面に遷移する
			} catch (SQLException e) {
				bean.setErrorMsg("会話内容が登録できません。");
				e.printStackTrace();
			}

		} finally {
			try {
				// DB接続終了
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
		/**
		 *
		 * @param bean
		 * @param userId
		 * @param deleteMessageNo
		 * @return
		 */
	public DirectMessageBean deleteDirectMessage(DirectMessageBean bean, String userId, int deleteMessageNo) {
		//データベースと接続
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";

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
		try {
			// 引数からログインユーザーの会員番号を取得
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
				bean.setErrorMsg("自分の会員番号が取得できません");
			}

			/*
			* メッセージ削除処理
			*/
			//確認ダイアログ表示処理(これはjspで行う)から、OKが押されたら送られてくるパラメータを取り出す

			//取り出せたら以下の処理を行う

				//会話情報論理削除処理
				try {
					//SQLのUPDATE文を準備(さっきのsendMessageのところの削除フラグを1にする)
					String sqlDelete = "UPDATE T_MESSAGE_INFO SET DELETE_FLAG = '1' WHERE MESSAGE_NO = '"
							+ deleteMessageNo + "'";
					//SQLをDBに届けるPreparedStatementのインスタンスを取得
					PreparedStatement pStmtDelete = conn.prepareStatement(sqlDelete);
					//ResultSetインスタンスにINSERT文の結果を格納する
					ResultSet resultDelete = pStmtDelete.executeQuery();
					//メッセージ送信ページに戻る

				} catch (SQLException e) {
					bean.setErrorMsg("会話情報の論理削除ができません。");
				}

		}finally{
		try {
			// DB接続終了
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}return bean;
}}
