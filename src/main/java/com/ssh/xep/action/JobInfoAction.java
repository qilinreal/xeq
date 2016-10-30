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
import com.ssh.xep.entity.JobInfo;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.service.JobInfoService;
import com.ssh.xep.util.JobJSON;

@Namespace("/job")
@Result(name = "error", location = "/WEB-INF/error.jsp")
public class JobInfoAction extends ActionSupport implements ModelDriven<JobInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(JobInfoAction.class);

	private JobInfo info;
	private List<JobInfo> infos;

	@Autowired
	private JobInfoService service;
	@Autowired
	private FlowBasicInfoService flowService;

	public JobInfo getInfo() {
		return info;
	}

	public void setInfo(JobInfo info) {
		this.info = info;
	}

	public List<JobInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<JobInfo> infos) {
		this.infos = infos;
	}

	public void prepare() throws Exception {
	}

	public JobInfo getModel() {
		if (info != null) {
			return info;
		}
		info = new JobInfo();
		return info;
	}

	@Override
	@Action(value = "start", results = { @Result(name = SUCCESS, location = "/WEB-INF/success.jsp") })
	public String execute() throws Exception {
		LOGGER.info("启动指定任务");
		String flowBasicInfoId = ServletActionContext.getRequest().getParameter("flowBasicInfoId");
		if (flowBasicInfoId == null) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "流程ID缺失");
			return ERROR;
		}
		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
		FlowBasicInfo flowInfo = flowService.get(Integer.parseInt(flowBasicInfoId));
		JobJSON json = new JobJSON();
		json.init(flowInfo.getBpmn());
		info = new JobInfo();
		info.setUserId(userId);
		info.setProcessInfo(json.getJSON());
		service.save(info);

		return SUCCESS;
	}
}
