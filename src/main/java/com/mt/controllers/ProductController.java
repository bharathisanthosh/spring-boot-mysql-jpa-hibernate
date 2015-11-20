package com.mt.controllers;

import com.mt.models.TenantDao;
import com.mt.models.Product;
import com.mt.models.ProductDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class ProductController
 */
@Controller
public class ProductController {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  // Wire the ProductDao used inside this controller.
  @Autowired
  private ProductDao productDao;

  @Autowired
  private TenantDao tenantDao;

  @Autowired
  private HttpServletRequest request;

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @RequestMapping(value="/product/create")
  //@ResponseBody
  public String create(Model model) {
    model.addAttribute("product", new Product());
    return "create";
  }

  /**
   * Create a new product with an auto-generated id and email and name as passed
   * values.
   */
  @RequestMapping(value="product/save", method= RequestMethod.POST)
  //@ResponseBody
  public String save(Product product) {
    try {
      //User user = new User(email, name);
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      product.setTenantId(tid);
      productDao.save(product);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return "Error creating the product: " + ex.toString();
    }
    //return "User successfully created!";
    return "redirect:/product/list";
  }
  
  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="product/delete/{id}")
  //@ResponseBody
  public String delete(@PathVariable Integer id, Model model) {
    int result;
    try {
      Product product = new Product(id);
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      product.setTenantId(tid);

      result = productDao.delete(product);
    }
    catch (Exception ex) {
      return "Error deleting the product: " + ex.toString();
    }

    if(result > 0) {
      return "redirect:/product/list";
    }else{
      return "Product not found!";
    }

  }
  
  /**
   * Retrieve the id for the product with the passed email address.
   */
  @RequestMapping(value="/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String productId;
    try {
      Product product = productDao.getByEmail(email);
      productId = String.valueOf(product.getId());
    }
    catch (Exception ex) {
      return "Product not found: " + ex.toString();
    }
    return "The product id is: " + productId;
  }

  @RequestMapping("/product/edit/{id}")
  public String edit(@PathVariable Integer id, Model model){
    model.addAttribute("product", productDao.getById(id));
    return "create";
  }
  
  /**
   * Update the product identified by the passed id.
   */
  @RequestMapping(value="/update")
  @ResponseBody
  public String updateName(long id, String code, String name, String description, String price) {
    try {
      Product product = productDao.getById(id);
      product.setCode(code);
      product.setName(name);
      product.setDescription(description);
      product.setPrice(price);
      productDao.update(product);
    }
    catch (Exception ex) {
      return "Error updating the product: " + ex.toString();
    }
    return "Product successfully updated!";
  }

  /**
   * list all products of a tenant.
   */
  @RequestMapping(value="/product/list")
  //@ResponseBody
  public String list(Model model) {
    List<Product> resultList;
    try {
      int tid = Integer.parseInt("" + request.getAttribute("CURRENT_TENANT_IDENTIFIER"));
      resultList = productDao.getAll(tid);
      model.addAttribute("product_list", resultList);
    }
    catch (Exception ex) {
      return "Error fetching list of products: " + ex.toString();
      //return null;
    }
    return "list";
  }



} // class ProductController
