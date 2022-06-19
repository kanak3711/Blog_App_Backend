package com.example.demo.payloads;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	private int id;
	@NotNull
	@NotEmpty
	@Size(min=4,message="must be greater than 4")
	private String name;
	
	@NotNull
	private String password;
	@Email(message="email not valid")
	private String email;
	@NotEmpty
	private String about;

}
