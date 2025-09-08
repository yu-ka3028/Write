# 実装タスク分解

## 1. データ準備
### 1-1. CSVファイルの雛形を作る（SP:1）
- [x] users.csv, drinks.csv, interactions.csv の空ファイルを作り、1〜2行だけサンプルデータを入れてみる

### 1-2. サンプルデータを10件ずつ埋める（SP:2）
- [x] users.csv, drinks.csv, interactions.csvを10件くらいのダミーデータで埋める

---

## 2. Python環境のセットアップ
### 2-1. requirements.txtの作成（SP:1）
- ディレクトリ構造
  ```
      your_project/
  ├── app.py                # メインのPythonスクリプト
  ├── requirements.txt      # 必要なライブラリ一覧
  ├── Dockerfile            # Docker用設定ファイル
  ├── data/                 # データファイル（CSVなど）を入れるディレクトリ
  │   ├── users.csv
  │   ├── drinks.csv
  │   └── interactions.csv
  ├── output/               # 画像や分析結果などの出力先（任意）
  │   └── drinks_pca.png
  └── README.md             # プロジェクト説明（任意）
  ```
  - 参考ドキュメント：https://rinatz.github.io/python-book/ch04-07-project-structures/
- [] 必要なライブラリ名をrequirements.txtに書く
  - rubyでいうgemfile
  - まずは指定なしで動かす→動いた組み合わせをそのまま==で固定、または~=で軽く固定。
  - パッケージ検索はPyPIや公式ドキュメントで「pip install ○○」の表記を確認（ex: numpy → pip install numpy）
※ 実行はdocker fileで記載


### 2-2. Dockerfileの作成（SP:2）
- [] 公式イメージとrequirements.txtを使ってDockerfileを書く
  - [] RUNにpip install -r requirements.txt記載
    - rubyでいうbundle install
  - 参考ドキュメント：https://hub.docker.com/_/python

### 2-3. DockerでPythonコンテナをビルド・起動（SP:2）
- [] docker build, docker run で「Hello World」を確認
- [] `import pandas` でデータ分析の環境ができていることを確認(verが出るはず？)

---

## 3. データ読み込みと前処理
### 3-1. pandasでCSVを読み込む（SP:1）
- [] drinks.csvをpandasで読み込んでprintする
- [] users.csvとinteractions.csvも同様に読み込む
- [] 各CSVのshape、columns、head()を確認

### 3-2. ingredients列を分割してリスト化（SP:2）
- [] drinks.csvのingredients列を「|」で分割し、各行でリストにする
- [] 分割後のリストを新しい列（ingredients_list）として追加
- [] 分割結果をprintして確認

### 3-3. データの結合と前処理（SP:2）
- [ ] users.csvとinteractions.csvをuser_idで結合
- [ ] 結合したデータとdrinks.csvをitem_idで結合
- [ ] 欠損値の確認と処理
- [ ] データ型の確認と必要に応じて変換

---

## 4. ベクトル化
### 4-1. TfidfVectorizerでingredientsをベクトル化（SP:2）
- [] scikit-learnのTfidfVectorizerでingredientsをベクトルに変換し、shapeや内容をprint
- [ ] ベクトル化のパラメータ調整（max_features, min_df, max_df）
- [ ] 特徴量名（ingredients名）の取得と確認

### 4-2. ベクトル化結果の保存と読み込み（SP:1）
- [ ] ベクトル化した結果をpickleまたはnumpyで保存
- [ ] 保存したファイルから読み込む機能を実装

---

## 5. 近傍検索（K近傍法）
### 5-1. scikit-learnでK近傍検索（SP:2）
- [] NearestNeighborsを使い、特定の飲み物に似ているものをK件出す
- [ ] コサイン類似度とユークリッド距離の比較
- [ ] 異なるK値での結果比較

### 5-2. annoyで近似K近傍検索（SP:2）
- [] annoyライブラリでインデックスを作り、K件の近傍を出す
- [ ] インデックスの保存と読み込み
- [ ] 検索精度と速度の比較

### 5-3. 近傍検索の結果表示機能（SP:1）
- [ ] 飲み物名と類似度スコアを分かりやすく表示
- [ ] 結果をCSVファイルに出力する機能

---

## 6. シンプルな分析
### 6-1. 「Vim使いがよく飲むお酒ランキング」を集計（SP:2）
- [] users.csvとinteractions.csvを組み合わせて、uses_vim=1の人の飲み物選択回数をカウント
- [ ] ランキング結果を降順でソート
- [ ] 飲み物名とカウント数を分かりやすく表示

