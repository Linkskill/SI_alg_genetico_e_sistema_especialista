
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

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
        PriorityQueue<Solucao> populacao = new PriorityQueue<>(10, new SolucaoComparator());
        // Sempre tira os mais aptos (valor mais alto)
        
        inicializaPopulacao();
        for(Solucao s : populacao)
            s.calculaFitness();
        
        while (!condicao_de_parada)
        {
            geracao++;
            // maisAptos = seleciona n com maior fitness da populacao;
            // calcula aptidao usando aquilo da roleta (pra ver as chances de cada
            //  um ser escolhido nos cruzamentos
            // cruza pares de maisAptos, com chance pequena (de 1 até 5%) de mutacao
            // populacao.remove(maisFracos);
            // populacao.add(filhos);
            for (Solucao s : populacao)
                s.calculaFitness();
        }
    }
    public void inicializaPopulacao() {
        
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
