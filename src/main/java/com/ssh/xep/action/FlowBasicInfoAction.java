package com.ssh.xep.action;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

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
import com.ssh.xep.entity.ToolTypes;
import com.ssh.xep.entity.Tools;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.ToolTypesService;
import com.ssh.xep.service.ToolsService;

@Namespace("/flow")
@Result(name = ActionSupport.ERROR, location = "/WEB-INF/error.jsp")
public class FlowBasicInfoAction extends ActionSupport implements ModelDriven<FlowBasicInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(FlowBasicInfoAction.class);

	private FlowBasicInfo info;
	private List<FlowBasicInfo> infos;
	private List<Tools> tools;
	private List<ToolTypes> toolTypes;

	@Autowired
	private FlowBasicInfoService service;

	@Autowired
	private ToolsService toolService;

	@Autowired
	private ToolTypesService toolTypesService;

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
		infos = service.findAll(userId);

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
	public String modify() throws DocumentException, ParserConfigurationException {
		String id = ServletActionContext.getRequest().getParameter("id");
		LOGGER.info("修改或者创建某个流程： " + id);
		Integer userId = (Integer) ServletActionContext.getRequest().getSession().getAttribute("userId");
		tools = toolService.findAll(userId);
		toolTypes = toolTypesService.findAll();
		if (id == null) {
			ServletActionContext.getRequest().setAttribute("create", "创建");
			info = new FlowBasicInfo();
			info.setBpmn("");
			info.setFlowNum(0);
			info.setUserId(userId);
			info.setName("NO NAME");
			id = String.valueOf(service.save(info));
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
		if (info.getBpmn() == null || info.getBpmn().equals("") || info.getId() == 0) {
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
