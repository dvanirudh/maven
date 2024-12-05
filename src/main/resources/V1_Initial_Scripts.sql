DROP TABLE IF EXISTS Student;

CREATE TABLE IF NOT EXISTS STUDENT (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birth_date VARCHAR(255),
    subjects VARCHAR(500));


    -- CREATE TABLE IF NOT EXISTS SUBJECTS (
    --     ID INT AUTO_INCREMENT FORIGN KEY,
    --     subject_name VARCHAR(255),
    --     marks LIMIT(100) INT );



--INSERT INTO STUDENT(ID,first_name, last_name, birth_date, subjects) VALUES
--(1,'John', 'Wick', '2023-12-02', '[{"Mathematics": 88, "English": 76, "Science": 56}]'),
--(2,'Jack', 'Raider', '2022-02-12', '[{"Science": 70, "Mathematics": 90}]'),
--(3,'Muhammad', 'Ali', '2024-09-30', '[{"Social Science": 100, "GK": 83, "Hindi": 58}]'),
--(4,'Mick', 'Tyson', '2022-02-05', '[{"Social Science": 54, "Science": 70, "Hindi": 27}]'),
--(5,'Tim', 'Cook', '2020-03-21', '[{"Science": 58, "Social Science": 100, "English": 54}]'),
--(6,'Tony', 'Stark', '2021-10-10', '[{"GK": 36, "Mathematics": 26, "Spanish": 19}]'),
--(7,'Elon', 'Musk', '2024-11-27', '[{"Science": 13, "Geology": 90}]'),
--(8,'Alice', 'Pierce', '2022-07-22', '[{"GK": 75, "History": 98}]'),
--(9,'Sherlock', 'Homes', '1999-02-01', '[{"Mathematics": 100, "Hindi": 100, "Sanskrit": 99}]');
