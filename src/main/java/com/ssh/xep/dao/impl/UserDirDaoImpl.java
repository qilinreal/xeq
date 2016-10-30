package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.UserDirDao;
import com.ssh.xep.entity.UserDir;

@Repository("userDirDao")
public class UserDirDaoImpl implements UserDirDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public UserDir load(Integer id) {
		return (UserDir) getSession().load(UserDir.class, id);
	}

	public UserDir get(Integer id) {
		return (UserDir) getSession().get(UserDir.class, id);
	}

	public List<UserDir> findAll() {
		List<UserDir> infos = getSession().createQuery("from UserDir").list();
		return infos;
	}

	public void persist(UserDir entity) {
		getSession().persist(entity);
	}

	public Integer save(UserDir entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(UserDir entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		UserDir entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<UserDir> findAll(Integer userId) {
		List<UserDir> infos = getSession().createQuery("from UserDir where userId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

}
