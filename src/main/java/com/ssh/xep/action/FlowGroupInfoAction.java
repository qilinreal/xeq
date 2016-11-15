package com.ssh.xep.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.FlowGroupInfoService;

// 还没有增加创建

@Namespace("/flowGroup")
@Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp")
public class FlowGroupInfoAction extends ActionSupport implements ModelDriven<FlowGroupInfo>, Preparable {

	private static final long serialVersionUID = 2242673720623311926L;

	private static final Logger LOGGER = Logger.getLogger(FlowGroupInfoAction.class);

	private List<FlowGroupInfo> groups;
	private FlowGroupInfo group;
	private Integer groupId;

	@Autowired
	private FlowGroupInfoService service;

	public List<FlowGroupInfo> getGroups() {
		return groups;
	}

	public void setGroups(List<FlowGroupInfo> groups) {
		this.groups = groups;
	}

	public FlowGroupInfo getGroup() {
		return group;
	}

	public void setGroup(FlowGroupInfo group) {
		this.group = group;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void prepare() throws Exception {
	}

	@Override
	public FlowGroupInfo getModel() {
		if (group != null) {
			return group;
		}
		group = new FlowGroupInfo();
		return group;
	}

	// 此时和用户无关
	@Action("view")
	public String view() throws Exception {
		groups = service.findAll();
		return SUCCESS;
	}
}
