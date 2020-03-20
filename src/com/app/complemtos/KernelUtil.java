package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;

public class KernelUtil {

	/*
	 * Cria um kernel Gabor gen�rico.
	 *
	 * @param width - A largura do kernel.
	 * 
	 * @param sigma - A varia��o da fun��o gaussiana.
	 * 
	 * @param lambda - O comprimento de onda da fun��o sinusoidal.
	 * 
	 * @param theta - O par�metro de orienta��o.
	 * 
	 * @param gama - A propor��o espacial (elipticidade).
	 * 
	 * @param phi - O deslocamento de fase.
	 * 
	 * @return O kernel correspondente aos par�metros especificados.
	 */

	public static Kernel createGaborKernel_Generic(int width, double sigma, int lambda, double theta, double gamma,
			double phi) {

		BufferedImage biKernel = new BufferedImage(width, width, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster wr = biKernel.getRaster();

		float G[] = new float[width * width + 1];

		width--;
		width /= 2;
		theta = Math.toRadians(theta);
		phi = Math.toRadians(phi);

		for (int y = -(width); y <= (width); y++) {
			for (int x = -(width); x <= (width); x++) {

				double xPrime = x * Math.cos(theta) + y * Math.sin(theta);
				double yPrime = -x * Math.sin(theta) + y * Math.cos(theta);
				G[(y + width) * (width * 2 + 1) + (x + width)] = (float) (Math
						.exp((-(((xPrime * xPrime) + (gamma * gamma * yPrime * yPrime)) / (2 * sigma * sigma))))
						* Math.cos(2 * Math.PI * (xPrime / lambda) + phi));
				wr.setSample(y + width, x + width, 0,
						(int) ((G[(y + width) * (width * 2 + 1) + (x + width)] + 1) * 127));
			}
		}

		Kernel gaborKernel = new Kernel((width * 2 + 1), (width * 2 + 1), G);
		return gaborKernel;
	}
}
