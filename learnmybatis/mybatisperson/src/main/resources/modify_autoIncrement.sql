-- truncate table person;
update table person set id= (select max(id) from person order by id)+1;