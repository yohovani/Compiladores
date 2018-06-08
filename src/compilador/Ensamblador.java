/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;

/**
 *
 * @author Yohovani Vargas
 */
public class Ensamblador {
	private String cons;
	private String name;
	private String valor;
	private String tipo;
	private String otro;
	private String operacion;
	private String name2;
	private String If;
	private String condicion;
	private int Else,ElseIf;
	private ArrayList<Ensamblador> instrucciones;

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	
	public int getElse() {
		return Else;
	}

	public void setElse(int Else) {
		this.Else = Else;
	}

	public int getElseIf() {
		return ElseIf;
	}

	public void setElseIf(int ElseIf) {
		this.ElseIf = ElseIf;
	}

	public Ensamblador() {
		instrucciones = new ArrayList();
		this.Else=0;
		this.ElseIf=0;
	}

	public String getIf() {
		return If;
	}

	public void setIf(String If) {
		this.If = If;
	}

	public ArrayList<Ensamblador> getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(ArrayList<Ensamblador> instrucciones) {
		this.instrucciones = instrucciones;
	}

	
	
	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
	
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
	public String getCons() {
		return cons;
	}

	public void setCons(String cons) {
		this.cons = cons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}

	@Override
	public String toString() {
		String aux="";
		if(this.If != null){
			aux+=";"+If+":\n";
			aux+=condicion+"\n";
			aux+="je "+If+"\n";
			for(int i=0;i<this.ElseIf;i++){
				aux+=instrucciones.get(i).toString()+"\n";
			}
			for(int i=ElseIf;i<this.Else;i++){
				aux+=instrucciones.get(i).toString()+"\n";
			}
			
			//aux += this.instrucciones.stream().map((instruccione) -> instruccione.toString()+"\n").reduce(aux, String::concat);
			aux+="end "+If;
			
		}else{
			if(this.operacion != null){
				switch(operacion){
					case "add":
						aux+=";Suma\n";
						break;
					case "sub":
						aux+=";Resta\n";
						break;
					case "mul":
						aux+=";Multiplicación\n";
						break;
					case "div":
						aux+=";División\n";
						break;
				}
				if(this.name != null){
					aux+="mov al,"+name+"\n";
				}else{
					if(this.valor != null){
						aux+="mov al,"+valor+"\n";
					}
				}
				aux+=this.operacion+" al,"+this.name2+"\n";
				aux+="mov al,"+otro;
			}else{
				aux=name;
				if(this.cons != null){
					aux+= " "+cons;
				}

				if(this.valor != null){
					aux+=" "+valor;
				}else{
					aux+=" ?";
				}
			}
		}
		return aux;
	}
	
	
}
