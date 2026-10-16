public class Main {
    public static void main(String[] args) {
        Warrior arthur = new Warrior("Артур");
        Dragon smaug = new Dragon("Смауг");
        System.out.println("Начинается битва");
        arthur.printStatus();
        smaug.printStatus();
        System.out.println("=================================\n");
        while (arthur.health > 0 && smaug.health > 0) {
                 arthur.attack(smaug);
            if (smaug.health <= 0) break; 
            System.out.println("---------------------------------");
            smaug.attack(arthur);
            if (arthur.health <= 0) break; 
            System.out.println("---------------------------------");
        }
        System.out.println("Битва закончена");
        
        if (arthur.health > 0) {
            System.out.println(" Победил " + arthur.name + "!");
        } else {
            System.out.println(" Победил " + smaug.name + "!");
        }
    }
}
class Warrior {
    String name;
    int health;
    int damage;
    public Warrior(String name) {
        this.name = name;
        this.health = 100;
        this.damage = 15;
    }
    public void attack(Dragon dragon) {
        int currentDamage = this.damage;
        if (Math.random() < 0.2) {
            currentDamage *= 2; // Урон умножается на 2
            System.out.println("Тотальный удар" + this.name + " сносит " + currentDamage + " урона дракону " + dragon.name );
        } else {
            System.out.println( this.name + " атакует " + dragon.name + " и наносит " + currentDamage + " урона.");
        }
        dragon.health -= currentDamage;
    }

    public void printStatus() {
        System.out.println(" Воин " + name + " | HP: " + health);
    }
}
class Dragon {
    String name;
    int health;
    int damage;
    public Dragon(String name) {
        this.name = name;
        this.health = 200;
        this.damage = 20;
    }
    public void attack(Warrior warrior) {
        System.out.println(" Дракон " + this.name + this.damage + " урона воину " + warrior.name );
        warrior.health -= this.damage;
    }
    public void printStatus() {
        System.out.println(" Дракон " + name + " | HP: " + health);
    }
}
