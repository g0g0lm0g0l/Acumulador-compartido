package com.actividad.main;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ObjetoCompartido implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private int atributoEntero;
	
	private static Lock lock = new ReentrantLock(false);

	
	public ObjetoCompartido(int atributoEntero) {	
		super();
		this.atributoEntero = atributoEntero;
	}

	
	
	public int sumaNumero(int numSum) {
		
		if (lock.tryLock()) 
		{
			try {
				
				return this.atributoEntero += numSum;
				
			} finally {
				
				lock.unlock();
				
			}
		} 
		else 
		{
			
			return -1; // error
			
		}
		
	}

}
