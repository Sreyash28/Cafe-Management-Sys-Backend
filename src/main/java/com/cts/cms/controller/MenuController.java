package com.cts.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.cms.model.Menu;
import com.cts.cms.service.MenuService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api")
public class MenuController {

	@Autowired
	MenuService service;

	/**
	 * this will return all the items present in Menu
	 * 
	 * @return
	 */

	@GetMapping("/v1/items")
	public ResponseEntity<List<Menu>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	/**
	 * this will return item of respected id
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping("/v1/items/{id}")
	public ResponseEntity<Menu> findById(@PathVariable("id") int id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	/**
	 * this will create Menu Item of in Menu
	 * 
	 * @param items
	 * @return
	 */

	@PostMapping("/v1/items")
	public ResponseEntity<Menu> create(@RequestBody Menu items) {
		ResponseEntity<Menu> responseEntity;
		responseEntity = new ResponseEntity<>(service.createItems(items), HttpStatus.CREATED);
		return responseEntity;
	}

	/**
	 * this will delete the item with respected id
	 * 
	 * @param id
	 */

	@DeleteMapping("/items/{id}")
	public void delete(@PathVariable("id") int id) {
		service.deleteById(id);
	}

	/**
	 * this will update the item with respected id
	 */

	@PutMapping("/items/{id}")
	public Menu update(@PathVariable("id") int id, @RequestBody Menu items) {
		return service.update(id, items);
	}

}
