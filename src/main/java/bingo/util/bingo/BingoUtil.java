package bingo.util.bingo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class BingoUtil {

	public List<Integer> convertFromNumArrayStr(String numArrayStr) {
		return Stream.of(numArrayStr.split(","))
					 .map(num -> Integer.valueOf(num))
					 .collect(Collectors.toList());
	}
}
