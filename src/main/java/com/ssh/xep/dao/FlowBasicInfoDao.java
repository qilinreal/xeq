package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.FlowBasicInfo;

public interface FlowBasicInfoDao extends GenericDao<FlowBasicInfo, Integer> {
	List<FlowBasicInfo> findAll(Integer userId, Integer groupId, int[] auths, String startDate, String endDate);
	List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate);
}
