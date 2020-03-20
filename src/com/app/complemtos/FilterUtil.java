package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Stack;

import ibg.image.filter.GaussianSmoother;

public class FilterUtil {

	public static BufferedImage OrientFiltro(BufferedImage img, double[][] orient,
			int[] kernelIndices, Kernel[] kernel) {

		int stepX = 2;
		int stepY = 2;

		Raster inputRaster = img.getData();

		BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(),
				img.getType());

		double[][] outputData = new double[img.getWidth()][img.getHeight()];

		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {

				int currentOrientation = (int) orient[j][i];
				
				if (currentOrientation != -1) {
					
					int kernelIndex = kernelIndices[currentOrientation];
					Kernel currentKernel = kernel[kernelIndex];
					float[] kernelData = currentKernel.getKernelData(null);

					int filterSizeX = currentKernel.getWidth();
					int filterSizeY = currentKernel.getHeight();

					float sum = 0;

					int startY = Math.max(0, i - filterSizeY / 2);
					int endY = Math.min(i + filterSizeY / 2, img.getHeight() - 1);
					int startX = Math.max(0, j - filterSizeY / 2);
					int endX = Math.min(j + filterSizeY / 2, img.getWidth() - 1);

					for (int y = startY; y <= endY; y += stepY) {
						for (int x = startX; x <= endX; x += stepX) {
							sum += kernelData[(y - i + filterSizeY / 2) * filterSizeX + (x - j + filterSizeX / 2)]
									* (inputRaster.getSample(x, y, 0));
						}
					}
					outputData[j][i] = sum;
				}
			}
		}

		outputImage = ImageUtil.loadFrom2dDouble(outputData, BufferedImage.TYPE_3BYTE_BGR, true);
		return outputImage;
	}

	
	public static Stack<double[][]> cannyEdge(BufferedImage inputImage) throws IOException {
		
		Raster inputRaster = inputImage.getData();
		double[][] dx = new double[inputRaster.getWidth()][inputRaster.getHeight()];
		double[][] dy = new double[inputRaster.getWidth()][inputRaster.getHeight()];
		double[][] amp = new double[inputRaster.getWidth()][inputRaster.getHeight()];
		double[][] ori = new double[inputRaster.getWidth()][inputRaster.getHeight()];

		for (int i = 1; i < inputImage.getHeight() - 1; i++) {
			for (int j = 1; j < inputImage.getWidth() - 1; j++) {
				double deltaNS = (inputRaster.getSample(j, i + 1, 0) - inputRaster.getSample(j, i - 1, 0));
				double deltaEW = (inputRaster.getSample(j + 1, i, 0) - inputRaster.getSample(j - 1, i, 0));
				double deltaNWSE = (inputRaster.getSample(j + 1, i + 1, 0) - inputRaster.getSample(j - 1, i - 1, 0));
				double deltaNESW = (inputRaster.getSample(j + 1, i - 1, 0) - inputRaster.getSample(j - 1, i + 1, 0));

				dx[j][i] = deltaEW + ((deltaNWSE + deltaNESW) / 2);
				dy[j][i] = deltaNS + ((deltaNWSE - deltaNESW) / 2);

				amp[j][i] = Math.sqrt(dx[j][i] * dx[j][i] + dy[j][i] * dy[j][i]);
				ori[j][i] = Math.atan2(-dy[j][i], dx[j][i]);
			}
		}
		
		Stack<double[][]> tempStack = new Stack<double[][]>();
		tempStack.push(amp);
		tempStack.push(ori);
		tempStack.push(dx);
		tempStack.push(dy);

		return tempStack;
	}

	//Equalizar a imagem por janelas de tamanho de "x" pixels utilizando a técnica de pixels vizinhos.
	//Autaliza-se a imagem pelo contraste dos pixels, tornando-a mais normalizada.
	public static BufferedImage Equalizador(BufferedImage inputImage, int windowSizeX, int windowSizeY) {
		Raster inputRaster = inputImage.getData();

		BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				inputImage.getType());
		WritableRaster outputWRaster = outputImage.getRaster();

		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;

		for (int b = 0; b < inputRaster.getNumBands(); b++) {
			
			for (int i = 0; i < inputImage.getHeight(); i++) {
				
				int startY = Math.max(0, i - windowSizeY / 2);
				int endY = Math.min(i + windowSizeY / 2, inputImage.getHeight() - 1);

				int[] columnMin = new int[inputImage.getWidth()];
				int[] columnMax = new int[inputImage.getWidth()];

				for (int m = 0; m < inputImage.getWidth(); m++) {
					max = Integer.MIN_VALUE;
					min = Integer.MAX_VALUE;
					
					for (int k = startY; k <= endY; k++) {

						int val = inputRaster.getSample(m, k, b);
						if (k >= 0 && k < inputImage.getHeight() && val > 0) {
							if (val < min)
								min = val;
							if (val > max)
								max = val;
						}
					}
					
					columnMin[m] = min;
					columnMax[m] = max;
				}

				for (int j = 0; j < inputImage.getWidth(); j++) {
					
					int startX = Math.max(0, j - windowSizeX / 2);
					int endX = Math.min(j + windowSizeX / 2, inputImage.getWidth() - 1);

					max = Integer.MIN_VALUE;
					min = Integer.MAX_VALUE;

					for (int m = startX; m <= endX; m++) {
						if (m >= 0 && m < inputImage.getWidth()) {
							int tempMin = columnMin[m];
							int tempMax = columnMax[m];
							if (tempMin < min)
								min = tempMin;
							if (tempMax > max)
								max = tempMax;
						}
					}
					int newSample = 0;
					if (max != min)
						newSample = ((inputRaster.getSample(j, i, b) - min) * 255) / (max - min);
					else
						newSample = max;
					outputWRaster.setSample(j, i, b, newSample);
				}
			}
		}

		return outputImage;
	}

	public static BufferedImage gaussianBlur(BufferedImage inputImage, double sigma) {
		
		BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(),
				inputImage.getType());
		
		int kernelSize = 2 * (int) (Math.ceil(4 * sigma)) + 1;
		
		if (kernelSize % 2 == 0)
			kernelSize++;

		Kernel gaussianKernel = GaussianSmoother.generateKernel((float) sigma, kernelSize, kernelSize);
		ConvolveOp gaussianSmoother = new ConvolveOp(gaussianKernel);
		outputImage = gaussianSmoother.filter(inputImage, null);
		return outputImage;
	}
}
