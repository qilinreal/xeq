package com.ssh.xep.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FINISH

/**
 * 根据流程信息生成JOB
 */
public class Flow2JobImpl implements Flow2Job {
	private static final String loadNamePrefix = "com.ssh.xep.bpmn.";

	public Flow2JobImpl(int jobId) {
		jobSeed = String.valueOf(jobId);
	}

	private void setHeader(Element process, String userId, String jobId) {
		process.addAttribute("id", loadNamePrefix + userId + "." + jobId);
		process.addAttribute("name", jobId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssh.xep.util.Flow2Job#flow2Job(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public String flow2Job(String userId, String jobId, String flow, String jobTools)
			throws DocumentException, IOException {
		Document document = DocumentHelper.parseText(flow);
		Element eFlow = document.getRootElement();
		document = DocumentHelper.parseText(jobTools);
		Element eTools = document.getRootElement();
		Element process = eFlow.element("process");
		setHeader(process, userId, jobId);

		// 将输出文件加入索引
		indexOutputFileFromTool(eTools);

		List<Element> scripts = process.elements("scriptTask");
		List<Element> tools = eTools.elements("tool");
		for (Element es : scripts) {
			for (Element et : tools) {
				if (es.attributeValue("id").equals(et.attributeValue("id"))) {
					combine(es, et);
					break;
				}
			}
		}

		return document.asXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssh.xep.util.Flow2Job#flow2Job(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public String flow2Job(String userId, String jobId, String flow) throws IOException, DocumentException {
		Document document = DocumentHelper.parseText(flow);
		Element eFlow = document.getRootElement();
		Element process = eFlow.element("process");

		// 将输出文件加入索引
		indexOutputFileFromFlow(process);

		List<Element> scripts = process.elements("scriptTask");
		for (Element es : scripts) {
			Element tool = es.element("tool");
			es.remove(tool);
			combine(es, tool);
		}

		return document.asXML();
	}

	// 将flow和jobTools进行合并，生成可以使用的bpmn
	private void combine(Element es, Element et) throws IOException {
		String text = getScriptStr();
		int domId = Integer.parseInt(es.attributeValue("id").substring(13));

		// 额外信息，目前包括了tool-id
		es.addAttribute("tool-id", et.attributeValue("tool-id"));

		StringBuilder sb = new StringBuilder();
		// 判断工具类型：目前有jar、python、exe、sh
		String toolType = et.attributeValue("interpreter");
		if (toolType.equals("java")) {
			sb.append("java -jar ");
		} else if (toolType.equals("python")) {
			sb.append("python ");
		} else if (toolType.equals("exe")) {
			// DO NOTHING
		} else if (toolType.equals("sh")) {
			sb.append("bash ");
		}

		// 添加工具
		sb.append(et.attributeValue("path"));
		String toolName = sb.toString();
		sb = new StringBuilder();

		// 生成工具的参数
		List<Element> params = et.element("params").elements();
		for (Element p : params) {
			if (p.getName().equals("input")) {
				String ref = p.attributeValue("ref");
				String value = p.attributeValue("value");
				if (ref.equals("fromDatabase")) {
					sb.append("$");
					sb.append(value);
					sb.append(" ");
				} else if (ref.equals("fromFlow")) {
					int pos = value.lastIndexOf('-');
					if (fileInfos.get(value).equals("0")) {
						sb.append(UserProperties.TMP + generateFileName(value.substring(0, pos), value.substring(pos)));
					} else {
						sb.append("$");
						sb.append(fileInfos.get(value));
					}
					sb.append(" ");
				}
			} else if (p.getName().equals("output")) {
				String value = p.attributeValue("value");
				if (value.equals("0")) {
					String path = UserProperties.TMP + generateFileName(et.attributeValue("id"), p.attributeValue("id"));
					sb.append(path);
					sb.append(" ");
				} else {
					sb.append("$");
					sb.append(value);
					sb.append(" ");
				}
			} else if (p.getName().equals("param")) {
				String type = p.attributeValue("type");
				if (type.equals("fixed")) {
					if (p.attributeValue("selected").equals("1")) {
						sb.append(p.attributeValue("value"));
						sb.append(" ");
					}
				} else if (type.equals("select")) {
					String value = p.attributeValue("value");
					if (value != null && value.equals("") == false) {
						sb.append(value);
						sb.append(" ");
					}
				} else if (type.equals("text")) {
					String value = p.attributeValue("value");
					if (value != null && value.equals("") == false) {
						sb.append(value);
						sb.append(" ");
					}
				} else {
					// 整数、浮点数等类型
					String value = p.attributeValue("value");
					if (value != null && value.equals("") == false) {
						sb.append(value);
						sb.append(" ");
					}
				}
			}
		}

		// 至此，执行命令生成结束
		String commandLine = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";

		// 生成执行的字符串
		text = String.format(text, toolName, commandLine, domId);

		es.addElement("script").setText(text);
	}

	/**
	 * 获取script节点下需要执行的文本
	 *
	 * @return
	 * @throws IOException
	 */
	private String getScriptStr() throws IOException {
		FileInputStream fis = new FileInputStream("bpmn_script_template.dat");
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}
		fis.close();
		return sb.toString();
	}

	/**
	 * 生成输出文件的索引，方便输入文件进行查找
	 */
	// 来自流程，节点名字是process
	private void indexOutputFileFromFlow(Element process) {
		fileInfos = new HashMap<String, String>();

		List<Element> el = process.elements("scriptTask");
		for (Element e : el) {
			if (e.element("tool") != null)
				e = e.element("tool");
			indexOutputFile(e);
		}
	}

	// 来自工具，节点名字是tools
	private void indexOutputFileFromTool(Element tools) {
		fileInfos = new HashMap<String, String>();

		List<Element> el = tools.elements("tool");
		for (Element e : el) {
			indexOutputFile(e);
		}
	}

	/**
	 * 将该节点上的输出文件加入索引
	 *
	 * @param e
	 */
	private void indexOutputFile(Element tool) {
		List<Element> el = tool.element("params").elements("output");
		for (Element e : el) {
			StringBuilder index = new StringBuilder(tool.attributeValue("id"));
			index.append("-");
			index.append(e.attributeValue("id"));
			fileInfos.put(index.toString(), e.attributeValue("value"));
		}
	}

	/**
	 * 目前将文件放进了tmp文件夹中
	 *
	 * @param domId
	 * @param fileId 输出文件在dom中的output节点上的id
	 * @return
	 */
	private String generateFileName(String domId, String fileId) {
		return jobSeed + "-" + domId + "-" + fileId;
	}

	// 用来生成独一无二的文件
	private String jobSeed;
	private Map<String, String> fileInfos;
}

/**
 * 储存输出文件的信息
 */
// class FileInfo {
// public String value;
// }