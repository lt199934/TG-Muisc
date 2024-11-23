/*
 Navicat Premium Data Transfer

 Source Server         : MySQL55
 Source Server Type    : MySQL
 Source Server Version : 50554 (5.5.54)
 Source Host           : localhost:3306
 Source Schema         : tg_music

 Target Server Type    : MySQL
 Target Server Version : 50554 (5.5.54)
 File Encoding         : 65001

 Date: 23/11/2024 21:53:17
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    `super_id`  int(11) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `pwd`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`super_id`) USING BTREE,
    UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
    UNIQUE INDEX `account`(`nick_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin`
VALUES (1, 'admin', '超级管理员', 'admin');

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album`
(
    `album_id`     int(11) NOT NULL AUTO_INCREMENT,
    `album`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `singer_id`    int(11) NOT NULL,
    `album_img`    varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `introduction` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `company`      varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `time`         date                                                    NOT NULL,
    PRIMARY KEY (`album_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of album
-- ----------------------------
INSERT INTO `album`
VALUES (1, '牵丝戏', 1, '/album/牵丝戏.jpg',
        '《牵丝戏》是由Vagary填词，银临、Aki阿杰演唱的古风单曲，于2015年推出。歌曲通过描绘傀儡翁与牵扯一生的傀儡之间的相伴、别离，来诉说一段牵恋。',
        '极韵文化', '2019-03-28');
INSERT INTO `album`
VALUES (2, '宇光十色', 2, '/album/该死的温柔.jfif',
        '该歌曲获得第五届东南劲爆音乐榜劲爆十大金曲 [1]  、2007百度娱乐沸点年度颁奖礼“最热门MV”和“十大金曲” [2]  、2007年度9+2音乐先锋榜“内地十大先锋金曲“ [3]  等奖项。并入选大型音乐纪录片《岁月留声·中国唱片60年见证》 [4]  和日本书籍《通过唱歌记住中国话》',
        'xxx', '2007-01-01');
INSERT INTO `album`
VALUES (3, '干物女', 3, '/album/干物女.jfif',
        '干物女是指放弃恋爱，以对自己而言懒散舒适的生活方式生活的女性。来源于火浦智漫画《小萤的青春》（《萤之光》）的女主角「雨宫萤」的单身生活。（包括宫 萤在家，喜欢独自看漫画，饮可乐；假日好睡，幸福写意）。后被用来指无意恋爱的二十、三十岁女性。',
        'xxx传媒', '2018-11-01');

-- ----------------------------
-- Table structure for classic
-- ----------------------------
DROP TABLE IF EXISTS `classic`;
CREATE TABLE `classic`
(
    `classic_id`   int(11) NOT NULL AUTO_INCREMENT,
    `song_list_id` int(11) NOT NULL,
    `fen_id`       int(11) NOT NULL,
    PRIMARY KEY (`classic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of classic
-- ----------------------------
INSERT INTO `classic`
VALUES (1, 1, 1);
INSERT INTO `classic`
VALUES (2, 1, 7);
INSERT INTO `classic`
VALUES (3, 2, 8);

-- ----------------------------
-- Table structure for fenlei
-- ----------------------------
DROP TABLE IF EXISTS `fenlei`;
CREATE TABLE `fenlei`
(
    `fen_id`  int(11) NOT NULL AUTO_INCREMENT,
    `content` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`fen_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fenlei
-- ----------------------------
INSERT INTO `fenlei`
VALUES (1, '伤感');
INSERT INTO `fenlei`
VALUES (2, '快乐');
INSERT INTO `fenlei`
VALUES (3, '安静');
INSERT INTO `fenlei`
VALUES (4, '励志');
INSERT INTO `fenlei`
VALUES (5, '治愈');
INSERT INTO `fenlei`
VALUES (6, '思念');
INSERT INTO `fenlei`
VALUES (7, '古风');
INSERT INTO `fenlei`
VALUES (8, '爱情');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `message_id`    int(11) NOT NULL AUTO_INCREMENT,
    `user_id`       int(11) NOT NULL,
    `content`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `reply_user_id` int(11) NOT NULL DEFAULT 0,
    `time`          date                                                    NOT NULL,
    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for singer
-- ----------------------------
DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer`
(
    `singer_id`    int(11) NOT NULL AUTO_INCREMENT,
    `singer_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `sex`          varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `img_url`      varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `birthday`     date                                                    NOT NULL,
    `introduction` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`singer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of singer
-- ----------------------------
INSERT INTO `singer`
VALUES (1, 'ak阿杰', '女', '/singer/ak阿杰.jpg', '2023-07-08',
        'Aki阿杰，古风女歌手，原创音乐团队【墨明棋妙】成员 [1]  ，【i2star】组合成员。 [2]  2019年担任了《陈情令国风音乐专辑》中，魏无羡人物主题曲《曲尽陈情》的戏腔演唱。 [3]  2020年8月29日，发布了自己的首张个人国风专辑：《清商语》');
INSERT INTO `singer`
VALUES (2, '马天宇', '男', '/singer/马天宇.jpg', '1981-01-08',
        '该歌曲获得第五届东南劲爆音乐榜劲爆十大金曲 [1]  、2007百度娱乐沸点年度颁奖礼“最热门MV”和“十大金曲” [2]  、2007年度9+2音乐先锋榜“内地十大先锋金曲“ [3]  等奖项。并入选大型音乐纪录片《岁月留声·中国唱片60年见证》 [4]  和日本书籍《通过唱歌记住中国话》');
INSERT INTO `singer`
VALUES (3, '艾辰', '男', '/singer/艾辰.jpg', '1995-07-11', '艾辰1995年7月11日出生于江苏省连云港市，中国内地男歌手。');

-- ----------------------------
-- Table structure for song
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song`
(
    `song_id`        int(11) NOT NULL AUTO_INCREMENT,
    `song`           varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `singer_id`      int(11) NOT NULL,
    `play_count`     int(11) NOT NULL DEFAULT 0,
    `download_count` int(11) NOT NULL DEFAULT 0,
    `url`            varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `language`       varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `album_id`       int(11) NOT NULL,
    `time`           datetime                                                NOT NULL,
    PRIMARY KEY (`song_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of song
-- ----------------------------
INSERT INTO `song`
VALUES (1, '牵丝戏', 1, 104, 52, '/songs/牵丝戏.mp3', '汉语古风', 1, '2015-01-01 00:00:00');
INSERT INTO `song`
VALUES (2, '该死的温柔', 2, 8, 0, '/songs/该死的温柔.mp3', '汉语流行', 2, '2015-02-18 03:00:00');
INSERT INTO `song`
VALUES (3, '干物女', 3, 33, 0, '/songs/干物女.mp3', '汉语古风', 3, '2021-06-01 21:00:00');
INSERT INTO `song`
VALUES (4, '江南', 3, 11, 0, '/songs/江南.mp3', '汉语古风', 3, '2021-06-08 21:00:00');
INSERT INTO `song`
VALUES (5, '刚刚好', 3, 49, 0, '/songs/刚刚好.mp3', '汉语流行', 3, '2021-06-28 22:00:00');

-- ----------------------------
-- Table structure for songcollect
-- ----------------------------
DROP TABLE IF EXISTS `songcollect`;
CREATE TABLE `songcollect`
(
    `collect_id`   int(11) NOT NULL AUTO_INCREMENT,
    `song_list_id` int(11) NOT NULL,
    `song_id`      int(11) NOT NULL,
    PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of songcollect
-- ----------------------------
INSERT INTO `songcollect`
VALUES (1, 1, 1);
INSERT INTO `songcollect`
VALUES (2, 1, 3);
INSERT INTO `songcollect`
VALUES (3, 2, 5);
INSERT INTO `songcollect`
VALUES (4, 2, 4);
INSERT INTO `songcollect`
VALUES (5, 2, 1);
INSERT INTO `songcollect`
VALUES (7, 1, 5);

-- ----------------------------
-- Table structure for songlist
-- ----------------------------
DROP TABLE IF EXISTS `songlist`;
CREATE TABLE `songlist`
(
    `song_list_id` int(11) NOT NULL AUTO_INCREMENT,
    `song_list`    varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `introduction` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `img_url`      varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `time`         datetime                                                NOT NULL,
    `user_id`      int(11) NOT NULL,
    PRIMARY KEY (`song_list_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of songlist
-- ----------------------------
INSERT INTO `songlist`
VALUES (1, '古风歌单牵丝戏',
        '《牵丝戏》是由Vagary填词，银临、Aki阿杰演唱的古风单曲，于2015年推出。歌曲通过描绘傀儡翁与牵扯一生的傀儡之间的相伴、别离，来诉说一段牵恋。',
        '/songListImg/牵丝戏.jpg', '2021-01-08 07:00:00', 1);
INSERT INTO `songlist`
VALUES (2, '生活是自己的总是在忙着可爱', '心情不好吗就来听歌吧', '/songListImg/生活.jpg', '2020-01-01 00:00:00', 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`            int(11) NOT NULL AUTO_INCREMENT,
    `account`            varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `pwd`                varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `nick_name`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `phone`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `email`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `user_name`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `sex`                varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL,
    `head_img`           varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/headImg/head.jpg',
    `type`               varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT '普通用户',
    `Personal_signature` varchar(580) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `birthday`           date                                                    NOT NULL,
    `time`               timestamp                                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `account`(`account`) USING BTREE,
    UNIQUE INDEX `phone`(`phone`) USING BTREE,
    UNIQUE INDEX `email`(`email`) USING BTREE,
    UNIQUE INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'admin', '123123', '不靠谱先生', '15272330873', '123@123.com', '张三', '男', '/headImg/head.jpg', '管理员',
        '若人生只如初见，亦还是梦中人，还是我我', '2014-01-08', '2023-07-31 04:00:00');
INSERT INTO `user`
VALUES (2, 'ccc', '123123', '', '13456789011', 'tgblog@qq.com', '账务', '男', '/headImg/tj.png', '普通用户', '离开酒店',
        '2022-11-10', '2022-10-01 00:00:00');
INSERT INTO `user`
VALUES (3, 'ddd', '123123', 'test', '13540985678', '24842055501@qq.com', '梵蒂冈', '男', '/headImg/22.webp', '普通用户',
        '', '2023-03-10', '2023-02-10 00:00:00');
INSERT INTO `user`
VALUES (4, 'bbb', '123123', 'ssd', '15456789098', 'tgblosdsg@qq.com', 'sdfsf', '女', '/headImg/preview.jpg', '普通用户',
        '', '2023-03-09', '2023-02-10 00:00:00');
INSERT INTO `user`
VALUES (5, 'eee', '123123', '', '13540987654', '3105161314@qq.com', '里斯', '男', '/headImg/55.webp', '普通用户',
        '世界发生了纠纷', '2016-05-31', '2023-07-31 00:00:00');
INSERT INTO `user`
VALUES (8, 'fff', '123123', '老二', '13540987657', '3105161434@qq.com', '张老二', '男', '/headImg/6.webp', '普通用户',
        '上来看就是分厘卡即使对方', '2022-01-31', '2023-07-31 00:00:00');
INSERT INTO `user`
VALUES (9, 'ggg', '123123', '', '13540987659', '3105161sd314@qq.com', '张武', '男', '/headImg/h.webp', '普通用户',
        '啊实打实的', '2023-08-14', '2023-07-31 00:00:00');
INSERT INTO `user`
VALUES (10, 'aaa', '123123', '测试用户1', '15282330786', '310516dd1314@qq.com', '李嘉欣', '女', '/headImg/head.jpg',
        '管理员', '额我热温热', '2020-02-29', '2023-07-31 00:00:00');
INSERT INTO `user`
VALUES (11, 'hhh', '123123', '', '15285630903', '31dfdf161314@qq.com', '王五', '男', '/headImg/3.webp', '普通用户', '',
        '2023-09-01', '2023-09-01 01:03:16');
INSERT INTO `user`
VALUES (13, 'jjj', '123123', '', '13540987658', '310516i14@qq.com', '美羊羊', '女', '/headImg/d0.jpeg', '普通用户',
        'hi', '2023-09-01', '2023-09-01 01:07:33');

-- ----------------------------
-- Table structure for useralbums
-- ----------------------------
DROP TABLE IF EXISTS `useralbums`;
CREATE TABLE `useralbums`
(
    `user_album_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id`       int(11) NOT NULL,
    `album_id`      int(11) NOT NULL,
    PRIMARY KEY (`user_album_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of useralbums
-- ----------------------------
INSERT INTO `useralbums`
VALUES (36, 1, 4);
INSERT INTO `useralbums`
VALUES (41, 1, 1);
INSERT INTO `useralbums`
VALUES (42, 1, 3);
INSERT INTO `useralbums`
VALUES (43, 1, 2);

-- ----------------------------
-- Table structure for usersingers
-- ----------------------------
DROP TABLE IF EXISTS `usersingers`;
CREATE TABLE `usersingers`
(
    `user_singer_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        int(11) NOT NULL,
    `singer_id`      int(11) NOT NULL,
    PRIMARY KEY (`user_singer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usersingers
-- ----------------------------

-- ----------------------------
-- Table structure for usersonglists
-- ----------------------------
DROP TABLE IF EXISTS `usersonglists`;
CREATE TABLE `usersonglists`
(
    `user_songlist_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id`          int(11) NOT NULL,
    `song_list_id`     int(11) NOT NULL,
    PRIMARY KEY (`user_songlist_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usersonglists
-- ----------------------------
INSERT INTO `usersonglists`
VALUES (12, 1, 2);
INSERT INTO `usersonglists`
VALUES (13, 1, 1);

-- ----------------------------
-- Table structure for usersongs
-- ----------------------------
DROP TABLE IF EXISTS `usersongs`;
CREATE TABLE `usersongs`
(
    `user_song_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id`      int(11) NOT NULL,
    `song_id`      int(11) NOT NULL,
    PRIMARY KEY (`user_song_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of usersongs
-- ----------------------------
INSERT INTO `usersongs`
VALUES (33, 1, 5);
INSERT INTO `usersongs`
VALUES (40, 1, 4);
INSERT INTO `usersongs`
VALUES (42, 1, 1);
INSERT INTO `usersongs`
VALUES (44, 1, 2);
INSERT INTO `usersongs`
VALUES (46, 10, 3);

SET
FOREIGN_KEY_CHECKS = 1;
