package projektPAP.Highscore.Classes.Events;

import projektPAP.Highscore.Classes.Events.MatchEvent;
import projektPAP.Highscore.Classes.People.Referee;

import java.util.List;

public class Match {
    long match_id;
    String matchDate;
    long matchHour;
    String homeTeam;
    long homeScore;
    String awayTeam;
    long awayScore;
    List <MatchEvent> events;
    Referee referee;

    public Match() {
    }

    public Match(long match_id, String matchDate, long matchHour, String homeTeam, long homeScore, String awayTeam, long awayScore, List<MatchEvent> events, Referee referee) {
        this.match_id = match_id;
        this.matchDate = matchDate;
        this.matchHour = matchHour;
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.awayTeam = awayTeam;
        this.awayScore = awayScore;
        this.events = events;
        this.referee = referee;
    }

    public long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public long getMatchHour() {
        return matchHour;
    }

    public void setMatchHour(long matchHour) {
        this.matchHour = matchHour;
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

    public List<MatchEvent> getEvents() {
        return events;
    }

    public void setEvents(List<MatchEvent> events) {
        this.events = events;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    @Override
    public String toString() {
        return "Match{" +
                "match_id=" + match_id +
                ", matchDate='" + matchDate + '\'' +
                ", matchHour=" + matchHour +
                ", homeTeam='" + homeTeam + '\'' +
                ", homeScore=" + homeScore +
                ", awayTeam='" + awayTeam + '\'' +
                ", awayScore=" + awayScore +
                ", events=" + events +
                ", referee=" + referee +
                '}';
    }
}
