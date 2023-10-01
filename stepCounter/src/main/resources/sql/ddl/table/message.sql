DROP TABLE MESSAGE;
CREATE TABLE IF NOT EXISTS MESSAGE (
    messageid VARCHAR(256) NOT NULL
    , messagetext VARCHAR(256) NOT NULL
    , primary key(messageid)
);
COMMENT ON TABLE message IS 'メッセージ';
COMMENT ON COLUMN message.messageid IS 'メッセージID';
COMMENT ON COLUMN message.messagetext IS 'メッセージ内容';