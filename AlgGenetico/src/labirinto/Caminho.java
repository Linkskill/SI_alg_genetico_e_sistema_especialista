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
    private ArrayList<Ponto> pontosIntermediarios;
    private int fitness;
    
    public Caminho (int ymax, int xmax) {
        pontosIntermediarios = new ArrayList<>();
        
        randomPontos(ymax, xmax);
        fitness = Integer.MAX_VALUE;
        
        for(Ponto c : pontosIntermediarios)
            System.out.print("(" + c.getY() + "," + c.getX() + ") ");
        System.out.println();
    }
    public Caminho() {
        pontosIntermediarios = new ArrayList<>();
        fitness = Integer.MAX_VALUE;
    }
    /**
     * Inicializa os pontos intermediários com até 3 coordenadas
     * distinstas.
     * @param numPontos Número de pontos a serem colocados no 
     * @param ymax Valor máximo de Y
     * @param xmax Valor máximo de X
     */
    private void randomPontos (int ymax, int xmax) {
        Random rand = new Random();
        int numPontos = rand.nextInt(3)+1; //de 1 a 3
        int x,y;
        Ponto ponto;
        
        for (int i=1; i <= numPontos; i++)
        {
            do {
                x = rand.nextInt(ymax);
                y = rand.nextInt(xmax);
                ponto = new Ponto(y,x);
            } while (pontosIntermediarios.contains(ponto));
            
            pontosIntermediarios.add(ponto);
        }
    }
    private void addPonto(Ponto p){
        pontosIntermediarios.add(p);
    }
    public Ponto getPonto(int i){
        if (i < pontosIntermediarios.size())
            return pontosIntermediarios.get(i);
        return null;
    }
    public void calculaFitness() {
        
    }
    public int getFitness() { return fitness; }
    public int getSize() { return pontosIntermediarios.size(); }

    public void mutacao() {
        // escolhe uma coordenada qualquer, faz X ou Y +- 1 ou 2
        // tem que modificar um ponto existente, não criar outro
        // se não der certo assim tenta remover da lista, fazer mutação e colocar de novo
        Random rand = new Random();
        int num = rand.nextInt(5);
        int index = rand.nextInt(pontosIntermediarios.size());
        Ponto mutante = pontosIntermediarios.get(index);
        int y = mutante.getY();
        int x = mutante.getX();
        
        if(y+num-2 > 0 && x+num-2 > 0){
            mutante.setY(y+num-2);
            mutante.setX(x+num-2);
        }
        else{
            num = rand.nextInt(3);
            mutante.setY(y+num);
            mutante.setX(x+num);
        }
        
    }
    public Caminho cruzar(Caminho other) {
        Caminho filho = new Caminho();
        int mediaY, mediaX;
        
        if (getSize() == other.getSize())
            for (int i=0; i < getSize(); i++)
            {
                mediaY = (getPonto(i).getY() + other.getPonto(i).getY()) / 2;
                mediaX = (getPonto(i).getX() + other.getPonto(i).getX()) / 2;
                filho.addPonto(new Ponto(mediaY, mediaX));
            }
        
        return filho;
    }

}
