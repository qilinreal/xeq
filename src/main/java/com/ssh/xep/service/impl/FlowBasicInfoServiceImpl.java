package com.ssh.xep.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.dao.DirectoryDao;
import com.ssh.xep.dao.FileDao;
import com.ssh.xep.dao.FlowBasicInfoDao;
import com.ssh.xep.dao.ToolTypesDao;
import com.ssh.xep.dao.ToolsDao;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.service.FlowBasicInfoService;
import com.ssh.xep.util.JSON2XML;
import com.ssh.xep.util.XML2JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("flowBasicInfoService")
public class FlowBasicInfoServiceImpl implements FlowBasicInfoService {
	@Autowired
	private FlowBasicInfoDao dao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private DirectoryDao dirDao;

	@Deprecated
	public FlowBasicInfo load(Integer id) {
		return dao.load(id);
	}

	public FlowBasicInfo get(Integer id) throws DocumentException {
		FlowBasicInfo info = dao.get(id);
		if (info != null) {
			info.setBpmn(XML2JSON.xml2JSON(info.getBpmn()));
		}
		return info;
	}

	public List<FlowBasicInfo> findAll() throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll();
		if (infos != null) {
			for (FlowBasicInfo info : infos) {
				info.setBpmn(XML2JSON.xml2JSON(info.getBpmn()));
			}
		}
		return infos;
	}

	public List<FlowBasicInfo> findAll(Integer userId) throws DocumentException {
		List<FlowBasicInfo> infos = dao.findAll(userId);
		if (infos != null) {
			for (FlowBasicInfo info : infos) {
				info.setBpmn(XML2JSON.xml2JSON(info.getBpmn()));
			}
		}
		return infos;
	}

	public void pessist(FlowBasicInfo entity) {
		dao.persist(entity);
	}

	/**
	 * 获取从数据库中取的文件的绝对路径
	 */
	private String fixPath(String jsonStr) {
		JSONObject obj = JSONObject.fromObject(jsonStr);
		JSONArray infos = obj.getJSONArray("Obj");
		fixPath2(infos);
		return infos.toString();
	}

	private void fixPath2(JSONArray infos) {
		Iterator<JSONObject> it = infos.iterator();
		while (it.hasNext()) {
			JSONObject obj = it.next();
			fixPath3(obj.getJSONArray("tool-info"));
		}
	}

	private void fixPath3(JSONArray toolInfos) {
		Iterator<JSONObject> it = toolInfos.iterator();
		while (it.hasNext()) {
			JSONObject obj = it.next();
			if (obj.getString("type").startsWith("db") == false) {
				continue;
			}
			String id = obj.getString("value");
			try {
				obj.put("path", getAbsPath(Integer.parseInt(id)));
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	private String getAbsPath(int fileId) throws IOException {
		com.ssh.xep.entity.File file = fileDao.get(fileId);
		LinkedList<String> pl = new LinkedList<String>();
		int p = file.getFolderIdId();
		while (p != 0) {
			com.ssh.xep.entity.Directory dir = dirDao.get(p);
			pl.add(dir.getName());
			p = dir.getParentIdId();
		}

		FileInputStream fis = new FileInputStream("user.properties");
		Properties properties = new Properties();
		properties.load(fis);
		StringBuilder sb = new StringBuilder();
		sb.append(properties.getProperty("home"));
		fis.close();

		while (pl.isEmpty() == false) {
			sb.append(pl.pollLast());
			sb.append("/");
		}

		sb.append(file.getName());
		return sb.toString();
	}

	public Integer save(FlowBasicInfo entity) throws ParserConfigurationException {
		if (entity != null) {
			if (entity.getBpmn().startsWith("{")) {
				String bpmn = fixPath(entity.getBpmn());
				try {
					entity.setBpmn(JSON2XML.json2XML(entity.getId(), entity.getName(), bpmn));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dao.save(entity);
	}

	public void saveOrUpdate(FlowBasicInfo entity) throws ParserConfigurationException {
		if (entity != null) {
			if (entity.getBpmn().startsWith("{")) {
				String bpmn = fixPath(entity.getBpmn());
				try {
					entity.setBpmn(JSON2XML.json2XML(entity.getId(), entity.getName(), bpmn));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
