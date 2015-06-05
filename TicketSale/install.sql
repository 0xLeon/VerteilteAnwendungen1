DROP TABLE IF EXISTS ticketsale_seat;
CREATE TABLE ticketsale_seat (
	seatID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	seatNumber INT(10) NOT NULL,
	eventID INT(10) NOT NULL,
	seatStatus TINYINT(1) NOT NULL DEFAULT 0,
	userID INT(10) DEFAULT NULL,

	KEY (eventID),
	KEY (userID)
);

DROP TABLE IF EXISTS ticketsale_user_to_group;
CREATE TABLE ticketsale_user_to_group (
	userID INT(10) NOT NULL,
	groupID INT(10) NOT NULL,

	UNIQUE userToGroup (userID, groupID),
	KEY (groupID)
);

DROP TABLE IF EXISTS ticketsale_group;
CREATE TABLE ticketsale_group (
	groupID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	groupIdentifier VARCHAR(255) NOT NULL,
	groupName VARCHAR(255) NOT NULL,

	UNIQUE (groupIdentifier),
	UNIQUE (groupName),
	KEY (groupName)
);

DROP TABLE IF EXISTS ticketsale_event;
CREATE TABLE ticketsale_event (
	eventID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	eventName VARCHAR(255) NOT NULL,
	description TEXT,
	reservationDeadline BIGINT(20) NOT NULL,
	purchaseDeadline BIGINT(20) NOT NULL,

	KEY (eventName)
);

DROP TABLE IF EXISTS ticketsale_user;
CREATE TABLE ticketsale_user (
	userID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
	passwordHash VARCHAR(255) DEFAULT NULL,

	UNIQUE (username)
);

ALTER TABLE ticketsale_seat ADD FOREIGN KEY (eventID) REFERENCES ticketsale_event (eventID) ON DELETE CASCADE;
ALTER TABLE ticketsale_seat ADD FOREIGN KEY (userID) REFERENCES ticketsale_user (userID) ON DELETE SET NULL;

ALTER TABLE ticketsale_user_to_group ADD FOREIGN KEY (userID) REFERENCES ticketsale_user (userID) ON DELETE CASCADE;
ALTER TABLE ticketsale_user_to_group ADD FOREIGN KEY (groupID) REFERENCES ticketsale_group (groupID) ON DELETE CASCADE;

INSERT INTO ticketsale_group (groupIdentifier, groupName) VALUES ('user.admin', 'Admins');
INSERT INTO ticketsale_group (groupIdentifier, groupName) VALUES ('user.customer', 'Kunde');

INSERT INTO ticketsale_user (username, passwordHash) VALUES ('Admin', '1000:b71a091cd21ec1d577106cc7d709e0db746c551de7e7e209:d30b2e4600c93473ccbaf85936ddc4e3ff856bbe5f96e1c9');

INSERT INTO ticketsale_user_to_group (userID, groupID) VALUES (1, 1);
INSERT INTO ticketsale_user_to_group (userID, groupID) VALUES (1, 2);
