class BankAccount {
    private String owner;
    private double balance;
    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Положил " + amount + ". Теперь: " + balance);
        } else {
            System.out.println("Нельзя положить отрицательную сумму!");
        }
    }
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Снял " + amount + ". Осталось: " + balance);
        } else {
            System.out.println("Нельзя столько снять!");
        }
    }
    public double getbalance() {
        return balance;
    }
    protected void setBalance(double newBalance) {
        this.balance = newBalance;
    }
    public void printInfo() {
        System.out.println("Счёт " + owner + " баланс " + balance);
    }
}
class SavingsAccount extends BankAccount {
    private double interestRate;
    public SavingsAccount (String owner, double initialBalance, double interestRate) {
    super(owner,initialBalance);
    this.interestRate = interestRate;
    }
    public void addInterest() {
        double interest = getbalance() * (interestRate / 100);
        deposit(interest);
            System.out.println("начисленные процент" + interestRate);
    }
}
class CheckingAccount extends BankAccount {
    private double overdraftLimit;
public CheckingAccount (String owner, double initialBalance, double overdraftLimit) {
    super (owner, initialBalance);
    this.overdraftLimit = overdraftLimit;
}
    @Override
    public void withdraw (double amount) {
        double availableMoney = getbalance() + overdraftLimit;
        if (amount > 0 && amount <= availableMoney) {
            setBalance (getbalance() - amount);
            System.out.println("Снято " + amount);
        } else {
            System.out.println("Превышен лимит, доступно " + availableMoney);
        }
    }
}
public class Main {
public static void main(String[] args) {
    SavingsAccount savings = new SavingsAccount("Boris", 50000.0, 10);
    savings.printInfo();
    savings.addInterest();
    savings.printInfo();
    System.out.println("Пытаемся снять больше");
    savings.withdraw(100000);
    CheckingAccount checking = new CheckingAccount("Suprim", 10000.0, 5000);
    checking.printInfo();
    checking.withdraw(12000);
    checking.printInfo();
    System.out.println("Пытаемся снять больше чем есть");
    checking.withdraw(20000);
}
}
