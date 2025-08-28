package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Demo {

  public static void main(String[] args) {
    try {
      // Register the Driver class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Create connection
      Connection conn = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/lib", "root", "Passw0rd");

      if (conn != null) {
        System.out.println("Connected to the database successfully!");
      } else {
        System.out.println("Failed to make connection!");
        return;
      }



      // insert, update, delete queries
      String insertQuery = "INSERT INTO books (book_name, author_id) VALUES (?, ?)";

      String updateQuery = "UPDATE books SET book_name = ?, author_id = ? WHERE book_id = ?";

      String deleteQuery = "DELETE FROM books WHERE book_id = ?";

      String choice;
      Scanner scanner = new Scanner(System.in);

      do {
        System.out.println("1. Insert");
        System.out.println("2. Update");
        System.out.println("3. Delete");
        System.out.println("4. Select");
        System.out.println("0. Exit");

        System.out.println("Enter your choice: ");
        choice = scanner.nextLine();

        switch (choice) {
          case "1": // insert
            insertBook(conn, scanner);
            break;
          case "2": // update
            break;
          case "3": // delete
            break;
          case "4": // select
            getAllBooks(conn);
            break;
        }
      } while (!"0".equals(choice));


    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static void getAllBooks(Connection conn) throws Exception {
    // Select query
    // select * from books;
    String selectQuery = "SELECT * FROM books";
    try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);
    ) {
      int bookId, authorId;
      String bookName;

      while (rs.next()) {
        bookId = rs.getInt("book_id");
        bookName = rs.getString("book_name");
        authorId = rs.getInt("author_id");

        System.out.println("ID: " + bookId + ", Title: " + bookName + ", Author ID: " + authorId);
      }
    } catch (Exception ex) {
      throw  new Exception("Can not display books" + ex.getMessage());
    }
  }

  private static void insertBook(Connection conn, Scanner scanner) throws Exception {
    System.out.println("Enter book name: ");
    String bookName = scanner.nextLine();

    System.out.println("Enter author ID: ");
    int authorId = Integer.parseInt(scanner.nextLine());

    String insertQuery = "INSERT INTO books (book_name, author_id) VALUES (?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
      stmt.setString(1, bookName);
      stmt.setInt(2, authorId);

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("A new book was inserted successfully!");
      }
    } catch (Exception ex) {
      throw new Exception("Can not insert book: " + ex.getMessage());
    }
  }

}
