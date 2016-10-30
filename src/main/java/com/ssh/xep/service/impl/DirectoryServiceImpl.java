package com.ssh.xep.service.impl;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.DirectoryDao;
import com.ssh.xep.entity.Directory;
import com.ssh.xep.service.DirectoryService;

@Service("directoryService")
public class DirectoryServiceImpl implements DirectoryService {
	@Autowired
	private DirectoryDao dao;

	@Deprecated
	public Directory load(Integer id) {
		return dao.load(id);
	}

	public Directory get(Integer id) throws DocumentException {
		return dao.get(id);
	}

	public List<Directory> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<Directory> findAll(Integer userId) throws DocumentException {
		return dao.findAll(userId);
	}

	public void pessist(Directory entity) {
		dao.persist(entity);
	}

	public Integer save(Directory entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(Directory entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
