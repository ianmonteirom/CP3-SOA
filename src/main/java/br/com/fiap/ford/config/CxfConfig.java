package br.com.fiap.ford.config;

import br.com.fiap.ford.service.IVeiculoService;
import br.com.fiap.ford.soap.VeiculoSoapService;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    private final Bus bus;
    private final IVeiculoService veiculoService;

    public CxfConfig(Bus bus, IVeiculoService veiculoService) {
        this.bus = bus;
        this.veiculoService = veiculoService;
    }

    /**
     * Publica o VeiculoSoapService no endpoint /soap/veiculo.
     * WSDL disponivel em: http://localhost:8080/soap/veiculo?wsdl
     */
    @Bean
    public Endpoint veiculoEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new VeiculoSoapService(veiculoService));
        endpoint.publish("/veiculo");
        return endpoint;
    }
}