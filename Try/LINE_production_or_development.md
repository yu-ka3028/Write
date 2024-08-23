【LINE認証】本番/開発柔軟な環境設定【Rails×heroku×sorcery】
===

## ゴール：本番環境と開発環境を切り替えて実装する準備
<!-- どんな目的で、何をしたのかを分かりやすく簡潔に書きます。 -->
:::message
こちらは実務未経験/初学者の記事です。
内容に間違えなどございましたらご教授いただけますと幸いです。
:::
:::message
※LINE認証の実装はこの記事で取り扱いません
:::
本記事の目的は、LINE認証の開発において、本番環境と開発環境を効率的に切り替えられる実装方法を紹介することです。<br>
【現在の状態】事前にsorceryにてログイン機能の実装まで終了



## やってみた結果
<!-- やってみて自分がどうなったか。さらには、この記事を読んだ人がどうなるのかを書きます。 -->
develompent(テスト)↔︎production(本番)の切り替え作業を最小限に、スムーズな開発が可能となった
  - 具体的には、develompent(テスト)↔︎production(本番) で変更していた下記２箇所のcallback_URL書き換えが不要となった<br>
    - config/initializers/sorcery.rb<br>
    - LINE Developers

## 開発環境
- mac M1/2020
- Rails 7.1.3.4
- ruby 3.2.3
- heroku
- MySQL
- sorcery

## 事前準備
1. gem install
    ```
    gem 'omniauth-auth0'<br>
    gem 'omniauth-line'<br>
    gem 'omniauth-rails_csrf_protection'
    ```
    選定理由：
      - ~auth0: Auth0を使用した認証を容易にし開発速度を上げるため
      - ~line: LINE認証をRailsへ統合するために必要不可欠
      - ~rails_csrf_protection:  OmniAuth の CSRF 保護機能でセキュリティ強化
2. LINE Developersの登録
3. チャネルの作成：LINEログイン

*参考記事<br>
@[card](https://developers.line.biz/ja/docs/line-login/integrate-line-login/#login-flow)

## やったこと
<!-- 実際にやったことを書いていきます。 -->
### 1.ngrokを使用しlocalhost環境をhttpsで外部公開
- 理由：LINEログインのコールバックURLはhttpsのみでhttp://localhost:3000が使用できないため
  - ngrokのインストール<br>
  *参考記事<br>
    @[card](https://zenn.dev/monaka0309/articles/43b700887a328b#ngrok%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E3%83%AD%E3%83%BC%E3%82%AB%E3%83%AB%E7%92%B0%E5%A2%83%E3%82%92https%E3%81%A7%E5%85%AC%E9%96%8B%E3%81%99%E3%82%8B)

### 2.callback_URLの設定
- 【credentials.yml.enc】
sorcery Externalドキュメント通りにcallback_urlを記載<br>
*参考記事<br>
  @[card](https://github.com/Sorcery/sorcery/wiki/External))
  ```
  #開発環境のみの設定
  development:
    line:
      callback_url: "http://(URL)/oauth/callback?provider=line"
  #本番環境のみの設定
  production:
    line:
      callback_url: "http://(URL)/oauth/callback?provider=line"
  ```
  - 秘匿情報の管理についてcredentials.yml.enc選択理由
    - rails newでcredentials.yml.encとmaster.keyはセットで生成され、新規ファイル作成が不要でヒューマンエラーを防げる<br>
  *参考記事<br>
    @[card](https://zenn.dev/kame0707/articles/ef2453f31fe236)
- 【config/initializers/sorcery.rb】
訂正(2024/08/24)：シンプルな記載へリファクタリング
*参考資料<br>
@[card](https://api.rubyonrails.org/classes/Rails.html#method-c-env)

  ~~if Rails.env.production?~~
    ~~config.line.callback_url = Rails.application.credentials.dig(:production, :line, :callback_url)~~
  ~~else~~
    ~~config.line.callback_url = Rails.application.credentials.dig(:development, :line, :callback_url)~~
  ~~end~~
  ```
  config.line.callback_url = Rails.application.credentials.dig(Rails.env, :line, :callback_url)
  ```
  
  
- 【LINE Developers>console>LINEログイン設定>コールバックURL】の登録
  - 改行で複数URL登録可能:上と同じURLを記載
## callback_URL設定で詰まったところ
- 【注意１】URLのみではないため注意
  ```
  callback_url: "http://(URL)/oauth/callback?provider=line"
  ```
- 【注意２】URLは""で囲うのを忘れない!!
  ```
  callback_url: "(callback_URL)"
  ```

## 3.heroku(本番環境)に環境変数の設定
- herokuページのSetting>Config Vars
- Heroku CLIコマンド
  ```
  heroku config:set LINE_CALLBACK_URL="http://(URL)/oauth/callback?provider=line"
  heroku config:set RAILS_MASTER_KEY=<your-master-key>
  heroku config:set RAILS_SERVE_STATIC_FILES=true
  ```

## おわりに
<!-- この記事のまとめや、自分の感じたことなどを記述します。 -->
今回は、"秘匿情報の管理/小規模プロジェクト以外も考慮"を意識し実装してみました。
credentials.yml.encでURLに""をつけておらず時間を消費しましたが、良い教訓になりました。
何かの参考になることがあれば幸いです。最後まで目を通していただきありがとうございました！