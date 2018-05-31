package bean;

import java.util.ArrayList;

public class UserListBean {
	/**
	 * フィールド
	 */
	/** 表示する他ユーザーのユーザーNo */
	private ArrayList<Integer> userNo = new ArrayList<>();

	/** 表示する他ユーザーのユーザーID */
	private ArrayList<String> userID = new ArrayList<>();

	/** 表示する他ユーザーのユーザー名 */
	private ArrayList<String> userName = new ArrayList<>();

	/**	個人間の最新メッセージ */
	private ArrayList<String> directMessage = new ArrayList<>();

	/** エラーフラグ */
	private int errorFlag = 0;

	/**
	 * メソッド
	 */
	public ArrayList<Integer> getUserNo() {
		return userNo;
	}
	public void setUserNo(ArrayList<Integer> userNo) {
		this.userNo = userNo;
	}
	public ArrayList<String> getUserID() {
		return userID;
	}
	public void setUserID(ArrayList<String> userID) {
		this.userID = userID;
	}
	public ArrayList<String> getUserName() {
		return userName;
	}
	public void setUserName(ArrayList<String> userName) {
		this.userName = userName;
	}
	public ArrayList<String> getDirectMessage() {
		return directMessage;
	}
	public void setDirectMessage(ArrayList<String> directMessage) {
		this.directMessage = directMessage;
	}
	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}

}
