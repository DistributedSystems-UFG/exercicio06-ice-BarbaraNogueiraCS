import com.zeroc.Ice.Current;

/**
 * Implementacao concreta da interface remota Demo.Printer.
 *
 * Esta classe contem o comportamento real executado pelo servidor quando um
 * cliente chama os metodos remotos definidos em Printer.ice.
 */
public final class PrinterI implements Demo.Printer {
    private final String objectName;

    public PrinterI(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String printString(String texto, Current current) {
        String response = "Texto recebido pelo servidor: " + texto;
        log("printString", response);
        return response;
    }

    @Override
    public String printUpperCase(String texto, Current current) {
        String result = texto.toUpperCase();
        log("printUpperCase", result);
        return result;
    }

    @Override
    public String printLowerCase(String texto, Current current) {
        String result = texto.toLowerCase();
        log("printLowerCase", result);
        return result;
    }

    @Override
    public int countCharacters(String texto, Current current) {
        int result = texto.length();
        log("countCharacters", String.valueOf(result));
        return result;
    }

    private void log(String methodName, String result) {
        System.out.printf("[%s] Metodo chamado: %s | Resultado: %s%n", objectName, methodName, result);
    }
}
