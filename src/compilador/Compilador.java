/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import static compilador.abrir.abrirArchivo;
import java.util.ArrayList;

/**
 *
 * @author yohov
 */
public class Compilador {

	/**
	 * @param args the command line arguments
	 */

	public static final ArrayList<String> tablaSimbolos = abrir.abrirTabladeSimbolos();
	public static ArrayList<String> tablaIdentificadores = new ArrayList();
	
	
	public static void main(String args[]){
		ArrayList<String> aux = abrirArchivo();
		
		ArrayList<String> asd = new ArrayList();
		for(int i=0;i<aux.size();i++){
			String[] linea = aux.get(i).split(" ");
			for(int j=0;j<linea.length;j++){
				String identificador = comprobar(linea[j]);
				asd.add(identificador);
				if(comprobarTablaSimbolos(identificador) && !identificador.isEmpty()){
					agregarIdentificador(identificador);
				}else{
				//	System.out.println(identificador+" se encontro en la tabla de simbolos");
				}
			}
		}
		
		Sintactico s = new Sintactico(asd,tablaIdentificadores);
		s.programa();
//		System.out.println("\nTabla de simbolos");
//			for(String au : tablaIdentificadores){
//				System.out.println(au);
//			}
//			int y=0;
	}
	
	public static String comprobar(String aux){
		int i=0;
		String aux2 = "";
		while(i < aux.length()){
			int x = (int) aux.charAt(i);
			if((x >= 97 && x <= 122) || (x >= 65 && x <= 90)){
				aux2+=aux.charAt(i);
			}else{
				if(((x >= 48 && x <= 57) || (x == 95))){
					int d=0;
					if(x == 95)
						aux2+='_';
					else
						aux2+=aux.charAt(i);
				}else{
					//Verificamos si el caracter no se encuentra dentro de la tabla de simbolos
					switch(x){
						case 58:{
							if((i+1) < aux.length()){
								int z = aux.charAt(i+1);
								if(z == 61){
									return ":=";
								}
							}
							
							return ":";
						}
						case 61:{						
							return "=";
						}
						case 62:{
							if((i+1) < aux.length()){
								int z = aux.charAt(i+1);
								if(z == 61){
									return ">=";
								}
							}
							return ">";
						}
						case 60:{
							if((i+1) < aux.length()){
								int z = aux.charAt(i+1);
								if(z == 61){
									return "<=";
								}
							}
							return "<";
						}
						case 43:{
							return "+";
						}
						case 45:{
							return "-";
						}
						case 42:{
							return "*";
						}
						case 47:{
							return "/";
						}
						case 59:{
							return ";";
						}
						case 91:{
							return "[";
						}
						case 93:{
							return "]";
						}
					}
				}
			}
			if(x == 36)
				aux2+=aux.charAt(i);
			i++;
		}
		return aux2;
	}
	
	public static boolean comprobarTablaSimbolos(String palabra){
		for(String aux : tablaSimbolos){
			if(aux.equals(palabra)){
				return false;
			}
		}
		if(palabra.length() > 1){
			
			return true;
		}
			return false;
	}
	
	public static void agregarIdentificador(String aux){
		tablaIdentificadores.add(aux);
	}

}