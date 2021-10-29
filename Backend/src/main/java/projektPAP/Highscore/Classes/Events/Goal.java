package projektPAP.Highscore.Classes.Events;

public class Goal {
    int fixture_id;
    int scorer_id;
    int assist_id;
    int goal_minute;

    public Goal() {
    }

    public Goal(int fixture_id, int scorer_id, int assist_id, int goal_minute) {
        this.fixture_id = fixture_id;
        this.scorer_id = scorer_id;
        this.assist_id = assist_id;
        this.goal_minute = goal_minute;
    }

    public int getFixture_id() {
        return fixture_id;
    }

    public void setFixture_id(int fixture_id) {
        this.fixture_id = fixture_id;
    }

    public int getScorer_id() {
        return scorer_id;
    }

    public void setScorer_id(int scorer_id) {
        this.scorer_id = scorer_id;
    }

    public int getAssist_id() {
        return assist_id;
    }

    public void setAssist_id(int assist_id) {
        this.assist_id = assist_id;
    }

    public int getGoal_minute() {
        return goal_minute;
    }

    public void setGoal_minute(int goal_minute) {
        this.goal_minute = goal_minute;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "fixture_id=" + fixture_id +
                ", scorer_id=" + scorer_id +
                ", assist_id=" + assist_id +
                ", goal_minute=" + goal_minute +
                '}';
    }
}
