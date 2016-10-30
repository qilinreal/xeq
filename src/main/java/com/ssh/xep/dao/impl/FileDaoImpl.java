package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.FileDao;
import com.ssh.xep.entity.File;

@Repository("fileDao")
public class FileDaoImpl implements FileDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public File load(Integer id) {
		return (File) getSession().load(File.class, id);
	}

	public File get(Integer id) {
		return (File) getSession().get(File.class, id);
	}

	public List<File> findAll() {
		List<File> infos = getSession().createQuery("from File").list();
		return infos;
	}

	public void persist(File entity) {
		getSession().persist(entity);
	}

	public Integer save(File entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(File entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		File entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<File> findAll(Integer userId) {
		List<File> infos = getSession().createQuery("from File where userId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

}
