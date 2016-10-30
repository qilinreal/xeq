package com.ssh.xep.service.impl;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.FileDao;
import com.ssh.xep.entity.File;
import com.ssh.xep.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao dao;

	@Deprecated
	public File load(Integer id) {
		return dao.load(id);
	}

	public File get(Integer id) throws DocumentException {
		return dao.get(id);
	}

	public List<File> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<File> findAll(Integer userId) throws DocumentException {
		return dao.findAll(userId);
	}

	public void pessist(File entity) {
		dao.persist(entity);
	}

	public Integer save(File entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(File entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
