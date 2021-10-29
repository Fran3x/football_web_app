package projektPAP.Highscore.Classes.Leagues;

import projektPAP.Highscore.Classes.Events.Match;

import java.util.List;

public class Gameweek {
    Long id;
    List<Match> matches;

    public Gameweek() {
    }

    public Gameweek(Long id, List<Match> matches) {
        this.id = id;
        this.matches = matches;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "Gameweek{" +
                "id=" + id +
                ", matches=" + matches +
                '}';
    }
}

