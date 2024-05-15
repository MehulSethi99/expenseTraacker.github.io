\connect dfm5l879qhk5f7;
alter default privileges grant all on tables to sqguwdfykfylek;
alter default privileges grant all on sequences to sqguwdfykfyle;

create table exp_tracker_users(
first_name varchar(20) not null,
last_name varchar(20) not null,
avatar_url varchar(50) not null,
email varchar(30) PRIMARY KEY not null,
password text not null,
prefered_currency varchar(5) not null
);


create table exp_tracker_expense (
expense_id INTEGER PRIMARY KEY not null,
title varchar(20) not null,
description varchar(50) not null,
email varchar(30)  not null,
transaction_date bigint not null,
amount numeric(10,2) not null,
month varchar(20) not null,
year INTEGER not null
);

create table exp_tracker_savings (
saving_id INTEGER PRIMARY KEY not null,
title varchar(20) not null,
description varchar(50) not null,
email varchar(30)  not null,
transaction_date bigint not null,
amount numeric(10,2) not null,
month varchar(20) not null,
year INTEGER not null
);

create table exp_tracker_debts (
debt_id INTEGER PRIMARY KEY not null,
title varchar(20) not null,
description varchar(50) not null,
email varchar(30)  not null,
transaction_date bigint not null,
amount numeric(10,2) not null,
month varchar(20) not null,
year INTEGER not null
);

create table exp_tracker_lending (
lending_id INTEGER PRIMARY KEY not null,
title varchar(20) not null,
description varchar(50) not null,
email varchar(30)  not null,
transaction_date bigint not null,
amount numeric(10,2) not null,
month varchar(20) not null,
year INTEGER not null
);

alter table exp_tracker_expense add constraint exp_user_fk
foreign key (email) references exp_tracker_users(email);

alter table exp_tracker_savings add constraint save_user_fk
foreign key (email) references exp_tracker_users(email);

alter table exp_tracker_debts add constraint debt_user_fk
foreign key (email) references exp_tracker_users(email);

alter table exp_tracker_lending add constraint lend_user_fk
foreign key (email) references exp_tracker_users(email);

alter table exp_tracker_users alter COLUMN avatar_url type character varying(1000);

alter table exp_tracker_expense alter COLUMN expense_id  type character varying(1000);
alter table exp_tracker_expense alter COLUMN description type character varying(1000);

alter table exp_tracker_savings alter COLUMN saving_id  type character varying(1000);
alter table exp_tracker_savings alter COLUMN description type character varying(1000);

alter table exp_tracker_lending alter COLUMN lending_id  type character varying(1000);
alter table exp_tracker_lending alter COLUMN description type character varying(1000);

alter table exp_tracker_debts alter COLUMN debt_id  type character varying(1000);
alter table exp_tracker_debts alter COLUMN description type character varying(1000);


create table otp_service(email varchar(255), otpexpirytime varchar(20), otp varchar(10));

alter table exp_tracker_users add COLUMN is_valid  varchar(5);


ALTER TABLE exp_tracker_users ADD COLUMN googleauthkey varchar(50);

