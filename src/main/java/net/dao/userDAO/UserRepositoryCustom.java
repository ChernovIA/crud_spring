package net.dao.userDAO;

import net.model.RolesTypes;
import net.model.User;

public interface UserRepositoryCustom {
    Iterable<User> findByRole(RolesTypes role);
}
