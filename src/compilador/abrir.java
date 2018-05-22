/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author yohovani
 */
public class abrir {
	public static ArrayList abrirArchivo(){
		try {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto","txt");
			chooser.addChoosableFileFilter(filtro);	
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showDialog(chooser, null);
			File archivo = chooser.getSelectedFile();
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);

			ArrayList<String> buffer=new ArrayList<>();
			String aux;
			while((aux = br.readLine()) != null){
				buffer.add(aux);
			}
			return buffer;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(abrir.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(abrir.class.getName()).log(Level.SEVERE, null, ex);
		}

		return null;

	}

	//Abrimos la tabla de simbolos
	public static ArrayList<String> abrirTabladeSimbolos(){
		try {
		
		File archivo = new File("Tabla de simbolos.txt");
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> buffer=new ArrayList<>();
		String aux;
		while((aux = br.readLine()) != null){
			buffer.add(aux);
		}
		return buffer;
		} catch (FileNotFoundException ex) {
			Logger.getLogger(abrir.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(abrir.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
		
	}
	
}
