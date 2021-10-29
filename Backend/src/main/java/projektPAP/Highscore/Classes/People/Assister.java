package projektPAP.Highscore.Classes.People;

public class Assister {
    String name;
    String surname;
    long assists;

    public Assister() {
    }

    public Assister(String name, String surname, long assists) {
        this.name = name;
        this.surname = surname;
        this.assists = assists;
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

    public long getAssists() {
        return assists;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    @Override
    public String toString() {
        return "Assister{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", assists=" + assists +
                '}';
    }
}
