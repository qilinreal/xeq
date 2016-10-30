package com.ssh.xep.service.impl;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.JobInfoDao;
import com.ssh.xep.entity.JobInfo;
import com.ssh.xep.service.JobInfoService;

@Service("jobInfoService")
public class JobInfoServiceImpl implements JobInfoService {
	@Autowired
	private JobInfoDao dao;

	@Deprecated
	public JobInfo load(Integer id) {
		return dao.load(id);
	}

	public JobInfo get(Integer id) throws DocumentException {
		JobInfo info = dao.get(id);
		return info;
	}

	public List<JobInfo> findAll() throws DocumentException {
		List<JobInfo> infos = dao.findAll();
		return infos;
	}

	public List<JobInfo> findAll(Integer userId) throws DocumentException {
		List<JobInfo> infos = dao.findAll(userId);
		return infos;
	}

	public void pessist(JobInfo entity) {
		dao.persist(entity);
	}

	public Integer save(JobInfo entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(JobInfo entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
