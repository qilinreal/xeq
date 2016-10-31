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
import com.ssh.xep.util.Flow2Job;
import com.ssh.xep.util.Flow2JobImpl;

@Namespace("/job")
@Result(name = "error", location = "/WEB-INF/error.jsp")
public class JobInfoAction extends ActionSupport implements ModelDriven<JobInfo>, Preparable {

	private static final long serialVersionUID = -7988746546869953899L;

	private static final Logger LOGGER = Logger.getLogger(JobInfoAction.class);

	private JobInfo info;
	private List<JobInfo> infos;
	private int flowId;
	private int jobId;

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

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

	@Action(value = "create", results = { @Result(name = SUCCESS, location = "/WEB-INF/success.jsp") })
	public String create() throws Exception {
		LOGGER.info("创建指定任务");
		if (flowId == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "流程ID缺失");
			return ERROR;
		}
		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
		FlowBasicInfo flowInfo = flowService.get(flowId);
		info = new JobInfo();
		info.setBgTime(0);
		info.setEdTime(0);
		info.setBpmn("");
		info.setFlowBasicInfoId(flowId);
		info.setProcessInfo(flowInfo.getFlow());
		info.setUserId(userId);
		info.setId(service.save(info));

		Flow2Job flow2Job = new Flow2JobImpl(info.getId());
		info.setBpmn(flow2Job.flow2Job(String.valueOf(userId), String.valueOf(info.getId()), flowInfo.getFlow()));
		service.saveOrUpdate(info);
		return SUCCESS;
	}

	@Override
	@Action(value = "start", results = { @Result(name = SUCCESS, location = "/WEB-INF/success.jsp") })
	public String execute() throws Exception {
		LOGGER.info("启动指定任务");
		if (jobId == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "任务ID缺失");
			return ERROR;
		}
		info = service.get(jobId);

		/// FIXME 没有启动JBPM
		return SUCCESS;
	}

	@Action(value = "createAndStart", results = { @Result(name = SUCCESS, location = "/WEB-INF/success.jsp") })
	public String createAndStart() throws Exception {
		LOGGER.info("创建并启动指定任务");
		if (flowId == 0) {
			ServletActionContext.getRequest().setAttribute("errorInformation", "流程ID缺失");
			return ERROR;
		}

		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
		FlowBasicInfo flowInfo = flowService.get(flowId);
		info = new JobInfo();
		info.setBgTime(0);
		info.setEdTime(0);
		info.setBpmn("");
		info.setFlowBasicInfoId(flowId);
		info.setProcessInfo(flowInfo.getFlow());
		info.setUserId(userId);
		info.setId(service.save(info));

		Flow2Job flow2Job = new Flow2JobImpl(info.getId());
		info.setBpmn(flow2Job.flow2Job(String.valueOf(userId), String.valueOf(info.getId()), flowInfo.getFlow()));
		service.saveOrUpdate(info);
		
		/// FIXME 没有启动JBPM
		return SUCCESS;
	}

	@Action(value = "view", results = { @Result(name = SUCCESS, location = "/WEB-INF/content/job/view.jsp") })
	public String view() throws Exception {
		Integer userId = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("userId"));
		infos = service.findAll(userId);
		return SUCCESS;
	}
}
