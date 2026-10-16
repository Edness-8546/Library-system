import java.util.*;
class Student implements Comparable<Student> {
    String name;
    int course; 
    double averageGrade; 
    public Student(String name, int course, double averageGrade) {
        this.name = name;
        this.course = course;
        this.averageGrade = averageGrade;
    }
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }
    @Override
    public String toString() {
        return name + " (ср. балл: " + averageGrade + ")";
    }
}
public class Main {
    public static void main(String[] args) {
        List<Student> allStudents = Arrays.asList(
            new Student("Иван", 2, 4.5),
            new Student("Алекс", 1, 4.8),
            new Student("Мария", 3, 4.9),
            new Student("Петр", 1, 3.8),
            new Student("Ольга", 2, 4.2),
            new Student("Дмитрий", 4, 4.0),
            new Student("Анна", 1, 4.7),
            new Student("Сергей", 3, 3.5),
            new Student("Елена", 2, 4.6),
            new Student("Николай", 4, 4.3)
        );
        System.out.println(" ГРУППИРОВКА СТУДЕНТОВ ПО КУРСАМ ");
        Map<String, List<Student>> courseMap = new TreeMap<>(); 
        for (Student student : allStudents) {
            String courseKey = student.course + " курс";
            List<Student> studentsInCourse = courseMap.computeIfAbsent(courseKey, k -> new ArrayList<>());
            studentsInCourse.add(student);
        }
        for (List<Student> students : courseMap.values()) {
            Collections.sort(students); // 
        }
        for (Map.Entry<String, List<Student>> entry : courseMap.entrySet()) {
            System.out.println(" " + entry.getKey() + " (" + entry.getValue().size() + " студентов):");
            for (Student student : entry.getValue()) {
                System.out.println("   • " + student);
            }
            System.out.println();
        }
        String biggestCourse = null;
        int maxStudents = 0;
        for (Map.Entry<String, List<Student>> entry : courseMap.entrySet()) {
            if (entry.getValue().size() > maxStudents) {
                maxStudents = entry.getValue().size();
                biggestCourse = entry.getKey();
            }
        }
        System.out.println(" Самый многолюдный курс: " + biggestCourse + 
                         " (" + maxStudents + " студентов)");
    }
}
