package com.app.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.app.complemtos.CriptMD5;
import com.app.complemtos.ManipularImagem;
import com.app.complemtos.Preprocessamento;
import com.app.validDig.controletfSenha;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.model.bean.ConexaoBEAN;
import com.model.dao.ConexaoDAO;

public class login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, panelImagem;
	private static JButton btnBuscar, btnLogin;
	private static JLabel lblImagem;
	private static JPasswordField tfSenha;
	private static JFormattedTextField tfNome;
	private JToggleButton tglbtnAtivardesativarDicas;
	private String dica1 = "Obs: no máximo 10 caracteres.";
	private String dica2 = "Obs 2: dê um duplo clique\r\npara remover a imagem.";
	private static ConexaoBEAN bean = new ConexaoBEAN();
	private static JFileChooser fileChooser;
	private JLabel lblObsNoMximo_1;
	private JTextPane txtpnObsDUm;
	private JPanel panelDigital;
	private static byte[] img;
	private static JLabel lblDigital;
	private static ConexaoDAO dao = new ConexaoDAO();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
					btnBuscar.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("serial")
	public login() {
		setIconImage(new ImageIcon(getClass().getResource("/imagens/icone.png")).getImage());
		setTitle("SOLUÇÃO BIOMÉTRICA DE SEGURANÇA - Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 769, 495);
		contentPane = new JPanel() {

			Image image = getImage("/imagens/fundo.jpg");

			public Image getImage(String path) {
				URL imageURL = getClass().getResource(path);
				if (imageURL == null)
					return null;
				return new ImageIcon(imageURL).getImage();
			}

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension dDesktop = this.getSize();
				double width = dDesktop.getWidth();
				double height = dDesktop.getHeight();
				Image background = new ImageIcon(this.image.getScaledInstance((int) width, (int) height, 1)).getImage();
				g.drawImage(background, 0, 0, this);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);
		setResizable(false);

		JLabel lblUsurio = new JLabel("Usuário: ");
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setBounds(5, 28, 50, 14);

		tfNome = new JFormattedTextField();
		tfNome.setEditable(false);
		tfNome.setBounds(65, 25, 239, 20);
		tfNome.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setBounds(7, 86, 48, 14);

		tfSenha = new JPasswordField();
		tfSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
					e.consume();
					return;
				}
			}
		});
		tfSenha.setBounds(65, 83, 119, 20);
		tfSenha.setEditable(false);
		tfSenha.setHorizontalAlignment(SwingConstants.LEFT);
		tfSenha.setDocument(new controletfSenha(16));

		btnLogin = new JButton("Logar-se");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
					e.consume();
					return;
				}
			}
		});
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(5, 136, 99, 23);
		btnLogin.setEnabled(false);
		btnLogin.setOpaque(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				do_btnLogin_actionPerformed1(arg0);
			}
		});

		panelImagem = new JPanel();
		panelImagem.setForeground(Color.BLACK);
		panelImagem.setBounds(588, 16, 147, 188);
		panelImagem.setBorder(new LineBorder(Color.WHITE, 2, true));
		panelImagem.setOpaque(false);

		btnBuscar = new JButton("Buscar Digital");
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscar.doClick();
					e.consume();
					return;
				}
			}
		});
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBounds(598, 215, 119, 23);
		btnBuscar.setOpaque(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buscarBi();
					;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		lblObsNoMximo_1 = new JLabel("");
		lblObsNoMximo_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblObsNoMximo_1.setForeground(Color.WHITE);
		lblObsNoMximo_1.setBounds(54, 111, 250, 23);
		lblObsNoMximo_1.setHorizontalAlignment(SwingConstants.CENTER);

		lblImagem = new JLabel("");
		lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						apagar();
						break;
					case MouseEvent.BUTTON2:
						break;
					case MouseEvent.BUTTON3:
						break;
					default:
						break;
					}
				}
			}
		});
		panelImagem.setLayout(new CardLayout(0, 0));
		panelImagem.add(lblImagem, "name_2787595731700");

		tglbtnAtivardesativarDicas = new JToggleButton("Ativar/Desativar Dicas");
		tglbtnAtivardesativarDicas.setForeground(Color.WHITE);
		tglbtnAtivardesativarDicas.setBackground(Color.WHITE);
		tglbtnAtivardesativarDicas.setBounds(5, 432, 163, 23);
		tglbtnAtivardesativarDicas.setOpaque(false);
		tglbtnAtivardesativarDicas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (tglbtnAtivardesativarDicas.isSelected()) {
					mostrarDicas();
				} else {
					esconderDicas();
				}
			}
		});
		tglbtnAtivardesativarDicas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tglbtnAtivardesativarDicas.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							tglbtnAtivardesativarDicas.doClick();
							e.consume();
							return;
						}
					}
				});
			}
		});

		txtpnObsDUm = new JTextPane();
		txtpnObsDUm.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnObsDUm.setForeground(Color.WHITE);
		txtpnObsDUm.setBackground(Color.WHITE);
		txtpnObsDUm.setBounds(568, 249, 167, 34);
		txtpnObsDUm.setEditable(false);
		txtpnObsDUm.setOpaque(false);

		contentPane.setLayout(null);
		contentPane.add(lblUsurio);
		contentPane.add(lblSenha);
		contentPane.add(tfSenha);
		contentPane.add(lblObsNoMximo_1);
		contentPane.add(tfNome);
		contentPane.add(btnLogin);
		contentPane.add(panelImagem);
		contentPane.add(btnBuscar);
		contentPane.add(txtpnObsDUm);
		contentPane.add(tglbtnAtivardesativarDicas);

		panelDigital = new JPanel();
		panelDigital.setOpaque(false);
		panelDigital.setForeground(Color.BLACK);
		panelDigital.setBorder(new LineBorder(Color.WHITE, 2, true));
		panelDigital.setBounds(375, 16, 147, 188);
		contentPane.add(panelDigital);
		panelDigital.setLayout(new CardLayout(0, 0));

		lblDigital = new JLabel("");
		lblDigital.setHorizontalAlignment(SwingConstants.CENTER);
		panelDigital.add(lblDigital, "name_20226772771000");

	}

	// meus métodos.

	private File selecionarImagem() {

		fileChooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos JPG, PNG & BMP",
				new String[] { "jpg", "png", "bmp" });
		fileChooser.addChoosableFileFilter(filtro);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.showOpenDialog(this);

		if (fileChooser.getSelectedFile() != null) {
			try {
				img = Files.readAllBytes(fileChooser.getSelectedFile().toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			;
		} else {
			return null;
		}
		return fileChooser.getSelectedFile();
	}

	private boolean comparar(byte[] img) {

		FingerprintTemplate template = new FingerprintTemplate().deserialize(bean.getTemplate());

		FingerprintTemplate bi = null;
		try {
			bi = new FingerprintTemplate().dpi(500).create(Preprocessamento.pré_processamento(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double semelhança = new FingerprintMatcher().index(bi).match(template);
		boolean iguais = semelhança >= 40;
		return iguais;
	}

	private void do_btnLogin_actionPerformed1(ActionEvent arg0) {

		String o = String.valueOf(tfSenha.getPassword());

		limparBean();

		if (o.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Preêncha o campo de senha corretamente!", "Atenção!",
					JOptionPane.WARNING_MESSAGE);
			apagar();
			return;

		} else {

			dao.conectar();

			if (dao.conectou == true) {

				bean.setSenhaHash(CriptMD5.md5(o.substring(0, 3) + o.trim() + o.substring(3)));

				if (dao.buscarSenha(bean) == true) {

					if (comparar(img) == true) {

						tfNome.setText(ConexaoBEAN.getNome());
						try {
							lblDigital = ManipularImagem.exibiImagemLabel(bean.getImagem(), lblDigital, panelDigital);
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (bean.getAdm().equals("Sim")) {
							dao.fecharCon();
							JOptionPane.showMessageDialog(null, "Bem vindo " + ConexaoBEAN.getNome() + "!",
									"Olá Administrador!", JOptionPane.DEFAULT_OPTION);
							windowClosing();

						} else {

							dao.fecharCon();
							JOptionPane.showMessageDialog(null, "Bem vindo " + ConexaoBEAN.getNome() + "!", "Olá!",
									JOptionPane.DEFAULT_OPTION);
							System.exit(0);
						}

					} else {

						try {
							lblDigital = ManipularImagem.exibiImagemLabel(bean.getImagem(), lblDigital, panelDigital);
						} catch (IOException e) {
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,
								"Não foi possível autenticar sua digital. Por favor, tente novamente.", "Erro!",
								JOptionPane.ERROR_MESSAGE);
						limparBean();
						apagar();
						return;
					}

				} else if (dao.buscarSenha(bean) == false) {
					JOptionPane.showMessageDialog(null, "Usuário não encontrado! Insira um usuário já cadastrado. "
							+ "Caso não seja um usuário cadastrado, solicite ao Administrador para registra-lo.",
							"Erro!", JOptionPane.ERROR_MESSAGE);
					limparBean();
					apagar();
					return;
				}

			} else {
				JOptionPane.showMessageDialog(null, "Nao foi possivel conectar ao Banco de Dados.", "Erro!",
						JOptionPane.ERROR_MESSAGE);
				apagar();
				return;
			}
		}
	}

	private void esconderDicas() {
		lblObsNoMximo_1.setText(null);
		txtpnObsDUm.setText(null);
	}

	private void mostrarDicas() {
		lblObsNoMximo_1.setText(dica1);
		txtpnObsDUm.setText(dica2);
	}

	private static void apagar() {
		tfNome.setText(null);
		tfSenha.setText(null);
		lblDigital.setIcon(null);
		lblImagem.setIcon(null);
		btnLogin.setEnabled(false);
		tfSenha.setEditable(false);
		fileChooser = null;
		img = null;
	}

	private void windowClosing() {

		apagar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaControle frame = new TelaControle();
					frame.setVisible(true);
					frame.dadosLogin();
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void limparBean() {

		bean.setImagem(null);
		bean.setNome(null);
		bean.setSenhaHash(null);
		bean.setTemplate(null);
		bean.setAdm(null);
	}

	private void buscarBi() throws IOException {

		selecionarImagem();

		if (img == null) {
			return;

		} else {

			tfSenha.setEditable(true);
			tfSenha.requestFocus();
			btnLogin.setEnabled(true);

			try {
				lblImagem = ManipularImagem.exibiImagemLabel(img, lblImagem, panelImagem);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
