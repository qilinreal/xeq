package com.ssh.xep.service.impl;

import com.ssh.xep.dao.FlowGroupInfoDao;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.service.FlowGroupInfoService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

@Service("flowGroupInfoService")
public class FlowGroupInfoServiceImpl implements FlowGroupInfoService {
	@Autowired
	private FlowGroupInfoDao dao;

	public FlowGroupInfo load(Integer id) {
		return dao.load(id);
	}

	public FlowGroupInfo get(Integer id) throws DocumentException {
		FlowGroupInfo info = dao.get(id);
		return info;
	}

	public List<FlowGroupInfo> findAll() throws DocumentException {
		List<FlowGroupInfo> infos = dao.findAll();
		return infos;
	}

	public void pessist(FlowGroupInfo entity) {
		dao.persist(entity);
	}

	public Integer save(FlowGroupInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(FlowGroupInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
