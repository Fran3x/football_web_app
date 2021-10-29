package projektPAP.Highscore.Generator;

import projektPAP.Highscore.Team.Team;

import java.util.Arrays;
import java.util.List;

public class DatabaseGenerator {
    List<String> referees;
    List<String> teams_names;


    public DatabaseGenerator() {
        this.referees = Arrays.asList(new String[]{"refA", "refB", "refC", "refD", "refE"});
        this.teams_names = Arrays.asList(new String[]{
                "teamA", "teamB", "teamC", "teamD", "teamE",
                "teamF", "teamG", "teamH", "teamI", "teamJ",
                "teamK",  "teamL", "teamM", "teamN", "teamO",
                "teamP", "teamR", "teamS", "teamT", "teamU"});
    };

    public void createDatabase() {

    };

}
