# SpringBootWeb
```
/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.web;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.http.CacheControl;

/**
 * Properties used to configure resource handling.
 *
 * @author Phillip Webb
 * @author Brian Clozel
 * @author Dave Syer
 * @author Venil Noronha
 * @author Kristine Jetzke
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

	/**
	 * Locations of static resources. Defaults to classpath:[/META-INF/resources/,
	 * /resources/, /static/, /public/].
	 */
	private String[] staticLocations = CLASSPATH_RESOURCE_LOCATIONS;

	/**
	 * Whether to enable default resource handling.
	 */
	private boolean addMappings = true;

	private final Chain chain = new Chain();

	private final Cache cache = new Cache();

	public String[] getStaticLocations() {
		return this.staticLocations;
	}

	public void setStaticLocations(String[] staticLocations) {
		this.staticLocations = appendSlashIfNecessary(staticLocations);
	}

	private String[] appendSlashIfNecessary(String[] staticLocations) {
		String[] normalized = new String[staticLocations.length];
		for (int i = 0; i < staticLocations.length; i++) {
			String location = staticLocations[i];
			normalized[i] = location.endsWith("/") ? location : location + "/";
		}
		return normalized;
	}

	public boolean isAddMappings() {
		return this.addMappings;
	}

	public void setAddMappings(boolean addMappings) {
		this.addMappings = addMappings;
	}

	public Chain getChain() {
		return this.chain;
	}

	public Cache getCache() {
		return this.cache;
	}

	/**
	 * Configuration for the Spring Resource Handling chain.
	 */
	public static class Chain {

		/**
		 * Whether to enable the Spring Resource Handling chain. By default, disabled
		 * unless at least one strategy has been enabled.
		 */
		private Boolean enabled;

		/**
		 * Whether to enable caching in the Resource chain.
		 */
		private boolean cache = true;

		/**
		 * Whether to enable HTML5 application cache manifest rewriting.
		 */
		private boolean htmlApplicationCache = false;

		/**
		 * Whether to enable resolution of already gzipped resources. Checks for a
		 * resource name variant with the "*.gz" extension.
		 */
		private boolean gzipped = false;

		private final Strategy strategy = new Strategy();

		/**
		 * Return whether the resource chain is enabled. Return {@code null} if no
		 * specific settings are present.
		 * @return whether the resource chain is enabled or {@code null} if no specified
		 * settings are present.
		 */
		public Boolean getEnabled() {
			return getEnabled(getStrategy().getFixed().isEnabled(),
					getStrategy().getContent().isEnabled(), this.enabled);
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isCache() {
			return this.cache;
		}

		public void setCache(boolean cache) {
			this.cache = cache;
		}

		public Strategy getStrategy() {
			return this.strategy;
		}

		public boolean isHtmlApplicationCache() {
			return this.htmlApplicationCache;
		}

		public void setHtmlApplicationCache(boolean htmlApplicationCache) {
			this.htmlApplicationCache = htmlApplicationCache;
		}

		public boolean isGzipped() {
			return this.gzipped;
		}

		public void setGzipped(boolean gzipped) {
			this.gzipped = gzipped;
		}

		static Boolean getEnabled(boolean fixedEnabled, boolean contentEnabled,
				Boolean chainEnabled) {
			return (fixedEnabled || contentEnabled) ? Boolean.TRUE : chainEnabled;
		}

	}

	/**
	 * Strategies for extracting and embedding a resource version in its URL path.
	 */
	public static class Strategy {

		private final Fixed fixed = new Fixed();

		private final Content content = new Content();

		public Fixed getFixed() {
			return this.fixed;
		}

		public Content getContent() {
			return this.content;
		}

	}

	/**
	 * Version Strategy based on content hashing.
	 */
	public static class Content {

		/**
		 * Whether to enable the content Version Strategy.
		 */
		private boolean enabled;

		/**
		 * Comma-separated list of patterns to apply to the content Version Strategy.
		 */
		private String[] paths = new String[] { "/**" };

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String[] getPaths() {
			return this.paths;
		}

		public void setPaths(String[] paths) {
			this.paths = paths;
		}

	}

	/**
	 * Version Strategy based on a fixed version string.
	 */
	public static class Fixed {

		/**
		 * Whether to enable the fixed Version Strategy.
		 */
		private boolean enabled;

		/**
		 * Comma-separated list of patterns to apply to the fixed Version Strategy.
		 */
		private String[] paths = new String[] { "/**" };

		/**
		 * Version string to use for the fixed Version Strategy.
		 */
		private String version;

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String[] getPaths() {
			return this.paths;
		}

		public void setPaths(String[] paths) {
			this.paths = paths;
		}

		public String getVersion() {
			return this.version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

	/**
	 * Cache configuration.
	 */
	public static class Cache {

		/**
		 * Cache period for the resources served by the resource handler. If a duration
		 * suffix is not specified, seconds will be used. Can be overridden by the
		 * 'spring.resources.cache.cachecontrol' properties.
		 */
		@DurationUnit(ChronoUnit.SECONDS)
		private Duration period;

		/**
		 * Cache control HTTP headers, only allows valid directive combinations. Overrides
		 * the 'spring.resources.cache.period' property.
		 */
		private final Cachecontrol cachecontrol = new Cachecontrol();

		public Duration getPeriod() {
			return this.period;
		}

		public void setPeriod(Duration period) {
			this.period = period;
		}

		public Cachecontrol getCachecontrol() {
			return this.cachecontrol;
		}

		/**
		 * Cache Control HTTP header configuration.
		 */
		public static class Cachecontrol {

			/**
			 * Maximum time the response should be cached, in seconds if no duration
			 * suffix is not specified.
			 */
			@DurationUnit(ChronoUnit.SECONDS)
			private Duration maxAge;

			/**
			 * Indicate that the cached response can be reused only if re-validated with
			 * the server.
			 */
			private Boolean noCache;

			/**
			 * Indicate to not cache the response in any case.
			 */
			private Boolean noStore;

			/**
			 * Indicate that once it has become stale, a cache must not use the response
			 * without re-validating it with the server.
			 */
			private Boolean mustRevalidate;

			/**
			 * Indicate intermediaries (caches and others) that they should not transform
			 * the response content.
			 */
			private Boolean noTransform;

			/**
			 * Indicate that any cache may store the response.
			 */
			private Boolean cachePublic;

			/**
			 * Indicate that the response message is intended for a single user and must
			 * not be stored by a shared cache.
			 */
			private Boolean cachePrivate;

			/**
			 * Same meaning as the "must-revalidate" directive, except that it does not
			 * apply to private caches.
			 */
			private Boolean proxyRevalidate;

			/**
			 * Maximum time the response can be served after it becomes stale, in seconds
			 * if no duration suffix is not specified.
			 */
			@DurationUnit(ChronoUnit.SECONDS)
			private Duration staleWhileRevalidate;

			/**
			 * Maximum time the response may be used when errors are encountered, in
			 * seconds if no duration suffix is not specified.
			 */
			@DurationUnit(ChronoUnit.SECONDS)
			private Duration staleIfError;

			/**
			 * Maximum time the response should be cached by shared caches, in seconds if
			 * no duration suffix is not specified.
			 */
			@DurationUnit(ChronoUnit.SECONDS)
			private Duration sMaxAge;

			public Duration getMaxAge() {
				return this.maxAge;
			}

			public void setMaxAge(Duration maxAge) {
				this.maxAge = maxAge;
			}

			public Boolean getNoCache() {
				return this.noCache;
			}

			public void setNoCache(Boolean noCache) {
				this.noCache = noCache;
			}

			public Boolean getNoStore() {
				return this.noStore;
			}

			public void setNoStore(Boolean noStore) {
				this.noStore = noStore;
			}

			public Boolean getMustRevalidate() {
				return this.mustRevalidate;
			}

			public void setMustRevalidate(Boolean mustRevalidate) {
				this.mustRevalidate = mustRevalidate;
			}

			public Boolean getNoTransform() {
				return this.noTransform;
			}

			public void setNoTransform(Boolean noTransform) {
				this.noTransform = noTransform;
			}

			public Boolean getCachePublic() {
				return this.cachePublic;
			}

			public void setCachePublic(Boolean cachePublic) {
				this.cachePublic = cachePublic;
			}

			public Boolean getCachePrivate() {
				return this.cachePrivate;
			}

			public void setCachePrivate(Boolean cachePrivate) {
				this.cachePrivate = cachePrivate;
			}

			public Boolean getProxyRevalidate() {
				return this.proxyRevalidate;
			}

			public void setProxyRevalidate(Boolean proxyRevalidate) {
				this.proxyRevalidate = proxyRevalidate;
			}

			public Duration getStaleWhileRevalidate() {
				return this.staleWhileRevalidate;
			}

			public void setStaleWhileRevalidate(Duration staleWhileRevalidate) {
				this.staleWhileRevalidate = staleWhileRevalidate;
			}

			public Duration getStaleIfError() {
				return this.staleIfError;
			}

			public void setStaleIfError(Duration staleIfError) {
				this.staleIfError = staleIfError;
			}

			public Duration getSMaxAge() {
				return this.sMaxAge;
			}

			public void setSMaxAge(Duration sMaxAge) {
				this.sMaxAge = sMaxAge;
			}

			public CacheControl toHttpCacheControl() {
				PropertyMapper map = PropertyMapper.get();
				CacheControl control = createCacheControl();
				map.from(this::getMustRevalidate).whenTrue()
						.toCall(control::mustRevalidate);
				map.from(this::getNoTransform).whenTrue().toCall(control::noTransform);
				map.from(this::getCachePublic).whenTrue().toCall(control::cachePublic);
				map.from(this::getCachePrivate).whenTrue().toCall(control::cachePrivate);
				map.from(this::getProxyRevalidate).whenTrue()
						.toCall(control::proxyRevalidate);
				map.from(this::getStaleWhileRevalidate).whenNonNull().to(
						(duration) -> control.staleWhileRevalidate(duration.getSeconds(),
								TimeUnit.SECONDS));
				map.from(this::getStaleIfError).whenNonNull().to((duration) -> control
						.staleIfError(duration.getSeconds(), TimeUnit.SECONDS));
				map.from(this::getSMaxAge).whenNonNull().to((duration) -> control
						.sMaxAge(duration.getSeconds(), TimeUnit.SECONDS));
				return control;
			}

			private CacheControl createCacheControl() {
				if (Boolean.TRUE.equals(this.noStore)) {
					return CacheControl.noStore();
				}
				if (Boolean.TRUE.equals(this.noCache)) {
					return CacheControl.noCache();
				}
				if (this.maxAge != null) {
					return CacheControl.maxAge(this.maxAge.getSeconds(),
							TimeUnit.SECONDS);
				}
				return CacheControl.empty();
			}

		}

	}

}
```
####默认的静态资源路径是可以被修改的
```
spring.resources.staticLocations=classpath:/html/
```
#### 错误页面自定义
```
/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.web.servlet.error;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.util.HtmlUtils;

/**
 * {@link EnableAutoConfiguration Auto-configuration} to render errors via an MVC error
 * controller.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
// Load before the main WebMvcAutoConfiguration so that the error View is available
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({ ServerProperties.class, ResourceProperties.class })
public class ErrorMvcAutoConfiguration {

	private final ServerProperties serverProperties;

	private final DispatcherServletPath dispatcherServletPath;

	private final List<ErrorViewResolver> errorViewResolvers;

	public ErrorMvcAutoConfiguration(ServerProperties serverProperties,
			DispatcherServletPath dispatcherServletPath,
			ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
		this.serverProperties = serverProperties;
		this.dispatcherServletPath = dispatcherServletPath;
		this.errorViewResolvers = errorViewResolversProvider.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
	public DefaultErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes(
				this.serverProperties.getError().isIncludeException());
	}

	@Bean
	@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
	public BasicErrorController basicErrorController(ErrorAttributes errorAttributes) {
		return new BasicErrorController(errorAttributes, this.serverProperties.getError(),
				this.errorViewResolvers);
	}

	@Bean
	public ErrorPageCustomizer errorPageCustomizer() {
		return new ErrorPageCustomizer(this.serverProperties, this.dispatcherServletPath);
	}

	@Bean
	public static PreserveErrorControllerTargetClassPostProcessor preserveErrorControllerTargetClassPostProcessor() {
		return new PreserveErrorControllerTargetClassPostProcessor();
	}

	@Configuration
	static class DefaultErrorViewResolverConfiguration {

		private final ApplicationContext applicationContext;

		private final ResourceProperties resourceProperties;

		DefaultErrorViewResolverConfiguration(ApplicationContext applicationContext,
				ResourceProperties resourceProperties) {
			this.applicationContext = applicationContext;
			this.resourceProperties = resourceProperties;
		}

		@Bean
		@ConditionalOnBean(DispatcherServlet.class)
		@ConditionalOnMissingBean
		public DefaultErrorViewResolver conventionErrorViewResolver() {
			return new DefaultErrorViewResolver(this.applicationContext,
					this.resourceProperties);
		}

	}

	@Configuration
	@ConditionalOnProperty(prefix = "server.error.whitelabel", name = "enabled", matchIfMissing = true)
	@Conditional(ErrorTemplateMissingCondition.class)
	protected static class WhitelabelErrorViewConfiguration {

		private final SpelView defaultErrorView = new SpelView(
				"<html><body><h1>Whitelabel Error Page</h1>"
						+ "<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>"
						+ "<div id='created'>${timestamp}</div>"
						+ "<div>There was an unexpected error (type=${error}, status=${status}).</div>"
						+ "<div>${message}</div></body></html>");

		@Bean(name = "error")
		@ConditionalOnMissingBean(name = "error")
		public View defaultErrorView() {
			return this.defaultErrorView;
		}

		// If the user adds @EnableWebMvc then the bean name view resolver from
		// WebMvcAutoConfiguration disappears, so add it back in to avoid disappointment.
		@Bean
		@ConditionalOnMissingBean
		public BeanNameViewResolver beanNameViewResolver() {
			BeanNameViewResolver resolver = new BeanNameViewResolver();
			resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
			return resolver;
		}

	}

	/**
	 * {@link SpringBootCondition} that matches when no error template view is detected.
	 */
	private static class ErrorTemplateMissingCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("ErrorTemplate Missing");
			TemplateAvailabilityProviders providers = new TemplateAvailabilityProviders(
					context.getClassLoader());
			TemplateAvailabilityProvider provider = providers.getProvider("error",
					context.getEnvironment(), context.getClassLoader(),
					context.getResourceLoader());
			if (provider != null) {
				return ConditionOutcome
						.noMatch(message.foundExactly("template from " + provider));
			}
			return ConditionOutcome
					.match(message.didNotFind("error template view").atAll());
		}

	}

	/**
	 * Simple {@link View} implementation that resolves variables as SpEL expressions.
	 */
	private static class SpelView implements View {

		private static final Log logger = LogFactory.getLog(SpelView.class);

		private final NonRecursivePropertyPlaceholderHelper helper;

		private final String template;

		private volatile Map<String, Expression> expressions;

		SpelView(String template) {
			this.helper = new NonRecursivePropertyPlaceholderHelper("${", "}");
			this.template = template;
		}

		@Override
		public String getContentType() {
			return "text/html";
		}

		@Override
		public void render(Map<String, ?> model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			if (response.isCommitted()) {
				String message = getMessage(model);
				logger.error(message);
				return;
			}
			if (response.getContentType() == null) {
				response.setContentType(getContentType());
			}
			PlaceholderResolver resolver = new ExpressionResolver(getExpressions(),
					model);
			String result = this.helper.replacePlaceholders(this.template, resolver);
			response.getWriter().append(result);
		}

		private String getMessage(Map<String, ?> model) {
			Object path = model.get("path");
			String message = "Cannot render error page for request [" + path + "]";
			if (model.get("message") != null) {
				message += " and exception [" + model.get("message") + "]";
			}
			message += " as the response has already been committed.";
			message += " As a result, the response may have the wrong status code.";
			return message;
		}

		private Map<String, Expression> getExpressions() {
			if (this.expressions == null) {
				synchronized (this) {
					ExpressionCollector expressionCollector = new ExpressionCollector();
					this.helper.replacePlaceholders(this.template, expressionCollector);
					this.expressions = expressionCollector.getExpressions();
				}
			}
			return this.expressions;
		}

	}

	/**
	 * {@link PlaceholderResolver} to collect placeholder expressions.
	 */
	private static class ExpressionCollector implements PlaceholderResolver {

		private final SpelExpressionParser parser = new SpelExpressionParser();

		private final Map<String, Expression> expressions = new HashMap<>();

		@Override
		public String resolvePlaceholder(String name) {
			this.expressions.put(name, this.parser.parseExpression(name));
			return null;
		}

		public Map<String, Expression> getExpressions() {
			return Collections.unmodifiableMap(this.expressions);
		}

	}

	/**
	 * SpEL based {@link PlaceholderResolver}.
	 */
	private static class ExpressionResolver implements PlaceholderResolver {

		private final Map<String, Expression> expressions;

		private final EvaluationContext context;

		ExpressionResolver(Map<String, Expression> expressions, Map<String, ?> map) {
			this.expressions = expressions;
			this.context = getContext(map);
		}

		private EvaluationContext getContext(Map<String, ?> map) {
			return SimpleEvaluationContext.forPropertyAccessors(new MapAccessor())
					.withRootObject(map).build();
		}

		@Override
		public String resolvePlaceholder(String placeholderName) {
			Expression expression = this.expressions.get(placeholderName);
			Object expressionValue = (expression != null)
					? expression.getValue(this.context) : null;
			return escape(expressionValue);
		}

		private String escape(Object value) {
			return HtmlUtils.htmlEscape((value != null) ? value.toString() : null);
		}

	}

	/**
	 * {@link WebServerFactoryCustomizer} that configures the server's error pages.
	 */
	private static class ErrorPageCustomizer implements ErrorPageRegistrar, Ordered {

		private final ServerProperties properties;

		private final DispatcherServletPath dispatcherServletPath;

		protected ErrorPageCustomizer(ServerProperties properties,
				DispatcherServletPath dispatcherServletPath) {
			this.properties = properties;
			this.dispatcherServletPath = dispatcherServletPath;
		}

		@Override
		public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
			ErrorPage errorPage = new ErrorPage(this.dispatcherServletPath
					.getRelativePath(this.properties.getError().getPath()));
			errorPageRegistry.addErrorPages(errorPage);
		}

		@Override
		public int getOrder() {
			return 0;
		}

	}

	/**
	 * {@link BeanFactoryPostProcessor} to ensure that the target class of ErrorController
	 * MVC beans are preserved when using AOP.
	 */
	static class PreserveErrorControllerTargetClassPostProcessor
			implements BeanFactoryPostProcessor {

		@Override
		public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
				throws BeansException {
			String[] errorControllerBeans = beanFactory
					.getBeanNamesForType(ErrorController.class, false, false);
			for (String errorControllerBean : errorControllerBeans) {
				try {
					beanFactory.getBeanDefinition(errorControllerBean).setAttribute(
							AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
				}
				catch (Throwable ex) {
					// Ignore
				}
			}
		}

	}

}

```
#### 其实我们经常看到的错误页面就是这里的一个模版
```
	private final SpelView defaultErrorView = new SpelView(
				"<html><body><h1>Whitelabel Error Page</h1>"
						+ "<p>This application has no explicit mapping for /error, so you are seeing this as a fallback.</p>"
						+ "<div id='created'>${timestamp}</div>"
						+ "<div>There was an unexpected error (type=${error}, status=${status}).</div>"
						+ "<div>${message}</div></body></html>");
```




