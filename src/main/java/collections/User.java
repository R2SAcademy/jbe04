package collections;

import java.util.Objects;

public class User {
  private String name;
  private String email;

  public User(String name, String email) {
    this.name = name;
    this.email = email.toLowerCase().trim(); // Normalize email
  }

  // Only email is used to check uniqueness
  @Override
  public boolean equals(Object o) {
    User user = (User) o;
    return email.equals(user.email);  // same value (with the same email)
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);       // same place
  }
}
