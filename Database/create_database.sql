

CREATE TABLE leagues (league_id NUMBER, name VARCHAR(20));
CREATE TABLE teams (team_id NUMBER, name VARCHAR(20), league_id NUMBER);
CREATE TABLE players (player_id NUMBER, team_id NUMBER, name VARCHAR(20), surname VARCHAR(20), position VARCHAR(20));
CREATE TABLE matchesall (fixture_id NUMBER, team_home_id NUMBER, team_away_id NUMBER, gameweek_nr NUMBER, fixture_date DATE, fixture_hour NUMBER, referee_id NUMBER, league_id NUMBER);
CREATE TABLE referees (referee_id NUMBER, name VARCHAR(20), surname VARCHAR(20));
CREATE TABLE goals (fixture_id NUMBER, scorer_id NUMBER, assist_id NUMBER, goal_minute NUMBER);
CREATE TABLE yellow_cards (fixture_id NUMBER, player_id NUMBER, ycard_minute NUMBER);
CREATE TABLE red_cards (fixture_id NUMBER, player_id NUMBER, rcard_minute NUMBER);
CREATE TABLE app_users (user_id NUMBER, user_name VARCHAR(30), password VARCHAR(30));
CREATE TABLE users_matches (user_name VARCHAR(30), fixture_id NUMBER);


INSERT INTO leagues VALUES (1, 'LaLiga');


INSERT INTO teams VALUES (1, 'Alaves', 1);
INSERT INTO teams VALUES (2, 'Athletic', 1);
INSERT INTO teams VALUES (3, 'Atletico', 1);
INSERT INTO teams VALUES (4, 'Barcelona', 1);
INSERT INTO teams VALUES (5, 'Betis', 1);
INSERT INTO teams VALUES (6, 'Cadiz', 1);
INSERT INTO teams VALUES (7, 'Celta Vigo', 1);
INSERT INTO teams VALUES (8, 'Eibar', 1);
INSERT INTO teams VALUES (9, 'Elche', 1);
INSERT INTO teams VALUES (10, 'Getafe', 1);
INSERT INTO teams VALUES (11, 'Granada', 1);
INSERT INTO teams VALUES (12, 'Huesca', 1);
INSERT INTO teams VALUES (13, 'Levante', 1);
INSERT INTO teams VALUES (14, 'Osasuna', 1);
INSERT INTO teams VALUES (15, 'Real Madrid', 1);
INSERT INTO teams VALUES (16, 'Real Sociedad', 1);
INSERT INTO teams VALUES (17, 'Sevilla', 1);
INSERT INTO teams VALUES (18, 'Valencia', 1);
INSERT INTO teams VALUES (19, 'Valladolid', 1);
INSERT INTO teams VALUES (20, 'Villarreal', 1);


CREATE OR REPLACE TRIGGER check_if_new_match
BEFORE INSERT or UPDATE ON users_matches FOR EACH ROW
DECLARE 
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM users_matches
    WHERE fixture_id = :new.fixture_id AND user_name = :new.user_name;
    IF v_count > 0 THEN
        raise_application_error(-20001, 'Mecz znajduje sie juz w tabeli ulubionych');
    END IF;
    
END;
/



CREATE OR REPLACE TRIGGER check_if_new_user
BEFORE INSERT or UPDATE ON app_users FOR EACH ROW
DECLARE 
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM app_users
    WHERE user_name = :new.user_name;
    IF v_count > 0 THEN
        raise_application_error(-20002, 'Uzytkownik o podanym loginie juz istnieje');
    END IF;
    
END;
/


CREATE OR REPLACE FUNCTION random_name
RETURN VARCHAR
AS
    player_name VARCHAR(20);
BEGIN
    CASE floor(dbms_random.value(1,16))
        WHEN 1 THEN player_name := 'Mateo';
        WHEN 2 THEN player_name := 'Santiago';
        WHEN 3 THEN player_name := 'Diego';
        WHEN 4 THEN player_name := 'Thiago';
        WHEN 5 THEN player_name := 'Martin';
        WHEN 6 THEN player_name := 'Alejandro';
        WHEN 7 THEN player_name := 'Leonardo';
        WHEN 8 THEN player_name := 'Emiliano';
        WHEN 9 THEN player_name := 'Felipe';
        WHEN 10 THEN player_name := 'Adrian';
        WHEN 11 THEN player_name := 'Angel';
        WHEN 12 THEN player_name := 'Benjamin';
        WHEN 13 THEN player_name := 'Gabriel';
        WHEN 14 THEN player_name := 'Emmanuel';
        WHEN 15 THEN player_name := 'Antonio';
    END CASE;
    dbms_output.put_line(player_name);
    RETURN player_name;
