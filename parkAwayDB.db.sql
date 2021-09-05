BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Grad" (
	"grad_id"	INTEGER NOT NULL,
	"naziv"	TEXT NOT NULL,
	PRIMARY KEY("grad_id")
);
CREATE TABLE IF NOT EXISTS "Slike" (
	"id"	INTEGER,
	"slika"	TEXT,
	"parking_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("parking_id") REFERENCES "Parking"("parking_id")
);
CREATE TABLE IF NOT EXISTS "Lokacja" (
	"lokacija_id"	INTEGER NOT NULL,
	"grad_id"	INTEGER NOT NULL,
	"naziv_ulice"	TEXT NOT NULL,
	PRIMARY KEY("lokacija_id"),
	FOREIGN KEY("grad_id") REFERENCES "Grad"("grad_id")
);
CREATE TABLE IF NOT EXISTS "Racun" (
	"racun_id"	INTEGER NOT NULL,
	"vozilo_id"	INTEGER NOT NULL,
	"parking_mjesto_id"	INTEGER NOT NULL,
	"placeno"	INTEGER NOT NULL,
	"vrijeme_prijave"	DATE,
	"vrijeme_odjave"	INTEGER,
	PRIMARY KEY("racun_id"),
	FOREIGN KEY("vozilo_id") REFERENCES "Vozilo"("vozilo_id")
);
CREATE TABLE IF NOT EXISTS "Kartica" (
	"id"	INTEGER,
	"vlasnik"	TEXT,
	"tip"	INTEGER,
	"broj_kartice"	INTEGER,
	"godina_isticanja"	INTEGER,
	"mjesec_isticanja"	INTEGER,
	"cwc"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Parking_mjesto" (
	"parking_mjesto_id"	INTEGER NOT NULL,
	"parking_id"	INTEGER NOT NULL,
	"broj_parking_mjesta"	TEXT NOT NULL,
	"mjesto_za_invalide"	INTEGER NOT NULL,
	"vozilo_id"	INTEGER DEFAULT 0,
	PRIMARY KEY("parking_mjesto_id"),
	FOREIGN KEY("parking_id") REFERENCES "Parking"("parking_id"),
	FOREIGN KEY("vozilo_id") REFERENCES "Vozilo"("vozilo_id")
);
CREATE TABLE IF NOT EXISTS "Parking" (
	"parking_id"	INTEGER NOT NULL,
	"naziv"	TEXT NOT NULL,
	"lokacija_id"	INTEGER NOT NULL,
	"cijena"	INTEGER NOT NULL,
	"pocetak_radnog_vremena"	TEXT NOT NULL,
	"kraj_radnog_vremena"	TEXT NOT NULL,
	"stalni_parking"	INTEGER NOT NULL,
	"ocjena"	INTEGER NOT NULL,
	"opis"	TEXT,
	PRIMARY KEY("parking_id"),
	FOREIGN KEY("lokacija_id") REFERENCES "Lokacja"("lokacija_id")
);
CREATE TABLE IF NOT EXISTS "Korisnik" (
	"korisnik_id"	INTEGER NOT NULL,
	"ime"	TEXT NOT NULL,
	"prezime"	TEXT NOT NULL,
	"broj_telefona"	TEXT NOT NULL UNIQUE,
	"lokacja_id"	INTEGER NOT NULL,
	"kartica_id"	INTEGER NOT NULL,
	"email"	TEXT,
	"lozinka"	TEXT,
	PRIMARY KEY("korisnik_id"),
	FOREIGN KEY("lokacja_id") REFERENCES "Lokacja"("lokacija_id")
);
CREATE TABLE IF NOT EXISTS "Vozilo" (
	"vozilo_id"	INTEGER NOT NULL,
	"registracijske_oznake"	TEXT NOT NULL UNIQUE,
	"model"	INTEGER NOT NULL,
	"korisnik_id"	INTEGER NOT NULL,
	"sasija"	TEXT,
	PRIMARY KEY("vozilo_id"),
	FOREIGN KEY("korisnik_id") REFERENCES "Korisnik"("korisnik_id")
);
INSERT INTO "Grad" VALUES (1,'Sarajevo');
INSERT INTO "Grad" VALUES (2,'Mostar');
INSERT INTO "Grad" VALUES (3,'Tuzla');
INSERT INTO "Grad" VALUES (4,'Visoko');
INSERT INTO "Grad" VALUES (5,'Zenica');
INSERT INTO "Slike" VALUES (1,'/img/parking1.jpg',1);
INSERT INTO "Slike" VALUES (2,'/img/parking2.jpg',2);
INSERT INTO "Slike" VALUES (3,'/img/parking3.jpg',3);
INSERT INTO "Lokacja" VALUES (1,1,'Zmaja od Bosne');
INSERT INTO "Lokacja" VALUES (2,2,'Hrvatskih branitelja');
INSERT INTO "Lokacja" VALUES (3,3,'Kulina Bana');
INSERT INTO "Lokacja" VALUES (4,4,'Hadzijina voda');
INSERT INTO "Lokacja" VALUES (5,5,'Musala');
INSERT INTO "Lokacja" VALUES (6,1,'Pionirska');
INSERT INTO "Lokacja" VALUES (7,4,'Rosulje');
INSERT INTO "Lokacja" VALUES (8,3,'Ulica 8');
INSERT INTO "Lokacja" VALUES (9,1,'Ulica 10');
INSERT INTO "Lokacja" VALUES (10,1,'SCC');
INSERT INTO "Lokacja" VALUES (11,3,'afea');
INSERT INTO "Racun" VALUES (1,1,3,0,'2021-08-14T20:00:00',NULL);
INSERT INTO "Racun" VALUES (2,2,4,1,'2021-08-14T21:00:00',NULL);
INSERT INTO "Racun" VALUES (3,3,1,0,'2021-08-14T22:00:00',NULL);
INSERT INTO "Racun" VALUES (4,4,2,0,'2021-08-14T22:30:00',NULL);
INSERT INTO "Racun" VALUES (5,5,3,0,'2021-08-14T22:40:00',NULL);
INSERT INTO "Racun" VALUES (6,6,1,0,'2021-08-14T22:55:00',NULL);
INSERT INTO "Racun" VALUES (7,7,2,0,'2021-08-14T23:00:00',NULL);
INSERT INTO "Racun" VALUES (8,8,3,0,'2021-08-14T23:10:00',NULL);
INSERT INTO "Racun" VALUES (9,9,1,0,'2021-08-14T23:15:00',NULL);
INSERT INTO "Racun" VALUES (10,10,4,0,'2021-08-14T23:35:00',NULL);
INSERT INTO "Kartica" VALUES (0,'sd',1,'sds',2023,5,3343);
INSERT INTO "Kartica" VALUES (1,'fae',1,'fea',2023,3,32);
INSERT INTO "Parking_mjesto" VALUES (1,1,'1',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (2,1,'2',0,1);
INSERT INTO "Parking_mjesto" VALUES (3,1,'3',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (4,2,'1',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (5,2,'2',0,2);
INSERT INTO "Parking_mjesto" VALUES (6,2,'3',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (7,2,'4',0,5);
INSERT INTO "Parking_mjesto" VALUES (8,1,'4',0,6);
INSERT INTO "Parking_mjesto" VALUES (9,2,'5',1,NULL);
INSERT INTO "Parking" VALUES (1,'Sarajevo Vilsonovo',1,2,'06:00:00','23:30:00',0,4,NULL);
INSERT INTO "Parking" VALUES (2,'Verso',2,5,'00:00:00','00:00:00',1,5,'Nadkrovljena parkng garaza, 063 557 755');
INSERT INTO "Parking" VALUES (3,'SCC',10,2,'00:00:00','00:00:00',1,5,'Parking pored SCC-a');
INSERT INTO "Korisnik" VALUES (1,'Mujo','Mujic','062-123-123',1,'1111-1111-2222-2222','mmujic@gmail.com','mujo123');
INSERT INTO "Korisnik" VALUES (2,'Suljo','Suljic','062-234-234',3,'1234-2345-1234-4567','ssuljic@gmail.com','suljo123');
INSERT INTO "Korisnik" VALUES (3,'Pero','Peric','061-122-221',2,'1223-2345-1234-4345','pperic@gmail.com','pero123');
INSERT INTO "Korisnik" VALUES (4,'Musa','Pusa','066-666-666',4,'2222-2222-1111-1111','musa.pusa@gmail.com','musa123');
INSERT INTO "Korisnik" VALUES (5,'Mirza','Mirzic','060-060-060',5,'5555-5555-5555-5555','mirza@gmail.com','mirza123');
INSERT INTO "Korisnik" VALUES (6,'Zvonko','Zvonic','033-888-777',6,'6666-6666-6666-6666','zvonko@Wgmail.com','zvonko123');
INSERT INTO "Korisnik" VALUES (7,'Emin','Eminic','062-061-060',7,'1234-4567-7890-1234','emin@gmail.com','emin123');
INSERT INTO "Korisnik" VALUES (8,'Sulejman','Suljic','055-555-555',3,'4564-4565-4564-4564','sulejman@gmail.com','sulejman123');
INSERT INTO "Korisnik" VALUES (9,'Meho','Mehic','061-061-061',8,'4444-5555-4444-3333','meho@gmail.com','meho123');
INSERT INTO "Korisnik" VALUES (10,'Sale','Salic','066-555-444',9,'6666-6666-5555-1111','sale@gmail.com','sale123');
INSERT INTO "Korisnik" VALUES (11,'1','1','111-111-111',1,'123-123-123-123-1234','1','1');
INSERT INTO "Korisnik" VALUES (12,'dsa','deaw','cfe',11,1,'fes','fff');
INSERT INTO "Vozilo" VALUES (1,'K63-M-058','Volkswagen Golf 5',1,NULL);
INSERT INTO "Vozilo" VALUES (2,'A12-A-345','Mercedes-Benz C220',2,NULL);
INSERT INTO "Vozilo" VALUES (3,'E67-0-143','Skoda oktavia ',3,NULL);
INSERT INTO "Vozilo" VALUES (4,'K21-J-825','Opel Astra',1,NULL);
INSERT INTO "Vozilo" VALUES (5,'X00-X-000','Renault Megane',4,NULL);
INSERT INTO "Vozilo" VALUES (6,'K14-M-521','Volkswagen Golf 7',5,NULL);
INSERT INTO "Vozilo" VALUES (7,'G63-O-000','Mercedes G class',6,NULL);
INSERT INTO "Vozilo" VALUES (8,'M54-A-555','Peugeot 206',7,NULL);
INSERT INTO "Vozilo" VALUES (9,'A42-A-999','Mercedes A class',8,NULL);
INSERT INTO "Vozilo" VALUES (10,'A92-M-843','Kia Sportage',9,NULL);
INSERT INTO "Vozilo" VALUES (11,'A32-B-123','Volkswagen Passat B8',10,NULL);
COMMIT;
