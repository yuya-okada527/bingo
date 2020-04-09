package bingo.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import bingo.domain.data.model.HostModel;

@Mapper
public interface HostMapper {

	@Select(  " SELECT                       "
			+ "    name                      "
			+ " FROM                         "
			+ "    host                      "
			+ " WHERE                        "
			+ "       name = #{name}         "
			+ "   AND password = #{password} ")
	@Results(id = "host", value = {
		@Result(id = true, column = "name", property = "name")
	})
	public HostModel selectHost(String name, String password);
}
