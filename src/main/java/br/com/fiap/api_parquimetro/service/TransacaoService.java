package br.com.fiap.api_parquimetro.service;

import br.com.fiap.api_parquimetro.model.Transacao;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoFinalizadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoIniciadaResponseDto;
import br.com.fiap.api_parquimetro.model.dto.response.TransacaoPagamentoPendenteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransacaoService {

    TransacaoIniciadaResponseDto iniciarTransacaoTempoFlexivel(TransacaoRequestFlexivelDto dto);

    TransacaoFinalizadaResponseDto finalizarTransacao(Long id);

    Page<TransacaoPagamentoPendenteResponseDto> listarTransacoesPendentes(Pageable pageable);

    Page<TransacaoFinalizadaResponseDto> listarTransacoesConcluidas(Pageable pageable);

    Object buscarPorId(Long id);

    TransacaoIniciadaResponseDto iniciarTransacaoTempoFixo(TransacaoRequestFixoDto dto);

    Transacao adicionaHoraExtra(Transacao transacao);
}
