package com.cts.cms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.cts.cms.exception.MenuItemNotFoundException;
import com.cts.cms.model.Menu;
import com.cts.cms.repository.MenuRepository;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

	@Mock
	private MenuRepository repository;

	@InjectMocks
	private MenuService service = new MenuServiceImpl();

	private Menu item1;
	private Menu item2;
	private List<Menu> itemList;

	@BeforeEach
	public void setup() {

		item1 = new Menu(11, "pizza", 100.0, null);
		item2 = new Menu(12, "kachori", 20.0, null);

		itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);

	}

	@Test
	    void test_Create_Items() {
	        when(repository.save(item1)).thenReturn(item1);

	        Menu result = service.createItems(item1);

	        assertEquals(item1, result);
	        verify(repository).save(item1);
	    }

	@Test
	void test_Find_By_Id() throws MenuItemNotFoundException {
        when(repository.findById(11)).thenReturn(Optional.of(item1));

        Menu result = service.findById(11);

        assertEquals(item1, result);
        verify(repository).findById(11);

        assertThrows(MenuItemNotFoundException.class, () -> {
            service.findById(4);
        });
    }

	@Test
	void test_Find_All_Items() {
		List<Menu> menuList = new ArrayList<>();
		menuList.add(item1);
		menuList.add(item2);

		when(repository.findAll()).thenReturn(menuList);

		List<Menu> result = service.findAll();

		assertEquals(menuList, result);
		verify(repository).findAll();
	}

	@Test
	void test_Delete_By_Id() throws MenuItemNotFoundException {
	        when(repository.findById(11)).thenReturn(Optional.of(item1));

	        service.deleteById(11);

	        verify(repository).findById(11);
	        verify(repository).deleteById(11);

	        assertThrows(MenuItemNotFoundException.class, () -> {
	            service.deleteById(4);
	        });
	    }

	@Test
    void test_Update_Item() throws MenuItemNotFoundException {
		when(repository.findById(item1.getId())).thenReturn(Optional.of(item1));
        Menu item = new Menu(11,"burger",80, null);
        when(repository.save(item)).thenReturn(item);

        Menu result = service.update(item1.getId(),item);
        assertEquals(item, result);
        verify(repository).findById(item1.getId());
        verify(repository).save(item);

        assertThrows(MenuItemNotFoundException.class, () -> {
            service.update(4, item1);
        });
    }
}
