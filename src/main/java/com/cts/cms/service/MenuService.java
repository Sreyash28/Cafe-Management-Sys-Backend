package com.cts.cms.service;

import java.util.List;

import com.cts.cms.exception.MenuItemNotFoundException;
import com.cts.cms.model.Menu;

public interface MenuService {

	Menu createItems(Menu items);

	List<Menu> findAll();

	Menu findById(int id) throws MenuItemNotFoundException;

	void deleteById(int id) throws MenuItemNotFoundException;

	Menu update(int id, Menu item);

}
