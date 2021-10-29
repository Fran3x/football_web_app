package projektPAP.Highscore.Classes.Leagues;

import projektPAP.Highscore.Classes.Events.Match;

import java.util.List;

public class DailyMatches {
    //league name
    String name;
    List<Match> matches;

    public DailyMatches() {
    }

    public DailyMatches(String name, List<Match> matches) {
        this.name = name;
        this.matches = matches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "DailyMatches{" +
                "name='" + name + '\'' +
                ", matches=" + matches +
                '}';
    }
}
