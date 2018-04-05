/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.Objects;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class Estado {
    private final Ponto ponto;
    
    public Estado (int y, int x){
        ponto = new Ponto(y, x);
    }
    public int getY() { return ponto.getY(); }
    public int getX() { return ponto.getX(); }
    public Ponto getPonto() { return ponto; }

    @Override
    public String toString(){
        return ponto.toString();
    }
    @Override
    public boolean equals(Object other){
        //Como só trabalhamos com um labirinto por vez, a única coisa que
        //precisamos para comparar se dois estados são iguais é a posição.
        Estado e = (Estado)other;
        if (ponto.equals(e.getPonto()))
            return true;
        return false;
    }
    @Override
    //Sempre que der Override no equals() precisa dar no hashCode() também
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.ponto);
        return hash;
    }
}
