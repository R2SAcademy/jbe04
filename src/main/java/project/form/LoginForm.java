package project.form;

import project.dao.UserDAO;
import project.dao.UserDAOImpl;
import project.entity.User;
import project.exception.DAOException;
import project.exception.GlobalExceptionHandler;
import project.util.ScannerUtil;
import project.util.SecurityUtil;

import java.util.Optional;

public class LoginForm {
    private final UserDAO userDAO = new UserDAOImpl();

    public void login() {
        try {
            String username = ScannerUtil.readString("Enter username: ");
            String password = ScannerUtil.readString("Enter password: ");

            Optional<User> optionalUser = userDAO.findByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                String passwordHash = SecurityUtil.hashPassword(password);

                if (user.getPasswordHash().equals(passwordHash)) {
                    System.out.println("Login successful!");
                    System.out.println("Hello " + user.getUsername() + ", Role: " + user.getRole());
                } else {
                    System.out.println("Invalid password. Please try again.");
                }
            } else {
                System.out.println("User not found.");
            }

        } catch (DAOException e) {
            GlobalExceptionHandler.handle(e);

        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }
}
