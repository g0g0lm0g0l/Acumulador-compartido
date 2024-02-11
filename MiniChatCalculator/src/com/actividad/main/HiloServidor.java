package com.actividad.main;

import java.io.*;
import java.net.*;

public class HiloServidor implements Runnable {

	private Socket socket;
	private ObjetoCompartido compartido;

	public HiloServidor(Socket socket, ObjetoCompartido compartido) 
	{
		super();
		this.socket = socket;
		this.compartido = compartido;
	}

	@Override
	public void run() 
	{
		
		try (	ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
				ObjectInput objectInput = new ObjectInputStream(socket.getInputStream())
			) {
			

			Object receivedMessage = null;
				
				while ((receivedMessage = objectInput.readObject()) != null) {
					
					System.out.printf("El servidor ha recibido el mensaje del: %s\n",
							socket.getInetAddress().getHostAddress());
					

					if (receivedMessage.equals("*")) 
					{
						
						System.out.printf("El mensaje consiste de un asterisco (*), servidor sesión cerrada");
						
						break;
						
					}
					

					if (receivedMessage instanceof Integer ) 
					{
						
						int receivedIntNumber = (Integer) receivedMessage;
						
						System.out.printf("El mensaje es un número entero: %d\n", receivedIntNumber);

						int numberResult = compartido.sumaNumero(receivedIntNumber);

						if (numberResult == -1) 
						{
							
							System.err.printf("Modificar el objeto no ha sido posible\n");
							
						}

						System.out.printf("Es objeto modificado:\t%d\n", numberResult);

						objectOutput.writeObject(numberResult);
						
						System.out.printf("El objeto ha sido enviado al cliente\n");

					} 
					else 
					{
						
						System.err.printf("El mensaje recibido no es un tipo válido!");
						
					}

				} 
		

		} catch (EOFException e) {
			
			System.out.printf("Cerrando sesión...\n");
			Thread.currentThread().interrupt();
		
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		
		} finally {
			
			try {
				
				if (!socket.isClosed()) 
				{
					
					socket.close();		
					
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
		}

	}

}
