package bingo.infrastructure.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import bingo.domain.data.model.BingoGameModel;

@Mapper
public interface BingoGameMapper {

	@Insert(  " INSERT INTO     "
			+ "    bingo_game   "
			+ "   (game_name)   "
			+ " VALUES          "
			+ "   (#{gameName}) ")
	public int insertGame(String gameName);

	@Select(  " SELECT                              "
			+ "    game_name                        "
			+ "   ,num_array_string                 "
			+ " FROM                                "
			+ "    bingo_game                       "
			+ " WHERE                               "
			+ "    game_name = #{gameName}          ")
	@Results(id = "bingoGame", value = {
		@Result(id = true, column = "game_name", property = "gameName"),
		@Result(column = "num_array_string", property = "numArrayString")
	})
	public BingoGameModel selectGame(String gameName);

	@Select(" SELECT game_name FROM bingo_game ")
	public List<String> selectAllGameNames();

	@Update(  " UPDATE                                                   "
			+ "    bingo_game                                            "
			+ " SET                                                      "
			+ "    num_array_string = #{numArrayString} "
			+ " WHERE                                                    "
			+ "    game_name = #{gameName}                ")
	public void updateGame(String gameName, String numArrayString);
}
