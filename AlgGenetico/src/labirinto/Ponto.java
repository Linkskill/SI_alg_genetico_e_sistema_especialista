/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class Ponto {
    private int x;
    private int y;
    
    public Ponto(int y, int x){
        this.y = y;
        this.x = x;
    }
    public int getX(){ return x; }
    public int getY(){ return y; }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + getY() + ", " + getX() + ")";
    }
    @Override
    public boolean equals(Object other){
        Ponto coord = (Ponto)other;
        if (getX() == coord.getX() && getY() == coord.getY())
            return true;
        return false;
    }
    @Override
    //Sempre que der Override no equals() precisa dar no hashCode() também
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.getX();
        hash = 13 * hash + this.getY();
        return hash;
    }
}
