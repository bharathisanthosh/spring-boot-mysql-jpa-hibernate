package com.mt.controllers;

import com.mt.models.Tenant;
import com.mt.models.TenantDao;
import com.mt.models.User;
import com.mt.models.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(value="/user/create")
  //@ResponseBody
  public String create(Model model) {
    model.addAttribute("user", new User());
    return "create";
  }

  /**
   * Create a new user with an auto-generated id and email and name as passed 
   * values.
   */
  @RequestMapping(value="user/save", method= RequestMethod.POST)
  //@ResponseBody
  public String save(User user) {
    try {
      //User user = new User(email, name);
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      user.setTenantId(tid);
      userDao.save(user);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return "Error creating the user: " + ex.toString();
    }
    //return "User successfully created!";
    return "redirect:/user/list";
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="user/delete/{id}")
  //@ResponseBody
  public String delete(@PathVariable Integer id, Model model) {
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
      return "redirect:/user/list";
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

  @RequestMapping("/user/edit/{id}")
  public String edit(@PathVariable Integer id, Model model){
    model.addAttribute("user", userDao.getById(id));
    return "create";
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
  @RequestMapping(value="/user/list")
  //@ResponseBody
  public String list(Model model) {
    List<User> resultList;
    try {
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      resultList = userDao.getAll(tid);
      model.addAttribute("user_list", resultList);
    }
    catch (Exception ex) {
      return "Error fetching list of users: " + ex.toString();
      //return null;
    }
    return "list";
  }



} // class UserController
