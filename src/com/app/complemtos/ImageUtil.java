package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageUtil {
	
	public static int[] sumImageColumns(BufferedImage inputImage) {
		Raster inputRaster = inputImage.getData();

		// System.out.println("SUM OF COLS:");
		int[] sum = new int[inputImage.getWidth()];
		for (int i = 0; i < inputImage.getWidth(); i++) {
			sum[i] = 0;
			for (int j = 0; j < inputImage.getHeight(); j++) {
				sum[i] += inputRaster.getSample(i, j, 0);
			}
			// System.out.print(sum[i]+", ");
		}
		// System.out.println();
		return sum;
	}

	public static BufferedImage loadFrom2dDouble(double[][] d, int type, boolean normalize) {
		int width = d.length;
		int height = d[0].length;

		BufferedImage outputImage = new BufferedImage(width, height, type);
		WritableRaster outputWRaster = outputImage.getRaster();

		double min = 0;
		double max = 0;

		if (normalize) {
			min = MathUtil.min(d);
			max = MathUtil.max(d);
		}

		int rgb = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (normalize)
					rgb = (int) ((d[j][i] + Math.abs(min)) * (255 / (Math.abs(min) + max)));
				else
					rgb = (int) (d[j][i]);

				outputWRaster.setSample(j, i, 0, rgb);
				try {
					outputWRaster.setSample(j, i, 1, rgb);
					outputWRaster.setSample(j, i, 2, rgb);
				} catch (Exception ee) {
				}
			}
		}
		return outputImage;
	}
}
