package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.Directory;;

public interface DirectoryService {
	Directory load(Integer id);

	Directory get(Integer id) throws DocumentException;

	List<Directory> findAll() throws DocumentException;

	List<Directory> findAll(Integer userId) throws DocumentException;

	void pessist(Directory entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(Directory entity) throws ParserConfigurationException;

	void saveOrUpdate(Directory entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
