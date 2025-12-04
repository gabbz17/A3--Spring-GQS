# CI/CD Pipeline Documentation

## Visão Geral

Este projeto utiliza GitHub Actions para implementar um pipeline completo de CI/CD (Continuous Integration/Continuous Deployment) para a aplicação Spring Boot.

## Estrutura do Pipeline

O workflow é dividido em **4 jobs principais** que executam em sequência:

### 1. 🏗️ Build and Test
**Responsabilidades:**
- Checkout do código fonte
- Configuração do JDK 21 (Temurin)
- Cache de dependências Maven
- Compilação do projeto
- Execução de testes unitários e de integração
- Geração de relatórios de testes
- Build do arquivo JAR
- Upload de artefatos

**Duração estimada:** 3-5 minutos

### 2. 🔍 Code Quality Analysis
**Responsabilidades:**
- Análise de qualidade do código
- Verificação de padrões e best practices
- Pode ser estendido com SonarQube, Checkstyle, etc.

**Duração estimada:** 1-2 minutos

### 3. 🚀 Deploy to Production Branch
**Responsabilidades:**
- Download dos artefatos buildados
- Geração de versionamento automático
- Criação de metadados do build
- Deploy para branch `production`
- Criação de release (se houver tag)

**Versionamento:** `vYYYYMMDD-HHMMSS-{SHA8}`
- Exemplo: `v20251204-143022-a1b2c3d4`

**Duração estimada:** 1-2 minutos

### 4. 📢 Notifications
**Responsabilidades:**
- Resumo do status de todos os jobs
- Notificações (pode ser integrado com Slack, Discord, etc.)

**Duração estimada:** < 1 minuto

## Triggers (Gatilhos)

O pipeline é acionado automaticamente quando:

1. **Push para branches principais:**
   ```yaml
   branches: [main, master]
   ```

2. **Pull Requests para branches principais:**
   ```yaml
   branches: [main, master]
   ```

3. **Execução manual:**
   - Via interface do GitHub Actions (workflow_dispatch)

## Análise do Projeto

### Tecnologias Identificadas

| Componente | Versão/Detalhes |
|------------|-----------------|
| **Java** | 21 (Eclipse Temurin) |
| **Spring Boot** | 3.3.10 |
| **Build Tool** | Maven (mvnw wrapper) |
| **Banco de Dados** | PostgreSQL (runtime) |
| **Documentação API** | SpringDoc OpenAPI 2.6.0 |
| **Testes** | 4 arquivos de teste identificados |

### Estrutura do Projeto

```
src/
├── main/java/com/example/demo/
│   ├── A3SpringApplication.java          # Main class
│   ├── web/
│   │   ├── controller/                   # Controllers REST
│   │   ├── dto/                          # Data Transfer Objects
│   │   └── mapper/                       # Mappers
│   ├── entity/                           # Entidades JPA
│   ├── repository/                       # Repositories
│   ├── Service/                          # Serviços
│   └── docs/                             # Configuração OpenAPI
└── test/java/com/example/demo/
    ├── JUnit/                            # Testes JUnit
    ├── integration/                      # Testes de integração
    ├── restAssured/                      # Testes REST Assured
    └── A3SpringApplicationTests.java     # Testes da aplicação
```

### Dependências Principais

- **spring-boot-starter-data-jpa** - Persistência de dados
- **spring-boot-starter-validation** - Validações
- **spring-boot-starter-web** - API REST
- **postgresql** - Driver PostgreSQL
- **springdoc-openapi** - Documentação API
- **lombok** - Redução de boilerplate
- **spring-boot-starter-test** - Testes

## Artefatos Gerados

### Durante o Build

1. **application-jar** (retenção: 30 dias)
   - Arquivo JAR executável da aplicação
   - Localização: `target/*.jar`

2. **test-reports** (retenção: 14 dias)
   - Relatórios dos testes (Surefire)
   - Relatórios de cobertura (se configurado)

### Na Branch Production

Após deploy bem-sucedido, a branch `production` conterá:

1. **Arquivo JAR** - Aplicação empacotada
2. **build-info.txt** - Informações legíveis do build
3. **build-metadata.json** - Metadados em JSON

Exemplo de `build-metadata.json`:
```json
{
  "version": "v20251204-143022-a1b2c3d4",
  "buildDate": "2025-12-04T14:30:22Z",
  "commitSha": "a1b2c3d4e5f6...",
  "branch": "main",
  "triggeredBy": "username",
  "workflowRun": "42",
  "repository": "owner/repo"
}
```

## Otimizações Implementadas

### 1. Cache de Dependências
```yaml
- uses: actions/cache@v4
  with:
    path: ~/.m2/repository
    key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
```
**Benefício:** Reduz tempo de build em ~60-80%

### 2. Maven Daemon
```yaml
env:
  MAVEN_OPTS: -Dhttp.keepAlive=false ...
```
**Benefício:** Melhora estabilidade em ambientes CI

### 3. Build Incremental
- Cache nativo do `setup-java@v4`
- Reutilização de artefatos entre jobs

### 4. Paralelização
- Jobs independentes executam em paralelo
- Build e testes executam simultaneamente quando possível

## Configuração de Secrets

Para funcionalidades adicionais, configure os seguintes secrets no GitHub:

