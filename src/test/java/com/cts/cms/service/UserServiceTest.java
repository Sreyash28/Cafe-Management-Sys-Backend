package com.cts.cms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.cms.exception.UserNotFoundException;
import com.cts.cms.model.Users;
import com.cts.cms.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService service ;

	private Users user1;
	private Users user2;
	private List<Users> userList;

	@BeforeEach
	public void setUp() {
		user1 = new Users();
		user1.setId(12);
		user1.setUserName("testuser");
		user1.setContactNumber("8945632100");
		user1.setEmail("testuser@example.com");
		user1.setPassword("test333");
		user1.setRole("user");

		user2 = new Users();
		user2.setId(13);
		user2.setUserName("testuser2");
		user2.setContactNumber("8956321004");
		user2.setEmail("testuser2@example.com");
		user2.setPassword("test222");
		user2.setRole("user");

		userList = new ArrayList<>();
		userList.add(user1);
		userList.add(user2);

	}

	@Test
	void test_Find_User_ById_Success() throws UserNotFoundException {
	
		when(repository.findById(11)).thenReturn(Optional.of(user1));
		
		Users result = service.findUserById(11);
		assertEquals(user1, result);
		assertNotNull(result);
		assertEquals("testuser", result.getUsername());
		assertEquals("testuser@example.com", result.getEmail());
	}

	@Test
	void test_Find_User_ById_Failure() {

		when(repository.findById(12)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> service.findUserById(12));
	}

	@Test
	void test_Get_All_Users() {
	        
	        when(repository.findAll()).thenReturn(userList);
	        
	        List<Users> result = service.getAllUsers();
	        assertEquals(userList, result);
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        assertEquals("testuser", result.get(0).getUsername());
	        assertEquals("testuser@example.com", result.get(0).getEmail());
	    }

	@Test
	 void test_Create_User() {
	        when(repository.save(user1)).thenReturn(user1);

	        Users createdUser = service.createUser(user1);
	        assertNotNull(createdUser);
	        assertEquals("testuser", createdUser.getUsername());
	        assertEquals("testuser@example.com", createdUser.getEmail());
	    }

	@Test
	 void test_Delete_ById_ThrowsException() {
	        when(repository.findById(3)).thenReturn(Optional.empty());
	        assertThrows(UserNotFoundException.class, () -> {
	            service.deleteById(3);
	        });
	    }

	@Test
	void test_Update_User_Success() throws UserNotFoundException {
	       
	        when(repository.findById(user1.getId())).thenReturn(Optional.of(user1));
	        Users user = new Users(12,"testuser", "8945631100", "testuser@example.com", "test323", "user", null,null);
	        when(repository.save(user)).thenReturn(user);

	        Users result = service.update(user1.getId(), user);
	        assertEquals(user, result);
	        verify(repository).findById(user1.getId());
	        verify(repository).save(user);
	    }

	@Test
    void test_Update_ThrowsException() {
        when(repository.findById(3)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            service.update(3, user1);
        });
    }

}
