# 読書ログ「Java学習第6版」

Findy × O’Reilly 学び放題プログラムに当選したため、この機会にたくさん本を読んで知識を増やそうと思い始めた読書ログです。  
書籍の内容ではなく、何を得られたかに重点を置いて自分のための読書備忘録も目的としています。

## 1. なぜこの本を選んだか
<span style="font-size: 80%"><span style="color: #cccccc">（書籍を選んだ背景や、今の自分の課題・興味。どんな期待を持って手に取ったか。）</span></span>
rubyを経て静的型付け言語でオブジェクト指向の理解を深めるためJava学習中のため、Javaとは？の学習に選択

## 2. 何が学べたか
<span style="font-size: 80%"><span style="color: #cccccc">（実際に得られた発見や気づき。自分なりの収穫、意外だったこと、考えが変わった点など。）</span></span>
<p class="r-fuki shiba">とりあえずこういうもの！と書き方を学んで実装していた部分をコードベースで細かく解説してくれて、なんとなくで過ごし得ていた箇所の理解が深まった。
</p>

### ポリモーフィズムとは
より特殊なクラス（子クラス）が一般化されたクラス（親クラス）や先祖のクラスを持って働けること
### 単一継承とインターフェース
- Javaは単一継承（子クラスが直接継承できる親クラスが1つだけ）
- ただしインターフェースは単一継承の一種の逃げ道となる可能性がある
- インターフェースはクラスは継承できないが、データ型として使用はできる
    - 継承には2種類
      型の継承（仕様を継承する、メソッドやその振る舞い）、実装の継承（コードの継承、処理）
<p class="r-fuki shiba">インターフェースはメソッド定義のみ持ってきて、実装は含まないのでimplementtsした際に自分で実装する必要あるのか...どれくらい変わるのか、どう使い分けるのか...実際やってみないとイメージ湧かないな...
</p>
【コード書いて整理してみた】
1. Animalクラスで動物の数を数える
2. Dogクラスで継承する
3. Animalクラスに追加機能として動物の足の数を数える
4. Dogクラスでも足の数を数える機能を継承
```
プロジェクトフォルダ/
├── Animal.java      ← 親クラス
├── Dog.java         ← 子クラス1
└── Main.java        ← メインクラス
```
1. 親クラスを定義する
親クラス【Animal.java】
- 型と実装の両方を含む
```
class Animal {
    protected int count;
    
    public Animal(int count) {
        this.count = count;
    }
    
    public void showCount() {
        System.out.println("動物が" + count + "匹います");
    }
    
    public int getCount() {
        return count;
    }
}
```
2-1:継承の場合
- 型と実装の両方をそのまま使用
子クラス1【Dog.java】
- 継承したメソッドをそのまま使用
```
class Dog extends Animal {
    public Dog(int count) {
        super(count);
    }
}
```

2-2:インターフェースの場合
- 型のみ使用し、再度実装の必要あり
子クラス1【Dog.java】
```
interface AnimalCount {
    void showCount();
    int getCount();
}

public class Dog implements AnimalCount {
    private int count;
    
    public Dog(int count) {
        this.count = count;
    }
    
    // 子クラスでも再度実装
    @Override
    public void showCount() {
        System.out.println("動物が" + count + "匹います");
    }
    
    @Override
    public int getCount() {
        return count;
    }
}
```

3. 親クラスへ機能追加
親クラス【Animal.java】
```
class Animal {
    protected int count;
  + protected int legCount;  // 新機能：足の数
    
    public Animal(int count, int legCount) {
        this.count = count;
        this.legCount = legCount;
    }
    
    public void showCount() {
        System.out.println("動物が" + count + "匹います");
    }
    
    public int getCount() {
        return count;
    }
    
  + // 新機能：足の数を表示
  + public void showLegs() {
  +     System.out.println("足が" + legCount + "本あります");
  + }
    
  + public int getLegCount() {
  +     return legCount;
  + }
}
```

4-1:継承の場合
子クラス1【Dog.java】
- int legCountとlegCountのみを追記
```
class Dog extends Animal {
    public Dog(int count, int legCount) {
        super(count, legCount);
    }
}
```

4-2:インターフェースの場合
子クラス1【Dog.java】
- 再度実その必要あり
```
public class Dog implements AnimalCount {
    private int count;
  + private int legCount;
    
    public Dog(int count, int legCount) {
        this.count = count;
      + this.legCount = legCount;
    }
    
    // 子クラスでも再度実装
    @Override
    public void showCount() {
        System.out.println("動物が" + count + "匹います");
    }
    
    @Override
    public int getCount() {
        return count;
    }

  + // 足の数用のメソッドを追加
  + public void showLegs() {
  +     System.out.println("足が" + legCount + "本あります");
  + }

  + public int getLegCount() {
  +     return legCount;
  + }
}
```

```
メインクラス【Main.java】
出力結果
動物が3匹います
足が4本あります
```
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog(3, 4);
        dog.showCount();
        dog.showLegs();
    }
}
```

## 3. どんな知識があると読みやすいか
<span style="font-size: 80%"><span style="color: #cccccc">（読者が事前に持っていると理解しやすい知識や経験。前提、基礎、関連分野など。）</span></span>
多重継承やインターフェースの用語を知っていると入ってきやすいと思いました。
加えてオブジェクト指向とは何かをざっくり押さえておくと読みやすそうです。

## 4. 次にどんな知識を入れたいか
<span style="font-size: 80%"><span style="color: #cccccc">（この本を読んで広がった興味や、今後深掘りしたいテーマ。関連書籍や実践したいことなど。）</span></span>
知識というより実践で活かしたいと感じた。
継承とインターフェースどちらを使うのか、同共存させるといいのかについて考えるきっかけとなった
【今後深めていきたいテーマ】
- 今後親クラスへ追加機能の実装予定があるか
- 親クラスで追加された機能を子クラスでも使用するのか
- 継承すると後に親クラスで変更を考えると保守性が下がるのでは
- インターフェースの方が可読性が高いのでは
- 継承しても子クラスでオーバーライドするならインターフェースでも良いのでは

## 5. こんな人におすすめ
<span style="font-size: 80%"><span style="color: #cccccc">（どんな目的や課題を持った人に特におすすめか。読者への一言メッセージで締め。）</span></span>
オブジェクト指向に興味がある人
Javaの単一継承やインターフェースの使い分けに関して考えたい人
