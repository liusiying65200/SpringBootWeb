package net.hualisheng.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
public class CoustomerErrorPage implements ErrorPageRegistrar {

	

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage e404=new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
		// TODO Auto-generated method stub
		registry.addErrorPages(e404);
	}

}
