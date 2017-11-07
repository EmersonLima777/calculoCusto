package model_CalculoPrecoCusto;

import java.util.Scanner;

import javax.swing.JFileChooser;

public class importacaoNfe {

	static JFileChooser importacao = new JFileChooser();
	static StringBuilder sb = new StringBuilder();
	static String nota = new String();
	
	//Metodo para abrir diretorio no sistema operacional
	public static String importar() throws Exception{

		if(importacao.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			nota = importacao.getSelectedFile().getAbsolutePath();

			Scanner input = new Scanner(nota);

			while (input.hasNext()){
				sb.append(input.nextLine());
				sb.append("\n");
			}

			input.close();

		}
		else {
			sb.append("Nenhum arquivo selecionado");
		}return nota;

	}

}
