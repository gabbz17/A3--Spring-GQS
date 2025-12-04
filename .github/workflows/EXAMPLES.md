# Exemplos Práticos do CI/CD Pipeline

## 🎯 Cenários de Uso

Este documento contém exemplos práticos de como o pipeline CI/CD funciona em diferentes situações.

## 📝 Cenário 1: Desenvolvimento de Nova Feature

### Workflow do Desenvolvedor

```bash
# 1. Criar branch de feature
git checkout -b feature/adicionar-relatorios
git push -u origin feature/adicionar-relatorios

# 2. Desenvolver a funcionalidade
# ... código aqui ...

# 3. Testar localmente
./mvnw clean test
./mvnw spring-boot:run

# 4. Commit e push
git add .
git commit -m "feat: adiciona relatórios de transações"
git push

# 5. Criar Pull Request no GitHub
# O CI rodará automaticamente e validará:
# ✅ Build passa
# ✅ Testes passam
# ✅ Código compila
```

### O que acontece no CI:

```
┌─────────────────────────────────────┐
│  Pull Request aberto                │
│  feature/adicionar-relatorios       │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  🏗️  Job: Build and Test            │
│  - Checkout código                  │
│  - Setup JDK 21                     │
│  - Cache Maven dependencies         │
│  - Compilar projeto                 │
│  - Executar testes                  │
│  - Gerar relatórios                 │
│  Status: ✅ Passou                  │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  🔍 Job: Code Quality               │
│  - Análise de código                │
│  - Verificações de qualidade        │
│  Status: ✅ Passou                  │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  ⚠️  Deploy PULADO                  │
│  (Só ocorre em push para main)      │
└─────────────────────────────────────┘
```

## 🚀 Cenário 2: Deploy para Produção

### Após aprovação do PR e merge para main:

```bash
# 1. PR foi aprovado e feito merge
git checkout main
git pull origin main

# 2. Pipeline inicia automaticamente
```

### Fluxo Completo do Pipeline:

```
┌─────────────────────────────────────┐
│  Push para branch main              │
│  Commit: a1b2c3d4                   │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  🏗️  Build and Test                 │
│  Tempo: ~3 min                      │
│  Resultado: ✅                      │
│  Artefato: A3-Spring-0.0.1.jar      │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  🔍 Code Quality                    │
│  Tempo: ~1 min                      │
│  Resultado: ✅                      │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  🚀 Deploy to Production            │
│  Tempo: ~1 min                      │
│  Versão: v20251204-143022-a1b2c3d4  │
│                                     │
│  Ações:                             │
│  1. Download JAR artifact           │
│  2. Gera metadados:                 │
│     - build-info.txt                │
│     - build-metadata.json           │
│  3. Cria/atualiza branch production │
│  4. Commit com info de deploy       │
│  5. Push para production            │
│  Resultado: ✅                      │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  📢 Notifications                   │
│  Resumo: Todos jobs passaram ✅     │
└─────────────────────────────────────┘
```

### O que fica na branch production:

```bash
# Checkout da branch production
git checkout production
git pull origin production

# Conteúdo:
ls -la
# A3-Spring-0.0.1-SNAPSHOT.jar
# build-info.txt
# build-metadata.json
```

**Arquivo `build-info.txt`:**
```
====================================
PRODUCTION BUILD INFORMATION
====================================
Version: v20251204-143022-a1b2c3d4
Build Date: 2025-12-04T14:30:22Z
Commit SHA: a1b2c3d4e5f6789...
Branch: main
Triggered by: seu-usuario
Workflow Run: 42
====================================
```

**Arquivo `build-metadata.json`:**
```json
{
  "version": "v20251204-143022-a1b2c3d4",
  "buildDate": "2025-12-04T14:30:22Z",
  "commitSha": "a1b2c3d4e5f6789...",
  "branch": "main",
  "triggeredBy": "seu-usuario",
  "workflowRun": "42",
  "repository": "owner/A3--Spring-GQS"
}
```

## 🔧 Cenário 3: Hotfix Urgente

### Quando há um bug crítico em produção:

```bash
# 1. Criar branch de hotfix
git checkout main
git pull origin main
git checkout -b hotfix/corrigir-validacao-critica

# 2. Fazer a correção
# ... código ...

# 3. Testar localmente
./mvnw clean test

# 4. Commit e push DIRETO para main (em emergências)
git add .
git commit -m "fix: corrige validação crítica de segurança"
git push origin hotfix/corrigir-validacao-critica

# 5. Criar PR para main
# Se aprovado, o merge dispara deploy imediato
```

### Timeline de um Hotfix:

| Tempo | Ação |
|-------|------|
| T+0min | Merge do hotfix para main |
| T+1min | CI inicia build e testes |
| T+4min | Testes passam, build completo |
| T+5min | Code quality check passa |
| T+6min | Deploy para production iniciado |
| T+7min | Nova versão disponível em production |

**Total: ~7 minutos** do merge até deploy!

## 📊 Cenário 4: Monitoramento de Builds

### Visualizando o status do pipeline:

1. **No GitHub:**
   - Acesse: `Actions` tab
   - Veja histórico de execuções
   - Clique em uma execução para detalhes

2. **Via Badge (opcional):**

Adicione ao README.md:
```markdown
![CI/CD](https://github.com/seu-usuario/A3--Spring-GQS/actions/workflows/ci-cd.yml/badge.svg)
```

