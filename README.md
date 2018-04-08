# SI_algoritmo_genetico

To-Do List:

## Iniciar População

(X) Iniciar população com N indivíduos (pode ser N=10, por exemplo) --> Gabriel

	Um indivíduo é um caminho que vai do início até a saída. Mais especificamente, um conjunto de 1 a 3 coordenadas (y,x) em que o caminho passa.
	
	Exemplos de caminhos:
	C1 = (1,1)
	C2 = (5,1) (6,3)
	C3 = (4,3) (9,9) (6,6)
	...

## Adequabilidade (Fitness)

	(X) Calcular Fitness --> Gabriel
	
		Fitness de um caminho = número de passos + numParedes*(algumPeso)
		(quanto mais paredes, pior o fitness)

		Quanto menor o valor, mais adequado/apto é.

		Imagine desenhar linhas retas indo do início até a 1a coordenada, da 1a pra 2a (se tiver) ... da última até a saída. O caminho propriamente dito é composto das posições (y,x) na grid que essa reta passa por cima.
	
## Cruzamento de 2 indivíduos

	(X) Função de aptidão para definir porcentagem de ser escolhido nos cruzamentos (aquilo lá da roleta) -> Jooj
	
	(X) Cruzamento de 2 caminhos com o mesmo número de coordenadas --> Gabriel
	
		Para cada par de coordenadas (uma de cada caminho), a coordenada (y,x) do caminho novo é (média dos Y, média dos X)
		
	(X) Cruzamento de caminhos com número diferente de coordenadas --> Lincoln
	
		Por exemplo: 1 caminho passa por 1 coordenada, outro passa por 3, ou 1 caminho passa por 2 coordenadas e outro passa por 3)
	
## Mutação

	(X) Mutação --> Lincoln
	
		Tem chance pequena de acontecer (1-5%).
		Altera levemente (+- 1 ou 2) o X ou o Y de uma das coordenadas do caminho.
	
## Mudança de gerações

	(X) Definir próxima geração -> Jooj
	
		Testar vários jeitos, depois ver qual o melhor
		( ) Simplesmente esquece os pais, fica com os N melhores filhos (nem precisa fazer se não quiser, a outra funciona bem)
		
		(X) Independente de ser pai ou filho, fica com os N melhores (deve funcionar melhor)
	
## Condição de parada

	(X) Enquanto (geração < 10)
