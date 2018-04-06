
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
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
    
    /**
     * "Liga" o agente.
     */
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
        int geracao = 0;
        Random rand = new Random();
        
        PriorityQueue<Caminho> populacao = new PriorityQueue<>(10, new CaminhoComparator());
        // Fila de prioridades com valores de fitness (menor valor = prioridade alta)
        
        inicializaPopulacao(populacao);
        for(Caminho individuo : populacao)
            individuo.calculaFitness(labirinto);
        
        // Condiçao de parada -> numero de geraçoes
        while (geracao < 10)
        {
            geracao++;
            
            // maisAptos = seleciona n com maior fitness da populacao;
            // calcula aptidao usando aquilo da roleta (pra ver as chances de cada
            //  um ser escolhido nos cruzamentos
            // cruza pares de maisAptos, com chance pequena (de 1 até 5%) de mutacao
            // populacao.remove(maisFracos);
            // populacao.add(filhos);
        
            for(Caminho s : populacao)
            {
                s.calculaFitness(labirinto);
                
                if(rand.nextInt(99) < 1)
                {
                    s.mutacao(labirinto.getAltura(),labirinto.getLargura());
                }
            }
        }
    }
    private void inicializaPopulacao(PriorityQueue<Caminho> populacao) {
        Caminho individuo;
        int ymax = labirinto.getAltura();
        int xmax = labirinto.getLargura();
        
        for(int i=0; i<10; i++)
        {
            individuo = new Caminho(ymax, xmax);
            populacao.add(individuo);
        }
    }
    /**
     * Faz o agente percorrer a solução encontrada.
     */
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
