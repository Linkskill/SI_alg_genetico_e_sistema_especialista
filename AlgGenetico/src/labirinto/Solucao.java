/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.ArrayList;

/**
 *
 * @author Saphira
 */
class Solucao {
    private ArrayList<Coordenada> pontosIntermediarios;
    private int fitness;
    
    public Solucao () {
        escolhe um numero aleatorio de 1 a 3 (numero de pontos intermediarios)
        for (int i=0; i < num; i++)
            x = aleatorio
            y = aleatorio
            pontosIntermediarios.add(new Coordenada(y, x))
        
        fitness = Integer.MAX_VALUE;
    }
    public void calculaFitness() {
        
    }
    public int getFitness() { return fitness; }

    public void mutacao() {
        escolhe uma coordenada qualquer, faz X ou Y +- 1 ou 2
    }
    public Solucao cruzar(Solucao other) {
        
        
        return filho;
    }

}
