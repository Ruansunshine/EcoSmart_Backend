    package com.ecosmart.eco.service;

    import com.ecosmart.eco.model.Objeto;
    import com.ecosmart.eco.model.Ambiente;
    import com.ecosmart.eco.repository.ObjetoRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.function.Supplier;
    import java.util.logging.Logger;

    @Service
    public class ObjetoService {

        @Autowired
        private ObjetoRepository objetoRepository;

        private static final Logger logger = Logger.getLogger(ObjetoService.class.getName());

        // =============================================================================
        // PADRÃO TEMPLATE METHOD (COMPORTAMENTAL)
        // Define um algoritmo padrão para operações de busca com etapas customizáveis
        // =============================================================================

        /**
         * Template Method - Define o algoritmo padrão para operações de busca
         * Etapas: validação -> log início -> execução -> log resultado -> retorno
         */
        private <T> T executarOperacaoBusca(String operacao, Supplier<T> busca, Object... parametros) {
            // Etapa 1: Validação dos parâmetros (pode ser sobrescrita)
            validarParametrosBusca(parametros);

            // Etapa 2: Log do início da operação
            logInicioOperacao(operacao, parametros);

            // Etapa 3: Execução da busca (implementação específica)
            T resultado = busca.get();

            // Etapa 4: Log do resultado
            logResultadoOperacao(operacao, resultado);

            // Etapa 5: Retorno do resultado
            return resultado;
        }

        /**
         * Etapa do template que pode ser customizada - Validação de parâmetros
         */
        private void validarParametrosBusca(Object... parametros) {
            for (Object param : parametros) {
                if (param == null) {
                    throw new IllegalArgumentException("Parâmetro de busca não pode ser nulo");
                }
                if (param instanceof String && ((String) param).trim().isEmpty()) {
                    throw new IllegalArgumentException("Parâmetro de texto não pode estar vazio");
                }
            }
        }

        /**
         * Etapa do template - Log do início da operação
         */
        private void logInicioOperacao(String operacao, Object... parametros) {
            logger.info(String.format("Iniciando operação: %s com parâmetros: %s",
                    operacao, java.util.Arrays.toString(parametros)));
        }

        /**
         * Etapa do template - Log do resultado
         */
        private void logResultadoOperacao(String operacao, Object resultado) {
            if (resultado instanceof List) {
                List<?> lista = (List<?>) resultado;
                logger.info(String.format("Operação %s concluída. Encontrados %d registros",
                        operacao, lista.size()));
            } else if (resultado instanceof Optional) {
                Optional<?> optional = (Optional<?>) resultado;
                logger.info(String.format("Operação %s concluída. Registro %s",
                        operacao, optional.isPresent() ? "encontrado" : "não encontrado"));
            } else {
                logger.info(String.format("Operação %s concluída", operacao));
            }
        }

        // =============================================================================
        // MÉTODOS CRUD ORIGINAIS - Agora utilizando o Template Method
        // =============================================================================

        // CREATE - Criar objeto
        public Objeto salvar(Objeto objeto) {
            return objetoRepository.save(objeto);
        }

        // READ - Buscar todos (usando Template Method)
        public List<Objeto> buscarTodos() {
            return executarOperacaoBusca("buscarTodos",
                    () -> objetoRepository.findAll());
        }

        // READ - Buscar por ID (usando Template Method)
        public Optional<Objeto> buscarPorId(Integer id) {
            return executarOperacaoBusca("buscarPorId",
                    () -> objetoRepository.findById(id), id);
        }

        // READ - Buscar por nome (usando Template Method)
        public List<Objeto> buscarPorNome(String nomeObjeto) {
            return executarOperacaoBusca("buscarPorNome",
                    () -> objetoRepository.findByNomeObjeto(nomeObjeto), nomeObjeto);
        }

        // READ - Buscar por nome (contém) (usando Template Method)
        public List<Objeto> buscarPorNomeContendo(String nomeObjeto) {
            return executarOperacaoBusca("buscarPorNomeContendo",
                    () -> objetoRepository.findByNomeObjetoContainingIgnoreCase(nomeObjeto), nomeObjeto);
        }

        // READ - Buscar por tipo (usando Template Method)
        public List<Objeto> buscarPorTipo(String tipoObjeto) {
            return executarOperacaoBusca("buscarPorTipo",
                    () -> objetoRepository.findByTipoObjeto(tipoObjeto), tipoObjeto);
        }

        // READ - Buscar por tipo (contém) (usando Template Method)
        public List<Objeto> buscarPorTipoContendo(String tipoObjeto) {
            return executarOperacaoBusca("buscarPorTipoContendo",
                    () -> objetoRepository.findByTipoObjetoContainingIgnoreCase(tipoObjeto), tipoObjeto);
        }

        // READ - Buscar por status (usando Template Method)
        public List<Objeto> buscarPorStatus(String status) {
            return executarOperacaoBusca("buscarPorStatus",
                    () -> objetoRepository.findByStatus(status), status);
        }

        // READ - Buscar objetos ativos (usando Template Method)
        public List<Objeto> buscarPorAtivo(Integer ativo) {
            return executarOperacaoBusca("buscarPorAtivo",
                    () -> objetoRepository.findByAtivo(ativo), ativo);
        }

        // READ - Buscar por potência (usando Template Method)
        public List<Objeto> buscarPorPotencia(Integer potencia) {
            return executarOperacaoBusca("buscarPorPotencia",
                    () -> objetoRepository.findByPotencia(potencia), potencia);
        }

        // READ - Buscar por faixa de potência (usando Template Method)
        public List<Objeto> buscarPorFaixaPotencia(Integer potenciaMin, Integer potenciaMax) {
            return executarOperacaoBusca("buscarPorFaixaPotencia",
                    () -> objetoRepository.findByPotenciaBetween(potenciaMin, potenciaMax),
                    potenciaMin, potenciaMax);
        }

        // READ - Buscar objeto com ambientes (usando Template Method)
        public Optional<Objeto> buscarPorIdComAmbientes(Integer id) {
            return executarOperacaoBusca("buscarPorIdComAmbientes",
                    () -> objetoRepository.findByIdWithAmbientes(id), id);
        }

        // READ - Buscar objeto completo (usando Template Method)
        public Optional<Objeto> buscarPorIdCompleto(Integer id) {
            return executarOperacaoBusca("buscarPorIdCompleto",
                    () -> objetoRepository.findByIdComplete(id), id);
        }

        // READ - Buscar por nome que começa com texto (usando Template Method)
        public List<Objeto> buscarPorNomeIniciandoCom(String nomeObjeto) {
            return executarOperacaoBusca("buscarPorNomeIniciandoCom",
                    () -> objetoRepository.findByNomeObjetoStartingWithIgnoreCase(nomeObjeto), nomeObjeto);
        }

        // READ - Buscar por tipo que começa com texto (usando Template Method)
        public List<Objeto> buscarPorTipoIniciandoCom(String tipoObjeto) {
            return executarOperacaoBusca("buscarPorTipoIniciandoCom",
                    () -> objetoRepository.findByTipoObjetoStartingWithIgnoreCase(tipoObjeto), tipoObjeto);
        }

        // READ - Buscar por potência maior que (usando Template Method)
        public List<Objeto> buscarPorPotenciaMaiorQue(Integer potencia) {
            return executarOperacaoBusca("buscarPorPotenciaMaiorQue",
                    () -> objetoRepository.findByPotenciaGreaterThan(potencia), potencia);
        }

        // READ - Buscar por tempo de uso maior que (usando Template Method)
        public List<Objeto> buscarPorTempoUsoMaiorQue(Double tempoUso) {
            return executarOperacaoBusca("buscarPorTempoUsoMaiorQue",
                    () -> objetoRepository.findByTempoUsoGreaterThan(tempoUso), tempoUso);
        }

        // READ - Buscar objetos ativos por tipo (usando Template Method)
        public List<Objeto> buscarPorAtivoETipo(Integer ativo, String tipoObjeto) {
            return executarOperacaoBusca("buscarPorAtivoETipo",
                    () -> objetoRepository.findByAtivoAndTipoObjeto(ativo, tipoObjeto),
                    ativo, tipoObjeto);
        }

        // UPDATE - Atualizar objeto
        public Objeto atualizar(Objeto objeto) {
            return objetoRepository.save(objeto);
        }

        // DELETE - Deletar por ID
        public void deletarPorId(Integer id) {
            objetoRepository.deleteById(id);
        }

        // DELETE - Deletar objeto
        public void deletar(Objeto objeto) {
            objetoRepository.delete(objeto);
        }

        // Verificar se existe por ID
        public boolean existePorId(Integer id) {
            return objetoRepository.existsById(id);
        }

        // Verificar se existe por nome
        public boolean existePorNome(String nomeObjeto) {
            return objetoRepository.existsByNomeObjeto(nomeObjeto);
        }

        // Verificar se existe por tipo
        public boolean existePorTipo(String tipoObjeto) {
            return objetoRepository.existsByTipoObjeto(tipoObjeto);
        }

        // Contar objetos
        public long contar() {
            return objetoRepository.count();
        }

        // Contar objetos por tipo
        public long contarPorTipo(String tipoObjeto) {
            return objetoRepository.countByTipoObjeto(tipoObjeto);
        }

        // Contar objetos por status
        public long contarPorStatus(String status) {
            return objetoRepository.countByStatus(status);
        }

        // Contar objetos ativos
        public long contarPorAtivo(Integer ativo) {
            return objetoRepository.countByAtivo(ativo);
        }
    }