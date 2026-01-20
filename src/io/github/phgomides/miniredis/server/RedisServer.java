package io.github.phgomides.miniredis.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class RedisServer {
    private final int port;

    private final Map<String, String> dados = new ConcurrentHashMap<>();

    public RedisServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Iniciando servidor na porta " + port);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor pronto! Aguardando conexões...");

            while (true) {
                Socket client = server.accept();
                System.out.println("Novo cliente conectado: " + client.getInetAddress());

                ClientHandler handler = new ClientHandler(client, dados);
                Thread threadDoCliente = new Thread(handler);
                threadDoCliente.start();

            }
        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}
