package net.dao.userDAO;

import net.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository

public class UsersDAOImpl implements UsersDAO {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public User get(long id){
        return getEntityManager().find(User.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User get(String login) {
        TypedQuery<User> tq = getEntityManager().createQuery("FROM User WHERE login = ?1",User.class);
        tq.setParameter(1, login);
        User user = tq.getSingleResult();
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getTable() {
        EntityManager entityManager = getEntityManager();
        entityManager.clear();
        return entityManager.createQuery("from User",User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
     //   entityManager.getTransaction().begin();
        getEntityManager().persist(user);
     //   entityManager.getTransaction().commit();
    }

    @Override
    public void upDateUser(User user) {
      //  entityManager.getTransaction().begin();
        getEntityManager().merge(user);
      //  entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
     //   entityManager.getTransaction().begin();
        EntityManager entityManager = getEntityManager();
        User user =  entityManager.find(User.class, id);
        entityManager.remove(user);
     //   entityManager.getTransaction().commit();
    }
}
