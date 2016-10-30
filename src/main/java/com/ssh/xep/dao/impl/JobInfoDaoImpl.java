package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.JobInfoDao;
import com.ssh.xep.entity.JobInfo;

@Repository("jobInfoDao")
public class JobInfoDaoImpl implements JobInfoDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public JobInfo load(Integer id) {
		return (JobInfo) getSession().load(JobInfo.class, id);
	}

	public JobInfo get(Integer id) {
		return (JobInfo) getSession().get(JobInfo.class, id);
	}

	public List<JobInfo> findAll() {
		List<JobInfo> infos = getSession().createQuery("from JobInfo").list();
		return infos;
	}

	public void persist(JobInfo entity) {
		getSession().persist(entity);
	}

	public Integer save(JobInfo entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(JobInfo entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		JobInfo entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<JobInfo> findAll(Integer userId) {
		List<JobInfo> infos = getSession().createQuery("from JobInfo where userId=?0").list();
		return infos;
	}

	public List<JobInfo> findAll(Integer userId, Integer flowBasicInfoId) {
		List<JobInfo> infos = getSession().createQuery("from JobInfo where userId=?0 and flowBasicInfoId=?1")
				.setInteger("0", userId).setInteger("1", flowBasicInfoId).list();
		return infos;
	}

}
