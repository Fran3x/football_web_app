package projektPAP.Highscore.Generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projektPAP.Highscore.Team.Team;
import projektPAP.Highscore.Team.TeamRepository;
import projektPAP.Highscore.Team.TeamService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class GeneratorController {
    List<String> referees;
    List<String> teams_names;
    int league_size, players_per_team;

    private final GeneratorRepository generatorRepository;

    @Autowired
    public GeneratorController(GeneratorRepository generatorRepository) {
        this.generatorRepository = generatorRepository;
        this.referees = Arrays.asList(new String[]{"refA", "refB", "refC", "refD", "refE"});
        this.teams_names = Arrays.asList(new String[]{
                "teamA", "teamB", "teamC", "teamD", "teamE",
                "teamF", "teamG", "teamH", "teamI", "teamJ",
                "teamK",  "teamL", "teamM", "teamN", "teamO",
                "teamP", "teamR", "teamS", "teamT", "teamU"});
        this.league_size = 20;
        this.players_per_team = 23;
    }

    public void generateTeams(){
        generatorRepository.dropTable("new_table");
        generatorRepository.createTable("new_table", Arrays.asList("team_id", "Number", "name", "Varchar(20)"));
        for (int i = 0; i < this.league_size; i++){
            generatorRepository.insertRow("new_table", Arrays.asList("team_id", "name"), Arrays.asList(String.valueOf(i + 1), "'" + this.teams_names.get(i) + "'"));
        }
    };

    public void generateFixtures(){
        generatorRepository.dropTable("fixtures");
        generatorRepository.createTable("fixtures", Arrays.asList("fixture_id", "Number", "team_home_id", "Number", "team_away_id", "Number", "referee_surname", "Varchar(20)"));

        int fixture_id = 1;
        // teams play with each other twice
        for (int i = 0; i < this.league_size; i++){
            for (int j = 0; j < this.league_size; j++){
                if (i != j) {
                    Random rand = new Random();
                    float float_random = rand.nextFloat();
                    int team_home_id = 0, team_away_id = 0;
                    if (float_random > 0.5) {
                        team_home_id = j + 1;
                        team_away_id = i + 1;
                    } else {
                        team_home_id = i + 1;
                        team_away_id = j + 1;
                    }
                    //random referee
                    String referee = referees.get(rand.nextInt(referees.size()));
                    generatorRepository.insertRow("fixtures", Arrays.asList("fixture_id", "team_home_id", "team_away_id", "referee_surname"), Arrays.asList(String.valueOf(fixture_id), String.valueOf(team_home_id), String.valueOf(team_away_id), "'" + referee + "'"));
                    fixture_id += 1;
                }
            }
        }
    };

    public void generatePlayers() {
        generatorRepository.dropTable("players");
        generatorRepository.createTable("players", Arrays.asList("player_id", "Number", "team_id", "Number"));

        int player_id = 1;
        for (int i = 0; i < this.league_size; i++) {
            for (int j = 0; j < this.players_per_team; j++) {
                generatorRepository.insertRow("players", Arrays.asList("player_id", "team_id"), Arrays.asList(String.valueOf(player_id), String.valueOf(i + 1)));
                player_id += 1;
            }
        }
    }


    @GetMapping("/generate")
    public void createDatabase(){
        System.out.println("abc");

        this.generateTeams();

        this.generateFixtures();

        this.generatePlayers();


    }
}
