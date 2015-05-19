package my.cool.app.token;

/**
 * トランザクショントークンチェックの区分
 * 
 * @author mitsui0273
 *
 */
public enum TransactionTokenType {
	/** トークンチェックしない */
	NONE, 
	/** トークンの発行 */
	BEGIN, 
	/** トークンチェック */
	CHECK;
}
