package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Recibo;
import br.com.fiap.api_parquimetro.service.ImpressaoService;
import org.springframework.stereotype.Service;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Service
public class ImpressaoServiceImpl implements ImpressaoService {

    @Override
    public void imprimirRecibo(Recibo recibo) {
        StringBuilder reciboTexto = new StringBuilder();
        reciboTexto.append("Recibo de Estacionamento\n");
        reciboTexto.append("--------------------------------\n");
        reciboTexto.append("ID Transação: ").append(recibo.getTransacaoId()).append("\n");
        reciboTexto.append("Veículo: ").append(recibo.getPlacaDoVeiculo()).append("\n");
        reciboTexto.append("Hora da Entrada: ").append(recibo.getHoraDaEntrada()).append("\n");
        reciboTexto.append("Hora da Saída: ").append(recibo.getHoraDaSaida()).append("\n");
        reciboTexto.append("Tempo Estacionado: ").append(recibo.getTempoEstacionado().toHours()).append(" horas\n");
        reciboTexto.append("Valor Total Pago: R$ ").append(recibo.getValorTotalPago()).append("\n");
        reciboTexto.append("--------------------------------\n");
        reciboTexto.append("Obrigado por usar nossos serviços!\n");

        InputStream stream = new ByteArrayInputStream(reciboTexto.toString().getBytes(StandardCharsets.UTF_8));
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        SimpleDoc doc = new SimpleDoc(stream, flavor, null);

        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        if (service != null) {
            DocPrintJob job = service.createPrintJob();
            try {
                job.print(doc, null);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No default print service found.");
        }
    }
}
