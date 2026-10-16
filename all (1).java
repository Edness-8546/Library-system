import java.util.*;
import java.lang.*;
import java.io.*;
class Person {
    String name;
    int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    void introduce() {
        System.out.println("Привет, я " + name +", мне " + age);
    }
}
class Student extends Person {
    String grade;
    public Student (String name,int age,String grade) {
        super (name,age);
        this.grade = grade;
    }
        @Override
        void introduce() {
            System.out.println("Привет, я " + name +", мне " + age + ", я студент " + grade + " курса");
        }
}
class Teacher extends Person {
    String subject;
    public Teacher (String name,int age,String subject) {
        super(name,age);
        this.subject = subject;
    }
        @Override
        void introduce() {
            System.out.println("Привет, я " + name +", мне " + age + ", я учитель " + subject + " урока");
        }
}
class Main {
    public static void main(String[] args) {
        Student student = new Student("Александр",18,"80");
        student.introduce();
        Teacher teacher = new Teacher("Томавна",50,"Физики");
        teacher.introduce();
    }
}
