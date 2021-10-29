package projektPAP.Highscore.Classes.People;

public class Player {
    long player_id;
    long team_id;
    String name;
    String surname;
    String position;

    public Player() {
    }

    public Player(long player_id, long team_id, String name, String surname, String position) {
        this.player_id = player_id;
        this.team_id = team_id;
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "player_id=" + player_id +
                ", team_id=" + team_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
