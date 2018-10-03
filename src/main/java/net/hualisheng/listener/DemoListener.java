package net.hualisheng.listener;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;
@Component
public class DemoListener implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
			Set<SessionTrackingMode> defaultSessionTrackingModes = servletContext.getDefaultSessionTrackingModes();
			Iterator<SessionTrackingMode> iterator = defaultSessionTrackingModes.iterator();
			while (iterator.hasNext()) {
				SessionTrackingMode next = iterator.next();
				System.err.println(next);
//				next.SSL.COOKIE.name();
				System.err.println("已经进入了ServletContextInitializer");
				
			}
	}

}
