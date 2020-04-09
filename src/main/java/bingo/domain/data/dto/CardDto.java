package bingo.domain.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

	private int[][] squaresNum;

	private boolean[][] squaresBoolean;

	private boolean bingo;

}
