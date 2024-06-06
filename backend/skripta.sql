INSERT INTO public."user"(
    id, city, company_info, email, first_name, last_name, occupation, password, state, is_verified, tel_number, username, role, is_first_login, penalty_points)
VALUES 
    (-1, 'City0', 'Company Info 0', 'email0@example.com', 'First Name0', 'Last Name0', 'Occupation0', 'password0', 'State0', 'true','1234567890', 'username0', 0, false, 0),
    (-2, 'City1', 'Company Info 1', 'email1@example.com', 'First Name1', 'Last Name1', 'Occupation1', 'password1', 'State1', 'true','1234567891', 'username1', 0, false, 0),
    (-3, 'City2', 'Company Info 2', 'email2@example.com', 'First Name2', 'Last Name2', 'Occupation2', 'password2', 'State2', 'true','1234567892', 'username2', 0, false, 0),
    (-4, 'City3', 'Company Info 3', 'email3@example.com', 'First Name3', 'Last Name3', 'Occupation3', 'password3', 'State3', 'true','1234567893', 'username3', 1, false, 0),
    (-5, 'City4', 'Company Info 4', 'email4@example.com', 'First Name4', 'Last Name4', 'Occupation4', 'password4', 'State4', 'true','1234567894', 'username4', 1, false, 0),
    (-6, 'City5', 'Company Info 5', 'email5@example.com', 'First Name5', 'Last Name5', 'Occupation5', 'password5', 'State5', 'true','1234567895', 'username5', 1, false, 0),
    (-7, 'City6', 'Company Info 6', 'email6@example.com', 'First Name6', 'Last Name6', 'Occupation6', 'password6', 'State6','true', '1234567896', 'username6', 2, false, 0),
    (-8, 'City7', 'Company Info 7', 'email7@example.com', 'First Name7', 'Last Name7', 'Occupation7', 'password7', 'State7', 'true','1234567897', 'username7', 2, false, 0),
    (-9, 'City8', 'Company Info 8', 'email8@example.com', 'First Name8', 'Last Name8', 'Occupation8', 'password8', 'State8', 'true','1234567898', 'username8', 2, false, 0),
    (-10, 'City10', 'Company Info 10', 'email10@example.com', 'First Name10', 'Last Name10', 'Occupation10', 'password10', 'State10', 'true',  '1234567890', 'username10', 2, false, 0),
    (-11, 'City11', 'Company Info 11', 'email11@example.com', 'First Name11', 'Last Name11', 'Occupation11', 'password11', 'State11', 'true','1234567891', 'username11', 2, false, 0),
    (-12, 'City12', 'Company Info 12', 'email12@example.com', 'First Name12', 'Last Name12', 'Occupation12', 'password12', 'State12','true', '1234567892', 'username12', 2, false, 0),
    (-13, 'City13', 'Company Info 13', 'email13@example.com', 'First Name13', 'Last Name13', 'Occupation13', 'password13', 'State13', 'true','1234567893', 'username13', 2, false, 0),
    (-14, 'City14', 'Company Info 14', 'email14@example.com', 'First Name14', 'Last Name14', 'Occupation14', 'password14', 'State14','true', '1234567894', 'username14', 2, false, 0),
    (-15, 'City15', 'Company Info 15', 'email15@example.com', 'First Name15', 'Last Name15', 'Occupation15', 'password15', 'State15','true', '1234567895', 'username15', 2, false, 0),
    (-16, 'City16', 'Company Info 16', 'email16@example.com', 'First Name16', 'Last Name16', 'Occupation16', 'password16', 'State16','true', '1234567896', 'username16', 2, false, 0),
    (-17, 'City17', 'Company Info 17', 'email17@example.com', 'First Name17', 'Last Name17', 'Occupation17', 'password17', 'State17','true', '1234567897', 'username17', 2, false, 0),
    (-18, 'City18', 'Company Info 18', 'email18@example.com', 'First Name18', 'Last Name18', 'Occupation18', 'password18', 'State18', 'true','1234567898', 'username81', 2, false, 0),
    (-20, 'City20', 'Company Info 10', 'email10@example.com', 'First Name10', 'Last Name10', 'Occupation10', 'password10', 'State10', 'true',  '1234567890', 'username20', 0, false, 0),
    (-21, 'City21', 'Company Info 11', 'email11@example.com', 'First Name11', 'Last Name11', 'Occupation11', 'password11', 'State11', 'false','1234567891', 'username21', 0, false, 0),
    (-22, 'City22', 'Company Info 12', 'email12@example.com', 'First Name12', 'Last Name12', 'Occupation12', 'password12', 'State12','false', '1234567892', 'username22', 0, false, 0),
    (-23, 'Grad', 'Company Info 13', 'example@example.com', 'First Name13', 'Last Name13', 'Occupation13', 'password13', 'State13', 'true','1234567893', 'username23', 0, false, 0),
    (-24, 'Grad', 'Company Info 14', 'example@example.com', 'First Name14', 'Last Name14', 'Occupation14', 'password14', 'State14','true', '1234567894', 'username24', 1, false, 0),
    (-25, 'Gradic', 'Company Info 15', 'example@example.com', 'First Name15', 'Last Name15', 'Occupation15', 'password15', 'State15','true', '1234567895', 'username25', 1, false, 0),
    (-26, 'Gradic', 'Company Info 16', 'mail@example.com', 'First Name16', 'Last Name16', 'Occupation16', 'password16', 'State16','false', '1234567896', 'username26', 1, false, 0),
    (-27, 'Gradic', 'Company Info 17', 'mail@example.com', 'First Name17', 'Last Name17', 'Occupation17', 'password17', 'State17','true', '1234567897', 'username77', 1, false, 0),
	(-28, 'Grad', 'Company Info 18', 'mail@example.com', 'First Name18', 'Last Name18', 'Occupation18', 'password18', 'State18', 'false','1234567898', 'username29', 2, false, 0);

