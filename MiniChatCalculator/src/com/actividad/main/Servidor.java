package com.actividad.main;

import java.io.*;
import java.net.*;

public class Servidor {

	
	private static final int PORT = 9999;
	
	private static final int BACKLOG = 10;
	
	private static int NUM_INICIAL = 0;
	
	private static int COUNT_CLIENTS = 0;

	
	public static void main(String[] args) 
	{

		try {

			InetAddress bindAddr = InetAddress.getByName("localhost");

			try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG, bindAddr)) 
			{

				System.out.printf("Seridor levantado\n");

				ObjetoCompartido compartido = new ObjetoCompartido(NUM_INICIAL);

				System.out.printf("El Objeto compartido ha sido creado\n");

				for (;;) 
				{

					Socket socket = serverSocket.accept();

					System.out.printf("Cliente número: %d se ha conectado al servidor \n", ++COUNT_CLIENTS);

					Thread thread = new Thread(new HiloServidor(socket, compartido));

					thread.start();
					
				}
			}
			
		} catch (UnknownHostException e) {
			
			System.err.printf("Error: No se puede resolver el nombre de host (%s)\n", e.getMessage());
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			System.err.printf("Error: I/O al interactuar con el socket: %s\n", e.getMessage());
			
			e.printStackTrace();
			
		}

	}

}
