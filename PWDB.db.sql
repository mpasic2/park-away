BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Racun" (
	"racun_id"	INTEGER NOT NULL,
	"vozilo_id"	INTEGER NOT NULL,
	"parking_mjesto_id"	INTEGER NOT NULL,
	"placeno"	INTEGER NOT NULL,
	"datum_odjave"	DATE,
	PRIMARY KEY("racun_id"),
	FOREIGN KEY("vozilo_id") REFERENCES "Vozilo"("vozilo_id")
);
CREATE TABLE IF NOT EXISTS "Grad" (
	"grad_id"	INTEGER NOT NULL,
	"naziv"	TEXT NOT NULL,
	PRIMARY KEY("grad_id")
);
CREATE TABLE IF NOT EXISTS "Parking_mjesto" (
	"parking_mjesto_id"	INTEGER NOT NULL,
	"parking_id"	INTEGER NOT NULL,
	"parkirano_vozilo_id"	INTEGER,
	"vrijeme_prjave"	TIMEDATE,
	"broj_parking_mjesta"	INTEGER NOT NULL,
	"mjesto_za_invalide"	INTEGER NOT NULL,
	PRIMARY KEY("parking_mjesto_id"),
	FOREIGN KEY("parking_id") REFERENCES "Parking"("parking_id"),
	FOREIGN KEY("parkirano_vozilo_id") REFERENCES "Vozilo"("vozilo_id")
);
CREATE TABLE IF NOT EXISTS "Vozilo" (
	"vozilo_id"	INTEGER NOT NULL,
	"registracijske_oznake"	TEXT NOT NULL UNIQUE,
	"model"	INTEGER NOT NULL,
	"korisnik_id"	INTEGER NOT NULL,
	PRIMARY KEY("vozilo_id")
);
CREATE TABLE IF NOT EXISTS "Parking" (
	"parking_id"	INTEGER NOT NULL,
	"naziv"	TEXT NOT NULL,
	"lokacija_id"	INTEGER NOT NULL,
	"cijena"	INTEGER NOT NULL,
	"pocetak_radnog_vremena"	TIME NOT NULL,
	"kraj_radnog_vremena"	TIME NOT NULL,
	"stalni_parking"	INTEGER NOT NULL,
	"ocjena"	INTEGER NOT NULL,
	"opis"	TEXT,
	"slika"	TEXT,
	PRIMARY KEY("parking_id"),
	FOREIGN KEY("lokacija_id") REFERENCES "Lokacja"("lokacija_id")
);
CREATE TABLE IF NOT EXISTS "Korisnik" (
	"korisnik_id"	INTEGER NOT NULL,
	"ime"	TEXT NOT NULL,
	"prezime"	TEXT NOT NULL,
	"broj_telefona"	TEXT NOT NULL UNIQUE,
	"lokacja_id"	INTEGER NOT NULL,
	"broj_bankovnog_racuna"	TEXT NOT NULL,
	"slika"	TEXT,
	"email"	TEXT,
	"lozinka"	TEXT,
	PRIMARY KEY("korisnik_id"),
	FOREIGN KEY("lokacja_id") REFERENCES "Lokacja"("lokacija_id")
);
CREATE TABLE IF NOT EXISTS "Lokacja" (
	"lokacija_id"	INTEGER NOT NULL,
	"grad_id"	INTEGER NOT NULL,
	"naziv_ulice"	TEXT NOT NULL,
	PRIMARY KEY("lokacija_id")
);
INSERT INTO "Racun" VALUES (1,1,3,0,NULL);
INSERT INTO "Racun" VALUES (2,2,4,1,'2021-01-07 21:00:00');
INSERT INTO "Grad" VALUES (1,'Sarajevo');
INSERT INTO "Grad" VALUES (2,'Mostar');
INSERT INTO "Grad" VALUES (3,'Tuzla');
INSERT INTO "Grad" VALUES (4,'Visoko');
INSERT INTO "Parking_mjesto" VALUES (1,1,NULL,NULL,1,0);
INSERT INTO "Parking_mjesto" VALUES (2,1,NULL,NULL,2,0);
INSERT INTO "Parking_mjesto" VALUES (3,1,1,'2021-01-07 12:19:00',3,0);
INSERT INTO "Parking_mjesto" VALUES (4,2,2,'2021-01-07 13:32:10',1,0);
INSERT INTO "Parking_mjesto" VALUES (5,2,NULL,NULL,2,0);
INSERT INTO "Parking_mjesto" VALUES (6,2,NULL,NULL,3,0);
INSERT INTO "Parking_mjesto" VALUES (7,2,NULL,NULL,4,0);
INSERT INTO "Parking_mjesto" VALUES (8,1,NULL,NULL,4,0);
INSERT INTO "Parking_mjesto" VALUES (9,2,NULL,NULL,5,1);
INSERT INTO "Vozilo" VALUES (1,'K63-M-058','Volkswagen Golf 5',1);
INSERT INTO "Vozilo" VALUES (2,'A12-A-345','Mercedes-Benz C220',2);
INSERT INTO "Vozilo" VALUES (3,'E67-0-143','Skoda oktavia ',3);
INSERT INTO "Vozilo" VALUES (4,'K21-J-825','Opel Astra',1);
INSERT INTO "Parking" VALUES (1,'Parking Sarajevo Vilsonovo setaliste',1,2,'06:00','23:30',0,4,NULL,NULL);
INSERT INTO "Parking" VALUES (2,'Parking Verso',2,5,'00:00','00:00',1,5,'Nadkrovljena parkng garaza, 063 557 755',NULL);
INSERT INTO "Korisnik" VALUES (1,'Mujo','Mujic','062-123-123',1,'1111-1111-2222-2222',NULL,'mmujic@gmail.com','password');
INSERT INTO "Korisnik" VALUES (2,'Suljo','Suljic','062-234-234',3,'1234-2345-1234-4567',NULL,'ssuljic@gmail.com','probaproba123');
INSERT INTO "Korisnik" VALUES (3,'Pero','Peric','061-122-221',2,'1223-2345-1234-4345',NULL,'pperic@gmail.com','peroDjetlic125');
INSERT INTO "Lokacja" VALUES (1,1,'Zmaja od Bosne');
INSERT INTO "Lokacja" VALUES (2,2,'Hrvatskih branitelja');
INSERT INTO "Lokacja" VALUES (3,3,'Kulina Bana');
INSERT INTO "Lokacja" VALUES (4,4,'Hadzijina voda');
COMMIT;
