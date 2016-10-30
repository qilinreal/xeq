package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.Directory;

public interface DirectoryDao extends GenericDao<Directory, Integer> {
	List<Directory> findAll(Integer userId);
}
