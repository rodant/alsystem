# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "SIGNUPDATA" ("first_name" VARCHAR NOT NULL PRIMARY KEY,"email" VARCHAR NOT NULL,"password" VARCHAR NOT NULL);

# --- !Downs

drop table "SIGNUPDATA";

