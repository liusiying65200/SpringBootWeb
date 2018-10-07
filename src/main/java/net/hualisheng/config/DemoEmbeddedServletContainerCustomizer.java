package net.hualisheng.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;

/**
 * EmbeddedServletContainerCustomizer已经被WebServerFactoryCustomizer替代 ，在spring
 * boot 2.x中
 */
@Component
public class DemoEmbeddedServletContainerCustomizer implements WebServerFactoryCustomizer<WebServerFactory> {
	public void run() {
		System.err.println("那就使用一会");
	}

	@Override
	public void customize(WebServerFactory factory) {
		// TODO Auto-generated method stub
		System.err.println("容器类型:" + factory);

	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
			@Override
			public void customize(ConfigurableWebServerFactory factory) {
//                factory.setPort(8088);
				System.err.println(factory.getClass().getName());
//				TomcatWebServerFactory tomcat = (ConfigurableTomcatWebServerFactory) factory;
//            	String pathname="D:/spring-boot/tomcat/logs";
//				File baseDirectory=new File(pathname);
//				tomcat.setBaseDirectory(baseDirectory);
//				tomcat.setPort(8888);
////				Accesslog contextValves=new Accesslog();
////				tomcat.addContextValves();
//				tomcat.setPort(8088);
//				Accesslog logPath;
				// tomcat.addContextCustomizers(tomcatContextCustomizers);
				TomcatServletWebServerFactory tomcat=(TomcatServletWebServerFactory) factory;
				run();

				Set<ErrorPage> errPages=new TreeSet<>();
				ErrorPage e404=new ErrorPage(HttpStatus.NOT_FOUND,"error/400.html");
				errPages.add(e404);
				tomcat.setErrorPages(errPages);


				
				
//				tomcat.addEngineValves();
			}
		};
	}

}
