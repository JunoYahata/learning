# フロントエンドの基礎

## 言語
* HTML（Hyper Text Markup Language）: WEBページの基本構造を記述する。
* CSS （Cascading Style Sheet）：各部品の見た目を記述する。
* JS （Java Script）：動的な処理を記述する。

## HTML
基本の形
```
<!DOCTYPE html>   ← 文書型宣言
<html>　          ← html開始タグ
  <head>          ← head開始タグ
    head要素（付加情報/表示されない）
  </head>         ← head終了タグ
  <body>　        ← body開始タグ
    body要素（コンテンツ/表示される）
  </body>　        ← body終了タグ
</html>　          ← html終了タグ
```
サンプルは　  [HTMLSample.html](../Source/Sample/HTMLSample.html)

## CSS
基本の形１（タグのstyle属性に記述）
```
<!DOCTYPE html>
<html>
  <head>
    <title>タイトル</title>
    <meta charset="utf-8" />
  </head>
  <body>
    <p style="color: blue;">色が変わる</p>
  </body>
</html>
```
[CSSSample_1.html](../Source/Sample/CSSSample_1.html)

基本の形２（styleタグで定義）
```
<!DOCTYPE html>
<html>
  <head>
    <title>タイトル</title>
    <meta charset="utf-8" />
    <style type="text/css">
      p {
        color: yellow;
        background-color: black;
      }
    </style>
  </head>
  <body>
    <p>色が変わる</p>
  </body>
</html>
```
[CSSSample_2.html](../Source/Sample/CSSSample_2.html)

基本の形３（別ファイルで定義）
```
<!DOCTYPE html>
<html>
  <head>
    <title>タイトル</title>
    <meta charset="utf-8" />
    <link href="Sample.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <p>色が変わる</p>
  </body>
</html>
```
[CSSSample_3.html](../Source/Sample/CSSSample_3.html)

```
p {
    color: pink;
    background-color: green;
}
```
[Sample.css](../Source/Sample/Sample.css)

## JS
基本の形１（scriptタグで記述）
```
<!DOCTYPE html>
<html>
  <head>
    <title>タイトル</title>
    <meta charset="utf-8" />
  </head>
  <body>
    <p>アラートが出る</p>
    <script type="text/javascript">
      alert("アラートがでた");
    </script>
  </body>
</html>
```
[JSSample_1.html](../Source/Sample/JSSample_1.html)

基本の形２（別ファイルで定義）
```
<!DOCTYPE html>
<html>
  <head>
    <title>タイトル</title>
    <meta charset="utf-8" />
    <script type="text/javascript" src="Sample.js"></script>
  </head>
  <body>
    <p>アラートが出る</p>
  </body>
</html>
```
[JSSample_2.html](../Source/Sample/JSSample_2.html)

```
alert("アラートがでた");
```
[Sample.js](../Source/Sample/Sample.js)
