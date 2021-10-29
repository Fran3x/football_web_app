package projektPAP.Highscore.Classes.Leagues;

/*export interface ITableRow{
    nr: number,
    teamName: string,
    points: number,
    wins: number,
    loses: string,
    draws: number,
    goalsScored: number,
    goalsLost: number
}*/

public class TeamRow implements Comparable<TeamRow>{
    long nr;
    String teamName;
    long points;
    long wins;
    long losses;
    long draws;
    long goalsScored;
    long goalsLost;

    public TeamRow() {
    }

    public TeamRow(long nr, String teamName, long points, long wins, long losses, long draws, long goalsScored, long goalsLost) {
        this.nr = nr;
        this.teamName = teamName;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.goalsScored = goalsScored;
        this.goalsLost = goalsLost;
    }

    public long getNr() {
        return nr;
    }

    public void setNr(long nr) {
        this.nr = nr;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(long goalsScored) {
        this.goalsScored = goalsScored;
    }

    public long getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(long goalsLost) {
        this.goalsLost = goalsLost;
    }

    @Override
    public String toString() {
        return "TeamRow{" +
                "nr=" + nr +
                ", teamName='" + teamName + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", losses=" + losses +
                ", draws=" + draws +
                ", goalsScored=" + goalsScored +
                ", goalsLost=" + goalsLost +
                '}';
    }

    @Override
    public int compareTo(TeamRow tr2) {
        long e1 = ((TeamRow)tr2).getPoints();
        return (int)(e1 - this.getPoints());
    }
}
