package bingo.domain.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BingoGameModel {

	private String gameName;

	/**
	 * ビンゴのくじの配列
	 *
	 * カンマ区切りで、0~75の数字が並ぶ
	 */
	private String numArrayString;

}
