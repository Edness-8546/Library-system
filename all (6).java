import java.util.*;
import java.lang.*;
import java.io.*;
class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
class User {
    private String name;
    private int age;
    private String email;
    public User(String name, String ageStr, String email) 
            throws InvalidNameException, InvalidAgeException, InvalidEmailException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Имя не может быть пустым: '" + name + "'");
        }
        this.name = name;
        int parsedAge;
        try {
            parsedAge = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new InvalidAgeException("Возраст должен быть числом: '" + ageStr + "'");
        }
        if (parsedAge < 0 || parsedAge > 120) {
            throw new InvalidAgeException("Возраст должен быть от 0 до 120! Получено: " + parsedAge);
        }
        this.age = parsedAge;
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("Некорректный email! Получено: '" + email + "'");
        }
        this.email = email;
    }
    @Override
    public String toString() {
        return "Пользователь [имя =" + name + ", возраст =" + age + ", email =" + email + "]";
    }
}
public class Main {
    public static void main(String[] args) {
        String[] userData = {"", "-5", "invalid-email"};
        for (int i = 0; i < userData.length; i++) {
            String problemData = userData[i];
            try {
                User user;
                if (i == 0) {
                    user = new User(problemData, "25", "Shcoln@mail.ru");
                } else if (i == 1) {
                    user = new User("Школьник", problemData, "Shcoln@mail.ru");
                } else {
                    user = new User("Дядька", "25", problemData);
                }
                System.out.println(" Успешно создан: " + user);
            } catch (InvalidNameException e) {
                System.out.println(" [InvalidNameException] " + e.getMessage());
            } catch (InvalidAgeException e) {
                System.out.println(" [InvalidAgeException] " + e.getMessage());
            } catch (InvalidEmailException e) {
                System.out.println(" [InvalidEmailException] " + e.getMessage());
            }
            System.out.println("-------------------------------");
        }
    }
}
