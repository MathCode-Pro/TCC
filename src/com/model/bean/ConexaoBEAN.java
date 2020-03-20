package com.model.bean;

public class ConexaoBEAN {

	private static String nome = null;
	private String senhaHash = null;
	private String template = null;
	private String adm = null;
	private byte[] imagem = null;
	private static int id = 0;

	public static String getNome() {
		return ConexaoBEAN.nome;
	}

	public void setNome(String nome) {
		ConexaoBEAN.nome = nome;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getAdm() {
		return adm;
	}

	public void setAdm(String adm) {
		this.adm = adm;
	}

	public int getId() {
		return id;
	}

	public static void setId(int id) {
		ConexaoBEAN.id = id;
	}

}
