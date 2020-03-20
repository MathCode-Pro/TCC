package com.app.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.app.complemtos.CriptMD5;
import com.app.complemtos.ManipularImagem;
import com.app.complemtos.Preprocessamento;
import com.app.validDig.controleLetra;
import com.app.validDig.controletfSenha;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.model.bean.ConexaoBEAN;
import com.model.dao.ConexaoDAO;

import net.miginfocom.swing.MigLayout;

public class TelaControle extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, painelImagemAlt, panel_2, panel_1, panelDados, panelFoto, panelFotoAlt, panelDadosAlt,
			panelExcluir, panelDadosExcl, panelFotoExcl, painelImagemExlc;
	static ConexaoBEAN bean = new ConexaoBEAN();
	static ConexaoDAO dao = new ConexaoDAO();
	private static JFormattedTextField tfNome;
	private static JLabel lblImagemAlt;
	private static JButton btnRegistrar, btnAlterar;
	private JButton btnBuscarFot, btnDeslogar, btnBuscarFotAlt, btnExcluir, btnLimparTudo, btnBuscarExcl, btnBuscarAlt;
	private static JFileChooser fileChooser;
	private JToggleButton tglbtnDesativarDicas;
	private String dica1 = " Obs: para ativar o botão \"Registrar\" busque a impressão digital do usuário.";
	private String dica3 = " Obs 2: os campos marcados com \" * \" s\u00E3o obrigat\u00F3rios.";
	private String dica6 = " Obs: para ativar o botão \"Alterar\" busque o usuário registrado pela sua"
			+ " senha ou nome registrado. Para alterar os dados, basta mudar as informações que deseja"
			+ " alterar e clicar no botão \"Alterar\".";
	private String dica4 = " Obs: para ativar o botão \"Excluir\" busque o usuário registrado pelo seu nome ou senha registrada.";
	private JTextPane txtpnParaAtivarO_1Alt;
	private String loginNome;
	protected TelaControle frame;
	private byte[] img;
	private JLabel lblObsOsCampos, lblObsOsCamposAlt, lblUsurio, lblAlterarUsuario, lblAdmAlt, lblNomeExcl,
			lblSenhaExcl, lblImagemExcl;
	private JLabel lblAdm;
	private JLabel lblNovoUsuario;

	private JFormattedTextField tfNomeAlt, tfNomeExcl;
	private JPasswordField tfSenhaExcl, tfSenhaAlt, tfSenha;
	private JComboBox<Object> cbAdm, cbAdmAlt;

	private String fundo = "/imagens/fundo.jpg";
	private JTextPane txtpnObsParaAtivar;
	private JPanel painelImagem;
	private JLabel lblImagem;
	private JLabel lblSenhaAlt;
	private String temp;
	private JTextPane txtpnParaAtivarO_1;
	private JLabel labelExcluir;

	// contrutor da janela
	public TelaControle() throws ParseException {

		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaControle.class.getResource("/imagens/icone.png")));
		setTitle(
				"SBS - Solução Biométrica de Segurança                                                                               "
						+ "by Matheus Ol. Sant. ;)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1159, 679);

		contentPane = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Image image = getImage(fundo);

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
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBackground(new Color(0, 0, 0, 0));
		tabbedPane.setOpaque(false);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		tabbedPane.setBorder(null);

		JPanel panelInserir = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Image image = getImage(fundo);

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
		panelInserir.setForeground(Color.WHITE);
		panelInserir.setOpaque(false);
		panelInserir.setToolTipText("");
		tabbedPane.addTab("Registrar", panelInserir);
		tabbedPane.setBackgroundAt(0, new Color(0, 0, 0, 0));
		panelInserir.setLayout(new MigLayout("",
				"[424.00px,grow,left][424.00px,left][164.00px,fill][419.00px,grow,right]", "[347.00,grow][]"));

		panelDados = new JPanel();
		panelDados.setOpaque(false);
		panelInserir.add(panelDados, "cell 0 0 2 1,alignx left,aligny top");
		GridBagLayout gbl_panelDados = new GridBagLayout();
		gbl_panelDados.columnWidths = new int[] { 82, 170, 0, 16, 0 };
		gbl_panelDados.rowHeights = new int[] { 0, 20, 0 };
		gbl_panelDados.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelDados.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panelDados.setLayout(gbl_panelDados);

		lblNovoUsuario = new JLabel("Novo Usu\u00E1rio:");
		lblNovoUsuario.setForeground(Color.YELLOW);
		GridBagConstraints gbc_lblNovoUsuario = new GridBagConstraints();
		gbc_lblNovoUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNovoUsuario.gridwidth = 2;
		gbc_lblNovoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNovoUsuario.gridx = 0;
		gbc_lblNovoUsuario.gridy = 0;
		panelDados.add(lblNovoUsuario, gbc_lblNovoUsuario);

		JLabel lblnome = new JLabel("*Nome: ");
		GridBagConstraints gbc_lblnome = new GridBagConstraints();
		gbc_lblnome.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblnome.insets = new Insets(0, 0, 5, 5);
		gbc_lblnome.gridx = 0;
		gbc_lblnome.gridy = 1;
		panelDados.add(lblnome, gbc_lblnome);
		lblnome.setForeground(Color.WHITE);

		tfNome = new JFormattedTextField();
		tfNome.setColumns(30);
		tfNome.setDocument(new controleLetra(80));
		GridBagConstraints gbc_tfNome = new GridBagConstraints();
		gbc_tfNome.gridwidth = 3;
		gbc_tfNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNome.insets = new Insets(0, 0, 5, 0);
		gbc_tfNome.gridx = 1;
		gbc_tfNome.gridy = 1;
		panelDados.add(tfNome, gbc_tfNome);

		JLabel lblSenha = new JLabel("*Senha: ");
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSenha.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenha.gridx = 0;
		gbc_lblSenha.gridy = 2;
		panelDados.add(lblSenha, gbc_lblSenha);
		lblSenha.setForeground(Color.WHITE);

		tfSenha = new JPasswordField();
		tfSenha.setColumns(15);
		tfSenha.setDocument(new controletfSenha(16));
		tfSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarFot.doClick();
					e.consume();
					return;
				}
			}
		});

		GridBagConstraints gbc_tfSenha = new GridBagConstraints();
		gbc_tfSenha.gridwidth = 2;
		gbc_tfSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSenha.insets = new Insets(0, 0, 5, 5);
		gbc_tfSenha.gridx = 1;
		gbc_tfSenha.gridy = 2;
		panelDados.add(tfSenha, gbc_tfSenha);

		lblAdm = new JLabel("ADM: ");
		lblAdm.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblAdm = new GridBagConstraints();
		gbc_lblAdm.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAdm.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdm.gridx = 0;
		gbc_lblAdm.gridy = 3;
		panelDados.add(lblAdm, gbc_lblAdm);

		cbAdm = new JComboBox<Object>();
		cbAdm.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "Sim" }));
		cbAdm.setSelectedIndex(0);
		cbAdm.setMaximumRowCount(5);
		GridBagConstraints gbc_cbAdm = new GridBagConstraints();
		gbc_cbAdm.anchor = GridBagConstraints.WEST;
		gbc_cbAdm.insets = new Insets(0, 0, 5, 5);
		gbc_cbAdm.gridx = 1;
		gbc_cbAdm.gridy = 3;
		panelDados.add(cbAdm, gbc_cbAdm);

		panelFoto = new JPanel();
		panelFoto.setOpaque(false);
		panelInserir.add(panelFoto, "cell 3 0,alignx right,aligny top");
		panelFoto.setLayout(new MigLayout("", "[150.00px,fill][30.00px,fill][10px,right][77px,grow,fill]",
				"[23px][23px][165px,fill][23px,grow,bottom]"));

		btnRegistrar = new JButton("Registrar");
		panelFoto.add(btnRegistrar, "cell 2 0 2 1,growx,aligny top");
		btnRegistrar.setBackground(Color.WHITE);
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setEnabled(false);
		btnRegistrar.setOpaque(false);
		btnRegistrar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				btnRegistrar_actionPerformed(arg0);
			}
		});

		btnRegistrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegistrar.doClick();
					e.consume();
					return;
				}
			}
		});

		painelImagem = new JPanel();
		painelImagem.setOpaque(false);
		painelImagem.setBorder(new LineBorder(Color.WHITE, 2, true));
		panelFoto.add(painelImagem, "cell 0 0 2 3,grow");
		painelImagem.setLayout(new CardLayout(0, 0));

		lblImagem = new JLabel("");
		lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						btnLimparTudo.doClick();
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
		painelImagem.add(lblImagem, "name_99357072250000");
		painelImagem.setLayout(new CardLayout(0, 0));

		btnBuscarFot = new JButton("Buscar foto\r\n");
		panelFoto.add(btnBuscarFot, "flowx,cell 0 3 2 1,growx,aligny top");
		btnBuscarFot.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarFot.doClick();
					e.consume();
					return;
				}
			}
		});

		btnBuscarFot.setBackground(Color.WHITE);
		btnBuscarFot.setForeground(Color.WHITE);
		btnBuscarFot.setOpaque(false);
		btnBuscarFot.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnBuscar_actionPerformed(e);
			}
		});

		lblObsOsCampos = new JLabel(" Obs 3: os campos marcados com \" * \" s\u00E3o obrigat\u00F3rios.");
		panelInserir.add(lblObsOsCampos, "cell 0 1 2 1,alignx left,aligny bottom");
		lblObsOsCampos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblObsOsCampos.setForeground(Color.WHITE);

		txtpnParaAtivarO_1 = new JTextPane();
		txtpnParaAtivarO_1.setOpaque(false);
		txtpnParaAtivarO_1.setForeground(Color.WHITE);
		txtpnParaAtivarO_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnParaAtivarO_1.setEditable(false);
		panelInserir.add(txtpnParaAtivarO_1, "cell 3 1,alignx right,aligny top");

		JPanel panelAlterar = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Image image = getImage(fundo);

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
		panelAlterar.setForeground(Color.WHITE);
		panelAlterar.setOpaque(false);
		tabbedPane.addTab("Alterar", null, panelAlterar, null);
		panelAlterar.setLayout(new MigLayout("",
				"[424.00px,grow,left][424.00px,left][164.00px,fill][419.00px,grow,right]", "[347.00,grow][]"));
		panelDadosAlt = new JPanel();
		panelDadosAlt.setOpaque(false);
		panelAlterar.add(panelDadosAlt, "cell 0 0 2 1,alignx left,aligny top");
		GridBagLayout gbl_panelDados1 = new GridBagLayout();
		gbl_panelDados1.columnWidths = new int[] { 82, 170, 0, 16, 0 };
		gbl_panelDados1.rowHeights = new int[] { 0, 20, 0 };
		gbl_panelDados1.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelDados1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panelDadosAlt.setLayout(gbl_panelDados1);

		lblAlterarUsuario = new JLabel("Alterar Usu\u00E1rio:");
		lblAlterarUsuario.setForeground(Color.YELLOW);
		GridBagConstraints gbc_lblAlterarUsuario = new GridBagConstraints();
		gbc_lblAlterarUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAlterarUsuario.gridwidth = 2;
		gbc_lblAlterarUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlterarUsuario.gridx = 0;
		gbc_lblAlterarUsuario.gridy = 0;
		panelDadosAlt.add(lblAlterarUsuario, gbc_lblAlterarUsuario);

		JLabel lblnomeAlt = new JLabel("*Nome: ");
		GridBagConstraints gbc_lblnome1 = new GridBagConstraints();
		gbc_lblnome1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblnome1.insets = new Insets(0, 0, 5, 5);
		gbc_lblnome1.gridx = 0;
		gbc_lblnome1.gridy = 1;
		panelDadosAlt.add(lblnomeAlt, gbc_lblnome1);
		lblnomeAlt.setForeground(Color.WHITE);

		tfNomeAlt = new JFormattedTextField();
		tfNomeAlt.setColumns(30);
		GridBagConstraints gbc_tfNome1 = new GridBagConstraints();
		gbc_tfNome1.gridwidth = 3;
		gbc_tfNome1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNome1.insets = new Insets(0, 0, 5, 0);
		gbc_tfNome1.gridx = 1;
		gbc_tfNome1.gridy = 1;
		panelDadosAlt.add(tfNomeAlt, gbc_tfNome1);
		tfNomeAlt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarAlt.doClick();
					e.consume();
					return;
				}
			}
		});
		tfNomeAlt.setDocument(new controleLetra(60));

		lblSenhaAlt = new JLabel("*Senha: ");
		lblSenhaAlt.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSenhaAlt = new GridBagConstraints();
		gbc_lblSenhaAlt.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSenhaAlt.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenhaAlt.gridx = 0;
		gbc_lblSenhaAlt.gridy = 2;
		panelDadosAlt.add(lblSenhaAlt, gbc_lblSenhaAlt);

		tfSenhaAlt = new JPasswordField();
		tfSenhaAlt.setColumns(15);
		tfSenhaAlt.setDocument(new controletfSenha(16));

		GridBagConstraints gbc_tfSenhaAlt = new GridBagConstraints();
		gbc_tfSenhaAlt.insets = new Insets(0, 0, 5, 5);
		gbc_tfSenhaAlt.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSenhaAlt.gridx = 1;
		gbc_tfSenhaAlt.gridy = 2;
		panelDadosAlt.add(tfSenhaAlt, gbc_tfSenhaAlt);

		lblAdmAlt = new JLabel("ADM: ");
		lblAdmAlt.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblAdmAlt = new GridBagConstraints();
		gbc_lblAdmAlt.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAdmAlt.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdmAlt.gridx = 0;
		gbc_lblAdmAlt.gridy = 3;
		panelDadosAlt.add(lblAdmAlt, gbc_lblAdmAlt);

		cbAdmAlt = new JComboBox<Object>();
		cbAdmAlt.setModel(new DefaultComboBoxModel<Object>(new String[] { "", "Sim" }));
		cbAdmAlt.setMaximumRowCount(5);
		GridBagConstraints gbc_cbAdmAlt = new GridBagConstraints();
		gbc_cbAdmAlt.anchor = GridBagConstraints.WEST;
		gbc_cbAdmAlt.insets = new Insets(0, 0, 5, 5);
		gbc_cbAdmAlt.gridx = 1;
		gbc_cbAdmAlt.gridy = 3;
		panelDadosAlt.add(cbAdmAlt, gbc_cbAdmAlt);

		panelFotoAlt = new JPanel();
		panelFotoAlt.setOpaque(false);
		panelAlterar.add(panelFotoAlt, "cell 3 0,alignx right,aligny top");
		panelFotoAlt.setLayout(new MigLayout("", "[150.00px,fill][30.00px,fill][10px,right][77px,grow,fill]",
				"[23px][23px][162px,fill][23px,grow,bottom]"));

		btnAlterar = new JButton("Alterar");
		panelFotoAlt.add(btnAlterar, "cell 2 0 2 1,growx,aligny top");
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setForeground(Color.WHITE);
		btnAlterar.setEnabled(false);
		btnAlterar.setOpaque(false);
		btnAlterar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				btnAlterar_actionPerformed(arg0);
			}
		});

		btnAlterar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnAlterar.doClick();
					e.consume();
					return;
				}
			}
		});

		painelImagemAlt = new JPanel();
		panelFotoAlt.add(painelImagemAlt, "cell 0 0 2 3,grow");
		painelImagemAlt.setBorder(new LineBorder(Color.WHITE, 2, true));
		painelImagemAlt.setOpaque(false);

		lblImagemAlt = new JLabel();
		lblImagemAlt.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagemAlt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						btnLimparTudo.doClick();
						e.consume();
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
		painelImagemAlt.setLayout(new CardLayout(0, 0));
		painelImagemAlt.add(lblImagemAlt, "name_29937897275700");

		btnBuscarAlt = new JButton("Buscar");
		btnBuscarAlt.setOpaque(false);
		btnBuscarAlt.setForeground(Color.WHITE);
		btnBuscarAlt.setBackground(Color.WHITE);
		panelFotoAlt.add(btnBuscarAlt, "cell 2 1 2 1,growx,aligny top");
		btnBuscarAlt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnBuscarAlt_actionPerformed(e);
			}
		});

		btnBuscarAlt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarAlt.doClick();
					e.consume();
					return;
				}
			}
		});

		btnBuscarFotAlt = new JButton("Buscar foto\r\n");
		panelFotoAlt.add(btnBuscarFotAlt, "flowx,cell 0 3 2 1,growx,aligny top");
		btnBuscarFotAlt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnBuscarFotAlt_actionPerformed(e);
			}
		});

		btnBuscarFotAlt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarFotAlt.doClick();
					e.consume();
					return;
				}
			}
		});

		btnBuscarFotAlt.setBackground(Color.WHITE);
		btnBuscarFotAlt.setForeground(Color.WHITE);
		btnBuscarFotAlt.setOpaque(false);

		lblObsOsCamposAlt = new JLabel(" Obs 3: os campos marcados com \" * \" s\u00E3o obrigat\u00F3rios.");
		panelAlterar.add(lblObsOsCamposAlt, "cell 0 1 2 1,alignx left,aligny bottom");
		lblObsOsCamposAlt.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblObsOsCamposAlt.setForeground(Color.WHITE);

		txtpnParaAtivarO_1Alt = new JTextPane();
		panelAlterar.add(txtpnParaAtivarO_1Alt, "cell 3 1,alignx right,aligny top");
		txtpnParaAtivarO_1Alt.setEditable(false);
		txtpnParaAtivarO_1Alt.setOpaque(false);
		txtpnParaAtivarO_1Alt.setForeground(Color.WHITE);
		txtpnParaAtivarO_1Alt.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnParaAtivarO_1Alt.setText(
				"Obs: para ativar o bot\u00E3o \"Alterar\" busque o usu\u00E1rio registrado pela sua senha ou nome registrado. Para alterar os dados, basta mudar as informa\u00E7\u00F5es que deseja alterar e clicar no bot\u00E3o \"Alterar\".");
		contentPane.setLayout(new MigLayout("", "[482.00px,left][444px,grow,right]", "[25.00px][238px,grow,fill]"));
		contentPane.add(tabbedPane, "cell 0 1 2 1,grow");

		panelExcluir = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Image image = getImage(fundo);

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
		panelExcluir.setToolTipText("");
		panelExcluir.setOpaque(false);
		panelExcluir.setForeground(Color.WHITE);
		tabbedPane.addTab("Excluir", null, panelExcluir, null);
		panelExcluir.setLayout(new MigLayout("",
				"[424.00px,grow,left][424.00px,left][164.00px,fill][419.00px,grow,right]", "[347.00,grow][]"));

		panelDadosExcl = new JPanel();
		panelDadosExcl.setOpaque(false);
		panelExcluir.add(panelDadosExcl, "cell 0 0,alignx left,aligny top");
		GridBagLayout gbl_panelDadosExcl = new GridBagLayout();
		gbl_panelDadosExcl.columnWidths = new int[] { 82, 170, 0, 16, 0 };
		gbl_panelDadosExcl.rowHeights = new int[] { 0, 20, 0 };
		gbl_panelDadosExcl.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelDadosExcl.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panelDadosExcl.setLayout(gbl_panelDadosExcl);

		labelExcluir = new JLabel("Excluir Usu\u00E1rio:");
		labelExcluir.setForeground(Color.YELLOW);
		GridBagConstraints gbc_labelExcluir = new GridBagConstraints();
		gbc_labelExcluir.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelExcluir.gridwidth = 2;
		gbc_labelExcluir.insets = new Insets(0, 0, 5, 5);
		gbc_labelExcluir.gridx = 0;
		gbc_labelExcluir.gridy = 0;
		panelDadosExcl.add(labelExcluir, gbc_labelExcluir);

		lblNomeExcl = new JLabel("Nome: ");
		lblNomeExcl.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblNomeExcl = new GridBagConstraints();
		gbc_lblNomeExcl.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNomeExcl.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomeExcl.gridx = 0;
		gbc_lblNomeExcl.gridy = 1;
		panelDadosExcl.add(lblNomeExcl, gbc_lblNomeExcl);

		tfNomeExcl = new JFormattedTextField();
		tfNomeExcl.setColumns(30);
		tfNomeExcl.setDocument(new controleLetra(80));
		tfNomeExcl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarExcl.doClick();
					arg0.consume();
					return;
				}
			}
		});
		GridBagConstraints gbc_tfNomeExcl = new GridBagConstraints();
		gbc_tfNomeExcl.gridwidth = 3;
		gbc_tfNomeExcl.insets = new Insets(0, 0, 5, 0);
		gbc_tfNomeExcl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNomeExcl.gridx = 1;
		gbc_tfNomeExcl.gridy = 1;
		panelDadosExcl.add(tfNomeExcl, gbc_tfNomeExcl);

		lblSenhaExcl = new JLabel("Senha: ");
		lblSenhaExcl.setForeground(Color.WHITE);
		GridBagConstraints gbc_lblSenhaExcl = new GridBagConstraints();
		gbc_lblSenhaExcl.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSenhaExcl.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenhaExcl.gridx = 0;
		gbc_lblSenhaExcl.gridy = 2;
		panelDadosExcl.add(lblSenhaExcl, gbc_lblSenhaExcl);

		tfSenhaExcl = new JPasswordField();
		tfSenhaExcl.setColumns(15);
		tfSenhaExcl.setDocument(new controletfSenha(16));
		tfSenhaExcl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarExcl.doClick();
					arg0.consume();
					return;
				}
			}
		});
		GridBagConstraints gbc_tfSenhaExcl = new GridBagConstraints();
		gbc_tfSenhaExcl.insets = new Insets(0, 0, 5, 5);
		gbc_tfSenhaExcl.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSenhaExcl.gridx = 1;
		gbc_tfSenhaExcl.gridy = 2;
		panelDadosExcl.add(tfSenhaExcl, gbc_tfSenhaExcl);

		panelFotoExcl = new JPanel();
		panelFotoExcl.setOpaque(false);
		panelExcluir.add(panelFotoExcl, "cell 3 0,alignx right,aligny top");
		panelFotoExcl.setLayout(new MigLayout("", "[150.00px,fill][30.00px,fill][10px,right][77px,grow,fill]",
				"[23px][23px][162px,fill][23px,grow,bottom]"));

		painelImagemExlc = new JPanel();
		painelImagemExlc.setBorder(new LineBorder(new Color(255, 255, 255), 2, true));
		painelImagemExlc.setOpaque(false);
		panelFotoExcl.add(painelImagemExlc, "cell 0 0 2 3,grow");
		painelImagemExlc.setLayout(new CardLayout(0, 0));

		lblImagemExcl = new JLabel();
		lblImagemExcl.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagemExcl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					switch (e.getButton()) {
					case MouseEvent.BUTTON1:
						btnLimparTudo.doClick();
						lblImagemExcl.setIcon(null);
						e.consume();
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
		painelImagemExlc.add(lblImagemExcl, "name_68936448706800");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.setOpaque(false);
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setBackground(Color.WHITE);

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExcluir_actionPerformed(e);
			}
		});

		btnExcluir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnExcluir.doClick();
					e.consume();
					return;
				}
			}
		});
		panelFotoExcl.add(btnExcluir, "cell 2 0 2 1,growx,aligny top");

		btnBuscarExcl = new JButton("Buscar");
		btnBuscarExcl.setOpaque(false);
		btnBuscarExcl.setForeground(Color.WHITE);
		btnBuscarExcl.setBackground(Color.WHITE);

		btnBuscarExcl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnBuscarExcl_actionPerformed(e);
			}
		});

		btnBuscarExcl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnBuscarExcl.doClick();
					e.consume();
					return;
				}
			}
		});
		panelFotoExcl.add(btnBuscarExcl, "cell 2 1 2 1,growx,aligny top");

		txtpnObsParaAtivar = new JTextPane();
		panelExcluir.add(txtpnObsParaAtivar, "cell 3 1,alignx right,aligny top");
		txtpnObsParaAtivar.setText(
				" Obs: para ativar o bot\u00E3o \"Excluir\" busque o usu\u00E1rio registrado pelo seu nome ou senha registrada.");
		txtpnObsParaAtivar.setOpaque(false);
		txtpnObsParaAtivar.setForeground(Color.WHITE);
		txtpnObsParaAtivar.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtpnObsParaAtivar.setEditable(false);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel, "cell 0 0 2 1,growx,aligny top");
		panel.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_2.setOpaque(false);
		panel.add(panel_2, BorderLayout.WEST);

		lblUsurio = new JLabel();
		panel_2.add(lblUsurio);
		lblUsurio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsurio.setForeground(Color.WHITE);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new MigLayout("", "[91px][91px][139px]", "[23px]"));

		btnLimparTudo = new JButton("LImpar tudo");
		btnLimparTudo.setOpaque(false);
		btnLimparTudo.setForeground(Color.WHITE);
		btnLimparTudo.setBackground(Color.WHITE);
		panel_1.add(btnLimparTudo, "cell 0 0");

		btnLimparTudo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				limparBean();

				apagar(tfNome, cbAdm, tfSenha, img, lblImagem, btnBuscarFot);

				apagar(tfNomeAlt, cbAdmAlt, tfSenhaAlt, img, lblImagemAlt, btnBuscarFotAlt);

				tfNomeExcl.setText(null);
				tfSenhaExcl.setText(null);
				lblImagemExcl.setIcon(null);
				btnExcluir.setEnabled(false);
				btnRegistrar.setEnabled(false);
				btnAlterar.setEnabled(false);
			}
		});

		btnLimparTudo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLimparTudo.doClick();
					e.consume();
					return;
				}
			}
		});

		btnDeslogar = new JButton("Deslogar-se");
		panel_1.add(btnDeslogar, "cell 1 0,alignx left,aligny top");
		btnDeslogar.setBackground(Color.WHITE);
		btnDeslogar.setForeground(Color.WHITE);
		btnDeslogar.setOpaque(false);
		esconderDicas();
		tglbtnDesativarDicas = new JToggleButton("Ativar/Desativar dicas");
		tglbtnDesativarDicas.setSelected(true);
		panel_1.add(tglbtnDesativarDicas, "cell 2 0,alignx left,aligny top");
		tglbtnDesativarDicas.setBackground(Color.WHITE);
		tglbtnDesativarDicas.setForeground(Color.WHITE);
		tglbtnDesativarDicas.setOpaque(false);
		tglbtnDesativarDicas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (tglbtnDesativarDicas.isSelected()) {
					esconderDicas();
				} else {
					mostrarDicas();
				}
			}
		});
		tglbtnDesativarDicas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tglbtnDesativarDicas.doClick();
					e.consume();
					return;
				}
			}
		});

		btnDeslogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeslogar_actionPerformed(e);
			}
		});

		btnDeslogar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnDeslogar.doClick();
					e.consume();
					return;
				}
			}
		});
	}

	// meus métodos

	protected void dadosLogin() {
		loginNome = ConexaoBEAN.getNome();
		lblUsurio.setText("Usu\u00E1rio: " + loginNome);
	}

	private File selecionarImagem() {

		fileChooser = null;
		img = null;
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos JPG & PNG",
				new String[] { "jpg", "png" });
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

	private void btnBuscar_actionPerformed(ActionEvent arg0) {

		selecionarImagem();

		if (img == null) {
			return;
		} else {

			try {
				lblImagem.setIcon(null);
				lblImagem = ManipularImagem.exibiImagemLabel(img, lblImagem, painelImagem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			btnRegistrar.setEnabled(true);
			btnRegistrar.requestFocus();
		}

	}

	private void btnBuscarFotAlt_actionPerformed(ActionEvent arg0) {

		selecionarImagem();

		if (img == null) {
			return;
		} else {
			try {
				lblImagemAlt.setIcon(null);
				lblImagemAlt = ManipularImagem.exibiImagemLabel(img, lblImagemAlt, painelImagemAlt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			btnAlterar.setEnabled(true);
			btnAlterar.requestFocus();
		}

	}

	protected void btnExcluir_actionPerformed(ActionEvent e) {

		if (!ConexaoBEAN.getNome().equals(loginNome)) {

			limparBean();

			dao.conectar();

			if (dao.conectou == true) {

				if (String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && tfNomeExcl.getText().isEmpty()) {

					btnExcluir.setEnabled(false);
					tfNomeExcl.setText(null);
					tfSenhaExcl.requestFocus();
					dao.fecharCon();
					JOptionPane.showMessageDialog(null,
							"A senha ou nome do usuário a ser excluído do sistema têm de ser preênchido!", "Erro!",
							JOptionPane.ERROR_MESSAGE);
					return;

				} else if (String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && !tfNomeExcl.getText().isEmpty()) {

					int i = JOptionPane.showConfirmDialog(null, "Descadastrar este usuário?", "Confirmar descadastro!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (i == JOptionPane.YES_OPTION) {

						bean.setNome(tfNomeExcl.getText());
						dao.excluirNome(bean);
						dao.fecharCon();
						btnLimparTudo.doClick();
						JOptionPane.showMessageDialog(null, "Usuário descadastrado!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						return;

					} else {

						dao.fecharCon();
						return;
					}

				} else if (!String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && tfNomeExcl.getText().isEmpty()) {

					int i = JOptionPane.showConfirmDialog(null, "Descadastrar este usuário?", "Confirmar descadastro!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (i == JOptionPane.YES_OPTION) {

						bean.setSenhaHash(CriptMD5.md5(String.valueOf(tfSenhaExcl.getPassword()).trim().substring(0, 3)
								+ String.valueOf(tfSenhaExcl.getPassword()).trim()
								+ String.valueOf(tfSenhaExcl.getPassword()).trim().substring(3)));
						dao.excluir(bean);
						dao.fecharCon();
						btnLimparTudo.doClick();
						JOptionPane.showMessageDialog(null, "Usuário descadastrado!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						return;

					}

				} else if (!String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && !tfNomeExcl.getText().isEmpty()) {

					int i = JOptionPane.showConfirmDialog(null, "Descadastrar este usuário?", "Confirmar descadastro!",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (i == JOptionPane.YES_OPTION) {

						bean.setSenhaHash(CriptMD5.md5(String.valueOf(tfSenhaExcl.getPassword()).trim().substring(0, 3)
								+ String.valueOf(tfSenhaExcl.getPassword()).trim()
								+ String.valueOf(tfSenhaExcl.getPassword()).trim().substring(3)));
						dao.excluir(bean);
						dao.fecharCon();
						btnLimparTudo.doClick();
						JOptionPane.showMessageDialog(null, "Usuário descadastrado!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						return;

					}

				}
			} else {

				btnLimparTudo.doClick();
				JOptionPane.showMessageDialog(null, "Nao foi possível conectar ao Banco de Dados.", "Erro!",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

		} else {

			btnLimparTudo.doClick();
			JOptionPane.showMessageDialog(null, "Você não pode excluir seu próprio usuário!", "Atenção!",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	protected void btnBuscarAlt_actionPerformed(ActionEvent e) {

		dao.conectar();

		if (dao.conectou == true) {

			if (String.valueOf(tfSenhaAlt.getPassword()).isEmpty() && tfNomeAlt.getText().isEmpty()) {

				tfNomeAlt.requestFocus();
				JOptionPane.showMessageDialog(null, "Preencha a senha ou o nome do membro corretamente!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);

			} else if (!String.valueOf(tfSenhaAlt.getPassword()).isEmpty() && tfNomeAlt.getText().isEmpty()) {

				if (String.valueOf(tfSenhaAlt.getPassword()).length() < 4) {

					tfSenhaAlt.setText(null);
					tfSenhaAlt.requestFocus();
					JOptionPane.showMessageDialog(null, "A senha precisa ter no mínimo 4 caracters!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
					return;

				} else {

					limparBean();
					bean.setSenhaHash(CriptMD5.md5(String.valueOf(tfSenhaAlt.getPassword()).trim().substring(0, 3)
							+ String.valueOf(tfSenhaAlt.getPassword()).trim()
							+ String.valueOf(tfSenhaAlt.getPassword()).trim().substring(3)));

					if (dao.buscarSenha(bean) == true) {
						try {
							buscarDados();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						btnAlterar.setEnabled(true);
						return;

					} else {

						limparBean();
						dao.fecharCon();
						tfSenhaAlt.setText(null);
						tfSenhaAlt.requestFocus();
						JOptionPane.showMessageDialog(null, "Este usuário não está cadastrado!", "Erro!",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else if (!tfNomeAlt.getText().isEmpty() && String.valueOf(tfSenhaAlt.getPassword()).isEmpty()) {

				limparBean();
				bean.setNome(tfNomeAlt.getText());

				if (dao.buscarNome(bean) == true) {

					try {
						buscarDados();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btnAlterar.setEnabled(true);
					return;

				} else {
					limparBean();
					dao.fecharCon();
					tfNomeAlt.setText(null);
					tfNomeAlt.requestFocus();
					JOptionPane.showMessageDialog(null, "Este usuário não está cadastrado!", "Erro!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (!tfNomeAlt.getText().trim().isEmpty()
					&& !String.valueOf(tfSenhaAlt.getPassword()).trim().isEmpty()) {

				btnLimparTudo.doClick();
				tfNomeAlt.requestFocus();
				dao.fecharCon();
				JOptionPane.showMessageDialog(null, "Preencha somente ou campo Senha ou o campo Nome!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

		} else {
			btnLimparTudo.doClick();
			JOptionPane.showMessageDialog(null, "Nao foi possível conectar ao Banco de Dados.", "Erro!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	protected void btnBuscarExcl_actionPerformed(ActionEvent e) {

		dao.conectar();

		if (dao.conectou == true) {

			if (String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && tfNomeExcl.getText().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Preencha a senha ou o nome do usuário corretamente!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);

			} else if (!String.valueOf(tfSenhaExcl.getPassword()).isEmpty() && tfNomeExcl.getText().isEmpty()) {

				if (String.valueOf(tfSenhaExcl.getPassword()).length() < 4) {

					tfSenhaExcl.setText(null);
					tfSenhaExcl.requestFocus();
					JOptionPane.showMessageDialog(null, "Preencha a senha corretamente!", "Atenção!",
							JOptionPane.WARNING_MESSAGE);
					return;

				} else {

					limparBean();
					bean.setSenhaHash(CriptMD5.md5(String.valueOf(tfSenhaExcl.getPassword()).trim().substring(0, 3)
							+ String.valueOf(tfSenhaExcl.getPassword()).trim()
							+ String.valueOf(tfSenhaExcl.getPassword()).trim().substring(3)));

					if (dao.buscarSenha(bean) == true) {
						try {
							buscarDadosExcl();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						btnExcluir.setEnabled(true);
						btnExcluir.requestFocus();
						return;

					} else {
						limparBean();
						dao.fecharCon();
						tfSenhaExcl.setText(null);
						tfSenhaExcl.requestFocus();
						JOptionPane.showMessageDialog(null, "Este usuário não está cadastrado!.", "Erro!",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else if (!tfNomeExcl.getText().isEmpty() && String.valueOf(tfSenhaExcl.getPassword()).isEmpty()) {

				limparBean();
				bean.setNome(tfNomeExcl.getText());

				if (dao.buscarNome(bean) == true) {

					try {
						buscarDadosExcl();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btnExcluir.setEnabled(true);
					btnExcluir.requestFocus();
					return;

				} else {

					limparBean();
					dao.fecharCon();
					tfNomeExcl.setText(null);
					tfNomeExcl.requestFocus();
					JOptionPane.showMessageDialog(null, "Este membro não está cadastrado!.", "Erro!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (!tfNomeExcl.getText().trim().isEmpty()
					&& !String.valueOf(tfSenhaExcl.getPassword()).trim().isEmpty()) {

				limparBean();
				dao.fecharCon();
				tfNomeExcl.setText(null);
				tfSenhaExcl.setText(null);
				tfNomeExcl.requestFocus();
				JOptionPane.showMessageDialog(null, "Preencha somento o campo senha ou o campo Nome!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

		} else {

			btnLimparTudo.doClick();
			JOptionPane.showMessageDialog(null, "Nao foi possível conectar ao Banco de Dados.", "Erro!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void buscarDadosExcl() throws IOException {

		tfNomeExcl.setText(ConexaoBEAN.getNome());
		lblImagemExcl = ManipularImagem.exibiImagemLabel(bean.getImagem(), lblImagemExcl, painelImagemExlc);
		img = bean.getImagem();
	}

	private void buscarDados() throws IOException {

		tfNomeAlt.setText(ConexaoBEAN.getNome());
		cbAdmAlt.setSelectedItem(bean.getAdm());
		lblImagemAlt = ManipularImagem.exibiImagemLabel(bean.getImagem(), lblImagemAlt, painelImagemAlt);
		btnAlterar.requestFocus();
		img = bean.getImagem();
		temp = bean.getSenhaHash();

	}

	private void btnRegistrar_actionPerformed(ActionEvent e) {

		dao.conectar();

		if (dao.conectou == true) {

			if (String.valueOf(tfSenha.getPassword()).isEmpty()) {

				btnRegistrar.setEnabled(false);
				JOptionPane.showMessageDialog(null,
						"A senha do usuário a ser cadastrado no sistema têm de ser preênchida!", "Erro!",
						JOptionPane.ERROR_MESSAGE);
				btnLimparTudo.doClick();
				tfSenha.requestFocus();
				dao.fecharCon();
				return;

			} else if (tfNome.getText().isEmpty()) {

				btnRegistrar.setEnabled(false);
				JOptionPane.showMessageDialog(null,
						"O nome do usuário a ser cadastrado no sistema têm de ser preênchido!", "Erro!",
						JOptionPane.ERROR_MESSAGE);
				btnLimparTudo.doClick();
				tfNome.requestFocus();
				dao.fecharCon();
				return;

			} else {

				int i = JOptionPane.showConfirmDialog(null, "Registar este novo usuário?", "Confirmar registro!",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (i == JOptionPane.YES_OPTION) {

					limparBean();

					try {
						if (inserirBean(tfNome, cbAdm, tfSenha, img, lblImagem, btnBuscarFot) == true) {

							if (String.valueOf(tfSenha.getPassword()).length() < 4) {

								tfSenha.setText(null);
								tfSenha.requestFocus();
								JOptionPane.showMessageDialog(null, "A senha precisa ter no mínimo 4 caracters!",
										"Atenção!", JOptionPane.WARNING_MESSAGE);
								return;

							} else {

								if (dao.verificarUsuario(bean, "nome") == true) {

									dao.fecharCon();
									limparBean();
									JOptionPane.showMessageDialog(null, "Nome já cadastrado!.", "Erro!",
											JOptionPane.ERROR_MESSAGE);
									tfNome.requestFocus();

									return;

								} else if (dao.verificarUsuario(bean, "digital") == true) {

									dao.fecharCon();
									limparBean();
									JOptionPane.showMessageDialog(null, "Digital já cadastrado!.", "Erro!",
											JOptionPane.ERROR_MESSAGE);
									btnBuscarFot.requestFocus();

									return;

								} else if (dao.verificarUsuario(bean, "senha") == true) {

									dao.fecharCon();
									limparBean();
									JOptionPane.showMessageDialog(null, "Senha já cadastrado!.", "Erro!",
											JOptionPane.ERROR_MESSAGE);
									tfSenha.requestFocus();

									return;

								} else {

									if (dao.inserir(bean) == true) {

										btnLimparTudo.doClick();
										dao.fecharCon();
										JOptionPane.showMessageDialog(null, "Dados registrados!", "Sucesso!",
												JOptionPane.INFORMATION_MESSAGE);
										return;

									} else {
										JOptionPane.showMessageDialog(null,
												"Não foi possível cadastrar o usuário! Verifique sua conexão com o Banco de Dados.",
												"Erro!", JOptionPane.ERROR_MESSAGE);
										return;
									}

								}
							}
						} else {

							limparBean();
							dao.fecharCon();
							JOptionPane.showMessageDialog(null,
									"Erro ao cadastrar usuário! Verifique a conexão com o Banco de Dados", "Erro!",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (HeadlessException | ParseException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {

					limparBean();
					dao.fecharCon();
					return;
				}

			}

		} else {

			limparBean();
			dao.fecharCon();
			JOptionPane.showMessageDialog(null, "Nao foi possivel conectar ao Banco de Dados.", "Erro!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private boolean inserirBean(JFormattedTextField tfNome, JComboBox<Object> cbAdm, JPasswordField tfSenha, byte[] img,
			JLabel lblImagem, JButton btnBuscar) throws ParseException, IOException {

		if (!tfNome.getText().isEmpty() && lblImagem.getIcon() != null) {

			String template = criarTemplate(Preprocessamento.pré_processamento(img));

			if (img.equals(null)) {

				JOptionPane.showMessageDialog(null, "Selecione a digital do usuário!", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				lblImagem.equals(null);
				btnBuscar.requestFocus();
				return false;

			} else {

				if (!tfSenha.getPassword().equals(null)) {
					bean.setSenhaHash(CriptMD5.md5(String.valueOf(tfSenha.getPassword()).trim().substring(0, 3)
							+ String.valueOf(tfSenha.getPassword()).trim()
							+ String.valueOf(tfSenha.getPassword()).trim().substring(3)));
				}
				bean.setImagem(img);
				bean.setNome(tfNome.getText().trim());
				bean.setAdm(cbAdm.getSelectedItem().toString());
				bean.setTemplate(template);
				return true;
			}

		} else {

			JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "Atenção!",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	private void btnDeslogar_actionPerformed(ActionEvent e) {

		int i2 = JOptionPane.showConfirmDialog(null, "Fazer logoff?", "Deslogar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (i2 == JOptionPane.YES_OPTION) {

			btnLimparTudo.doClick();

			try {
				if (dao.conectar().isReadOnly()) {
					dao.fecharCon();
				}

			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Não é possível deslogar!", "Erro!", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return;
			}
			windowClosing();
		} else
			return;
	}

	private void btnAlterar_actionPerformed(ActionEvent e) {

		dao.conectar();

		if (dao.conectou == true) {

			System.out.println(temp);
			limparBean();

			int i = JOptionPane.showConfirmDialog(null, "Atualizar usuário?", "Confirmar alteração!",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (i == JOptionPane.YES_OPTION) {

				try {

					if (!String.valueOf(tfSenhaAlt.getPassword()).isEmpty() && !tfNomeAlt.getText().isEmpty()) {

						if (String.valueOf(tfSenhaAlt.getPassword()).length() < 4) {

							tfSenhaAlt.setText(null);
							tfSenhaAlt.requestFocus();
							JOptionPane.showMessageDialog(null, "A senha precisa ter no mínimo 4 caracters!",
									"Atenção!", JOptionPane.WARNING_MESSAGE);
							return;

						} else {

							try {
								if (inserirBean(tfNomeAlt, cbAdmAlt, tfSenhaAlt, img, lblImagemAlt,
										btnBuscarFotAlt) == true) {

									if (!bean.getSenhaHash().equals(temp)) {

										if (dao.verificarUsuario(bean, "senha") == true) {

											dao.fecharCon();
											limparBean();
											JOptionPane.showMessageDialog(null, "Senha já cadastrado!.", "Erro!",
													JOptionPane.ERROR_MESSAGE);
											tfSenhaAlt.requestFocus();
											return;

										} else {

											if (dao.alterar(bean, temp) == true) {

												btnLimparTudo.doClick();
												dao.fecharCon();
												temp = null;
												JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!",
														"Sucesso!", JOptionPane.INFORMATION_MESSAGE);
												return;

											} else {

												limparBean();
												dao.fecharCon();
												JOptionPane.showMessageDialog(null,
														"Erro ao atualizar usuário! Verifique a conexão com o Banco de Dados",
														"Erro!", JOptionPane.ERROR_MESSAGE);
												return;
											}

										}

									} else {

										if (dao.alterar(bean, temp) == true) {

											btnLimparTudo.doClick();
											dao.fecharCon();
											temp = null;
											JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!",
													"Sucesso!", JOptionPane.INFORMATION_MESSAGE);
											return;

										} else {

											limparBean();
											dao.fecharCon();
											JOptionPane.showMessageDialog(null,
													"Erro ao atualizar usuário! Verifique a conexão com o Banco de Dados",
													"Erro!", JOptionPane.ERROR_MESSAGE);
											return;
										}
									}

								} else {

									limparBean();
									dao.fecharCon();
									JOptionPane.showMessageDialog(null,
											"Nao foi possivel enviar as informações ao Banco de Dados.", "Erro!",
											JOptionPane.ERROR_MESSAGE);
									return;
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					} else {

						JOptionPane.showMessageDialog(null,
								"Se não sabe o nome ou a senha do usuário, por favor, dite um(a) novo(a) nome/senha.",
								"Atenção!", JOptionPane.WARNING_MESSAGE);
						tfSenhaAlt.requestFocus();
						return;
					}
				} catch (HeadlessException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {

				limparBean();
				dao.fecharCon();
				return;
			}

		} else {

			limparBean();
			dao.fecharCon();
			JOptionPane.showMessageDialog(null, "Nao foi possivel conectar ao Banco de Dados.", "Erro!",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	private void windowClosing() {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login l = new login();
					l.setVisible(true);
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void esconderDicas() {
		txtpnParaAtivarO_1Alt.setText(null);
		txtpnParaAtivarO_1.setText(null);
		lblObsOsCamposAlt.setText(null);
		lblObsOsCampos.setText("");
		txtpnObsParaAtivar.setText(null);
	}

	private void mostrarDicas() {
		txtpnParaAtivarO_1Alt.setText(dica6);
		txtpnParaAtivarO_1.setText(dica1);
		lblObsOsCamposAlt.setText(dica3);
		lblObsOsCampos.setText(dica3);
		txtpnObsParaAtivar.setText(dica4);
	}

	private static void apagar(JFormattedTextField tfNome, JComboBox<Object> cbEstCivil, JPasswordField tfSenha,
			byte[] img, JLabel lblImagem, JButton btnBuscar) {

		tfNome.setText(null);
		tfSenha.setText(null);
		cbEstCivil.setSelectedIndex(0);
		img = null;
		fileChooser = null;
		lblImagem.setIcon(null);
		btnBuscar.requestFocus();
	}

	private void limparBean() {

		bean.setImagem(null);
		bean.setNome(null);
		bean.setSenhaHash(null);
		bean.setTemplate(null);
		bean.setAdm(null);
		ConexaoBEAN.setId(0);
	}

	private String criarTemplate(byte[] image) {

		FingerprintTemplate template = new FingerprintTemplate().dpi(500).create(image);
		String json = template.serialize();
		return json;
	}
}
