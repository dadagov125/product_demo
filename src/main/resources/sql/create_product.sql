
CREATE TABLE IF NOT EXISTS product
(
	code varchar(32) not null constraint product_pk primary key,
	name varchar not null,
	price decimal default 0 not null,
	article varchar not null,
	produced timestamp not null,
	count int default 0 not null
);
