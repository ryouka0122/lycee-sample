package net.coolblossom.lycee.core.evals;

/**
 * <b>テストケースの評価用関数インタフェイス</b>
 * <p>
 * </p>
 * @author ryouka
 *
 */
@FunctionalInterface
public interface Evaluator {

	/**
	 * <b>評価式</b>
	 * <p>
	 * </p>
	 *
	 * @param obj 評価する値
	 */
	void invoke(Object obj);

}
