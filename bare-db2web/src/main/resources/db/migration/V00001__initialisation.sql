
CREATE TABLE new_user
(
    id                    	serial,
    email         			varchar(255),
    transfer_type      		varchar(255),
    primary key (id)
);

CREATE TABLE ready_user
(
    id                    	serial,
    email         			varchar(255),
    transfer_type      		varchar(255),
    primary key (id)
);

INSERT INTO new_user (email, transfer_type) VALUES('kuzds@bk.ru', 'SOAP');
INSERT INTO new_user (email, transfer_type) VALUES('selin@bk.ru', 'REST');