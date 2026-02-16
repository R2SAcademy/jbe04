package project.util;

public class ValidationUtil {
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidUsername(String username) {
        //Check NULL
        if(username == null){
            return false;
        }
        //Check Validate Input
        if(!username.matches("^[a-zA-Z0-9]{3,20}$")){
            return false;
        }

        return true;
    }

    public static boolean isValidPassword(String password) {
        //Check NULL
        if (password == null) {
            return false;
        }
        //Check length
        if (password.length() < 6) {
            return false;
        }
        //Check Validate Input
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$")) {
            return false;
        }

        return true;
    }
}