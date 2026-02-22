-- User 프로필 이미지 업데이트 SQL (topics 이미지 재사용)

-- 게임 유저들
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/lol/1.jpg' WHERE account_id = 'lol_master';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/valorant/1.jpg' WHERE account_id = 'valorant_ace';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/genshin/1.jpg' WHERE account_id = 'genshin_fan';

-- 아이돌 팬 유저들
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/newjeans/1.jpg' WHERE account_id = 'newjeans_lover';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/ive/1.jpg' WHERE account_id = 'ive_stan';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/bts/1.jpg' WHERE account_id = 'bts_army';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/lesserafim/1.jpg' WHERE account_id = 'lesserafim_fearnot';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/aespa/1.jpg' WHERE account_id = 'aespa_my';

-- 애니메이션 팬 유저들
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/onepiece/1.jpg' WHERE account_id = 'onepiece_mania';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/naruto/1.jpg' WHERE account_id = 'naruto_ninja';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/demonslayer/1.jpg' WHERE account_id = 'demon_slayer';
UPDATE tbl_user SET profile = 'https://building-duck.s3.us-east-1.amazonaws.com/topics/jujutsukaisen/1.jpg' WHERE account_id = 'jujutsu_otaku';


https://building-duck.s3.us-east-1.amazonaws.com/topics/lol/2.jpg