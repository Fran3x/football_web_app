package projektPAP.Highscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import projektPAP.Highscore.Team.TeamRepository;

@SpringBootApplication
public class HighscoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(HighscoreApplication.class, args);
	}

}
