package com.app.complemtos;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Histograma {

	static int[] calculaHistograma(BufferedImage img) {
		int[] histograma = new int[256];
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color color = new Color(img.getRGB(x, y));
				int red = color.getRed();
				histograma[red] += 1;
			}
		}
		return histograma;
	}

	public static int[] calculaHistogramaAcumulado(int[] histograma) {
		int[] acumulado = new int[256];
		acumulado[0] = histograma[0];
		for (int i = 1; i < histograma.length; i++) {

			acumulado[i] = histograma[i] + acumulado[i - 1];
		}
		return acumulado;
	}

	private static int menorValor(int[] histograma) {
		for (int i = 0; i < histograma.length; i++) {
			if (histograma[i] != 0) {
				return histograma[i];
			}
		}
		return 0;
	}

	private static int[] calculaMapadeCores(int[] histograma, int pixels) {
		
		int[] mapaDeCores = new int[256];
		int[] acumulado = calculaHistogramaAcumulado(histograma);
		float menor = menorValor(histograma);
		for (int i = 0; i < histograma.length; i++) {
			mapaDeCores[i] = Math.round(((acumulado[i] - menor) / (pixels - menor)) * 255);
		}
		return mapaDeCores;
	}

	public static BufferedImage equalizacao(BufferedImage img) {
		
		int[] histograma = calculaHistograma(img);
		int[] mapaDeCores = calculaMapadeCores(histograma, img.getWidth() * img.getHeight());
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color color = new Color(img.getRGB(x, y));
				int tom = color.getRed();
				int newTom = mapaDeCores[tom];
				Color newColor = new Color(newTom, newTom, newTom);
				out.setRGB(x, y, newColor.getRGB());
			}
		}
		return out;
	}
}
