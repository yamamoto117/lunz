# 【ジードライブ関係者向け】ランチ口コミアプリ『ランジー』

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

### (4)URL
http://java.apps.rok.jp:22757/

## 2.主な機能

<table>
	<tr>
		<th style="text-align: center">お店検索</th>
		<th style="text-align: center">検索結果並び替え</th>
		<th style="text-align: center">口コミ投稿</th>
	</tr>
	<tr>
		<td><img alt="search" src="https://github.com/yamamoto117/lunz/assets/99392507/e45d2660-e80c-4d91-b3ed-79e64dbde460"></td>
		<td><img alt="sort" src="https://github.com/yamamoto117/lunz/assets/99392507/e64893bf-587c-4d2f-b74f-62874c5c9c08"></td>
		<td><img alt="review" src="https://github.com/yamamoto117/lunz/assets/99392507/3d511135-2f7d-4107-8c7b-f0e7322ced63"></td>
    </tr>
</table>

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