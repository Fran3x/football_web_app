package projektPAP.Highscore.API;

import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import projektPAP.Highscore.Classes.Events.*;
import projektPAP.Highscore.Classes.Leagues.*;
import projektPAP.Highscore.Classes.People.Assister;
import projektPAP.Highscore.Classes.People.Player;
import projektPAP.Highscore.Classes.People.Referee;
import projektPAP.Highscore.Classes.People.Scorer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;

@Service
public class APIService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    Logger logger;

    public long getTeamId(long fixture_id, String mode){
        //away
        String sql = "SELECT team_away_id FROM matchesall WHERE fixture_id = ?";
        //home
        if (mode.equals("home")) {
            sql = "SELECT team_home_id FROM matchesall WHERE fixture_id = ?";
        }
        return jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Integer.class);
    }

    public Referee getReferee(long fixture_id){
        //referee_id
        String sql = "SELECT referee_id FROM matchesall WHERE fixture_id = ?";
        long referee_id = jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Integer.class);

        //actual referee object
        sql = "SELECT * FROM referees WHERE referee_id = ?";
        List<Referee> referee = jdbcTemplate.query(sql, new Object[]{referee_id},
                BeanPropertyRowMapper.newInstance(Referee.class));
        return referee.get(0);
    }

    public long getTeamScore(long fixture_id, long team_id, long minute){
        String sql = "SELECT COUNT(*) " +
                "FROM goals " +
                "INNER JOIN players " +
                "ON scorer_id = player_id " +
                "WHERE team_id = ? " +
                "AND fixture_id = ?" +
                "AND goal_minute <= ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{team_id, fixture_id, minute},
                Long.class);
    }

    public List <Goal> getGoals(long fixture_id, long minute){
        String sql = "SELECT * FROM goals WHERE fixture_id = ? AND goal_minute <= ?";
        List<Goal> goals = jdbcTemplate.query(sql, new Object[]{fixture_id, minute},
                BeanPropertyRowMapper.newInstance(Goal.class));
        return goals;
    }

    public List <YellowCard> getYellowCards(long fixture_id, long minute){
        String sql = "SELECT * FROM yellow_cards WHERE fixture_id = ? AND ycard_minute <= ?";
        List<YellowCard> yellow_cards = jdbcTemplate.query(sql, new Object[]{fixture_id, minute},
                BeanPropertyRowMapper.newInstance(YellowCard.class));
        return yellow_cards;
    }

    public List <RedCard> getRedCards(long fixture_id, long minute){
        String sql = "SELECT * FROM red_cards WHERE fixture_id = ? AND rcard_minute <= ?";
        List<RedCard> red_cards = jdbcTemplate.query(sql, new Object[]{fixture_id, minute},
                BeanPropertyRowMapper.newInstance(RedCard.class));
        return red_cards;
    }

    public String getTeamName(long team_id){
        String sql = "SELECT name FROM teams WHERE team_id = ?";
        String name = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                String.class);
        return name;
    }

    public String getTeamNameByPlayer(long player_id){
        String sql = "SELECT t.name FROM teams t INNER JOIN players USING (team_id) WHERE player_id = ?";
        String name = jdbcTemplate.queryForObject(sql, new Object[]{player_id},
                String.class);
        return name;
    }

    public String getPlayerName(long player_id){
        String sql = "SELECT name FROM players WHERE player_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{player_id},
                String.class);
    }

    public String getPlayerSurname(long player_id){
        String sql = "SELECT surname FROM players WHERE player_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{player_id},
                String.class);
    }

    public String getMatchDate(long fixture_id){
        String sql = "SELECT extract(year from fixture_date) FROM matchesall WHERE fixture_id = ?";
        long year = jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Long.class);
        sql = "SELECT extract(month from fixture_date) FROM matchesall WHERE fixture_id = ?";
        long month = jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Long.class);
        sql = "SELECT extract(day from fixture_date) FROM matchesall WHERE fixture_id = ?";
        long day = jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Long.class);

        String output = Long.toString(year) + "/" + Long.toString(month) + "/" + Long.toString(day);
        return output;
    }

    public long getMatchHour(long fixture_id){
        String sql = "SELECT fixture_hour FROM matchesall WHERE fixture_id = ?";
        long hour = jdbcTemplate.queryForObject(sql, new Object[]{fixture_id},
                Long.class);
        return hour;
    }


    public Player getPlayer(long player_id){
        String sql = "SELECT * FROM players WHERE player_id = ?";
        List <Player> new_player = jdbcTemplate.query(sql, new Object[]{player_id},
                BeanPropertyRowMapper.newInstance(Player.class));
        return new_player.get(0);
    }


    public long getTeamWins(long team_id){
        String sql = "SELECT COUNT (*) FROM matchesall WHERE ((team_home_id = ? AND home_score > away_score) OR (team_away_id = ? AND away_score > home_score)) AND fixture_date < SYSDATE";
        return jdbcTemplate.queryForObject(sql, new Object[]{team_id, team_id},
                Long.class);
    }

    public long getTeamLosses(long team_id){
        String sql = "SELECT COUNT (*) FROM matchesall WHERE ((team_home_id = ? AND home_score < away_score) OR (team_away_id = ? AND away_score < home_score)) AND fixture_date < SYSDATE";
        return jdbcTemplate.queryForObject(sql, new Object[]{team_id, team_id},
                Long.class);
    }

    public long getTeamDraws(long team_id){
        String sql = "SELECT COUNT (*) FROM matchesall WHERE (team_home_id = ? OR team_away_id = ?) AND away_score = home_score AND fixture_date < SYSDATE";
        return  jdbcTemplate.queryForObject(sql, new Object[]{team_id, team_id},
                Long.class);
    }

    public long howManyMatchesHome(long team_id){
        String sql = "SELECT COUNT(*) FROM matchesall WHERE team_home_id = ? AND fixture_date < SYSDATE";
        long how_many = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                Long.class);
        return how_many;
    }

    public long howManyMatchesAway(long team_id){
        String sql = "SELECT COUNT(*) FROM matchesall WHERE team_away_id = ? AND fixture_date < SYSDATE";
        return jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                Long.class);
    }

    public long getGoalsFor(long team_id){
        String sql = "SELECT SUM(home_score) FROM matchesall WHERE team_home_id = ? AND fixture_date < SYSDATE";
        long goals_for_home = 0;
        if (howManyMatchesHome(team_id) > 0) {
            goals_for_home = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                    Long.class);
        }

        long goals_for_away = 0;
        if (howManyMatchesAway(team_id) > 0){
            sql = "SELECT SUM(away_score) FROM matchesall WHERE team_away_id = ? AND fixture_date < SYSDATE";
            goals_for_away = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                    Long.class);
        }

        return goals_for_home + goals_for_away;
    }


    public long getGoalsAgainst(long team_id){
        String sql = "SELECT SUM(away_score) FROM matchesall WHERE team_home_id = ? AND fixture_date < SYSDATE";
        long goals_against_home = 0;
        if (howManyMatchesHome(team_id) > 0) {
            goals_against_home = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                    Long.class);
        }

        long goals_against_away = 0;
        if (howManyMatchesAway(team_id) > 0) {
            sql = "SELECT SUM(home_score) FROM matchesall WHERE team_away_id = ? AND fixture_date < SYSDATE";
            goals_against_away = jdbcTemplate.queryForObject(sql, new Object[]{team_id},
                    Long.class);
        }

        return goals_against_home + goals_against_away;
    }


    public List<Match> getMatchInfo(long fixture_id, long minute) {
        //date
        String date = getMatchDate(fixture_id);

        //hour
        long hour = getMatchHour(fixture_id);

        //home team id
        long home_team_id = getTeamId(fixture_id, "home");
        //logger.debug(String.valueOf(home_team_id));

        //away team id
        long away_team_id = getTeamId(fixture_id, "away");
        //logger.debug(String.valueOf(away_team_id));

        //home team name
        String home_team_name = getTeamName(home_team_id);

        //away team name
        String away_team_name = getTeamName(away_team_id);

        //home score
        long home_score = getTeamScore(fixture_id, home_team_id, minute);
        //logger.debug(String.valueOf(home_score));

        //away score
        long away_score = getTeamScore(fixture_id, away_team_id, minute);
        //logger.debug(String.valueOf(away_score));

        //referee
        Referee referee = getReferee(fixture_id);
        //logger.debug(String.valueOf(referee));

        //list of all match events
        List <MatchEvent> events = new ArrayList<>();

        //goals
        List <Goal> goals = getGoals(fixture_id, minute);
        //logger.debug(String.valueOf(goals));

        goals.forEach((temp) -> {
            String scorer_name = getPlayerName(temp.getScorer_id()),
            scorer_surname = getPlayerSurname(temp.getScorer_id()),
            assist_name = getPlayerName(temp.getAssist_id()),
            assist_surname = getPlayerSurname(temp.getAssist_id()),
            team_name = getTeamNameByPlayer(temp.getScorer_id());
            events.add(new MatchEvent(temp, team_name, scorer_name, scorer_surname, assist_name, assist_surname));
        });

        //yellow cards
        List <YellowCard> yellow_cards = getYellowCards(fixture_id, minute);
        //logger.debug(String.valueOf(yellow_cards));

        yellow_cards.forEach((temp) -> {
            String name = getPlayerName(temp.getPlayer_id()),
                    surname = getPlayerSurname(temp.getPlayer_id()),
                    team_name = getTeamNameByPlayer(temp.getPlayer_id());
            events.add(new MatchEvent(temp, team_name, name, surname));
        });

        //red cards
        List <RedCard> red_cards = getRedCards(fixture_id, minute);
        //logger.debug(String.valueOf(red_cards));

        red_cards.forEach((temp) -> {
            String name = getPlayerName(temp.getPlayer_id()),
                    surname = getPlayerSurname(temp.getPlayer_id()),
                    team_name = getTeamNameByPlayer(temp.getPlayer_id());
            events.add(new MatchEvent(temp, team_name, name, surname));
        });

        //events sorting
        Collections.sort(events);


        Match new_match = new Match(fixture_id, date, hour, home_team_name, home_score, away_team_name, away_score, events, referee);

        List <Match> matches = new ArrayList <Match>();
        matches.add(new_match);

        return matches;
    }


    public long getTeamIdByName(String team_name){
        String sql = "SELECT team_id FROM teams WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{team_name},
                Long.class);
    }

    public List<Player> getStartingEleven(String team_name){
        Long team_id = getTeamIdByName(team_name);
        int[] player_ids = {1, 3, 4, 5, 6, 11, 12, 13, 14, 19, 20};
        for (int i = 0; i < player_ids.length; i++) {
            player_ids[i] = player_ids[i] + (int)(team_id - 1) * 23;
        }
        List <Player> players = new ArrayList<>();
        for (int i = 0; i < player_ids.length; i++) {
            players.add(getPlayer(player_ids[i]));
        }
        players.forEach((temp) -> {
           // logger.debug(String.valueOf(temp));
        });
        return players;
    }

    public TeamRow getTeamRow(long team_id){
        long wins = getTeamWins(team_id);
        //logger.debug(wins);
        long losses = getTeamLosses(team_id);
        //logger.debug(losses);
        long draws = getTeamDraws(team_id);
        //logger.debug(draws);
        long points = 3 * wins + draws;
        long nr = 1;
        long goalsScored = getGoalsFor(team_id);
        //logger.debug(goalsScored);
        long goalsLost = getGoalsAgainst(team_id);
        //logger.debug(goalsLost);
        String teamName = getTeamName(team_id);

        return new TeamRow(nr, teamName, points,  wins, losses, draws, goalsScored, goalsLost);
    }

    public List<TeamRow> getTeamRows(){
        List <TeamRow> team_rows = new ArrayList<TeamRow>();
        for (int i = 1; i <= 20; i++){
            team_rows.add(getTeamRow(i));
        }
        Collections.sort(team_rows);
        for (int i = 1; i <= 20; i++){
            team_rows.get(i - 1).setNr(i);
        }
       // logger.debug(String.valueOf(team_rows));
        return team_rows;
    }

    public List<League> getLeagues(){
        String sql = "SELECT * FROM leagues";
        return jdbcTemplate.query(sql, new Object[]{},
                BeanPropertyRowMapper.newInstance(League.class));
    }


    private List<Match> getMatchesForLeague(long league_id, LocalDate date) {
        List<Match> matches = new ArrayList<>();
        //String sql = "SELECT fixture_id FROM matchesall WHERE league_id = " + String.valueOf(league_id) + " + AND fixture_date = " + String.valueOf(date);
        //List<Long> match_ids = jdbcTemplate.query(sql, new Object[]{league_id, date},
        //BeanPropertyRowMapper.newInstance(Long.class));
        String sql = "SELECT fixture_id FROM matchesall WHERE league_id = ? AND fixture_date = ? ORDER BY fixture_hour";
        List<Long> match_ids = jdbcTemplate.queryForList(sql, new Object[]{league_id, date}, Long.class);
        for (int i = 0; i < match_ids.size(); i++){
            Long minute = 0L;
            if (Period.between(LocalDate.now(), date).getDays() < 0){
                minute = 90L;
            }
            if (Period.between(LocalDate.now(), date).getDays() == 0){
                sql = "SELECT fixture_hour FROM matchesall WHERE league_id = ? AND fixture_date = ?ORDER BY fixture_hour";
                long hour = jdbcTemplate.queryForList(sql, new Object[]{league_id, date}, Long.class).get(0);
                minute = (LocalTime.now().getHour() - hour) * 60 + LocalTime.now().getMinute();
            }
            matches.add(getMatchInfo(match_ids.get(i), minute).get(0));
        }
        return matches;
    }


    public String getLeagueName(long league_id){
        String sql = "SELECT name FROM leagues WHERE league_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{league_id},
                String.class);
    }


    public List<DailyMatches> getDailyMatches(LocalDate date) {
        List <League> leagues = getLeagues();

        List<Match> matches_list = new ArrayList<>();
        List<DailyMatches> daily_matches = new ArrayList<>();
        for (int i = 0; i < leagues.size(); i++) {
            //team id
            long id = leagues.get(i).getLeague_id();
            matches_list = getMatchesForLeague(id, date);
            daily_matches.add(new DailyMatches(getLeagueName(id), matches_list));

        }
        return daily_matches;
    }

    public List<Scorer> getTopScorers() {
        String sql = "SELECT name, surname, COUNT(*) as n_of_goals FROM goals INNER JOIN players " +
                "ON scorer_id = player_id " +
                "INNER JOIN matchesall USING (fixture_id) " +
                "WHERE fixture_date < SYSDATE " +
                "GROUP BY player_id, name, surname " +
                "ORDER BY n_of_goals DESC";
        List<Scorer> scorers = jdbcTemplate.query(sql, new Object[]{},
                BeanPropertyRowMapper.newInstance(Scorer.class));

        sql = "SELECT COUNT(*) FROM goals INNER JOIN players " +
                "ON scorer_id = player_id " +
                "INNER JOIN matchesall USING (fixture_id) " +
                "WHERE fixture_date < SYSDATE " +
                "GROUP BY player_id, name, surname " +
                "ORDER BY COUNT(*) DESC";
        List<Long> goals = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);

        for (int i = 0; i < goals.size(); i++){
            scorers.get(i).setGoals(goals.get(i));
        }
        return scorers;
    }

    public List<Assister> getTopAssists() {
        String sql = "SELECT name, surname, COUNT(*) as n_of_assists FROM goals " +
                "INNER JOIN players ON assist_id = player_id " +
                "INNER JOIN matchesall USING (fixture_id) " +
                "WHERE fixture_date < SYSDATE " +
                "GROUP BY player_id, name, surname " +
                "ORDER BY n_of_assists DESC";
        List<Assister> assisters = jdbcTemplate.query(sql, new Object[]{},
                BeanPropertyRowMapper.newInstance(Assister.class));

        sql = "SELECT COUNT(*) FROM goals INNER JOIN players " +
                "ON assist_id = player_id " +
                "INNER JOIN matchesall USING (fixture_id) " +
                "WHERE fixture_date < SYSDATE " +
                "GROUP BY player_id, name, surname " +
                "ORDER BY COUNT(*) DESC";
        List<Long> assists = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);

        for (int i = 0; i < assists.size(); i++){
            assisters.get(i).setAssists(assists.get(i));
        }
        return assisters;
    }

    private List<Long> getPreviousIds(String teamA, String teamB){
        String sql = "SELECT fixture_id FROM matchesall " +
                "INNER JOIN teams t1 ON team_home_id = t1.team_id " +
                "INNER JOIN teams t2 ON team_away_id = t2.team_id " +
                "WHERE ((t1.name = ? AND t2.name = ?)" +
                "OR (t2.name = ? AND t1.name = ?))" +
                "AND extract(day from fixture_date) < extract(day from SYSDATE)";
        List<Long> previous_ids = jdbcTemplate.queryForList(sql, new Object[]{teamA, teamB, teamA, teamB}, Long.class);
        return previous_ids;
    }

    public List<Match> getPreviousMatches(String teamA, String teamB){
        List<Long> match_ids = getPreviousIds(teamA, teamB);
       // logger.debug(String.valueOf(match_ids));

        List<Match> previous_matches = new ArrayList <>();
        for (int i = 0; i < match_ids.size(); i++){
            previous_matches.add(getMatchInfo(match_ids.get(i), 90).get(0));
        }

        return previous_matches;
    }


    public List<Long> getGameweekIds(long gameweek_nr){
        String sql = "SELECT fixture_id FROM matchesall WHERE gameweek_nr = ?";

        List<Long> ids = jdbcTemplate.queryForList(sql, new Object[]{gameweek_nr}, Long.class);
        return ids;
    }


    public List<Gameweek> getGameweekMatches(long gw_nr){
        List<Long> match_ids = getGameweekIds(gw_nr);

        List<Match> matches = new ArrayList <>();
        for (int i = 0; i < match_ids.size(); i++){
            matches.add(getMatchInfo(match_ids.get(i), 0).get(0));
        }
        List<Gameweek> gameweeks = new ArrayList <>();
        gameweeks.add(new Gameweek(gw_nr, matches));
        return gameweeks;
    }
    public List<GameweekLight> getPreviousGameweeks(){
        String sql = "SELECT gameweek_nr FROM matchesall WHERE fixture_date < SYSDATE";
        return getGameweeks(sql);
    }
    public List<GameweekLight> getGameweekLightMatches(long gw_nr){
        List<Long> match_ids = getGameweekIds(gw_nr);
        List<MatchLight> matches = new ArrayList <>();
        for (int i = 0; i < match_ids.size(); i++){
            matches.add(getMatchLightInfo(match_ids.get(i), 0).get(0));
        }
        List<GameweekLight> gameweeks = new ArrayList <>();
        gameweeks.add(new GameweekLight(gw_nr, matches));
        return gameweeks;
    }
    public List<MatchLight> getMatchLightInfo(long fixture_id, long minute){
        minute = 90;
        //home team id
        long home_team_id = getTeamId(fixture_id, "home");
        //away team id
        long away_team_id = getTeamId(fixture_id, "away");
        //home team name
        String home_team_name = getTeamName(home_team_id);
        //away team name
        String away_team_name = getTeamName(away_team_id);
        //home score
        long home_score = getTeamScore(fixture_id, home_team_id, minute);
       // logger.debug(String.valueOf(home_score));
        //away score
        long away_score = getTeamScore(fixture_id, away_team_id, minute);
       // logger.debug(String.valueOf(away_score));
        MatchLight new_match = new MatchLight(fixture_id, home_team_name, home_score, away_team_name, away_score);
        List <MatchLight> matches = new ArrayList <>();
        matches.add(new_match);
        return matches;
    }
    public List<GameweekLight> getUpcomingGameweeks(){
        String sql = "SELECT gameweek_nr FROM matchesall WHERE fixture_date > SYSDATE";
        return getGameweeks(sql);
    }
    public List<GameweekLight> getGameweeks(String sql){
        List<Long> fixture_nrs = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);

       // logger.debug(String.valueOf(fixture_nrs));
        List<Long> gameweek_nrs = new ArrayList<>();
        for (long gw = 1; gw <= 38; gw++) {
            long how_many_matches = 0;
            for (int i = 0; i < fixture_nrs.size(); i++) {
                if (fixture_nrs.get(i) == gw){
                    how_many_matches++;
                }
            }
            if (how_many_matches >= 10){
                gameweek_nrs.add(gw);
            }
        }
       // logger.debug(String.valueOf(gameweek_nrs));
        List<GameweekLight> gameweeks = new ArrayList<GameweekLight>();
        for (int i = 0; i < gameweek_nrs.size(); i++){
            gameweeks.add(getGameweekLightMatches(gameweek_nrs.get(i)).get(0));
        }
        return gameweeks;
    }

    public List<DailyMatches> getLiveMatches(){
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT fixture_id FROM matchesall " +
                "WHERE (sysdate - fixture_date) * 1440 - 60 * fixture_hour >= 0 " +
                "AND (sysdate - fixture_date) * 1440 - 60 * fixture_hour <= 90";
        List<Long> fixture_nrs = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);
        sql = "SELECT (sysdate - fixture_date) * 1440 - 60 * fixture_hour FROM matchesall " +
                "WHERE (sysdate - fixture_date) * 1440 - 60 * fixture_hour >= 0 " +
                "AND (sysdate - fixture_date) * 1440 - 60 * fixture_hour <= 90";
        List<Long> minute_diffs = jdbcTemplate.queryForList(sql, new Object[]{}, Long.class);
        for (int i = 0; i < fixture_nrs.size(); i++){
            matches.add(getMatchInfo(fixture_nrs.get(i), minute_diffs.get(i)).get(0));
        }
        List<DailyMatches> daily_matches = new ArrayList<>();
        daily_matches.add(new DailyMatches(getLeagueName(1), matches));
        return daily_matches;
    }

    public long login(String log, String password) {
        String sql = "SELECT COUNT(*) FROM app_users " +
                "WHERE user_name = ? AND password = ?";
        long no_users =jdbcTemplate.queryForObject(sql, new Object[]{log, password},
                Long.class);
        if (no_users > 1) {



            //error
            return 2L;
        }
        if (no_users <= 0) {
            //incorrect login or password
            return 1L;
        }
        return 0L;
    }
    public long register(String log, String password) {
        List<Long> user_ids = new ArrayList<>();
        String sql = "SELECT user_id FROM app_users " +
                "WHERE user_name = ?";
        user_ids = jdbcTemplate.queryForList(sql, new Object[]{log}, Long.class);
        if (user_ids.size() >= 1) {
            //user with same login exists
            return 1L;
        }
        sql = "SELECT COUNT (*) FROM app_users";
        long user_id = jdbcTemplate.queryForObject(sql, new Object[]{},
                Long.class);
        addUser(user_id + 1, log, password);
        return 0L;
    }
    public void addUser(long user_id, String log, String password) {
        System.out.println(5);
        String sql = "INSERT INTO app_users (user_id, user_name, password) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user_id, log, password);
    }
    public List<DailyMatches> getFavMatches(String username) {
        //list of match ids
        String sql = "SELECT fixture_id FROM users_matches " +
                "WHERE user_name = ?";
        List<Long> match_ids = jdbcTemplate.queryForList(sql, new Object[]{username}, Long.class);
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < match_ids.size(); i++){
            long match_id = match_ids.get(i);
            //List<Match> match = getMatchInfo(match_id, 90L);
            //System.out.println(match.size());
            matches.add(getMatchInfo(match_id, 90L).get(0));
        }
        DailyMatches favourites = new DailyMatches(getLeagueName(1), matches);
        List<DailyMatches> output = new ArrayList<>();
        output.add(favourites);
        return output;
    }
    public List<Long> getFavIds(String username) {
        String sql = "SELECT fixture_id FROM users_matches " +
                "WHERE user_name = ?";
        List<Long> match_ids = jdbcTemplate.queryForList(sql, new Object[]{username}, Long.class);
        return match_ids;
    }
    public void addFavMatch(String username, long match_id){
        String sql = "SELECT COUNT(*) FROM users_matches " +
                "WHERE user_name = ? AND fixture_id = ?";
        long count = jdbcTemplate.queryForList(sql, new Object[]{username, match_id},
                Long.class).get(0);
        if (count > 0) {
            //duplicated match
            return;
        }
        sql = "INSERT INTO users_matches (user_name, fixture_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, username, match_id);
    }
    public void removeFavMatch(String user_name, long match_id) {
        String sql = "DELETE FROM users_matches " +
                "WHERE user_name = ? AND fixture_id = ?";
        jdbcTemplate.update(sql, user_name, match_id);}
}
