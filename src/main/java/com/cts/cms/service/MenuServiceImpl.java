package com.cts.cms.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cms.exception.MenuItemNotFoundException;
import com.cts.cms.model.Menu;
import com.cts.cms.repository.MenuRepository;

import ch.qos.logback.classic.Logger;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuRepository repository;

	@Override
	public Menu createItems(Menu items) {
		logger.info("Creating item: {}", items);
		return repository.save(items);
	}

	@Override
	public List<Menu> findAll() {
		logger.info("Finding all items");
		return repository.findAll();
	}

	@Override
	public Menu findById(int id) throws MenuItemNotFoundException {
		logger.info("Finding item by ID: {}", id);
		Optional<Menu> optional = repository.findById(id);
		if (optional.isEmpty()) {
			logger.warn("Item not found with ID: {}", id);
			throw new MenuItemNotFoundException("Item not found with ID: " + id);
		}
		Menu item = optional.get();
		logger.info("Found item: {}", item);
		return item;
	}

	@Override
	public void deleteById(int id) throws MenuItemNotFoundException {
		logger.info("Deleting item by ID: {}", id);
		findById(id);
		repository.deleteById(id);
		logger.info("Deleted item with ID: {}", id);

	}

	@Override
	public Menu update(int id, Menu item) {
		logger.info("Updating item with ID: {}, new item data: {}", id, item);
		findById(id);
		return repository.save(item);
	}

}
