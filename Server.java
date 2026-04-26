import com.zeroc.Ice.*;

public class Server {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            // Create the object adapter
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                "SimpleAdapter", "default -p 11000");

            // Create and add the Printer servant
            Demo.Printer printerServant = new PrinterI();
            adapter.add(printerServant, Util.stringToIdentity("SimplePrinter"));

            // Create and add the Calculator servant
            Demo.Calculator calcServant = new CalculatorI();
            adapter.add(calcServant, Util.stringToIdentity("SimpleCalculator"));

            // Activate the adapter
            adapter.activate();

            System.out.println("Server is running...");
            System.out.println("Printer object available at: SimplePrinter");
            System.out.println("Calculator object available at: SimpleCalculator");
            System.out.println("Port: 11000");

            // Wait until shutdown
            communicator.waitForShutdown();
        }
    }
}

// Printer servant implementation
class PrinterI implements Demo.Printer {
    @Override
    public void printString(String s, Current current) {
        System.out.println("[Printer] " + s);
    }

    @Override
    public int sum(int n1, int n2, Current current) {
        int result = n1 + n2;
        System.out.println("[Printer] sum(" + n1 + ", " + n2 + ") = " + result);
        return result;
    }
}

// Calculator servant implementation
class CalculatorI implements Demo.Calculator {
    @Override
    public double divisor(double n1, double n2, Current current) {
        if (n2 == 0) {
            throw new RuntimeException("Division by zero");
        }
        double result = n1 / n2;
        System.out.println("[Calculator] divisor(" + n1 + ", " + n2 + ") = " + result);
        return result;
    }
}
