USE cinema;

INSERT INTO users
VALUES (DEFAULT,'manager2@gmail.com','+3809999999','Менеджер','Просто менеджер','Manager',MD5('managerpass1'),'manager',0);


INSERT INTO users
VALUES (DEFAULT,'clientno1@gmail.com','+3801111111','Перший','Клієнт','ClientN1',MD5('clientpass1'),'client',0);


INSERT INTO users
VALUES (DEFAULT,'clientno2@gmail.com','+3802222222','Другий','Клієнт','ClientN2',MD5('clientpass2'),'client',0);

INSERT INTO users
VALUES (DEFAULT,'clientno3@gmail.com','+3803333333','Third','Client','ClientN3',MD5('clientpass3'),'client',0);

INSERT INTO users
VALUES (DEFAULT,'clientno4@gmail.com','+3804444444','Четвертий','Кліент','ClientN4',MD5('clientpass4'),'client',0);

INSERT INTO films
VALUES (DEFAULT, 'Morbius', 'Its morbin time!', 'Masterpiece', 'posterImages/Morbius_poster', 'Daniel Espinosa', 104,'oZ6iiRrz1SY');
INSERT INTO films
VALUES (DEFAULT, 'Minions: The Rise of Gru', 'Ласкаво просимо до світу, де живуть посіпаки. Ці невгамовні створіння не знають, що таке спокій. Вони не зупиняться, поки не знайдуть для себе геніального негідника, щоб виконувати усі його забаганки.', 'дитячий, комедія', 'posterImages/Minions_The_Rise_of_Gru_poster', 'Kyle Balda', 87,'6DxjJzmYsXo');
INSERT INTO films
VALUES (DEFAULT, 'Thor: Love and Thunder', 'У новому фільмі «Тор: Любов і грім» від Marvel Studios Бог грому (Кріс Гемсворт) вирушає на пошуки внутрішнього спокою. Проте галактичний убивця Ґорр, знаний як Убивця богів (Крістіан Бейл), прагне знищити усіх небожителів та перериває вихід Тора на пенсію. Щоб подолати загрозу, Бог грому звертається по допомогу до королеви Валькірії (Тесса Томпсон), Корґа (Тайка Вайтіті) та своєї колишньої дівчини Джейн Фостер (Наталі Портман). Остання, на подив Тора, тепер володіє його магічним молотом Мйолніром так само, як і Могутній Тор. Разом вони вирушають у жахливу космічну пригоду, щоб розкрити таємницю помсти Убивці богів і зупинити його, поки не стало надто пізно.', 'пригоди, фантастика, Marvel', 'posterImages/Thor_Love_and_Thunder_poster', 'Taika Waititi', 119,'Go8nTmfrQd8');
INSERT INTO films
VALUES (DEFAULT, 'Top Gun: Maverick', 'Капітан Піт «Меверік» Мітчел (Том Круз) досі звершує бойові вильоти. Він такий же вправний у небі, проте відмовляється від підвищень по службі. ', 'бойовик, драма', 'posterImages/Top_Gun_Maverick_poster', 'Joseph Kosinski', 139,'giXco2jaZ_4');
INSERT INTO films
VALUES (DEFAULT, 'The Black Phone', 'Події розгортаються наприкінці 1970-х років у невеличкому містечку у передмісті Колорадо. Невідомий викрав вже п’ятьох підлітків, а поліція досі не може вийти на слід злочинця. Черговою жертвою викрадача (Ітан Хоук) стає Фінні (Мейсон Темз). Це сором’язливий та добрий хлопчик. У школі над ним глузують. Вдома ж – вічно п’яний тато, який ніяк не може оговтатися після загибелі дружини. Опинившись у звуконепроникному підвалі, Фінні був впевнений, що він приречений. Аж поки на стіні не задзвонив старезний чорний телефон зі зрізаними дротами. У слухавці хлопець почув голоси попередніх жертв, які радили йому як потрібно поводитися.', 'хоррор', 'posterImages/The_Black_Phone_poster', 'Scott Derrickson', 104,'3eGP6im8AZA');
