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
    private ArrayList<Ponto> pontosCaminho;
    private int fitness;
    
    public Caminho (int ymax, int xmax) {
        pontosIntermediarios = new ArrayList<>();
        randomPontos(ymax, xmax);
        fitness = Integer.MAX_VALUE;
    }
    public Caminho() {
        pontosIntermediarios = new ArrayList<>();
        fitness = Integer.MAX_VALUE;
    }
    /**
     * Inicializa os pontos intermediários com até 3 coordenadas
     * distinstas.
     * @param ymax Valor máximo de Y
     * @param xmax Valor máximo de X
     */
    private void randomPontos (int ymax, int xmax) {
        Random rand = new Random();
        int numPontos = rand.nextInt(3)+1; //de 1 a 3
        int x,y;
        Ponto ponto;
        
        //TODO: não pode ser nem a entrada nem a saída do labirinto
        
        while(pontosIntermediarios.size() <= numPontos)
        {
            x = rand.nextInt(ymax);
            y = rand.nextInt(xmax);
            ponto = new Ponto(y,x);
            addIntermediario(ponto);
        }
    }
    private boolean addIntermediario(Ponto p){
        if (pontosIntermediarios.contains(p))
            return false;
        
        pontosIntermediarios.add(p);
        return true;
    }
    public Ponto getIntermediario(int i){
        if (i >= 0 && i < pontosIntermediarios.size())
            return pontosIntermediarios.get(i);
        return null;
    }
    public void calculaFitness(Labirinto labirinto) {
        Ponto start = labirinto.getStart().getPonto();
        Ponto exit = labirinto.getExit().getPonto();
        
        ArrayList<Ponto> pontosQuePassa = new ArrayList<>();
        pontosQuePassa.add(start);
        pontosQuePassa.addAll(pontosIntermediarios);
        pontosQuePassa.add(exit);
        
        int x1,y1,x2,y2,x,y;
        int variacaoX, variacaoY;
        int incremX, incremY;

        Ponto ponto1, ponto2;
        int dist1AteExit, dist2AteExit;
        
        // https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        
        //TODO: para cada linha reta (start-->c1, c1-->c2, c2-->c3, c3-->exit)
        //distAteExit precisa ser distAteProx
        //x2 e y2 precisam ser prox.getX e prox.getY
        x1 = start.getX();
        y1 = start.getY();
        x2 = pontosIntermediarios.get(0).getX();
        y2 = pontosIntermediarios.get(0).getY();
        variacaoX = x2-x1;
        variacaoY = y2-y1;

        incremX = (x2 > x1) ? 1 : -1;
        incremY = (y2 > y1) ? 1 : -1;
        
        if (variacaoX == 0) {
            //Caminho é exatamente na vertical
            for(y=y1; y != y2; y+=incremY)
                pontosCaminho.add(new Ponto(x1, y));
        }
        else if (variacaoY == 0) {
            //Caminho é exatamente na horizontal
            for(x=x1; x != x2; x+=incremX)
                pontosCaminho.add(new Ponto(x, y1));
        }
        else if (variacaoX == variacaoY) {
            //Caminho é exatamente na diagonal
            x = x1;
            y = y1;
            while(x != x2 && y != y2)
            {
                pontosCaminho.add(new Ponto(x, y));
                x+=incremX;
                y+=incremY;
            }
        }
        else if (variacaoX > variacaoY) {
            //Para cada coluna x, temos um ponto que faz parte do caminho
            //No entanto, para cada linha y podemos ter vários pontos
            y = y1;
            for(x=x1; x != x2; x+=incremX)
            {
                ponto1 = new Ponto(x, y);
                ponto2 = new Ponto(x, y+1);
                dist1AteExit = ponto1.distanciaManhattan(exit);
                dist2AteExit = ponto2.distanciaManhattan(exit);
                if (dist1AteExit < dist2AteExit)
                    pontosCaminho.add(ponto1);
                else {
                    pontosCaminho.add(ponto2);
                    y++;
                }
            }
        }
        else if (variacaoY > variacaoX) {
            //Para cada linha y, temos um ponto que faz parte do caminho
            //No entanto, para cada coluna x podemos ter vários pontos
            x = x1;
            for(y=y1; y != y2; y+=incremY)
            {
                ponto1 = new Ponto(x, y);
                ponto2 = new Ponto(x+1, y);
                dist1AteExit = ponto1.distanciaManhattan(exit);
                dist2AteExit = ponto2.distanciaManhattan(exit);
                if (dist1AteExit < dist2AteExit)
                    pontosCaminho.add(ponto1);
                else {
                    pontosCaminho.add(ponto2);
                    x++;
                }
            }
        }
        
        //TODO: fitness = pontosCaminho.size()
        //      for ponto in pontosCaminho
        //       if labirinto.isWall(y, x)  fitness += 10
        
    }
    public int getFitness() { return fitness; }
    public int getNumPontos() { return pontosIntermediarios.size(); }

    /**
     * Escolhe uma coordenada qualquer, faz X ou Y +- 1 ou 2
     */
    public void mutacao(int altura, int largura) {
        Random rand = new Random();
        int num;
        do {
            num = rand.nextInt(5)-2; //-2 até 2
        } while (num == 0);
        
        int index = rand.nextInt(pontosIntermediarios.size());
        Ponto mutante = pontosIntermediarios.get(index);
        int y = mutante.getY();
        int x = mutante.getX();
        
        //Faz separado pra X e Y (ie. pode somar no X e subtrair no Y)
        if(y+num >= 0 && y+num < altura) //tenta somar
            mutante.setY(y+num);
        else if (y-num >= 0 && y+num < altura) //tenta subtrair
            mutante.setY(y-num);
        
        if(x+num >= 0 && x+num < largura)
            mutante.setX(x+num);
        else if (x-num >= 0 && x+num < largura)
            mutante.setX(x-num);
    }
    public Caminho cruzar(Caminho other) {
        Caminho filho = new Caminho();
        int mediaY, mediaX;
        
        if (getNumPontos() == other.getNumPontos())
            for (int i=0; i < getNumPontos(); i++)
            {
                mediaY = (getIntermediario(i).getY() + other.getIntermediario(i).getY()) / 2;
                mediaX = (getIntermediario(i).getX() + other.getIntermediario(i).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
            }
        
        return filho;
    }
}
