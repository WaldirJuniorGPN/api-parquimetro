package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.service.PagamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PagamentoServiceImpl implements PagamentoService {
    @Override
    public void processar() {
        log.info("Pagamento processado com sucesso!");
    }
}
