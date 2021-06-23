-- DROP DATABASE barry_s_gym;

CREATE DATABASE barry_s_gym;

USE barry_s_gym;

FLUSH PRIVILEGES;

DROP USER 'admin'@'localhost';

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'Password1!';

GRANT ALL PRIVILEGES ON barry_s_gym.* TO 'admin'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE Trainer(
	Trainer_ID VARCHAR(10) NOT NULL,
	Trainer_name VARCHAR(50) NOT NULL,
	Trainer_surname VARCHAR(50) NOT NULL,
    Trainer_dob DATE,
    Trainer_sex CHAR(1),
    Trainer_hourly_price DECIMAL(5, 2),
    Trainer_address VARCHAR (100),
    Trainer_phone VARCHAR (20),
	Trainer_email VARCHAR(100),
	PRIMARY KEY (Trainer_ID)
);

CREATE TABLE Client(
	Client_ID VARCHAR(10) NOT NULL,
	Client_name VARCHAR(50) NOT NULL,
	Client_surname VARCHAR(50) NOT NULL,
    Client_dob DATE,
    Client_sex CHAR(1),
    Client_height DECIMAL(5, 2),
    Client_weight DECIMAL(5, 2),
    Client_address VARCHAR (100),
    Client_phone VARCHAR (20),
	Client_email VARCHAR(100),
	PRIMARY KEY (Client_ID)
);

CREATE TABLE Speciality(
	Focus VARCHAR(25) NOT NULL,
    Description VARCHAR(100),
	PRIMARY KEY (Focus)
);

CREATE TABLE Trainer_on_speciality(
	Trainer_ID VARCHAR(10) NOT NULL,
	Focus VARCHAR(25) NOT NULL,
	PRIMARY KEY (Trainer_ID, Focus),
    FOREIGN KEY (Trainer_ID) REFERENCES Trainer(Trainer_ID),
    FOREIGN KEY (Focus) REFERENCES Speciality(Focus)
);

CREATE TABLE Booking(
	Booking_ID VARCHAR(10) NOT NULL,
	Client_ID VARCHAR(10) NOT NULL,
	Trainer_ID VARCHAR(10) NOT NULL,
	Booking_time TIME NOT NULL,
    Booking_date DATE NOT NULL,
    Booking_duration TIME NOT NULL,
    Focus VARCHAR(25) NOT NULL,
	PRIMARY KEY (Booking_ID),
	FOREIGN KEY (Client_ID) REFERENCES Client(Client_ID),
    FOREIGN KEY (Trainer_ID, Focus) REFERENCES Trainer_on_speciality(Trainer_ID, Focus)
);

INSERT INTO Trainer (Trainer_ID, Trainer_name, Trainer_surname, Trainer_dob, Trainer_sex, Trainer_hourly_price, Trainer_address, Trainer_phone, Trainer_email)
VALUES ('LON0000001', 'Mitchell', 'Ian', '1979-12-03', 'M', 35.00, 'The Burroughs, Hendon, London NW4 4BT, United-Kingdom', '+44(0)2084116014', 'i.mitchell@mdx.ac.uk'),
('DUB0000001','Bashir','Engie', '1982-03-25', 'F', 35.00, 'Knowledge Park, Block 16 - Dubai', '+971(0)4-3616248', 'e.bashir@mdx.ac.ae'),
('DUB0000002','Kotipalli','Sumitra', '1983-08-12', 'F', 28.50, 'Knowledge Park, Block 16 - Dubai', '+971(0)4-3678100', 's.kotipalli@mdx.ac.ae'),
('DUB0000003','Renji','Roshan', '1982-09-14', 'M', 28.50, 'Knowledge Park, Block 16 - Dubai', '+971(0)4-3678100', 'r.renji@mdx.ac.ae'),
('MAU0000001','Santokhee','Aditya', '1979-01-04', 'M', 32.75, 'Coastal Rd, Flic en Flac, Maurice', '+2304036400', 'a.santokhee@mdx.ac.mu'),
('MAL0000001','Zammit','Omar', '1984-08-29', 'M', 133.7, 'Block A, Alamein Road, Pembroke PBK 1776, Malta', '+35621456862', 'o.zammit@mdx.ac.uk');

