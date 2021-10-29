package projektPAP.Highscore.Classes.Leagues;

import projektPAP.Highscore.Classes.Events.MatchLight;

import java.util.List;
public class GameweekLight {
    Long id;
    List<MatchLight> matches;
    public GameweekLight() {
    }
    public GameweekLight(Long id, List<MatchLight> matches) {
        this.id = id;
        this.matches = matches;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<MatchLight> getMatches() {
        return matches;
    }
    public void setMatches(List<MatchLight> matches) {
        this.matches = matches;
    }
    @Override
    public String toString() {
        return "GameweekLight{" +
                "id=" + id +
                ", matches=" + matches +
                '}';

    }
}