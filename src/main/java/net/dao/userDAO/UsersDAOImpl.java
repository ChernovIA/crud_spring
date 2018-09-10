package net.dao.userDAO;

import net.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UsersDAOImpl implements UsersDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User get(long id){
        return em.find(User.class, id);
    }

    @Override
    public User get(String login) {
        TypedQuery<User> tq = em.createQuery("FROM User WHERE login = ?1",User.class);
        tq.setParameter(1, login);
        User user = tq.getSingleResult();
        return user;
    }

    @Override
    public List<User> getTable() {
        em.clear();
        return em.createQuery("FROM User",User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public void upDateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user =  em.find(User.class, id);
        em.remove(user);
    }
}
