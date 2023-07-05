package com.tec02.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.impl.RoleService;
import com.tec02.model.dto.ResponseDto;
import com.tec02.model.dto.impl.impl.impl.RoleDto;
import com.tec02.model.entity.impl.modifiableEnityimpl.Role;

@RestController
@RequestMapping("api/v1/role")
public class RoleAPI extends BaseApiV1<RoleDto, Role>{

	protected RoleAPI(RoleService service) {
		super(service);
	}

	@Autowired
	private RoleService roleService;

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDto> update(@RequestBody Role role, @PathVariable("id") Long id) {
		try {
			RoleDto roleDto = this.roleService.updateDto(id, role);
			if (roleDto == null) {
				return ResponseDto.toResponse(false, null, "update role failed!");
			}
			return ResponseDto.toResponse(true, roleDto, "update role ok!");
		} catch (Exception e) {
			return ResponseDto.toResponse(false, null, e.getLocalizedMessage());
		}
	}
}
