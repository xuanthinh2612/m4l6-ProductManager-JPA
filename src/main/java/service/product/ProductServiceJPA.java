package service.product;


import model.Product;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.data.jpa.provider.HibernateUtils;

////// them vao annotation transaction
@Transactional
public class ProductServiceJPA implements IProductService {

    //////them vao annotation de khai bao
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        String query = "select c from Product as c";
        TypedQuery<Product> typedQuery = entityManager.createQuery(query, Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void create(Product product) {

        if (product.getId() != 0) {

            // neu id khac 0 (da ton tai) thi se update doi tuong do
            entityManager.merge(product);
        } else {
            // them moi 1 doi tuong khi id chua co san dc tao mac dinh bang 0
            entityManager.persist(product);
        }

    }

    @Override
    public void update(Product product) {
    }

    @Override
    public void delete(int id) {
        entityManager.remove(findById(id));
    }

    @Override
    public List<Product> findByName(String name) {
        String query = " select c from Product as c where c.productName like :name";
        TypedQuery typedQuery = entityManager.createQuery(query,Product.class);
        typedQuery.setParameter("name","%"+name+"%");
        return typedQuery.getResultList();
    }
}
