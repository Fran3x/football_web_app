package projektPAP.Highscore.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public class TeamRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void putTeam(Team new_team) {
        String sql = "INSERT INTO TEAMS (name, score) VALUES (?, ?)";
        jdbcTemplate.update(
                sql,
                new_team.getName(),
                new_team.getScore()
        );
    }

}
