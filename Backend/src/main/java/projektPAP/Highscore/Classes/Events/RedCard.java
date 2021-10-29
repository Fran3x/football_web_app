package projektPAP.Highscore.Classes.Events;

public class RedCard {
    int fixture_id;
    int player_id;
    int rcard_minute;

    public RedCard() {
    }

    public RedCard(int fixture_id, int player_id, int ycard_minute) {
        this.fixture_id = fixture_id;
        this.player_id = player_id;
        this.rcard_minute = rcard_minute;
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

    public int getRcard_minute() {
        return rcard_minute;
    }

    public void setRcard_minute(int ycard_minute) {
        this.rcard_minute = rcard_minute;
    }

    @Override
    public String toString() {
        return "RedCard{" +
                "fixture_id=" + fixture_id +
                ", player_id=" + player_id +
                ", rcard_minute=" + rcard_minute +
                '}';
    }
}
