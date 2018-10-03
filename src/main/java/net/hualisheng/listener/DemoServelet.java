package net.hualisheng.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class DemoServelet implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
			System.err.println(sce.getSource());
			System.err.println(sce.getServletContext());
			System.err.println("已经进入了DemoServelet");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.err.println(sce.getSource());
		System.err.println(sce.getServletContext());

	}

}
