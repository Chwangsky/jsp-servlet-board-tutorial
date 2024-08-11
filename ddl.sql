-- Active: 1722838177121@@127.0.0.1@3308@ebrainsoft_study

use ebrainsoft_study;

CREATE TABLE board
(
  board_id    int       NOT NULL AUTO_INCREMENT,
  title       varchar(100)  NOT NULL COMMENT '게시글의 제목',
  writer      varchar(5)    NOT NULL COMMENT '게시글의 작성자',
  views       int       NOT NULL COMMENT '게시글의 조회수',
  reg_date    datetime      NOT NULL COMMENT '게시글의 등록시간',
  update_date datetime      NOT NULL COMMENT '게시글의 최근 수정시간',
  category_id int       NOT NULL default 1,
  content     varchar(2000) NOT NULL COMMENT '게시글의 내용',
  password    varchar(16)   NOT NULL COMMENT '게시글 삭제를 위한 비밀번호',
  PRIMARY KEY (board_id)
) COMMENT '자유게시판 테이블';

CREATE TABLE category
(
  category_id int     NOT NULL AUTO_INCREMENT,
  name        varchar(55) NOT NULL COMMENT '카테고리 이름',
  PRIMARY KEY (category_id)
) COMMENT '카테고리 테이블';

CREATE TABLE comments
(
  comment_id int      NOT NULL AUTO_INCREMENT,
  board_id   int      NOT NULL,
  content    varchar(255) NOT NULL COMMENT '댓글의 내용',
  reg_date   datetime     NOT NULL COMMENT '댓글의 작성 시간',
  PRIMARY KEY (comment_id)
) COMMENT '자유게시판의 댓글 테이블';

CREATE TABLE files
(
  files_id    int      NOT NULL AUTO_INCREMENT,
  attach_type varchar(55)  NOT NULL COMMENT '첨부 파일의 타입',
  byte_size   int      NOT NULL COMMENT '첨부 파일의 크기',
  uuid_name   varchar(255) NOT NULL COMMENT '첨부 파일의 uuid 이름',
  org_name    varchar(255) NOT NULL COMMENT '첨부 파일의 원래 이름',
  file_dir    varchar(255) NOT NULL COMMENT '첨부 파일의 경로',
  board_id    int      NOT NULL,
  PRIMARY KEY (files_id)
) COMMENT '첨부파일';

ALTER TABLE comments
  ADD CONSTRAINT FK_board_TO_comment
    FOREIGN KEY (board_id)
    REFERENCES board (board_id)
    ON DELETE CASCADE;

ALTER TABLE files
  ADD CONSTRAINT FK_board_TO_files
    FOREIGN KEY (board_id)
    REFERENCES board (board_id)
    ON DELETE CASCADE;

ALTER TABLE board
  ADD CONSTRAINT FK_category_TO_board
    FOREIGN KEY (category_id)
    REFERENCES category (category_id)
    ON DELETE SET DEFAULT;


insert into category(name) VALUES('JAVA');
insert into category(name) VALUES('Javascript');
insert into category(name) values ('Database');
insert into category(name) values('C++');
insert into category(name) values('C');
insert into category(name) values('Assembly');
insert into category(name) values('Kotlin');
insert into category(name) values('Haskell');
insert into category(name) values('Rust');
insert into category(name) values('Python');

insert into 
  board (title, writer, views, reg_date, update_date, category_id, content, password)
values
  (
    'OKKY 12월 세미나 모두를 위한 ML / Data, ML, Service', 
    '박상진', 
    213, 
    '2022-04-08 16:32:00', 
    '2022-04-08 16:32:33', 
    3, 
    'Data, ML, 세미나 테스트 내용입니다.', 
    '1234'
  );

insert into 
  board (title, writer, views, reg_date, update_date, category_id, content, password)
values
(
    'Javascript가 뭔가요?', 
    '오상진', 
    3213, 
    '2022-04-09 16:32:00', 
    '2022-04-09 16:32:33', 
    2, 
    '자바스크립트 테스트 내용입니다.', 
    '1234'
);

  
insert into 
  comments (
    board_id, 
    content, 
    reg_date
  )
values
  (
    1, 
    "댓글 테스트 1", 
    NOW()
  );

  insert into 
  comments (
    board_id, 
    content, 
    reg_date
  )
values
  (
    1, 
    "댓글 테스트 2", 
    NOW()
  );

-- 카테고리, 등록일-시작, 등록일-끝이 모두 있을 때
SELECT
    count(*) as count,
    (select c.name from category c where c.category_id = b.category_id) as category,
    b.title,
    (
        SELECT COUNT(*)
        FROM files f
        WHERE f.board_id = b.board_id
    ) AS file_count,
    b.writer,
    b.views,
    b.reg_date as reg_date,
    b.update_date as update_Date
FROM
    board b
WHERE
    b.reg_date > '2022-04-01 00:00:00' -- 변수
    AND b.update_date < '2022-04-30 00:00:00' -- 변수
    AND b.category_id = (
        SELECT c.category_id
        FROM category c
        WHERE c.name = 'Javascript' -- 변수
    )
    AND (
      b.title LIKE '%Javascript%' -- 변수
      OR
      b.content LIKE '%Javascript%' -- 변수
    )
group by b.board_id
LIMIT 10 -- 변수
OFFSET 0; -- 변수

-- COUNT(*) 까지 포함한 최종버전
SELECT
    (SELECT COUNT(*) FROM board WHERE 1=1 
        AND reg_date >= '2022-01-01'
        AND reg_date <= '2023-12-31'
        AND category_id = (
            SELECT c.category_id
            FROM category c
            WHERE c.name = 'Javascript'
        )
        AND (title LIKE '%Java%'
            OR content LIKE '%Java%')
    ) AS total_count,
    (
        SELECT c.name
        FROM category c
        WHERE b.category_id = c.category_id
    ) AS category,
    (
        SELECT COUNT(*)
        FROM files f
        WHERE f.board_id = b.board_id
    ) AS file_count,
    b.title AS title,
    b.writer AS writer,
    b.views AS views,
    b.reg_date AS reg_date,
    b.update_date AS update_date
FROM
    board b
WHERE
    1=1
    AND b.reg_date >= '2022-01-01'
    AND b.reg_date <= '2023-12-31'
    AND b.category_id = (
        SELECT c.category_id
        FROM category c
        WHERE c.name = 'Javascript'
    )
    AND (b.title LIKE '%Java%'
        OR b.content LIKE '%Java%')
GROUP BY
    b.board_id, b.category_id, b.title, b.writer, b.views, b.reg_date, b.update_date
LIMIT 10
OFFSET 0;



-- 카테고리, 등록일-시작, 등록일-끝이 모두 없을 때

SELECT b.title, b.writer, b.views, b.reg_date, b.update_date
FROM board b
limit 10
offset 0;



-- 전체 페이지의 개수
SELECT ceil(COUNT(*) / 10)
from board;

-- 게시판 보기 시 가져와야할 값들
SELECT
  (
    select c.name
    FROM category c
    WHERE b.category_id = c.category_id
  ) as name,
  b.title,
  b.reg_date,
  b.update_date,
  b.content,
  (
    
  )
FROM board b;



-- 페이지네이션 관찰을 위한 추가 쿼리문 -- 이거 100번 추가하자

insert into 
  board (title, writer, views, reg_date, update_date, category_id, content, password)
values
  (
    'Javascript 테스트', 
    '0woo', 
    123, 
    '2022-04-09 16:32:00', 
    '2022-04-09 16:32:33', 
    2, 
    '자바스크립트 테스트 내용입니다.', 
    '1234'
  );
