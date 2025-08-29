package project.form;

import project.dao.UserDAO;
import project.dao.UserDAOImpl;
import project.entity.User;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.util.ScannerUtil;
import project.util.SecurityUtil;
import project.util.ValidationUtil;

public class RegisterForm {
    private final UserDAO userDAO = new UserDAOImpl();

    public void register() {
        try {
            String username = ScannerUtil.readString("Enter username: ");
            String password = ScannerUtil.readString("Enter password: ");
            String role = ScannerUtil.readString("Enter role (admin/user): ");

            if (!ValidationUtil.isValidUsername(username)) {
                System.out.println("Invalid username.");
                return;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println("Password too weak.");
                return;
            }

            if (role == null || role.trim().isEmpty()) {
                role = "user";
            }

            String passwordHash = SecurityUtil.hashPassword(password);

            User user = new User(0, username, passwordHash, role);

            try {
                userDAO.insert(user);
                System.out.println("User registered successfully!");
            } catch (DAOException e) {
                GlobalExceptionHandler.handle(e);
            }

        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }
}
