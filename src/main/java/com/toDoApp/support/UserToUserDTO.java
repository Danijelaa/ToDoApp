package com.toDoApp.support;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.toDoApp.model.User;
import com.toDoApp.web.dto.UserDTO;

@Component
public class UserToUserDTO  implements Converter<User, UserDTO>{

	@Override
	public UserDTO convert(User user) {
		UserDTO userDto=new UserDTO();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setLastName(user.getLastName());
		userDto.setUsername(user.getUsername());
		return userDto;
	}

}
