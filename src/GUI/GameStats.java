package GUI;

import AI.Time;
import Game.Card.Card;

public class GameStats {
    public int coin;
    private Card currentCard;
    public int Points;
    public float expectedPoints;
    public Time t;
    public boolean wait;
    public boolean notRunning;
    public boolean cardChanged;
    public void setCard(Card c){
        this.currentCard=c;
        cardChanged=true;
    }
    public Card getCard(){
        return currentCard;
    }
}
