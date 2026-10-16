class Smartphone {
    private String brand;
    private int batteryLevel;
    private boolean isScreenOn;
    private int volume;
    public Smartphone(String brand, int batteryLevel) {
        this.brand = brand;
        if (batteryLevel > 100) {
            this.batteryLevel = 100;
        } else if (batteryLevel < 0) {
            this.batteryLevel = 0;
        } else {
            this.batteryLevel = batteryLevel;
        }
        this.isScreenOn = false; 
        this.volume = 5;
        System.out.println("Создан телефон: " + this.brand + " | Заряд: " + this.batteryLevel + "%\n");
    }
    public void charge(int minutes) {
        if (batteryLevel >= 100) {
            System.out.println("Телефон уже заряжен на 100%!");
            return;
        }
        batteryLevel += minutes * 2;
        if (batteryLevel > 100) {
            batteryLevel = 100;
        }
        System.out.println("Зарядка длилась " + minutes + " мин. Текущий заряд: " + batteryLevel + "%");
    }
    public void use(int minutes) {
        if (batteryLevel <= 0) {
            System.out.println("Телефон разряжен! Зарядите его.");
            return;
        }
        batteryLevel -= minutes;
        if (batteryLevel <= 0) {
            batteryLevel = 0;
            isScreenOn = false;
            System.out.println("Вот ты и наигрался, телефон разрядился в 0% и выключился.");
        } else {
            System.out.println("Использовали " + minutes + " мин. Осталось: " + batteryLevel + "%");
        }
    }
    public void turnScreenOn() {
        if (batteryLevel <= 0) {
            System.out.println("Не получается: телефон разряжен!");
            return;
        }
        isScreenOn = true;
        System.out.println("Экран включен.");
    }
    public void setVolume(int newVolume) {
        if (!isScreenOn) {
            throw new IllegalArgumentException("Сначала включи экран телефона!");
        }
        if (newVolume < 0 || newVolume > 10) {
            throw new IllegalArgumentException("Громкость должна быть в диапазоне от 0 до 10!");
        }
        this.volume = newVolume;
        System.out.println("Громкость успешно изменена на: " + this.volume);
    }
    public int getVolume() {
        return volume;
    }
}
class Main {
    public static void main(String[] args) {
        Smartphone phone1 = new Smartphone("Samsung", 50);
        phone1.use(20); 
        phone1.turnScreenOn();
        System.out.println("\n--- Попытка изменить громкость на 15 (ошибка) ---");
        try {
            phone1.setVolume(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println("Проверка: громкость осталась прежней: " + phone1.getVolume());
        System.out.println("\n--- Попытка изменить громкость при выключенном экране ---");
        System.out.println("\n--- Корректное изменение громкости ---");
        try {
            phone1.setVolume(8);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println("\n--- Разряжаем телефон полностью ---");
        phone1.use(30);
        phone1.turnScreenOn();
        System.out.println("\n--- Заряжаем ---");
        phone1.charge(40);
    }   
}
