# 要件定義・設計書（最新版）

## 1. プロジェクト概要
ITエンジニアの属性データ（エディタ、好きな言語、OSなど）と飲み物の選択履歴を組み合わせて、「Vim使いがよく飲むお酒ランキング」などの分析を行う。PythonとRubyで同じ分析を実装し、ライブラリの違いを比較する。
Python + Ruby + Dockerを活用し、CLIツールでレコメンド機能を体験できる。可視化はコマンドラインで画像ファイル生成（Python推奨）。

## 2. 目的
- 属性や嗜好に関する“あるある”傾向をK近傍法で分析・可視化
- PythonとRubyのライブラリ（例: scikit-learn, pandas, numpy, annoy, matplotlib, umap-learn, gem: tf-idf-similarity, gem: annoy, gem: gruff, gem: nmatrix, gem: statsample など）を比較し、技術記事としてまとめる
- Dockerで環境構築し、再現性・導入容易性を担保

## 3. 機能要件
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
### 3.1 入力データ
- users.csv  
  - user_id, gender, age, favorite_lang, uses_vim, ai_assistant, os, editor, coding_hours_per_day, night_owl, extroversion_tag, favorite_alcohol
- drinks.csv  
  - drink_id, name, category, abv, ingredients（区切り: "|"、セルはダブルクォート）
- interactions.csv  
  - user_id, item_type, item_id（drink_id）

### 3.2 分析・レコメンド機能（段階的実装）

**Phase 1: 基本分析**
- 属性別ランキング（例: Vim使いがよく飲むお酒ランキング）
- 基本的な集計と可視化

**Phase 2: 類似度分析**
- アイテム間類似（成分ベースK近傍、例: レモンサワーに似ている飲み物）
- scikit-learnのK近傍法を使用

**Phase 3: 高度な分析**
- ユーザー間類似（自分に近い属性の人がよく選ぶアイテム）
- 共起・アソシエーション分析

**Phase 4: Ruby実装**
- 同様の分析をRubyで実装
- ライブラリの比較

### 3.3 技術比較

**必須ライブラリ（Phase 1-2）**
- Python: pandas, scikit-learn, matplotlib
- Ruby: csv, 基本的な配列操作

**推奨ライブラリ（Phase 3）**
- Python: numpy, annoy
- Ruby: gem: tf-idf-similarity, gem: annoy

**オプションライブラリ（Phase 4）**
- Python: umap-learn
- Ruby: gem: gruff, gem: nmatrix, gem: statsample

## 4. 非機能要件
- 小規模データ（100〜300ユーザー、30〜80飲み物）
- CLIで実行・結果表示
- Docker対応
- 記事用にコード・出力例・比較ポイントを整理

## 5. 設計方針
- users.csv：属性・嗜好を固定語彙で管理
- drinks.csv：飲み物名＋カテゴリ＋可変ingredients（成分ベース）
- interactions.csv：ユーザー×飲み物の選択履歴
- 表記揺れ防止のため辞書化・lowercase統一
- 年齢は範囲化（20s, 30s, ...）、個人特定リスクなし

## 6. 分析ロジック
- 内容ベース：drinks.csvのingredientsをone-hot/TF-IDFベクトル化→K近傍法（cosine/Annoy）で類似アイテム抽出  
  - Pythonではscikit-learn、Rubyではgem: tf-idf-similarity
  - 近似探索はPythonではannoy、Rubyではgem: annoy
- 属性ベース：users.csvで条件抽出→interactions集計→ランキング生成
- 共起分析：interactions.csvからペア出現頻度・confidence・lift算出
- ユーザーベース：ユーザー属性ベクトル化→K近傍で近いユーザー抽出→そのユーザーの選択アイテムを推薦
- 可視化：PCA/UMAP/t-SNEで2次元に落とし、Pythonではmatplotlib、Rubyではgem: gruffで画像生成（t-SNE/UMAPはPython推奨）

## 7. CLI設計例（段階的）

**Phase 1: 基本分析**
```bash
python app.py --analysis basic --filter vim_users
```

**Phase 2: 類似度分析**
```bash
python app.py --analysis similarity --item "lemon_sour" --top 5
```

**Phase 3: 高度な分析**
```bash
python app.py --analysis advanced --user 42 --knn --top 5
```

