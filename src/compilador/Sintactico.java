/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;

/**
 *
 * @author yohov
 */
public class Sintactico {
	private String token,aux;
	private ArrayList<String> cadena;
	private ArrayList<String> tablaIdentificadores;
	private final ArrayList<String> tablaSimbolos;
	private int contador,auxC=0;
	
	public Sintactico(ArrayList<String> cadena,ArrayList<String> c) {
		this.cadena = cadena;
		this.tablaIdentificadores = c;
		this.contador = 0;
		aux="";
		tablaSimbolos = abrir.abrirTabladeSimbolos();
	}
	
	
	
	public void programa(){
		constamte();
		variableFuncion();
		System.out.println("Termino Variables y funciones");
		token = Lexico();
		preposicion();
		
		if(token.equals("$")){
			System.out.println("Sintaxis Correcta");
		}else{
			System.out.println("Error se esperaba $");
		}
System.out.println(aux);
	}
	
	public void constamte(){
		token = Lexico();
		if(!token.equals("tam") && !token.equals("ikiqat") && !token.equals("simli") && !token.equals("Booleani") && !token.equals("IDENT") && !token.equals(":=") && !token.equals("VALOR") && !token.equals(";"))
			if(token.equals("sabit")){
				this.aux+=token;
				token = Lexico();
				tipo();
				token = Lexico();
				if(token.equals("IDENT")){
					this.aux+=token;
					token = Lexico();
					if(token.equals(":=")){
						this.aux+=token;
						token = Lexico();
						if(token.equals("VALOR")){
							this.aux+=token;
							auxConstamt();
							if(token.equals(";")){
								this.aux+=token;
							}else{
								System.out.println("Error se esperaba ;");
							}
						}else{
							System.out.println("Error se esperaba un VALOR");
						}
					}else{
						System.out.println("Error se esperaba :=");
					}
				}
			}else{
				System.out.println("Error se esperaba sabit");
			}
	}
	
	public void auxConstamt(){
		token = Lexico();
		if(!token.equals(";"))
			if(token.equals(":")){
				this.aux+=token;
				token = Lexico();
				if(token.equals("IDENT")){
					this.aux+=token;
					token = Lexico();
					if(token.equals(":=")){
						this.aux+=token;
						token = Lexico();
						if(token.equals("VALOR")){
							this.aux+=token;
							///
							auxConstamt();
						}else{
							System.out.println("Error se esperaba un Valor");
						}
					}else{
						System.out.println("Error se esperaba :=");
					}
				}else{
					System.out.println("Error se esperaba un Identificador");
				}
			}else{
				System.out.println("Error se esperaba un :");
			}
		
	}
	public void auxVariable(){
		token = Lexico();
		if(!token.equals(";"))
		//	token = Lexico();
			if(token.equals(":")){
				this.aux+=token;
				token = Lexico();
				if(token.equals("IDENT")){
					this.aux+=token;
					auxVariable();
				}else{
					System.out.println("Error se esperaba un IDENTIFICADOR");
				}
			}else{
				System.out.println("Error se esperaba :");
			}
	}
	public void variableFuncion(){
		token = Lexico();
		tipo();
		this.aux+=token;
		token = Lexico();
		if(token.equals("IDENT")){
			this.aux+=token;
			auxVF();
//			token = Lexico();
			if(token.equals(";")){
				
			}else{
				System.out.println("Error se esperaba ';'");
			}
		}else{
			System.out.println("Error se esperaba un Identificador");
		}
		
	}
	public void auxVF(){
		auxFuncion();
		token = Lexico();
		tipo();
		this.aux+=token;
		token = Lexico();
		if(token.equals("IDENT")){
		auxVariable();
		}else{
			System.out.println("sadasdas");
		}
	}
	
