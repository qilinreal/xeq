package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tools", catalog = "xep")
public class Tools implements Serializable {

	private static final long serialVersionUID = 7833103177618986871L;

	private int id;
	private int toolId;
	private String toolName;
	private int userId;
	private int isShared;
	private int toolTypeId;
	private String addedTime;
	private int isCreatedByUser;
	private String description;
	private String xmlPath;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "tool_id")
	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	@Column(name = "tool_name")
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "is_shared")
	public int getIsShared() {
		return isShared;
	}

	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}

	@Column(name = "tool_type_id")
	public int getToolTypeId() {
		return toolTypeId;
	}

	public void setToolTypeId(int toolType) {
		this.toolTypeId = toolType;
	}

	@Column(name = "added_time")
	public String getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}

	@Column(name = "is_created_by_user")
	public int getIsCreatedByUser() {
		return isCreatedByUser;
	}

	public void setIsCreatedByUser(int isCreatedByUser) {
		this.isCreatedByUser = isCreatedByUser;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "xml_path")
	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

}
