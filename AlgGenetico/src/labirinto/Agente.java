
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class Agente {
    private Labirinto labirinto;
    private Estado estadoAtual;
    private List<Estado> solucao;
    
    public Agente(Labirinto lab){
        labirinto = lab; 
        labirinto.setAgente(this);
        
        solucao = new ArrayList<>();
    }
    
    public void run(){
        System.out.println("Procurando solução...");
        algGenetico();

        System.out.println("Percorrendo caminho...");
        walkThroughSolution();

        System.out.println("Número de passos dados: " + (solucao.size()-1));
        //-1 pois a solução inclui o estado inicial
        
        for (Estado e : solucao)
            System.out.print(e + " ");
        System.out.println();
    }

    public void algGenetico() {
        Random rand = new Random();

        List<Caminho> populacao = new ArrayList<>();
        CaminhoComparator comparator = new CaminhoComparator();
        
        inicializaPopulacao(populacao);
        populacao.sort(comparator); //ordena por fitness

        int geracao = 0;
        int ind1, ind2;
        Caminho escolhido1, escolhido2, filho;
       
        while (geracao < 5)
        {
            System.out.println("Geracao: " + geracao);
            System.out.println("\nPopulacao");
            for(Caminho individuo : populacao)
            {
                System.out.print(individuo);
                System.out.println(" com fitness " + individuo.getFitness());
            }
            
            // calcula aptidao de cada um usando aquilo da roleta
            
            // cruza pares (os com melhor fitness tem mais
            // chances de serem escolhidos por conta desse
            // negócio da roleta)
            for(int i=0; i < 10; i++)
            {
                //Por enquanto só escolhe 2 aleatórios,
                //falta fazer o negócio da roleta
                ind1 = rand.nextInt(populacao.size());
                do {
                    ind2 = rand.nextInt(populacao.size());
                } while (ind1 == ind2);
                
                escolhido1 = populacao.get(ind1);
                escolhido2 = populacao.get(ind2);
                
                System.out.println("Vai cruzar: ");
                System.out.println(escolhido1);
                System.out.println(escolhido2);
                filho = escolhido1.cruzamento(escolhido2);
                System.out.print("Filho: ");
                System.out.println(filho);
                
                if(rand.nextInt(100) < 5) {
                    System.out.println("Teve mutação");
                    
                    filho.mutacao(labirinto.getAltura(), labirinto.getLargura());

                    System.out.print("Ficou: ");
                    System.out.println(filho+"\n");
                }
                filho.calculaFitness(labirinto);
                populacao.add(filho);
            }
            
            //Remove os mais fracos, fica somente com
            //os 10 melhores pra nova geração
            populacao.sort(comparator);
            for(int i=populacao.size()-1; i >= 10; i--)
                populacao.remove(i);
            
            geracao++;
        }
        
        /* Monta o caminho de estados que o agente
        percorre nessa solucao. */
        Caminho best = populacao.get(0);
        for(Ponto p : best.getCaminho())
            solucao.add(new Estado(p));
    }
    
    private void inicializaPopulacao(List<Caminho> populacao) {
        Caminho individuo;
        int ymax = labirinto.getAltura();
        int xmax = labirinto.getLargura();
        Ponto start = labirinto.getStart().getPonto();
        Ponto exit = labirinto.getExit().getPonto();
        
        for(int i=0; i<10; i++)
        {
            individuo = new Caminho(start, exit, ymax, xmax);
            individuo.calculaFitness(labirinto);
            populacao.add(individuo);
        }
    }
    
    public void walkThroughSolution(){
        for(Estado e : solucao)
        {
            move(e);
            labirinto.show();
        }
    }
    private void move(Estado e){
        estadoAtual = e;
    }
    public Estado getEstadoAtual(){ return estadoAtual; }
}
