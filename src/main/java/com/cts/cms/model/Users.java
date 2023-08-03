package com.cts.cms.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@CrossOrigin
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Users implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "email", unique = true)
	private String email;

	private String password;

	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties("user")
	private List<Orders> orders;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//	@JsonIgnoreProperties()
	private List<Role> roles;

//	

	public Users(String userName, String contactNumber, String email, String password, List<Role> roles,
			List<Orders> orders) {

		super();
		this.userName = userName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.password = password;
		this.orders = orders;
		this.roles = roles;
	}

	public Users(String userName, String contactNumber, String email, String password) {
		super();
		this.userName = userName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.password = password;
	}

	public Users(String email, String password, List<Role> roles) {
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;

	}

}
