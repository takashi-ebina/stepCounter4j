# stepCounterforJava
プログラムファイルのステップ数集計ツールです。

開発の現場ではあまり経験できなかった以下の点を意識して製造した・・つもり

 * ３層アーキテクチャや軽量DDDを意識したパッケージ構成
 * 保守性や変更容易性を意識した実装
 * ファクトリクラスやシングルトンなどのデザインパターンを意識した実装
 * Mavenによるテスト自動化、実行形式Jarの作成、JavaDoc、テストレポートの出力（JavaDoc、テストレポートはGitHub Pagesに公開）

# 画面レイアウト
![スクリーンショット 2023-09-13 1 02 10](https://github.com/takashi-ebina/stepCounterforJava/assets/40939908/b7e68a9e-2ec4-4ce4-af4a-a3bb5b37d18a)

# 主なディレクトリ構成
```
stepCounterforJava
├──docs           ・・・ JavaDoc等のドキュメントが格納されているフォルダ
└──stepCounter    ・・・ stepCounterForJavaの資源が格納されているフォルダ
    ├── src
    │   ├── main  ・・・ javaソースコードが格納されているフォルダ
    │   │   ├── java
    │   │   │   └── co
    │   │   │       └── jp
    │   │   │           └── stepCounter
    │   │   └── resources  ・・・ 静的資源が格納されているフォルダ
    │   │       ├── img
    │   │       │   └── icon_ebi.png
    │   │       └── settings
    │   │           └── log4j2.xml
    │   └── test  ・・・ テストコードが格納されているフォルダ
    ├── target  ・・・ ビルド後の資源が格納されているフォルダ
    │   ├── StepCounter-jar-with-dependencies.jar  ・・・ステップカウント処理実行に利用する実行形式Jarファイル
    │   └── settings    ・・・ 実行形式Jarファイルが参照する静的資源
    │       └── log4j2.xml
    └── pom.xml  
```

# 使い方
## Jarファイル名及びJarファイルビルド方法
### ファイル名
ステップ数の集計ツール起動時に利用するファイルは`StepCounter-jar-with-dependencies.jar`です。<br>
同階層に`settings`フォルダも存在する状態で起動してください。

### Jarファイルビルド方法
pom.xmlが存在する階層で以下のコマンドを実行してください。
```
mvn package
```
 * ビルド時に`stepCounter>src>mainフォルダ`に存在するテストコードが起動します。
 * テスト成功の場合に`StepCounter-jar-with-dependencies.jar`ファイルが作成されます。
 * Jarファイルの出力先は`stepCounter>target`フォルダになります。

## GUIでの起動方法
```
java -jar StepCounter-jar-with-dependencies.jar
```

## CUIでの起動方法
```
java -jar StepCounter-jar-with-dependencies.jar　[オプション]
```

### [オプション]
 * `-h`:このメッセージを表示して終了する。

 * `-i`:対話モードで実行する。（オプションを指定しない場合はGUIモード）

 * `-s`:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）

 * `-input=[inputDirectoryPath]`:ステップカウント対象のディレクトリパスを指定する。 ※「-s」オプションを利用する場合に指定してください。

 * `-output=[outputFilePath]`:カウント結果出力対象のファイルパスを指定する。 ※「-s」オプションを利用する場合に指定してください。

 * `-asc=[sortTarget]`:ステップカウント処理の出力順を`[sortTarget]`をキーとして昇順ソートする。

 * `-desc=[sortTarget]`:ステップカウント処理の出力順を`[sortTarget]`をキーとして降順ソートする。
 * `[sortTarget]`:0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数。

### [出力ファイルイメージ]
ファイルパス      ,総行数,実行行数,コメント行数,空行数 <br>
/Users/xxA.java,30   ,20     ,4       ,6     <br>
/Users/xxB.java,15   ,8     ,2       ,5     <br>
/Users/xxC.java,15   ,12    ,2       ,1     <br>
合計            ,60   ,40     ,8       ,12    <br>

## JavaDocの生成
```
mvn javadoc:javadoc
```

## テストコードの実行およびテストレポート、カバレッジの出力
```
mvn clean  test -Dmaven.test.failure.ignore=true  site -DgenerateReports=false  surefire-report:report jacoco:report
```
# 対象Javaバージョン
Java SE 16

# 集計対象プログラム言語
 * Java
 * C#
 * sql

# ドキュメント
## API仕様書
https://takashi-ebina.github.io/stepCounterforJava/javadoc/index.html
## 単体テスト
### テストレポート
https://takashi-ebina.github.io/stepCounterforJava/test-reports/surefire-report.html
### テストカバレッジ
https://takashi-ebina.github.io/stepCounterforJava/test-coverage/index.html

