package bingo.infrastructure.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestMapper {

	@Insert(  " INSERT INTO                "
			+ "    guest                   "
			+ "   (guest_name, game_name)    "
			+ " VALUES                     "
			+ "   (#{guestName}, #{gameName}) ")
	public void insertGuest(String guestName, String gameName);
}
