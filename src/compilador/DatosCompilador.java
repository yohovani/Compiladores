/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Yohovani Vargas
 */
public class DatosCompilador {
	private boolean constante;
	private boolean funcion;
	private boolean parametro;
	private String tipo;
	private String name;
	private String tipoDato;

	public DatosCompilador() {
	}

	public DatosCompilador(boolean constante, boolean funcion, String tipo, String name) {
		this.constante = constante;
		this.funcion = funcion;
		this.tipo = tipo;
		this.name = name;
	}

	public DatosCompilador(boolean constante, boolean funcion, boolean parametro, String tipo, String name) {
		this.constante = constante;
		this.funcion = funcion;
		this.parametro = parametro;
		this.tipo = tipo;
		this.name = name;
	}

	public boolean isFuncion() {
		return funcion;
	}

	public void setFuncion(boolean funcion) {
		this.funcion = funcion;
	}

	public boolean isConstante() {
		return constante;
	}

	public void setConstante(boolean constante) {
		this.constante = constante;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isParametro() {
		return parametro;
	}

	public void setParametro(boolean parametro) {
		this.parametro = parametro;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	@Override
	public String toString() {
		return "DatosCompilador{" + "constante=" + constante + ", funcion=" + funcion + ", parametro=" + parametro + ", tipo=" + tipo + ", name=" + name + ", tipoDato=" + tipoDato + '}';
	}
	
	
}
