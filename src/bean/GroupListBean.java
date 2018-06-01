package bean;

import java.util.ArrayList;

public class GroupListBean {
	/**
	 * フィールド
	 */
	/**	表示するグループNo */
	private ArrayList<Integer> groupNo = new ArrayList<>();

	/**	表示するグループ名 */
	private ArrayList<String> groupName = new ArrayList<>();

	/**	グループの最新メッセージ */
	private ArrayList<String> groupMessage = new ArrayList<>();

	/** グループに参加していない場合の文言 */
	private String groupNullMes = "";

	/** エラーフラグ */
	private int errorFlag = 0;

	/**
	 * メソッド
	 */
	public ArrayList<Integer> getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(ArrayList<Integer> groupNo) {
		this.groupNo = groupNo;
	}

	public ArrayList<String> getGroupName() {
		return groupName;
	}

	public void setGroupName(ArrayList<String> groupName) {
		this.groupName = groupName;
	}

	public ArrayList<String> getGroupMessage() {
		return groupMessage;
	}

	public void setGroupMessage(ArrayList<String> groupMessage) {
		this.groupMessage = groupMessage;
	}

	public String getGroupNullMes() {
		return groupNullMes;
	}

	public void setGroupNullMes(String groupNullMes) {
		this.groupNullMes = groupNullMes;
	}

	public int getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}

}
