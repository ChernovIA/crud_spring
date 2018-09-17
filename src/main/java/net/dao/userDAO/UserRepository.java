package net.dao.userDAO;

import net.model.RolesTypes;
import net.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>, UserRepositoryCustom {
    User findByLogin(String login);

    @Override
    Iterable<User> findByRole(RolesTypes role);
}
