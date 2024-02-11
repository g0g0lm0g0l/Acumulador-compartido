package com.actividad.main;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {

	private static final int PORT = 9999;

	public static void main(String[] args) 
	{

		try {

			InetAddress address = InetAddress.getByName("localhost");

			try (	Socket socket = new Socket(address, PORT);

					ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());

					ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());

					Scanner scanner = new Scanner(System.in)
				) {

				
				
				String inputToSendToServer = null;
		
				do {

					System.out.printf("Ingresa un número entero:\n");

					inputToSendToServer = scanner.nextLine();

					if (inputToSendToServer .contains("*"))
					{
						
						System.out.printf("Has ingresado el asterisco (*), \t cliente APAGADO\n");		
						
						return;
						
					}
					
					int numToSend = Integer.parseInt(inputToSendToServer);		

					objectOutput.writeObject(numToSend);

					System.out.printf("El número %d ha sido enviado al servidor\n", numToSend);

					System.out.printf("Esperando la respuesta del servidor...\n");

					for (;;) 
					{

						try {

							Object messageFromServer;
							
							if ((messageFromServer = objectInput.readObject()) != null) 
							{
								
								System.out.printf(
										"El cliente ha recibido el mensaje del servidor: %s\n",
										messageFromServer.toString());
								
								if (messageFromServer instanceof Integer) 
								{

									int numIntegerReceived = (Integer) messageFromServer;

									System.out.printf("El número recibido es:\t%d\n", numIntegerReceived);

								} 
								else 
								{
									
									System.out.printf("El objeto recibido no es un entero!\n");
									
								}
								
								break;
							
							}

						} catch (ClassNotFoundException e) {
							
						    System.err.format(
						    		"Error: No se pudo encontrar la clase necesaria. Mensaje de error: %s\n",
						    		e.getMessage());
						    
						    e.printStackTrace();
						    
						}

					}

				} while (true);
				
				

			}
			
			
		} catch (NumberFormatException nfe) {
			
		    System.err.printf("Error: El valor ingresado no es válido!\n");
		    
		    nfe.printStackTrace();
		    
		} catch (UnknownHostException e) {
		
		    System.err.println("Error: No se puede resolver el nombre de host!\n");
		    
		    e.printStackTrace();
		    
		} catch (IOException e) {
			
		    System.err.println("Error: I/O al interactuar con el socket!\n");
		    
		    e.printStackTrace();
		    
		}

	}
}
