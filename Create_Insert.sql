-- sequence
CREATE SEQUENCE user_id_seq START 1000;
CREATE SEQUENCE animal_id_seq START 1;
CREATE SEQUENCE ad_no_seq START 1;
-- Tables
CREATE TABLE animals(
	id int DEFAULT nextval('animal_id_seq'),
	name varchar(20) not null,
	kind varchar(20),
	age int not null,
	sex char(1) not null,
	CONSTRAINT PK_Animal PRIMARY KEY (id)
);
CREATE TABLE users(
	id int DEFAULT nextval('user_id_seq'),
	user_name varchar(20) not null,
	phone_number char(10) not null,
	password char(6) DEFAULT '123456',
	address varchar(20) not null,
	CONSTRAINT PK_Customer PRIMARY KEY (id)
);
CREATE TABLE owns(
	user_id int not null,
	animal_id int not null,
	ownage_date DATE not null,
	foreign key (user_id) references users(id) ON DELETE CASCADE,
	foreign key (animal_id) references animals(id) ON DELETE CASCADE
);
ALTER TABLE owns ADD CONSTRAINT unique_animal_user_id UNIQUE (user_id, animal_id);
CREATE TABLE ads(
	ad_no int DEFAULT nextval('ad_no_seq'),
	owner_id int not null,
	animal_id int not null,
	kind varchar(20) not null,
	price int not null,
	address varchar(20) not null,
	ad_date DATE not null,
	foreign key (animal_id) references animals(id) ON DELETE CASCADE,
	foreign key (owner_id) references users(id) ON DELETE CASCADE,
	CONSTRAINT PK_Ads PRIMARY KEY (ad_no),
	CONSTRAINT price_ck CHECK  (price >=50)
);
CREATE TABLE applications(
	applicent_id int not null,
	ad_no int not null,
	foreign key (applicent_id) references users(id) ON DELETE CASCADE
);
ALTER TABLE applications ADD CONSTRAINT unique_ad_no_id UNIQUE (ad_no, applicent_id);
CREATE TABLE items(
	id int not null,
	name varchar(30) not null,
	price int not null,
	CONSTRAINT PK_Item PRIMARY KEY (id)
);
CREATE TABLE transactions(
	user_id int not null,
	item_id int not null,
	count int not null,
	total_price int not null,
	ts_date DATE not null,
	foreign key (user_id) references users(id) ON DELETE CASCADE,
	foreign key (item_id) references items(id) ON DELETE CASCADE
);

-- Inserts
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Mavis', 5, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Limon', 1, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Geveze', 3, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Dilli', 4, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Cilgin', 7, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kus', 'Afacan', 2, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Incir', 12,'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Komur', 3, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Ringo', 10, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Azman', 8, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Tuylu', 9, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kopek', 'Korsan', 5, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Sarman', 11, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Simit', 8, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Pamuk', 6, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Tekir', 4, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Yumus', 2, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kedi', 'Kara', 7, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Tombik', 45, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Kaplis', 21, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Hizli', 30, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Kurbi', 10, 'F');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Kambur', 13, 'M');
INSERT INTO animals(kind,name,age,sex) VALUES ('Kaplumbaga', 'Atılgan', 13, 'M');
-- users tablosuna eklenecekler
INSERT INTO users(user_name, phone_number, address) VALUES ('Ceren', '5555555555', 'Bursa');
INSERT INTO users(user_name,phone_number,address) VALUES ('Ahmet Mahir', '5555555556', 'Trabzon');
INSERT INTO users(user_name,phone_number,address) VALUES ('Yavuz', '5555555557', 'Istanbul');
INSERT INTO users(user_name,phone_number,address) VALUES ('Ahmed Asim', '5555555558', 'Istanbul');
INSERT INTO users(user_name,phone_number,address) VALUES ('Osman Berkay', '5555555559', 'Ankara');
INSERT INTO users(user_name,phone_number,address) VALUES ('Mert', '5555555560', 'Ankara');
INSERT INTO users(user_name,phone_number,address) VALUES ('Elif', '5555555561', 'Eskisehir');
INSERT INTO users(user_name,phone_number,address) VALUES ('Kemal', '5555555562', 'Trabzon');
INSERT INTO users(user_name,phone_number,address) VALUES ('Gamze', '5555555563', 'Eskisehir');
INSERT INTO users(user_name,phone_number,address) VALUES ('Betül', '5555555564', 'Adana');
INSERT INTO users(user_name,phone_number,address) VALUES ('Elif', '5555555565', 'Adana');
INSERT INTO users(user_name, phone_number, address) VALUES ('Ayse Nur', '5555555566', 'Istanbul');
INSERT INTO users(user_name, phone_number, address) VALUES ('Mehmet Can', '5555555567', 'Ankara');
INSERT INTO users(user_name, phone_number, address) VALUES ('Zeynep', '5555555568', 'Izmir');
INSERT INTO users(user_name, phone_number, address) VALUES ('Kaan', '5555555569', 'Antalya');
INSERT INTO users(user_name, phone_number, address) VALUES ('Eren', '5555555570', 'Bursa');

