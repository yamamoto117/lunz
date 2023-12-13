# ランジー /【ジードライブ関係者向け】ランチ口コミアプリ

<img width="1440" alt="top" src="https://github.com/yamamoto117/lunz/assets/99392507/bda4e139-d674-4413-aa2c-9f570b196033">

(※『ジードライブ』とは開発者がお世話になった職業訓練校名です)

## 1.アプリ概要

### (1)開発目的
* 職業訓練中の昼休憩は45分間と短いため、外食する場合には「お店選び」が重要
* お店選びには『食べログ』などのサービスを利用する方が多いが、「食事にかかる時間」の情報はほとんど無い
* そこで「食事にかかる時間」にフォーカスした口コミプラットフォームを開発することにより、お店選びを楽にしたいと考えた

### (2)コンセプト
「ジードライブ関係者のランチのお店選びを楽に」

(アプリ名の『ランジー』は、「ランチ × ジードライブ」から名付けました)

### (3)特徴
* 口コミ評価項目は「時間的余裕」(「移動時間 + 食事にかかる時間のゆとり」を星5段階で評価)
* 口コミ評価が高いお店を選ぶことで、比較的時間にゆとりのある外食が可能となる

### (4)アプリURL
http://java.apps.rok.jp:22757/

## 2.画面
### (1)ホーム(検索結果表示時)
<img width="1440" alt="search" src="https://github.com/yamamoto117/lunz/assets/99392507/6a2492e8-dcad-48e5-a69a-4247d1ab01bd">

検索結果の並び替え(評価が高い順)
![sort](https://github.com/yamamoto117/lunz/assets/99392507/554b3994-8b7e-4573-9d93-5771514b193b)

### (2)お店詳細
![detail](https://github.com/yamamoto117/lunz/assets/99392507/eba5d5ab-d27d-4f29-b85a-33d78b9ee920)

### (3)口コミ投稿
<img width="1440" alt="review" src="https://github.com/yamamoto117/lunz/assets/99392507/9e3eb2a3-eefc-4f7c-86e3-f0143b0d51d8">

## 3.機能一覧
### (1)メイン機能
* ジードライブ周辺のお店情報閲覧
	* キーワード検索
	* ジャンル検索
	* 評価が高い順に並び替え
* 口コミ投稿(訪問日 / 時間的余裕 / コメント)

### (2)認証機能
* ユーザー登録
* ログイン / ログアウト
* ゲストログイン

## 4.使用技術
### (1)フロントエンド
* HTML
* CSS
* JavaScript
* Tailwind CSS 3.3.3

### (2)バックエンド
* Java 17.0.6
* Spring Boot 2.7.16
* Thymeleaf 3.0.15
* MyBatis 2.3.1
* MySQL 8.0.23
* Maven 3.2.0
* jBCrypt 0.4.3
* Node.js 18.12.1 / npm 8.19.2

### (3)インフラ
* Apache Tomcat 9.0.41

### (4)その他
* Google Maps API(Places API / Directions API / Maps API)
* Git 2.37.1 / GitHub
* Eclipse
* Windows 10 / macOS

## 5.ER図
![lunz_er](https://github.com/yamamoto117/lunz/assets/99392507/4da677c5-a803-472d-9891-ff834a6a2554)