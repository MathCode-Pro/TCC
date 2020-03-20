package com.app.complemtos;

public class OtsuThresholder {
	private int histData[];
	private int maxLevelValue;
	private int threshold;

	public OtsuThresholder() {

		histData = new int[256];
	}

	public int[] getHistData() {
		return histData;
	}

	public int getMaxLevelValue() {
		return maxLevelValue;
	}

	public int getThreshold() {
		return threshold;
	}

	public int Limiar(byte[] srcData, byte[] monoData) {
		int ptr;

		ptr = 0;
		while (ptr < histData.length)
			histData[ptr++] = 0;

		// Calcule o histograma e encontre o nível com o valor máximo
		ptr = 0;
		maxLevelValue = 0;
		while (ptr < srcData.length) {
			int h = 0xFF & srcData[ptr];
			histData[h]++;
			if (histData[h] > maxLevelValue)
				maxLevelValue = histData[h];
			ptr++;
		}

		int total = srcData.length;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Peso do fundo da imagem
			if (wB == 0)
				continue;

			wF = total - wB; // Peso do primeiro plano da imagem
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Média do fundo
			float mF = (sum - sumB) / wF; // Média do primeiro plano

			// Calcular variação de classe
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Verificar se o novo valor máximo foi encontrado
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		// Calcular limiar para criar uma imagem binária
		if (monoData != null) {
			ptr = 0;
			while (ptr < srcData.length) {
				monoData[ptr] = ((0xFF & srcData[ptr]) >= threshold) ? (byte) 255 : 0;
				ptr++;
			}
		}

		return threshold;
	}
}
