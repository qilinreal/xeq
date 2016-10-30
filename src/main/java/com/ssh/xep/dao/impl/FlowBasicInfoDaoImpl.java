package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.FlowBasicInfoDao;
import com.ssh.xep.entity.FlowBasicInfo;

@Repository("flowBasicInfoDao")
public class FlowBasicInfoDaoImpl implements FlowBasicInfoDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public FlowBasicInfo load(Integer id) {
		return (FlowBasicInfo) getSession().load(FlowBasicInfo.class, id);
	}

	public FlowBasicInfo get(Integer id) {
		return (FlowBasicInfo) getSession().get(FlowBasicInfo.class, id);
	}

	public List<FlowBasicInfo> findAll() {
		List<FlowBasicInfo> infos = getSession().createQuery("from FlowBasicInfo").list();
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId) {
		List<FlowBasicInfo> infos = getSession().createQuery("from FlowBasicInfo where userId=?0")
				.setInteger("0", userId).list();
		return infos;
	}

	public void persist(FlowBasicInfo entity) {
		getSession().persist(entity);
	}

	public Integer save(FlowBasicInfo entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(FlowBasicInfo entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		FlowBasicInfo entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

}
