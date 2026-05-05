import sys
import Ice
import Demo


class PrinterI(Demo.Printer):
    """Implementação concreta do objeto remoto Printer."""

    def __init__(self, object_name: str):
        self.object_name = object_name

    def printString(self, texto: str, current=None) -> str:
        """Recebe um texto do cliente, imprime no servidor e devolve uma resposta."""
        resultado = f"Texto recebido pelo servidor: {texto}"
        print(f"[{self.object_name}] {resultado}")
        return resultado

    def printUpperCase(self, texto: str, current=None) -> str:
        """Converte o texto recebido para letras maiúsculas."""
        resultado = texto.upper()
        print(f"[{self.object_name}] Maiúsculas: {resultado}")
        return resultado

    def printLowerCase(self, texto: str, current=None) -> str:
        """Converte o texto recebido para letras minúsculas."""
        resultado = texto.lower()
        print(f"[{self.object_name}] Minúsculas: {resultado}")
        return resultado

    def countCharacters(self, texto: str, current=None) -> int:
        """Conta a quantidade de caracteres do texto recebido."""
        quantidade = len(texto)
        print(f"[{self.object_name}] Quantidade de caracteres: {quantidade}")
        return quantidade


def main() -> None:
    communicator = None

    try:
        communicator = Ice.initialize(sys.argv)

        adapter = communicator.createObjectAdapterWithEndpoints(
            "SimpleAdapter",
            "default -p 11000"
        )

        # Objeto servidor existente solicitado pela atividade.
        simple_printer = PrinterI("SimplePrinter")
        adapter.add(simple_printer, communicator.stringToIdentity("SimplePrinter"))

        # Novo objeto servidor solicitado pela atividade.
        secondary_printer = PrinterI("SecondaryPrinter")
        adapter.add(secondary_printer, communicator.stringToIdentity("SecondaryPrinter"))

        adapter.activate()

        print("Servidor ICE iniciado na porta 11000.")
        print("Objetos disponíveis: SimplePrinter e SecondaryPrinter.")
        print("Pressione Ctrl+C para encerrar o servidor.")

        communicator.waitForShutdown()

    except KeyboardInterrupt:
        print("Servidor encerrado pelo usuário.")
    finally:
        if communicator is not None:
            communicator.destroy()


if __name__ == "__main__":
    main()
