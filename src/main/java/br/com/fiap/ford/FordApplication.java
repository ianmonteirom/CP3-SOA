package br.com.fiap.ford;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FordApplication {

    public static void main(String[] args) {
        SpringApplication.run(FordApplication.class, args);

        System.out.println("\n================================================");
        System.out.println("  Ford Competitive Intelligence - CP3 SOA");
        System.out.println("================================================");
        System.out.println("REST API:  http://localhost:8080/api/veiculos");
        System.out.println("SOAP WS:   http://localhost:8080/soap/veiculo");
        System.out.println("WSDL:      http://localhost:8080/soap/veiculo?wsdl");
        System.out.println("================================================\n");
    }
}