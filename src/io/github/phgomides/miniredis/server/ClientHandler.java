package io.github.phgomides.miniredis.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Map<String, String> dados;

    public ClientHandler(Socket socket, Map<String, String> dados) {
        this.socket = socket;
        this.dados = dados;
    }

    @Override
    public void run() {
        try {

            Scanner mensageiro = new Scanner(socket.getInputStream());
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

            while (mensageiro.hasNextLine()) {
                String mensagem = mensageiro.nextLine();
                if (mensagem.trim().isEmpty()) continue;
                System.out.println("[Thread " + Thread.currentThread().getId() + "] Comando: " + mensagem);

                String[] partes = mensagem.split(" ");
                if (partes.length == 0) continue;
                String comando = partes[0].toUpperCase();

                switch (comando) {
                    case "SET":
                        if (partes.length < 3) {
                            saida.println("ERRO: O comando SET precisa de chave e valor (ex: SET token usuario)");
                        } else {
                            String chave = partes[1];
                            String valor = partes[2];
                            dados.put(chave, valor);
                            saida.println("OK (Salvo): " + chave + " -> " + valor);
                        }
                        break;

                    case "GET":
                        if (partes.length < 2) {
                            saida.println("ERRO: O comando GET precisa da chave (ex: GET token)");
                        } else {
                            String chave = partes[1];
                            String valor = dados.get(chave);

                            if (valor == null) {
                                saida.println("Chave não encontrada (nil)");

                            } else {
                                saida.println("Valor encontrado: " + valor);
                            }
                        }

                        break;

                    case "DEL":
                        if (partes.length < 2) {
                            saida.println("ERRO: O comando DEL precisa da chave");
                        } else {
                            String chave = partes[1];
                            dados.remove(chave);
                            saida.println("OK (Removido): " + chave);
                        }
                        break;

                    default:
                        saida.println("ERRO: Comando desconhecido (" + comando + ")");
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        } finally {

            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {

            }
        }
    }
}



