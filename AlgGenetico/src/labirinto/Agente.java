
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.List;
import java.util.ArrayList;
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
        boolean validSolution = walkThroughSolution();

        if(validSolution) {
            System.out.println("Resolvido!");
            System.out.println("Número de passos dados: " + (solucao.size()-1));
            //-1 pois a solução inclui o estado inicial
        } else {
            System.out.println("Nao encontrou solucao valida!");
            System.out.println("Precisou atravessar alguma parede!");
        }
        System.out.print("Caminho: ");
        for (Estado e : solucao)
            System.out.print(e + " ");
        System.out.println();
    }

    public void algGenetico() {
        Random rand = new Random();

        List<Caminho> populacao = new ArrayList<>();
        CaminhoComparator comparator = new CaminhoComparator();
        
        int geracao = 0;
        int N=10;
        inicializaPopulacao(populacao, N);
        populacao.sort(comparator); //ordena por fitness

        Caminho escolhido1, escolhido2, filho;
       
        while (geracao < 10)
        {
            System.out.println("\nGeracao: " + geracao);
            System.out.println("Populacao");
            for(Caminho individuo : populacao)
            {
                System.out.print(individuo);
                System.out.println(" com fitness " + individuo.getFitness());
            }

            calculaAptidoes(populacao);
            
            // Cruza pares de indivíduos
            for(int i=0; i < N; i++)
            {
                escolhido1 = rodaARoleta(populacao);
                do {
                    escolhido2 = rodaARoleta(populacao);
                } while (escolhido1.equals(escolhido2));
                
                System.out.println("\nVai cruzar: ");
                System.out.print(escolhido1);
                System.out.println(" com fitness " + escolhido1.getFitness());
                System.out.print(escolhido2);
                System.out.println(" com fitness " + escolhido2.getFitness());
                filho = escolhido1.cruzamento(escolhido2);
                System.out.println("Filho: " + filho);
                
                if(rand.nextInt(100) < 5) {
                    System.out.println("Teve mutação");
                    filho.mutacao(labirinto.getAltura(), labirinto.getLargura());
                    System.out.println("Ficou: " + filho);
                }
                filho.calculaFitness(labirinto);
                
                //Só adiciona na população se não tiver ninguém igual.
                //Isso evita convergências (ie. ter um monte de
                //indivíduos iguais que, quando cruzados, só geram
                //outros iguais à ele).
                if(populacao.contains(filho))
                    populacao.add(filho);
            }
            
            //Remove os mais fracos, fica somente com
            //os N melhores pra nova população
            populacao.sort(comparator);
            for(int i=populacao.size()-1; i >= N; i--)
                populacao.remove(i);
            
            geracao++;
        }
        
        /* Monta o caminho de estados que o agente
        vai percorrer na melhor solução encontrada. */
        Caminho best = populacao.get(0);
        System.out.println("\n\nPontos intermediarios do melhor caminho: " + best);
        for(Ponto p : best.getCaminho())
            solucao.add(new Estado(p));
    }
    
    private void inicializaPopulacao(List<Caminho> populacao, int N) {
        Caminho individuo;
        int ymax = labirinto.getAltura();
        int xmax = labirinto.getLargura();
        Ponto start = labirinto.getStart().getPonto();
        Ponto exit = labirinto.getExit().getPonto();
        
        for(int i=0; i < N; i++)
        {
            individuo = new Caminho(start, exit, ymax, xmax);
            individuo.calculaFitness(labirinto);
            populacao.add(individuo);
        }
    }

    private void calculaAptidoes(List<Caminho> populacao){
        // Para a roleta funcionar, "mais apto" precisa significar
        // fitness MAIOR.
        //    Como no nosso caso "mais apto" significa ter um
        // fitness MENOR, precisamos inverter o fitness de todo
        // mundo para calcular as aptidões corretamente (menor
        // fitness == mais chances).
        double totalFitnessInvertido = 0;
        for(Caminho individuo : populacao)
            totalFitnessInvertido += 1.0/individuo.getFitness();

        double aptidao;
        for(Caminho individuo : populacao)
        {
            aptidao = ((1.0/individuo.getFitness())/totalFitnessInvertido)*100;
            individuo.setAptidao(aptidao);
        }
    }

    /**
     * Retorna um indivíduo da população. Indivíduos mais aptos
     * possuem maior aptidão, e portanto mais chances de serem
     * escolhidos.
     * @param populacao Conjunto de indivíduos
     * @return Um dos indivíduos.
     */
    private Caminho rodaARoleta(List<Caminho> populacao){
        Random rand = new Random();

        double valorSorteado = rand.nextDouble()*100; //[0..100)
        double somaPorcentagens=0;
        
        for(Caminho individuo : populacao) {
            somaPorcentagens += individuo.getAptidao();
            if(somaPorcentagens >= valorSorteado)
                return individuo;
        }
        return populacao.get(populacao.size()-1);
    }
    
    private boolean walkThroughSolution() {
        boolean validMove, validSolution=true;
        for(Estado e : solucao)
        {
            validMove = move(e);
            if(!validMove)
                validSolution = false;
            labirinto.show();
        }
        return validSolution;
    }
    private boolean move(Estado e){
        estadoAtual = e;

        return labirinto.isAccessible(e.getPonto());
    }
    public Estado getEstadoAtual(){ return estadoAtual; }
}
