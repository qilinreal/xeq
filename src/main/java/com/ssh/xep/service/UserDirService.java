package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.UserDir;

public interface UserDirService {
	UserDir load(Integer id);

	UserDir get(Integer id) throws DocumentException;

	List<UserDir> findAll() throws DocumentException;

	List<UserDir> findAll(Integer userId) throws DocumentException;

	void pessist(UserDir entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(UserDir entity) throws ParserConfigurationException;

	void saveOrUpdate(UserDir entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
