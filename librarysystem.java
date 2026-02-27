import java.io.*;
import java.util.*;

// 1. The Book Class (OOP: Encapsulation)
// We implement 'Serializable' to allow File Handling to save this object
class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    
    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Available: " + (isIssued ? "No" : "Yes");
    }
}

// 2. The Library Class (OOP: Logic & File Handling)
class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private final String filename = "library_data.ser"; // File to store data

    @SuppressWarnings("unchecked")
    public Library() {
        loadData(); // Load previous data when program starts
    }

    public void addBook(int id, String title, String author) {
        books.add(new Book(id, title, author));
        saveData(); // Save immediately after adding
        System.out.println("Book Added Successfully!");
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
        } else {
            for (Book b : books) {
                System.out.println(b);
            }
        }
    }

    // FILE HANDLING: Saving Data
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // FILE HANDLING: Loading Data
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            books = (ArrayList<Book>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's fine (first run)
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data.");
        }
    }
}

// 3. The Main Class
public class librarysystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        while (true) {
            System.out.println("\n--- Library System ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    lib.addBook(id, title, author);
                    break;
                case 2:
                    lib.viewBooks();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}