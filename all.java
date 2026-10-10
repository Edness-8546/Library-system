import java.util.Arrays;
class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
class CannotBorrowException extends Exception {
    public CannotBorrowException(String message) {
        super(message);
    }
}
interface Borrowable {
    boolean canBeBorrowed();
    int getMaxBorrowDays();
}
abstract class LibraryItem {
    private int id;
    private String title;
    private int year;
    private boolean isAvailable;
    public LibraryItem(int id, String title, int year, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.isAvailable = isAvailable;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title; 
    }
    public int getYear() {
        return year; 
    }
    public boolean isAvailable() {
        return isAvailable; 
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public abstract String getItemType();
    @Override
    public String toString() {
        return String.format("[ID:%d] %s (%d г.) | %s | Статус: %s",
            id, title, year, getItemType(), isAvailable ? "Доступно" : "Выдано");
    }
}
class Book extends LibraryItem implements Borrowable {
    private String author;
    private int pages;
    private String genre;
    public Book(int id, String title, int year, String author, int pages, String genre) {
        super(id, title, year, true);
        this.author = author;
        this.pages = pages;
        this.genre = genre;
    }
    @Override
    public String getItemType() {
        return "---Книга---"; 
    }
    @Override
    public boolean canBeBorrowed() {
        return true; 
    }
    @Override
    public int getMaxBorrowDays() {
        return 14; 
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" | Автор: %s, Страниц: %d, Жанр: %s", author, pages, genre);
    }
}
class Magazine extends LibraryItem implements Borrowable {
    private int issueNumber;
    private String category;
    public Magazine(int id, String title, int year, int issueNumber, String category) {
        super(id, title, year, true);
        this.issueNumber = issueNumber;
        this.category = category;
    }
    @Override
    public String getItemType() {
        return "---Журнал---"; 
    }
    @Override
    public boolean canBeBorrowed() {
        return false; 
    }
    @Override
    public int getMaxBorrowDays() {
        return 0; 
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" | Выпуск: %d, Категория: %s", issueNumber, category);
    }
}
class DVD extends LibraryItem implements Borrowable {
    private String director;
    private int durationMinutes;
    public DVD(int id, String title, int year, String director, int durationMinutes) {
        super(id, title, year, true);
        this.director = director;
        this.durationMinutes = durationMinutes;
    }
    @Override
    public String getItemType() {
        return "---DVD---"; 
    }
    @Override
    public boolean canBeBorrowed() {
        return true; 
    }
    @Override
    public int getMaxBorrowDays() {
        return 7; 
    }
    @Override
    public String toString() {
        return super.toString() + String.format(" | Режиссёр: %s, Длительность: %d мин.", director, durationMinutes);
    }
}
abstract class User {
    private int id;
    private String name;
    private String email;
    public User(int id, String name, String email) throws InvalidEmailException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("!!!Некорректный email: " + email + "!!!");
        }
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public abstract String getUserType();
    @Override
    public String toString() {
        return String.format("[ID:%d] %s (%s) | Email: %s", id, name, getUserType(), email);
    }
}
class Reader extends User {
    private LibraryItem[] borrowedItems;
    private int borrowedCount;
    public Reader(int id, String name, String email) throws InvalidEmailException {
        super(id, name, email);
        this.borrowedItems = new LibraryItem[5];
        this.borrowedCount = 0;
    }
    @Override
    public String getUserType() {
        return "===Читатель==="; 
    }
    public void borrowItem(LibraryItem item) throws CannotBorrowException {
        if (!(item instanceof Borrowable)) {
            throw new CannotBorrowException("---Предмет не реализует интерфейс Borrowable.---");
        }
        Borrowable b = (Borrowable) item;
        if (!b.canBeBorrowed()) {
            throw new CannotBorrowException("---Этот предмет нельзя брать на дом (например, журналы).---");
        }
        if (borrowedCount >= 5) {
            throw new CannotBorrowException("---Превышен лимит: вы уже взяли 5 предметов.---");
        }
        if (!item.isAvailable()) {
            throw new CannotBorrowException("---Предмет '" + item.getTitle() + "' уже выдан другому читателю.---");
        }
        borrowedItems[borrowedCount] = item;
        borrowedCount++;
        item.setAvailable(false);
        System.out.println("===Читатель '" + getName() + "' взял: " + item.getTitle() + "===");
    }
    public void returnItem(int itemId) throws CannotBorrowException {
        int index = -1;
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedItems[i].getId() == itemId) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new CannotBorrowException("===У читателя '" + getName() + "' нет предмета с ID: " + itemId + "===");
        }
        LibraryItem item = borrowedItems[index];
        item.setAvailable(true);
        for (int i = index; i < borrowedCount - 1; i++) {
            borrowedItems[i] = borrowedItems[i + 1];
        }
        borrowedItems[borrowedCount - 1] = null;
        borrowedCount--;
        System.out.println("===Читатель '" + getName() + "' вернул предмет ID: " + itemId + "===");
    }
    public LibraryItem[] getBorrowedItems() {
        LibraryItem[] result = new LibraryItem[borrowedCount];
        for (int i = 0; i < borrowedCount; i++) {
            result[i] = borrowedItems[i];
        }
        return result;
    }
}
class Librarian extends User {
    private String department;
    public Librarian(int id, String name, String email, String department) throws InvalidEmailException {
        super(id, name, email);
        this.department = department;
    }
    @Override
    public String getUserType() { return "===Библиотекарь==="; }
    @Override
    public String toString() {
        return super.toString() + " | Отдел: " + department;
    }
}
class Library {
    private LibraryItem[] items = new LibraryItem[100];
    private int itemCount = 0;
    private User[] users = new User[50];
    private int userCount = 0;
    private int nextItemId = 1;
    private int nextUserId = 1;
    public int getNextItemId() {
        return nextItemId++; 
    }
    public int getNextUserId() {
        return nextUserId++; 
    }
    public void addItem(LibraryItem item) {
        if (itemCount < items.length) {
            items[itemCount++] = item;
        }
    }
    public void addUser(User user) {
        if (userCount < users.length) {
            users[userCount++] = user;
        }
    }
    public LibraryItem findItemById(int id) throws ItemNotFoundException {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getId() == id) {
                return items[i];
            }
        }
        throw new ItemNotFoundException("---Предмет с ID " + id + " не найден в библиотеке.---");
    }
    public User findUserById(int id) throws UserNotFoundException {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("===Пользователь с ID " + id + " не найден.===");
    }
    public LibraryItem[] getAllItems() {
        LibraryItem[] result = new LibraryItem[itemCount];
        for (int i = 0; i < itemCount; i++) result[i] = items[i];
        return result;
    }
    // Добавленно: Метод для получения всех пользователей
    public User[] getAllUsers() {
        User[] result = new User[userCount];
        for (int i = 0; i < userCount; i++) result[i] = users[i];
        return result;
    }
    public LibraryItem[] getAvailableItems() {
        int count = 0;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].isAvailable()) count++;
        }
        LibraryItem[] result = new LibraryItem[count];
        int index = 0;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].isAvailable()) result[index++] = items[i];
        }
        return result;
    }
    public LibraryItem[] getItemsByType(String type) {
        int count = 0;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getItemType().equals(type)) {
                count++;
            }
        }
        LibraryItem[] result = new LibraryItem[count];
        int index = 0;
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getItemType().equals(type)) {
                result[index++] = items[i];
            }
        }
        return result;
    }
}
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        System.out.println("=== 1. ДОБАВЛЕНИЕ ПРЕДМЕТОВ ===");
        library.addItem(new Book(library.getNextItemId(), "---Война и мир---", 1869, "===Л.Н. Толстой===", 1225, "---Роман---"));
        library.addItem(new Book(library.getNextItemId(), "---Колобок---", 1300, "===Сборник народных русских сказок===", 200, "Сказка"));
        library.addItem(new Book(library.getNextItemId(), "Стационный смотритель", 1830, "А.С.Пушкин", 350, "Повесть"));
        library.addItem(new Magazine(library.getNextItemId(), "Докторские науки", 1934, 120, "Наука"));
        library.addItem(new Magazine(library.getNextItemId(), "Как стать миллиардером", 2002, 20, "Бизнес"));
        library.addItem(new DVD(library.getNextItemId(), "Чума", 2027, "Иркутск", 148));
        library.addItem(new DVD(library.getNextItemId(), "Человек паук", 2026, "Марк Роберт", 169));
        System.out.println("=== 2. ДОБАВЛЕНИЕ ПОЛЬЗОВАТЕЛЕЙ ===");
        // Объявляем переменные до try, чтобы они были видны во всем блоке
        Reader reader1 = null;
        Reader reader2 = null;
        Reader reader3 = null;
        Librarian librarian = null;
        try {
            reader1 = new Reader(library.getNextUserId(), "===Иван Иванов===", "ivan@mail.ru");
            reader2 = new Reader(library.getNextUserId(), "===Петр Петров===", "petr@yandex.ru");
            reader3 = new Reader(library.getNextUserId(), "===Анна Сидорова===", "anna@gmail.com");
            librarian = new Librarian(library.getNextUserId(), "===Мария Сергеевна===", "lib@library.ru", "---Художественная литература---");
            library.addUser(reader1);
            library.addUser(reader2);
            library.addUser(reader3);
            library.addUser(librarian);
            System.out.println("---Пользователи успешно созданы.---\n");
            System.out.println("=== 3. ДЕЙСТВИЯ ЧИТАТЕЛЯ 1 (Иван) ===");
            reader1.borrowItem(library.findItemById(1));
            reader1.borrowItem(library.findItemById(2));
            try {
                System.out.println("---Попытка взять журнал---");
                reader1.borrowItem(library.findItemById(4));
            } catch (CannotBorrowException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            reader1.returnItem(1);
            System.out.println("\n=== 4. ДЕЙСТВИЯ ЧИТАТЕЛЯ 2 (Петр) ===");
            reader2.borrowItem(library.findItemById(3));
            reader2.borrowItem(library.findItemById(6));
            try {
                System.out.println("---Попытка взять уже выданную книгу---");
                reader2.borrowItem(library.findItemById(2));
            } catch (CannotBorrowException e) {
                System.out.println("---Ошибка: " + e.getMessage() + "---");
            }
            System.out.println("\n=== 5. ДЕЙСТВИЯ ЧИТАТЕЛЯ 3 (Анна) ===");
            try {
                System.out.println("===Анна пытается взять 6 предметов===");
                reader3.borrowItem(library.findItemById(1));
                reader3.borrowItem(library.findItemById(3));
                reader3.borrowItem(library.findItemById(5));
                reader3.borrowItem(library.findItemById(6));
                reader3.borrowItem(library.findItemById(7));
                reader3.borrowItem(library.findItemById(4)); // 6-й предмет, вызовет ошибку
            } catch (CannotBorrowException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println("\n=== 6. ДЕЙСТВИЯ БИБЛИОТЕКАРЯ ===");
            try {
                LibraryItem foundItem = library.findItemById(7);
                System.out.println("===Библиотекарь нашел предмет:");
                System.out.println("   " + foundItem + "===");
            } catch (ItemNotFoundException e) {
                System.out.println("" + e.getMessage());
            }
            System.out.println("\n=== 7. СТАТИСТИКА БИБЛИОТЕКИ ===");
            System.out.println("\n---Все книги в библиотеке: ");
            // ИСПРАВЛЕНО: Тип должен точно совпадать с тем, что возвращает getItemType()
            for (LibraryItem item : library.getItemsByType("---Книга---")) {
                System.out.println("   " + item + "---");
            }
            System.out.println("\n---Доступные для выдачи предметы:");
            for (LibraryItem item : library.getAvailableItems()) {
                System.out.println("   " + item + "---");
            }
            System.out.println("\n===Читатели и их предметы:");
            // ИСПРАВЛЕНО: Теперь мы итерируемся по пользователям, а не по предметам
            // И используем полиморфизм для безопасного приведения типа
            for (User user : library.getAllUsers()) {
                if (user instanceof Reader) {
                    Reader r = (Reader) user;
                    System.out.println("  === " + r.getName() + " взял: " + Arrays.toString(r.getBorrowedItems()) + " ===");
                }
            }
            System.out.println("\n===Читатели и их предметы:");
            // Теперь мы итерируемся по пользователям, а не по предметам
            // и используем полиморфизм для безопасного приведения типа
            for (User user : library.getAllUsers()) {
                if (user instanceof Reader) {
                    Reader r = (Reader) user;
                    System.out.println("  === " + r.getName() + " взял: " + Arrays.toString(r.getBorrowedItems()) + " ===");
                }
            }
        // ИСПРАВЛЕНО: UserNotFoundException удален, так как он нигде не выбрасывается в этом try
        } catch (InvalidEmailException | ItemNotFoundException | CannotBorrowException e) {
            System.out.println("---Критическая ошибка системы: " + e.getMessage() + "---");
        }
    }
}