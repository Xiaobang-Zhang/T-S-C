CREATE TABLE "authorization"
(
	user_id bigint NOT NULL,
	user_password varchar(50) NOT NULL
)
;

CREATE TABLE codetable
(
	id bigserial NOT NULL,
	category text NOT NULL,
	name text NOT NULL,
	display_value text NOT NULL,
	is_active boolean   DEFAULT true
)
;

CREATE TABLE comment
(
	id bigserial NOT NULL,
	content text NOT NULL,
	owner_id bigint NOT NULL,
	topic_id bigint NOT NULL,
	super_comment_id bigint,
	likes_count integer   DEFAULT 0,
	created_dt timestamp without time zone NOT NULL,
	last_update_dt timestamp without time zone NOT NULL,
	is_active boolean   DEFAULT true
)
;

CREATE TABLE course
(
	id bigserial NOT NULL,
	course_number bigint NOT NULL,
	name text NOT NULL,
	description text,
	created_dt timestamp without time zone NOT NULL,
	last_update_dt timestamp without time zone NOT NULL,
	is_active boolean   DEFAULT true
)
;

CREATE TABLE student_course_xref
(
	id bigserial NOT NULL,
	student_id bigint NOT NULL,
	teacher_course_xref_id bigint NOT NULL,
	score real
)
;

CREATE TABLE teacher_course_xref
(
	id bigserial NOT NULL,
	teacher_id bigint NOT NULL,
	course_id bigint NOT NULL,
	is_active boolean NOT NULL   DEFAULT true
)
;

CREATE TABLE topic
(
	id bigserial NOT NULL,
	owner_id bigint NOT NULL,
	course_id bigint NOT NULL,
	category_id bigint NOT NULL,
	title text NOT NULL,
	content text NOT NULL,
	likes_count integer   DEFAULT 0,
	created_dt timestamp without time zone NOT NULL,
	last_update_dt timestamp without time zone NOT NULL,
	is_active boolean   DEFAULT true
)
;

CREATE TABLE "user"
(
	id bigserial NOT NULL,
	account_id bigint NOT NULL,
	name text NOT NULL,
	user_role bigint NOT NULL,
	created_dt timestamp without time zone NOT NULL,
	last_left_dt timestamp without time zone,
	is_active boolean   DEFAULT true
)
;

CREATE TABLE user_detail
(
	id bigserial NOT NULL,
	user_id bigint NOT NULL,
	nickname text,
	picture_path text,
	birthday timestamp without time zone,
	grade integer,
	signature text,
	last_update_dt timestamp without time zone NOT NULL
)
;

ALTER TABLE "authorization" ADD CONSTRAINT PK_authorization
	PRIMARY KEY (user_id)
;

ALTER TABLE codetable ADD CONSTRAINT PK_codetable
	PRIMARY KEY (id)
;

ALTER TABLE codetable 
  ADD CONSTRAINT UQ_codetable_name UNIQUE (name)
;

ALTER TABLE codetable 
  ADD CONSTRAINT UQ_codetable_category_name UNIQUE (name,category)
;

ALTER TABLE comment ADD CONSTRAINT PK_comment
	PRIMARY KEY (id)
;

ALTER TABLE course ADD CONSTRAINT PK_course
	PRIMARY KEY (id)
;

ALTER TABLE course 
  ADD CONSTRAINT UQ_course_course_number UNIQUE (course_number)
;

ALTER TABLE student_course_xref ADD CONSTRAINT PK_student
	PRIMARY KEY (id)
;

ALTER TABLE student_course_xref 
  ADD CONSTRAINT UQ_STUDENT_COURSE UNIQUE (student_id,teacher_course_xref_id)
;

ALTER TABLE teacher_course_xref ADD CONSTRAINT PK_teacher_course_xref
	PRIMARY KEY (id)
;

ALTER TABLE topic ADD CONSTRAINT PK_topic
	PRIMARY KEY (id)
;

ALTER TABLE "user" ADD CONSTRAINT PK_user
	PRIMARY KEY (id)
;

ALTER TABLE "user" 
  ADD CONSTRAINT UQ_user_account_id UNIQUE (account_id)
;

ALTER TABLE user_detail ADD CONSTRAINT PK_user_detail
	PRIMARY KEY (id)
;

ALTER TABLE "authorization" ADD CONSTRAINT FK_authorization_user_02
	FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE comment ADD CONSTRAINT FK_comment_topic
	FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE comment ADD CONSTRAINT FK_comment_user
	FOREIGN KEY (owner_id) REFERENCES "user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE student_course_xref ADD CONSTRAINT FK_student_user
	FOREIGN KEY (student_id) REFERENCES "user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE student_course_xref ADD CONSTRAINT FK_student_course_xref_teacher_course_xref
	FOREIGN KEY (teacher_course_xref_id) REFERENCES teacher_course_xref (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE teacher_course_xref ADD CONSTRAINT FK_teacher_course_xref_course
	FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE teacher_course_xref ADD CONSTRAINT FK_teacher_course_xref_user
	FOREIGN KEY (teacher_id) REFERENCES "user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE topic ADD CONSTRAINT FK_topic_codetable
	FOREIGN KEY (category_id) REFERENCES codetable (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE topic ADD CONSTRAINT FK_topic_course
	FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE topic ADD CONSTRAINT FK_topic_user
	FOREIGN KEY (owner_id) REFERENCES "user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE "user" ADD CONSTRAINT FK_user_codetable
	FOREIGN KEY (user_role) REFERENCES codetable (id) ON DELETE Cascade ON UPDATE Cascade
;

ALTER TABLE user_detail ADD CONSTRAINT FK_user_detail_user
	FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE Cascade ON UPDATE Cascade
;
