package com.mt.controllers;

import com.mt.models.Tenant;
import com.mt.models.TenantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

  @RequestMapping("/")
  @ResponseBody
  public String index(HttpServletRequest request, HttpServletResponse response) {

    String tid = ""+request.getAttribute("CURRENT_TENANT_IDENTIFIER");
    return "Welcome "+tid+":)";
  }

}
