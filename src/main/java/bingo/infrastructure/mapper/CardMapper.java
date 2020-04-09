package bingo.infrastructure.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import bingo.domain.data.model.CardModel;

@Mapper
public interface CardMapper {

	@Insert(  " INSERT INTO                          "
			+ "    card                              "
			+ " VALUES                               "
			+ "   (#{cardId}, #{cardNumArrayString}) ")
	public void insertCard(String cardId, String cardNumArrayString);

	@Select(  " SELECT                   "
			+ "    card_id               "
			+ "   ,card_num_array_string "
			+ " FROM                     "
			+ "    card                  "
			+ " WHERE                    "
			+ "    card_id = #{cardId}   ")
	@Results(id = "card", value = {
		@Result(id = true, column = "card_id", property = "cardId"),
		@Result(column = "card_num_array_string", property = "cardNumArrayString")
	})
	public CardModel selectCard(String cardId);
}
