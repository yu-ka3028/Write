# 読書メモ「Java学習第6版（3章）」

Findy × O’Reilly 学び放題プログラムに当選したため、この機会にたくさん本を読んで知識を増やそうと思い始めた読書メモです。  
書籍の内容ではなく、何を得られたかに重点を置いて自分のための読書備忘録も目的としています。

## 1. なぜこの本を選んだか
<span style="font-size: 80%"><span style="color: #cccccc">（書籍を選んだ背景や、今の自分の課題・興味。どんな期待を持って手に取ったか。）</span></span>

rubyを経て静的型付け言語でオブジェクト指向の理解を深めるためJava学習中のため、Javaとは？の学習に選択

## 2. 何が学べたか
<span style="font-size: 80%"><span style="color: #cccccc">（実際に得られた発見や気づき。自分なりの収穫、意外だったこと、考えが変わった点など。）</span></span>

<p class="r-fuki shiba">いきなりインストールで詰まった記憶...あるなぁ</p>

### JavaインストールはOSに合わせて実行
- MacOSの場合、最近のバージョンのオペレーティングシステムにはJavaコマンドの「スタブ」がインストールされている
- 使用OSのJavaをインストールしJDKをインストールする
- コマンドはあるのに実際は動かない...あるある

### クラスパスを理解する
<p class="r-fuki shiba">.classってなんだ....javaをコンパイルすると作成される？</p>
- Javaは.classをJARで読み込み実行される
- Docker環境だと、下記のような設定をDokerfileへ記載して実行するクラスを示す
  ```
  CMD ["java", "com.example.MainClass"]
  ```

## 3. どんな知識があると読みやすいか
<span style="font-size: 80%"><span style="color: #cccccc">（読者が事前に持っていると理解しやすい知識や経験。前提、基礎、関連分野など。）</span></span>

Java開発の環境構築に触れたことがあると「あぁ...」となるかと思います

## 4. 次にどんな知識を入れたいか
<span style="font-size: 80%"><span style="color: #cccccc">（この本を読んで広がった興味や、今後深掘りしたいテーマ。関連書籍や実践したいことなど。）</span></span>

実際に実行されている.classを読んで理解を深めるのも面白そう

## 5. こんな人におすすめ
<span style="font-size: 80%"><span style="color: #cccccc">（どんな目的や課題を持った人に特におすすめか。読者への一言メッセージで締め。）</span></span>

Javaの開発環境を構築しようと思っている人、エラーで詰まっている人。
