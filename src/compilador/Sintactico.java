/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author yohov
 */
public class Sintactico {
	private String token,aux,tipo;
	private ArrayList<String> cadena;
	private ArrayList<String> tablaIdentificadores;
	private final ArrayList<String> tablaSimbolos;
	private String x1,x2;
	private int contador,sad;
	private boolean auxV;
	private ArrayList<DatosCompilador> datos;
	private ArrayList<Ensamblador> ensamblador,auxEnsamblador;
	private String funcion;

	public ArrayList<Ensamblador> getEnsamblador() {
		return ensamblador;
	}
	
	private DatosCompilador auxDatos;
	
	public Sintactico(ArrayList<String> cadena,ArrayList<String> c) {
		this.cadena = cadena;
		this.tablaIdentificadores = c;
		this.contador = 0;
		tablaSimbolos = abrir.abrirTabladeSimbolos();
		auxV = false;
		this.datos = new ArrayList();
		x1 = null;
		x2 = null;
		this.ensamblador = new ArrayList();
	}
	
	
	
	public void programa(){
		constamte();
		this.auxDatos = null;
		variableFuncion();
		token = Lexico();
		while(!token.equals("$")){
			preposicion();
			token = Lexico();
		}
		if(token.equals("$") || token == null){
			for(int i=0;i<this.datos.size();i++){
				if(datos.get(i).getTipoDato() != null){
					if((datos.get(i).getTipoDato().equals("cadena") && !datos.get(i).getTipo().equals("simli")) || (datos.get(i).getTipoDato().equals("numero") && datos.get(i).getTipo().equals("simli"))){
						System.out.println("Asignacion de datos incorrecta");
						System.exit(0);
					}
				}
			}
			System.out.println("Sintaxis Correcta");
		}else{
			System.out.println("Error se esperaba $");
			System.exit(0);
		}
	}
	
