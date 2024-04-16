package com.tec02.model.entity.impl.modifiableEnityimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.tec02.model.entity.impl.BaseModifiableEnity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends BaseModifiableEnity<User> implements GrantedAuthority{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4174694370556897669L;
	/**
	 * 
	 */
	@ManyToMany(mappedBy = "userRoles", fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();
	
	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}
	
	public void addUsers(List<User> listUser) {
		users.addAll(listUser);
	}

	@Override
	public String getAuthority() {
		return name;
	}
	
}
