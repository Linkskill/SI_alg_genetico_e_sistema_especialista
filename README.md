# SI_algoritmo_genetico

To-Do List:

( ) Implementar algoritmo genético <br>
	( ) Iniciar população com N indivíduos (pode ser N=10, por exemplo)
		Um indivíduo é um caminho que vai do início até a saída. Mais especificamente, um conjunto de 1 a 3 coordenadas (y,x) em que o caminho passa.
		Exemplos de caminhos:
			C1 = (1,1)
			C2 = (5,1) (6,3)
			C3 = (4,3) (9,9) (6,6)
			...

		
	( ) Calcular fitness
		Imagine desenhar linhas retas indo do início até a 1a coordenada, da 1a pra 2a (se tiver) ... da última até a saída. O caminho propriamente dito é composto das posições (y,x) na grid que essa reta passa por cima.
		Quanto menor o valor, mais adequado/apto é.
		Fitness de um caminho = número de passos + numParedes*(algumPeso)
		(quanto mais paredes, pior o fitness)
		
	( ) Cruzamento de 2 indivíduos
		( ) Função de aptidão para definir porcentagem de ser escolhido nos cruzamentos (aquilo lá da roleta)
		Pra juntar 2 caminhos:
		( ) Se eles tem o mesmo número de coordenadas
			Para cada par de coordenadas (uma de cada caminho), a coordenada (y,x) do caminho novo é (média dos Y, média dos X)
		( ) Se tem número de coordenadas diferentes (por exemplo: 1 caminho passa por 1 coordenada, outro passa por 3) -> Lincoln
			De duas uma: ou combina cada uma das 3 coordenadas com a única do outro caminho, ou escolhe uma das 3 pra combinar e ignora as outras
		
	( ) Mutação -> Lincoln
		Tem chance pequena de acontecer (1-5%).
		Altera levemente (+- 1 ou 2) o X ou o Y de uma das coordenadas do caminho.
		
	( ) Definir próxima geração
		Testar vários jeitos, depois ver qual o melhor
		( ) Simplesmente esquece os pais, fica com os N melhores filhos
		( ) Independente de ser pai ou filho, fica com os N melhores (deve funcionar melhor)
		
	( ) Condição de parada
		Enquanto (geração < 10)
