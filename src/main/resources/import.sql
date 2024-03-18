INSERT INTO t_role (name) VALUES ('ROLE_USER');
INSERT INTO t_role (name) VALUES ('ROLE_DOCTOR');
INSERT INTO t_role (name) VALUES ('ROLE_ADMIN');



-- Админ: Пароль admin
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Слобожанин', 'Федор', 'Владимирович', 'vortexofnebula@mail.ru', '$2a$10$jRWvBoWkYZ2Oxq6VF6gv.uLctq5EhhuTrUnz9IanYWD/lc/xDNhRy', '89009696053', '0000 0000 0000 0000');

-- Доктор Петр Петров Петрович: Пароль doctor
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Петров', 'Петр', 'Петрович', 'mega_pixel_pro@mail.ru', '$2a$10$7U7tNQFCj7XnsbBfZiFhReUEhkKB2G/4k/wZdoANBMGVmQatFRx6q', '89009696053', '0000 0000 0000 0001');

-- Доктор Иван Иванович Иванов: Пароль doctor
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Иванов', 'Иван', 'Иванович', 'mega_pixel_pro@mail.ru', '$2a$10$7U7tNQFCj7XnsbBfZiFhReUEhkKB2G/4k/wZdoANBMGVmQatFRx6q', '89009696053', '0000 0000 0000 0002');

-- Доктор Федор Федорович Федоров: Пароль doctor
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Федоров', 'Федор', 'Федорович', 'mega_pixel_pro@mail.ru', '$2a$10$7U7tNQFCj7XnsbBfZiFhReUEhkKB2G/4k/wZdoANBMGVmQatFRx6q', '89009696053', '0000 0000 0000 0003');

-- Доктор Александр Иванович Петров: Пароль doctor
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Петров', 'Александр', 'Иванович', 'mega_pixel_pro@mail.ru', '$2a$10$7U7tNQFCj7XnsbBfZiFhReUEhkKB2G/4k/wZdoANBMGVmQatFRx6q', '89009696053', '0000 0000 0000 0004');

-- Пользователь Петр Федорович Иванов: Пароль user
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Иванов', 'Петр', 'Федорович', 'mega_pixel_pro@mail.ru', '$2a$10$MDmp5S6Tl3DjWoLtM1wMkuGau3B7sAoaNkmqTy/yu7UZzRWKcPIVC', '89009696053', '0000 0000 0000 0005');

-- Пользователь Федор Александрович Федоров: Пароль user
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Федоров', 'Федор', 'Александрович', 'mega_pixel_pro@mail.ru', '$2a$10$MDmp5S6Tl3DjWoLtM1wMkuGau3B7sAoaNkmqTy/yu7UZzRWKcPIVC', '89009696053', '0000 0000 0000 0006');

-- Пользователь Федор Петрович Александров: Пароль user
INSERT INTO t_user (date_of_birth, last_name, first_name, sur_name, mail, password, phone, username) VALUES ('2001-06-21 00:00:00', 'Александров', 'Федор', 'Петрович', 'mega_pixel_pro@mail.ru', '$2a$10$MDmp5S6Tl3DjWoLtM1wMkuGau3B7sAoaNkmqTy/yu7UZzRWKcPIVC', '89009696053', '0000 0000 0000 0007');



INSERT INTO t_user_roles(user_id, role_id) VALUES (1, 3), (2, 2), (3, 2), (4, 2), (5, 2), (6, 1), (7, 1), (8, 1);

INSERT INTO doctors(specialization, user_id) VALUES ('Окулист', 2), ('Терапевт', 3), ('Хирург', 4), ('Терапевт', 5);



INSERT INTO schedule(date, day_type, doctor_id) VALUES ('2024-03-28', 'WORK', 1);

INSERT INTO schedule(date, day_type, doctor_id) VALUES ('2024-03-28', 'WORK', 2);

INSERT INTO schedule(date, day_type, doctor_id) VALUES ('2024-03-28', 'WORK', 3);

INSERT INTO schedule(date, day_type, doctor_id) VALUES ('2024-03-28', 'WORK', 4);



INSERT INTO public.ticket("time", schedule_id) VALUES ('08:30:00', 1);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:00:00', 1);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:30:00', 1);

INSERT INTO public.ticket("time", schedule_id) VALUES ('08:30:00', 2);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:00:00', 2);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:30:00', 2);

INSERT INTO public.ticket("time", schedule_id) VALUES ('08:30:00', 3);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:00:00', 3);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:30:00', 3);

INSERT INTO public.ticket("time", schedule_id) VALUES ('08:30:00', 4);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:00:00', 4);
INSERT INTO public.ticket("time", schedule_id) VALUES ('09:30:00', 4);











