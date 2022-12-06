# BDAV


Class names:
wc: WC_Runner

matrix: MatrixMultiply
union: Union
intersection: Intersection
avglength:
groupsum:
word cooccurence: [Pairs & Stripes] [dynamic]

# WORD COUNT FREQUENCY SORT:
https://gist.github.com/pingsutw/df2fbdb050f903dc4bedbb38a8c037eb

# RELATION ALGEBRA OF WORD COUNT [UNION, DIFFERENCE, INTERSECTION, GROUPSUM]
https://github.com/wosiu/Hadoop-map-reduce-relational-algebra/tree/master/src/main/java

# For Word Count / General:
EXPORT THE JAR USING IDE [NETBEANS & ECIPPLSE]
Or 
USING COMMAND:
javac MyApp.java
jar -cf myJar.jar MyApp.clas

hadoop jar path_of_jar name_of_class /dir/output name_of_file_for_output
hadoop  jar /home/cloudera/Desktop/wc.jar WordCount /p2results results 

For word cooccurence:

PAIRS:
hadoop jar /home/cloudera/Desktop/Cooccurrencejar.jar Cooccurrence pairs /premmc/wordcount.txt

STRIPES:
hadoop jar /home/cloudera/Desktop/Cooccurrencejar.jar Cooccurrence stripes /premmc/wordcount.txt


# HIVE:
A) SHOW ALL DATABASES:
Command:
show databases;

B) CREATE TABLE:
Command:
create table employee_narender(id int, name string, city string, dept string, salary
int, domain string);

C) DESCRIBE TABLE:
Command:
describe employee_narender;

D) COMPLETE DESCRIPTION OF TABLE:
Command:
describe formatted employee_narender;

E) INSERT INTO TABLE VALUES:
insert into table employee_narender values (1, "Narender Keswani", "Ulhasnagar",
"Backend Development", 60000, "IT");
insert into table employee_narender values (2, "Hassan Haque", "Mahalaxmi",
"Graphics Development", 50000, "Desgning");
insert into table employee_narender values (3, "Ritesh Yadav", "Andheri", "Web
Development", 100000, "IT");
insert into table employee_narender values (4, "Ronak Karia", "Thane",
"Wordpress Development", 20000, "IT");
insert into table employee_narender values (5, "Neelam Khatri", "Churchgate",
"Human Resource", 40000, "HR");
insert into table employee_narender values (6,"Jayesh Mehtha", "Ajmer",
"Accounts", 30000, "Book Keeping");

F) SELECT / PRINT HR TABLE:
Command:
select * from employee_narender;

G) SUM:
Command:
select sum(salary) from employee_narender;

H) MAX:
Command:
select max(salary) from employee_narender;

I) COUNT:
Command:
select count(*) from employee_narender;

J) ORDER BY DESC:
Command:
select * from employee_narender order by id desc;
select * from employee_narender order by name desc;

K) CREATE SCHEMA: [ALTERNATIVE OF CREATION OF DATABASE]:
Command:
create schema userdb_narender;

L) CREATE EXTERNAL TABLE:
Command:
CREATE EXTERNAL TABLE emp_narender (id int, name string, dept string, yoj int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;

M) READING FILE FROM LOCAL MACHINE AND WRITING INTO THE TABLE:
NOTE:
INSERT OVERWRITE: WILL DELETE OLD RECORDS AND PUTS NEW RECORDS
WHEREAS, INSERT INTO WILL APPEND THE RECORDS
Command:
LOAD DATA LOCAL INPATH '/home/cloudera/Desktop/emp.csv' OVERWRITE INTO
TABLE emp_narender;
select * from emp_narender;
JOINS:
CREATING TABLES FOR JOINS:
CUSTOMER:
COMMAND / QUERY:
CREATE TABLE customers(
ID int,
Name string,
Age int,
Address string,
Salary int
)
COMMENT 'This is customers table stored as textfile'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

T1.txt:
1,Ross,32,Ahmedabad,2000
2,Rachel,25,Delhi,1500
3,Chandler,23,Kota,2000
4,Narender,23,Mumbai,6500
5,Mike,27,Bhopal,8500
6,Phoebe,22,MP,4500
7,Joey,24,Indore,10000
8,Amardeep,22,UP,8000
9,Ramesh,56,Gujarat,80000
10,Shammas,25,Akola,9000

