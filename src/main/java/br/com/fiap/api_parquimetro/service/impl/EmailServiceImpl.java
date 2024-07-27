package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.response.ReciboResponseDto;
import br.com.fiap.api_parquimetro.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.fiap.api_parquimetro.utils.Utils.formatarLocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarEmail(String para, String assunto, String mensagem) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(mensagem, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail", e);
        }
    }

    @Override
    public String getTemplate(Transacao transacao, BigDecimal valorPago, ReciboResponseDto reciboResponseDto) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; }")
                .append(".container { width: 20%; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }")
                .append(".header { text-align: center; font-size: 24px; font-weight: bold; margin-bottom: 20px; }")
                .append(".section { margin-bottom: 10px; }")
                .append(".label { font-weight: bold; }")
                .append(".value { margin-left: 10px; }")
                .append(".footer { text-align: center; font-weight: bold; margin-top: 20px; }")
                .append("</style>")
                .append("</head>");
        stringBuilder.append("<body>")
                .append("<div class='container'>");
        stringBuilder.append("<div class='header'>Recibo de Estacionamento</div>");
        stringBuilder .append("<div class='section'><span class='label'>Veículo:</span><span class='value'>").append(reciboResponseDto.getPlacaDoVeiculo()).append("</span></div>");
        stringBuilder.append("<div class='section'><span class='label'>Hora da Entrada:</span><span class='value'>").append(reciboResponseDto.getHoraDaEntrada()).append("</span></div>");
        if (reciboResponseDto.getHoraDaSaida() != null) {
            stringBuilder.append("<div class='section'><span class='label'>Hora da Saída:</span><span class='value'>").append(reciboResponseDto.getHoraDaSaida()).append("</span></div>");
            stringBuilder.append("<div class='section'><span class='label'>Tempo Estacionado:</span><span class='value'>")
                    .append(reciboResponseDto.getTempoEstacionado().toHours()).append(":")
                    .append(reciboResponseDto.getTempoEstacionado().toMinutesPart()).append(":")
                    .append(reciboResponseDto.getTempoEstacionado().toSecondsPart()).append("</span></div>");
        } else {
            stringBuilder.append("<div class='section'><span class='label'>Tempo limite:</span><span class='value'>").append(formatarLocalDateTime(transacao.getTempoLimite())).append("</span></div>");
        }
        stringBuilder.append("<div class='section'><span class='label'>Valor Total Pago:</span><span class='value'>R$ ").append(valorPago).append("</span></div>");
        stringBuilder.append("<div class='footer'>Obrigado por usar nosso serviço!</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return stringBuilder.toString();
    }
}
