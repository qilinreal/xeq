package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fileupload_library", catalog = "xep")
public class UserDir implements Serializable {

	private static final long serialVersionUID = 4370589360268200507L;

	private int id;
	private int userIdId;
	private int rootFolderId;
	private String createTime;
	private String updateTime;
	private int deleted;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "user_id_id", nullable = false)
	public int getUserIdId() {
		return userIdId;
	}

	public void setUserIdId(int userIdId) {
		this.userIdId = userIdId;
	}

	@Column(name = "root_folder_id", nullable = false)
	public int getRootFolderId() {
		return rootFolderId;
	}

	public void setRootFolderId(int rootFolderId) {
		this.rootFolderId = rootFolderId;
	}

	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "deleted")
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