READ FROM DATA FROM LOCAL MACHINE AND PUT IN THE TABLE:
LOAD DATA LOCAL INPATH '/home/cloudera/Desktop/t1.txt' OVERWRITE INTO
TABLE customers;
select * from customers;
describe customers;

ORDER:
COMMAND / QUERY:
CREATE TABLE orders(
OID int,
order_name string,
Customer_ID int,
Amount int)
COMMENT 'This is orders table stored as textfile'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

T2.txt:
102,laptop,3,3000
100,tv,3,1500
101,mobile,2,1560
103,clothes,5,2060
104,headphone,4,3050

LOAD DATA LOCAL INPATH '/home/cloudera/Desktop/t2.txt' OVERWRITE INTO
TABLE orders;
select * from orders;

N) INNER JOIN:
Command:
SELECT c.ID, c.NAME, c.AGE, o.AMOUNT FROM CUSTOMERS c JOIN ORDERS o
ON (c.ID = o.CUSTOMER_ID);

O) LEFT JOIN:
Command:
SELECT c.ID, c.NAME, o.AMOUNT, o.order_name FROM CUSTOMERS c LEFT
OUTER JOIN ORDERS o ON (c.ID = o.CUSTOMER_ID);

P) RIGHT JOIN:
Command:
SELECT c.ID, c.NAME, o.AMOUNT, o.order_name FROM CUSTOMERS c RIGHT
OUTER JOIN ORDERS o ON (c.ID = o.CUSTOMER_ID);

Q) FULL OUTER JOIN:
Command:
SELECT c.ID, c.NAME, o.AMOUNT, o.order_name FROM CUSTOMERS c FULL
OUTER JOIN ORDERS o ON (c.ID = o.CUSTOMER_ID);

R) CREATE VIEW:
Command:
create view emp_40000 AS select * from employee_narender where
salary>=40000;

S) DISPLAY VIEW:
Command:
select * from emp_40000;

T) DROP VIEW:
Command:
drop view emp_40000;

U) CREATE INDEX:
Command:
create index index_salary on table employee_narender(salary) As
'org.apache.hadoop.hive.ql.index.compact.CompactIndexHandler' WITH
DEFERRED REBUILD;

V) VIEW INDEX:
Command:
show formatted index on employee_narender;

W) DROP INDEX:
Command:
drop index index_salary ON employee_narender;
PARTITION:
CREATING STUDENT TABLE FOR PARTITION EXAMPLE:
create table student (id int, name string, age int, institute string) partitioned by
(course string) row format delimited fields terminated by ',';

X) CREATING PARTITION:
Commands:
load data local inpath '/home/cloudera/Desktop/student.txt' into table student
partition(course= "java");
load data local inpath '/home/cloudera/Desktop/student2.txt' into table student
partition(course= "hadoop");
select * from student;

Y) DISPLAY BY PARTITION COURSE:
Commands:
select * from student where course="java";
select * from student where course="hadoop";

Z) DROP PARTITION:
Commands:
ALTER TABLE student DROP PARTITION (course= "hadoop");
Alternative (if exists):
ALTER TABLE student DROP [IF EXISTS] PARTITION (course= "hadoop");

AA) RENAME TABLE:
Command:
alter table student rename to student_narender;

BB) INSERT DATA INTO TABLE USING HDFS [HADOOP]:
Command:
CREATE TABLE customers_narender(
ID int,
Name string,
Age int,
Address string,
Salary int )
COMMENT 'This is customers table stored as textfile'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

cust.txt:
1,Ross,32,Ahmedabad,2000
2,Rachel,25,Delhi,1500
3,Chandler,23,Kota,2000
4,Narender,23,Mumbai,6500
5,Mike,27,Bhopal,8500
hdfs dfs -mkdir /hivefiles
hdfs dfs -put /home/narender/cust.txt /hivefiles
hdfs dfs -ls /hivefiles
load data inpath '/hivefiles/cust.txt' into table customers_narender;

Select * from customers_narender;

CC) HCATALOG CREATE TABLE:
Command:
hcat -e "CREATE TABLE IF NOT EXISTS employee_narender ( eid int, name String,
salary String, destination String) COMMENT 'Employee details' ROW FORMAT
DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' STORED AS
TEXTFILE;"

DD) HCATALOG SHOW TABLES:
Command:
hcat -e "show tables";

EE) HCATALOG DESCRIBE TABLE:
Command:
hcat -e "describe employee_narender";

FF)HCATALOG DROP TABLE:
Command:
hcat -e "drop table employee_narender";
hcat -e "show tables";
