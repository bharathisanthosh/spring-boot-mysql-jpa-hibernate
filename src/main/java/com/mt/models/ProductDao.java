package com.mt.models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Filter;
import org.springframework.stereotype.Repository;

/**
 * This class is used to access data for the Product entity.
 * Repository annotation allows the component scanning support to find and 
 * configure the DAO without any XML configuration and also provide the Spring
 * exception translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */
@Repository
@Transactional
public class ProductDao {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager entityManager;
  
  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Save the product in the database.
   */
  public void save(Product product) {
    if( product.getId()> 0 ){
      entityManager.merge(product);
    }else {
      entityManager.persist(product);
    }
    return;
  }
  
  /**
   * Save the product in the database.
   */
  public void create(Product product) {
    entityManager.persist(product);
    return;
  }
  
  /**
   * Delete the product from the database.
   */
  public int delete(Product product) {
    Query query = entityManager.createQuery("delete from Product where id=:ID and tenantId=:TENANT_ID ");
    query.setParameter("ID", product.getId());
    query.setParameter("TENANT_ID", product.getTenantId());
    int result = query.executeUpdate();

    /*if (entityManager.contains(product))
      entityManager.remove(product);
    else
      entityManager.remove(entityManager.merge(product));*/
    return result;
  }
  
  /**
   * Return all the products stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List<Product> getAll(int tenantId) {
    //Configure your filters
    Session hibernateSession = entityManager.unwrap(Session.class);
    Filter publishedAfterFilter = hibernateSession.enableFilter("tenantFilter");
    publishedAfterFilter.setParameter("tenantId", tenantId);
    publishedAfterFilter.validate();

    return entityManager.createQuery("from Product").getResultList();
  }
  
  /**
   * Return the product having the passed code.
   */
  public Product getByEmail(String code) {
    return (Product) entityManager.createQuery(
        "fromPproduct where code = :code")
        .setParameter("code", code)
        .getSingleResult();
  }

  /**
   * Return the product having the passed id.
   */
  public Product getById(long id) {
    return entityManager.find(Product.class, id);
  }

  /**
   * Update the passed product in the database.
   */
  public void update(Product product) {
    entityManager.merge(product);
    return;
  }


  
} // class ProductDao