END;
/


CREATE OR REPLACE FUNCTION random_surname
RETURN VARCHAR
AS
    player_surname VARCHAR(20);
BEGIN
    CASE floor(dbms_random.value(1,16))
        WHEN 1 THEN player_surname := 'Garcia';
        WHEN 2 THEN player_surname := 'Fernandez';
        WHEN 3 THEN player_surname := 'Gonzalez';
        WHEN 4 THEN player_surname := 'Rodriguez';
        WHEN 5 THEN player_surname := 'Lopez';
        WHEN 6 THEN player_surname := 'Martinez';
        WHEN 7 THEN player_surname := 'Sanchez';
        WHEN 8 THEN player_surname := 'Perez';
        WHEN 9 THEN player_surname := 'Gomez';
        WHEN 10 THEN player_surname := 'Ruiz';
        WHEN 11 THEN player_surname := 'Hernandez';
        WHEN 12 THEN player_surname := 'Jimenez';
        WHEN 13 THEN player_surname := 'Alvarez';
        WHEN 14 THEN player_surname := 'Alonso';
        WHEN 15 THEN player_surname := 'Gutierrez';
    END CASE;
    dbms_output.put_line(player_surname);
    RETURN player_surname;
END;
/


CREATE OR REPLACE FUNCTION get_position(player_id NUMBER)
RETURN VARCHAR
AS
    c_players_per_team NUMBER := 23;
    v_position VARCHAR(20);
    v_variable NUMBER;
BEGIN
    v_variable := MOD(player_id, c_players_per_team);
    CASE 
        WHEN v_variable >= 1 AND v_variable <= 2 THEN v_position := 'Goalkeeper';
        WHEN v_variable >= 3 AND v_variable <= 10 THEN v_position := 'Defender';
        WHEN v_variable >= 11 AND v_variable <= 18 THEN v_position := 'Midfielder';
        ELSE v_position := 'Forward';
    END CASE;
    RETURN v_position;
END;
/


CREATE OR REPLACE PROCEDURE generate_players
AS
    v_counter_teams NUMBER := 0;
    v_counter_players NUMBER := 0;
    c_league_size NUMBER:= 20;
    c_players_per_team NUMBER := 23;
    v_player_id NUMBER := 1;
BEGIN
    LOOP
        LOOP
            dbms_output.put_line(v_player_id);
            INSERT INTO players
            VALUES (v_player_id, 
            v_counter_teams + 1,
            random_name,
            random_surname,
            get_position(v_player_id)
            );
            v_player_id := v_player_id + 1;
            v_counter_players := v_counter_players + 1;
            
            EXIT WHEN v_counter_players >= c_players_per_team;
            
        END LOOP;
        v_counter_teams := v_counter_teams + 1;
        v_counter_players := 0;
        EXIT WHEN v_counter_teams >= c_league_size;
    END LOOP;
END;
/


CREATE OR REPLACE FUNCTION gameweek_date (gameweek_number NUMBER)
RETURN DATE
AS
    v_gam_date DATE := TO_DATE('2021/01/02');
BEGIN
    v_gam_date := v_gam_date + 7 * (gameweek_number - 1);
    RETURN v_gam_date;
END;
/

CREATE OR REPLACE FUNCTION match_hour (fixture_id NUMBER)
RETURN NUMBER
AS
    v_variable NUMBER;
    v_hour NUMBER := 0;
BEGIN
    v_variable := MOD(fixture_id - 1, 10);
    v_variable := v_variable + 1;
    
    CASE
        WHEN v_variable = 1 THEN v_hour := 10;
        WHEN v_variable = 2 THEN v_hour := 10;
        WHEN v_variable = 3 THEN v_hour := 12;
        WHEN v_variable = 4 THEN v_hour := 12;
        WHEN v_variable = 5 THEN v_hour := 14;
        WHEN v_variable = 6 THEN v_hour := 14;
        WHEN v_variable = 7 THEN v_hour := 16;
        WHEN v_variable = 8 THEN v_hour := 16;
        WHEN v_variable = 9 THEN v_hour := 18;
        WHEN v_variable = 10 THEN v_hour := 18;
    END CASE;
    
    return v_hour;
END;
/