INSERT INTO Client (Client_ID, Client_name, Client_surname, Client_dob, Client_sex, Client_height, Client_weight, Client_address, Client_phone, Client_email)
VALUES ('LONA000001', 'Doe','John', '1995-02-03', 'M', 182.50, 93.75, '121 Great North Road, Altura, PH34 1EH, UK', '+44 (0)77 5808 7379', 'realJohnDoe@email.com'),
('LONA000002', 'Buck','Jane', '1990-10-26', 'F', 169.25, 62.50, '80 Guild Street, LONDON, EC4P 7RR, UK', '+44 (0)70 1718 3677', 'maybeJaneBuck@gmail.com'),
('LONB000001', 'Kestrel','Eloise', '1963-07-20', 'F', 179.00, 82.50, '113 Crown Street, LONDON, SW6 3HW, UK', '+44 (0)70 3352 0873', 'Elo.Kestrel@yahoo.com'),
('DUBA000001', 'El Abdallah','Sireen', '1982-10-09', 'F', 158.50, 53.75, 'Villa 4, Frond L, Al Fara, The Palm, Dubai, United Arab Emirates', '+971 2667720', 'TheMermaid@queenofdubai.com'),
('DUBA000002', 'El Sadri','Siraaj', '1987-01-19', 'M', 178.00, 91.25, '54, Ghayathi - Madinat Zayed Road, Al Gharbia, Abu Dhabi, United Arab Emirates', '+971 3477696', 'sir.elsadri@gmail.com'),
('DUBA000003', 'Al Moghaddam','Rajab', '1972-05-29', 'M', 193.00, 74.75, '192, Emirates road, Dubai, United Arab Emirates', '+971 2257919', 'Raj-Raj-Rajab@hotmail.com'),
('MAUA000001', 'Fontenot','Ruby', '1992-03-25', 'F', 183.00, 72.00, 'Seven Amps Ltd., 35,  Queen Street, Port Louis, Mauritius', '+971 216 1137', 'RubyRubyWoman@musicfan.com'),
('MAUA000002', 'Lapauze','Kavi', '1975-06-21', 'M', 193.00, 84.50, '58 Royal Road, Port Louis, Mauritius', '+971 242 0042', 'Kavi-LaPauze@email.com'),
('MAUA000003', 'Gauber','Kaleem', '1988-06-21', 'M', 163.00, 55.75, '21 Maurice Pompidou Street, Port Louis, Mauritius', '+971 212 2991', 'Ka-L-Eem@email.com'),
('MAUA000004', 'Labadie','Cheyenne', '1989-05-16', 'F', 173.00, 69.75, '199, B7 - Deux Bras Road, Grand Port District, Mauritius', '+971 207 4378', 'Cheyenne-not-the-tribe@yahoo.com'),
('MALA000001', 'Muscat','Joseph', '1974-01-22', 'M', 173.00, 74.50, 'Office of the Prime Minister, Auberge de Castille, Valletta VLT 1061, Malta', '+356 2200 0000', 'The-Doc@malta-gov.mt'),
('MALA000002', 'Mallia','Emmanuel', '1969-11-11', 'M', 168.00, 89.25, '28, Triq San Duminku, Valletta VLT 1061, Malta', '+356 2456 6972', 'Manu-Malta@yahoo.com'),
('MALA000003', 'Chandler','Christopher', '1960-03-03', 'M', 182.00, 75.00, '198, Triq Il-Kabiri, Qrendi, Malta', '+356 9994 5049', 'Chris-Billionair-Chandler@email.com'),
('MALA000004', 'De Raffaele','Clifford', '1978-03-12', 'M', 175.00, 66.75, 'Block A, Alamein Road, Pembroke PBK 1776, Malta', '+356 2145 6862', 'The-Cliff@mdx.ac.uk');

INSERT INTO Speciality (Focus, Description)
VALUES ('Weight', 'Lifting heavy things to have bigger muscles'),
('Stamina', 'Running, cycling, swimming and the other things that take your breath away'),
('Weight-loss', 'Exercises focusing on making you lose weight in the right places'),
('Core', 'Exercises to help you develop your core muscles'),
('Upper-body', 'Time to get those guns shining'),
('Lower-body', 'Do not skip leg day'),
('Beach-body', 'Too late, summer is almost over');

INSERT INTO Trainer_on_speciality
VALUES ('LON0000001', 'Weight'),
('LON0000001', 'Lower-body'),
('LON0000001', 'Beach-body'),
('DUB0000001', 'Weight-loss'),
('DUB0000001', 'Upper-body'),
('DUB0000002', 'Stamina'),
('DUB0000003', 'Lower-body'),
('DUB0000003', 'Upper-body'),
('DUB0000003', 'Beach-body'),
('MAU0000001', 'Stamina'),
('MAU0000001', 'Core'),
('MAU0000001', 'Beach-body'),
('MAL0000001', 'Weight'),
('MAL0000001', 'Weight-loss'),
('MAL0000001', 'Core'),
('MAL0000001', 'Upper-body'),
('MAL0000001', 'Lower-body'),
('MAL0000001', 'Beach-body');

INSERT INTO Booking (Booking_ID, Client_ID, Trainer_ID, Booking_Time, Booking_Date, Booking_Duration, Focus)
VALUES ('APP0000001', 'MALA000001', 'MAL0000001', '12:00:00', '2020-02-01', '02:00:00', 'Beach-body'),
('APP0000002', 'DUBA000003', 'LON0000001', '10:00:00', '2020-03-02', '01:00:00', 'Lower-body'),
('APP0000003', 'MAUA000004', 'DUB0000002', '17:00:00', '2020-02-14', '02:00:00', 'Stamina'),
('APP0000004', 'MAUA000002', 'MAL0000001', '19:00:00', '2020-02-17', '01:00:00', 'Core'),
('APP0000005', 'DUBA000001', 'MAU0000001', '08:00:00', '2020-02-23', '01:00:00', 'Core'),
('APP0000006', 'LONB000001', 'DUB0000001', '15:00:00', '2020-03-11', '01:00:00', 'Weight-loss')
;
