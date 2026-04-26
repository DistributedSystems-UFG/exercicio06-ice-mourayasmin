import com.zeroc.Ice.*;

public class Client {
    public static void main(String[] args) {
        // 1. Initialize the Ice communicator within a try-with-resources block
        try (Communicator communicator = Util.initialize(args)) {

            // 2. Create a proxy to the remote 'Printer' object
            // For local testing: use "127.0.0.1"
            // For remote server: replace with the server's IP address
            String serverHost = "127.0.0.1"; // Change this to your server IP
            ObjectPrx printerBase = communicator.stringToProxy("SimplePrinter:default -h " + serverHost + " -p 11000");

            // 3. Downcast the proxy to the Printer interface
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(printerBase);

            if (printer == null) {
                throw new Error("Invalid proxy");
            }

            // 4. Call the remote methods
            System.out.println("=== Testing Printer Interface ===");
            printer.printString("Hello from Goiania!");
            System.out.println("Called printString()");

            int sumResult = printer.sum(5, 6);
            System.out.println("sum(5, 6) = " + sumResult);

            // 5. Create a proxy to the remote 'Calculator' object
            ObjectPrx calcBase = communicator.stringToProxy("SimpleCalculator:default -h " + serverHost + " -p 11000");

            // 6. Downcast the proxy to the Calculator interface
            Demo.CalculatorPrx calculator = Demo.CalculatorPrx.checkedCast(calcBase);

            if (calculator == null) {
                throw new Error("Invalid Calculator proxy");
            }

            // 7. Call the remote method
            System.out.println("\n=== Testing Calculator Interface ===");
            double divResult = calculator.divisor(10.0, 2.0);
            System.out.println("divisor(10.0, 2.0) = " + divResult);

        } catch (LocalException e) {
            e.printStackTrace();
        }
    }
}
