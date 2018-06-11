package bean;
/**
 * グループ作成ページ用のビーン。
 * モデルでSQLを実行して起きたエラーの情報を入れる。
 * jspで表示はしないから使わなくても処理作成できるかも
 * @author masuda-keito
 *
 */
public class MakeGroupBean {
	/**
	 * フィールド
	 */
	/** エラーメッセージ */
	private String errorMsg="";
	/** エラーフラグ 1:SQLエラー 2:入力値エラー */
	private int errorFlag;
	/**
	 * メソッド
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
}
