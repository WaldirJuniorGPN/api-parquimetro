package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.*;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransacaoServiceImplTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private PagamentoService pagamentoService;

    @Mock
    private VeiculoService veiculoService;

    @Mock
    private ParquimetroService parquimetroService;

    @Mock
    private ReciboService reciboService;

    @Mock
    private ImpressaoService impressaoService;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void iniciarTransacaoTempoFlexivel() {
        Long veiculoId = 1L;
        Long parquimetroId = 1L;
        var dto = new TransacaoRequestFlexivelDto(veiculoId, parquimetroId, TipoPagamento.CREDITO);

        var veiculo = new Veiculo();
        veiculo.setId(veiculoId);

        var parquimetro = new Parquimetro();
        parquimetro.setId(parquimetroId);

        when(veiculoService.buscarVeiculo(veiculoId)).thenReturn(veiculo);
        when(parquimetroService.buscarParquimetro(parquimetroId)).thenReturn(parquimetro);

        var transacao = new Transacao();
        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(TipoTransacao.TEMPO_FLEXIVEL);

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        var response = transacaoService.iniciarTransacaoTempoFlexivel(dto);

        assertEquals(veiculoId, response.veiculoId());
        assertEquals(parquimetroId, response.parquimetroId());
        assertEquals(TipoTransacao.TEMPO_FLEXIVEL, response.tipo());

        verify(veiculoService).buscarVeiculo(veiculoId);
        verify(parquimetroService).buscarParquimetro(parquimetroId);
        verify(transacaoRepository).save(any(Transacao.class));
        verify(parquimetroService).ocuparParquimetro(parquimetro);
    }
}