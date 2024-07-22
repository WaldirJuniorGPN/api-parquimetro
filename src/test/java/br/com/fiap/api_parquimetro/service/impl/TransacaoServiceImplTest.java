package br.com.fiap.api_parquimetro.service.impl;

import br.com.fiap.api_parquimetro.model.*;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFixoDto;
import br.com.fiap.api_parquimetro.model.dto.request.TransacaoRequestFlexivelDto;
import br.com.fiap.api_parquimetro.model.enums.StatusParquimetro;
import br.com.fiap.api_parquimetro.model.enums.TipoPagamento;
import br.com.fiap.api_parquimetro.model.enums.TipoTransacao;
import br.com.fiap.api_parquimetro.repository.TransacaoRepository;
import br.com.fiap.api_parquimetro.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Transacao transacao = invocation.getArgument(0);
                BigDecimal valorPago = invocation.getArgument(1);
                transacao.setPagamentoPendente(false);
                transacao.setValorPago(valorPago);
                return null;
            }
        }).when(pagamentoService).processarPagamento(any(Transacao.class), any(BigDecimal.class));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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

    @Test
    void iniciarTransacaoTempoFixo() {
        Long veiculoId = 1L;
        Long parquimetroId = 1L;
        int duracao = 2;
        var dto = new TransacaoRequestFixoDto(veiculoId, parquimetroId, duracao, TipoPagamento.CREDITO);

        var veiculo = new Veiculo();
        veiculo.setId(veiculoId);

        var parquimetro = new Parquimetro();
        parquimetro.setId(parquimetroId);

        when(veiculoService.buscarVeiculo(veiculoId)).thenReturn(veiculo);
        when(parquimetroService.buscarParquimetro(parquimetroId)).thenReturn(parquimetro);

        var transacao = new Transacao();
        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(TipoTransacao.TEMPO_FIXO);

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        var response = transacaoService.iniciarTransacaoTempoFixo(dto);

        assertEquals(veiculoId, response.veiculoId());
        assertEquals(parquimetroId, response.parquimetroId());
        assertEquals(TipoTransacao.TEMPO_FIXO, response.tipo());

        verify(veiculoService).buscarVeiculo(veiculoId);
        verify(parquimetroService).buscarParquimetro(parquimetroId);
        verify(transacaoRepository).save(any(Transacao.class));
        verify(parquimetroService).ocuparParquimetro(parquimetro);
    }

    @Test
    void finalizarTransacaoFlexivel() {
        Long transacaoId = 1L;
        Long veiculoId = 1L;
        Long parquimetroId = 1L;
        Long tarifaId = 1L;

        var veiculo = new Veiculo();
        veiculo.setId(veiculoId);

        var parquimetro = new Parquimetro();
        parquimetro.setId(parquimetroId);
        parquimetro.setStatusParquimetro(StatusParquimetro.LIVRE);

        var tarifa = new Tarifa();
        tarifa.setId(tarifaId);
        tarifa.setTarifaFlexivelPorHora(BigDecimal.valueOf(5));
        parquimetro.setTarifa(tarifa);

        var transacao = new Transacao();
        transacao.setId(transacaoId);
        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(TipoTransacao.TEMPO_FLEXIVEL);
        transacao.setInputDate(LocalDateTime.now().minusHours(2));

        when(transacaoRepository.findByIdAndAtivoTrue(transacaoId)).thenReturn(Optional.of(transacao));
        when(pagamentoService.calcularValorFlexivel(any(LocalDateTime.class), any(LocalDateTime.class), any(Tarifa.class))).thenReturn(BigDecimal.valueOf(10));
        Recibo reciboMock = new Recibo();
        when(reciboService.gerarRecibo(any(Transacao.class))).thenReturn(reciboMock);

        var response = transacaoService.finalizarTransacao(transacaoId);

        assertEquals(transacaoId, response.inicio().id());
        assertFalse(transacao.isPagamentoPendente());
        assertEquals(BigDecimal.valueOf(10), response.valorPago());

        verify(transacaoRepository).findByIdAndAtivoTrue(transacaoId);
        verify(parquimetroService).liberarParquimetro(parquimetro);
        verify(transacaoRepository).save(any(Transacao.class));
        verify(pagamentoService).calcularValorFlexivel(any(LocalDateTime.class), any(LocalDateTime.class), any(Tarifa.class));
        verify(pagamentoService).processarPagamento(any(Transacao.class), eq(BigDecimal.valueOf(10)));
        verify(reciboService).gerarRecibo(any(Transacao.class));
        verify(impressaoService).imprimirRecibo(any(Recibo.class));
    }

    @Test
    void finalizarTransacaoFixa() {
        Long transacaoId = 1L;
        Long veiculoId = 1L;
        Long parquimetroId = 1L;
        Long tarifaId = 1L;

        var veiculo = new Veiculo();
        veiculo.setId(veiculoId);

        var parquimetro = new Parquimetro();
        parquimetro.setId(parquimetroId);
        parquimetro.setStatusParquimetro(StatusParquimetro.LIVRE);

        var tarifa = new Tarifa();
        tarifa.setId(tarifaId);
        tarifa.setTarifaFixaPorHora(BigDecimal.valueOf(5));
        parquimetro.setTarifa(tarifa);

        var transacao = new Transacao();
        transacao.setId(transacaoId);
        transacao.setVeiculo(veiculo);
        transacao.setParquimetro(parquimetro);
        transacao.setTipo(TipoTransacao.TEMPO_FIXO);
        transacao.setInputDate(LocalDateTime.now().minusHours(2));
        transacao.setTempoEstacionado(Duration.ofHours(2));
        transacao.setValorPago(BigDecimal.valueOf(10));

        when(transacaoRepository.findByIdAndAtivoTrue(transacaoId)).thenReturn(Optional.of(transacao));
        when(pagamentoService.calcularValorFixo(any(LocalDateTime.class), any(Integer.class), any(Tarifa.class))).thenReturn(BigDecimal.valueOf(20));
        Recibo reciboMock = new Recibo();
        when(reciboService.gerarRecibo(any(Transacao.class))).thenReturn(reciboMock);

        var response = transacaoService.finalizarTransacao(transacaoId);

        assertEquals(transacaoId, response.inicio().id());
        assertEquals(BigDecimal.valueOf(10), response.valorPago());

        verify(transacaoRepository).findByIdAndAtivoTrue(transacaoId);
        verify(parquimetroService).liberarParquimetro(parquimetro);
        verify(transacaoRepository).save(any(Transacao.class));
        verify(reciboService).gerarRecibo(any(Transacao.class));
        verify(impressaoService).imprimirRecibo(any(Recibo.class));
    }
}