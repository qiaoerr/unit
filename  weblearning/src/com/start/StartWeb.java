package com.start;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class StartWeb {

	public static void main(String[] args) throws IOException,
			ServletException, LifecycleException, InterruptedException {

		String currentDir = new File(".").getCanonicalPath();
		String tomcatDir = currentDir + File.separatorChar + "tomcat";
		String webRoot = currentDir + File.separatorChar + "WebRoot";
		Tomcat tomcat = new Tomcat();
		Connector connector = tomcat.getConnector();
		connector.setURIEncoding("GBK");
		tomcat.setBaseDir(tomcatDir);
		tomcat.setPort(8080);
		tomcat.addWebapp("/unit", webRoot);
		tomcat.start();
		tomcat.getServer().await();
	}
}