package com.epam;

public class RolesService {
    public boolean canAccess(String role) {
        return role.equals("ADMIN");
    }
}
