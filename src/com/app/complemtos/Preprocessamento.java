package com.app.complemtos;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Preprocessamento {

	public static byte[] pré_processamento(byte[] imageInByte) throws IOException {

		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage img = ImageIO.read(in);

		img = ManipularImagem.escalaDeCinza(img);

		img = Histograma.equalizacao(img);

		img = FilterUtil.Equalizador(img, 39, 39);
		img = FilterUtil.gaussianBlur(img, 2.0);

		double[][] orient = Orientation.calcular(img, 16);
		
		RidgeFrequency rf = new RidgeFrequency(img, orient, 32, 5);
		double frequen = rf.getMediana();

		img = Gabor.execute(img, orient, frequen);

		Raster raster = img.getData();
		DataBuffer buffer = raster.getDataBuffer();
		DataBufferByte byteBuffer = (DataBufferByte) buffer;
		byte[] srcData = byteBuffer.getData(0);
		byte[] dstData = new byte[srcData.length];

		OtsuThresholder otsu = new OtsuThresholder();
		int limiar = otsu.Limiar(srcData, dstData);

		img = ManipularImagem.binarizacao(img, limiar);

		img = ZhangSuen.processImagem(img);

		img = FilterUtil.gaussianBlur(img, 2.0);
		img = FilterUtil.Equalizador(img, 39, 39);

		imageInByte = ManipularImagem.getImgBytes(img);
		return imageInByte;
	}
}
