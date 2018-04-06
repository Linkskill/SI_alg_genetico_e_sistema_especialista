/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Saphira
 */
class Caminho {
    private Ponto pontoInicial;
    private Ponto pontoFinal;
    private ArrayList<Ponto> pontosIntermediarios;
    private ArrayList<Ponto> pontosCaminho;
    private int fitness;
    
    public Caminho (Ponto inicio, Ponto fim, int ymax, int xmax) {
        this(inicio, fim);
        randomPontos(ymax, xmax);
    }
    public Caminho(Ponto inicio, Ponto fim) {
        pontoInicial = inicio;
        pontoFinal = fim;
        pontosIntermediarios = new ArrayList<>();
        pontosCaminho = new ArrayList<>();
        fitness = Integer.MAX_VALUE;
    }
    /**
     * Inicializa os pontos intermediários com até
     * 3 coordenadas distinstas.
     * @param ymax Valor máximo de Y
     * @param xmax Valor máximo de X
     */
    private void randomPontos (int ymax, int xmax) {
        Random rand = new Random();
        int numPontos = rand.nextInt(3)+1; //de 1 a 3
        int x,y;
        Ponto ponto;
        
        while(pontosIntermediarios.size() < numPontos)
        {
            x = rand.nextInt(ymax);
            y = rand.nextInt(xmax);
            ponto = new Ponto(y,x);
            addIntermediario(ponto);
        }
    }
    private boolean addIntermediario(Ponto p){
        if (pontosIntermediarios.contains(p) ||
              p.equals(pontoInicial) ||
              p.equals(pontoFinal))
            return false;
        
        pontosIntermediarios.add(p);
        return true;
    }
    public Ponto getIntermediario(int i){
        if (i >= 0 && i < pontosIntermediarios.size())
            return pontosIntermediarios.get(i);
        return null;
    }
    public int getNumIntermediarios() { return pontosIntermediarios.size(); }
    
    /**
     * Calcula o fitness (adequabilidade) deste caminho.
     * Fitness depende do número de passos e número de
     * paredes no caminho.
     * Quanto menor o valor, mais adequado é.
     * @param labirinto Labirinto a que o caminho pertence.
     */
    public void calculaFitness(Labirinto labirinto) {
        ArrayList<Ponto> pontosQuePassa = new ArrayList<>();
        pontosQuePassa.add(pontoInicial);
        pontosQuePassa.addAll(pontosIntermediarios);
        pontosQuePassa.add(pontoFinal);
        
        montaCaminho(pontosQuePassa);
        
        fitness = pontosCaminho.size()-1;
        
        System.out.println("\n" + fitness);
        
        for (Ponto p : pontosCaminho)
            if (!labirinto.isAccessible(p))
                fitness += 10;
        
        System.out.println(fitness);
    }
    
    /**
     * Monta o caminho propriamente dito (vetor de coordenadas).
     * @param pontosQuePassa Pontos pelos quais o caminho tem que
     *        passar.
     */
    private void montaCaminho(ArrayList<Ponto> pontosQuePassa){
        Ponto p1, p2;
        for(int i=0; i < pontosQuePassa.size()-1; i++)
        {
            p1 = pontosQuePassa.get(i);
            p2 = pontosQuePassa.get(i+1);
            pontosCaminho.add(p1);
            montaSegmentoCaminho(p1, p2);
            pontosCaminho.add(p2);
        }
    }
    
    /**
     * Monta um segmento de caminho.
     * Imaginando uma linha reta indo de um ponto a outro,
     * o caminho é composto pelos pontos/posições no
     * grid que melhor se adequam à esta reta.
     * @param inicioSeg Ponto de início do segmento
     * @param fimSeg Ponto de fim do segmento
     */
    private void montaSegmentoCaminho(Ponto inicioSeg, Ponto fimSeg)
    {
        int x1,y1,x2,y2;
        x1 = inicioSeg.getX();
        y1 = inicioSeg.getY();
        x2 = fimSeg.getX();
        y2 = fimSeg.getY();

        int incremX = (x2 > x1) ? 1 : -1;
        int incremY = (y2 > y1) ? 1 : -1;

        int variacaoX = abs(x2-x1);
        int variacaoY = abs(y2-y1);
        double m = (double)variacaoY/variacaoX;
        //Equacao ponto-inclinação da reta: (y-y1) = m(x-x1)
        
        //Como (y1,x1) já foi colocado no caminho, pula ele
        x1 += incremX;
        y1 += incremY;

        int x, y;
        
        if (variacaoX == 0) {
            //Linha vertical
            for(y=y1; y != y2; y+=incremY)
                pontosCaminho.add(new Ponto(x1, y));
        }
        else if (variacaoY == 0) {
            //Linha horizontal
            for(x=x1; x != x2; x+=incremX)
                pontosCaminho.add(new Ponto(x, y1));
        }
        else if (m == 1) {
            //Linha diagonal perfeita (quando x anda 1, y anda 1)
            x = x1;
            y = y1;
            while(x != x2 && y != y2)
            {
                pontosCaminho.add(new Ponto(x, y));
                x+=incremX;
                y+=incremY;
            }
        }
        else if (m < 1) {
            //Diagonal com X variando mais do que Y
            //Uma mesma linha y pode ter vários pontos
            //pertencendo ao caminho
            double erro = m;
            
            y = y1;
            for(x=x1; x != x2; x+=incremX)
            {
                pontosCaminho.add(new Ponto(y,x));
                erro += m;
                
                if(erro >= 0.5) { //pulou pro próximo y
                    y += incremY;
                    erro -= 1.0;
                }
            }
        }
        else if (m > 1) {
            //Diagonal com Y variando mais
            //Uma mesma coluna x pode ter vários pontos
            //pertencendo ao caminho
            double erro = 1.0/m;
            
            x = x1;
            for(y=y1; y != y2; y+=incremY)
            {
                pontosCaminho.add(new Ponto(y,x));
                erro += 1.0/m;
                
                if(erro >= 0.5) { //pulou pro próximo x
                    x += incremX;
                    erro -= 1.0;
                }
            }
        }
    }
    public int getFitness() { return fitness; }

    /**
     * Escolhe uma coordenada qualquer e altera levemente
     * os valores de X e Y (soma ou subtrai 1 ou 2)
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
        
        /* Faz separado pra X e Y (ie. pode somar em um e
        subtrair no outro).
           Tenta alterar, se sair dos limites tenta com sinal
        negativo. Se com sinal negativo também sair, ignora
        (ou talvez poderia escolher outro ponto? Não faz
        muita diferença, só não vai conseguir fazer a
        mutação se a grid for muito pequena (< 5x5) */
        if(y+num >= 0 && y+num < altura)
            mutante.setY(y+num);
        else if (y-num >= 0 && y+num < altura)
            mutante.setY(y-num);
        
        if(x+num >= 0 && x+num < largura)
            mutante.setX(x+num);
        else if (x-num >= 0 && x+num < largura)
            mutante.setX(x-num);
    }
    
    public Caminho cruzamento(Caminho other) {
        Caminho filho = new Caminho(pontoInicial, pontoFinal);
        int mediaY, mediaX;
        
        if (getNumIntermediarios() == other.getNumIntermediarios())
            for (int i=0; i < getNumIntermediarios(); i++)
            {
                mediaY = (getIntermediario(i).getY() + other.getIntermediario(i).getY()) / 2;
                mediaX = (getIntermediario(i).getX() + other.getIntermediario(i).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
            }
        
        return filho;
    }
}
