package com.ssh.xep.dao;

import java.util.List;

import com.ssh.xep.entity.File;

public interface FileDao extends GenericDao<File, Integer> {
	List<File> findAll(Integer userId);
}