CREATE OR REPLACE PROCEDURE generate_match(fixture_id NUMBER, counter NUMBER, gameweek_number NUMBER, league_size NUMBER)
AS
    team_home_id NUMBER;
    team_away_id NUMBER;
BEGIN
    team_home_id := MOD(3 * league_size - 2 - gameweek_number - counter, league_size - 1) + 2;
    team_away_id := MOD(2 * league_size - 1 - gameweek_number + counter, league_size - 1) + 2;
    IF counter = 0 THEN
        team_home_id := 1;
    END IF;
    INSERT INTO matchesall
    VALUES (fixture_id, 
    team_home_id,
    team_away_id,
    gameweek_number,
    (SELECT gameweek_date(gameweek_number) from dual),
    (SELECT match_hour(fixture_id) from dual),
    floor(dbms_random.value(1,21)),
    1);
END;
/


CREATE OR REPLACE PROCEDURE generate_matchesall
AS
    v_gameweek_number NUMBER := 1;
    v_fixture_id NUMBER := 1;
    c_league_size NUMBER:= 20;
    v_counter NUMBER := 0;
BEGIN
    LOOP
        LOOP
            generate_match(v_fixture_id, v_counter, v_gameweek_number, c_league_size);
            v_counter := v_counter + 1;
            v_fixture_id := v_fixture_id + 1;
            EXIT WHEN v_counter >= c_league_size / 2;
        END LOOP;
        v_gameweek_number := v_gameweek_number + 1;
        v_counter := 0;
        EXIT WHEN v_gameweek_number > 2 * c_league_size - 2;
    END LOOP;
END;
/


CREATE OR REPLACE PROCEDURE generate_referees
AS
    v_referee_id NUMBER := 1;
    c_number_of_referees NUMBER:= 20;
BEGIN
    LOOP
        INSERT INTO referees
        VALUES (v_referee_id, 
        (SELECT random_name from dual),
        (SELECT random_surname from dual));
        v_referee_id := v_referee_id + 1;
        EXIT WHEN v_referee_id > c_number_of_referees;
    END LOOP;
END;
/

CREATE OR REPLACE FUNCTION number_of_goals
--can also represent number of yellow cards per match per team
RETURN NUMBER
AS
    v_variable NUMBER;
    goals NUMBER;
BEGIN
    v_variable := dbms_random.value(0,100);
    CASE 
        WHEN v_variable >= 0 AND v_variable < 20 THEN goals := 0;
        WHEN v_variable >= 21 AND v_variable < 44 THEN goals := 1;
        WHEN v_variable >= 44 AND v_variable < 65 THEN goals := 2;
        WHEN v_variable >= 65 AND v_variable < 83 THEN goals := 3;
        WHEN v_variable >= 83 AND v_variable < 95 THEN goals := 4;
        WHEN v_variable >= 95 AND v_variable < 99 THEN goals := 5;
        ELSE goals := 6;
    END CASE;
    RETURN goals;
END;
/


CREATE OR REPLACE FUNCTION who_scored
RETURN NUMBER
AS
    v_variable NUMBER;
    player_id NUMBER;
BEGIN
    v_variable := dbms_random.value(0,100);
    CASE 
        WHEN v_variable >= 0 AND v_variable < 1 THEN player_id := 3;
        WHEN v_variable >= 1 AND v_variable < 2 THEN player_id := 4;
        WHEN v_variable >= 2 AND v_variable < 3 THEN player_id := 5;
        WHEN v_variable >= 3 AND v_variable < 4 THEN player_id := 6;
        WHEN v_variable >= 4 AND v_variable < 5 THEN player_id := 7;
        WHEN v_variable >= 5 AND v_variable < 6 THEN player_id := 8;
        WHEN v_variable >= 6 AND v_variable < 7 THEN player_id := 9;
        WHEN v_variable >= 7 AND v_variable < 8 THEN player_id := 10;
        WHEN v_variable >= 8 AND v_variable < 16 THEN player_id := 11;
        WHEN v_variable >= 16 AND v_variable < 20 THEN player_id := 12;
        WHEN v_variable >= 20 AND v_variable < 24 THEN player_id := 13;
        WHEN v_variable >= 24 AND v_variable < 28 THEN player_id := 14;
        WHEN v_variable >= 28 AND v_variable < 32 THEN player_id := 15;
        WHEN v_variable >= 32 AND v_variable < 36 THEN player_id := 16;
        WHEN v_variable >= 36 AND v_variable < 40 THEN player_id := 17;
        WHEN v_variable >= 40 AND v_variable < 44 THEN player_id := 18;
        WHEN v_variable >= 44 AND v_variable < 55 THEN player_id := 19;
        WHEN v_variable >= 55 AND v_variable < 66 THEN player_id := 20;
        WHEN v_variable >= 66 AND v_variable < 77 THEN player_id := 21;
        WHEN v_variable >= 77 AND v_variable < 88 THEN player_id := 22;
        WHEN v_variable >= 88 AND v_variable <= 100 THEN player_id := 23;
    END CASE;
    RETURN player_id;
