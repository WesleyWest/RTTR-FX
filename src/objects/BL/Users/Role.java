package objects.BL.Users;

import java.util.Arrays;
import java.util.HashSet;

public enum Role {
    ADMIN(""), USER(""), GUEST("");
    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static void setRoleNameByCode(String roleCode, String roleName) {
        Role.valueOf(roleCode).roleName = roleName;
    }

    public static Role roleByName(String roleName) {
        HashSet<Role> setRole = new HashSet<>(Arrays.asList(Role.values()));
        for (Role r : setRole) {
            if (roleName.equals(r.getRoleName())) {
                return r;
            }
        }
        return Role.GUEST;
    }
}


