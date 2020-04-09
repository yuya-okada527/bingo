package bingo.app.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostLoginForm {

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9_!?]*")
	@Size(max = 16)
	private String name;

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]*")
	@Size(min = 8, max = 8)
	private String password;
}