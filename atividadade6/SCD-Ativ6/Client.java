import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

/**
 * Cliente Java ICE.
 *
 * Este cliente acessa remotamente um objeto chamado SimplePrinter, que pode estar
 * implementado em Python ou Java, desde que siga a mesma interface Printer.ice.
 */
public final class Client {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 11000;
    private static final String DEFAULT_OBJECT_NAME = "SimplePrinter";
    private static final String DEFAULT_TEXT = "Hello from Goiania!";

    private Client() {
        // Classe utilitária: não deve ser instanciada.
    }

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = args.length > 1 ? parsePort(args[1]) : DEFAULT_PORT;
        String objectName = args.length > 2 ? args[2] : DEFAULT_OBJECT_NAME;
        String text = args.length > 3 ? joinTextArguments(args, 3) : DEFAULT_TEXT;

        try (Communicator communicator = Util.initialize(args)) {
            Demo.PrinterPrx printer = createPrinterProxy(communicator, objectName, host, port);

            System.out.println("Cliente conectado ao objeto remoto: " + objectName);
            System.out.println("Endereco do servidor: " + host + ":" + port);
            System.out.println("Texto enviado: " + text);
            System.out.println();

            String printResponse = printer.printString(text);
            System.out.println("Resposta de printString: " + printResponse);

            String upperCaseResponse = printer.printUpperCase(text);
            System.out.println("Resposta de printUpperCase: " + upperCaseResponse);

            String lowerCaseResponse = printer.printLowerCase(text);
            System.out.println("Resposta de printLowerCase: " + lowerCaseResponse);

            int characterCount = printer.countCharacters(text);
            System.out.println("Resposta de countCharacters: " + characterCount);
        } catch (LocalException exception) {
            System.err.println("Erro de comunicacao com o servidor ICE.");
            exception.printStackTrace();
        } catch (RuntimeException exception) {
            System.err.println("Erro ao executar o cliente.");
            exception.printStackTrace();
        }
    }

    private static Demo.PrinterPrx createPrinterProxy(
            Communicator communicator,
            String objectName,
            String host,
            int port
    ) {
        String proxyAddress = String.format("%s:default -h %s -p %d", objectName, host, port);
        ObjectPrx baseProxy = communicator.stringToProxy(proxyAddress);
        Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(baseProxy);

        if (printer == null) {
            throw new IllegalStateException("Proxy invalido para o objeto remoto: " + proxyAddress);
        }

        return printer;
    }

    private static int parsePort(String value) {
        try {
            int port = Integer.parseInt(value);
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("A porta deve estar entre 1 e 65535.");
            }
            return port;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Porta invalida: " + value, exception);
        }
    }

    private static String joinTextArguments(String[] args, int startIndex) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < args.length; i++) {
            if (i > startIndex) {
                builder.append(' ');
            }
            builder.append(args[i]);
        }
        return builder.toString();
    }
}
