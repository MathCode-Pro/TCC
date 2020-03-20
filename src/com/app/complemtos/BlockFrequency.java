package com.app.complemtos;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.LinkedList;

public class BlockFrequency {

	//Detectar a frequência de cada bloco
	public static double calcular(BufferedImage inputImage, double[][] orient, int windowSize) throws IOException {
		double meanCosOrient = 0;
		double meanSinOrient = 0;
		double totalValidOrientations = 0;
		double blockOrientation = 0;

		int orientWidth = orient.length;
		int orientHeight = orient[0].length;
		
		// Encontre orientação média sobre o bloco
		for (int i = 0; i < orientHeight; i++) {
			for (int j = 0; j < orientWidth; j++) {
				if (orient[j][i] != -1) {
					orient[j][i] = Math.toRadians(orient[j][i]);
					meanCosOrient += Math.cos(2 * orient[j][i]);
					meanSinOrient += Math.sin(2 * orient[j][i]);
					totalValidOrientations++;
				}
			}
		}

		if (totalValidOrientations == 0)
			totalValidOrientations = 1;

		meanCosOrient /= totalValidOrientations;
		meanSinOrient /= totalValidOrientations;

		blockOrientation = Math.toDegrees((Math.atan2(meanSinOrient, meanCosOrient) / 2));

		if (blockOrientation > 180 || blockOrientation < 0)
			return -1;

		// Calcular a rotação necessária para obter linhas verticais	
		double rotation = 0;
		if (blockOrientation > 90)
			rotation = -(180 - blockOrientation);
		else if (blockOrientation < 90)
			rotation = 90 - blockOrientation;
		if ((int) blockOrientation == 0)
			rotation = 90;

		rotation = -rotation;

		// Rotacionar a imagem
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(rotation), inputImage.getWidth() / 2, inputImage.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage rIm = op.filter(inputImage, null);

		// Cortar a imagem
		int cropsize = (int) (inputImage.getHeight() / Math.sqrt(2));
		int offset = (int) (((double) inputImage.getHeight() - cropsize) / 2);

		rIm = rIm.getSubimage(offset, offset, cropsize, cropsize);
		Raster rImRaster = rIm.getData();

		BufferedImage subIm = new BufferedImage(rIm.getWidth(), rIm.getHeight(), BufferedImage.TYPE_INT_RGB);
		WritableRaster subImRaster = subIm.getRaster();

		for (int i = 0; i < rIm.getHeight(); i++) {
			for (int j = 0; j < rIm.getWidth(); j++) {

				subImRaster.setSample(j, i, 0, rImRaster.getSample(j, i, 0));
				subImRaster.setSample(j, i, 1, rImRaster.getSample(j, i, 0));
				subImRaster.setSample(j, i, 2, rImRaster.getSample(j, i, 0));
			}

		}

		// Dilata a matriz de somas de coluna (será usada para encontrar picos de frequência)
		int[] colSumArray = ImageUtil.sumImageColumns(subIm);
		int[] dilation = MathUtil.dilate(colSumArray, windowSize);
		double meanColSumArray = MathUtil.avgIntArray(colSumArray);

		// Encontrar picos de frequência
		LinkedList<Integer> ll = MathUtil.findPeaks(colSumArray, dilation, meanColSumArray);

		// Calcular a frequência com base nos picos
		double freq = 0;

		if (ll.size() >= 2) {
			double firstPeak = ll.get(0);
			
			double lastPeak = ll.get(ll.size() - 1);

			double noPeaks = ll.size() - 1;

			double wavelength = (lastPeak - firstPeak) / (noPeaks);
			freq = 1 / wavelength;

		} else {
			freq = 0;
		}

		return freq;
	}
}
