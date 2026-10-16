class Student {
    private String name;
    private int grade;
    public Student(String name, int grade) {
        this.name = name;
        setGrade(grade); 
    }
    public String getName() {
    return name;
}
    public void setName(String name) {
        this.name = name;
}
    public int getGrade() {
        return grade;
}
    public void setGrade(int grade) {
        if (grade >= 1 && grade <= 10) {
            this.grade = grade;
    } else {
            System.out.println(" Ошибка: оценка должна быть от 1 до 10! Вы попытались поставить: " + grade);
        }
    }   
    public void printInfo() {
        System.out.println(" Студент: " + name + " | Оценка: " + grade);
    }
}
public class Main {
    public static void main(String[] args) {
    Student student1 = new Student("Иван", 5);
    student1.printInfo(); 
    System.out.println("\n--- Пытаемся изменить оценку ---");
    student1.setGrade(8); 
    student1.printInfo();
    student1.setGrade(12); 
    student1.printInfo(); 
    student1.setGrade(-5);
    student1.printInfo(); 
    }
}