END;
/


CREATE OR REPLACE FUNCTION who_assisted
RETURN NUMBER
AS
    v_variable NUMBER;
    player_id NUMBER;
BEGIN
    v_variable := dbms_random.value(0,100);
    CASE 
        WHEN v_variable >= 0 AND v_variable < 1 THEN player_id := 3;
        WHEN v_variable >= 1 AND v_variable < 2 THEN player_id := 4;
        WHEN v_variable >= 2 AND v_variable < 3 THEN player_id := 5;
        WHEN v_variable >= 3 AND v_variable < 4 THEN player_id := 6;
        WHEN v_variable >= 4 AND v_variable < 5 THEN player_id := 7;
        WHEN v_variable >= 5 AND v_variable < 6 THEN player_id := 8;
        WHEN v_variable >= 6 AND v_variable < 7 THEN player_id := 9;
        WHEN v_variable >= 7 AND v_variable < 8 THEN player_id := 10;
        WHEN v_variable >= 8 AND v_variable < 16 THEN player_id := 11;
        WHEN v_variable >= 16 AND v_variable < 24 THEN player_id := 12;
        WHEN v_variable >= 24 AND v_variable < 32 THEN player_id := 13;
        WHEN v_variable >= 32 AND v_variable < 40 THEN player_id := 14;
        WHEN v_variable >= 40 AND v_variable < 48 THEN player_id := 15;
        WHEN v_variable >= 48 AND v_variable < 56 THEN player_id := 16;
        WHEN v_variable >= 56 AND v_variable < 64 THEN player_id := 17;
        WHEN v_variable >= 64 AND v_variable < 72 THEN player_id := 18;
        WHEN v_variable >= 72 AND v_variable < 78 THEN player_id := 19;
        WHEN v_variable >= 78 AND v_variable < 84 THEN player_id := 20;
        WHEN v_variable >= 84 AND v_variable < 90 THEN player_id := 21;
        WHEN v_variable >= 90 AND v_variable < 95 THEN player_id := 22;
        WHEN v_variable >= 95 AND v_variable <= 100 THEN player_id := 23;
    END CASE;
    RETURN player_id;
END;
/



CREATE OR REPLACE FUNCTION who_yellow_card
RETURN NUMBER
AS
    v_variable NUMBER;
    player_id NUMBER;
BEGIN
    v_variable := dbms_random.value(0,100);
    CASE
        WHEN v_variable >= 0 AND v_variable < 2 THEN player_id := 1;
        WHEN v_variable >= 2 AND v_variable < 4 THEN player_id := 2;
        WHEN v_variable >= 4 AND v_variable < 10 THEN player_id := 3;
        WHEN v_variable >= 10 AND v_variable < 16 THEN player_id := 4;
        WHEN v_variable >= 16 AND v_variable < 22 THEN player_id := 5;
        WHEN v_variable >= 22 AND v_variable < 28 THEN player_id := 6;
        WHEN v_variable >= 28 AND v_variable < 34 THEN player_id := 7;
        WHEN v_variable >= 34 AND v_variable < 40 THEN player_id := 8;
        WHEN v_variable >= 40 AND v_variable < 46 THEN player_id := 9;
        WHEN v_variable >= 46 AND v_variable < 52 THEN player_id := 10;
        WHEN v_variable >= 52 AND v_variable < 56 THEN player_id := 11;
        WHEN v_variable >= 56 AND v_variable < 60 THEN player_id := 12;
        WHEN v_variable >= 60 AND v_variable < 64 THEN player_id := 13;
        WHEN v_variable >= 64 AND v_variable < 68 THEN player_id := 14;
        WHEN v_variable >= 68 AND v_variable < 72 THEN player_id := 15;
        WHEN v_variable >= 72 AND v_variable < 76 THEN player_id := 16;
        WHEN v_variable >= 76 AND v_variable < 80 THEN player_id := 17;
        WHEN v_variable >= 80 AND v_variable < 84 THEN player_id := 18;
        WHEN v_variable >= 84 AND v_variable < 87 THEN player_id := 19;
        WHEN v_variable >= 87 AND v_variable < 90 THEN player_id := 20;
        WHEN v_variable >= 90 AND v_variable < 93 THEN player_id := 21;
        WHEN v_variable >= 93 AND v_variable < 96 THEN player_id := 22;
        WHEN v_variable >= 96 AND v_variable <= 100 THEN player_id := 23;
    END CASE;
    RETURN player_id;
