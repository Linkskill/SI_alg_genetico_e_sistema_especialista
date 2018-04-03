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
    private final Ponto coordenada;
    
    public Estado (int y, int x){
        coordenada = new Ponto(y, x);
    }
    public int getY() { return coordenada.getY(); }
    public int getX() { return coordenada.getX(); }
    public Ponto getCoordenada() { return coordenada; }

    @Override
    public String toString(){
        return coordenada.toString();
    }
    @Override
    public boolean equals(Object other){
        //Como só trabalhamos com um labirinto por vez, a única coisa que
        //precisamos para comparar se dois estados são iguais é a posição.
        Estado e = (Estado)other;
        if (coordenada.equals(e.getCoordenada()))
            return true;
        return false;
    }
    @Override
    //Sempre que der Override no equals() precisa dar no hashCode() também
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.coordenada);
        return hash;
    }
}
