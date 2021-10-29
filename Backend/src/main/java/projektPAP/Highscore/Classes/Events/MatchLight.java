package projektPAP.Highscore.Classes.Events;

public class MatchLight {
    long match_id;
    String homeTeam;
    long homeScore;
    String awayTeam;
    long awayScore;
    public MatchLight() {
    }
    public MatchLight(long match_id, String homeTeam, long homeScore, String awayTeam, long awayScore) {
        this.match_id = match_id;
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
    }
    public long getMatch_id() {
        return match_id;
    }
    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }
    public String getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }
    public long getHomeScore() {
        return homeScore;
    }
    public void setHomeScore(long homeScore) {
        this.homeScore = homeScore;
    }
    public String getAwayTeam() {
        return awayTeam;
    }
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
    public long getAwayScore() {
        return awayScore;
    }
    public void setAwayScore(long awayScore) {
        this.awayScore = awayScore;
    }
    @Override
    public String toString() {
        return "MatchLight{" +
                "match_id=" + match_id +
                ", homeTeam='" + homeTeam + '\'' +
                ", homeScore=" + homeScore +
                ", awayTeam='" + awayTeam + '\'' +
                ", awayScore=" + awayScore +
                '}';
    }
}