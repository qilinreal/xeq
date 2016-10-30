package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.DirectoryDao;
import com.ssh.xep.entity.Directory;

@Repository("directoryDao")
public class DirectoryDaoImpl implements DirectoryDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public Directory load(Integer id) {
		return (Directory) getSession().load(Directory.class, id);
	}

	public Directory get(Integer id) {
		return (Directory) getSession().get(Directory.class, id);
	}

	public List<Directory> findAll() {
		List<Directory> infos = getSession().createQuery("from Directory").list();
		return infos;
	}

	public void persist(Directory entity) {
		getSession().persist(entity);
	}

	public Integer save(Directory entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(Directory entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		Directory entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<Directory> findAll(Integer userId) {
		List<Directory> infos = getSession().createQuery("from Directory where userId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

}
