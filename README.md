# Mini Redis Server (Java) ☕🚀

Um servidor Key-Value simples e leve, inspirado no Redis, construído do zero em Java puro (sem bibliotecas externas) para fins de estudo de Sockets e Estruturas de Dados.

## 🎯 Objetivo
Simular o funcionamento de um banco de dados NoSQL em memória, permitindo comunicação via rede (TCP) entre um Cliente e um Servidor. Ideal para entender como funcionam caches de sessão.

## 🛠️ Funcionalidades
O servidor aceita conexões na porta **6379** e suporta os seguintes comandos:

* **`SET chave valor`**: Salva um dado na memória.
    * *Ex:* `SET sessao_123 usuario_pedro`
* **`GET chave`**: Recupera um dado salvo.
    * *Ex:* `GET sessao_123` -> Retorna: `usuario_pedro`
* **`DEL chave`**: Remove um dado da memória.
    * *Ex:* `DEL sessao_123`

## 🚀 Como Rodar

1.  **Inicie o Servidor:**
    Execute a classe `io.github.phgomides.miniredis.Main`.
2.  **Conecte o Cliente:**
    Execute a classe `io.github.phgomides.miniredis.client.RedisClient` ou use o Netcat:
    ```bash
    nc localhost 6379
    ```

## 📚 O que aprendi com este projeto
* **Java Sockets:** Comunicação TCP/IP.
* **HashMap:** Armazenamento de alta performance em memória (O(1)).
* **Protocolo:** Criação de um protocolo simples de texto (Request/Response).
* **Arquitetura:** Separação de responsabilidades (Server Listener vs Data Store).