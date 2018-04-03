/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.util.Comparator;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class CaminhoComparator implements Comparator<Caminho> {
    @Override
    public int compare(Caminho s1, Caminho s2) {
        //https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
        //O primeiro elemento de PriorityQueue é o menor elemento
        // em relação à ordem especificada. Se a ordem é natural,
        // (1<2<3<4...), funciona como uma lista de prioridades
        // mínima (maior prioridade = valor mais baixo).
        
        // Para definir um ordem natural, precisamos retornar:
        //  inteiro negativo se param1 vem antes de param2
        //  0 se param1 e param2 forem iguais
        //  inteiro positivo se param2 vem antes de param1
        return (int) s1.getFitness() - s2.getFitness();
    }
}
