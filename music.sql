use music;
-- 管理员 
create table admin(
-- 管理员id
	super_id int primary key auto_increment,
-- 姓名
	user_name varchar(30) unique not null,
-- 帐号
	account varchar(30) unique not null,
-- 密码
	pwd varchar(30)  not null,
-- 帐号类型
	type varchar(10) not null default'管理员'
);
INSERT INTO `admin` VALUES ('1', 'aaa', 'admin', 'admin', '管理员');
-- 用户 
create table user(
-- 用户id
 	user_id int primary key auto_increment,
-- 帐号
	account varchar(100) unique not null,
-- 密码
	pwd varchar(100)  not null,
-- 昵称
	nick_name varchar(100),
-- 手机
	phone varchar(100) unique not null,
-- 邮箱
	email varchar(100) unique not null,
-- 姓名
	user_name varchar(100) unique not null,
-- 性别
	sex varchar(10) not null,
-- 头像
	head_img varchar(300) not null default'/headImg/head.jpg',
-- 帐号类型
	type varchar(10) not null default'普通用户',
-- 个性签名
	Personal_signature varchar(580),
-- 生日
	birthday date not null,
-- 注册时间
	time date not null
);
INSERT INTO `user` VALUES ('1', 'aaa', '123123','不靠谱先生', '15272330873', '123@123.com', '张三', '男', '/headImg/head.jpg', '普通用户', null, '2014-01-08', '2021-05-08');
-- 用户创建歌单
create table songlist(
-- 歌单id 
	song_list_id int primary key auto_increment,
-- 歌单
	song_list varchar(20) not null,
-- 简介 	
	introduction varchar(300) not null,
-- 歌单图片	
    img_url varchar(300) not null,
-- 歌单创建时间
	time date not null,
-- 用户id
	user_id int not null
	
);
INSERT INTO `songlist` VALUES ('1', '古风歌单牵丝戏', '《牵丝戏》是由Vagary填词，银临、Aki阿杰演唱的古风单曲，于2015年推出。歌曲通过描绘傀儡翁与牵扯一生的傀儡之间的相伴、别离，来诉说一段牵恋。', '/songlistImg/牵丝戏.jpg', '2021-01-08', '1');
INSERT INTO `songlist` VALUES ('2', '生活是自己的总是在忙着可爱', '心情不好吗就来听歌吧', '/songlistImg/生活.jpg', '2020-01-01', '1');
create table singer(
-- 歌手
	singer_id int primary key auto_increment,
-- 歌手名字
	singer_name varchar(20) not null,
-- 性别
	sex varchar(10) not null,
-- 歌手图片连接
	img_url varchar(300) not null,
-- 生日
	birthday date not null,
-- 简介 
	intrduction varchar(300) not null
);
INSERT INTO `singer` VALUES ('1', 'ak阿杰', '女', '/singer/ak阿杰.jpg', '1980-09-11', 'Aki阿杰，古风女歌手，原创音乐团队【墨明棋妙】成员 [1]  ，【i2star】组合成员。 [2]  2019年担任了《陈情令国风音乐专辑》中，魏无羡人物主题曲《曲尽陈情》的戏腔演唱。 [3]  2020年8月29日，发布了自己的首张个人国风专辑：《清商语》');
INSERT INTO `singer` VALUES ('2', '马天宇', '男', '/singer/马天宇.jpg', '1981-01-08', '该歌曲获得第五届东南劲爆音乐榜劲爆十大金曲 [1]  、2007百度娱乐沸点年度颁奖礼“最热门MV”和“十大金曲” [2]  、2007年度9+2音乐先锋榜“内地十大先锋金曲“ [3]  等奖项。并入选大型音乐纪录片《岁月留声·中国唱片60年见证》 [4]  和日本书籍《通过唱歌记住中国话》');
INSERT INTO `singer` VALUES ('3', '艾辰', '男', '/singer/艾辰.jpg', '1995-07-11', '艾辰1995年7月11日出生于江苏省连云港市，中国内地男歌手。');


