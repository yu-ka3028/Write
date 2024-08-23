【LINE認証】新規登録の失敗 【Rails×heroku×sorcery】
===
:::message
こちらは実務未経験/初学者の記事です。
内容に間違えなどございましたらご教授いただけますと幸いです。
:::
:::message
※LINE認証の実装はこの記事で取り扱いません
:::

## ゴール：LINEから情報を取得し、DBへ保存できない問題を解消
<!-- どんな目的で、何をしたのかを分かりやすく簡潔に書きます。 -->

本記事の目的は、LINE認証の開発において、LINEから情報を取得し、DBへ保存できない問題を解消方法を紹介することです。<br>
ログイン機能（LINE認証＋sorcery）の実装
- LINEによる外部認証：Displaynameのみアプリ側へ取得しusernameとして使用/emailの取得は行わない
- sorceryによる認証：username/passwordのみ(本リリース時はLINE認証のみを予定しているため)

## やってみた結果
<!-- やってみて自分がどうなったか。さらには、この記事を読んだ人がどうなるのかを書きます。 -->


## 開発環境
- mac M1.2020
- Rails 7.1.3.4
- ruby 3.2.3
- sorcery
- omniauth-auth0
- omniauth-line
- omniauth-rails_csrf_protection

## 事前準備
基本的にはGitHubのドキュメントどおり実装しました。
*参考資料<br>
  @[card](https://github.com/Sorcery/sorcery/wiki/External))

## やったこと:ドキュメントに追加/変更した部分
<!-- 実際にやったことを書いていきます。 -->
【config/initializers/sorcery.rb】
```md
+ #(本リリースで外すか検討中)Sorceryの内部認証を使用
+ config.user_class = "User"

+ #ユーザー名をデフォルトのemailからusernameに変更
+ user.username_attribute_names = [:username]
  
+ #LINEから取得したdisplayNameをローカルでusernameとして使用
+ config.line.user_info_mapping = { username: 'displayName' }
```
補足：開発/テストコード作成時の手間を考慮し、環境設定はテスト/本番の2つを現在の環境に対応するようRails.envで設定した[記事はこちら](https://zenn.dev/yuuka3028/articles/da8bb63c858873)です

## 〇〇のインストール
<!-- まず、〜を開くために... -->

## 〇〇の設定
<!-- このように細かく分け、タイトルと文章を1対1対応で書きます。 -->

## 結論に対しての補足
<!-- - 関連サービスの紹介 -->
<!-- - 参考文献や、公式ページへのリンクなど -->

## おわりにorまとめ
<!-- この記事のまとめや、自分の感じたことなどを記述します。 -->