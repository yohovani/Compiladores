/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import static compilador.abrir.abrirArchivo;
import java.io.FileWriter;
import java.io.PrintWriter;
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
				int x1 = (int)identificador.charAt(0);
				int x2 = (int)identificador.charAt(identificador.length()-1);
				if(comprobarTablaSimbolos(identificador) && !identificador.isEmpty() && x1 != 39 && x2 != 39){
					agregarIdentificador(identificador);
				}else{
				//	System.out.println(identificador+" se encontro en la tabla de simbolos");
				}
			}
		}
		
		Sintactico s = new Sintactico(asd,tablaIdentificadores);
		s.programa();
		
		System.out.println("Codigo Ensamblador\n");
//		for(int i=0;i<s.getEnsamblador().size();i++){
//			if(s.getEnsamblador().get(i).getFuncion() != null){
//				System.out.println(s.getEnsamblador().get(i).toString());
//			}
//			//System.out.println(s.getEnsamblador().get(i).toString());
//		}
	
	//	ArrayList<Ensamblador> as = s.getEnsamblador();
	String codigo = ".model tiny\n.stack\n.data\n";
	ArrayList<Ensamblador> auxEns = new ArrayList();
		for(int i=0;i<s.getEnsamblador().size();i++){
			if(s.getEnsamblador().get(i).getInstrucciones().size() == 0 && s.getEnsamblador().get(i).getOperacion() == null && s.getEnsamblador().get(i).getFor() == null
				&& s.getEnsamblador().get(i).getFuncion() == null && s.getEnsamblador().get(i).getIf() == null  && s.getEnsamblador().get(i).getLlamada() == null){
				codigo+=s.getEnsamblador().get(i).toString()+"\n";
				auxEns.add(s.getEnsamblador().get(i));
		//		as.remove(i);
			}
		}
		codigo+=".code\n.startup\n";
		
		for(int i=0;i<s.getEnsamblador().size();i++){
			if(s.getEnsamblador().get(i).getFuncion() == null){
				if(!auxEns.contains(s.getEnsamblador().get(i))){
					codigo+=s.getEnsamblador().get(i).toString()+"\n";
					auxEns.add(s.getEnsamblador().get(i));
				}
		//		as.remove(i);
			}
		}
		codigo+=".exit\n";
		for(int i=0;i<s.getEnsamblador().size();i++){
			if(s.getEnsamblador().get(i).getFuncion() != null){
				if(!auxEns.contains(s.getEnsamblador().get(i))){
					codigo+=s.getEnsamblador().get(i).toString()+"\n";
					auxEns.add(s.getEnsamblador().get(i));
				}
		//		as.remove(i);
			}
		}
		codigo+=".end\n";
		System.out.println(codigo);
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("codigo.asm");
            pw = new PrintWriter(fichero);

                pw.println(codigo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		
	//	s.setEnsamblador(as);
//		System.out.println(".code \n.startup");
//		for(int i=0;i<s.getEnsamblador().size();i++){
//			if(s.getEnsamblador().get(i).getFuncion() == null){
//				System.out.println(s.getEnsamblador().get(i).toString());
//				s.getEnsamblador().remove(i);
//			}
//		}
//		System.out.println(".exit");
//		for(int i=0;i<s.getEnsamblador().size();i++){
//			System.out.println(s.getEnsamblador().get(i).toString());
//		}
//		System.out.println(".end");

	}
	
	public static String comprobar(String aux){
		int i=0;
		String aux2 = "";
		while(i < aux.length()){
			int x = (int) aux.charAt(i);
			if((x >= 97 && x <= 122) || (x >= 65 && x <= 90) || x == 39){
				aux2+=aux.charAt(i);
			}else{
				if(x == 38 || x == 124){
					aux2+=aux.charAt(i);
				}
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
							if((i+1) < aux.length()){
								int z = aux.charAt(i+1);
								if(z == 43)
									return "++";
							}
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