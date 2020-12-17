# 게시물 전부 삭제
TRUNCATE article;

# 회원 전부 삭제
TRUNCATE `member`;

# 관리자 계정 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = '이명학';