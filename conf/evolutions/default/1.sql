# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "LOGINDATA" ("user_id" VARCHAR NOT NULL PRIMARY KEY,"password" VARCHAR NOT NULL);

# --- !Downs

drop table "LOGINDATA";

