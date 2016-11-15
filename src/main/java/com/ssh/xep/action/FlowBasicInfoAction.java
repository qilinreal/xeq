package com.ssh.xep.action;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.FlowGroupInfo;
import com.ssh.xep.entity.ToolTypes;
import com.ssh.xep.entity.Tools;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.FlowGroupInfoService;
import com.ssh.xep.service.ToolTypesService;
import com.ssh.xep.service.ToolsService;
import com.ssh.xep.util.MakeBpmn;

@Namespace("/flow")
@Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp")
public class FlowBasicInfoAction extends ActionSupport implements ModelDriven<FlowBasicInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(FlowBasicInfoAction.class);

	private FlowBasicInfo info;
	private List<FlowBasicInfo> infos;
	private List<FlowGroupInfo> groups;
	private List<Tools> tools;
	private List<ToolTypes> toolTypes;
	private int groupId;
	private String startDate, endDate;

	@Autowired
	private FlowBasicInfoService service;

	@Autowired
	private ToolsService toolService;

	@Autowired
	private ToolTypesService toolTypesService;

	@Autowired
	private FlowGroupInfoService flowGroupInfoService;

	public List<FlowGroupInfo> getGroups() {
		return groups;
	}

	public void setGroups(List<FlowGroupInfo> groups) {
		this.groups = groups;
	}

	public List<ToolTypes> getToolTypes() {
		return toolTypes;
	}

	public void setToolTypes(List<ToolTypes> toolTypes) {
		this.toolTypes = toolTypes;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public FlowBasicInfo getInfo() {
		return info;
	}

	public void setInfo(FlowBasicInfo info) {
		this.info = info;
	}

	public List<FlowBasicInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FlowBasicInfo> infos) {
		this.infos = infos;
	}

	public List<Tools> getTools() {
		return tools;
	}

	public void setTools(List<Tools> tools) {
		this.tools = tools;
	}

	public void prepare() throws Exception {
	}

	public FlowBasicInfo getModel() {
		if (info != null) {
			return info;
		}
		info = new FlowBasicInfo();
		return info;
	}

	@Override
	@Action("view")
	public String execute() throws Exception {
		LOGGER.info("查询所有流程");
		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
		boolean isAdmin = (Boolean) (ServletActionContext.getRequest().getSession().getAttribute("isAdmin"));
		if (startDate == null) {
			startDate = "1900/01/01";
		}
		if (endDate == null) {
			endDate = "2100/10/10";
		}
		int[] auths;
		if (isAdmin) {
			auths = new int[] { 1, 2 };
		} else {
			auths = new int[] { 2 };
		}
		if (groupId != 0) {
			infos = service.findAll(userId, groupId, auths, startDate, endDate);
		} else {
			infos = service.findAll(userId, auths, startDate, endDate);
		}
		groups = flowGroupInfoService.findAll();

		return SUCCESS;
	}

	@Action(value = "detail")
	public String detail() throws DocumentException, ParserConfigurationException {
		Integer id = info.getId();
		LOGGER.info("查看某个流程详细信息： " + id);
		info = service.get(Integer.valueOf(id));
		if (info == null) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "找不到对象呀。");
			return ERROR;
		}

		return SUCCESS;
	}

	@Action(value = "modify", results = { @Result(name = SUCCESS, location = "/WEB-INF/content/flow/modify.jsp") })
	public String modify() throws DocumentException, ParserConfigurationException, TransformerFactoryConfigurationError,
			TransformerException {
		String id = ServletActionContext.getRequest().getParameter("id");
		LOGGER.info("修改或者创建某个流程： " + id);
		Integer userId = (Integer) ServletActionContext.getRequest().getSession().getAttribute("userId");
		tools = toolService.findAll(userId);
		toolTypes = toolTypesService.findAll();
		groups = flowGroupInfoService.findAll();
		if (id == null) {
			String flowName = ServletActionContext.getRequest().getParameter("flowName");
			String authStr = ServletActionContext.getRequest().getParameter("auth");
			String groupId = ServletActionContext.getRequest().getParameter("groupId");
			if (groupId == null) {
				groupId = "1";
			}
			if (flowName == null || authStr == null) {
				ServletActionContext.getRequest().setAttribute("errorInformation", "缺少参数。");
				return ERROR;
			}

			ServletActionContext.getRequest().setAttribute("create", "创建");
			info = new FlowBasicInfo();
			info.setFlow(new MakeBpmn(String.valueOf(userId)).get());
			info.setFlowNum(0);
			info.setUserId(userId);
			info.setName(flowName);
			info.setAuth((short) 0);
			info.setGroupId(1);
			id = String.valueOf(service.save(info));
			info.setId(Integer.parseInt(id));
		} else {
			ServletActionContext.getRequest().setAttribute("create", "修改");
			info = service.get(Integer.parseInt(id));
			if (info == null) {
				ServletActionContext.getRequest().setAttribute("errorInformation", "找不到对象呀。");
				return ERROR;
			}
		}
		return SUCCESS;
	}

	@Action(value = "modify-commit", results = { @Result(name = SUCCESS, location = "/WEB-INF/success.jsp") })
	public String modifyCommit() throws ParserConfigurationException {
		if (info.getFlow() == null || info.getFlow().equals("") || info.getId() == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "数据缺失");
			return ERROR;
		}
		if (info.getUserId() == 0) {
			Integer userId = (Integer) ServletActionContext.getRequest().getSession().getAttribute("userId");
			info.setUserId(userId);
		}
		service.saveOrUpdate(info);
		return SUCCESS;
	}
}
