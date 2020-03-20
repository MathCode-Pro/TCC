package com.app.complemtos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ManipularImagem {

	public static BufferedImage setImagemDimensao(byte[] caminhoImg, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		ImageIcon imagem = null;
		BufferedImage novaImagem = null;

		try {
			imagem = (new ImageIcon(caminhoImg));

			novaImgLargura = (double) imagem.getIconWidth();

			novaImgAltura = (double) imagem.getIconHeight();

			if (novaImgLargura >= imgLargura) {
				imgProporcao = (novaImgAltura / novaImgLargura);
				novaImgLargura = (double) imgLargura;

				novaImgAltura = (novaImgLargura * imgProporcao);

				while (novaImgAltura > imgAltura) {
					novaImgLargura = (double) (--imgLargura);
					novaImgAltura = (novaImgLargura * imgProporcao);
				}
			} else if (novaImgAltura >= imgAltura) {
				imgProporcao = (novaImgLargura / novaImgAltura);
				novaImgAltura = (double) imgAltura;
				while (novaImgLargura > imgLargura) {
					novaImgAltura = (double) (--imgAltura);
					novaImgLargura = (novaImgAltura * imgProporcao);
				}
			}

			novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(),
					BufferedImage.TYPE_3BYTE_BGR);
			g2d = novaImagem.createGraphics();
			imagem.paintIcon(null, g2d, 0, 0);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"A digital informada já está cadastrada! Informe uma digital não registrada!", "Erro!",
					JOptionPane.ERROR_MESSAGE);
		}

		return novaImagem;
	}

	public static BufferedImage setImagemDimensao2(String caminhoImg, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		BufferedImage imagem = null, novaImagem = null;

		try {
			imagem = ImageIO.read(new File(caminhoImg));
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar a imagem!", "Erro!", JOptionPane.ERROR_MESSAGE);

		}

		novaImgLargura = (double) imagem.getWidth();

		novaImgAltura = (double) imagem.getHeight();

		if (novaImgLargura >= imgLargura) {
			imgProporcao = (novaImgAltura / novaImgLargura);
			novaImgLargura = (double) imgLargura;

			novaImgAltura = (novaImgLargura * imgProporcao);

			while (novaImgAltura > imgAltura) {
				novaImgLargura = (double) (--imgLargura);
				novaImgAltura = (novaImgLargura * imgProporcao);
			}
		} else if (novaImgAltura >= imgAltura) {
			imgProporcao = (novaImgLargura / novaImgAltura);
			novaImgAltura = (double) imgAltura;
			while (novaImgLargura > imgLargura) {
				novaImgAltura = (double) (--imgAltura);
				novaImgLargura = (novaImgAltura * imgProporcao);
			}
		}

		novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(),
				BufferedImage.TYPE_3BYTE_BGR);
		g2d = novaImagem.createGraphics();
		g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);

		return novaImagem;
	}

	public static byte[] getImgBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "PNG", baos);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Selecione a digital do usuário!", "Atenção!",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}

		return baos.toByteArray();
	}

	public static javax.swing.JLabel exibiImagemLabel(byte [] imgB, javax.swing.JLabel label,
			javax.swing.JPanel panelImagem) throws IOException {
		
		InputStream in = new ByteArrayInputStream(imgB);
		BufferedImage img = ImageIO.read(in);
		if (img != null) {

			Graphics g = img.createGraphics();
			Dimension dDesktop = panelImagem.getSize();
			double width = dDesktop.getWidth()-1;
			double height = dDesktop.getHeight()-1;
			Image background = new ImageIcon(img.getScaledInstance((int) width, (int) height, 1)).getImage();
			g.drawImage(background, 0, 0, null);
			label.setIcon(new ImageIcon(background));
			g.dispose();

		} else {
			label.setIcon(null);
		}
		return label;

	}

	public static BufferedImage escalaDeCinza(BufferedImage imagem) {

		int width = imagem.getWidth();
		int height = imagem.getHeight();

		int media = 0;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int rgb = imagem.getRGB(i, j);

				int r = (int) ((rgb & 0x00FF0000) >>> 16); // R
				int g = (int) ((rgb & 0x0000FF00) >>> 8); // G
				int b = (int) (rgb & 0x000000FF); // B

				media = (r + g + b) / 3;

				Color color = new Color(media, media, media);
				imagem.setRGB(i, j, color.getRGB());
			}
		}
		return imagem;
	}

	public static BufferedImage binarizacao(BufferedImage image, int limiar) {

		int width = image.getWidth();

		int height = image.getHeight();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int rgb = image.getRGB(i, j);
				int r = (int) ((rgb & 0x00FF0000) >>> 16);
				int g = (int) ((rgb & 0x0000FF00) >>> 8);
				int b = (int) (rgb & 0x000000FF);
				int media = (r + g + b) / 3;
				Color white = new Color(255, 255, 255);
				Color black = new Color(0, 0, 0);

				if (media < limiar)
					image.setRGB(i, j, black.getRGB());
				else
					image.setRGB(i, j, white.getRGB());
			}
		}
		return image;
	}
}
