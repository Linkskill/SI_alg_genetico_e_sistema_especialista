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
    
    public Caminho (int alt, int larg) {
        pontosIntermediarios = new ArrayList<>();
        randomPoints(alt, larg);
        fitness = Integer.MAX_VALUE;
    }
    private void randomPoints (int alt, int larg) {
        // escolhe um numero aleatorio de 1 a 3 (numero de pontos intermediarios)
        Random rand = new Random();
        int num = rand.nextInt(3);
        int x,y;
        
        for (int i=0; i < num; i++)
        {
            x = rand.nextInt(larg);
            y = rand.nextInt(alt);
            pontosIntermediarios.add(new Coordenada(y, x));
        }
    }
    public void calculaFitness() {
        
    }
    public int getFitness() { return fitness; }

    public void mutacao() {
        // escolhe uma coordenada qualquer, faz X ou Y +- 1 ou 2
        Random rand = new Random();
        int num = rand.nextInt(2);
        //Se for adicionar um ponto novo, tem que tirar um antigo. O x e o y que
        //vai usar são esses do antigo +- num
        pontosIntermediarios.add(new Coordenada(y+num, x+num));
        
    }
    public Caminho cruzar(Caminho other) {
        // Se algum deles coordenada repetida (ex. (1,1) (1,1))
        // escolhe 1 deles e junta com o outro diferente
        
        
        
        return filho;
    }

}
