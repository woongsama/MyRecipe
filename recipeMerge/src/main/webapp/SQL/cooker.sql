-- cooker 계정에 생성할 테이블
-- 분류에 따라 파일 분리해도됨

-- 테스트용 테이블
create table Test(
			name varchar2(20),
			age number
);

select * from test;

select * from tab;
select * from member;
select id,nickname,pwd,state from member;

delete from recipe_re where id='aaaa';
update member set state=1 where id='aaaa';

delete from RECIPE where subject='글1';