Resultado:
![CI/CD](https://github.com/seu-usuario/A3--Spring-GQS/actions/workflows/ci-cd.yml/badge.svg)

### Logs Importantes:

```bash
# Ver logs de teste
# Actions → Workflow → Job: Build and Test → Step: Run unit tests

# Ver artefatos gerados
# Actions → Workflow → Summary → Artifacts
```

## 🧪 Cenário 5: Teste Falha no CI

### O que acontece:

```bash
# Você faz push
git push origin main

# CI detecta teste falhando
```

### Output do Pipeline:

```
┌─────────────────────────────────────┐
│  🏗️  Build and Test                 │
│                                     │
│  [INFO] Running tests...            │
│  [ERROR] Test failed:               │
│    PeopleServiceTest.testSalvar     │
│    Expected: 200                    │
│    Actual: 500                      │
│                                     │
│  Status: ❌ FAILED                  │
└─────────────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│  ⚠️  Pipeline PARADO                │
│  Deploy NÃO executado               │
│  Produção não afetada ✅            │
└─────────────────────────────────────┘
```

### Como corrigir:

```bash
# 1. Ver logs no GitHub Actions
# 2. Reproduzir localmente
./mvnw clean test -Dtest=PeopleServiceTest#testSalvar

# 3. Corrigir o teste
# ... correção ...

# 4. Commitar correção
git add .
git commit -m "fix: corrige teste de salvamento"
git push

# 5. Pipeline roda novamente automaticamente
```

## 🔄 Cenário 6: Rollback de Deploy

### Se o deploy introduziu um bug:

```bash
# 1. Identificar versão anterior na branch production
git checkout production
git log --oneline
# v20251204-143022-a1b2c3d4  ← versão com bug
# v20251204-120000-xyz12345  ← versão anterior (boa)

# 2. Reverter para commit anterior
git checkout v20251204-120000-xyz12345

# 3. Baixar artefato daquela versão
# O JAR estará disponível naquele commit

# 4. OU: Reverter o commit problemático na main
git checkout main
git revert a1b2c3d4
git push origin main

# Pipeline rodará novamente com código revertido
```

## 🎨 Cenário 7: Deploy Manual (se necessário)

### Baixar e executar artefato manualmente:

```bash
# 1. Clonar repositório
git clone https://github.com/seu-usuario/A3--Spring-GQS.git
cd A3--Spring-GQS

# 2. Checkout da branch production
git checkout production

# 3. Verificar versão
cat build-info.txt

# 4. Executar aplicação
java -jar A3-Spring-0.0.1-SNAPSHOT.jar

# 5. Ou com perfil específico
java -jar -Dspring.profiles.active=prod A3-Spring-0.0.1-SNAPSHOT.jar
```

### Deploy em servidor remoto:

```bash
# No servidor de produção
cd /opt/app

# Baixar última versão
git clone --single-branch --branch production \
  https://github.com/seu-usuario/A3--Spring-GQS.git latest

# Parar aplicação antiga
systemctl stop myapp

# Copiar novo JAR
cp latest/*.jar ./app.jar

# Iniciar nova versão
systemctl start myapp

# Verificar logs
journalctl -u myapp -f
```

## 📈 Cenário 8: Análise de Métricas

### Extrair informações de builds:

```bash
# Download do build-metadata.json da production
git checkout production
cat build-metadata.json | jq .

# Verificar todas as versões deployadas
git log --all --oneline --grep="Production Deploy"

# Ver histórico de versões
git log --oneline --format="%s" | grep "v202"
```

### Exemplo de análise:

```bash
# Quantos deploys este mês?
git log --since="2025-12-01" --grep="Production Deploy" --oneline | wc -l

# Último deploy
git log -1 --grep="Production Deploy" --format="%ai %s"

# Tempo médio entre deploys
# (pode ser calculado com script)
```

## 🛡️ Cenário 9: Segurança e Validações

### O que o pipeline NÃO permite:

```bash
# ❌ Deploy sem testes passando
# ❌ Deploy sem build bem-sucedido
# ❌ Deploy sem análise de qualidade
# ❌ Merge de PR com CI falhando
```

### O que o pipeline GARANTE:

```bash
# ✅ Código compila
# ✅ Testes passam
# ✅ Qualidade validada
# ✅ Artefato gerado corretamente
# ✅ Versionamento automático
# ✅ Rastreabilidade completa
```

## 🎯 Cenário 10: Múltiplos Ambientes

### Estender para staging/production:

```yaml
# Adicionar ao workflow
deploy-staging:
  if: github.ref == 'refs/heads/develop'
  # ... deploy para staging

deploy-production:
  if: github.ref == 'refs/heads/main'
  # ... deploy para production
```

### Branch strategy:

```
develop  → CI/CD → staging branch  (ambiente de testes)
   ↓
   PR
   ↓
main     → CI/CD → production branch (ambiente produção)
```

## 📚 Recursos Adicionais

### Comandos úteis:

```bash
# Verificar status do último workflow
gh run list --limit 1

# Ver logs do último workflow
gh run view --log

# Re-executar workflow falhado
gh run rerun <run-id>

# Disparar workflow manualmente
gh workflow run ci-cd.yml
```

### Exemplos de integração:

```bash
# Webhook para notificação
curl -X POST https://seu-webhook.com/deploy \
  -H "Content-Type: application/json" \
  -d '{"version": "v20251204-143022-a1b2c3d4", "status": "success"}'
```

---

## 🎓 Conclusão

Este pipeline CI/CD foi projetado para:

- ✅ **Automatizar** todo o processo de build e deploy
- ✅ **Garantir qualidade** com testes automáticos
- ✅ **Rastrear versões** com versionamento automático
- ✅ **Facilitar rollbacks** com histórico completo
- ✅ **Economizar tempo** com builds rápidos e cache
- ✅ **Aumentar confiança** com validações automáticas

**Próximos passos:** Explore os outros documentos para configuração avançada!

---

**Criado em:** 2025-12-04
**Versão:** 1.0.0
