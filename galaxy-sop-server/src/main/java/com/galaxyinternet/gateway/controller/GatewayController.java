package com.galaxyinternet.gateway.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.ZuulRunner;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.filters.FilterRegistry;
import com.netflix.zuul.monitoring.MonitoringHelper;

@Controller
public class GatewayController implements InitializingBean, ApplicationContextAware
{
	private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);
	private ZuulRunner zuulRunner = new ZuulRunner(false);
	@RequestMapping("/gateway/**")
	public void route(HttpServletRequest request, HttpServletResponse response)
	{
		long start = System.currentTimeMillis();
		try {
            init(request, response);

            // Marks this request as having passed through the "Zuul engine", as opposed to servlets
            // explicitly bound in web.xml, for which requests will not have the same data attached
            RequestContext context = RequestContext.getCurrentContext();
            context.setZuulEngineRan();

            try {
                preRoute();
            } catch (ZuulException e) {
                error(e);
                postRoute();
                return;
            }
            try {
                route();
            } catch (ZuulException e) {
                error(e);
                postRoute();
                return;
            }
            try {
                postRoute();
            } catch (ZuulException e) {
                error(e);
                return;
            }

        } catch (Throwable e) {
            error(new ZuulException(e, 500, "UNHANDLED_EXCEPTION_" + e.getClass().getName()));
        } finally {
            RequestContext.getCurrentContext().unset();
            if(logger.isDebugEnabled())
            {
            	logger.debug(String.format("Gateway URL:%s, Spend Time: %s", request.getRequestURI(),System.currentTimeMillis() - start));
            }
        }
	}

	/**
	 * executes "post" ZuulFilters
	 *
	 * @throws ZuulException
	 */
	void postRoute() throws ZuulException
	{
		zuulRunner.postRoute();
	}

	/**
	 * executes "route" filters
	 *
	 * @throws ZuulException
	 */
	void route() throws ZuulException
	{
		zuulRunner.route();
	}

	/**
	 * executes "pre" filters
	 *
	 * @throws ZuulException
	 */
	void preRoute() throws ZuulException
	{
		zuulRunner.preRoute();
	}

	/**
	 * initializes request
	 *
	 * @param servletRequest
	 * @param servletResponse
	 */
	void init(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
	{
		zuulRunner.init(servletRequest, servletResponse);
	}

	/**
	 * sets error context info and executes "error" filters
	 *
	 * @param e
	 */
	void error(ZuulException e)
	{
		RequestContext.getCurrentContext().setThrowable(e);
		zuulRunner.error();
	}

	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.applicationContext = applicationContext;
		
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		MonitoringHelper.initMocks();
		Map<String,ZuulFilter> map = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ZuulFilter.class, true, false);
		if(map != null && map.size()>0)
		{
			FilterRegistry reg = FilterRegistry.instance();
			Set<Entry<String,ZuulFilter>> set = map.entrySet();
			for(Entry<String,ZuulFilter> entry : set)
			{
				reg.put(entry.getKey(), entry.getValue());
			}
		}
	}

}
