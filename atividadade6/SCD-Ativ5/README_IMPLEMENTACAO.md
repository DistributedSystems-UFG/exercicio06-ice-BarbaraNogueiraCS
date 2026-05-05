# Atividade ICE - Printer com novos métodos e novo objeto servidor

## Estrutura de pastas

```text
atividade_ice_printer/
├── Printer.ice
├── Printer_ice.py
├── server.py
├── client.py
└── Demo/
    ├── __init__.py
    └── __pycache__/              # gerada automaticamente pelo Python
```

## Como gerar o arquivo Printer_ice.py

Depois de alterar `Printer.ice`, execute:

```bash
slice2py Printer.ice
```

O arquivo `Printer_ice.py` é gerado automaticamente pelo ICE. Não edite esse arquivo manualmente em uma implementação real.

## Como executar

Instale as dependências:

```bash
sudo dnf install python3-ice ice-compilers
```

Gere o código ICE:

```bash
slice2py Printer.ice
```

Execute o servidor em um terminal:

```bash
python3 server.py
```

Execute o cliente em outro terminal na mesma máquina:

```bash
python3 client.py
```

Para executar o cliente apontando para outra máquina:

```bash
python3 client.py IP_DA_MAQUINA_DO_SERVIDOR 11000
```
