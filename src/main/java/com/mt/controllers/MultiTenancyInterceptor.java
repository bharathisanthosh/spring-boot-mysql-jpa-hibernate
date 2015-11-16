package com.mt.controllers;

import com.mt.models.Tenant;
import com.mt.models.TenantDao;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by bharathim on 11/11/2015.
 */
public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    TenantDao tenantDao;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

        Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        //req.setAttribute("CURRENT_TENANT_IDENTIFIER", req.getServerName());
        int tid = resolveCurrentTenantIdentifier(req.getServerName());
        req.setAttribute("CURRENT_TENANT_IDENTIFIER", tid);

        /*
        //Not able to enable the filter here
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Session hibernateSession = sessionFactory.openSession();
        Filter publishedAfterFilter = hibernateSession.enableFilter("tenantFilter");
        publishedAfterFilter.setParameter("tenantId", tid);
        publishedAfterFilter.validate();
        */
        return true;
    }

    private int resolveCurrentTenantIdentifier(String domainName){

        Tenant tenant = tenantDao.findByDomain(domainName);
        if (tenant == null) {
            System.out.println("Unsupported domain: " + domainName);
            throw new NullPointerException("Unsupported domain: " + domainName);
        }

        return tenant.getId();
    }
}