package projektPAP.Highscore.Classes.People;

public class Scorer{
    String name;
    String surname;
    long goals;

    public Scorer() {
    }

    public Scorer(String name, String surname, long goals) {
        this.name = name;
        this.surname = surname;
        this.goals = goals;
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

    public long getGoals() {
        return goals;
    }

    public void setGoals(long goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return "Scorer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", goals=" + goals +
                '}';
    }

}
