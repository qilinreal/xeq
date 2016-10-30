package com.ssh.xep.util;

import java.io.IOException;

import org.dom4j.DocumentException;

public interface Flow2Job {

	String flow2Job(String userId, String jobId, String flow, String jobTools) throws DocumentException, IOException;

	/**
	 * 这个将jobTools放进了flow之中
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	String flow2Job(String userId, String jobId, String flow) throws IOException, DocumentException;

}