	public void constamte(){
		token = Lexico();
		if(!token.equals("tam") && !token.equals("ikiqat") && !token.equals("simli") && !token.equals("Booleani") && !token.equals("IDENT") && !token.equals(":=") && !token.equals("VALOR") && !token.equals(";"))
			if(token.equals("sabit")){
				this.ensamblador.add(new Ensamblador());
				this.ensamblador.get(ensamblador.size()-1).setCons("equ");
				DatosCompilador aux = new DatosCompilador();
				aux.setConstante(true);
				aux.setFuncion(false);
				token = Lexico();
				tipo();
				
				aux.setTipo(token);
				this.auxDatos = aux;
				token = Lexico();
				
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						if(token.equals("VALOR")){
							auxConstamt();
							if(token.equals(";")){
							}else{
								System.out.println("Error se esperaba ;");
								System.exit(0);
							}
						}else{
							System.out.println("Error se esperaba un VALOR");
							System.exit(0);
						}
					}else{
						System.out.println("Error se esperaba :=");
						System.exit(0);
					}
				}
			}else{
				System.out.println("Error se esperaba sabit");
				System.exit(0);
			}
	}
	
	public void auxConstamt(){
		DatosCompilador aa = new DatosCompilador();
		aa.setConstante(true);
		aa.setFuncion(false);
		aa.setTipo(this.auxDatos.getTipo());
		token = Lexico();
		
		if(!token.equals(";"))
			if(token.equals(":")){
				int au = ensamblador.size();
				this.ensamblador.add(new Ensamblador());
				this.ensamblador.get(au).setCons("equ");
				this.auxDatos = aa;
				token = Lexico();
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						if(token.equals("VALOR")){
							///
							auxConstamt();
						}else{
							System.out.println("Error se esperaba un Valor");
							System.exit(0);
						}
					}else{
						System.out.println("Error se esperaba :=");
						System.exit(0);
					}
				}else{
					System.out.println("Error se esperaba un Identificador");
					System.exit(0);
				}
			}else{
				System.out.println("Error se esperaba un :");
				System.exit(0);
			}
		
	}
	public void auxVariable(){
		token = Lexico();
		if(!token.equals(";")){
		//	token = Lexico();
			int au = ensamblador.size();
			this.ensamblador.add(new Ensamblador());
			if(token.equals(":")){
				this.ensamblador.get(au).setTipo(ensamblador.get(au-1).getTipo());
				String auxT = auxDatos.getTipo();
				
				this.auxDatos = new DatosCompilador(false,false,auxT,"");
				token = Lexico();				
				if(token.equals("IDENT")){
					auxVariable();
				}else{
					System.out.println("Error se esperaba un IDENTIFICADOR");
					System.exit(0);
				}
			}else{
				System.out.println("Error se esperaba :");
				System.exit(0);
			}
		}else{
			this.auxDatos = null;
		}
	}
	public void variableFuncion(){
		this.ensamblador.add(new Ensamblador());
		token = Lexico();
		tipo();
		this.tipo = token;
		this.auxDatos = null;
		this.auxDatos = new DatosCompilador(false,false,token,"");
		token = Lexico();
		this.funcion = this.cadena.get(contador-1);
		
		if(token.equals("IDENT")){
			this.sad = 1;
			auxVF();
//			token = Lexico();
			if(token.equals(";")){
				
			}else{
				System.out.println("Error se esperaba ';'");
				System.exit(0);
			}
		}else{
			System.out.println("Error se esperaba un Identificador");
			System.exit(0);
		}
		
	}
	public void auxVF(){
		auxFuncion();
		if(this.auxV){
			this.sad = 0;
			token = Lexico();
			int au = ensamblador.size();
			this.ensamblador.add(new Ensamblador());
			this.auxDatos = null;
			this.auxDatos =  new DatosCompilador();
			this.auxDatos.setConstante(false);
			this.auxDatos.setTipo(token);
			tipo();
			token = Lexico();
		
			if(token.equals("IDENT")){
				auxVariable();
				this.auxDatos = null;
			}else{
				System.out.println("Se esperaba IDENT");
				System.exit(0);
			}
		}
	}
	
	public void auxFuncion(){
		token = Lexico();
		this.ensamblador.get(ensamblador.size()-1).setFuncion("proc [near]\n");
		this.auxEnsamblador = new ArrayList();
		if(token.equals("[")){
			DatosCompilador aa = new DatosCompilador();
			aa.setConstante(auxDatos.isConstante());
			aa.setFuncion(auxDatos.isFuncion());
			aa.setTipo(auxDatos.getTipo());

			this.datos.get(datos.size()-1).setFuncion(true);
			auxDatos = null;
			parametros();
			token = Lexico();
			preposicion();
			token = Lexico();
			if(!token.equals(";"))
				if(token.equals("end")){
					token = Lexico();
					this.auxDatos = aa;
					
					if(token.equals("IDENT")){
						this.ensamblador.get(ensamblador.size()-1).setInstrucciones(auxEnsamblador);
						auxEnsamblador = null;
						this.auxV=true;
					}else{
						System.out.println("Error se esperaba un identificador de la funcion");
						System.exit(0);
					}
				}else{
					System.out.println("Error se esperaba 'end'");
					System.exit(0);
				}
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
					if(token.equals("mentre")){
							
					}else{
						System.out.println("Error se esperaba mentre");
						System.exit(0);
					}
					
				}else{
					System.out.println("Error se esperaba end");
					System.exit(0);
				}
				break;
			}
			case "pera":{
				this.ensamblador.add(new Ensamblador());
				Random r = new Random();
				this.ensamblador.get((ensamblador.size()-1)).setFor("for"+r.nextInt(100));
				sad = 0;
				auxDatos = new DatosCompilador();
				auxDatos.setConstante(false);
				auxDatos.setFuncion(false);
				auxDatos.setParametro(false);
				auxFor();
				token = Lexico();
				
				if(token.equals("IDENT")){
					
					this.auxDatos = null;
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						if(token.equals("VALOR")){
							condicion();
							auxIncremento();
							token = Lexico();
							this.auxEnsamblador = new ArrayList();
							
							preposicion();
							token = Lexico();
							if(token.equals("end")){
								if(auxEnsamblador != null)
									this.ensamblador.get((ensamblador.size()-1)).setInstrucciones(auxEnsamblador);
								
								token = Lexico();
								if(token.equals("pera")){
								this.auxEnsamblador = null;
								}else{
									System.out.println("Error se esperaba pera");
									System.exit(0);
								}
								
							}else{
								System.out.println("Error se esperaba end");
								System.exit(0);
							}
						}else{
							System.out.println("Error se esperaba un Valor");
							System.exit(0);
						}
					}else{
						System.out.println("Error se esperaba :=");
						System.exit(0);
					}
				}else{
					
					System.out.println("Error se esperaba un Identificador");
					System.exit(0);
				}
				break;
			}
			case "schalter":{
				token = Lexico();
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals("llavors")){
						token = Lexico();
						casos();
						if(token.equals("per")){
							token = Lexico();
							
								if(token.equals("defecte")){
									token = Lexico();
									
										if(token.equals(":")){
											token = Lexico();
											preposicion();
											token = Lexico();
											if(token.equals("end")){
												token = Lexico();

													if(token.equals("schalter")){
														
													}else{
														System.out.println("Error se esperaba schalter");
														System.exit(0);
													}
												
											}else{
												System.out.println("Error se esperaba end");
												System.exit(0);
											}
										}else{
											System.out.println("error se esperaba :");
											System.exit(0);
										}
									
								}else{
									System.out.println("error se esperaba defecte");
									System.exit(0);
								}
							
							
						}else{
							System.out.println("Error se esperaba per");
							System.exit(0);
						}
					}else{
						System.out.println("Error se esperaba llavors");
						System.exit(0);
					}
				}else{
					System.out.println("Error se esperaba un Identificador");
					System.exit(0);
				}
				break;
			}
			case "si":{
				Random r = new Random();
				if(this.auxEnsamblador == null){
					this.ensamblador.add(new Ensamblador());
					this.ensamblador.get((ensamblador.size()-1)).setIf("if"+r.nextInt(100));
				}else{
					if(this.auxEnsamblador.size() > 0)
						this.auxEnsamblador.get((auxEnsamblador.size()-1)).setIf("if"+r.nextInt(100));
					else{
						this.auxEnsamblador.add(new Ensamblador());
						this.auxEnsamblador.get((auxEnsamblador.size()-1)).setIf("if"+r.nextInt(100));
					}
					
				}

				condicion();
				//token = Lexico();
				if(token.equals("llavors")){
					token = Lexico();
					this.auxEnsamblador = new ArrayList();
					preposicion();
					auxIf();
				}else{
					System.out.println("Error se esperaba llavors");
					System.exit(0);
				}
				break;
			}
			case "call":{
				this.ensamblador.add(new Ensamblador());
				this.ensamblador.get(ensamblador.size()-1).setLlamada("call");
				token = Lexico();
				if(token.equals("IDENT")){
					
				}else{
					System.out.println("Se esperaba un identificador");
				}
				break;
			}
			default:{
				
				if(token.equals("IDENT")){
					token = Lexico();
					if(token.equals(":=")){
						token = Lexico();
						if(auxEnsamblador != null){
							if(auxEnsamblador.size() > 0)
								auxEnsamblador.get(auxEnsamblador.size()-1).setOtro(aux);
							else{
								auxEnsamblador.add(new Ensamblador());
								auxEnsamblador.get(auxEnsamblador.size()-1).setOtro(aux);
							}
						}else{
							ensamblador.add(new Ensamblador());
							ensamblador.get(ensamblador.size()-1).setOtro(aux);
						}
						auxPreposicion();
						token= Lexico();
						if(token.equals(";")){
						}else{
							System.out.println("Se esperaba ;");
							System.exit(0);
						}
					}else{
						System.out.println("Error se esperaba :=");
						System.exit(0);
					}
				}else{
					System.out.println("error se esperaba un Identificador");
					System.exit(0);
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
				this.auxDatos.setTipo(token);
			}else{
				if(token.equals("ikiqat")){
					this.auxDatos.setTipo(token);
				}else{
					this.auxDatos = null;
					System.out.println("Se esperaba ikiqat o tam");
					System.exit(0);
				}
			}
	}
	public void auxIncremento(){
		
		if(token.equals("IDENT")){
			token = Lexico();
			if(token.contains("++")){
				
			}else{
				if(token.contains("--")){
       
				}else{
					System.out.println("Se esperaba -- o ++");
					System.exit(0);
				}
            }
		}else{
			System.out.println("Se esperaba ident");
			System.exit(0);
		}
	}
	public void auxIf(){
		token = Lexico();
		if(!token.equals("$") || !token.equals("end") || !token.equals("pauza"))
			switch(token){	
				case "no":{
					if(auxEnsamblador != null){
						ensamblador.get(ensamblador.size()-1).setElse(auxEnsamblador.size());
						this.auxEnsamblador.add(new Ensamblador());
					}
					token = Lexico();
					
					preposicion();
					this.ensamblador.get(ensamblador.size()-1).setInstrucciones(auxEnsamblador);
					auxEnsamblador = null;
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
					token = Lexico();
					preposicion();
					token = Lexico();
					if(token.equals("pauza")){
						token = Lexico();
						if(token.equals(";")){
							token = Lexico();
							casos();
						}else{
							System.out.println("Se esperaba ;");
							System.exit(0);
						}

					}
					else{
						System.out.println("Se esperaba pauza");
						System.exit(0);
					}
				}else{
					System.out.println("Se esperaba un");
					System.exit(0);
				}
			}
			else{
				System.out.println("Se esperaba un valor");
				System.exit(0);
			}
		}
		else{
			System.out.println("Se esperaba case");
			System.exit(0);
		}
	}
}
	public void expresion(){
		//token = Lexico();
		factor();
		token = Lexico();
		auxExpresion();
	}
	public void auxExpresion(){
		
		switch(token){
			case "*":{
				if(this.auxEnsamblador != null){
					auxEnsamblador.get(auxEnsamblador.size()-1).setOperacion("mul");
				}else{
					ensamblador.get(ensamblador.size()-1).setOperacion("mul");
				}
				token = Lexico();
				factor();
				if(!x1.equals(x2) || x1.equals("cadena")){
					System.out.println("Operacion no valida no se pueden multiplicar cadenas");
					System.exit(0);
				}
				x1=null;
				x2=null;
				break;
			}
			case "+":{
				if(this.auxEnsamblador != null){
					auxEnsamblador.get(auxEnsamblador.size()-1).setOperacion("add");
				}else{
					ensamblador.get(ensamblador.size()-1).setOperacion("add");
				}
				token = Lexico();
				factor();
				if(!x1.equals(x2)){
					System.out.println("Operacion no valida entre "+x1+" y "+x2);
					System.exit(0);
				}
				x1=null;
				x2=null;
				break;
			}
			case "-":{
				if(this.auxEnsamblador != null){
					auxEnsamblador.get(auxEnsamblador.size()-1).setOperacion("sub");
				}else{
					ensamblador.get(ensamblador.size()-1).setOperacion("sub");
				}
				
				token = Lexico();
				factor();
				if(!x1.equals(x2) || x1.equals("cadena")){
					System.out.println("Operacion no valida no es posible restar ccadenas");
					System.exit(0);
				}
				x1=null;
				x2=null;
				break;
			}
			case "/":{
				if(this.auxEnsamblador != null){
					auxEnsamblador.get(auxEnsamblador.size()-1).setOperacion("div");
				}else{
					ensamblador.get(ensamblador.size()-1).setOperacion("div");
				}
				token = Lexico();
				factor();
				if(!x1.equals(x2) || x1.equals("cadena")){
					System.out.println("Operacion no valida no es posible la division en cadenas");
					System.exit(0);
				}
				x1=null;
				x2=null;
				break;
			}
			default:
				System.out.println("Error se esperaba '*' o '+' o '-' o '/'");
				System.exit(0);
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
							System.exit(0);
                        }
		}else{
			if(token.equals("IDENT")){
				
			}else{
				if(token.equals("VALOR")){
					
				}else{
					System.out.println("Se esperaba un valor");
					System.exit(0);
				}
			}
		}
	}
	public void condicion(){
		token = Lexico();
		if(this.auxEnsamblador == null)
			this.ensamblador.get((ensamblador.size()-1)).setCondicion(token+" ");
		else{
			if(this.auxEnsamblador.size() > 0){
				this.auxEnsamblador.get((auxEnsamblador.size()-1)).setCondicion(token+" ");
			}else{
				this.auxEnsamblador.add(new Ensamblador());
				this.auxEnsamblador.get((auxEnsamblador.size()-1)).setCondicion(token+" ");
			}

		}
		if(token.equals("IDENT") || token.equals("VALOR")){
		//expresion();
		token = Lexico();
		if(this.auxEnsamblador == null)
			this.ensamblador.get((ensamblador.size()-1)).setCondicion(this.ensamblador.get((ensamblador.size()-1)).getCondicion()+token+" ");
		else{
			if(this.auxEnsamblador.size() > 0){
				this.auxEnsamblador.get((auxEnsamblador.size()-1)).setCondicion(this.ensamblador.get((ensamblador.size()-1)).getCondicion()+token+" ");
			}else{
				this.auxEnsamblador.add(new Ensamblador());
				this.auxEnsamblador.get((auxEnsamblador.size()-1)).setCondicion(this.ensamblador.get((ensamblador.size()-1)).getCondicion()+token+" ");
			}
		}
		auxCondicion();
		token = Lexico();
		//expresion();
		if(token.equals("IDENT") || token.equals("VALOR"))
			token = Lexico();
		}
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
				System.exit(0);
			}
		}
	}
	//dasfas
	public void auxCondicion2(){
		if(!token.equals("IDENT") && !token.equals("VALOR") && !token.equals("[") && !token.equals("llavors")){
			if(token.equals("&&") || token.equals("||")){
				token = Lexico();
				expresion();
				token = Lexico();
				auxCondicion();
				token = Lexico();
				expresion();
				token = Lexico();
				auxCondicion2();
			}else{
				System.out.println("Se esperaba && o ||");
				System.exit(0);
			}
		}
	}
	public void parametros(){
		
		if(token.equals("[")){
		/*	DatosCompilador aa = new DatosCompilador();
			aa.setConstante(false);
			aa.setFuncion(false);
			aa.setParametro(true);
			
			token = Lexico();
			tipo();
			aa.setTipo(token);
			this.auxDatos = aa;
			token = Lexico();
			if(token.equals("IDENT")){
				this.auxDatos = null;
				auxParametros();*/
		token = Lexico();
				if(token.equals("]")){

				}else{
					System.out.println("Se esperaba ]");
					System.exit(0);
				}
		/*	}else{
				System.out.println("Se esperaba Identificador");
				System.exit(0);
			}*/
		}else{
			System.out.println("Se esperaba [");
			System.exit(0);
		}
	}
	public void auxParametros(){
		token = Lexico();
		if(!token.equals("]")){
			if(token.equals(":")){
				DatosCompilador aa = new DatosCompilador();
				aa.setConstante(false);
				aa.setFuncion(false);
				aa.setParametro(true);
				token = Lexico();
				tipo();
				aa.setTipo(token);
				this.auxDatos = aa;
				token = Lexico();
				if(token.equals("IDENT")){
					this.auxDatos = null;
					auxParametros();
				}else{
					System.out.println("Se esperaba identificador");
					System.exit(0);
				}
			}else {
				System.out.println("Se esperaba :");
				System.exit(0);
			}
		}
	}
	public void tipo(){
		switch(token){
			case "tam":{
				if(this.ensamblador.size() > 0){
					this.ensamblador.get(ensamblador.size()-1).setTipo("db");
				}
				break;
			}
			case "ikiqat":{
				if(this.ensamblador.size() > 0){
					this.ensamblador.get(ensamblador.size()-1).setTipo("db");
				}
				break;
			}
			case "simli":{
				if(this.ensamblador.size() > 0){
					this.ensamblador.get(ensamblador.size()-1).setTipo("dw");
				}
				break;
			}
			case "Booleani":{
				if(this.ensamblador.size() > 0){
					this.ensamblador.get(ensamblador.size()-1).setTipo("db");
				}
				break;
			}
			default:{
				System.out.println("Error se esperaba 'tam' o 'Ikiqat' o 'Simli' o 'Booleani'");
				System.exit(0);
			}
		}
	}

	private String Lexico() {
		System.out.println(contador);
		if(contador < this.cadena.size()){
			if(this.cadena.get(contador).equals("")){
				contador++;
			}
			String aux = this.cadena.get(contador);
			
			contador++;
			
			
			
			if(comprobarIDENT(aux)){
				if(this.auxDatos != null){
					this.auxDatos.setName(aux);
					for (DatosCompilador dato : datos) {
						if(dato.getName().equals(auxDatos.getName())){
							System.out.println(auxDatos.getName()+" ya ha sido declarada con anterioridad");
							System.exit(0);
						}
					}
					this.datos.add(auxDatos);
				}else{
					if(!comprobarDeclaracion(aux)){
						System.out.println(aux+" No ha sido declarada");
						System.exit(0);
					}
				}
				if(this.funcion != aux && sad != 1){
					if(this.auxEnsamblador == null || 
						(ensamblador.get(ensamblador.size()-1).getName() == null && ensamblador.get(ensamblador.size()-1).getFor() != null) || 
						(ensamblador.get(ensamblador.size()-1).getName() == null && ensamblador.get(ensamblador.size()-1).getLlamada() != null) ||
						(ensamblador.get(ensamblador.size()-1).getName() == null && ensamblador.get(ensamblador.size()-1).getIf() != null)){
						if(this.ensamblador.get(ensamblador.size()-1).getName() == null){
							this.ensamblador.get(ensamblador.size()-1).setName(aux);
						}else{
							this.ensamblador.get(ensamblador.size()-1).setName2(aux);
						}
					}else{
						if(auxEnsamblador.size() == 0){
								auxEnsamblador.add(new Ensamblador());
						}
						if(this.auxEnsamblador.get(auxEnsamblador.size()-1).getName() == null){
							this.auxEnsamblador.get(auxEnsamblador.size()-1).setName(aux);
						}else{
							this.auxEnsamblador.get(auxEnsamblador.size()-1).setName2(aux);
						}
					}
				}
				
				this.aux = aux;
				return "IDENT";
			}else{
				if(comprobarValor(aux)){
				//	System.out.println(aux+" -> "+comprobarTipo(aux));
					if(auxDatos != null){
						auxDatos.setTipoDato(comprobarTipo(aux));
					}
					if(x1 == null){
						x1 = comprobarTipo(aux);
					}else{
						if(x2 == null){
							x2 = comprobarTipo(aux);
						}
					}
					
					if(this.auxEnsamblador == null || (ensamblador.get(ensamblador.size()-1).getValor() == null && ensamblador.get(ensamblador.size()-1).getFor() != null) ||
						(ensamblador.get(ensamblador.size()-1).getName() == null && ensamblador.get(ensamblador.size()-1).getIf() != null)){
						if(this.ensamblador.get(ensamblador.size()-1).getValor() == null){
							this.ensamblador.get(ensamblador.size()-1).setValor(aux);
						}else{
							if(this.ensamblador.get(ensamblador.size()-1).getOperacion() != null){
								this.ensamblador.get(ensamblador.size()-1).setName2(aux);
							}else{
								this.ensamblador.get(ensamblador.size()-1).setOtro(aux);								
							}

						}
					}else{
						if(auxEnsamblador.size() == 0){
							auxEnsamblador.add(new Ensamblador());
						}
						if(this.auxEnsamblador.get(auxEnsamblador.size()-1).getValor() == null){
							this.auxEnsamblador.get(auxEnsamblador.size()-1).setValor(aux);
						}else{
							if(this.auxEnsamblador.get(auxEnsamblador.size()-1).getOperacion() != null){
								this.auxEnsamblador.get(auxEnsamblador.size()-1).setName2(aux);
							}else{
								this.auxEnsamblador.get(auxEnsamblador.size()-1).setOtro(aux);								
							}
							//this.auxEnsamblador.get(auxEnsamblador.size()-1).setOtro(aux);
						}
					}
//					if(this.ensamblador.size() > 0){
//						//this.ensamblador.get(x).getInstrucciones().get(y).setOtro(aux);
//						int x = ensamblador.size()-1;
//						int y=0;
//						if(ensamblador.get(x).getInstrucciones().size()-1 >= 0)
//							y = ensamblador.get(x).getInstrucciones().size()-1;
//	
//						if(this.ensamblador.get(x).getIf() == null){
//							if(this.ensamblador.get(ensamblador.size()-1).getValor() == null)
//								this.ensamblador.get(ensamblador.size()-1).setValor(aux);
//							else{
//								this.ensamblador.get(ensamblador.size()-1).setName2(aux);
//							}
//						}else{
//							if(ensamblador.get(x).getInstrucciones().size() > 0){
//								if(this.ensamblador.get(x).getName() == null){
//									this.ensamblador.get(x).setName(aux);
//								}else{
//									this.ensamblador.get(x).setName2(aux);
//								}
//								if(this.ensamblador.get(x).getInstrucciones().get(y).getValor() == null)
//									this.ensamblador.get(x).getInstrucciones().get(y).setValor(aux);
//								else{
//									this.ensamblador.get(x).getInstrucciones().get(y).setName2(aux);
//								}
//							}else{
//								ensamblador.get(x).getInstrucciones().add(new Ensamblador());
//								if(this.ensamblador.get(x).getInstrucciones().get(y).getValor() == null)
//									this.ensamblador.get(x).getInstrucciones().get(y).setValor(aux);
//								else{
//									this.ensamblador.get(x).getInstrucciones().get(y).setName2(aux);
//								}
//							}
//						}
//					}
					return "VALOR";
				}
			}
			return aux;
		}
		return null;
	}
	
	private boolean comprobarIDENT(String aux){
		if(this.tablaIdentificadores.contains(aux)){
			return true;
		}
		return false;
	}
	
	private boolean comprobarValor(String aux){
		if(!this.tablaSimbolos.contains(aux)){
			return true;
		}
		return false;
	}

	public ArrayList<DatosCompilador> getDatos() {
		return datos;
	}
	
	private boolean comprobarDeclaracion(String ident){
		for(int i=0;i<this.datos.size();i++){
			if(ident.equals(this.datos.get(i).getName())){
				return true;
			}
		}
		return false;
	}
	
	private String comprobarTipo(String c){
		String aux="";
		int num=0;
		if(c.equals("true") || c.equals("false")){
			aux="booleano";
		}else{
			for(int i=0;i<c.length();i++){
				int x = (int)c.charAt(i);
				if((x >= 48 && x <= 57) || (x == 44)){
					num++;
				}
			}
			if(num == c.length()){
				aux = "numero";
			}else{
				aux = "cadena";
			}
	}
		return aux;
	}

	public void setEnsamblador(ArrayList<Ensamblador> ensamblador) {
		this.ensamblador = ensamblador;
	}
	
}
