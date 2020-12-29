package Game.Card;

import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;
import sun.awt.image.ImageWatched;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class CardGenerator {
    public static float fullCardCount=11f;

    public static Card Ruin(){
        Card r=new Card("Ruins");
        r.ruinCard=true;
        r.duration=0;
        return r;
    }
    public static Card Baumwipfeldorf(){
        Card Bw=new Card("Baumwipfeldorf");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{false,false,true,true},{true,true,true,false}});
        p1.setType(TileType.FOREST);
        Possibility p2=new Possibility(p1);
        p2.setType(TileType.CITY);
        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }
    public static Card Großer_Strom(){
        Card Bw=new Card("Großer Strom");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,true,true}});
        p1.setType(TileType.WATER);
        p1.setGetCoin(true);

        Possibility p2=new Possibility(p1);
        p2.setToPlace(new boolean[][]{{false,false,true},{false,true,true},{true,true,false}});
        p2.setGetCoin(false);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(1);
        return Bw;
    }
    public static Card Ackerland(){
        Card Bw=new Card("Ackerland");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,true}});
        p1.setType(TileType.FIELD);
        p1.setGetCoin(true);

        Possibility p2=new Possibility(p1);
        p2.setToPlace(new boolean[][]{{false,true,false},{true,true,true},{false,true,false}});
        p2.setGetCoin(false);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(1);
        return Bw;
    }
    public static Card Hinterlandbach(){
        Card Bw=new Card("Hinterlandbach");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,true,true},{true,false,false},{true,false,false}});
        p1.setType(TileType.FIELD);

        Possibility p2=new Possibility(p1);
        p2.setType(TileType.WATER);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }
    public static Card Obsthain(){
        Card Bw=new Card("Obsthain");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,true,true},{false,false,true}});
        p1.setType(TileType.FOREST);

        Possibility p2=new Possibility(p1);
        p2.setType(TileType.FIELD);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }
    public static Card Weiler(){
        Card Bw=new Card("Weiler");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,false},{true,true}});
        p1.setType(TileType.CITY);
        p1.setGetCoin(true);

        Possibility p2=new Possibility(p1);
        p2.setToPlace(new boolean[][]{{true,true,true},{true,true,false}});
        p2.setGetCoin(false);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(1);
        return Bw;
    }
    public static Card VergessenerWald(){
        Card Bw=new Card("Vergessener Wald");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,false},{false,true}});
        p1.setType(TileType.FOREST);
        p1.setGetCoin(true);

        Possibility p2=new Possibility(p1);
        p2.setToPlace(new boolean[][]{{true,false},{true,true},{false, true}});
        p2.setGetCoin(false);

        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(1);
        return Bw;
    }
    public static Card Splitterland(){
        Card Bw=new Card("Splitterland");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true}});
        p1.setType(TileType.ASSHOLE);

        Possibility p2=new Possibility(p1);
        p2.setType(TileType.FIELD);

        Possibility p3=new Possibility(p1);
        p3.setType(TileType.FOREST);

        Possibility p4=new Possibility(p1);
        p4.setType(TileType.CITY);

        Possibility p5=new Possibility(p1);
        p5.setType(TileType.WATER);



        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.addPossibility(p3);
        Bw.addPossibility(p4);
        Bw.addPossibility(p5);
        Bw.setDuration(0);
        return Bw;
    }
    public static Card Fischerdorf(){
        Card Bw=new Card("Fischerdorf");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,true,true,true}});
        p1.setType(TileType.CITY);
        Possibility p2=new Possibility(p1);
        p2.setType(TileType.WATER);
        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }
    public static Card Gehoeft(){
        Card Bw=new Card("Geföft");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{false,true,false},{true,true,true}});
        p1.setType(TileType.FIELD);
        Possibility p2=new Possibility(p1);
        p2.setType(TileType.CITY);
        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }
    public static Card Sumpf(){
        Card Bw=new Card("Sumpf");
        Possibility p1=new Possibility();
        p1.setToPlace(new boolean[][]{{true,false,false},{true,true,true},{true,false,false}});
        p1.setType(TileType.FOREST);
        Possibility p2=new Possibility(p1);
        p2.setType(TileType.WATER   );
        Bw.addPossibility(p1);
        Bw.addPossibility(p2);
        Bw.setDuration(2);
        return Bw;
    }

    public static LinkedList<Card> generateAndShuffleCards(){
        return generateAndShuffleCards(new Random());
    }

    public static LinkedList<Card> generateAndShuffleCards(Random r){
        LinkedList<Card> ret=new LinkedList<>();
        ret.add(Baumwipfeldorf());
        ret.add(Großer_Strom());
        ret.add(Ackerland());
        ret.add(Hinterlandbach());
        ret.add(Obsthain());
        ret.add(Weiler());
        ret.add(VergessenerWald());
        ret.add(Splitterland());
        ret.add(Fischerdorf());
        ret.add(Gehoeft());
        ret.add(Sumpf());
        ret.add(Ruin());
        ret.add(Ruin());
        Collections.shuffle(ret,r);
        return ret;
    }

    public static LinkedList<Card> generateCardsExceptRuins(){
        LinkedList<Card> ret=new LinkedList<>();
        ret.add(Baumwipfeldorf());
        ret.add(Großer_Strom());
        ret.add(Ackerland());
        ret.add(Hinterlandbach());
        ret.add(Obsthain());
        ret.add(Weiler());
        ret.add(VergessenerWald());
        ret.add(Splitterland());
        ret.add(Fischerdorf());
        ret.add(Gehoeft());
        ret.add(Sumpf());
        Collections.shuffle(ret);
        return ret;
    }




}