-- owns tablosuna eklenecekler
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1000, 1, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1011, 2, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1014, 7, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1014, 10, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1015, 12, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1012, 13, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1000, 15, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1011, 20, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1015, 22, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1012, 24, '2023-12-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1010, 16, '2015-06-01');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1010, 5, '2015-07-15');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1005, 9, '2018-02-28');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1002, 14, '2010-05-31');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1001, 17, '2017-12-03');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1009, 19, '2021-11-14');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1008, 3, '2023-04-19');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1004, 4, '2023-06-24');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1006, 21, '2020-09-25');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1002, 11, '2023-06-13');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1003, 8, '2019-05-09');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1008, 18, '2020-11-02');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1009, 6, '2017-02-18');
INSERT INTO owns(user_id,animal_id,ownage_date) VALUES (1004, 23, '2019-04-22');
-- adds tablosuna eklenecekler
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1014, 7, 'Kopek',150, 'Turkiye', '2023-12-02');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1014, 10, 'Kopek',175, 'Turkiye', '2023-12-02');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1015, 12, 'Kopek',350, 'Turkiye', '2023-12-02');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1015, 22, 'Kaplumbaga',50, 'Turkiye', '2023-12-02');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1012, 24, 'Kaplumbaga',55, 'Turkiye', '2023-12-02');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1004, 4, 'Kus',50, 'Ankara', '2023-11-16');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1007, 3, 'Kus',60, 'Trabzon', '2023-11-17');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1002, 14, 'Kedi',500, 'Istanbul', '2023-11-18');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1007, 3, 'Kus',70, 'Trabzon', '2023-11-19');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1009, 19, 'Kaplumbaga',70, 'Adana', '2023-11-16');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1008, 18, 'Kedi',450, 'Eskisehir', '2023-11-17');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1005, 9, 'Kopek',1000, 'Ankara', '2023-11-18');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1010,16, 'Kedi',500, 'Adana', '2023-11-19');
INSERT INTO ads(owner_id,animal_id,kind,price, address,ad_date) VALUES (1004, 23, 'Kaplumbaga',80, 'Ankara', '2023-11-16');
INSERT INTO ads(owner_id,animal_id,kind,price,address,ad_date) VALUES (1001, 17, 'Kedi',600, 'Trabzon', '2023-11-17');

-- applications tablosuna eklenecekler
INSERT INTO applications VALUES (1001,7);
INSERT INTO applications VALUES (1004,3);
INSERT INTO applications VALUES (1009,4);
INSERT INTO applications VALUES (1003,4);
INSERT INTO applications VALUES (1000,8);
INSERT INTO applications VALUES (1005,5);
INSERT INTO applications VALUES (1007,1);
INSERT INTO applications VALUES (1003,5);
INSERT INTO applications VALUES (1008,9);
INSERT INTO applications VALUES (1002,2);

