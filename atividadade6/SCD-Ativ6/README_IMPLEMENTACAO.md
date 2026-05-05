# Atividade: RPC Multiplataforma com ICE em Java

Esta implementação contém:

- `Printer.ice`: interface remota usada por cliente e servidor;
- `Client.java`: cliente Java que chama todos os métodos remotos;
- `PrinterI.java`: implementação concreta da interface no servidor Java;
- `Server.java`: servidor Java que registra o objeto remoto `SimplePrinter`;
- scripts auxiliares de compilação e execução.

## 1. Interface remota

A interface usada pela atividade é:

```ice
module Demo
{
    interface Printer
    {
        string printString(string texto);
        string printUpperCase(string texto);
        string printLowerCase(string texto);
        int countCharacters(string texto);
    }
}
```

Cliente e servidor precisam usar exatamente essa mesma interface.

## 2. Dependências

Instale o JDK e o ICE conforme o README original da tarefa.

Baixe o runtime Java do ICE:

```bash
wget https://repo1.maven.org/maven2/com/zeroc/ice/3.7.11/ice-3.7.11.jar
```

## 3. Gerar os arquivos Java da interface

Execute:

```bash
slice2java Printer.ice
```

Esse comando cria a pasta `Demo/` com os arquivos Java gerados automaticamente pelo ICE.

## 4. Compilar

```bash
javac -cp ".:ice-3.7.11.jar" Client.java Server.java PrinterI.java Demo/*.java
```

Ou use:

```bash
./build.sh
```

## 5. Executar o servidor Java

```bash
java -cp ".:ice-3.7.11.jar" Server 11000 SimplePrinter
```

Ou use:

```bash
./run_server.sh
```

## 6. Executar o cliente Java na mesma máquina

```bash
java -cp ".:ice-3.7.11.jar" Client 127.0.0.1 11000 SimplePrinter "Hello from Goiania!"
```

Ou use:

```bash
./run_client.sh 127.0.0.1 11000 SimplePrinter "Hello from Goiania!"
```

## 7. Executar o cliente Java com servidor em outra máquina

Na máquina do servidor, execute o servidor Python do Exercício 05 ou o servidor Java desta implementação.

Na máquina do cliente, execute:

```bash
java -cp ".:ice-3.7.11.jar" Client IP_DO_SERVIDOR PORTA SimplePrinter "Texto de teste"
```

Exemplo:

```bash
java -cp ".:ice-3.7.11.jar" Client 192.168.0.10 11000 SimplePrinter "Hello from Goiania!"
```

## 8. Observação importante

Se o servidor Python do Exercício 05 tiver sido implementado com uma versão antiga da interface contendo apenas `printString`, ele precisa ser atualizado para a mesma interface deste projeto. Caso contrário, o cliente Java não conseguirá chamar os novos métodos.