## 8. 技術スタック参考文献
- scikit-learn: https://scikit-learn.org/
- pandas: https://pandas.pydata.org/docs/
- numpy: https://numpy.org/doc/
- annoy (Python): https://github.com/spotify/annoy
- gem: tf-idf-similarity: https://github.com/deepfryed/tf-idf-similarity
- gem: annoy: https://github.com/ankane/annoy
- gem: gruff: https://github.com/topfunky/gruff
- gem: statsample: https://github.com/sciruby/statsample
- gem: nmatrix: https://github.com/SciRuby/nmatrix

## 9. PythonとRubyを比較する意義
- ライブラリ・エコシステムの違い、実装のしやすさ・表現力の違いを体験できる
- 記事として「なぜPythonがデータ分析で選ばれるのか？」を実感できる
- Rubyエンジニアが分析に挑戦する際の参考になる
- 両方のコード・出力例を並べて、読者の理解や学びが深まる

---

# 環境構築手順（Dockerベース）

## Python環境
1. プロジェクトディレクトリを作成し、`python/` サブディレクトリへ移動
2. `requirements.txt` を作成  
   ```
   scikit-learn
   pandas
   numpy
   annoy
   matplotlib
   umap-learn
   ```
3. `Dockerfile` を作成  
   ```dockerfile
   FROM python:3.11-slim
   WORKDIR /app
   COPY requirements.txt .
   RUN pip install --no-cache-dir -r requirements.txt
   COPY . /app
   ```
4. `drinks.csv` などデータファイルを `/data` に配置
5. `docker build -t pyrec ./python` でビルド
6. `docker run --rm -it -v $(pwd)/data:/app/data pyrec python app.py ...` で実行

## Ruby環境
1. プロジェクトディレクトリに `ruby/` サブディレクトリを作成
2. `Gemfile` を作成  
   ```
   gem "tf-idf-similarity"
   gem "annoy"
   gem "gruff"
   gem "nmatrix"
   gem "statsample"
   ```
3. `Dockerfile` を作成  
   ```dockerfile
   FROM ruby:3.3
   WORKDIR /app
   COPY Gemfile Gemfile.lock* ./
   RUN bundle install
   COPY . /app
   ```
4. `drinks.csv` などデータファイルを `/data` に配置
5. `docker build -t rbrec ./ruby` でビルド
6. `docker run --rm -it -v $(pwd)/data:/app/data rbrec ruby app.rb ...` で実行

## docker-compose例
```yaml
services:
  pyrec:
    build: ./python
    volumes:
      - ./data:/app/data
  rbrec:
    build: ./ruby
    volumes:
      - ./data:/app/data
```

---

# 実装の流れ手順書（Python例）

1. データ準備
   - `users.csv`, `drinks.csv`, `interactions.csv` を作成・配置

2. 前処理
   - pandasでCSV読み込み
   - drinks.csvのingredientsをone-hotまたはTF-IDFベクトル化（scikit-learn）

3. 近傍探索
   - scikit-learnで正確KNN（NearestNeighbors, metric='cosine'）
   - annoyで近似KNN（angular/cosine）

4. 属性・共起分析
   - users.csvで条件抽出
   - interactions.csvで集計・ペア出現頻度・confidence/lift算出

5. 可視化
   - PCA/UMAP/t-SNEで2次元に落とす（scikit-learn, umap-learn）
   - matplotlibでPNG画像出力

6. CLIで実行
   - コマンドラインで分析・レコメンド・可視化を実行
   - 出力例や画像を記事用に保存

---

# 実装の流れ手順書（Ruby例）

1. データ準備
   - `users.csv`, `drinks.csv`, `interactions.csv` を作成・配置

2. 前処理
   - CSVでデータ読み込み
   - drinks.csvのingredientsをone-hotベクトル化

3. 近傍探索
   - gem: tf-idf-similarityでベクトル化
   - gem: annoyで近似KNN（angular/cosine）

4. 属性・共起分析
   - users.csvで条件抽出
   - interactions.csvで集計・ペア出現頻度・confidence/lift算出

5. 可視化
   - PCA（gem: nmatrix, gem: statsample）で2次元化
   - gem: gruffで散布図画像出力（t-SNE/UMAPはPython推奨）

6. CLIで実行
   - コマンドラインで分析・レコメンド・可視化を実行
   - 出力例や画像を記事用に保存

---

※ 文中・手順中で出てくるライブラリやgemは、必ず「gem: tf-idf-similarity」「ライブラリ: scikit-learn」などで表記しています。