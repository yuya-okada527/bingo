DROP TABLE IF EXISTS host;
DROP TABLE IF EXISTS guest;
DROP TABLE IF EXISTS bingo_game;
DROP TABLE IF EXISTS card;


CREATE TABLE host (
	name		VARCHAR(16),
	password	CHAR(8),
	PRIMARY KEY (name)
);

CREATE TABLE guest (
	guest_name	VARCHAR(16),
	game_name	VARCHAR(32),
	card_id		VARCHAR(48),
	reach_flg	CHAR(1),
	bingo_flg	CHAR(1),
	PRIMARY KEY	(guest_name, game_name),
	FOREIGN KEY	(game_name)
	REFERENCES	bingo_game (game_name)
);

CREATE TABLE bingo_game (
	game_name			VARCHAR(32),
	num_array_string	VARCHAR(256),
	PRIMARY KEY (game_name)
);

CREATE TABLE card (
	card_id	VARCHAR(48),
	card_num_array_string	varchar(128),
	PRIMARY KEY (card_id)
);
