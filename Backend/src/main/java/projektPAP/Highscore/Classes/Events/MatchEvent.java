package projektPAP.Highscore.Classes.Events;

public class MatchEvent implements  Comparable<MatchEvent> {
    long minute;
    String team;
    String name;
    String surname;
    String sname;
    String ssurname;
    String aname;
    String asurname;
    String eventType;

    public MatchEvent() {
    }

    public MatchEvent(Goal goal, String team_name, String scorer_name, String scorer_surname, String assist_name, String assist_surname){
        this.minute = goal.getGoal_minute();
        this.team = team_name;
        this.name = null;
        this.surname = null;
        this.sname = scorer_name;
        this.ssurname = scorer_surname;
        this.aname = assist_name;
        this.asurname = assist_surname;
        this.eventType = "goal";
    }

    public MatchEvent(YellowCard yellowcard, String team_name, String player_name, String player_surname){
        this.minute = yellowcard.getYcard_minute();
        this.team = team_name;
        this.name = player_name;
        this.surname = player_surname;
        this.sname = null;
        this.ssurname = null;
        this.aname = null;
        this.asurname = null;
        this.eventType = "yellow_card";
    }

    public MatchEvent(RedCard redcard, String team_name, String player_name, String player_surname){
        this.minute = redcard.getRcard_minute();
        this.team = team_name;
        this.name = player_name;
        this.surname = player_surname;
        this.sname = null;
        this.ssurname = null;
        this.aname = null;
        this.asurname = null;
        this.eventType = "red_card";
    }

    public MatchEvent(long minute, String team, String name, String surname, String sname, String ssurname, String aname, String asurname, String eventType) {
        this.minute = minute;
        this.team = team;
        this.name = name;
        this.surname = surname;
        this.sname = sname;
        this.ssurname = ssurname;
        this.aname = aname;
        this.asurname = asurname;
        this.eventType = eventType;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsurname() {
        return ssurname;
    }

    public void setSsurname(String ssurname) {
        this.ssurname = ssurname;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAsurname() {
        return asurname;
    }

    public void setAsurname(String asurname) {
        this.asurname = asurname;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "MatchEvent{" +
                "minute=" + minute +
                ", team='" + team + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sname='" + sname + '\'' +
                ", ssurname='" + ssurname + '\'' +
                ", aname='" + aname + '\'' +
                ", asurname='" + asurname + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }

    @Override
    public int compareTo(MatchEvent e2) {
        long e1 = ((MatchEvent)e2).getMinute();
        /* For Ascending order*/
        return (int)(this.getMinute() - e1);
    }
}