INSERT INTO items(id,name,price) VALUES (1, 'Kedi Mamasi', 20);
INSERT INTO items(id,name,price) VALUES (2, 'Kopek Oyuncagi', 15);
INSERT INTO items(id,name,price) VALUES (3, 'Kus Kafesi', 30);
INSERT INTO items(id,name,price) VALUES (4, 'Balik Yemi', 10);
INSERT INTO items(id,name,price) VALUES (5, 'Tavsan Kafesi', 25);
INSERT INTO items(id,name,price) VALUES (6, 'Hamster Oyun Tuneli', 12);
INSERT INTO items(id,name,price) VALUES (7, 'Kopek Kuru Mamasi', 35);
INSERT INTO items(id,name,price) VALUES (8, 'Kedi Oyuncagı Seti', 18);
INSERT INTO items(id,name,price) VALUES (9, 'Kus Kumu', 8);
INSERT INTO items(id,name,price) VALUES (10, 'Kopek Tasmasi', 22);
INSERT INTO items(id,name,price) VALUES (11, 'Akvaryum Isitici', 28);
INSERT INTO items(id,name,price) VALUES (12, 'Kedi Tirmalama Tahtasi', 15);
INSERT INTO items(id,name,price) VALUES (13, 'Kus Kafesi Oyun Seti', 40);
INSERT INTO items(id,name,price) VALUES (14, 'Kopek Yatagi', 30);
INSERT INTO items(id,name,price) VALUES (15, 'Balik Akvaryumu', 50);
INSERT INTO items(id,name,price) VALUES (16, 'Kedi Kumu', 12);
INSERT INTO items(id,name,price) VALUES (17, 'Kopek Kemigi', 25);
INSERT INTO items(id,name,price) VALUES (18, 'Kus Yuvasi', 18);
INSERT INTO items(id,name,price) VALUES (19, 'Kopek Oyun Topu', 10);
INSERT INTO items(id,name,price) VALUES (20, 'Tavsan Mama Kabi', 8);
-- fiş ekleme
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1001, 1, 2, 40, '2024-01-02');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1002, 3, 1, 30, '2024-01-03');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1001, 5, 3, 75, '2024-01-04');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1003, 2, 2, 30, '2024-01-05');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1002, 4, 1, 10, '2024-01-06');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1003, 6, 2, 24, '2024-01-07');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1001, 8, 1, 18, '2024-01-08');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1002, 10, 4, 88, '2024-01-09');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1003, 12, 3, 45, '2024-01-10');
INSERT INTO transactions (user_id, item_id, count, total_price, ts_date) VALUES (1001, 14, 1, 30, '2024-01-11');

-- functions


CREATE OR REPLACE FUNCTION register_user(
    p_username VARCHAR(255),
    p_address VARCHAR(255),
    p_phone VARCHAR(20),
    p_password VARCHAR(255)
)
RETURNS INTEGER AS $$
DECLARE
    new_user_id INTEGER;
BEGIN
    INSERT INTO users(user_name, address, phone_number, password)
    VALUES (p_username, p_address, p_phone, p_password)
    RETURNING id INTO new_user_id;
    RETURN new_user_id;
END;
$$ LANGUAGE plpgsql;

CREATE TYPE login_info AS(user_id INTEGER,password varchar(255));
CREATE OR REPLACE FUNCTION login_user(
    p_user_id INTEGER,
    p_password VARCHAR(255)
)
RETURNS BOOLEAN AS $$
DECLARE
    user_record login_info;
BEGIN
    SELECT id , password INTO user_record
    FROM users
    WHERE id = p_user_id;


    IF user_record IS NOT NULL AND user_record.password = p_password THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION set_ownage_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.ownage_date := CURRENT_DATE;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_price_by_percentage(p_ad_no INTEGER, p_percentage INTEGER)
RETURNS INTEGER AS $$
DECLARE
    v_current_price INTEGER;
    v_new_price INTEGER;
    c_ads CURSOR FOR SELECT price FROM ads WHERE ad_no = p_ad_no;
BEGIN
    FOR r_ad IN c_ads LOOP
        v_current_price := r_ad.price;
        v_new_price := v_current_price * p_percentage / 100;

        UPDATE ads SET price = v_new_price WHERE ad_no = p_ad_no;
    END LOOP;
    RETURN v_new_price;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_price()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.price > 10000 THEN
        RAISE EXCEPTION 'Trigger! Cant put ad price higher than 10.000!';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_application_count()
RETURNS TRIGGER AS $$
DECLARE
    applicent_id_val INT;
    ad_no_val INT;
    application_count INT;
BEGIN
    applicent_id_val := NEW.applicent_id;

    SELECT COUNT(*)
    INTO application_count
    FROM applications
    WHERE applicent_id = applicent_id_val;

    IF application_count >= 3 THEN
        RAISE EXCEPTION 'Kullanıcının en fazla 3 başvurusu olabilir.';
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- triggers

CREATE TRIGGER owns_set_ownage_date
BEFORE INSERT ON owns
FOR EACH ROW
EXECUTE FUNCTION set_ownage_date();

CREATE TRIGGER check_price_trigger
BEFORE INSERT OR UPDATE ON ads
FOR EACH ROW
EXECUTE FUNCTION check_price();

CREATE TRIGGER before_application_insert
BEFORE INSERT ON applications
FOR EACH ROW
EXECUTE FUNCTION check_application_count();