-- dept
TRUNCATE `dfw_m030m`;
INSERT INTO`dfw_m030m` (`use_flag`,`company_cd`,`dept_f`,`dept_name`) VALUES (1,'xee',1,'XEEX Project');

-- users
TRUNCATE `dfw_m050m`;
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','brcvn05@brycen.com.vn','brcvn05','Brycen VN','$2a$10$7DXfrmfWDo6ZnfbHGkxdK.StlnKY12y8oC1.LfZFUXGiUdUkbRvj.');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','tester@brycen.com.vn','tester','Tester User','$2a$10$KHB.YJ7rjTQ35yKvBlz7buj7uHtMgY3Vp7AFHW.5WcTGwgLTvZrcq');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','v_long@brycen.com.vn','v_long','Lê Văn Long','$2a$10$X0RkPVrqzfjj2Z6h3paZFuKyQDs2LaQ5bKa9WMzijAXcDy8zig/fK');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','lb_mui@brycen.com.vn','lb_mui','Lê Bá Mùi','$2a$10$fzON5101U3EHPiiuue4Jnu6nc6tZ0LPXSFML1GPqDtIe3oBXildWi');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','tdq_vinh@brycen.com.vn','tdq_vinh','Trần Đoàn Quang Vinh','$2a$10$nU.q9Eru0c.YRXWnZzw/cOqjyLwrXzksAyJiTC9LbknYU86/2Nxqi');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','ph_cuong@brycen.com.vn','ph_cuong','Phạm Huy Cường','$2a$10$lF1v6cNlM.Ncp7P1xwkxOedZzWnHMO.4UR3uCkFugWKHsy0VUjU7e');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','nb_hung@brycen.com.vn','nb_hung','Nguyễn Bảo Hưng','$2a$10$u9V/leHMx5D9Gydn0JebHeUCQ5YQ7TbYtXMg0gXM1g/4YM5xkf6dm');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','a_vi@brycen.com.vn','a_vi','Phạm Ánh Vi','$2a$10$1B/o0ogb5JFq7hP2Qt.68.Nt1wFoOCWPeBXM2iBL7bjaVkp1hAL7e');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','lt_tung@brycen.com.vn','lt_tung','Lý Thanh Tùng','$2a$10$Dmz1uFMPWZRs4IrM7CAAW.kehKblbJm28GaO3Vv/wHWjdFasTjK2u');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','dv_khai@brycen.com.vn','dv_khai','Đoàn Văn Khai','$2a$10$1fSChLi2ltnfa2NVEi.dWe2ctqtDCpJb/hK4L7Xhy7pSBArFkxzBm');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','q_thinh@brycen.com.vn','q_thinh','Nguyễn Quốc Thịnh','$2a$10$zV5NF41LjOUYeqrAcUSUb.Hzb294hTKEll1tO7VCyWU9URPYXEhZC');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','mc_phuoc@brycen.com.vn','mc_phuoc','Mai Công Phước','$2a$10$34aRKNlaeK96TNGngpg.XuiZV1Q7HOoIjH11Odnl4hvMJ6tO0CuWG');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','tx_thanh@brycen.com.vn','tx_thanh','Trần Công Thành','$2a$10$QWLlrWgORMWZb7qA88Ey2upUQFUHrotVwTzxkUFRP/JXnWRx6PTaa');
INSERT INTO dfw_m050m(use_flag,active_flag,dept_cd,email,emp_cd,full_name,password)  VALUES('0','1','xeex','ng_duc@brycen.com.vn','ng_duc','Nguyễn Ngọc Đức','$2a$10$B2oxhcqLcMXfChyVcvLFJ.B6pXlgaVeGaeH9gKHVBl7/au7kqVdni');

-- chat group
TRUNCATE `chat_group`;
INSERT INTO chat_group(id, group_name)  VALUES(300001,'Backend');
INSERT INTO chat_group(id, group_name)  VALUES(300002,'Group Leader');
INSERT INTO chat_group(id, group_name)  VALUES(300003,'Frontends');
INSERT INTO chat_group(id, group_name)  VALUES(300004,'BrSE');
INSERT INTO chat_group(id, group_name)  VALUES(300005,'Al XEEX');

-- chat group members
TRUNCATE `chat_group`;
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300001,'q_thinh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300001,'brcvn05');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300001,'v_long');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300002,'lb_mui');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300002,'brcvn05');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300002,'tdq_vinh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'ng_duc');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'lt_tung');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'dv_khai');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'brcvn05');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'mc_phuoc');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'a_vi');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300003,'tx_thanh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300004,'brcvn05');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300004,'v_long');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300004,'ph_cuong');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'ng_duc');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'lb_mui');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'nb_hung');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'dv_khai');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'brcvn05');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'tester');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'mc_phuoc');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'v_long');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'tx_thanh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'q_thinh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'lt_tung');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'tdq_vinh');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'a_vi');
INSERT INTO chat_group_member(group_id,member_emp_cd) VALUES (300005,'ph_cuong');