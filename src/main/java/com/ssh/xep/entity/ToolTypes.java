package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tool_types", catalog = "xep")
public class ToolTypes implements Serializable {

	private static final long serialVersionUID = 7833103177618986871L;

	private int id;
	private String typeName;
	private int addedUserId;
	private String showColor;
	private String description;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "added_user_id")
	public int getAddedUserId() {
		return addedUserId;
	}

	public void setAddedUserId(int addedUserId) {
		this.addedUserId = addedUserId;
	}

	@Column(name = "show_color")
	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

}
