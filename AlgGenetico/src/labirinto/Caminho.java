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
    public ArrayList<Ponto> getCaminho() {
        //como retornar uma cópia não modificável?
        return pontosCaminho;
    }
    public int getNumPontosCaminho() { return pontosCaminho.size(); }
    

    /**
     * Calcula o fitness (adequabilidade) deste caminho.
     * Fitness depende do número de passos e número de
     * paredes no caminho. Quanto menor o valor, mais
     * adequado é.
     * @param labirinto Labirinto a que o caminho pertence.
     */
    public void calculaFitness(Labirinto labirinto) {
        ArrayList<Ponto> pontosQuePassa = new ArrayList<>();
        pontosQuePassa.add(pontoInicial);
        pontosQuePassa.addAll(pontosIntermediarios);
        pontosQuePassa.add(pontoFinal);
        
        montaCaminho(pontosQuePassa);
        
        fitness = pontosCaminho.size()-1;
        for (Ponto p : pontosCaminho)
            if (!labirinto.isAccessible(p))
                fitness += 10;
    }
    
    /**
     * Monta o caminho propriamente dito (vetor de coordenadas).
     * @param pontosQuePassa Pontos pelos quais o caminho tem que
     *        passar.
     */
    private void montaCaminho(ArrayList<Ponto> pontosQuePassa){
        Ponto p1, p2;
        pontosCaminho.add(pontosQuePassa.get(0));
        for(int i=0; i < pontosQuePassa.size()-1; i++)
        {
            p1 = pontosQuePassa.get(i);
            p2 = pontosQuePassa.get(i+1);
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
        
        int x, y;
        
        if (variacaoX == 0) {
            //Linha vertical
            for(y=y1+incremY; y != y2; y+=incremY)
                pontosCaminho.add(new Ponto(y, x1));
        }
        else if (variacaoY == 0) {
            //Linha horizontal
            for(x=x1+incremX; x != x2; x+=incremX)
                pontosCaminho.add(new Ponto(y1, x));
        }
        else if (m == 1) {
            //Linha diagonal perfeita (quando x anda 1, y anda 1)
            x = x1+incremX;
            y = y1+incremY;
            while(x != x2 && y != y2)
            {
                pontosCaminho.add(new Ponto(y, x));
                x+=incremX;
                y+=incremY;
            }
        }
        else if (m < 1) {
            /* Diagonal com X variando mais do que Y
                - Cada coluna x tem só um ponto no caminho
                - Uma mesma linha y pode ter vários pontos
                   pertencendo ao caminho
                - Quando x aumenta em 1, y aumenta em m */
            double erro = m;
            
            y = y1;
            for(x=x1+incremX; x != x2; x+=incremX)
            {
                pontosCaminho.add(new Ponto(y, x));
                erro += m;
                
                if(erro >= 0.5) { //pulou pro próximo y
                    y += incremY;
                    erro -= 1.0;
                }
            }
        }
        else if (m > 1) {
            /* Diagonal com Y variando mais
                - Cada linha y tem só um ponto no caminho
                - Uma mesma coluna x pode ter vários pontos
                    pertencendo ao caminho
                - Quando y aumenta em 1, x aumenta em 1.0/m */
            double erro = 1.0/m;
            
            x = x1;
            for(y=y1+incremY; y != y2; y+=incremY)
            {
                pontosCaminho.add(new Ponto(y, x));
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
     * Escolhe um dos pontos intermediários e altera levemente
     * os valores de X e Y (soma ou subtrai 1 ou 2). Tanto a
     * operação (soma ou subtração) quanto o valor (1 ou 2)
     * pode ser diferente do X pro Y.
     * Ex: soma +1 no X, subtrai -2 do Y
     */
    public void mutacao(int altura, int largura) {
        Random rand = new Random();
        
        //Escolhe o quanto o X vai mudar
        int alteracaoX;
        do {
            alteracaoX = rand.nextInt(5)-2; //-2 até 2
        } while (alteracaoX == 0);
        
        //Escolhe o quanto o Y vai mudar
        int alteracaoY;
        do {
            alteracaoY = rand.nextInt(5)-2; //-2 até 2
        } while (alteracaoY == 0);
        
        //Escolhe um dos pontos intermediários pra mudar
        int index = rand.nextInt(pontosIntermediarios.size());
        Ponto escolhido = pontosIntermediarios.get(index);
        int y = escolhido.getY();
        int x = escolhido.getX();
        
        Ponto mutante = new Ponto(y,x);
        //Verifica se a alteração iria sair do grid,
        //se iria então tenta com sinal invertido.

        //Se com sinal invertido também sairia da grid,
        //não modifica (pode acontecer quando alguma
        //das dimensões da grid é < 4)
        if(y+alteracaoY >= 0 && y+alteracaoY < altura)
            mutante.setY(y+alteracaoY);
        else if (y-alteracaoY >= 0 && y-alteracaoY < altura)
            mutante.setY(y-alteracaoY);
        
        if(x+alteracaoX >= 0 && x+alteracaoX < largura)
            mutante.setX(x+alteracaoX);
        else if (x-alteracaoX >= 0 && x-alteracaoX < largura)
            mutante.setX(x-alteracaoX);
        
        //Se já tem um ponto igual a esse mutante, remove ele
        if (pontosIntermediarios.contains(mutante))
            pontosIntermediarios.remove(mutante);

        escolhido.setY(mutante.getY());
        escolhido.setX(mutante.getX());
    }
    
    /**
     * Cruza 2 caminhos e retorna o caminho-filho.
     * @param other Caminho a ser cruzado com this.
     * @return Filho desses caminhos, combinando
     *         seus pontos intermediários de
     *         alguma forma.
     */
    public Caminho cruzamento(Caminho other) {
        Caminho filho = new Caminho(pontoInicial, pontoFinal);
        int mediaY, mediaX;
        
        //Se igual, junta pontosIntermediarios 1 a 1
        if (getNumIntermediarios() == other.getNumIntermediarios())
            for (int i=0; i < getNumIntermediarios(); i++)
            {
                mediaY = (getIntermediario(i).getY() + other.getIntermediario(i).getY()) / 2;
                mediaX = (getIntermediario(i).getX() + other.getIntermediario(i).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
            }
        else { //Número diferente de pontos intermediários nos 2 caminhos
            //Um com 1 e outro com N
            if(getNumIntermediarios() == 1){
                for (int i=0; i < other.getNumIntermediarios(); i++){
                    mediaY = (getIntermediario(0).getY() + other.getIntermediario(i).getY()) / 2;
                    mediaX = (getIntermediario(0).getX() + other.getIntermediario(i).getX()) / 2;
                    filho.addIntermediario(new Ponto(mediaY, mediaX));
                }
            }
            else if(other.getNumIntermediarios() == 1){
                for (int i=0; i < getNumIntermediarios(); i++){
                    mediaY = (getIntermediario(i).getY() + other.getIntermediario(0).getY()) / 2;
                    mediaX = (getIntermediario(i).getX() + other.getIntermediario(0).getX()) / 2;
                    filho.addIntermediario(new Ponto(mediaY, mediaX));
                }
            }
            //Um com 2 e outro com 3
            else if(getNumIntermediarios() == 2 && other.getNumIntermediarios() == 3){
                mediaY = (getIntermediario(0).getY() + other.getIntermediario(0).getY()) / 2;
                mediaX = (getIntermediario(0).getX() + other.getIntermediario(0).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
                
                mediaY = (getIntermediario(1).getY() + other.getIntermediario(1).getY()) / 2;
                mediaX = (getIntermediario(1).getX() + other.getIntermediario(1).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
                
                mediaY = (getIntermediario(1).getY() + other.getIntermediario(2).getY()) / 2;
                mediaX = (getIntermediario(1).getX() + other.getIntermediario(2).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
            }
            else if(other.getNumIntermediarios() == 2 && getNumIntermediarios() == 3){
                mediaY = (getIntermediario(0).getY() + other.getIntermediario(0).getY()) / 2;
                mediaX = (getIntermediario(0).getX() + other.getIntermediario(0).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
                
                mediaY = (getIntermediario(1).getY() + other.getIntermediario(1).getY()) / 2;
                mediaX = (getIntermediario(1).getX() + other.getIntermediario(1).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
                
                mediaY = (getIntermediario(2).getY() + other.getIntermediario(1).getY()) / 2;
                mediaX = (getIntermediario(2).getX() + other.getIntermediario(1).getX()) / 2;
                filho.addIntermediario(new Ponto(mediaY, mediaX));
            }
        }
        
        return filho;
    }
    @Override
    public String toString(){
        String str = "";
        for(Ponto p : pontosIntermediarios)
            str = str.concat(p + " ");
        return str;
    }
}
