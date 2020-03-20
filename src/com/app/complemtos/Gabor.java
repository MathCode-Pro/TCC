package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.io.IOException;

public class Gabor {

	public static BufferedImage execute(BufferedImage img, double[][] orient, double frequen) throws IOException {

		int[] kernelIndices = createKernelIndices();
		Kernel[] kernel = createKernels(frequen);

		BufferedImage outputImage = null;

		outputImage = FilterUtil.OrientFiltro(img, orient, kernelIndices, kernel);

		return outputImage;
	}

	/*
	 * Cria os índices do kernel necessários para o filtro baseado em orientação.
	 * Esse método simplesmente mapeia a cada 10 graus para um único núcleo (graus
	 * 0-9 ao núcleo 0, graus 10-19 para o kernel 1, etc.). 
	 * 
	 * @return um int[] especificando o # do kernel a ser usado nas orientações de
	 * 0° a 360°.
	 */
	private static int[] createKernelIndices() {
		int[] kernelIndices = new int[360];

		for (int i = 0; i < 360; i++) {
			kernelIndices[i] = (i + 4) / 10;
			kernelIndices[i] = (i >= 176 && i <= 185) ? 0 : kernelIndices[i];
		}
		return kernelIndices;
	}

	/*
	 * Cria os kernels compatíveis com os índices gerados no createKernelIndices().
	 * Por exemplo, kernelIndices [0], ..., kernelIndices [9]; tudo contém o valor
	 * 0, o que indica que o kernel[0] deve ser usado. Kernel[0] contém o kernel do
	 * Gabor orientado a 0 graus.
	 * 
	 * @param frequency a frequência de cada núcleo Gabor
	 * 
	 * @return uma matriz de kernels compatíveis com os índices gerados em
	 * createKernelIndices().
	 */
	private static Kernel[] createKernels(double frequency) {
		int numKernels = 18;
		Kernel[] kernelIndices = new Kernel[numKernels];

		double kx = 0.45;
		double ky = kx;

		int lambda = (int) (1 / frequency);
		double sigmaX = lambda * kx;
		double sigmaY = lambda * ky;
		double sigma = sigmaX;
		int filterSize = (int) (3 * Math.max(sigmaX, sigmaY));

		if (filterSize % 2 == 0)
			filterSize--;

		for (int i = 0; i < numKernels; i++) {
			kernelIndices[i] = KernelUtil.createGaborKernel_Generic(filterSize, sigma, lambda, 90 - i * 10, 3.0, 0);
		}

		return kernelIndices;
	}
}
