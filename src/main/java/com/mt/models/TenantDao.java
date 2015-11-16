package com.mt.models;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by bharathim on 11/11/2015.
 */
@Repository
@Transactional
public class TenantDao {
    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    // An EntityManager will be automatically injected from entityManagerFactory
    // setup on DatabaseConfig class.
    @PersistenceContext
    private EntityManager entityManager;

    public Tenant findById(long id) {

        return entityManager.find(Tenant.class, id);
    }

    public Tenant findByDomain(String domain) {

        return (Tenant) entityManager.createQuery(
                "from Tenant where domain = :domain")
                .setParameter("domain", domain)
                .getSingleResult();
    }
}
