package io.github.phgomides.miniredis.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RedisServer {
    private final int port;

    private final Map<String, String> dados = new HashMap<>();

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

                PrintWriter saida = new PrintWriter(client.getOutputStream(), true);

                while (mensageiro.hasNextLine()) {
                    String mensagem = mensageiro.nextLine();
                    if (mensagem.trim().isEmpty()) continue;
                    System.out.println("[LOG] Comando recebido: " + mensagem);

                    String[] partes = mensagem.split(" ");
                    if (partes.length == 0) continue;
                    String comando = partes[0].toUpperCase();

                    switch (comando) {
                        case "SET":
                            if (partes.length < 3) {
                                System.out.println("[LOG] Comando SET (Erro)");
                                saida.println("ERRO: O comando SET precisa de chave e valor (ex: SET token usuario)");
                            } else {
                                String chave = partes[1];
                                String valor = partes[2];
                                dados.put(chave, valor);
                                System.out.println("[LOG] Comando SET (Salvo): " + chave + " -> " + valor );
                                saida.println("OK (Salvo): " + chave + " -> " + valor);
                            }
                            break;

                        case "GET":
                            if (partes.length < 2) {
                                System.out.println("[LOG] Comando GET (Erro)");
                                saida.println("ERRO: O comando GET precisa da chave (ex: GET token)");
                            } else {
                                String chave = partes[1];
                                String valor = dados.get(chave);

                                if (valor == null) {
                                    saida.println("Chave não encontrada (nil)");

                                } else {
                                    System.out.println("Valor consultado: " + valor);
                                    saida.println("Valor encontrado: " + valor);
                                }
                            }

                            break;

                        case "DEL":
                            if (partes.length < 2) {
                                System.out.println("[LOG] Comando DEL (Erro)");
                                saida.println("ERRO: O comando DEL precisa da chave");
                            } else {
                                String chave = partes[1];
                                dados.remove(chave);
                                System.out.println("Removido: " + chave);
                                saida.println("OK (Removido): " + chave);
                            }
                            break;

                        default:
                            System.out.println("Comando desconhecido: " + comando);
                            saida.println("ERRO: Comando desconhecido (" + comando + ")");
                            break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Erro crítico no servidor: " + e.getMessage());
        }
    }
}