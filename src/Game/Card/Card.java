package Game.Card;

import java.util.LinkedList;
import java.util.Objects;

public class Card {
    public boolean ruinCard=false;
    public boolean onRuin=false;
    private String name;
    int duration;
    private LinkedList<Possibility> PossibleChoices;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Card(String name){
        this.name =name;
        PossibleChoices=new LinkedList<Possibility>();
    }
    public void addPossibility(Possibility p){
        PossibleChoices.add(p);
    }

    public LinkedList<Possibility> getPossibilities() {
        return PossibleChoices;
    }

    @Override
    public String toString(){
        StringBuilder s= new StringBuilder(name + "\n");
        //for (Possibility p:PossibleChoices){
        //    s.append(p);
        //}
        return s.toString();
    }

    public Card copy() {
        Card c= new Card(this.name);
        c.duration=this.duration;
        c.onRuin=this.onRuin;
        c.ruinCard=this.ruinCard;
        c.PossibleChoices=this.PossibleChoices;
        return c;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
