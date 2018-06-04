package bean;

import java.util.ArrayList;

public class GroupMessageBean {
	/**
	 * フィールド
	 */
	/** 相手の会員番号を格納する変数を宣言 */
	private String userNo;

	/**ログインユーザーあての会話情報を格納する変数を宣言 */
	private String message;

	/**	相手ユーザーの名前を格納する変数を宣言 */
	private String userName;

	/**	会話番号の最大値を格納する変数を宣言 */
	private int n ;

	/**	エラーページ用の変数を宣言 */
	private String errorMsg ;

	private String userId;

	private int groupNo;

	private ArrayList<String> listUserName = new ArrayList<>();

	private ArrayList<String> listMessage = new ArrayList<>();

	private ArrayList<String> listUserNo = new ArrayList<>();

	private ArrayList<String> listMsgNo = new ArrayList<>();

	private int messageNo;

	private int groupExitNo;

	private int registUserNo;

	private int exitGroupNo;
	/**
	 * メソッド
	 */
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
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

	public int getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	public int getGroupExitNo() {
		return groupExitNo;
	}

	public void setGroupExitNo(int groupExitNo) {
		this.groupExitNo = groupExitNo;
	}

	public int getRegistUserNo() {
		return registUserNo;
	}

	public void setRegistUserNo(int registUserNo) {
		this.registUserNo = registUserNo;
	}

	public int getExitGroupNo() {
		return exitGroupNo;
	}

	public void setExitGroupNo(int exitGroupNo) {
		this.exitGroupNo = exitGroupNo;
	}

}
