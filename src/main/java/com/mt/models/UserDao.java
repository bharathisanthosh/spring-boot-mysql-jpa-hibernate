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
 * This class is used to access data for the User entity.
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
public class UserDao {

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
   * Save the user in the database.
   */
  public void save(User user) {
    if( user.getId()> 0 ){
      entityManager.merge(user);
    }else {
      entityManager.persist(user);
    }
    return;
  }
  
  /**
   * Save the user in the database.
   */
  public void create(User user) {
    entityManager.persist(user);
    return;
  }
  
  /**
   * Delete the user from the database.
   */
  public int delete(User user) {
    Query query = entityManager.createQuery("delete from User where id=:ID and tenantId=:TENANT_ID ");
    query.setParameter("ID", user.getId());
    query.setParameter("TENANT_ID", user.getTenantId());
    int result = query.executeUpdate();

    /*if (entityManager.contains(user))
      entityManager.remove(user);
    else
      entityManager.remove(entityManager.merge(user));*/
    return result;
  }
  
  /**
   * Return all the users stored in the database.
   */
  @SuppressWarnings("unchecked")
  public List<User> getAll(int tenantId) {
    //Configure your filters
    Session hibernateSession = entityManager.unwrap(Session.class);
    Filter publishedAfterFilter = hibernateSession.enableFilter("tenantFilter");
    publishedAfterFilter.setParameter("tenantId", tenantId);
    publishedAfterFilter.validate();

    return entityManager.createQuery("from User").getResultList();
  }
  
  /**
   * Return the user having the passed email.
   */
  public User getByEmail(String email) {
    return (User) entityManager.createQuery(
        "from User where email = :email")
        .setParameter("email", email)
        .getSingleResult();
  }

  /**
   * Return the user having the passed id.
   */
  public User getById(long id) {
    return entityManager.find(User.class, id);
  }

  /**
   * Update the passed user in the database.
   */
  public void update(User user) {
    entityManager.merge(user);
    return;
  }


  
} // class UserDao
