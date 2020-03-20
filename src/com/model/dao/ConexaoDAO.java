package com.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.bean.ConexaoBEAN;

public class ConexaoDAO {

	private Connection con;
	private PreparedStatement stmt;
	public boolean conectou;

	public Connection conectar() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sbs", "root", "");

			if (con != null) {

				conectou = true;

			} else {

				conectou = false;
			}

			return con;

		} catch (ClassNotFoundException e) {

			System.out.println("O driver expecificado nao foi encontrado.");
			return null;

		} catch (SQLException e) {
			conectou = false;
			return null;
		}
	}

	private boolean pegarIdSenha(String senha) {

		String id = "SELECT usuario.id AS id FROM usuario WHERE usuario.senha = ?";

		try {
			stmt = con.prepareStatement(id);

			stmt.setString(1, senha);

			ResultSet res = stmt.executeQuery();

			while (res.next()) {

				ConexaoBEAN.setId(res.getInt("id"));
			}

			stmt.close();
			res.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean pegarIdNome(ConexaoBEAN dados) {

		String id = "SELECT usuario.id AS id FROM usuario WHERE usuario.nome LIKE ?";

		try {
			stmt = con.prepareStatement(id);

			stmt.setString(1, ConexaoBEAN.getNome());

			ResultSet res = stmt.executeQuery();

			while (res.next()) {

				ConexaoBEAN.setId(res.getInt("id"));
			}

			stmt.close();
			res.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificarUsuario(ConexaoBEAN dados, String consulta) {

		if (consulta.equals("senha")) {

			pegarIdSenha(dados.getSenhaHash());
			String consultaSenha = "SELECT nome FROM usuario WHERE id=?";

			try {

				stmt = con.prepareStatement(consultaSenha);
				stmt.setInt(1, dados.getId());

				ResultSet res = stmt.executeQuery();

				if (res.next()) {

					return true;
				}

				stmt.close();
				res.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}

		} else if (consulta.equals("nome")) {

			pegarIdNome(dados);
			String consultaNome = "SELECT nome FROM usuario WHERE id=?";

			try {

				stmt = con.prepareStatement(consultaNome);
				stmt.setInt(1, dados.getId());

				ResultSet res = stmt.executeQuery();

				if (res.next()) {

					return true;
				}

				stmt.close();
				res.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}

		} else if (consulta.equals("adm")) {

			pegarIdSenha(dados.getSenhaHash());
			String consultaADM = "SELECT adm FROM usuario WHERE id=?";

			try {

				stmt = con.prepareStatement(consultaADM);
				stmt.setInt(1, dados.getId());

				ResultSet res = stmt.executeQuery();

				if (res.next()) {

					return true;
				}

				stmt.close();
				res.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}

		} else if (consulta.equals("digital")) {

			String consultaDigital = "SELECT imagem FROM digital WHERE imagem=?";

			try {

				stmt = con.prepareStatement(consultaDigital);
				stmt.setBytes(1, dados.getImagem());

				ResultSet res = stmt.executeQuery();

				if (res.next()) {

					return true;
				}

				stmt.close();
				res.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return false;

			}

		}

		return false;
	}

	public boolean inserir(ConexaoBEAN dados) {

		// tabela usuário
		String sql = "INSERT INTO usuario(senha,nome,adm) VALUES (?,?,?)";

		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, dados.getSenhaHash());
			stmt.setString(2, ConexaoBEAN.getNome());
			stmt.setString(3, dados.getAdm());

			stmt.execute();
			stmt.close();

			// pegar chave primaria
			pegarIdSenha(dados.getSenhaHash());

			// tabela digital
			String sql2 = "INSERT INTO digital(template,imagem,usuario_senha,usuario_fk) VALUES (?,?,?,?)";

			stmt = con.prepareStatement(sql2);

			stmt.setString(1, dados.getTemplate());
			stmt.setBytes(2, dados.getImagem());
			stmt.setString(3, dados.getSenhaHash());
			stmt.setInt(4, dados.getId());

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean excluir(ConexaoBEAN dados) {

		try {

			String sql = "DELETE FROM usuario WHERE senha=?";

			stmt = con.prepareStatement(sql);

			stmt.setString(1, dados.getSenhaHash());

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

	}

	public boolean excluirNome(ConexaoBEAN dados) {

		try {

			String sql = "DELETE FROM usuario WHERE nome=?";

			stmt = con.prepareStatement(sql);

			stmt.setString(1, ConexaoBEAN.getNome());

			stmt.execute();
			stmt.close();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

		}

	}

	public boolean alterar(ConexaoBEAN dados, String temp) {

		// pegar chave primaria
		pegarIdSenha(temp);

		String sql = "UPDATE usuario u INNER JOIN digital d ON d.usuario_fk = u.id "
				+ "SET u.senha=?, u.nome=?, u.adm=?, d.template=?, d.imagem=?, d.usuario_senha=? WHERE u.id=?";

		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, dados.getSenhaHash());
			stmt.setString(2, ConexaoBEAN.getNome());
			stmt.setString(3, dados.getAdm());
			stmt.setString(4, dados.getTemplate());
			stmt.setBytes(5, dados.getImagem());
			stmt.setString(6, dados.getSenhaHash());
			stmt.setInt(7, dados.getId());

			stmt.executeUpdate();
			stmt.close();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean buscarSenha(ConexaoBEAN dados) {

		// pegar chave primaria
		pegarIdSenha(dados.getSenhaHash());
		
		String sql = ("SELECT usuario.`nome` AS nome, usuario.`adm` AS adm, "
				+ "digital.`template` AS template, digital.`imagem` AS imagem "
				+ "FROM usuario INNER JOIN digital ON usuario.`senha` = digital.`usuario_senha` "
				+ "WHERE usuario.`id` = ?");

		try {

			stmt = con.prepareStatement(sql);

			stmt.setInt(1, dados.getId());

			ResultSet res = stmt.executeQuery();

			while (res.next()) {

				dados.setNome(res.getString("nome"));
				dados.setAdm(res.getString("adm"));
				dados.setTemplate(res.getString("template"));
				dados.setImagem(res.getBytes("imagem"));

				return true;
			}

			stmt.close();
			res.close();

		} catch (SQLException e) {
			return false;
		}

		return false;
	}

	public boolean buscarNome(ConexaoBEAN dados) {

		// pegar chave primaria
		pegarIdNome(dados);

		String sql = ("SELECT usuario.adm AS adm, usuario.senha AS senha, digital.template AS template, digital.imagem AS imagem "
				+ "FROM usuario INNER JOIN digital ON usuario.senha = digital.usuario_senha WHERE usuario.id = ?");
		try {

			stmt = con.prepareStatement(sql);

			stmt.setInt(1, dados.getId());

			ResultSet res = stmt.executeQuery();

			while (res.next()) {

				dados.setAdm(res.getString("adm"));
				dados.setSenhaHash(res.getString("senha"));
				dados.setTemplate(res.getString("template"));
				dados.setImagem(res.getBytes("imagem"));

				return true;
			}

			stmt.close();
			res.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public void fecharCon() {

		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
