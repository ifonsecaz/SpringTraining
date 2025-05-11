create database taskmanager;

use taskmanager;

create table task(
	task_id int primary key,
    title varchar(100) unique,
    description varchar(200),
    status varchar(50),
    createdDate date
);

select * from task;