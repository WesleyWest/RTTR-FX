package objects;

import conf.Configs;
import forms.Login.LoginController;

import java.sql.*;
import java.util.ArrayList;


public class MySQLDataBase extends Configs {

    private class tmpRoles {
        String roleCode;
        String roleName;

        public tmpRoles(String roleCode, String roleName) {
            this.roleCode = roleCode;
            this.roleName = roleName;
        }
    }

    private Connection connection;

    public boolean isOpen() {
        return opened;
    }

    private boolean opened;

    public MySQLDataBase(LoginController cont) {

    }

    public void setRoleNamesFromDB() {
        ArrayList<tmpRoles> tmp = new ArrayList<tmpRoles>();
        try {
            Statement statement = connection.createStatement();
            String query =

                    "SELECT role_code,role_name " +
                            "FROM roles " +
                            "ORDER BY role_code ";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String roleCode = rs.getString("role_code");
                String roleName = rs.getString("role_name");
                Role.setRoleNameByCode(roleCode, roleName);
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            opened = false;
        }
        System.out.println("Role names was set...");

    }

    public ArrayList<User> readUsersFromDB() {

        ArrayList<User> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT user_id,user_name,user_password,user_role,user_status,user_undeletable " +
                            "FROM Users " +
                            "ORDER BY user_id ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String roleCode = rs.getString("user_role");
                Boolean status = rs.getBoolean("user_status");
                Boolean undeletable = rs.getBoolean("user_undeletable");
                tmp.add(new User(id, name, password, Role.valueOf(roleCode.toUpperCase()), status, undeletable));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        System.out.println("Users was read...");
        return tmp;
    }


    public void open() {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbSchema;
            System.out.println("Trying to connect to the DB...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            System.out.println("Connected successfully to: " + connectionString);
            opened = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getLocalizedMessage());
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getLocalizedMessage());
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Connection to database is closed...");
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
