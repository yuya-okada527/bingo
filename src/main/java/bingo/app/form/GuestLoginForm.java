package bingo.app.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestLoginForm {

	@NotBlank(message = "名前は、必須項目です。")
	@Size(max = 16, message = "名前は、16文字までで、ご入力ください。")
	private String guestName;

	private String gameName;
}
