# stepCounter4j
プログラムファイルのステップ数集計ツールです。

開発の現場ではあまり経験できなかった以下の点を意識して作成。

- ３層アーキテクチャや軽量DDDを意識したパッケージ構成
-  保守性や変更容易性を意識した実装
- ファクトリクラスやシングルトンなどのデザインパターンを意識した実装
- Mavenによるテスト自動化、実行形式Jarの作成、JavaDoc、テストレポートの出力
    - JavaDoc、テストレポートはGitHub Pagesに公開
- GitHub Actionsを用いたCI/CD環境の構築
    - masterにプッシュ時に単体テスト実施・実行形式Jarのビルド実施

## 画面レイアウト
![スクリーンショット 2023-09-13 1 02 10](https://github.com/takashi-ebina/stepCounterforJava/assets/40939908/b7e68a9e-2ec4-4ce4-af4a-a3bb5b37d18a)

## 主なディレクトリ構成
```
stepCounterforJava
├──docs           ・・・ JavaDoc等のドキュメントが格納されているフォルダ
│   ├── javadoc
│   ├── test-coverage
│   └── test-reports
└──stepCounter    ・・・ stepCounterForJavaの資源が格納されているフォルダ
    ├── src
    │   ├── main  ・・・ javaソースコードが格納されているフォルダ
    │   │   ├── java
    │   │   │   └── co
    │   │   │       └── jp
    │   │   │           └── stepCounter
    │   │   └── resources  ・・・ 静的資源が格納されているフォルダ
    │   │       ├── img
    │   │       └── settings
    │   └── test  ・・・ テストコードが格納されているフォルダ
    ├── target  ・・・ ビルド後の資源が格納されているフォルダ
    │   ├── StepCounter-jar-with-dependencies.jar  ・・・ステップカウント処理実行に利用する実行形式Jarファイル
    │   └── settings    ・・・ 実行形式Jarファイルが参照する静的資源
    │       └── log4j2.xml
    └── pom.xml  
```

## 使い方
### Jarファイル名及びJarファイルビルド方法
#### ファイル名
ステップ数の集計ツール起動時に利用するファイルは`StepCounter-jar-with-dependencies.jar`です。<br>
同階層に`settings`フォルダも存在する状態で起動してください。

#### Jarファイルビルド方法
pom.xmlが存在する階層で以下のコマンドを実行してください。
```
mvn package
```
 * ビルド時に`stepCounter>src>mainフォルダ`に存在するテストコードが起動します。
 * テスト成功の場合に`StepCounter-jar-with-dependencies.jar`ファイルが作成されます。
 * Jarファイルの出力先は`stepCounter>target`フォルダになります。

### GUIでの起動方法
```
java -jar StepCounter-jar-with-dependencies.jar
```

### CUIでの起動方法
```
java -jar StepCounter-jar-with-dependencies.jar　[オプション]
```

#### [オプション]
 * `-h`:このメッセージを表示して終了する。

 * `-i`:対話モードで実行する。（オプションを指定しない場合はGUIモード）

 * `-s`:スクリプトモードで実行する。（オプションを指定しない場合はGUIモード）

 * `-input=[inputDirectoryPath]`:ステップカウント対象のディレクトリパスを指定する。 ※「-s」オプションを利用する場合に指定してください。

 * `-output=[outputFilePath]`:カウント結果出力対象のファイルパスを指定する。 ※「-s」オプションを利用する場合に指定してください。

 * `-asc=[sortTarget]`:ステップカウント処理の出力順を`[sortTarget]`をキーとして昇順ソートする。

 * `-desc=[sortTarget]`:ステップカウント処理の出力順を`[sortTarget]`をキーとして降順ソートする。
 * `[sortTarget]`:0:ファイルパス、1:総行数、2:実行行数、3:コメント行数、4:空行数。

### [出力ファイルイメージ]
```
ファイルパス      ,総行数,実行行数,コメント行数,空行数
/Users/xxA.java,30   ,20     ,4       ,6
/Users/xxB.java,15   ,8     ,2       ,5
/Users/xxC.java,15   ,12    ,2       ,1
合計            ,60   ,40     ,8       ,12
```
### JavaDocの生成
```
mvn javadoc:javadoc
```

### テストコードの実行およびテストレポート、カバレッジの出力
```
mvn clean test -Dmaven.test.failure.ignore=true  site -DgenerateReports=false  surefire-report:report jacoco:report
```
## 対象Javaバージョン
Java SE 16

## 集計対象プログラム言語
 * Java
 * C#
 * sql

## ドキュメント
### 外部設計書一覧
[https://takashi-ebina.github.io/stepCounter4j/externalDesign/readme.md](https://github.com/takashi-ebina/stepCounter4j/tree/master/docs/externalDesign#readme)
### API仕様書
https://takashi-ebina.github.io/stepCounter4j/javadoc/index.html
### 単体テスト
#### テストレポート
https://takashi-ebina.github.io/stepCounter4j/test-reports/surefire-report.html
#### テストカバレッジ
https://takashi-ebina.github.io/stepCounter4j/test-coverage/index.html

---
    
# stepCounter4j
This is a tool to count the number of steps in a program file.

Created with the following points in mind, which were not experienced much in the development field.

- Package structure with an awareness of 3-tier architecture and lightweight DDD
- Implementation with awareness of maintainability and ease of modification
- Implementation with awareness of design patterns such as factory classes and singletons
- Test automation by Maven, creation of executable Jar, JavaDoc, and test report output
    - JavaDoc and test reports are published on GitHub Pages
- Build CI/CD environment using GitHub Actions
    - Perform unit tests and build executable Jar when pushing to master

## Screen layout
![スクリーンショット 2023-09-13 1 02 10](https://github.com/takashi-ebina/stepCounterforJava/assets/40939908/b7e68a9e-2ec4-4ce4-af4a-a3bb5b37d18a)


## Main directory structure
```
stepCounterforJava
├──docs           ・・・ Folder where documents such as JavaDoc are stored.
│   ├── javadoc
│   ├── test-coverage
│   └── test-reports
└──stepCounter    ・・・ Folder in which resources of stepCounterForJava are stored
    ├── src
    │   ├── main  ・・・ The folder where java source code is stored
    │   │   ├── java
    │   │   │   └── co
    │   │   │       └── jp
    │   │   │           └── stepCounter
    │   │   └── resources  ・・・ Folder where static resources are stored
    │   │       ├── img
    │   │       └── settings
    │   └── test  ・・・ Folder where test code is stored
    ├── target  ・・・ Folder where resources after build are stored
    │   ├── StepCounter-jar-with-dependencies.jar  ・・・Executable Jar file used for step count processing execution
    │   └── settings    ・・・ Static resource that executable form Jar file refers to
    │       └── log4j2.xml
    └── pom.xml  
```

## Usage
### Jar file name and Jar file build method
#### File name
The file to be used when starting the step counting tool is `StepCounter-jar-with-dependencies.jar`. <br>
Please start the tool with the `settings` folder also existing on the same level.

#### Jar file build method
Execute the following command in the hierarchy where `pom.xml` exists.
```
mvn package
```
 * Test code in `stepCounter>src>main folder` will be invoked at build time.
 * The `StepCounter-jar-with-dependencies.jar` file will be created on test success.
 * The Jar file will be output to the `stepCounter>target` folder.

### How to start with GUI
```
java -jar StepCounter-jar-with-dependencies.jar
```

### How to start with CUI
```
java -jar StepCounter-jar-with-dependencies.jar [options].
```

#### [options]
 * `-h`: Print this message and exit.

 * `-i`: Run in interactive mode. (GUI mode if no option is specified)

 * `-s`: Run in script mode. (GUI mode if no option is specified)

 * `-input=[inputDirectoryPath]`: Specify the directory path for step counting. * Specify when the `-s' option is used.

 * `-output=[outputFilePath]`:Specify the file path for count result output. *Specify when "-s" option is used.

 * `-asc=[sortTarget]`:Sort the output order of the step count process in ascending order using `[sortTarget]` as a key.

 * `-desc=[sortTarget]`: Sort the output order of the step count process in descending order using `[sortTarget]` as key.
 * `[sortTarget]`:0:file path, 1:total lines, 2:executed lines, 3:comment lines, 4:empty lines.

### [output file image].
```
ファイルパス      ,総行数,実行行数,コメント行数,空行数
/Users/xxA.java,30 ,20 ,4 ,6
/Users/xxB.java,15 ,8 ,2 ,5
/Users/xxC.java,15 ,12 ,2 ,2 ,1
Total ,60 ,40 ,8 ,12
```
### Generating JavaDoc
```
mvn javadoc:javadoc
```

### Run test code and output test report and coverage
```
mvn clean test -Dmaven.test.failure.ignore=true site -DgenerateReports=false surefire-report:report jacoco:report
```
## Target Java version
Java SE 16

## Target program language
 * Java
 * C#
 * sql

## Documentation
### List of external design documents
[https://takashi-ebina.github.io/stepCounter4j/externalDesign/readme.md](https://github.com/takashi-ebina/stepCounter4j/tree/master/docs/externalDesign#readme)

### API Specifications
https://takashi-ebina.github.io/stepCounter4j/javadoc/index.html
### Unit Tests
#### test report
https://takashi-ebina.github.io/stepCounter4j/test-reports/surefire-report.html
#### test coverage
https://takashi-ebina.github.io/stepCounter4j/test-coverage/index.html





