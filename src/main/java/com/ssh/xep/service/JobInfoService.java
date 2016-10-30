package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.JobInfo;

public interface JobInfoService {
	JobInfo load(Integer id);

	JobInfo get(Integer id) throws DocumentException;

	List<JobInfo> findAll() throws DocumentException;

	List<JobInfo> findAll(Integer userId) throws DocumentException;

	void pessist(JobInfo entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(JobInfo entity) throws ParserConfigurationException;

	void saveOrUpdate(JobInfo entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
