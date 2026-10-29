import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORTA = 8080;
    // Lista para manter os clientes conectados e fazer o broadcast
    private static Set<PrintWriter> escritoresClientes = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor de Chat iniciado na porta " + PORTA + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress());
                // Inicia uma thread para o novo cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método sincronizado para evitar concorrência ao enviar mensagens
    public static synchronized void broadcast(String mensagem) {
        for (PrintWriter writer : escritoresClientes) {
            writer.println(mensagem);
        }
    }

    public static synchronized void adicionarCliente(PrintWriter writer) {
        escritoresClientes.add(writer);
    }

    public static synchronized void removerCliente(PrintWriter writer) {
        escritoresClientes.remove(writer);
    }
}