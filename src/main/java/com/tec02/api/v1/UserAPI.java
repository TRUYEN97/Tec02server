package com.tec02.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.RoleService;
import com.tec02.Service.impl.impl.UserService;
import com.tec02.model.dto.AccountDto;
import com.tec02.model.dto.RequestDto;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.UserDto;
import com.tec02.model.entity.impl.modifiableEnityimpl.Role;
import com.tec02.model.entity.impl.modifiableEnityimpl.User;
import com.tec02.util.Util;

@RestController
@RequestMapping("api/v1/user")
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleServer;

	@PostMapping
	public ResponseEntity<ResponseDto> addUserWithRole(
			@RequestParam(value = "roleid", required = false) List<Long> roleIds, @RequestBody AccountDto accountDto) {
		try {
			List<Role> roles = this.roleServer.findAllByIds(roleIds);
			UserDto userSaved = this.userService.addUserWithRoles(roles, accountDto);
			return ResponseDto.toResponse(true, userSaved, "Save user success!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			UserDto userDto = userService.updateDto(id, user);
			if (userDto == null) {
				return ResponseDto.toResponse(false, null, "update user failed");
			}
			return ResponseDto.toResponse(true, userDto, "update user success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}/roleid")
	public ResponseEntity<ResponseDto> updateUserWithRole(@RequestParam("ids") List<Long> roleIds,
			@PathVariable("id") Long userID) {
		try {
			List<Role> roles = this.roleServer.findAllByIds(roleIds);
			User user = this.userService.findOne(userID);
			UserDto userSaved = this.userService.addUserWithRoles(roles, user);
			return ResponseDto.toResponse(true, userSaved, "Save user success!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDto> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.delete(id);
			return ResponseDto.toResponse(true, id, "delete user ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

	@GetMapping
	public ResponseEntity<ResponseDto> findUser(@RequestParam(value = "id", required = false) List<Long> paramIds,
			@RequestBody(required = false) RequestDto requestDto,
			@RequestParam(value = "status", required = false) Boolean status) {
		try {
			List<UserDto> userDtos;
			boolean userStatus = status == null || status ? true : false;
			if (!Util.isNullorEmpty(paramIds)) {
				userDtos = userService.findUser(paramIds, userStatus);
			} else if (requestDto != null && !Util.isNullorEmpty(requestDto.getIds())) {
				userDtos = userService.findUser(requestDto.getIds(), userStatus);
			} else if (status != null) {
				userDtos = userService.findAllByStatus(status);
			} else {
				userDtos = userService.findAllDto();
			}
			if (userDtos == null || userDtos.isEmpty()) {
				return ResponseDto.toResponse(false, null, "Not found!");
			}
			return ResponseDto.toResponse(true, userDtos, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}

}
