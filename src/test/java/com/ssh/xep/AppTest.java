package com.ssh.xep;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.service.FlowBasicInfoService;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml" })
public class AppTest {
	private static final Logger LOGGER = Logger.getLogger(AppTest.class);

	@Autowired
	private FlowBasicInfoService service;

	@Test
	public void save() throws ParserConfigurationException {
		FlowBasicInfo info = new FlowBasicInfo();
		info.setBpmn("123");
		info.setFlowNum(123);
		info.setName("444");
		info.setUserId(3424);
		Integer id = service.save(info);
		System.out.println(id);
	}
	
	@Test
	public void findAll() throws DocumentException {
		List<FlowBasicInfo> infos = service.findAll();
		for(FlowBasicInfo f : infos) {
			System.out.println(f.getId()+"--"+f.getName()+"--"+f.getUserId()+"--"+f.getFlowNum()+"--"+f.getBpmn());
		}
	}
}
