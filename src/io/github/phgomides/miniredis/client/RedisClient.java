package io.github.phgomides.miniredis.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RedisClient {
    void main(){
        try {
            Socket socket = new Socket("localhost", 6379);
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            escritor.println("Teste de conexão OK, Clinte comunicou com Servidor");
        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}
