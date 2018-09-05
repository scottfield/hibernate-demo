ALTER TABLE h_user ADD version BIGINT NOT NULL ;
SELECT count(*) FROM h_user;
SELECT * FROM h_user;
DELETE FROM h_user;
ALTER TABLE hibernate_user RENAME TO h_user;
