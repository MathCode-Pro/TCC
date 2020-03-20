package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.Stack;

public class Orientation {

	//Algoritmo de L. Hong, Y. Wan e A. Jain para detecção da orientação das cristas em um ângulo de até 180°
	public static double[][] calcular(BufferedImage inputImage, int windowSize)
			throws IOException {
		
		Raster inputRaster = inputImage.getData();

		Stack<double[][]> tempStack = FilterUtil.cannyEdge(inputImage);
		double[][] dx = tempStack.get(2);
		double[][] dy = tempStack.get(3);

		double[][] theta = new double[inputRaster.getWidth()][inputRaster.getHeight()];
		int[][] thetaI = new int[inputRaster.getWidth()][inputRaster.getHeight()];

		//calcula-se o ângulo dos pixels utilizando o algorítmo de Canny
		//depois calcula-se a média dessas orientações
		for (int i = 0; i < inputImage.getHeight(); i++) {
			
			int startY = Math.max(0, i - windowSize / 2);
			int endY = Math.min(i + windowSize / 2, inputImage.getHeight() - 1);

			int[] columnTotalVx = new int[inputImage.getWidth()];
			int[] columnTotalVy = new int[inputImage.getWidth()];

			//Soma-se as colunas de cada janela e depois executa-se a soma final da horizontal.
			for (int m = 0; m < inputImage.getWidth(); m++) {
				columnTotalVx[m] = 0;
				columnTotalVy[m] = 0;
				
				for (int k = startY; k <= endY; k++) {
					
					columnTotalVx[m] += (2 * dx[m][k] * dy[m][k]);
					columnTotalVy[m] += ((dx[m][k] * dx[m][k]) - (dy[m][k] * dy[m][k]));
				}
			}

			for (int j = 0; j < inputImage.getWidth(); j++) {
				
				int startX = Math.max(0, j - windowSize / 2);
				int endX = Math.min(j + windowSize / 2, inputImage.getWidth() - 1);

				double vx = 0;
				double vy = 0;

				for (int m = startX; m <= endX; m++) {
					if (m >= 0 && m < inputImage.getWidth()) {
						vx += columnTotalVx[m];
						vy += columnTotalVy[m];
					}
				}

				//remove-se arestas sem resistência
				if ((vx + vy) != 0.0) {
					theta[j][i] = Math.toDegrees((0.5) * Math.atan2(vy, vx));
					theta[j][i] += 45;
					while (theta[j][i] < 0)
						theta[j][i] += 180;
					thetaI[j][i] = (int) theta[j][i];
					if (thetaI[j][i] == 180)
						thetaI[j][i] = 0;
				} else {
					theta[j][i] = -1.0;
					thetaI[j][i] = -1;
				}
			}
		}

		return theta;
	}
}