create table album(
-- 专辑 id
	album_id int primary key auto_increment,
-- 专辑 
	album varchar(50) not null,
-- 歌手id
	singer_id int not null,
-- 	图片
    album_img varchar(300) not null,
-- 专辑 简介
	introduction varchar(300) not null,
-- 专辑 发行机构
	company varchar(30) not null,
-- 发布时间 
	time date not null
);
INSERT INTO `album` VALUES ('1', '牵丝戏', '1', '/album/牵丝戏.jpg', '《牵丝戏》是由Vagary填词，银临、Aki阿杰演唱的古风单曲，于2015年推出。歌曲通过描绘傀儡翁与牵扯一生的傀儡之间的相伴、别离，来诉说一段牵恋。', '极韵文化', '2019-03-28');
INSERT INTO `album` VALUES ('2', '宇光十色', '2', '/album/该死的温柔.jfif', '该歌曲获得第五届东南劲爆音乐榜劲爆十大金曲 [1]  、2007百度娱乐沸点年度颁奖礼“最热门MV”和“十大金曲” [2]  、2007年度9+2音乐先锋榜“内地十大先锋金曲“ [3]  等奖项。并入选大型音乐纪录片《岁月留声·中国唱片60年见证》 [4]  和日本书籍《通过唱歌记住中国话》', 'xxx', '2007-01-01');
INSERT INTO `album` VALUES ('3', '干物女', '3', '/album/干物女.jfif', '干物女是指放弃恋爱，以对自己而言懒散舒适的生活方式生活的女性。来源于火浦智漫画《小萤的青春》（《萤之光》）的女主角「雨宫萤」的单身生活。（包括宫 萤在家，喜欢独自看漫画，饮可乐；假日好睡，幸福写意）。后被用来指无意恋爱的二十、三十岁女性。', 'xxx传媒', '2018-11-01');
-- 歌曲 
create table song(
-- 歌曲id 
	song_id int primary key auto_increment,
-- 歌曲
	song varchar(50) not null,
-- 歌手id
	singer_id int not null,
-- 播放次数 
	play_count int not null  default 0,
-- 下载次数
	download_count int not null default 0,
-- 歌曲虚拟路径
	url varchar(500) not null,
-- 语种
	language varchar(30) not null,
-- 专辑id 
	album_id int not null,
-- 发布时间
	time date not null
);
INSERT INTO `song` VALUES ('1', '牵丝戏', '1', '77', '0', '/songs/牵丝戏.mp3', '汉语古风', '1', '2015-01-01');
INSERT INTO `song` VALUES ('2', '该死的温柔', '2', '4', '0', '/songs/该死的温柔.mp3', '汉语流行', '2', '2015-02-18');
INSERT INTO `song` VALUES ('3', '干物女', '3', '18', '0', '/songs/干物女.mp3', '汉语古风', '3', '2021-06-01');
INSERT INTO `song` VALUES ('4', '江南', '3', '9', '0', '/songs/江南.mp3', '汉语古风', '3', '2021-06-08');
INSERT INTO `song` VALUES ('5', '刚刚好', '3', '21', '0', '/songs/刚刚好.mp3', '汉语流行', '3', '2021-06-01');


-- 用户收藏歌曲 
  create table userSongs(
-- 用户收藏歌曲id
	user_song_id int primary key auto_increment,
-- 用户id
	user_id int not null,
-- 歌曲id
	song_id int not null
);

-- 用户收藏歌手 
  create table userSingers(
-- 用户收藏歌手id
	user_singer_id int primary key auto_increment,
-- 用户id
	user_id int not null,
-- 歌手id
	singer_id int not null
);


-- 用户收藏歌单 
  create table userSongLists(
-- 用户收藏歌单id
	user_songlist_id int primary key auto_increment,
-- 用户id
	user_id int not null,
-- 歌单id
	song_list_id int not null
);

-- 用户收藏专辑
  create table userAlbums(
-- 用户收藏歌单id
	user_album_id int primary key auto_increment,
-- 用户id
	user_id int not null,
-- 专辑id
	album_id int not null
);


-- 歌单连接歌曲表 
create table songCollect(
-- id
	collect_id int primary key auto_increment,
-- 歌单id
	song_list_id int not null,
-- 歌曲id
	song_id int not null
);
INSERT INTO `songcollect` VALUES ('1', '1', '1');
INSERT INTO `songcollect` VALUES ('2', '1', '3');
INSERT INTO `songcollect` VALUES ('3', '2', '5');
INSERT INTO `songcollect` VALUES ('4', '2', '4');
INSERT INTO `songcollect` VALUES ('5', '2', '1');

-- 情感分类 
create table fenlei(
-- 分类 id
	fen_id int primary key auto_increment,
-- 分类 内容
	content varchar(10) not null
);
INSERT INTO `fenlei` VALUES ('1', '伤感');
INSERT INTO `fenlei` VALUES ('2', '快乐');
INSERT INTO `fenlei` VALUES ('3', '安静');
INSERT INTO `fenlei` VALUES ('4', '励志');
INSERT INTO `fenlei` VALUES ('5', '治愈');
INSERT INTO `fenlei` VALUES ('6', '思念');
INSERT INTO `fenlei` VALUES ('7', '古风');
INSERT INTO `fenlei` VALUES ('8', '爱情');
-- 歌单分类
create table classic(
	classic_id int primary key auto_increment,
-- 歌单id
	song_list_id int not null,	
-- 分类 id
	fen_id int not null
);
	
INSERT INTO `classic` VALUES ('1', '1', '1');
INSERT INTO `classic` VALUES ('2', '1', '7');
INSERT INTO `classic` VALUES ('3', '2', '8');
-- 网站留言
create table message(
-- 留言id
	message_id int primary key auto_increment,
-- 用户id 
	user_id int not null,
-- 留言内容
	content varchar(100) not null,
-- 	回复留言id 未回复时默认为0
	reply_user_id int not null default 0,
-- 留言时间
	time date not null
);




