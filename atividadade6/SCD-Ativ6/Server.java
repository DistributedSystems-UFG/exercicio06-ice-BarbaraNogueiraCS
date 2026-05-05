import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

/**
 * Servidor Java ICE.
 *
 * Este servidor registra um objeto remoto chamado SimplePrinter, implementado
 * pela classe PrinterI, e fica aguardando chamadas de clientes ICE.
 */
public final class Server {
    private static final int DEFAULT_PORT = 11000;
    private static final String DEFAULT_OBJECT_NAME = "SimplePrinter";
    private static final String ADAPTER_NAME = "PrinterAdapter";

    private Server() {
        // Classe utilitária: não deve ser instanciada.
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? parsePort(args[0]) : DEFAULT_PORT;
        String objectName = args.length > 1 ? args[1] : DEFAULT_OBJECT_NAME;

        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                    ADAPTER_NAME,
                    "default -p " + port
            );

            PrinterI printer = new PrinterI(objectName);
            adapter.add(printer, Util.stringToIdentity(objectName));
            adapter.activate();

            System.out.println("Servidor Java ICE iniciado.");
            System.out.println("Objeto remoto registrado: " + objectName);
            System.out.println("Porta: " + port);
            System.out.println("Aguardando chamadas de clientes...");
            System.out.println("Use Ctrl+C para encerrar.");

            communicator.waitForShutdown();
        } catch (LocalException exception) {
            System.err.println("Erro de comunicacao no servidor ICE.");
            exception.printStackTrace();
        } catch (RuntimeException exception) {
            System.err.println("Erro ao executar o servidor.");
            exception.printStackTrace();
        }
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
}
