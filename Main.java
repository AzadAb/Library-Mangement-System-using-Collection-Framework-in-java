import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding books to the library
        library.addBook(new Book(1, "Java Programming", "James Gosling"));
        library.addBook(new Book(2, "Data Structures", "Robert Lafore"));

        // Adding users to the library system
        library.addUser(new User(101, "Sham"));
        library.addUser(new User(102, "Azad"));

        // Issuing a book to a user
        library.issueBook(1, 101);
        System.out.println("Book 1 issued to Alice");

        // Attempting to issue the same book to another user
        library.issueBook(1, 102);

        // Returning the book
        library.returnBook(1);
        System.out.println("Book 1 returned");

        // Displaying available books
        library.remainingbook();

        // Displaying all users in the system
        library.printuser();
    }
}

class User {
    private String name;
    private int userId;
    private List<Integer> borrowedBooks;

    public User(int userId, String name) {
        this.name = name;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>(); // List to track borrowed books
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Method to borrow a book
    public void borrowBook(int bookId) {
        borrowedBooks.add(bookId);
    }

    // Method to return a book
    public void returnBook(int bookId) {
        borrowedBooks.remove(Integer.valueOf(bookId));
    }

    public String toString() {
        return userId + " " + name;
    }
}

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.isAvailable = true; // Book is available by default
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }
}

class Library {
    private List<Book> books = new ArrayList<>(); // List of books in the library
    private Map<Integer, User> users = new HashMap<>(); // Map of users (userId -> User object)
    private Map<Integer, Integer> issuedBooks = new HashMap<>(); // Map of issued books (bookId -> userId)

    // Method to add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to remove a book from the library
    public void removeBook(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
    }

    // Method to add a user to the system
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    // Method to issue a book to a user
    public void issueBook(int bookId, int userId) {
        // Check if the book is already issued
        if (issuedBooks.containsKey(bookId)) {
            System.out.println("The book is already issued to another person");
            return;
        }

        // Find the book in the library
        Book issuedbook = null;
        for (Book b : books) {
            if (b.getBookId() == bookId && b.isAvailable()) {
                issuedbook = b;
                break;
            }
        }

        // Issue the book if available and the user exists
        if (issuedbook != null && users.containsKey(userId)) {
            issuedBooks.put(bookId, userId);
            issuedbook.setAvailable(false);
            users.get(userId).borrowBook(bookId);
        }
    }

    // Method to return a book
    public void returnBook(int bookId) {
        if (issuedBooks.containsKey(bookId)) {
            int userId = issuedBooks.remove(bookId); // Remove book from issuedBooks map
            users.get(userId).returnBook(bookId); // Remove book from user's borrowed list

            // Mark the book as available again
            for (Book b : books) {
                if (b.getBookId() == bookId) {
                    b.setAvailable(true);
                }
            }
        }
    }

    // Method to print all registered users
    public void printuser() {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + ", Name: " + entry.getValue().getName());
        }
    }

    // Method to display available books in the library
    public void remainingbook() {
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println("The Available Book in Library: " + book.getTitle());
            }
        }
    }
}
