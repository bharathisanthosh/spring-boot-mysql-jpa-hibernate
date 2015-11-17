package com.mt.controllers;

import com.mt.models.Tenant;
import com.mt.models.TenantDao;
import com.mt.models.User;
import com.mt.models.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class UserController
 */
@Controller
public class UserController {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  // Wire the UserDao used inside this controller.
  @Autowired
  private UserDao userDao;

  @Autowired
  private TenantDao tenantDao;

  @Autowired
  private HttpServletRequest request;

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="/create")
  @ResponseBody
  public String create(String email, String name) {
    try {
      User user = new User(email, name);
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      user.setTenantId(tid);
      userDao.create(user);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return "Error creating the user: " + ex.toString();
    }
    return "User successfully created!";
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(long id) {
    int result;
    try {
      User user = new User(id);
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      user.setTenantId(tid);

      result = userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }

    if(result > 0) {
      return "User successfully deleted!";
    }else{
      return "User not found!";
    }

  }
  
  /**
   * Retrieve the id for the user with the passed email address.
   */
  @RequestMapping(value="/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    try {
      User user = userDao.getByEmail(email);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found: " + ex.toString();
    }
    return "The user id is: " + userId;
  }
  
  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @RequestMapping(value="/update")
  @ResponseBody
  public String updateName(long id, String email, String name) {
    try {
      User user = userDao.getById(id);
      user.setEmail(email);
      user.setName(name);
      userDao.update(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User successfully updated!";
  }

  /**
   * list all users of a tenant.
   */
  @RequestMapping(value="/list")
  @ResponseBody
  public String list() {
    List<User> resultList;
    try {
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      resultList = userDao.getAll(tid);
    }
    catch (Exception ex) {
      return "Error fetching list of users: " + ex.toString();
      //return null;
    }
    return resultList.toString();
  }



} // class UserController
