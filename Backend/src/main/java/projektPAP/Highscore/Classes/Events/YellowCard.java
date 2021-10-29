package projektPAP.Highscore.Classes.Events;

public class YellowCard {
    int fixture_id;
    int player_id;
    int ycard_minute;

    public YellowCard() {
    }

    public YellowCard(int fixture_id, int player_id, int ycard_minute) {
        this.fixture_id = fixture_id;
        this.player_id = player_id;
        this.ycard_minute = ycard_minute;
    }

    public int getFixture_id() {
        return fixture_id;
    }

    public void setFixture_id(int fixture_id) {
        this.fixture_id = fixture_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getYcard_minute() {
        return ycard_minute;
    }

    public void setYcard_minute(int ycard_minute) {
        this.ycard_minute = ycard_minute;
    }

    @Override
    public String toString() {
        return "YellowCard{" +
                "fixture_id=" + fixture_id +
                ", player_id=" + player_id +
                ", ycard_minute=" + ycard_minute +
                '}';
    }
}
