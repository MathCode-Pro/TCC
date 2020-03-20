package com.app.complemtos;

import java.util.LinkedList;

public class MathUtil {

	/*
	 * Encontre os picos de frequência em um sinal (como representado por uma matriz
	 * inteira).
	 *
	 * @param projection - O sinal a ser analisado quanto aos picos de frequência.
	 * 
	 * @param dilation - Uma versão dilatada da projeção: picos de frequência são
	 * detectados onde a matriz dilatada é igual à matriz projetada.
	 * 
	 * @param mean - A média da projeção: se o pico de frequência encontrado não for
	 * acima da média, ele é descartado.
	 * 
	 * @return Um LinkedList de valores inteiros que representam a localização dos
	 * picos de frequência na projeção.
	 */
	public static LinkedList<Integer> findPeaks(int[] projection, int[] dilation, double mean) {
		LinkedList<Integer> ll = new LinkedList<Integer>();

		if (dilation.length != projection.length)
			return null; // change to throw error?

		for (int i = 0; i < dilation.length; i++) {
			if (dilation[i] == projection[i] && projection[i] > mean) {
				ll.add(i);
			}
		}
		return ll;
	}

	/*
	 * Define o elemento da matriz correspondente na matriz de saída para o máximo
	 * de uma janela ao redor de um elemento correspondente na matriz de entrada.
	 *
	 * @param array - A matriz de dados a serem dilatados.
	 * 
	 * @param windowSize - A janela em torno de cada célula para calcular o valor
	 * máximo.
	 * 
	 * @return Uma matriz 1D int contendo os dados dilatados.
	 */
	public static int[] dilate(int[] array, int windowSize) {
		final boolean DEBUG = false;

		int[] dilation = new int[array.length];

		int half = (windowSize - 1) / 2;

		if (DEBUG)
			System.out.println("DILATION:");

		for (int i = 0; i < dilation.length; i++) {
			int max = 0;
			for (int k = -half + i; k <= half + i; k++) {
				if (k >= 0 && k < dilation.length) {
					if (array[k] > max)
						max = array[k];
				}
			}
			dilation[i] = max;
			if (DEBUG)
				System.out.print(dilation[i] + ", ");

		}
		if (DEBUG)
			System.out.println();
		return dilation;
	}

	/*
	 * Calcula a média de uma matriz int 1D.
	 *
	 * @param array - A matriz 1D int de dados a serem calculados em média.
	 * 
	 * @return Valor double que contém a média dos dados de entrada.
	 */
	public static double avgIntArray(int[] array) {
		double mean = 0;

		for (int i = 0; i < array.length; i++) {
			mean += array[i];
		}
		mean /= array.length;

		return mean;
	}

	/*
	 * Calcula o máximo de uma matriz int 2D.
	 *
	 * @param d - Uma matriz int 2D.
	 * 
	 * @return O máximo da matriz de entrada.
	 */
	public static double max(double[][] d) {
		int width = d.length;
		int height = d[0].length;

		double max = -0x7FFFFFFF;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (d[j][i] > max)
					max = d[j][i];
			}
		}
		return max;
	}

	/*
	 * Calcula o mínimo de uma matriz dupla 2D.
	 *
	 * @param d - Uma matriz dupla 2D.
	 * 
	 * @return O min da matriz de entrada.
	 */
	public static double min(double[][] d) {
		int width = d.length;
		int height = d[0].length;

		double min = 0x7FFFFFFF;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (d[j][i] < min)
					min = d[j][i];
			}
		}
		return min;
	}
}
