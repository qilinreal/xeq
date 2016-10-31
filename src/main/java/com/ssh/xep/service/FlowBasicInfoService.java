package com.ssh.xep.service;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;

import com.ssh.xep.entity.FlowBasicInfo;

public interface FlowBasicInfoService {
	FlowBasicInfo load(Integer id);

	FlowBasicInfo get(Integer id) throws DocumentException;

	List<FlowBasicInfo> findAll() throws DocumentException;

	List<FlowBasicInfo> findAll(Integer userId, Integer groupId, int[] auths) throws DocumentException;
	
	List<FlowBasicInfo> findAll(Integer userId, int[] auths) throws DocumentException;

	void pessist(FlowBasicInfo entity);

	/**
	 * @param entity
	 *            传入的实体
	 * @return 生成的ID
	 * @throws ParserConfigurationException
	 */
	Integer save(FlowBasicInfo entity) throws ParserConfigurationException;

	void saveOrUpdate(FlowBasicInfo entity) throws ParserConfigurationException;

	void delete(Integer id);

	void flush();
}
