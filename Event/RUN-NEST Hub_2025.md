# AWS を完全に理解したのでアプリ作ってみた

:::message
この記事は同人誌「RUN-NEST Hub」に寄稿したものです。
技術的な内容に間違いなどございましたら、ご指摘いただけますと幸いです。
:::

## 自己紹介

- 名前：ゆーか
- RUNTEQ53 期卒業生（2025.01 入学）
  - 受講期間中はやたらオン・オフ問わずイベント主催してた人
  - 交流会、朝活会、もくもく会、LT 会、ワークショップ...メインは BBQ と酒の席を設けること...笑
  - 第 5 回 RUNTEQ 祭の運営もﾁﾗｯっと従事
- 2025.12 EC サイト構築・運用の受託会社で勤務
- 現在 Java をメインに開発業務に従事
- 柴犬のお尻ををこよなく愛する変 t...元?薬剤師

## 背景

受講期間中のカリキュラムで少し触った AWS は、卒制で選択できない程度の理解で避けて避けてきました笑

- お金かかりそう...
- なんか難しそう...
- heroku, render, vercel...こっちの方がみんな触ってて安心
  仕事で AWS の SAA 資格取得の機会があったので、このタイミングだ!!とお思い 1 週間ほど AWS 環境を触りながら書籍でふむふむ...3 日ほど資格過去問と書籍で用語確認ガッツリやってなるほど...?!
  以上の「完全に理解したッッ！」で記事書くついでにアプリも作ってみました（このノリで作るのが RUNTEQ 生の良いところ...ｼﾗﾝｹﾄﾞ）

## ゴール

- なーんだ AWS ってこんな感じなのね
- 資格はこうやって勉強すればいいのか
- じゃあ私もアプリ作ってみよ〜っと（AWS でデプロイしてね...?笑）

## 開発環境

- OS: macOS
- 言語: Java
- フレームワーク: Spring Boot
- テンプレートエンジン: Thymeleaf
- データベース: MySQL
- コンテナ: Docker
- クラウド: AWS
  - EC2
  - S3
- 開発ツール: IntelliJ IDEA / Eclipse

### 技術選定の理由

- **Spring Boot**: 実務使用予定のキャッチアップ
- **Docker**:
  - リモート開発でローカル環境への依存を最小化
  - バージョン管理が容易（Java、MySQL、Spring Boot のバージョン固定）
  - EC2+Docker でシンプルな構成が可能
- **AWS EC2**:
  - 仮想サーバーでアプリケーションをホスティング
  - Docker コンテナの実行環境として活用
- **AWS S3**:
  - 静的ファイル（画像、CSS、JS）の保存
  - アプリケーションのアセット管理

## 目次

1. AWS を使ったデプロイ体験談
2. AWS SAA 資格取得の道のり
3. 実際に作ったアプリの紹介

## 内容

### 1.AWS を使ったデプロイ体験談
基本的には公式を参考に下記手順で実施
1. 作成アプリのdockerイメージをローカルへbuildし動作確認
    ```markdown
    docker build -t pitatoku:latest .
    docker run -p 8085:8080 pitatoku:latest
    http://localhost:8085/
    ```
    
2. ローカル環境からAmazon ECRへイメージをpush
  ⅰ Amazon CLIをインストール
  ⅱ ECRへpush 
    ```markdown
    aws configure
    aws ecr get-login-password --region ap-northeast-1 | docker login --username AWS --password-stdin <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com
    docker tag pitatoku:latest <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com/pitatoku:latest
    docker push <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com/pitatoku:latest
    ```
    
3. EC2へdockerをインストールし、イメージをpullしてブラウザで動作確認
  ⅰ Dockerインストール
    ```markdown
    ssh -i pitatoku-dev8.pem ec2-user@<パブリックID>
    sudo yum update -y
    sudo yum install -y docker
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    exit
    ```
  ⅱ ECRのイメージをEC2内でpull
    ```markdown
    aws configure
    aws ecr get-login-password --region ap-northeast-1 | docker login --username AWS --password-stdin <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com
    docker pull <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com/pitatoku:latest
    docker run -d -p 8080:8080 <アカウントID>.dkr.ecr.ap-northeast-1.amazonaws.com/pitatoku:latest  
    ```
  ⅲ ブラウザで確認
    ```
    http://<パブリックIP>:8080/
    ```



i. EC2の起動（インスタンス・キーペア作成、セキュリティグループ設定）
ⅱ. ssh接続確認（AWS CLI使用しAmazon ECRへリポジトリ作成、ログイン・ログアウト）
ⅲ. 試しにローカルでpush（aws configureで認証、ログイン、タグ付け、push）
ⅳ. 本番環境へpush(ssh接続、aws configureで認証、EC2内にもDocker インストール・再起動、ログイン、タグ付け、push)


### 2.AWS SAA 資格取得の道のり

### 3.実際に作ったアプリの紹介

## まとめ

## あとがき

触ってしまえばなんてことはない、どんどん触ってどんどん失敗していきましょう〜（下手するとがっぽり勉強代がかかるので AI の言うとおりじゃなく情報はしっかり精査しましょう...いやそしたら結局...）

## 参考文献
- [Eclipseのダウンロード](https://willbrains.jp/index.html#/pleiades_distros2025.html)
- [spring初挑戦時の参考記事](https://medium-company.com/spring-boot%ef%bc%8bjpa%e3%81%a7%e3%83%87%e3%83%bc%e3%82%bf%e3%83%99%e3%83%bc%e3%82%b9%e3%81%ab%e6%8e%a5%e7%b6%9a%e3%81%99%e3%82%8b%e6%96%b9%e6%b3%95/)
- [JPAのアーキテクチャ](https://spring-boot.jp/database/overview/64#mokuji_8)
- [EclipseからGithubへpush](https://qiita.com/takeshi_1016/items/30429a357884c4238f72)
- [springリファレンス/MVC](https://spring-boot.jp/mvc/overview/12)
