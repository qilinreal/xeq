package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.FlowBasicInfo;

public interface FlowBasicInfoDao extends GenericDao<FlowBasicInfo, Integer> {
	List<FlowBasicInfo> findAll(Integer userId);
}
