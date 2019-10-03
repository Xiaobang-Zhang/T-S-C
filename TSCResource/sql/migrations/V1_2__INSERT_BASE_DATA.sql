-- Insert datas about codetable

-- Data about USER_ROLE
INSERT INTO codetable(category, name, display_value)
VALUES('USER_ROLE', 'ROLE_ADMIN', '管理员');

INSERT INTO codetable(category, name, display_value)
VALUES('USER_ROLE', 'ROLE_TEACHER', '教师');

INSERT INTO codetable(category, name, display_value)
VALUES('USER_ROLE', 'ROLE_STUDENT', '学生');

-- Datas about TOPIC_CATEGORY
INSERT INTO codetable(category, name, display_value)
VALUES('TOPIC_CATEGORY', 'HOMEWORK', '作业');

INSERT INTO codetable(category, name, display_value)
VALUES('TOPIC_CATEGORY', 'QUESTION', '问题');

INSERT INTO codetable(category, name, display_value)
VALUES('TOPIC_CATEGORY', 'GOSSIP', '树洞');