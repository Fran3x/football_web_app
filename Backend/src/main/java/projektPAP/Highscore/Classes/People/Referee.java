package projektPAP.Highscore.Classes.People;

public class Referee {
    int referee_id;
    String name;
    String surname;

    public Referee() {
    }

    public Referee(int referee_id, String name, String surname) {
        this.referee_id = referee_id;
        this.name = name;
        this.surname = surname;
    }

    public int getReferee_id() {
        return referee_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setReferee_id(int referee_id) {
        this.referee_id = referee_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Referee{" +
                "referee_id=" + referee_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
