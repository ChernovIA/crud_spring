package net.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.model.Roles;
import net.model.RolesTypes;
import net.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface RolesService {

    static Map<String, Boolean> getAllRoles(){
        Map<String, Boolean> map = new HashMap<>();
        for(RolesTypes rolesType : RolesTypes.values()){
            map.put(rolesType.name(), false);
        }

        return map;
    }

    static Map<String, Boolean> getAllRoles(User user){
        Map<String, Boolean> map = getAllRoles();

        for(Roles role: user.getRoles()){
            map.put(role.getType().name(), true);
        }

        return map;
    }

    static void setUserRoles(User user, String[] userRoles){
        Set<Roles> rolesSet = new HashSet<>();
        for(String userRole: userRoles){
            Roles role = new Roles();
            role.setType(RolesTypes.valueOf(userRole));
            rolesSet.add(role);
        }
        user.setRoles(rolesSet);
    }
}
