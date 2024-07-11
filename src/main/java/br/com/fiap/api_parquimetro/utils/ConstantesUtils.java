package br.com.fiap.api_parquimetro.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantesUtils {
    public static final String ENTRADA_REGISTRADA_VEICULO = "Entrada registrada para o veículo ID: {}";
    public static final String SAIDA_REGISTRADA_TRANSACAO = "Saída registrada para a transação ID: {}";
    public static final String PARAMETROS_JSON_INCORRETOS = "Parâmetros do Json incorretos";
    public static final String ENTIDADE_NAO_ENCONTRADA = "Entidade não encontrada";
    public static final String AGENTE_NAO_ENCONTRADO = "Agente não encontrado";
    public static final String TARIFA_NAO_ENCONTRADA = "Tarifa não encontrada";
    public static final String MOTORISTA_NAO_ENCONTRADO = "Motorista não encontrado";
    public static final String PARQUIMETRO_NAO_ENCONTRADO = "Parquímetro não encontrado";
    public static final String VEICULO_NAO_ENCONTRADO = "Veículo não encontrado";
    public static final String ENTITY_NOT_FOUND = "Entity not found";
    public static final String PROPERTY_REFERENCE_INVALID = "Property reference invalid";
    public static final String ERRO_VALIDACAO = "Erro de Validação";
    public static final String PAGAMENTO_PROCESSADO_SUCESSO = "Pagamento processado com sucesso!";
    public static final String VERIFICANDO_TRANSACOES_FIXAS = "Verificando transações com tempo fixo prestes a expirar.";
    public static final int TEMPO_ALERTA_MINUTOS = 10;
    public static final String MENSAGEM_ALERTA_TEMPO_ESTACIONADO = "Seu tempo de estacionamento está prestes a expirar, Veículo: %s, Parquímetro: %d";
    public static final String ASSUNTO_ALERTA_TEMPO_ESTACIONADO = "Alerta de Tempo Estacionado";
    public static final String ALERTA_ENVIADO_PARA_CONDUTOR = "Alerta enviado para o condutor do veículo {}.";
    public static final String RECIBO_GERADO = "Recibo gerado: {}";
}