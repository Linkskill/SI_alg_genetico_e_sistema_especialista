/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

/**
 *
 * @author Gabriel Eugenio, Lincoln Batista e Jorge Straub
 */
public class Labirinto {
    private final int altura;
    private final int largura;
    private char[][] cells;
    private Agente agente;
    private Estado start;
    private Estado exit;
    
    public Labirinto (int alt, int larg, char[][] matrix){
        altura = alt;
        largura = larg;
        cells = matrix;
        /* Salva o ponto de start e exit para fácil acesso */
        for(int i=0; i < altura; i++)
            for(int j=0; j < largura; j++)
                if(cells[i][j] == 'S') 
                    start = new Estado(i, j);
                else if(cells[i][j] == 'E')
                    exit = new Estado(i, j);
        agente = null;
    }
    /**
     * Posiciona o agente na matriz (se existir
     * um agente) e chama o método print().
     */
    public void show(){
        if(agente != null) {
            /* Antes de imprimir coloca o agente na matriz. Depois
            retorna ela à representação original. */
            int xAtual = agente.getEstadoAtual().getX();
            int yAtual = agente.getEstadoAtual().getY();

            char original = cells[yAtual][xAtual];
            cells[yAtual][xAtual] = 'A';
            print();
            cells[yAtual][xAtual] = original;
        } else {
            /* Se o agente ainda não está funcionando, somente
            imprime o labirinto */
            print();
        }
    }
    /**
     * Imprime a grid do labirinto.
     * Legenda:
     *   "   " = posição acessível
     *   "XXX" = parede
     *   " S " = start
     *   " E " = exist
     *   " A " = agente
     */
    private void print(){
        String linhaSeparadora = "+";
        for(int j=0; j < largura; j++)
            linhaSeparadora = linhaSeparadora.concat("---+");
        
        /* Número das colunas */
        System.out.print("\n ");
        for(int j=0; j < largura; j++)
            System.out.print(" " + j + "  ");
        System.out.println("\n" + linhaSeparadora);
        
        /* Número das linhas e o labirinto em si */
        for(int i=0; i < altura; i++){
            System.out.print("|");
            for(int j=0; j < largura; j++)
                switch (cells[i][j]) {
                    case '0':
                        System.out.print("   |");
                        break;
                    case '1':
                        System.out.print("XXX|");
                        break;
                    default:
                        System.out.print(" " + cells[i][j] + " |");
                        break;
                }
            System.out.println(" " + i);
            System.out.println(linhaSeparadora);
        }
        System.out.println();
    }
    /** 
     * Indica se a coordenada indicada é acessível na grid.
     * @param coordenada Coordenada a ser verificada.
     * @return True se é acessível, false se está fora dos
     *         limites ou se é uma parede.
     */
    public boolean isAccessible(Coordenada coordenada) {
        int row = coordenada.getY();
        int col = coordenada.getX();
        boolean estaForaDosLimites = row < 0 || col < 0
                || row >= altura || col >= largura;
        if(estaForaDosLimites || cells[row][col] == '1')
            return false;
        return true;
    }
    public void setAgente(Agente a){
        agente = a;
    }
    public int getAltura() { return altura; }
    public int getLargura() { return largura; }
    public Estado getStart(){ return start; }
    public Estado getExit() { return exit; }
}