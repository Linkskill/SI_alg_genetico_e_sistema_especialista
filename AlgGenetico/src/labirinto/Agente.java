
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

        int geracao = 0;
        PriorityQueue<Caminho> populacao = new PriorityQueue<>(10, new CaminhoComparator());
        // Fila de prioridades com valores de fitness (menor valor = prioridade alta)
        
        inicializaPopulacao(populacao);
        for(Caminho individuo : populacao)
        {
            for(int i=0; i < individuo.getNumIntermediarios(); i++)
                System.out.print(individuo.getIntermediario(i) + " ");
            
            individuo.calculaFitness(labirinto);
        }
        
        ArrayList<Caminho> maisAptos = new ArrayList<>();
//        while (geracao < 10)
//       {
            geracao++;
            
            for(int i=0; i < 5; i++)
                maisAptos.add(populacao.poll());
            
            // calcula aptidao usando aquilo da roleta (pra ver as chances de cada
            //  um ser escolhido nos cruzamentos)
            
            // cruza pares de maisAptos
            
            for(Caminho filho : maisAptos) //na verdade é pra cada filho
            {
//                if(rand.nextInt(99) < 1)
                    filho.mutacao(labirinto.getAltura(), labirinto.getLargura());

                System.out.print("\nFez mutação: ");
                for(int i=0; i < filho.getNumIntermediarios(); i++)
                    System.out.print(filho.getIntermediario(i) + " ");

                filho.calculaFitness(labirinto);
                populacao.add(filho);
            }
            
            // populacao = 10 melhores de populacao
//        }
    }
    private void inicializaPopulacao(PriorityQueue<Caminho> populacao) {
        Caminho individuo;
        int ymax = labirinto.getAltura();
        int xmax = labirinto.getLargura();
        Ponto start = labirinto.getStart().getPonto();
        Ponto exit = labirinto.getExit().getPonto();
        
        for(int i=0; i<10; i++)
        {
            individuo = new Caminho(start, exit, ymax, xmax);
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
