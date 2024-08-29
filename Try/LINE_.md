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

本記事の目的は、LINEから認証情報を取得し、DBへ保存できないエラー文と、解消方法を紹介することです。<br>



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
ログイン機能（LINE認証＋sorcery）の実装
- LINEによる外部認証：Displaynameのみアプリ側へ取得しusernameとして使用/emailの取得は行わない
- sorceryによる認証：username/passwordのみ(本リリース時はLINE認証のみを予定しているため)

## やったこと:ドキュメントに追加/変更した部分
<!-- 実際にやったことを書いていきます。 -->
基本的には公式ドキュメントに沿って実装しました。
*参考資料<br>
  @[card](https://github.com/Sorcery/sorcery/wiki/External))
  @[card]()

【config/initializers/sorcery.rb】
```md
Rails.application.config.sorcery.submodules = [:external]
Rails.application.config.sorcery.configure do |config|

+ #Sorceryの内部認証を使用
+ config.user_class = "User"

+ #ユーザー名をデフォルトのemailからusernameに変更
+ user.username_attribute_names = [:username]
  
+ #LINEから取得したdisplayNameをローカルでusernameとして使用
+ config.line.user_info_mapping = { username: 'displayName' }

+ #LINE友達登録に必要な情報を取得
+ config.line.scope = 'profile'
```
補足：開発、テストコード作成時の手間を考慮し、環境設定はテスト/本番の2つを現在の環境に対応するようRails.envで設定した[記事はこちら](https://zenn.dev/yuuka3028/articles/da8bb63c858873)です。

*参考記事<br>
[@card](https://developers.line.biz/ja/docs/line-login/integrate-line-login/#scopes)

【app/models/user.rb】
```md
class User < ApplicationRecord
+ authenticates_with_sorcery!

  has_many :authentications, dependent: :destroy
  accepts_nested_attributes_for :authentications

+ with_options unless: :using_oauth? do
    validates :username, presence: true, length: { minimum: 4 }
    validates :password, length: { minimum: 4 }, if: -> { new_record? || changes[:crypted_password] }
    validates :password, confirmation: true, if: -> { new_record? || changes[:crypted_password] }
    validates :password_confirmation, presence: true, if: -> { new_record? || changes[:crypted_password] }
  end
+ def using_oauth?
    authentications.present?
  end

+ # OAuth認証で取得したユーザ情報をもとにローカルへユーザを作成
+ def self.create_from(provider)
    user_hash = sorcery_fetch_user_hash(provider)
    return nil if user_hash.nil?
    username = user_hash[:displayName] || "user_#{SecureRandom.hex(4)}"
    user = User.new(
      username: username,
      password: SecureRandom.hex(16)
    )
  
    if user.save
      user.authentications.create(provider: provider, uid: user_hash[:userId])
    else
      Rails.logger.error("Failed to save user: #{user.errors.full_messages.join(", ")}")
    end
    user || raise("Failed to create user from #{provider}")
  end
```
*参考ドキュメント<br>
[@card](https://api.rubyonrails.org/v6.1/classes/Object.html#method-i-with_options)

[@card](https://github.com/Sorcery/sorcery)

・Core< User.authenticates_with_sorcery!<br>
・External< create_from(provider)

【app/controllers/oauths_controller.rb】
```md
class OauthsController < ApplicationController
+ include Sorcery::Controller
  skip_before_action :require_login, raise: false

  def oauth
    login_at(auth_params[:provider])
    puts auth_params[:provider]
  end

  def callback
    provider = params[:provider]
    Rails.logger.debug("Provider: #{provider}")
    
    if @user = login_from(provider)
      Rails.logger.debug("Existing user logged in: #{@user.inspect}")
      redirect_to root_path, notice: "#{provider.titleize}からログインしました!"
    else
      begin
        @user = create_from(provider)

        # LINEから取得したuserIdをローカルでline_user_idに保存
        @user.update(line_user_id: @user.authentications.find_by(provider: provider).uid)
        reset_session
        auto_login(@user)
  
        redirect_to root_path, notice: "#{provider.titleize}からログインしました!"
      rescue
        redirect_to root_path, alert: "#{provider.titleize}からのログインに失敗しました!"
      end
    end
  end

  private

  def auth_params
  # :stateはセキュリティ、:errorはリクエスト失敗時に使用
  + params.permit(:code, :provider, :state, :error)
  end
end
```

## 〇〇のインストール
<!-- まず、〜を開くために... -->

## 〇〇の設定
<!-- このように細かく分け、タイトルと文章を1対1対応で書きます。 -->

## 結論に対しての補足
<!-- - 関連サービスの紹介 -->
<!-- - 参考文献や、公式ページへのリンクなど -->

## おわりにorまとめ
<!-- この記事のまとめや、自分の感じたことなどを記述します。 -->