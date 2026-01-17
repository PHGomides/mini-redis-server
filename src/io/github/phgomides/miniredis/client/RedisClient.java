package io.github.phgomides.miniredis.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RedisClient {
    void main() {
        try {
            System.out.println("Conectando ao servidor...");
            Socket socket = new Socket("localhost", 6379);

            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            Scanner leitor = new Scanner(socket.getInputStream());

            System.out.println("> Enviando: SET sessao_pedro usuario_logado");
            escritor.println("SET sessao_pedro usuario_logado");
            String resposta = leitor.nextLine();
            System.out.println("Servidor respondeu: " + resposta);

            System.out.println("\n> Enviando: GET sessao_pedro");
            escritor.println("GET sessao_pedro");
            resposta = leitor.nextLine();
            System.out.println("Servidor respondeu: " + resposta);

            System.out.println("\n> Enviando: DEL sessao_pedro");
            escritor.println("DEL sessao_pedro");
            resposta = leitor.nextLine();
            System.out.println("Servidor respondeu: " + resposta);

            System.out.println("\n> Enviando: GET sessao_pedro");
            escritor.println("GET sessao_pedro");
            resposta = leitor.nextLine();
            System.out.println("Servidor respondeu: " + resposta);

            socket.close();

        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}