INSERT INTO public.predefined_date (
    id, company_admin_id, date_time_in_ms, duration, is_free
) VALUES
    (-1, -7, 1707882800000, 60,'true'),  -- 8:00 AM
    (-2, -8, 1708882800000, 60,'true'),  -- 1:00 PM
    (-3, -9, 1709882800000, 60,'true'),  -- 4:00 PM
    (-4, -10, 1710882800000, 60,'true'), -- 3:00 PM
    (-5, -11, 1707882800000, 60,'true'), -- 10:00 AM
    (-6, -12, 1709882800000, 60,'true'), -- 1:00 PM (next day)
    (-7, -13, 1707882800000, 60,'true'), -- 4:00 PM (next day)
    (-8, -14, 1707882800000, 60,'true'), -- 7:00 AM (next day)
    (-9, -15, 1710882800000, 60,'true'), -- 10:00 AM (next day)
    (-10, -16, 1707882800000, 60,'true'),   -- 3:00 PM (next day)
    (-11, -7, 1707882800000, 30,'true'), 
    (-12, -8, 1707882800000, 30,'true'), 
    (-13, -9, 1707882800000, 30,'true');

INSERT INTO public.company(
	id, address, administrator_id, avg_grade, description, predefined_dates_id, name)
VALUES 
    (-1, '123 Main St', ARRAY[-9,-8, -7], 4.5, 'A great company', ARRAY[-3, -2, -1, -11, -12, -13], 'Company A'),
    (-2, '456 Oak St', ARRAY[-10, -14, -15, -28], 3.8, 'Providing quality services', ARRAY[-4, -8, -9], 'Company B'),
    (-3, '789 Pine St', ARRAY[-11, -16], 4.2, 'Innovative solutions', ARRAY[-5, -10], 'Company C'),
    (-4, '101 Cedar St', ARRAY[-12, -17], 4.1, 'Excellence in every aspect', ARRAY[-6], 'Company D'),
    (-5, '202 Maple St', ARRAY[-13, -18], 3.9, 'Customer satisfaction is our priority', ARRAY[-7], 'Company E');
	
INSERT INTO public.equipment (
    id,
    company_id,
    description,
    grade,
    name,
    quantity,
    type
) VALUES
    (-1, -1, 'Medical Equipment 1', 4.5, 'PulseMaster Pro', 10, 0),
    (-2, -2, 'Medical Equipment 2', 3.8, 'ScanFlex Elite', 15, 1),
    (-3, -3, 'Medical Equipment 3', 4.2, 'MediLift Ultra', 20, 2),
    (-4, -4, 'Medical Equipment 4', 3.5, 'SurgiTech Precision', 12, 3),
    (-5, -5, 'Medical Equipment 5', 4.0, 'VitaCare Xpress', 8, 0),
    (-6, -1, 'Medical Equipment 6', 3.9, 'FlexiScan Pro', 18, 1),
    (-7, -2, 'Medical Equipment 7', 4.1, 'GloboMed FlexiView', 25, 2),
    (-8, -3, 'Medical Equipment 8', 3.7, 'HealthMaster Precision', 14, 3),
    (-9, -4, 'Medical Equipment 9', 4.3, 'SonoCare Ultra', 22, 0),
    (-10, -5, 'Medical Equipment 10', 3.6, 'CareTech Insight', 16, 1);

INSERT INTO public.reserved_date(
	id, company_admin_id, company_id, date_time_inms, duration, equipments, is_picked_up, user_id,link_to_order, qr_code_status)
	VALUES 
        (-1, -10, -2, 1707130800000, 60, ARRAY[-1, -2], false, -1,'http://localhost:3000/qrcode-equipment/-1', 0),
        (-2, -11, -2, 1707562800000, 60, ARRAY[-5, -10], false, -1,'http://localhost:3000/qrcode-equipment/-2', 0),
        (-3, -12, -1, 1707564600000, 60, ARRAY[-6], false, -2,'http://localhost:3000/qrcode-equipment/-3', 0),
        (-4, -13, -2, 1707553800000, 60, ARRAY[-8, -9, -10], false, -1,'http://localhost:3000/qrcode-equipment/-4', 0),
        (-5, -15, -5, 1707467400000, 60, ARRAY[-1, -2, -9], false, -1,'http://localhost:3000/qrcode-equipment/-5', 0),
        (-6, -10, -2, 1707467400000, 60, ARRAY[-10], false, -2, 'http://localhost:3000/qrcode-equipment/-6', 0);
