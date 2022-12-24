CREATE TABLE `User`(
                        `userIdx` int PRIMARY KEY AUTO_INCREMENT,
                        `name` varchar(20) NOT NULL,
                        `nickName` varchar(30) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `pwd` varchar(20) NOT NULL,
                        `profileimgUrl` text,
                        `gender` varchar(20),
                        `birth` date,
                        `phone` varchar(10),
                        `mannerTem` varchar(3),
                        `redealHope` varchar(3),
                        `responseRate` varchar(3),
                        `status` varchar(10) DEFAULT 'ACTIVE',
                        `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                        `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Location` (
                            `locationIdx` int PRIMARY KEY AUTO_INCREMENT,
                            `name` varchar(20) NOT NULL,
                            `status` varchar(10) DEFAULT 'ACTIVE',
                            `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                            `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `UserLocation` (
                                `userLocationIdx` int PRIMARY KEY AUTO_INCREMENT,
                                `certification` varchar(10) DEFAULT 'ACTIVE',
                                `certificationCount` int DEFAULT 0,
                                `representLo` varchar(20),
                                `latitude` double,
                                `longitude` double,
                                `maxRange` int DEFAULT 1,
                                `userIdx` int NOT NULL,
                                `locationIdx` int NOT NULL,
                                `status` varchar(10) DEFAULT 'ACTIVE',
                                `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                                `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Keyword` (
                           `keywordIdx` int PRIMARY KEY AUTO_INCREMENT,
                           `keyword` varchar(30) NOT NULL,
                           `userIdx` int NOT NULL,
                           `status` varchar(10) DEFAULT 'ACTIVE',
                           `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                           `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Product` (
                           `productIdx` int PRIMARY KEY AUTO_INCREMENT,
                           `productTitle` varchar(40) NOT NULL,
                           `productContent` varchar(100) NOT NULL,
                           `userIdx` int NOT NULL,
                           `locationIdx` int NOT NULL,
                           `price` int NOT NULL,
                           `priceProposal` BOOLEAN DEFAULT false,
                           `category` varchar(30) NOT NULL,
                           `sold` boolean DEFAULT false,
                           `status` varchar(10) DEFAULT 'ACTIVE',
                           `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                           `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `ProductLike` (
                               `productLikeIdx` int PRIMARY KEY AUTO_INCREMENT,
                               `userIdx` int NOT NULL,
                               `productIdx` int NOT NULL,
                               `status` varchar(10) DEFAULT 'ACTIVE',
                               `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                               `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `ProductImg` (
                              `productImgIdx` int PRIMARY KEY AUTO_INCREMENT,
                              `productIdx` int NOT NULL,
                              `productImg` varchar(100) NOT NULL,
                              `status` varchar(10) DEFAULT 'ACTIVE',
                              `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                              `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `ProductChat` (
                               `productChatIdx` int PRIMARY KEY AUTO_INCREMENT,
                               `productIdx` int NOT NULL,
                               `buyerIdx` int NOT NULL,
                               `sellerIdx` int NOT NULL,
                               `buyerStatus` boolean,
                               `sellerStatus` boolean,
                               `chat` varchar(500) NOT NULL,
                               `status` varchar(10) DEFAULT 'ACTIVE',
                               `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                               `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Post` (
                        `postIdx` int PRIMARY KEY AUTO_INCREMENT,
                        `postTitle` varchar(30) NOT NULL,
                        `postContent` varchar(100) NOT NULL,
                        `locationIdx` int NOT NULL,
                        `status` varchar(10) DEFAULT 'ACTIVE',
                        `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                        `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `PostImg` (
                           `postImgIdx` int PRIMARY KEY AUTO_INCREMENT,
                           `postIdx` int NOT NULL,
                           `postImgUrl` text NOT NULL,
                           `status` varchar(10) DEFAULT 'ACTIVE',
                           `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                           `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `PostComment` (
                               `postCommentIdx` int PRIMARY KEY AUTO_INCREMENT,
                               `comment` varchar(200) NOT NULL,
                               `anoComment` boolean DEFAULT false,
                               `postIdx` int NOT NULL,
                               `userIdx` int NOT NULL,
                               `commentNum` int,
                               `status` varchar(10) DEFAULT 'ACTIVE',
                               `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                               `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `PostLike` (
                            `postLikeIdx` int PRIMARY KEY AUTO_INCREMENT,
                            `postIdx` int NOT NULL,
                            `userIdx` int NOT NULL,
                            `status` varchar(10) DEFAULT 'ACTIVE',
                            `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                            `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `CommentLike` (
                               `commentLikeIdx` int PRIMARY KEY AUTO_INCREMENT,
                               `postCommentIdx` int NOT NULL,
                               `userIdx` int NOT NULL,
                               `status` varchar(10) DEFAULT 'ACTIVE',
                               `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                               `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `Concern` (
                           `concernIdx` int PRIMARY KEY AUTO_INCREMENT,
                           `concern` varchar(20) NOT NULL,
                           `status` varchar(10) DEFAULT 'ACTIVE',
                           `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                           `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `UserConcern` (
                               `userConcernIdx` int PRIMARY KEY AUTO_INCREMENT,
                               `concernIdx` int NOT NULL,
                               `userIdx` int NOT NULL,
                               `status` varchar(10) DEFAULT 'ACTIVE',
                               `createAt` timestamp DEFAULT CURRENT_TIMESTAMP,
                               `updateAt` timestamp DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `UserLocation` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `UserLocation` ADD FOREIGN KEY (`locationIdx`) REFERENCES `Location` (`locationIdx`);

ALTER TABLE `Keyword` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `Product` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `Product` ADD FOREIGN KEY (`locationIdx`) REFERENCES `Location` (`locationIdx`);

ALTER TABLE `ProductLike` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `ProductLike` ADD FOREIGN KEY (`productIdx`) REFERENCES `Product` (`productIdx`);

ALTER TABLE `ProductImg` ADD FOREIGN KEY (`productIdx`) REFERENCES `Product` (`productIdx`);

ALTER TABLE `ProductChat` ADD FOREIGN KEY (`productIdx`) REFERENCES `Product` (`productIdx`);

ALTER TABLE `ProductChat` ADD FOREIGN KEY (`buyerIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `ProductChat` ADD FOREIGN KEY (`sellerIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `Post` ADD FOREIGN KEY (`locationIdx`) REFERENCES `Location` (`locationIdx`);

ALTER TABLE `PostImg` ADD FOREIGN KEY (`postIdx`) REFERENCES `Post` (`postIdx`);

ALTER TABLE `PostComment` ADD FOREIGN KEY (`postIdx`) REFERENCES `Post` (`postIdx`);

ALTER TABLE `PostComment` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `PostLike` ADD FOREIGN KEY (`postIdx`) REFERENCES `Post` (`postIdx`);

ALTER TABLE `PostLike` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `CommentLike` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);

ALTER TABLE `CommentLike` ADD FOREIGN KEY (`postCommentIdx`) REFERENCES `PostComment` (`postCommentIdx`);

ALTER TABLE `UserConcern` ADD FOREIGN KEY (`concernIdx`) REFERENCES `Concern` (`concernIdx`);

ALTER TABLE `UserConcern` ADD FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`);
