package projektPAP.Highscore.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import projektPAP.Highscore.Team.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Team> getTeams() {
        String sql = "SELECT * FROM teams";
        List<Team> teams = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Team.class));
        teams.forEach(System.out::println);
        return teams;
    }

}