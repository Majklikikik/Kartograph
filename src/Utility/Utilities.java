package Utility;

import AI.Params;
import Game.Field.Tile.Tile;
import Game.Field.Tile.TileType;

import java.awt.*;

import static Game.Field.Tile.Tile.*;

public class Utilities {
    public static boolean[][] deepCopy(boolean [][] orig){
        boolean[][] ret=new boolean[orig.length][];
        for (int i=0;i<orig.length; i++){
            ret[i]=new boolean[orig[i].length];
            if (orig[i].length >= 0) System.arraycopy(orig[i], 0, ret[i], 0, orig[i].length);
        }
        return ret;
    }

    public static boolean [][] mrot(boolean [][] orig, int i){
        if (i<=0)
            return orig;
        else
            return mrot(rot(orig),i-1);
    }

    public static void slp(int m){
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean[][] rot(boolean [][] orig){
        boolean[][] ret=new boolean[orig[0].length][orig.length];
        for (int i=0;i<orig.length;i++){
            for (int j=0;j<orig[0].length;j++){
                ret[j][i]=orig[i][orig[0].length-1-j];
            }
        }
        return ret;
    }
    public static boolean[][] mirror(boolean [][] orig){
        boolean[][] ret=new boolean[orig.length][orig[0].length];
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[0].length; j++) {

                ret[i][j] = orig[i][orig[0].length - 1 - j];
            }
        }
        return ret;
    }

    public static String toString(boolean [][] f){
        return toString(f,TileType.EMPTY);
    }

    public static String toString(boolean [][] f, TileType v){
        StringBuilder s= new StringBuilder("-----\n");
        for (int i=0;i<f.length;i++){
            for (int j=0;j<f[i].length;j++){
                s.append(f[i][j] ? TileTypeToInt(v) : 0);
            }
            s.append("\n");
        }
        return(s+"-----\n");
    }

    public static int TileTypeToInt(TileType v){
        switch (v){
            case EMPTY: return Tile.TILE_EMPTY;
            case FOREST: return Tile.TILE_FOREST;
            case MOUNTAIN: return TILE_HILL;
            case OBSTACLE: return TILE_OBSTACLE;
            case CITY: return TILE_CITY;
            case FIELD: return TILE_FIELD;
            case WATER: return TILE_WATER;
            default: return  TILE_ASSHOLE;

        }
    }

    public static void print(boolean [][] f){
        System.out.println(toString(f));
    }

    public static void paintLines(Graphics g, boolean[][] f, int xo, int yo, int sc){
        for (int x=0;x<f[0].length;x++){
            for (int y=0;y<f.length;y++){
                if (x>0&&f[y][x]!=f[y][x-1])
                    g.drawLine(xo+(x)*sc,yo+(y)*sc,xo+(x)*sc,yo+(y+1)*sc);
                if (y>0&&f[y][x]!=f[y-1][x])
                    g.drawLine(xo+(x)*sc,yo+(y)*sc,xo+(x+1)*sc,yo+(y)*sc);
            }
        }
    }

    public static void paintLines(Graphics g, Tile[][] f, int xo, int yo, int sc){
        for (int x=0;x<f[0].length;x++){
            for (int y=0;y<f.length;y++){
                if (x>0&&(f[y][x].getType()!=f[y][x-1].getType()||f[y][x].getType()==TileType.EMPTY||f[y][x-1].getType()==TileType.EMPTY))
                    g.drawLine(xo+(x)*sc,yo+(y)*sc,xo+(x)*sc,yo+(y+1)*sc);
                if (y>0&&(f[y][x].getType()!=f[y-1][x].getType()||f[y][x].getType()==TileType.EMPTY||f[y-1][x].getType()==TileType.EMPTY))
                    g.drawLine(xo+(x)*sc,yo+(y)*sc,xo+(x+1)*sc,yo+(y)*sc);
            }
        }
    }

    public static float average(Params[] a){
        float ret=0;
        for (Params s:a){
            ret+=s.points/(float)a.length;
        }
        return ret;
    }

    public static float deviation(Params[] a, float avg){
        float ret=0;
        for (Params s:a){
            ret+=(avg-s.points)*(avg-s.points)/a.length;
        }
        return (float)Math.sqrt(ret);
    }

    public static String dateDiffToString(long l) {
        int sec=(int)l/1000;
        int min=sec/60;
        int h=min/60;
        return h+":"+(min%60)+":"+(sec%60)+"."+(l%1000);
    }
}
