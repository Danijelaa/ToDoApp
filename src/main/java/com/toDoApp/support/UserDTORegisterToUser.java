package com.toDoApp.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.User;
import com.toDoApp.web.dto.UserDTORegister;

@Component
public class UserDTORegisterToUser implements Converter<UserDTORegister, User> {

	@Override
	public User convert(UserDTORegister userDtoRegster) {
		User user=new User();
		user.setLastName(userDtoRegster.getLastName().trim());
		user.setName(userDtoRegster.getName().trim());
		user.setPassword(userDtoRegster.getPassword());
		user.setUsername(userDtoRegster.getUsername());
		return user;
	}

}
