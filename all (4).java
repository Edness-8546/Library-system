import java.util.*;
import java.lang.*;
import java.io.*;
interface  Wallet{
    void deposit(double amount);
    boolean pay(double amount);
    double getBalance();
}
class BasicWallet implements Wallet {
    private String owner;
    private double balance;
    public BasicWallet(String owner,double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("---Нельзя пополнить на такую сумму---");
        } else {
            balance += amount;
        }
    }
    @Override
    public boolean pay(double amount) {
        if (balance < amount) {
            System.out.println("---Недостаточно средств---");
           return false;
        } else {
            balance -= amount;
            return true;
        }
    }
    @Override
    public double getBalance() {
        System.out.println("Ваш баланс составляет " + balance);
        return balance;
    }
}
class CreditWallet implements Wallet {
        private String owner;
        private double balance;
        private double creditLimit;
        public CreditWallet(String owner,double balance,double CreditWallet) {
            this.owner = owner;
        this.balance = balance;
        this.creditLimit = creditLimit;
    }
        @Override
        public void deposit(double amount) {
            if (amount <= 0) {
            System.out.println("---Нельзя пополнить на такую сумму---");
        } else {
           balance += amount; 
        }
    }
    @Override
    public boolean pay(double amount) {
    if (balance - amount < -creditLimit) {
            System.out.println("---Превышен кредитный лимит, оплата отклонена.---");
            return false;
        } else {
            balance -= amount;
            return true;
        }
    }
    public double getBalance() {
        System.out.println("Ваш баланс и кредитный лимит равны " + balance + " " + creditLimit);
        return balance;
    }
    }
class SecureCryptoWallet implements Wallet {
    private String owner;
    private double balance;
    private int pin;
    private boolean isUnlocked;
    public SecureCryptoWallet(String owner, double initialBalance, int pin) {
        this.owner = owner;
        this.balance = initialBalance;
        this.pin = pin;
        this.isUnlocked = false;
    }
    public void enterPin(int enteredPin) {
        if (enteredPin == this.pin) {
            this.isUnlocked = true;
            System.out.println("---Кошелек разблокирован️---");
        } else {
            System.out.println("---Отказанно---");
        }
    }
    @Override
    public void deposit(double amount) {
       if (isUnlocked == false) {
           System.out.println("---Введите пин-код---");
           return;
       }
       if (amount <= 0) {
            System.out.println("---Нельзя пополнить на такую сумму---");
        } else {
            balance += amount;
        }
    }
    @Override
    public boolean pay(double amount) {
        if (balance < amount) {
            System.out.println("---Недостаточно средств---");
            return false;
        } else {
            balance -= amount;
        this.isUnlocked = false; 
        return true;
        }
    }
    @Override
    public double getBalance() {
        return balance;
    }
}
public class Main {
    public static void main (String[] args) {
    Wallet[] wallets = new Wallet[3];
    wallets[0] = new BasicWallet("Olege",0);
    wallets[1] = new CreditWallet("Olya",0,1500);
    wallets[2] = new SecureCryptoWallet("Bahmatic",0,1207);
    System.out.println("===Пополнение===");
    for (Wallet wt : wallets) {
        wt.deposit(1000);
    }
    System.out.println("===Покупаем что-то с помощью крипт. кошелька===");
    wallets[2].pay(500);
    SecureCryptoWallet cryptoWallet = (SecureCryptoWallet) wallets[2];
        cryptoWallet.enterPin(1207);
        wallets[2].pay(500);
        System.out.println("===снимаем 1500 с обыч. кошелька===");
        wallets[1].pay(1500);
        System.out.println("===С кредитного===");
        wallets[1].pay(1500);
        for (int i = 0; i < wallets.length; i++) {
            System.out.println("Кошелек " + (i + 1) + " Баланс: " + wallets[i].getBalance() + " руб.");
        }
    }    
}
