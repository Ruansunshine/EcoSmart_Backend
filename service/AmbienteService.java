    package com.ecosmart.eco.service;

    import com.ecosmart.eco.model.Ambiente;
    import com.ecosmart.eco.model.Objeto;
    import com.ecosmart.eco.repository.AmbienteRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class AmbienteService {

        @Autowired
        private AmbienteRepository ambienteRepository;

        // ============================================================
        // OPERAÇÕES CRUD BÁSICAS
        // ============================================================

        // CREATE - Criar ambiente
        public Ambiente salvar(Ambiente ambiente) {
            return ambienteRepository.save(ambiente);
        }

        // READ - Buscar todos
        public List<Ambiente> buscarTodos() {
            return ambienteRepository.findAll();
        }

        // READ - Buscar por ID
        public Optional<Ambiente> buscarPorId(Integer id) {
            return ambienteRepository.findById(id);
        }

        // UPDATE - Atualizar ambiente
        public Ambiente atualizar(Ambiente ambiente) {
            return ambienteRepository.save(ambiente);
        }

        // DELETE - Deletar por ID
        public void deletarPorId(Integer id) {
            ambienteRepository.deleteById(id);
        }

        // DELETE - Deletar ambiente
        public void deletar(Ambiente ambiente) {
            ambienteRepository.delete(ambiente);
        }

        // ============================================================
        // CONSULTAS POR CAMPOS DIRETOS DO AMBIENTE
        // ============================================================

        // READ - Buscar por nome
        public List<Ambiente> buscarPorNome(String nome) {
            return ambienteRepository.findByNome(nome);
        }

        // READ - Buscar por nome (contém)
        public List<Ambiente> buscarPorNomeContendo(String nome) {
            return ambienteRepository.findByNomeContainingIgnoreCase(nome);
        }

        // READ - Buscar por nome que começa com texto
        public List<Ambiente> buscarPorNomeIniciandoCom(String nome) {
            return ambienteRepository.findByNomeStartingWithIgnoreCase(nome);
        }

        // READ - Buscar por descrição contendo texto
        public List<Ambiente> buscarPorDescricaoContendo(String descricao) {
            return ambienteRepository.findByDescricaoContainingIgnoreCase(descricao);
        }

        // ============================================================
        // CONSULTAS POR RELACIONAMENTOS COM OBJETOS (CORRIGIDAS)
        // ============================================================

        // READ - Buscar por objeto (CORRIGIDO)
        public List<Ambiente> buscarPorObjeto(Objeto objeto) {
            return ambienteRepository.findByObjeto(objeto);
        }

        // READ - Buscar por ID do objeto (CORRIGIDO)
        public List<Ambiente> buscarPorObjetoId(Integer objetoId) {
            return ambienteRepository.findByObjetoId(objetoId);
        }

        // READ - Buscar por tipo de objeto
        public List<Ambiente> buscarPorTipoObjeto(String tipoObjeto) {
            return ambienteRepository.findByObjetoTipoObjeto(tipoObjeto);
        }

        // READ - Buscar por nome do objeto
        public List<Ambiente> buscarPorNomeObjeto(String nomeObjeto) {
            return ambienteRepository.findByObjetoNomeObjeto(nomeObjeto);
        }

        // READ - Buscar por status do objeto
        public List<Ambiente> buscarPorStatusObjeto(String status) {
            return ambienteRepository.findByObjetoStatus(status);
        }

        // READ - Buscar ambientes com objetos ativos
        public List<Ambiente> buscarPorObjetoAtivo(Integer ativo) {
            return ambienteRepository.findByObjetoAtivo(ativo);
        }

        // READ - Buscar por potência do objeto maior que
        public List<Ambiente> buscarPorPotenciaObjetoMaiorQue(Integer potencia) {
            return ambienteRepository.findByObjetoPotenciaGreaterThan(potencia);
        }

        // READ - Buscar por faixa de potência do objeto
        public List<Ambiente> buscarPorFaixaPotenciaObjeto(Integer potenciaMin, Integer potenciaMax) {
            return ambienteRepository.findByObjetoPotenciaBetween(potenciaMin, potenciaMax);
        }

        // ============================================================
        // CONSULTAS COM FETCH JOIN OTIMIZADAS (NOVAS)
        // ============================================================

        // READ - Buscar ambiente com objetos (NOVO)
        public Optional<Ambiente> buscarPorIdComObjetos(Integer id) {
            return ambienteRepository.findByIdWithObjetos(id);
        }

        // READ - Buscar ambiente com usuários
        public Optional<Ambiente> buscarPorIdComUsuarios(Integer id) {
            return ambienteRepository.findByIdWithUsuarios(id);
        }

        // READ - Buscar ambiente com relatórios
        public Optional<Ambiente> buscarPorIdComRelatorios(Integer id) {
            return ambienteRepository.findByIdWithRelatorios(id);
        }

        // READ - Buscar ambiente completo
        public Optional<Ambiente> buscarPorIdCompleto(Integer id) {
            return ambienteRepository.findByIdComplete(id);
        }

        // ============================================================
        // CONSULTAS POR RELACIONAMENTOS COM USUÁRIOS (NOVAS)
        // ============================================================

        // READ - Buscar ambientes por usuário
        public List<Ambiente> buscarPorUsuarioId(Integer usuarioId) {
            return ambienteRepository.findByUsuarioId(usuarioId);
        }

        // READ - Buscar ambientes por nome do usuário
        public List<Ambiente> buscarPorUsuarioNome(String nomeUsuario) {
            return ambienteRepository.findByUsuarioNome(nomeUsuario);
        }

        // ============================================================
        // CONSULTAS AVANÇADAS COM MÚLTIPLAS CONDIÇÕES (NOVAS)
        // ============================================================

        // READ - Buscar ambientes por nome e tipo de objeto
        public List<Ambiente> buscarPorNomeEObjetoTipo(String nomeAmbiente, String tipoObjeto) {
            return ambienteRepository.findByNomeAndObjetoTipo(nomeAmbiente, tipoObjeto);
        }

        // READ - Buscar ambientes com objetos ativos de determinado tipo
        public List<Ambiente> buscarPorObjetoAtivoETipo(String tipoObjeto) {
            return ambienteRepository.findByObjetoAtivoAndTipo(tipoObjeto);
        }

        // READ - Buscar ambientes por usuário e com objetos ativos
        public List<Ambiente> buscarPorUsuarioEObjetosAtivos(Integer usuarioId) {
            return ambienteRepository.findByUsuarioIdAndObjetosAtivos(usuarioId);
        }

        // ============================================================
        // CONSULTAS DE ESTATÍSTICAS E RELATÓRIOS (NOVAS)
        // ============================================================

        // READ - Contar objetos por ambiente
        public List<Object[]> contarObjetosPorAmbiente() {
            return ambienteRepository.countObjetosByAmbiente();
        }

        // READ - Buscar ambientes com mais de X objetos
        public List<Ambiente> buscarAmbientesComMaisQueXObjetos(int quantidade) {
            return ambienteRepository.findAmbientesWithMoreThanXObjetos(quantidade);
        }

        // READ - Buscar ambientes sem objetos
        public List<Ambiente> buscarAmbientesSemObjetos() {
            return ambienteRepository.findAmbientesSemObjetos();
        }

        // READ - Buscar ambientes sem usuários
        public List<Ambiente> buscarAmbientesSemUsuarios() {
            return ambienteRepository.findAmbientesSemUsuarios();
        }

        // ============================================================
        // OPERAÇÕES DE VERIFICAÇÃO E CONTAGEM
        // ============================================================

        // Verificar se existe por ID
        public boolean existePorId(Integer id) {
            return ambienteRepository.existsById(id);
        }

        // Verificar se existe por nome
        public boolean existePorNome(String nome) {
            return ambienteRepository.existsByNome(nome);
        }

        // Verificar se existe por objeto (CORRIGIDO)
        public boolean existePorObjeto(Objeto objeto) {
            return ambienteRepository.existsByObjeto(objeto);
        }

        // Verificar se existe por ID do objeto (CORRIGIDO)
        public boolean existePorObjetoId(Integer objetoId) {
            return ambienteRepository.existsByObjetoId(objetoId);
        }

        // Contar ambientes
        public long contar() {
            return ambienteRepository.count();
        }

        // Contar ambientes por objeto
        public long contarPorObjeto(Objeto objeto) {
            return ambienteRepository.countByObjeto(objeto);
        }

        // Contar ambientes por ID do objeto (CORRIGIDO)
        public long contarPorObjetoId(Integer objetoId) {
            return ambienteRepository.countByObjetoId(objetoId);
        }

        // Contar ambientes por tipo de objeto
        public long contarPorTipoObjeto(String tipoObjeto) {
            return ambienteRepository.countByObjetoTipoObjeto(tipoObjeto);
        }

        // Contar ambientes com objetos ativos
        public long contarPorObjetoAtivo(Integer ativo) {
            return ambienteRepository.countByObjetoAtivo(ativo);
        }

        // Contar ambientes por usuário (NOVO)
        public long contarPorUsuarioId(Integer usuarioId) {
            return ambienteRepository.countByUsuarioId(usuarioId);
        }

        // ============================================================
        // DECORATOR PATTERN - PADRÃO ESTRUTURAL
        // Permite adicionar funcionalidades extras às operações existentes
        // sem modificar o código original
        // Adiciona responsabilidades dinamicamente aos objetos através de composição
        // ============================================================

        /**
         * Interface base para decorar operações de Ambiente
         * Padrão Decorator - Define o contrato para decorators
         */
        public interface AmbienteOperationDecorator {
            void executarAntes();
            void executarDepois();
        }

        /**
         * Decorator para adicionar logging às operações
         * Padrão Decorator - Implementação concreta para logging
         */
        private class LoggingDecorator implements AmbienteOperationDecorator {
            private String operacao;
            private Object parametros;

            public LoggingDecorator(String operacao, Object parametros) {
                this.operacao = operacao;
                this.parametros = parametros;
            }

            @Override
            public void executarAntes() {
                System.out.println("[LOG] Iniciando operação: " + operacao + " com parâmetros: " + parametros);
            }

            @Override
            public void executarDepois() {
                System.out.println("[LOG] Operação concluída: " + operacao);
            }
        }

        /**
         * Decorator para adicionar validações extras às operações
         * Padrão Decorator - Implementação concreta para validação
         */
        private class ValidationDecorator implements AmbienteOperationDecorator {
            private Ambiente ambiente;

            public ValidationDecorator(Ambiente ambiente) {
                this.ambiente = ambiente;
            }

            @Override
            public void executarAntes() {
                if (ambiente != null && ambiente.getNome() != null) {
                    System.out.println("[VALIDATION] Validando ambiente: " + ambiente.getNome());
                    // Aqui poderiam ser adicionadas validações extras
                    if (ambiente.getNome().trim().isEmpty()) {
                        System.out.println("[VALIDATION] Aviso: Nome do ambiente está vazio");
                    }
                    // Validação da descrição (usando o método Facade da entidade)
                    if (!ambiente.isAmbienteCompleto()) {
                        System.out.println("[VALIDATION] Aviso: Ambiente não está completamente configurado");
                    }
                }
            }

            @Override
            public void executarDepois() {
                System.out.println("[VALIDATION] Validação concluída para ambiente");
            }
        }

        /**
         * Decorator para adicionar monitoramento de performance
         * Padrão Decorator - Implementação concreta para performance
         */
        private class PerformanceDecorator implements AmbienteOperationDecorator {
            private String operacao;
            private long tempoInicio;

            public PerformanceDecorator(String operacao) {
                this.operacao = operacao;
            }

            @Override
            public void executarAntes() {
                tempoInicio = System.currentTimeMillis();
                System.out.println("[PERFORMANCE] Iniciando monitoramento da operação: " + operacao);
            }

            @Override
            public void executarDepois() {
                long tempoExecucao = System.currentTimeMillis() - tempoInicio;
                System.out.println("[PERFORMANCE] Operação " + operacao + " executada em " + tempoExecucao + "ms");

                // Alertas de performance
                if (tempoExecucao > 1000) {
                    System.out.println("[PERFORMANCE] ALERTA: Operação " + operacao + " demorou mais que 1 segundo!");
                }
            }
        }

        /**
         * Decorator para adicionar auditoria às operações
         * Padrão Decorator - NOVA implementação para auditoria
         */
        private class AuditDecorator implements AmbienteOperationDecorator {
            private String operacao;
            private String usuario;
            private Object entidade;

            public AuditDecorator(String operacao, String usuario, Object entidade) {
                this.operacao = operacao;
                this.usuario = usuario;
                this.entidade = entidade;
            }

            @Override
            public void executarAntes() {
                System.out.println("[AUDIT] Usuário " + usuario + " iniciando " + operacao + " em " + java.time.LocalDateTime.now());
            }

            @Override
            public void executarDepois() {
                System.out.println("[AUDIT] Operação " + operacao + " concluída por " + usuario + " em " + java.time.LocalDateTime.now());
                // Aqui poderia salvar no banco de dados para auditoria real
            }
        }

        // ============================================================
        // MÉTODOS QUE USAM O DECORATOR PATTERN
        // Estes são métodos EXTRAS - não afetam os métodos originais
        // ============================================================

        /**
         * Salvar ambiente com logging usando Decorator Pattern
         */
        public Ambiente salvarComLogging(Ambiente ambiente) {
            LoggingDecorator decorator = new LoggingDecorator("salvar", ambiente.getNome());
            decorator.executarAntes();
            Ambiente resultado = ambienteRepository.save(ambiente);
            decorator.executarDepois();
            return resultado;
        }

        /**
         * Salvar ambiente com validação extra usando Decorator Pattern
         */
        public Ambiente salvarComValidacao(Ambiente ambiente) {
            ValidationDecorator decorator = new ValidationDecorator(ambiente);
            decorator.executarAntes();
            Ambiente resultado = ambienteRepository.save(ambiente);
            decorator.executarDepois();
            return resultado;
        }

        /**
         * Buscar por ID com monitoramento de performance usando Decorator Pattern
         */
        public Optional<Ambiente> buscarPorIdComPerformance(Integer id) {
            PerformanceDecorator decorator = new PerformanceDecorator("buscarPorId");
            decorator.executarAntes();
            Optional<Ambiente> resultado = ambienteRepository.findById(id);
            decorator.executarDepois();
            return resultado;
        }

        /**
         * Salvar ambiente com auditoria usando Decorator Pattern
         */
        public Ambiente salvarComAuditoria(Ambiente ambiente, String usuario) {
            AuditDecorator decorator = new AuditDecorator("salvar", usuario, ambiente);
            decorator.executarAntes();
            Ambiente resultado = ambienteRepository.save(ambiente);
            decorator.executarDepois();
            return resultado;
        }

        /**
         * Atualizar ambiente com múltiplos decorators
         * Exemplo de uso de múltiplos decorators em sequência
         */
        public Ambiente atualizarComDecorators(Ambiente ambiente) {
            // Usando múltiplos decorators
            LoggingDecorator logDecorator = new LoggingDecorator("atualizar", ambiente.getIdAmbiente());
            ValidationDecorator validationDecorator = new ValidationDecorator(ambiente);
            PerformanceDecorator performanceDecorator = new PerformanceDecorator("atualizar");

            // Executar decorators antes
            logDecorator.executarAntes();
            validationDecorator.executarAntes();
            performanceDecorator.executarAntes();

            // Operação principal
            Ambiente resultado = ambienteRepository.save(ambiente);

            // Executar decorators depois (ordem inversa)
            performanceDecorator.executarDepois();
            validationDecorator.executarDepois();
            logDecorator.executarDepois();

            return resultado;
        }

        /**
         * Método utilitário para aplicar decorator específico
         * Padrão Template Method - estrutura fixa com comportamento variável
         */
        public <T> T executarComDecorator(AmbienteOperationDecorator decorator, java.util.function.Supplier<T> operacao) {
            decorator.executarAntes();
            T resultado = operacao.get();
            decorator.executarDepois();
            return resultado;
        }

        // ============================================================
        // FACTORY PATTERN PARA DECORATORS
        // Facilita a criação de decorators
        // ============================================================

        /**
         * Enum para facilitar a escolha do tipo de decorator
         * Padrão Enum - define tipos seguros para decorators
         */
        public enum TipoDecorator {
            LOGGING,
            VALIDATION,
            PERFORMANCE,
            AUDIT
        }

        /**
         * Factory method para criar decorators
         * Padrão Factory Method - centraliza a criação de decorators
         */
        public AmbienteOperationDecorator criarDecorator(TipoDecorator tipo, Object... params) {
            switch (tipo) {
                case LOGGING:
                    return new LoggingDecorator(
                            params.length > 0 ? params[0].toString() : "operacao",
                            params.length > 1 ? params[1] : null
                    );
                case VALIDATION:
                    return new ValidationDecorator(
                            params.length > 0 && params[0] instanceof Ambiente ? (Ambiente) params[0] : null
                    );
                case PERFORMANCE:
                    return new PerformanceDecorator(
                            params.length > 0 ? params[0].toString() : "operacao"
                    );
                case AUDIT:
                    return new AuditDecorator(
                            params.length > 0 ? params[0].toString() : "operacao",
                            params.length > 1 ? params[1].toString() : "usuario_desconhecido",
                            params.length > 2 ? params[2] : null
                    );
                default:
                    return new LoggingDecorator("default", null);
            }
        }

        // ============================================================
        // MÉTODOS DE NEGÓCIO ESPECÍFICOS (USANDO FACADE DA ENTIDADE)
        // Demonstram o uso do padrão Facade implementado na entidade Ambiente
        // ============================================================

        /**
         * Busca ambiente e retorna seu resumo completo
         * Usa o método Facade da entidade Ambiente
         */
        public String obterResumoAmbiente(Integer id) {
            Optional<Ambiente> ambiente = buscarPorIdCompleto(id);
            return ambiente.isPresent() ? ambiente.get().obterResumoCompleto() : "Ambiente não encontrado";
        }

        /**
         * Verifica se um ambiente está completamente configurado
         * Usa o método Facade da entidade Ambiente
         */
        public boolean verificarAmbienteCompleto(Integer id) {
            Optional<Ambiente> ambiente = buscarPorId(id);
            return ambiente.isPresent() && ambiente.get().isAmbienteCompleto();
        }

        /**
         * Lista ambientes incompletos (que precisam de mais configuração)
         * Combina consulta com validação de negócio
         */
        public List<Ambiente> listarAmbientesIncompletos() {
            return buscarTodos().stream()
                    .filter(ambiente -> !ambiente.isAmbienteCompleto())
                    .toList();
        }
    }