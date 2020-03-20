package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class RidgeFrequency {

	private double[][] frequen = null;
	private double frequenMediana = 0;
	private double frequenMedia = 0;

	public RidgeFrequency(BufferedImage inputImage, double[][] orient, int blksize, int windowSize) throws IOException {
		
		double[][] blockOrient = new double[blksize][blksize];
		
		frequen = new double[inputImage.getWidth()][inputImage.getHeight()];

		for (int i = 0; i < inputImage.getHeight() - blksize; i += blksize) {
			for (int j = 0; j < inputImage.getWidth() - blksize; j += blksize) {
				
				// Pega a subimagem cuja frequência estamos examinando
				BufferedImage blockBi = inputImage.getSubimage(j, i, blksize, blksize);

				// Obtenha a orientação de cada pixel da sub-imagem
				for (int k = 0; k < blksize; k++) {
					for (int l = 0; l < blksize; l++) {
						
						blockOrient[l][k] = orient[l + j][i + k];
					}
				}

				try {
					// Frequência estimada para o bloco
					double freq = BlockFrequency.calcular(blockBi, blockOrient, windowSize);

					// Coloca o bloco de frequência na matriz de dados de frequência principal
					for (int k = 0; k < blksize; k++) {
						for (int l = 0; l < blksize; l++) {
							frequen[l + j][i + k] = freq;
						}
					}
				} catch (IOException e) {
					System.out.println("Erro!");
					e.printStackTrace();
				}

			}
		}
		generateFrequencyStats();
	}

	//Calcula a frequência média e mediana após os dados de frequência total da imagem serem obtidos.
	private void generateFrequencyStats() {
		if (this.frequen == null)
			return;

		int width = frequen.length;
		int height = frequen[0].length;

	//Calcula a frequência mediana e média real da crista em oposição à individual frequências de cada bloco.
		LinkedList<Double> freqList = new LinkedList<Double>();
		double sum = 0;
		int total = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (frequen[j][i] != -1) {
					if (frequen[j][i] > 0.0)
						freqList.add(frequen[j][i]);
					sum += frequen[j][i];
					total++;
				}
			}
		}
		Collections.sort(freqList);
		this.frequenMediana = freqList.get(freqList.size() / 2);
		this.frequenMedia = sum / (total);
	}

	public double getMediana() {

		return frequenMediana;
	}

	public double getMedia() {

		return frequenMedia;
	}

	public double[][] getFrequen() {
		return frequen;
	}
}
