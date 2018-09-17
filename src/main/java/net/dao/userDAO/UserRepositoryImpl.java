package net.dao.userDAO;

import net.model.RolesTypes;
import net.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Iterable<User> findByRole(RolesTypes role) {
        TypedQuery<User> query = em.createQuery("Select user from User as user inner join user.roles as tr where tr.type = ?1", User.class);
        query.setParameter(1, role);
        return query.getResultList();
    }
}
