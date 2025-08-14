public class Library {
  void displayBookInfo(Book book) {
    System.out.println("Title: " + book.title);
    System.out.println("Author: " + book.author);
    System.out.println("Publication Year: " + book.publicationYear);
  }

  public static void main(String[] args) {
    Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", 1960);
    Book book2 = new Book("1984", "George Orwell", 1949);
    Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925);

    Library library = new Library();
    library.displayBookInfo(book1);
    library.displayBookInfo(book2);
    library.displayBookInfo(book3);
  }
}
