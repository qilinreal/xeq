package com.ssh.xep.service.impl;

import com.ssh.xep.dao.UserDirDao;
import com.ssh.xep.entity.UserDir;
import com.ssh.xep.service.UserDirService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@Service("userDirService")
public class UserDirServiceImpl implements UserDirService {
	@Autowired
	private UserDirDao dao;

	@Deprecated
	public UserDir load(Integer id) {
		return dao.load(id);
	}

	public UserDir get(Integer id) throws DocumentException {
		return dao.get(id);
	}

	public List<UserDir> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<UserDir> findAll(Integer userId) throws DocumentException {
		return dao.findAll(userId);
	}

	public void pessist(UserDir entity) {
		dao.persist(entity);
	}

	public Integer save(UserDir entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(UserDir entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
