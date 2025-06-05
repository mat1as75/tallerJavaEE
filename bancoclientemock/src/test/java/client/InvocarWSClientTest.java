package client;

import bancoclientemock.DepositResponse;
import bancoclientemock.DepositSOAP;
import bancoclientemock.DepositSOAPService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvocarWSClientTest {

    @Test
    @DisplayName("Invocar servicio SOAP: realizar depósito")
    void testMakeDeposit() {
        // Instanciar el cliente del servicio generado a partir del WSDL
        DepositSOAPService service = new DepositSOAPService();
        DepositSOAP port = service.getDepositSOAPPort();

        // Datos de prueba
        String account = "ACC-9999";
        int amount = 1500;

        // Invocar el método remoto
        DepositResponse response = port.makeDeposit(account, amount);

        // Mostrar los resultados
        System.out.println("----- Resultado del depósito -----");
        System.out.println("¿Éxito?     : " + response.isSuccess());
        System.out.println("Mensaje     : " + response.getMessage());
        if (response.getDepositId() != null) {
            System.out.println("Depósito registrado con ID : " + response.getDepositId());
        } else {
            System.out.println("Depósito no registrado (ID nulo)");
        }
        System.out.println("----------------------------------");
    }
}
