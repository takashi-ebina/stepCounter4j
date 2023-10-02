/* _SQL_ID_ */
TRUNCATE TABLE message;
INSERT INTO message (messageid, messagetext) values ('BLANK_MESSAGE', '{0}　未入力です。');
INSERT INTO message (messageid, messagetext) values ('FILEPATH_MESSAGE', '{0}　ファイルパスを指定しています。');
INSERT INTO message (messageid, messagetext) values ('FOLDERPATH_MESSAGE', '{0}　フォルダパスを指定しています。');
INSERT INTO message (messageid, messagetext) values ('EXTENSION_MESSAGE', '{0}　拡張子が{1}ではありません。');
INSERT INTO message (messageid, messagetext) values ('MAXLENGTH_MESSAGE', '{0}　{1}文字を超えています。');
INSERT INTO message (messageid, messagetext) values ('RESULT_MESSAGE', 'ステップカウント処理が完了しました。 処理結果：{0}');
INSERT INTO message (messageid, messagetext) values ('SUSPENTION_MESSAGE', 'ステップカウント処理が中断しました。');