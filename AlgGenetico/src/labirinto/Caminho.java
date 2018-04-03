/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Saphira
 */
class Caminho {
    private ArrayList<Coordenada> pontosIntermediarios;
    private int fitness;
    
    public Caminho () {
        // escolhe um numero aleatorio de 1 a 3 (numero de pontos intermediarios)
        Random rand = new Random();
        int num = rand.nextInt(3);
        
        
        for (int i=0; i < num; i++){
            x = aleatorio
            y = aleatorio
            pontosIntermediarios.add(new Coordenada(y, x));
        }
        
        fitness = Integer.MAX_VALUE;
    }
    public void calculaFitness() {
        
    }
    public int getFitness() { return fitness; }

    public void mutacao() {
        // escolhe uma coordenada qualquer, faz X ou Y +- 1 ou 2
        Random rand = new Random();
        int num = rand.nextInt(2);
        pontosIntermediarios.add(new Coordenada(y+num, x+num));
        
    }
    public Caminho cruzar(Caminho other) {
        // Se algum deles coordenada repetida (ex. (1,1) (1,1))
        // escolhe 1 deles e junta com o outro diferente
        
        
        
        return filho;
    }

}
