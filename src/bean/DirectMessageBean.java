package bean;

import java.util.ArrayList;

public class DirectMessageBean {
	/**
	 * フィールド
	 */
	/** 相手の会員番号を格納する変数を宣言 */
	private int userNo;

	/** ログインユーザーの会員番号を格納する変数を宣言 */
	private int myNo;

	/**ログインユーザーあての会話情報を格納する変数を宣言 */
	private String message;

	/**	相手ユーザーの名前を格納する変数を宣言 */
	private String username;

	/** 自分のユーザーの名前を格納する変数を宣言 */
	private String myname;

	/**	会話番号の最大値を格納する変数を宣言 */
	private int n ;

	/**	エラーページ用の変数を宣言 */
	private String errorMsg ;

	/**	画面表示用のリストを宣言 */
	private ArrayList<String> listUserName = new ArrayList<>();

	private ArrayList<String> listMessage = new ArrayList<>();

	private ArrayList<String> listUserNo = new ArrayList<>();

	private ArrayList<String> listMsgNo = new ArrayList<>();



	/**
	 * メソッド
	 */
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getMyNo() {
		return myNo;
	}

	public void setMyNo(int myNo) {
		this.myNo = myNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ArrayList<String> getListUserName() {
		return listUserName;
	}

	public void setListUserName(ArrayList<String> listUserName) {
		this.listUserName = listUserName;
	}

	public ArrayList<String> getListMessage() {
		return listMessage;
	}

	public void setListMessage(ArrayList<String> listMessage) {
		this.listMessage = listMessage;
	}

	public ArrayList<String> getListUserNo() {
		return listUserNo;
	}

	public void setListUserNo(ArrayList<String> listUserNo) {
		this.listUserNo = listUserNo;
	}

	public ArrayList<String> getListMsgNo() {
		return listMsgNo;
	}

	public void setListMsgNo(ArrayList<String> listMsgNo) {
		this.listMsgNo = listMsgNo;
	}

	public String getMyname() {
		return myname;
	}

	public void setMyname(String myname) {
		this.myname = myname;
	}

}
