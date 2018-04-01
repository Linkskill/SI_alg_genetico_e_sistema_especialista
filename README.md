# SI_algoritmo_genetico

To-Do List:

( ) Implementar algoritmo genético <br>
	( ) Iniciar população
		Cada "indivíduo" é uma possível solução para o labirinto.
		Um conjunto de até 3 coordenadas (y,x) em que o caminho passa.
		Imagine linhas retas indo do início até a 1a coordenada, da 1a pra 2a (se tiver) ... e da última até a saída. O caminho propriamente dito são as posições (y,x) que essa reta passa por cima.

	( ) Calcular fitness
		Quanto menor, mais adequado (apto) é
		Considera o número de passos e se passa por paredes
		
	( ) Cruzamento de 2 indivíduos
		( ) Função de aptidão para definir porcentagem de ser escolhido nos cruzamentos (aquilo lá da roleta)
		
	( ) Mutação
		Tem chance pequena de acontecer, vai mudar levemente (+- 1 ou 2) uma das coordenadas do caminho.
		
	( ) Definir próxima geração
		Testar vários jeitos, depois ver qual o melhor
		( ) Simplesmente esquece os pais, fica com os N melhores filhos
		( ) Independente de ser pai ou filho, fica com os N melhores
