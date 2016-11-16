package com.ssh.xep.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by qi_l on 2016/11/16.
 * 读取user.properties配置
 */
public class UserProperties {
	public static final String HOME;
	public static final String TMP;

	static {
		String home = System.getProperty("user.dir");
		String tmp = System.getProperty("java.io.tmpdir");

		String path = Flow2JobImpl.class.getClassLoader().getResource("user.properties").getPath();
		Properties p = new Properties();
		try {
			FileInputStream fis = new FileInputStream(path);
			p.load(fis);
			home = p.getProperty("home");
			tmp = p.getProperty("tmp");
			fis.close();
		} catch (IOException e) {
		}

		HOME = home;
		TMP = tmp;
	}
}
