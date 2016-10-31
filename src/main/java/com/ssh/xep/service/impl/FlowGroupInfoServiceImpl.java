package com.ssh.xep.service.impl;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.FlowGroupInfoDao;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.service.FlowGroupInfoService;

@Service("flowGroupInfoService")
public class FlowGroupInfoServiceImpl implements FlowGroupInfoService {
	@Autowired
	private FlowGroupInfoDao dao;

	@Override
	public FlowGroupInfo load(Integer id) {
		return dao.load(id);
	}

	@Override
	public FlowGroupInfo get(Integer id) throws DocumentException {
		FlowGroupInfo info = dao.get(id);
		return info;
	}

	@Override
	public List<FlowGroupInfo> findAll() throws DocumentException {
		List<FlowGroupInfo> infos = dao.findAll();
		return infos;
	}

	@Override
	public void pessist(FlowGroupInfo entity) {
		dao.persist(entity);
	}

	@Override
	public Integer save(FlowGroupInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	@Override
	public void saveOrUpdate(FlowGroupInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void delete(Integer id) {
		dao.delete(id);
	}

	@Override
	public void flush() {
		dao.flush();
	}

}
