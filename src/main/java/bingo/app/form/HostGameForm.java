package bingo.app.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostGameForm {

	private String gameName;

	/** ビンゴのアタリ数字 */
	private Integer nextNum;

	private Integer reachNum;

	/** ビンゴした人の人数 */
	private Integer bingoNum;

	/**
	 * 次のアクション
	 *
	 * Next or Start
	 */
	private String nextAction;
}
