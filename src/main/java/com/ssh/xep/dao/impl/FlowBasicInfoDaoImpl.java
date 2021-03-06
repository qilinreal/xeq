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

	public List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate) {
		String sql = "from FlowBasicInfo where 1=1 and (userId=?0 ";
		if (auths != null && auths.length != 0) {
			sql += "or auth in (";
			for (int auth : auths) {
				sql += auth;
				sql += ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += "))";
		}
		sql += " and (createDate between ?1 and ?2)";
		List<FlowBasicInfo> infos = getSession().createQuery(sql).setInteger("0", userId).setString("1", startDate).setString("2", endDate).list();
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId, Integer groupId, int[] auths, String startDate, String endDate) {
		String sql = "from FlowBasicInfo where groupId=?0 and (userId=?1 ";
		if (auths != null && auths.length != 0) {
			sql += "or auth in (";
			for (int auth : auths) {
				sql += auth;
				sql += ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += ")";
		}
		sql += ")";
		sql += " and (createDate between ?2 and ?3)";
		List<FlowBasicInfo> infos = getSession().createQuery(sql).setInteger("1", userId).setInteger("0", groupId).setString("2", startDate).setString("3", endDate).list();
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
