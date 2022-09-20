DROP DATABASE IF EXISTS cinema;
CREATE DATABASE cinema;

USE cinema;
CREATE TABLE USERS
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(60) NOT NULL UNIQUE,
    phone_number VARCHAR(70) NOT NULL UNIQUE,
    name         VARCHAR(70) NOT NULL,
    surname      VARCHAR(70) NOT NULL,
    login        VARCHAR(30) NOT NULL UNIQUE,
    password     VARCHAR(60) NOT NULL,
    role         VARCHAR(15) NOT NULL,
    balance      BIGINT
);
CREATE TABLE SHOW_TIMES
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    filmId       BIGINT NOT NULL REFERENCES FILMS (id),
    date         DATE   NOT NULL,
    status       VARCHAR(30),
    startTime    TIME,
    endTime      TIME
);
CREATE TABLE FILMS
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(70)  NOT NULL UNIQUE,
    description      VARCHAR(2000),
    genre            VARCHAR(50)  NOT NULL,
    posterImgPath    VARCHAR(200) NOT NULL,
    director         VARCHAR(30)  NOT NULL,
    runningTime      BIGINT       NOT NULL,
    youtubeTrailerId VARCHAR(20)  NOT NULL
);

CREATE TABLE TICKETS
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId     BIGINT NOT NULL REFERENCES USERS (id),
    showTimeId BIGINT NOT NULL REFERENCES SHOW_TIMES (id),
    seat       VARCHAR(20),
    status     VARCHAR(20)
);
CREATE TABLE SEATS
(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   showtimeId BIGINT NOT NULL REFERENCES SHOW_TIMES(id),
   seat VARCHAR(10),
   status VARCHAR(30)
);