	public void auxFuncion(){
		token = Lexico();
		parametros();
		preposicion();
		token = Lexico();
		if(!token.equals(";"))
			if(token.equals("end")){
				token = Lexico();
				if(token.equals("IDENT")){

				}else{
					System.out.println("Error se esperaba un identificador de la funcion");
				}
			}else{
				System.out.println("Error se esperaba 'end'");
			}
	}
	public void preposicion(){
		switch(token){
			case "mentre":{
				condicion();
				preposicion();
				token = Lexico();
				if(token.equals("end")){
					token = Lexico();
					if(token.equals(" ")){
						token = Lexico();
						if(token.equals("mentre")){
							
						}else{
							System.out.println("Error se esperaba mentre");
						}
					}else{
						System.out.println("Error se esperaba ' '");
					}
				}else{
					System.out.println("Error se esperaba end");
				}
				break;
			}
			case "pera":{
				auxFor();
				token = Lexico();
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						if(token.equals("VALOR")){
							condicion();
							auxIncremento();
							preposicion();
							token = Lexico();
							if(token.equals("end")){
								token = Lexico();
								if(token.equals(" ")){
									token = Lexico();
									if(token.equals("pera")){
								
									}else{
										System.out.println("Error se esperaba pera");
									}
								}else{
									System.out.println("Error se esperaba ' '");
								}
							}else{
								System.out.println("Error se esperaba end");
							}
						}else{
							System.out.println("Error se esperaba un Valor");
						}
					}else{
						System.out.println("Error se esperaba :=");
					}
				}else{
					System.out.println("Error se esperaba un Identificador");
				}
				break;
			}
			case "schalter":{
				token = Lexico();
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals("llavors")){
						casos();
						token = Lexico();
						if(token.equals("per")){
							token = Lexico();
							if(token.equals(" ")){
								token = Lexico();
								if(token.equals("defecte")){
									token = Lexico();
									if(token.equals(" ")){
										token = Lexico();
										if(token.equals(":")){
											preposicion();
											token = Lexico();
											if(token.equals("end")){
												token = Lexico();
												if(token.equals(" ")){
													if(token.equals("schalter")){
														
													}else{
														System.out.println("Error se esperaba schalter");
													}
												}else{
													System.out.println("Error se esperaba ' '");
												}
											}else{
												System.out.println("Error se esperaba end");
											}
										}else{
											System.out.println("error se esperaba :");
										}
									}else{
										System.out.println("error se esperaba ' '");
									}
								}else{
									System.out.println("error se esperaba defecte");
								}
							}else{
								System.out.println("error se esperaba ' '");
							}
							
						}else{
							System.out.println("Error se esperaba per");
						}
					}else{
						System.out.println("Error se esperaba llavors");
					}
				}else{
					System.out.println("Error se esperaba un Identificador");
				}
				break;
			}
			case "si":{
				condicion();
				token = Lexico();
				if(token.equals("llavors")){
					preposicion();
					auxIf();
				}else{
					System.out.println("Error se esperaba llavors");
				}
				break;
			}
			default:{
				token = Lexico();
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						auxPreposicion();
						token= Lexico();
						if(token.equals(";")){
						}else{
							System.out.println("Se esperaba ;");
						}
					}else{
						System.out.println("Error se esperaba :=");
					}
				}else{
					System.out.println("error se esperaba un Identificador");
				}
			}
		}
	}
	
	public void auxPreposicion(){
		
		switch(token){
			case "IDENT":{
				expresion();
				break;
                                
			}
			case "VALOR": {
				expresion();
				break;
			}
			case "[":{
				parametros();
				break;
			}
		}	
	}
	
	public void auxFor(){
		token = Lexico();
		if(!token.equals("IDENT"))
			if(token.equals("tam")){
				if(token.equals("ikiqat")){

				}else{
					System.out.println("Se esperaba ikiqat");
				}
			}else{
				System.out.println("Se esperaba tam");
			}
	}
	public void auxIncremento(){
		
		if(token.equals("IDENT")){
			token = Lexico();
			if(token.contains("++")){
				if(token.contains("--")){
       
				}else{
					System.out.println("Se esperaba --");
				}
			}else{
				System.out.println("Se esperaba ++");
            }
		}else{
			System.out.println("Se esperaba ident");
		}
	}
	public void auxIf(){
		token = Lexico();
		if(!token.equals("$") || !token.equals("end") || !token.equals("pauza"))
			switch(token){
				case "si no":{
					preposicion();
					auxIf();
					break;
				}
				case "no":{
					preposicion();
					break;
				}
			}
	}
	public void casos(){
	if(!token.equals("per")){
		if(token.equals("case")){
			token = Lexico();
			if(token.equals("VALOR")){
				token = Lexico();
				if(token.equals(":")){
					preposicion();
					token = Lexico();
					if(token.equals("pauza")){
						if(token.equals(";")){
							casos();
						}else{
							System.out.println("Se esperaba ;");
						}

					}
					else{
						System.out.println("Se esperaba pauza");
					}
				}else{
					System.out.println("Se esperaba un");
				}
			}
			else{
				System.out.println("Se esperaba un valor");
			}
		}
		else{
			System.out.println("Se esperaba case");
		}
	}
}
	public void expresion(){
		token = Lexico();
		factor();
		token = Lexico();
		auxExpresion();
	}
	public void auxExpresion(){
		
		switch(token){
			case "*":{
				token = Lexico();
				factor();
				break;
			}
			case "+":{
				token = Lexico();
				factor();
				break;
			}
			case "-":{
				token = Lexico();
				factor();
				break;
			}
			case "/":{
				token = Lexico();
				factor();
				break;
			}
			default:
				System.out.println("Error se esperaba '*' o '+' o '-' o '/'");
		}
	}
	public void factor(){
		
		if(token.equals("[")){
			expresion();
			token = Lexico();
			if(token.equals("]")){
				
			}
                        else{
                            System.out.println("Se esperaba ]");
                        }
		}else{
			if(token.equals("IDENT")){
				
			}else{
				if(token.equals("VALOR")){
					
				}else{
					System.out.println("Se esperaba un valor");
				}
			}
		}
	}
	public void condicion(){
		expresion();
		auxCondicion();
		expresion();
		auxCondicion2();
	}
	public void auxCondicion(){
		
		switch(token){
			case "=":{
				break;
			}
			case "|=":{
				break;
			}
			case "<=":{
				break;
			}
			case ">=":{
				break;
			}
			case "<":{
				break;
			}
			case ">":{
				break;
			}
			default :{
				System.out.println("Error se esperaba '=' o '|=' o '<=' o '>=' o '<'  o '>'");
			}
		}
	}
	public void auxCondicion2(){
		if(!token.equals("IDENT") || !token.equals("VALOR") || !token.equals("[")){
			if(token.equals("&&")){
				if(token.equals("||")){
					expresion();
				auxCondicion();
				expresion();
				auxCondicion2();
				}else{
					System.out.println("Se esperaba ||");
				}
			}else{
				System.out.println("Se esperaba &&");
			}
		}
	}
	public void parametros(){
		
		if(token.equals("[")){
			token = Lexico();
			tipo();
			token = Lexico();
			if(token.equals("IDENT")){
				auxParametros();
				if(token.equals("]")){

				}else{
					System.out.println("Se esperaba [");
				}
			}else{
				System.out.println("Se esperaba Identificador");
			}
		}else{
			System.out.println("Se esperaba ]");
		}
	}
	public void auxParametros(){
		token = Lexico();
		if(!token.equals("]")){
			if(token.equals(":")){
				token = Lexico();
				tipo();
				token = Lexico();
				if(token.equals("IDENT")){
					auxParametros();
				}else{
					System.out.println("Se esperaba identificador");
				}
			}else {
				System.out.println("Se esperaba :");
			}
		}
	}
	public void tipo(){
		switch(token){
			case "tam":{
				this.aux+=token;
				break;
			}
			case "ikiqat":{
				this.aux+=token;
				break;
			}
			case "simli":{
				this.aux+=token;
				break;
			}
			case "Booleani":{
				this.aux+=token;
				break;
			}
			default:{
				System.out.println("Error se esperaba 'tam' o 'Ikiqat' o 'Simli' o 'Booleani'");
			}
		}
	}

	private String Lexico() {
		System.out.println(contador+"");
		if(contador < this.cadena.size()){
			if(this.cadena.get(contador).equals("")){
				contador++;
			}
			String aux = this.cadena.get(contador);
			
			
			
			if(aux.equals(":")){
				int x=0;
			}
			if(aux.equals(":=") || aux.equals("+")  || aux.equals("-")  || aux.equals("*")  || aux.equals("/")){
				this.auxC=(contador);
			}
			contador++;
			
			
			if(comprobarIDENT(aux)){
				return "IDENT";
			}else{
				if(auxC > 0)
					if((comprobarIDENT(this.cadena.get(auxC-1)) || this.cadena.get(auxC).equals("+")  || this.cadena.get(auxC).equals("-")  || this.cadena.get(auxC).equals("*")  || this.cadena.get(auxC).equals("/") || this.cadena.get(auxC).equals(":=")) && this.auxC > 0 && auxC != (contador-1)){
						auxC=0;
						return "VALOR";
					}
			}
			return aux;
		}
		return null;
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private boolean comprobarIDENT(String aux){
		if(this.tablaIdentificadores.contains(aux)){
			return true;
		}
		return false;
	}
	
	private boolean comprobarValor(String aux){
		if(this.tablaSimbolos.contains(aux)){
			return true;
		}
		return false;
	}
}
