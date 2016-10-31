package com.ssh.xep.service.impl;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.FlowBasicInfoDao;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.service.FlowBasicInfoService;

@Service("flowBasicInfoService")
public class FlowBasicInfoServiceImpl implements FlowBasicInfoService {
	@Autowired
	private FlowBasicInfoDao dao;

	public FlowBasicInfo load(Integer id) {
		return dao.load(id);
	}

	public FlowBasicInfo get(Integer id) throws DocumentException {
		FlowBasicInfo info = dao.get(id);
		return info;
	}

	public List<FlowBasicInfo> findAll() throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll();
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId, int[] auths) throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll(userId, auths);
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId, Integer groupId, int[] auths) throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll(userId, groupId, auths);
		return infos;
	}

	public void pessist(FlowBasicInfo entity) {
		dao.persist(entity);
	}

	public Integer save(FlowBasicInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(FlowBasicInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