END;
/



CREATE OR REPLACE PROCEDURE goals_for_fixture(fix_id NUMBER)
AS
    team_h_id NUMBER;
    team_a_id NUMBER;
    home_goals NUMBER;
    away_goals NUMBER;
    home_counter NUMBER := 0;
    away_counter NUMBER := 0;
    scorer_id NUMBER;
    assist_id NUMBER;
    insert_scorer NUMBER;
    insert_assist NUMBER;
    c_players_per_team NUMBER := 23;
BEGIN
    SELECT team_home_id into team_h_id from matchesall WHERE fixture_id = fix_id;
    SELECT team_away_id into team_a_id from matchesall WHERE fixture_id = fix_id;
    SELECT number_of_goals into home_goals from dual;
    SELECT number_of_goals into away_goals from dual;
    
    --home team
    LOOP
        EXIT when home_counter >= home_goals;
        SELECT who_scored into scorer_id from dual;
        SELECT who_assisted into assist_id from dual;
        IF assist_id = scorer_id THEN
            assist_id := 1;
        END IF;
        
        insert_scorer := scorer_id + ((team_h_id - 1) * c_players_per_team);
        insert_assist := assist_id + ((team_h_id - 1) * c_players_per_team);
        
        INSERT INTO goals VALUES (
            fix_id,
            insert_scorer,
            insert_assist,
            floor(dbms_random.value(1, 91)) --minute
        );
        
        home_counter := home_counter + 1;
    END LOOP;
    
    --away team
    LOOP
        EXIT when away_counter >= away_goals;
        SELECT who_scored into scorer_id from dual;
        SELECT who_assisted into assist_id from dual;
        IF assist_id = scorer_id THEN
            assist_id := 1;
        END IF;
        
        insert_scorer := scorer_id + ((team_a_id - 1) * c_players_per_team);
        insert_assist := assist_id + ((team_a_id - 1) * c_players_per_team);
        
        INSERT INTO goals VALUES (
            fix_id,
            insert_scorer,
            insert_assist,
            floor(dbms_random.value(1, 91)) --minute
        );
        
        away_counter := away_counter + 1;
    END LOOP;
END;
/



CREATE OR REPLACE PROCEDURE generate_goals
AS
    v_fixture_id NUMBER := 1;
    c_total_fixtures NUMBER;
BEGIN
    SELECT max(fixture_id) into c_total_fixtures from matchesall;
    LOOP
        goals_for_fixture(v_fixture_id);
        v_fixture_id := v_fixture_id + 1;
        EXIT WHEN v_fixture_id > c_total_fixtures;
    END LOOP;
END;
/



CREATE OR REPLACE PROCEDURE yellow_cards_for_fixture(fix_id NUMBER)
AS
    team_h_id NUMBER;
    team_a_id NUMBER;
    home_goals NUMBER;
    away_goals NUMBER;
    home_counter NUMBER := 0;
    away_counter NUMBER := 0;
    player_id NUMBER;
    insert_player NUMBER;
    c_players_per_team NUMBER := 23;
    how_many_yellows NUMBER;
