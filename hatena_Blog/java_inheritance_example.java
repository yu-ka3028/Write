// Javaの単一継承とインターフェースの例

// 1. 単一継承の例
// 親クラス
class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + "が食べています");
    }
    
    public void sleep() {
        System.out.println(name + "が眠っています");
    }
}

// 子クラス（単一継承）
class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    public void bark() {
        System.out.println(name + "がワンワンと吠えています");
    }
}

// 2. インターフェースの例
// インターフェース定義
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// 3. インターフェースを実装したクラス
class Bird extends Animal implements Flyable {
    public Bird(String name) {
        super(name);
    }
    
    @Override
    public void fly() {
        System.out.println(name + "が空を飛んでいます");
    }
}

// 4. 複数のインターフェースを実装
class Duck extends Animal implements Flyable, Swimmable {
    public Duck(String name) {
        super(name);
    }
    
    @Override
    public void fly() {
        System.out.println(name + "が空を飛んでいます");
    }
    
    @Override
    public void swim() {
        System.out.println(name + "が水を泳いでいます");
    }
}

// 5. インターフェースをデータ型として使用
class AnimalTrainer {
    public void trainFlyable(Flyable flyable) {
        System.out.println("飛行訓練を開始します");
        flyable.fly();
    }
    
    public void trainSwimmable(Swimmable swimmable) {
        System.out.println("水泳訓練を開始します");
        swimmable.swim();
    }
}

// 6. メインクラス
public class InheritanceExample {
    public static void main(String[] args) {
        // 単一継承の例
        Dog dog = new Dog("ポチ");
        dog.eat();    // 親クラスのメソッド
        dog.sleep();  // 親クラスのメソッド
        dog.bark();   // 子クラスのメソッド
        
        System.out.println("---");
        
        // インターフェース実装の例
        Bird bird = new Bird("ツバメ");
        bird.eat();   // 親クラスのメソッド
        bird.fly();   // インターフェースのメソッド
        
        System.out.println("---");
        
        // 複数インターフェース実装の例
        Duck duck = new Duck("アヒル");
        duck.eat();   // 親クラスのメソッド
        duck.fly();   // Flyableインターフェースのメソッド
        duck.swim();  // Swimmableインターフェースのメソッド
        
        System.out.println("---");
        
        // インターフェースをデータ型として使用
        AnimalTrainer trainer = new AnimalTrainer();
        trainer.trainFlyable(bird);  // BirdはFlyableを実装
        trainer.trainFlyable(duck);  // DuckもFlyableを実装
        trainer.trainSwimmable(duck); // DuckはSwimmableも実装
        
        // ポリモーフィズムの例
        Animal[] animals = {dog, bird, duck};
        System.out.println("---");
        System.out.println("ポリモーフィズムの例:");
        for (Animal animal : animals) {
            animal.eat();  // 各オブジェクトの型に関係なく、Animalとして扱える
        }
    }
}
