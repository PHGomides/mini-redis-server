package io.github.phgomides.miniredis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class RedisServer {
    private final int port;

    public RedisServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Iniciando servidor na porta " + port);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor pronto! Aguardando conexões...");

            while (true) {
                Socket client = server.accept();
                System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
                Scanner mensageiro = new Scanner(client.getInputStream());
                while (mensageiro.hasNextLine()) {
                    String mensagem = mensageiro.nextLine();
                    System.out.println("Mensagem: " + mensagem);
                }
            }

        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}