### 6-2. 属性別ランキングの汎用化（SP:2）
- [ ] 任意の属性（gender, favorite_lang, os等）でフィルタリングできる関数を作成
- [ ] 複数条件でのフィルタリング機能
- [ ] 結果をCSVファイルに出力

### 6-3. 統計情報の表示（SP:1）
- [ ] 各属性の分布（性別、年齢、言語等）
- [ ] 飲み物カテゴリの分布
- [ ] 基本的な統計量（平均、中央値等）

---

## 7. 可視化
### 7-1. PCAで2次元に落としてmatplotlibで散布図を保存（SP:2）
- [] drinksのベクトルをPCAで2次元化し、matplotlibでPNG画像として保存
- [ ] 飲み物名をラベルとして表示
- [ ] カテゴリ別に色分け

### 7-2. その他の可視化（SP:2）
- [ ] 属性別ランキングの棒グラフ
- [ ] 飲み物カテゴリの円グラフ
- [ ] ユーザー属性の分布ヒストグラム

### 7-3. 可視化の設定とカスタマイズ（SP:1）
- [ ] グラフのタイトル、ラベル、凡例の設定
- [ ] 日本語フォントの設定
- [ ] 出力画像の解像度とサイズ調整

---

## 8. CLI化
### 8-1. コマンドライン引数で飲み物名を受け取り、似ている飲み物を出す（SP:2）
- [] argparseで--item引数を受け、K近傍検索の結果をprint
- [ ] --top引数で表示件数を指定可能にする
- [ ] --method引数でscikit-learn/annoyを選択可能にする

### 8-2. 属性別ランキングのCLI化（SP:2）
- [ ] --filter引数で属性フィルタリング
- [ ] --analysis引数で分析タイプを選択
- [ ] エラーハンドリング（存在しない飲み物名等）

### 8-3. 可視化のCLI化（SP:1）
- [ ] --plot引数で可視化を実行
- [ ] 出力ファイル名の指定
- [ ] 複数の可視化を一度に実行

---

## 9. Ruby側の環境準備
### 9-1. Gemfileの作成（SP:1）
- [] 必要なgem（tf-idf-similarity, annoy, gruff, nmatrix, statsample）をGemfileに書く
- [ ] バージョン指定の確認と調整

### 9-2. Dockerfile作成・ビルド・起動（SP:2）
- [] Ruby公式イメージ＋GemfileでDockerfileを作り、docker build, docker runまで確認
- [ ] 必要なシステムライブラリのインストール

### 9-3. 基本的なRuby実装（SP:3）
- [ ] CSV読み込み機能
- [ ] 基本的な集計処理
- [ ] 結果表示機能

---

## 10. Ruby実装
### 10-1. データ読み込みと前処理（SP:2）
- [ ] CSVライブラリでデータ読み込み
- [ ] ingredientsの分割処理
- [ ] データの結合処理

### 10-2. ベクトル化（SP:3）
- [ ] tf-idf-similarity gemを使用したベクトル化
- [ ] 結果の確認とデバッグ

### 10-3. 近傍検索（SP:3）
- [ ] annoy gemを使用した近似K近傍検索
- [ ] 結果の表示と比較

### 10-4. 可視化（SP:2）
- [ ] gruff gemを使用した基本的なグラフ作成
- [ ] 画像ファイルの出力

---

## 11. 比較とドキュメント化
### 11-1. PythonとRubyの実装比較（SP:2）
- [ ] 同じ処理のコード比較
- [ ] 実行速度の比較
- [ ] メモリ使用量の比較

### 11-2. 結果の検証（SP:1）
- [ ] 同じデータでの結果一致確認
- [ ] エッジケースのテスト

### 11-3. 技術記事の作成（SP:3）
- [ ] 実装過程の記録
- [ ] ライブラリ比較のまとめ
- [ ] 学習ポイントの整理

---

## 12. READMEに手順をまとめる（SP:2）
- [ ] 環境構築手順
- [ ] 実行方法
- [ ] 出力例
- [ ] トラブルシューティング

## 参考資料
- [pandas公式チュートリアル](https://pandas.pydata.org/docs/user_guide/10min.html)
- [scikit-learn公式ガイド](https://scikit-learn.org/stable/user_guide.html)
- [matplotlib公式チュートリアル](https://matplotlib.org/stable/tutorials/index.html)