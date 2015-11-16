package com.mt.controllers;

/**
 * Created by bharathim on 11/13/2015.
 */

import com.mt.models.Tenant;
import com.mt.models.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.mt.models.TenantDao;
import com.mt.models.Tenant;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by bharathim on 11/11/2015.
 */
class LoginTenantIdResolver
{

    //@Autowired
    private TenantDao tenantDao;

    public LoginTenantIdResolver(){}

    public String resolveCurrentTenantIdentifier(){

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String domainName = (String) requestAttributes.getAttribute("CURRENT_TENANT_IDENTIFIER", RequestAttributes.SCOPE_REQUEST);

        Tenant tenant = tenantDao.findByDomain(domainName);
        if (tenant == null) {
            System.out.println("Unsupported domain: " + domainName);
        }

        return ""+tenant.getId();
    }

}
