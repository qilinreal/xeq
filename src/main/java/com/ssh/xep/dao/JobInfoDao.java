package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.JobInfo;

public interface JobInfoDao extends GenericDao<JobInfo, Integer> {
	List<JobInfo> findAll(Integer userId);
	List<JobInfo> findAll(Integer userId, Integer flowBasicInfoId);
}
