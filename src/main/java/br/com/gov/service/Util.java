package br.com.gov.service;

import java.util.Random;

public abstract class Util {

	public static String gerarSenha() {

		// Determia as letras que poderão estar presente nas chaves
		String letras = "ABCDEFGHIJ1234567890KLMNOPQRSTUVYWXZ";

		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return (armazenaChaves);

	}
	
}
