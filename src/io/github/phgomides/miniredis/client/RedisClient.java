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
            Scanner leitorTeclado = new Scanner(System.in);

            System.out.println("Conectado! Digite comandos (SET key val, GET key, DEL key) ou 'sair' para fechar.");

            while (true){
                System.out.print("> ");
                String comando = leitorTeclado.nextLine();

                if (comando.equalsIgnoreCase("sair")) {
                    break;
                }

                escritor.println(comando);

                if (leitor.hasNextLine()) {
                    String resposta = leitor.nextLine();
                    System.out.println("Servidor: " + resposta);
                }
            }
            socket.close();
            System.out.println("Conexão encerrada.");

        } catch (IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
}
