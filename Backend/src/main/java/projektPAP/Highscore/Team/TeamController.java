package projektPAP.Highscore.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import projektPAP.Highscore.Team.Team;
import projektPAP.Highscore.Team.TeamService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping()
public class TeamController {
    private final TeamService teamService;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamService teamService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/api/teams")
    public List<Team> getTeams() {
        return teamService.getTeams();
    }

    @PostMapping("/api/add")
    public void putTeam(@RequestBody Team new_team){
        teamRepository.putTeam(new_team);
    }

}