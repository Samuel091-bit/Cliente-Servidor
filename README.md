# Projeto: Arquitetura Cliente-Servidor (Chat Multi-Client)

**Instituição:** Senai Fatesg - Goiânia  
**Curso:** Análise e Desenvolvimento de Sistemas (ADS)  
**Disciplina:** Sistemas Distribuídos  

**Integrantes:**
- Filipi Vieira Giaconete
- João Victor do Santos Gonçalves
- Gustavo Fernandes Doutor
- Samuel da Silva

---

## 1. Objetivo do Projeto
Projetar, implementar em Java e demonstrar na prática uma arquitetura de programação distribuída utilizando o modelo Cliente-Servidor. O sistema consolida os conceitos de comunicação via rede, descentralização de nós e integração de mensagens através de um servidor central utilizando `java.net.Socket`.

## 2. Apresentação Teórica e Arquitetural

### O Modelo Cliente-Servidor
O modelo Cliente-Servidor é uma estrutura de computação distribuída que divide as tarefas entre os fornecedores de recursos (Servidores) e os requerentes de serviços (Clientes). Neste projeto, aplicamos esse conceito em um chat de múltiplas vias:
- **Servidor:** Atua como um nó centralizado. Ele gerencia as conexões, mantém uma lista ativa de usuários e é responsável por distribuir as mensagens (broadcast) garantindo concorrência segura.
- **Cliente:** Foca na interação com o usuário final (I/O). Ele capta as entradas do teclado, envia para o servidor via rede e possui uma thread assíncrona para escutar e exibir mensagens recebidas, sem bloquear o envio.

### Casos de Uso no Mercado
A arquitetura Cliente-Servidor é a base da internet moderna e de sistemas corporativos, sendo aplicada em:
- **APIs REST e Microsserviços:** Clientes (front-end, aplicativos mobile) consumindo dados de servidores back-end via HTTP.
- **Sistemas de Banco de Dados:** Aplicações conectando-se a instâncias centralizadas (ex: PostgreSQL, Oracle) para leitura e escrita segura de dados.
- **Jogos Multiplayer e Streaming:** Comunicação em tempo real baseada em Sockets e UDP/TCP para sincronização de estado entre múltiplos jogadores e o servidor host.

## 3. Fluxo de Dados e Diagrama

O fluxo de comunicação ocorre da seguinte forma:
1. O Servidor abre um `ServerSocket` na porta `8080` e entra em estado de escuta.
2. O Cliente `A` abre um `Socket` apontando para o IP do Servidor (`127.0.0.1`) e porta `8080`.
3. O Servidor aceita a conexão e delega o cliente para uma `Thread` exclusiva (`ClientHandler`).
4. O Cliente `A` digita uma mensagem no terminal (`InputStream`). A mensagem trafega via protocolo TCP/IP até o Servidor.
5. O Servidor processa a string e realiza um laço de repetição (`Broadcast`), enviando a mensagem para o `OutputStream` de todos os Clientes conectados (`B`, `C`, etc.).

```text
[Terminal Cliente A] ---> (TCP: Envia Mensagem) ---> [Servidor Central (Porta 8080)]
                                                               |
                                                               |---> (Broadcast) ---> [Terminal Cliente B]
                                                               |---> (Broadcast) ---> [Terminal Cliente C]
