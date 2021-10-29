package projektPAP.Highscore.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projektPAP.Highscore.Classes.Events.Match;
import projektPAP.Highscore.Classes.Leagues.*;
import projektPAP.Highscore.Classes.People.Assister;
import projektPAP.Highscore.Classes.People.Player;
import projektPAP.Highscore.Classes.People.Scorer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class APIController {
    private final APIService APIService;

    @Autowired
    public APIController(APIService APIService) {
        this.APIService = APIService;
    }

    @GetMapping("/api/match/{fixture_id}/{minute}")
    @ResponseBody
    public List<Match> getMatchInfo(@PathVariable long fixture_id, @PathVariable long minute){
        return APIService.getMatchInfo(fixture_id, minute);
    }

    @GetMapping("/api/players/{team_name}")
    @ResponseBody
    public List<Player> getStartingEleven(@PathVariable String team_name){
        return APIService.getStartingEleven(team_name);
    }

    @GetMapping("/api/table/{league_name}")
    @ResponseBody
    public List<TeamRow> getTeamRows(@PathVariable String league_name){
        return APIService.getTeamRows();
    }

    @GetMapping("/api/leagues")
    public List<League> getLeagues(){
        return APIService.getLeagues();
    }

    @GetMapping("/api/matches/{date}")
    @ResponseBody
    public List<DailyMatches> getDailyMatches(@PathVariable String date){
        date = date.replace(".","/");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate d = LocalDate.parse(date,formatter);
        System.out.println(d);
        return APIService.getDailyMatches(d);
    }

    @GetMapping("/api/goals/{league_name}")
    @ResponseBody
    public List<Scorer> getTopScorers(@PathVariable String league_name){
        return APIService.getTopScorers();
    }

    @GetMapping("/api/assists/{league_name}")
    @ResponseBody
    public List<Assister> getTopAssists(@PathVariable String league_name){
        return APIService.getTopAssists();
    }


    @GetMapping("/api/previous/{teamA}/{teamB}")
    public List<Match> getPreviousMatches(@PathVariable String teamA, @PathVariable String teamB){
        return APIService.getPreviousMatches(teamA, teamB);
    }

    @GetMapping("/api/gameweek/{gw_nr}")
    @ResponseBody
    public List<Gameweek> getGameweekMatches(@PathVariable long gw_nr) {
        return APIService.getGameweekMatches(gw_nr);
    }

    @GetMapping("/api/previousgameweeks")
    public List<GameweekLight> getPreviousGameweeks(){
        return APIService.getPreviousGameweeks();
    }

    @GetMapping("/api/cominggameweeks")
    public List<GameweekLight> getUpcomingGameweeks(){
        return APIService.getUpcomingGameweeks();
    }

    @GetMapping("/api/live")
    public List<DailyMatches> getLiveMatches(){
        return APIService.getLiveMatches();
    }

    @GetMapping("/api/login/{log}/{password}")
    @ResponseBody
    public long login(@PathVariable String log, @PathVariable String password) { return APIService.login(log, password); }

    @GetMapping("/api/register/{log}/{password}")
    @ResponseBody
    public long register(@PathVariable String log, @PathVariable String password) { return APIService.register(log, password); }

    @GetMapping("/api/favmatches/{user_name}")
    @ResponseBody
    public List<DailyMatches> getFavMatches(@PathVariable String user_name) { return APIService.getFavMatches(user_name); }
    @GetMapping("/api/favids/{user_name}")
    @ResponseBody
    public List<Long> getFavIds(@PathVariable String user_name) { return APIService.getFavIds(user_name); }
    @GetMapping("/api/addfav/{user_name}/{match_id}")
    @ResponseBody
    public void addFavMatch(@PathVariable String user_name, @PathVariable long match_id) { APIService.addFavMatch(user_name, match_id); }
    @GetMapping("/api/removefav/{user_name}/{match_id}")
    @ResponseBody
    public void removeFavMatch(@PathVariable String user_name, @PathVariable long match_id) { APIService.removeFavMatch(user_name, match_id); }
}
