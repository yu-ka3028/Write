// 継承 vs インターフェースの違いを説明するコード例

// ===== 継承の例 =====
// 親クラス（具象クラス）
class Vehicle {
    protected String brand;
    protected int speed;
    
    public Vehicle(String brand) {
        this.brand = brand;
        this.speed = 0;
    }
    
    // 共通のメソッド
    public void start() {
        System.out.println(brand + "がエンジンを始動しました");
    }
    
    public void stop() {
        System.out.println(brand + "が停止しました");
        this.speed = 0;
    }
    
    public void accelerate() {
        this.speed += 10;
        System.out.println(brand + "の速度: " + speed + "km/h");
    }
    
    // 後から追加される可能性のあるメソッド
    public void maintenance() {
        System.out.println(brand + "のメンテナンスを行います");
    }
}

// 継承を使った子クラス
class Car extends Vehicle {
    public Car(String brand) {
        super(brand);
    }
    
    public void openTrunk() {
        System.out.println(brand + "のトランクを開けました");
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String brand) {
        super(brand);
    }
    
    public void wheelie() {
        System.out.println(brand + "でウィリーしました");
    }
}

// ===== インターフェースの例 =====
// インターフェース定義
interface Drivable {
    void drive();
}

interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// インターフェースのみを使用したクラス
class Airplane implements Flyable {
    private String model;
    
    public Airplane(String model) {
        this.model = model;
    }
    
    @Override
    public void fly() {
        System.out.println(model + "が飛行しています");
    }
    
    // 独自のメソッド
    public void takeOff() {
        System.out.println(model + "が離陸しました");
    }
}

class Boat implements Swimmable {
    private String name;
    
    public Boat(String name) {
        this.name = name;
    }
    
    @Override
    public void swim() {
        System.out.println(name + "が航行しています");
    }
    
    // 独自のメソッド
    public void anchor() {
        System.out.println(name + "が錨を下ろしました");
    }
}

// 複数のインターフェースを実装
class AmphibiousVehicle implements Drivable, Swimmable {
    private String name;
    
    public AmphibiousVehicle(String name) {
        this.name = name;
    }
    
    @Override
    public void drive() {
        System.out.println(name + "が陸上を走行しています");
    }
    
    @Override
    public void swim() {
        System.out.println(name + "が水上を航行しています");
    }
}

// ===== 継承とインターフェースの混在例 =====
class FlyingCar extends Vehicle implements Flyable {
    public FlyingCar(String brand) {
        super(brand);
    }
    
    @Override
    public void fly() {
        System.out.println(brand + "が空を飛んでいます");
    }
    
    // 継承したメソッドをオーバーライド
    @Override
    public void maintenance() {
        System.out.println(brand + "の飛行車両メンテナンスを行います");
    }
}

// ===== 使用例の比較 =====
class VehicleManager {
    // 継承を使った処理
    public void manageVehicles(Vehicle[] vehicles) {
        System.out.println("=== 継承を使った処理 ===");
        for (Vehicle vehicle : vehicles) {
            vehicle.start();        // 継承したメソッド
            vehicle.accelerate();   // 継承したメソッド
            vehicle.maintenance();  // 継承したメソッド（後から追加された）
        }
    }
    
    // インターフェースを使った処理
    public void manageFlyable(Flyable[] flyables) {
        System.out.println("=== インターフェースを使った処理 ===");
        for (Flyable flyable : flyables) {
            flyable.fly();  // インターフェースのメソッドのみ
        }
    }
    
    public void manageSwimmable(Swimmable[] swimmables) {
        System.out.println("=== インターフェースを使った処理 ===");
        for (Swimmable swimmable : swimmables) {
            swimmable.swim();  // インターフェースのメソッドのみ
        }
    }
}

// ===== メインクラス =====
public class InheritanceVsInterfaceExample {
    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();
        
        // 継承の例
        Vehicle[] vehicles = {
            new Car("トヨタ"),
            new Motorcycle("ホンダ"),
            new FlyingCar("テスラ")
        };
        manager.manageVehicles(vehicles);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // インターフェースの例
        Flyable[] flyables = {
            new Airplane("ボーイング747"),
            new FlyingCar("テスラ")
        };
        manager.manageFlyable(flyables);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        Swimmable[] swimmables = {
            new Boat("クルーザー"),
            new AmphibiousVehicle("水陸両用車")
        };
        manager.manageSwimmable(swimmables);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 継承とインターフェースの違いを実演
        Car car = new Car("日産");
        car.start();        // 継承したメソッド
        car.accelerate();   // 継承したメソッド
        car.openTrunk();    // 独自のメソッド
        // car.fly();       // エラー！CarはFlyableを実装していない
        
        Airplane airplane = new Airplane("エアバス");
        airplane.fly();     // インターフェースのメソッド
        airplane.takeOff(); // 独自のメソッド
        // airplane.start(); // エラー！AirplaneはVehicleを継承していない
    }
}
