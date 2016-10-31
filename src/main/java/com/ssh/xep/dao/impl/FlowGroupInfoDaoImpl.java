package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.FlowGroupInfoDao;
import com.ssh.xep.entity.FlowGroupInfo;

@Repository("flowGroupInfoDao")
public class FlowGroupInfoDaoImpl implements FlowGroupInfoDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public FlowGroupInfo load(Integer id) {
		return (FlowGroupInfo) getSession().load(FlowGroupInfo.class, id);
	}

	@Override
	public FlowGroupInfo get(Integer id) {
		return (FlowGroupInfo) getSession().get(FlowGroupInfo.class, id);
	}

	@Override
	public List<FlowGroupInfo> findAll() {
		List<FlowGroupInfo> infos = getSession().createQuery("from FlowGroupInfo").list();
		return infos;
	}

	@Override
	public void persist(FlowGroupInfo entity) {
		getSession().persist(entity);
	}

	@Override
	public Integer save(FlowGroupInfo entity) {
		return (Integer) getSession().save(entity);
	}

	@Override
	public void saveOrUpdate(FlowGroupInfo entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(Integer id) {
		FlowGroupInfo entity = load(id);
		getSession().delete(entity);
	}

	@Override
	public void flush() {
		getSession().flush();
	}

}
