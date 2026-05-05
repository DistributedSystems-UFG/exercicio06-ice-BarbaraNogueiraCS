import sys
import Ice
import Demo


DEFAULT_HOST = "localhost"
DEFAULT_PORT = 11000


def get_printer_proxy(communicator, object_name: str, host: str, port: int):
    """Cria e valida o proxy de um objeto remoto Printer."""
    proxy_address = f"{object_name}:tcp -h {host} -p {port}"
    base_proxy = communicator.stringToProxy(proxy_address)
    printer = Demo.PrinterPrx.checkedCast(base_proxy)

    if not printer:
        raise RuntimeError(f"Proxy inválido para o objeto remoto: {object_name}")

    return printer


def call_printer_methods(printer, object_name: str, texto: str) -> None:
    """Chama todos os métodos disponíveis em um objeto remoto Printer."""
    print(f"\nChamando métodos do objeto remoto: {object_name}")

    printer.printString(texto)

    texto_maiusculo = printer.printUpperCase(texto)
    print(f"Retorno de printUpperCase: {texto_maiusculo}")

    texto_minusculo = printer.printLowerCase(texto)
    print(f"Retorno de printLowerCase: {texto_minusculo}")

    quantidade = printer.countCharacters(texto)
    print(f"Retorno de countCharacters: {quantidade}")


def main() -> None:
    host = sys.argv[1] if len(sys.argv) > 1 else DEFAULT_HOST
    port = int(sys.argv[2]) if len(sys.argv) > 2 else DEFAULT_PORT
    texto = "Hello World from ICE!"

    communicator = None

    try:
        communicator = Ice.initialize(sys.argv[:1])

        simple_printer = get_printer_proxy(
            communicator,
            "SimplePrinter",
            host,
            port
        )

        secondary_printer = get_printer_proxy(
            communicator,
            "SecondaryPrinter",
            host,
            port
        )

        call_printer_methods(simple_printer, "SimplePrinter", texto)
        call_printer_methods(secondary_printer, "SecondaryPrinter", texto)

    finally:
        if communicator is not None:
            communicator.destroy()


if __name__ == "__main__":
    main()
