package projektPAP.Highscore.Classes.Leagues;

public class League {
    long league_id;
    String name;

    public League() {
    }

    public League(long league_id, String name) {
        this.league_id = league_id;
        this.name = name;
    }

    public long getLeague_id() {
        return league_id;
    }

    public void setLeague_id(long league_id) {
        this.league_id = league_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "League{" +
                "league_id=" + league_id +
                ", name='" + name + '\'' +
                '}';
    }
}
