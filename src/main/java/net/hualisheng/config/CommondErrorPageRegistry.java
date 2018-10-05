package net.hualisheng.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
public class CommondErrorPageRegistry implements ErrorPageRegistrar {

	
	public void addErrorPages(ErrorPage... errorPages) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage e404=new  ErrorPage(HttpStatus.NOT_FOUND,"/error/404.html");
		ErrorPage e500=new  ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error/500.html");		
		ErrorPage arguments=new  ErrorPage(IllegalArgumentException.class,"/error/arguments.html");
		// TODO Auto-generated method stub
		registry.addErrorPages(e404,e500,arguments);
//		registry.addErrorPages(e500);
	}

}
