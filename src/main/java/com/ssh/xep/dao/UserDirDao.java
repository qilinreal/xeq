package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.UserDir;

public interface UserDirDao extends GenericDao<UserDir, Integer> {
	List<UserDir> findAll(Integer userId);
}