BEGIN
    SELECT team_home_id into team_h_id from matchesall WHERE fixture_id = fix_id;
    SELECT team_away_id into team_a_id from matchesall WHERE fixture_id = fix_id;
    SELECT number_of_goals into home_goals from dual; --also represents number of yellow cards
    SELECT number_of_goals into away_goals from dual;
    
    --home team
    LOOP
        EXIT when home_counter >= home_goals;
        SELECT who_yellow_card into player_id from dual;
        
        insert_player := player_id + ((team_h_id - 1) * c_players_per_team);
        
        --checks if that is first or second yellow card for that player
        
        SELECT COUNT(*) 
        INTO how_many_yellows 
        FROM yellow_cards 
        WHERE fixture_id = fix_id AND player_id = insert_player;
        
        --first yellow card
        IF how_many_yellows = 0 THEN
            INSERT INTO yellow_cards VALUES (
                fix_id,
                insert_player,
                floor(dbms_random.value(1, 91)) --minute
            );
        END IF;
        
        --second yellow card
        IF how_many_yellows = 1 THEN
            INSERT INTO red_cards VALUES (
                fix_id,
                insert_player,
                floor(dbms_random.value(1, 91)) --minute
            );
        END IF;
        
        
        home_counter := home_counter + 1;
    END LOOP;
    
    --away team
    LOOP
        EXIT when away_counter >= away_goals;
        SELECT who_yellow_card into player_id from dual;
        
        insert_player := player_id + ((team_a_id - 1) * c_players_per_team);
        
         --checks if that is first or second yellow card for that player
        
        SELECT COUNT(*) 
        INTO how_many_yellows 
        FROM yellow_cards 
        WHERE fixture_id = fix_id AND player_id = insert_player;
        
        --first yellow card
        IF how_many_yellows = 0 THEN
            INSERT INTO yellow_cards VALUES (
                fix_id,
                insert_player,
                floor(dbms_random.value(1, 91)) --minute
            );
        END IF;
        
        --second yellow card
        IF how_many_yellows = 1 THEN
            INSERT INTO red_cards VALUES (
                fix_id,
                insert_player,
                floor(dbms_random.value(1, 91)) --minute
            );
        END IF;
        
        away_counter := away_counter + 1;
    END LOOP;
END;
/




CREATE OR REPLACE PROCEDURE generate_yellow_cards
AS
    v_fixture_id NUMBER := 1;
    c_total_fixtures NUMBER;
BEGIN
    SELECT max(fixture_id) into c_total_fixtures from matchesall;
    LOOP
        yellow_cards_for_fixture(v_fixture_id);
        v_fixture_id := v_fixture_id + 1;
        EXIT WHEN v_fixture_id > c_total_fixtures;
    END LOOP;
END;
/



exec generate_players;
exec generate_matchesall;
exec generate_referees;
exec generate_goals;
exec generate_yellow_cards;




--home score and away score
    ALTER TABLE matchesall
    ADD home_score NUMBER;
    
    ALTER TABLE matchesall
    ADD away_score NUMBER;



CREATE OR REPLACE PROCEDURE goals_matchesall_table
-- add home score and away score in matchesall table
AS
    CURSOR cr1 IS 
    SELECT *
    FROM matchesall;
    v_match_id NUMBER;
    v_home_id NUMBER;
    v_away_id NUMBER;
    match_row matchesall%ROWTYPE;
    v_home_score NUMBER;
    v_away_score NUMBER;
    
BEGIN
    OPEN cr1;
    LOOP
        EXIT WHEN cr1%NOTFOUND;
        FETCH cr1 INTO match_row;
        v_home_id := match_row.team_home_id;
        v_away_id := match_row.team_away_id;
        
        --home
        SELECT COUNT(*) 
        INTO v_home_score
        FROM goals g
        INNER JOIN players p
        ON g.scorer_id = p.player_id
        WHERE team_id = v_home_id
        AND fixture_id = match_row.fixture_id;
        
        UPDATE matchesall 
        SET home_score = v_home_score
        WHERE team_home_id = v_home_id;
        
        --away
        SELECT COUNT(*) 
        INTO v_away_score
        FROM goals g
        INNER JOIN players p
        ON g.scorer_id = p.player_id
        WHERE team_id = v_away_id
        AND fixture_id = match_row.fixture_id;
        
        UPDATE matchesall 
        SET away_score = v_away_score
        WHERE team_away_id = v_away_id;
    END LOOP;
    CLOSE cr1;
END;
/



exec goals_matchesall_table;

--additional example match
INSERT INTO matchesall VALUES (381, 1, 2, 21, date '2021-05-21', 11, 1, 1, 2, 1);
INSERT INTO goals VALUES (381, 2, 3, 9);
INSERT INTO goals VALUES (381, 3, 4, 10);
INSERT INTO goals VALUES (381, 6, 7, 12);
INSERT INTO goals VALUES (381, 7, 8, 14);
INSERT INTO goals VALUES (381, 9, 11, 16);
INSERT INTO goals VALUES (381, 3, 4, 18);
INSERT INTO goals VALUES (381, 3, 4, 20);
INSERT INTO goals VALUES (381, 5, 7, 22);
INSERT INTO goals VALUES (381, 6, 7, 24);
INSERT INTO goals VALUES (381, 6, 7, 26);
INSERT INTO goals VALUES (381, 5, 6, 53);
INSERT INTO goals VALUES (381, 6, 7, 63);