| Secret | Descrição | Obrigatório |
|--------|-----------|-------------|
| `GITHUB_TOKEN` | Token automático do GitHub | ✅ Sim (auto-gerado) |
| `SLACK_WEBHOOK` | Webhook para notificações Slack | ❌ Opcional |
| `SONAR_TOKEN` | Token para SonarQube/SonarCloud | ❌ Opcional |

### Como configurar secrets:
1. Vá para: `Settings` → `Secrets and variables` → `Actions`
2. Clique em `New repository secret`
3. Adicione o nome e valor do secret

## Monitoramento

### Visualizar Execuções
1. Acesse a aba `Actions` no repositório
2. Selecione o workflow `CI/CD Pipeline`
3. Clique em uma execução específica para ver detalhes

### Logs Disponíveis
- Logs de compilação
- Saída dos testes
- Relatórios de cobertura
- Informações de deploy

## Troubleshooting

### ❌ Testes Falhando

**Problema:** Job `build-and-test` falha
**Solução:**
```bash
# Execute localmente para reproduzir
./mvnw clean test

# Verifique logs específicos
cat target/surefire-reports/*.txt
```

### ❌ Falha no Deploy para Production

**Problema:** Erro de permissão ao fazer push
**Solução:**
- Verifique se `GITHUB_TOKEN` tem permissões de escrita
- Em `Settings` → `Actions` → `General`
- Habilite: `Read and write permissions`

### ❌ Cache não está funcionando

**Problema:** Builds sempre baixam dependências
**Solução:**
- Verifique se o `pom.xml` não está mudando
- Limpe o cache nas configurações do repositório
- Re-execute o workflow

## Extensões Possíveis

### 1. Integração com SonarQube
```yaml
- name: SonarQube Analysis
  run: |
    ./mvnw sonar:sonar \
      -Dsonar.projectKey=${{ secrets.SONAR_PROJECT_KEY }} \
      -Dsonar.organization=${{ secrets.SONAR_ORG }} \
      -Dsonar.host.url=https://sonarcloud.io \
      -Dsonar.login=${{ secrets.SONAR_TOKEN }}
```

### 2. Deploy em Servidor/Cloud
```yaml
- name: Deploy to Production Server
  uses: appleboy/ssh-action@master
  with:
    host: ${{ secrets.SERVER_HOST }}
    username: ${{ secrets.SERVER_USER }}
    key: ${{ secrets.SSH_PRIVATE_KEY }}
    script: |
      cd /app
      wget ${{ github.server_url }}/${{ github.repository }}/releases/download/${{ env.VERSION_TAG }}/app.jar
      systemctl restart myapp
```

### 3. Build de Imagem Docker
```yaml
- name: Build Docker Image
  run: |
    docker build -t myapp:${{ env.VERSION_TAG }} .
    docker push myapp:${{ env.VERSION_TAG }}
```

### 4. Testes de Performance
```yaml
- name: Performance Tests
  run: |
    ./mvnw gatling:test
```

### 5. Security Scanning
```yaml
- name: Security Scan
  uses: aquasecurity/trivy-action@master
  with:
    scan-type: 'fs'
    scan-ref: '.'
```

## Melhorias Sugeridas para o Projeto

### 1. Adicionar Plugin de Cobertura de Código

Adicione ao [pom.xml](pom.xml):

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. Configurar Profiles Maven

Adicione profiles para diferentes ambientes:

```xml
<profiles>
    <profile>
        <id>dev</id>
        <properties>
            <spring.profiles.active>dev</spring.profiles.active>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <properties>
            <spring.profiles.active>prod</spring.profiles.active>
        </properties>
    </profile>
</profiles>
```

### 3. Adicionar Health Checks

Crie endpoint de health check para monitoramento:

```java
@RestController
@RequestMapping("/actuator")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }
}
```

### 4. Versionamento Semântico

Considere usar tags Git para releases:
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

### 5. Documentação da API

O projeto já usa SpringDoc, acesse:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Fluxo de Trabalho Recomendado

### Para Desenvolvedores

1. **Criar feature branch:**
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

2. **Desenvolver e testar localmente:**
   ```bash
   ./mvnw clean test
   ./mvnw spring-boot:run
   ```

3. **Commit e push:**
   ```bash
   git add .
   git commit -m "feat: adiciona nova funcionalidade"
   git push origin feature/nova-funcionalidade
   ```

4. **Abrir Pull Request:**
   - O CI rodará automaticamente
   - Aguarde aprovação e merge

5. **Após merge para main:**
   - CI/CD deploya automaticamente para `production`
   - Artefatos ficam disponíveis na branch `production`

### Para Deploy Manual

Se precisar fazer deploy manual:

```bash
# 1. Baixar artefato da branch production
git checkout production
git pull origin production

# 2. Copiar JAR
cp *.jar /caminho/destino/

# 3. Executar
java -jar A3-Spring-0.0.1-SNAPSHOT.jar
```

## Métricas e KPIs

O pipeline fornece as seguintes métricas:

- ⏱️ **Tempo total de build:** ~5-8 minutos
- ✅ **Taxa de sucesso:** Monitorada via GitHub Actions
- 📊 **Cobertura de testes:** Disponível após configurar JaCoCo
- 🐛 **Bugs detectados:** Via testes automatizados

## Contato e Suporte

Para questões sobre o CI/CD:
- Consulte os logs do GitHub Actions
- Verifique este documento
- Revise a configuração em [ci-cd.yml](ci-cd.yml)

---

**Última atualização:** 2025-12-04
**Versão do documento:** 1.0.0
