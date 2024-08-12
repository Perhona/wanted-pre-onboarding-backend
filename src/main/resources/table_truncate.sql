-- 회사, 사용자 임의 데이터
-- INSERT INTO company(id, name) VALUES (1, '원티드');
-- INSERT INTO company(id, name) VALUES (1, '원티드연구소');
-- INSERT INTO user(id, name) VALUES(1, '김철수');

-- 채용 공고 Truncate
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE hiredhub_api.job_posting;
TRUNCATE TABLE hiredhub_api.application;
SET FOREIGN_KEY_CHECKS = 1;
