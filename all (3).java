import java.util.*;
import java.lang.*;
import java.io.*;
interface Fightable {
    void attack();
    void defend();
}
class Warrior implements Fightable {
    @Override
    public void attack() {
        System.out.println("Воин наносит удар мечом!");
    }
    @Override
    public void defend() {
        System.out.println("Воин блокирует удар");
    }
}
class Mage implements Fightable {
    @Override
    public void attack() {
        System.out.println("Маг кидает огненный файербол");
    }
    @Override
    public void defend() {
        System.out.println("Маг защетился магией");
    }
    }
class Archer implements Fightable {
    @Override
    public void attack() {
        System.out.println("Лучник выстрельнул из лука");
    }
    @Override
    public void defend() {
        System.out.println("Лучник увернулся");
    }
}
public class Main {
    public static void main(String[] args) {
        Fightable[] fighters = new Fightable[5];
        fighters[0] = new Warrior();
        fighters[1] = new Mage();
        fighters[2] = new Archer();
        fighters[3] = new Mage();
        fighters[4] = new Warrior();
        System.out.println("=== Начало битвы ===");
        for (Fightable fighter : fighters) {
            fighter.attack();
        }
        System.out.println("\n=== Поиск магов для уни. Действий ===");
        for (Fightable fighter : fighters) {
            if (fighter instanceof Mage) {
                System.out.println("Найден маг! Готовим уникальное заклинание...");
                Mage foundMage = (Mage) fighter; 
                System.out.println("Маги делают супер-дупер-мегауникальное заклинание, и все выигрывают");
            }
        }
    }
}
