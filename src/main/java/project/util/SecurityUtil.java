package project.util;

import java.security.MessageDigest;

public class SecurityUtil {
    public static String hashPassword(String password) {
        try {
            //Lay doi thuong MessageDigest voi "SHA-256"
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //64 ky tu hex

            //Chuyen password thanh byte
            byte[] hashed = md.digest(password.getBytes()); //Ket qua 32 byte(256 bit)

            //Duyet qua tung byte
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b)); //Doi byte sang chuoi hex 2 ky tu(00 -> ff)
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }
}
