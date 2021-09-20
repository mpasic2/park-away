BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Racun" (
	"racun_id"	INTEGER NOT NULL,
	"vozilo_id"	INTEGER NOT NULL,
	"parking_mjesto_id"	INTEGER NOT NULL,
	"placeno"	INTEGER NOT NULL,
	"vrijeme_prijave"	TEXT,
	"vrijeme_odjave"	TEXT,
	FOREIGN KEY("vozilo_id") REFERENCES "Vozilo"("vozilo_id"),
	PRIMARY KEY("racun_id")
);
CREATE TABLE IF NOT EXISTS "Vozilo" (
	"vozilo_id"	INTEGER NOT NULL,
	"registracijske_oznake"	TEXT NOT NULL UNIQUE,
	"model"	INTEGER NOT NULL,
	"korisnik_id"	INTEGER NOT NULL,
	"sasija"	TEXT,
	FOREIGN KEY("korisnik_id") REFERENCES "Korisnik"("korisnik_id"),
	PRIMARY KEY("vozilo_id")
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
	FOREIGN KEY("lokacja_id") REFERENCES "Lokacja"("lokacija_id"),
	PRIMARY KEY("korisnik_id")
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
CREATE TABLE IF NOT EXISTS "Parking_mjesto" (
	"parking_mjesto_id"	INTEGER NOT NULL,
	"parking_id"	INTEGER NOT NULL,
	"broj_parking_mjesta"	TEXT NOT NULL,
	"mjesto_za_invalide"	INTEGER NOT NULL,
	"vozilo_id"	INTEGER DEFAULT 0,
	FOREIGN KEY("vozilo_id") REFERENCES "Vozilo"("vozilo_id"),
	FOREIGN KEY("parking_id") REFERENCES "Parking"("parking_id"),
	PRIMARY KEY("parking_mjesto_id")
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
CREATE TABLE IF NOT EXISTS "Lokacja" (
	"lokacija_id"	INTEGER NOT NULL,
	"grad_id"	INTEGER NOT NULL,
	"naziv_ulice"	TEXT NOT NULL,
	FOREIGN KEY("grad_id") REFERENCES "Grad"("grad_id"),
	PRIMARY KEY("lokacija_id")
);
CREATE TABLE IF NOT EXISTS "Slike" (
	"id"	INTEGER,
	"slika"	TEXT,
	"parking_id"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("parking_id") REFERENCES "Parking"("parking_id")
);
CREATE TABLE IF NOT EXISTS "Grad" (
	"grad_id"	INTEGER NOT NULL,
	"naziv"	TEXT NOT NULL,
	PRIMARY KEY("grad_id")
);
INSERT INTO "Racun" VALUES (1,13,6,0,'03:53:50.726729','00:55:57.199644100');
INSERT INTO "Racun" VALUES (2,12,1,1,'00:55:50.933021800','00:55:57.199644100');
INSERT INTO "Racun" VALUES (3,11,4,0,'00:57:33.848696600','00:55:57.199644100');
INSERT INTO "Racun" VALUES (4,10,1,0,'00:58:13.829440500','00:55:57.199644100');
INSERT INTO "Racun" VALUES (5,9,4,1,'01:02:42.537842800','01:04:12.445883500');
INSERT INTO "Racun" VALUES (6,14,4,1,'02:01:03.261621100','02:02:37.794521100');
INSERT INTO "Racun" VALUES (7,14,4,1,'02:08:32.844370700','02:08:44.249649500');
INSERT INTO "Racun" VALUES (8,14,1,1,'12:15:55.839911464','12:17:42.359429193');
INSERT INTO "Racun" VALUES (9,14,4,1,'12:18:16.097908337','12:18:21.804353555');
INSERT INTO "Racun" VALUES (10,14,4,1,'12:30:46.376008567','12:31:36.274848177');
INSERT INTO "Racun" VALUES (11,14,4,1,'12:39:11.358011524','12:39:28.969589849');
INSERT INTO "Racun" VALUES (12,14,4,1,'12:40:23.921139898','12:40:31.017052181');
INSERT INTO "Racun" VALUES (13,14,4,1,'12:42:08.915939411','12:42:28.017392818');
INSERT INTO "Racun" VALUES (14,14,4,1,'12:46:34.360948356','12:46:41.923605046');
INSERT INTO "Racun" VALUES (15,14,4,0,'12:46:48.362395088',NULL);
INSERT INTO "Racun" VALUES (16,15,6,0,'12:48:28.982464949',NULL);
INSERT INTO "Racun" VALUES (17,14,6,1,'12:50:36.215480159','13:33:20.357984302');
INSERT INTO "Racun" VALUES (18,14,4,0,'13:45:48.624575273',NULL);
INSERT INTO "Racun" VALUES (19,16,3,1,'14:08:22.906806906','14:14:19.402579425');
INSERT INTO "Racun" VALUES (20,14,1,1,'14:18:13.678463681','14:18:33.418378094');
INSERT INTO "Racun" VALUES (21,14,4,0,'14:19:21.997641917',NULL);
INSERT INTO "Racun" VALUES (22,14,1,0,'14:21:23.975313085',NULL);
INSERT INTO "Racun" VALUES (23,14,1,1,'22:13:11.352455200','22:15:23.922723900');
INSERT INTO "Racun" VALUES (24,14,4,1,'22:22:40.522004400','22:22:46.182010500');
INSERT INTO "Racun" VALUES (25,14,4,1,'22:41:49.988075400','22:41:55.697860800');
INSERT INTO "Racun" VALUES (26,14,1,1,'22:43:30.672728800','22:43:42.770920200');
INSERT INTO "Racun" VALUES (27,14,4,1,'22:45:33.024186200','22:45:37.844320900');
INSERT INTO "Racun" VALUES (28,14,4,1,'22:47:42.380777100','22:47:47.283718600');
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
INSERT INTO "Vozilo" VALUES (12,'A32-B-333','Audi Q8',13,NULL);
INSERT INTO "Vozilo" VALUES (13,'A32-B-222','Audi A3',14,NULL);
INSERT INTO "Vozilo" VALUES (14,'A52-B-173','VW Golf 8',11,NULL);
INSERT INTO "Vozilo" VALUES (15,'A32-B-545','Audi A8L',11,NULL);
INSERT INTO "Vozilo" VALUES (16,'A43-B-000','Audi A5',11,'123211651841509');
INSERT INTO "Vozilo" VALUES (17,'A54-H-555','Golf 4',15,'5151616192');
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
INSERT INTO "Korisnik" VALUES (12,'d','d','d',11,0,'d','ddd');
INSERT INTO "Korisnik" VALUES (13,'aa','a','43',12,1,'f','rr');
INSERT INTO "Korisnik" VALUES (14,'j','o','io',13,2,'iuuu','zzz');
INSERT INTO "Korisnik" VALUES (15,'Muki','Mukic','111-111/888',14,3,'niko@nikic.ba','niko123');
INSERT INTO "Parking" VALUES (1,'Sarajevo Vilsonovo',1,2,'06:00:00','23:30:00',0,4,NULL);
INSERT INTO "Parking" VALUES (2,'Verso',2,5,'00:00:00','00:00:00',1,5,'Nadkrovljena parkng garaza, 063 557 755');
INSERT INTO "Parking" VALUES (3,'SCC',10,2,'00:00:00','00:00:00',1,5,'Nadkrovljeni parking pored SCC-a');
INSERT INTO "Parking_mjesto" VALUES (1,1,'1',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (2,1,'2',0,1);
INSERT INTO "Parking_mjesto" VALUES (3,1,'3',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (4,2,'1',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (5,2,'2',0,2);
INSERT INTO "Parking_mjesto" VALUES (6,2,'3',0,NULL);
INSERT INTO "Parking_mjesto" VALUES (7,2,'4',0,5);
INSERT INTO "Parking_mjesto" VALUES (8,1,'4',0,6);
INSERT INTO "Parking_mjesto" VALUES (9,2,'5',1,NULL);
INSERT INTO "Kartica" VALUES (0,'d',0,'d',2026,5,32);
INSERT INTO "Kartica" VALUES (1,'f',1,'f',2025,3,432);
INSERT INTO "Kartica" VALUES (2,'ui',1,'mkl',2024,8,89);
INSERT INTO "Kartica" VALUES (3,'Niko',0,'1234-5555-6666-4321',2022,2,555);
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
INSERT INTO "Lokacja" VALUES (11,4,'d');
INSERT INTO "Lokacja" VALUES (12,4,'fese');
INSERT INTO "Lokacja" VALUES (13,3,'ui');
INSERT INTO "Lokacja" VALUES (14,5,'Nigdje bb');
INSERT INTO "Slike" VALUES (1,'/img/parking1.jpg',1);
INSERT INTO "Slike" VALUES (2,'/img/parking2.jpg',2);
INSERT INTO "Slike" VALUES (3,'/img/parking3.jpg',3);
INSERT INTO "Grad" VALUES (1,'Sarajevo');
INSERT INTO "Grad" VALUES (2,'Mostar');
INSERT INTO "Grad" VALUES (3,'Tuzla');
INSERT INTO "Grad" VALUES (4,'Visoko');
INSERT INTO "Grad" VALUES (5,'Zenica');
COMMIT;
