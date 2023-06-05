package trabalho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

class Aluno {

	private String ra;
	private String nome;

	public Aluno(String ra, String nome) {
		this.ra = ra;
		this.nome = nome;
	}

	public String getRA() {
		return ra;
	}

	public String getNome() {
		return nome;
	}
}

class JanelaAluno extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoRa;
	private JTextField campoNome;

	public JanelaAluno() {
		setTitle("Cadastro de Aluno");
		setSize(600, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		campoRa = new JTextField(10);
		campoNome = new JTextField(20);
		JButton cadastrarButton = new JButton("Cadastrar");

		cadastrarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarAluno();
			}
		});

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.add(new JLabel("RA:"));
		panel.add(campoRa);
		panel.add(new JLabel("Nome:"));
		panel.add(campoNome);
		panel.add(cadastrarButton);

		add(panel);
	}

	private void cadastrarAluno() {
		String ra = campoRa.getText();
		String nome = campoNome.getText();

		if (!ra.isEmpty() && !nome.isEmpty()) {
			if (!verificarAlunoExistente(ra, nome)) {
				Aluno aluno = new Aluno(ra, nome);
				salvarAluno(aluno);
				JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "RA ou nome já cadastrado!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
		}
	}

	private boolean verificarAlunoExistente(String ra, String nome) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("alunos.csv"));
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(",");
				if (dados.length > 1 && dados[0].equals(ra) && dados[1].equals(nome)) {
					reader.close();
					return true;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void salvarAluno(Aluno aluno) {
		try {
			FileWriter writer = new FileWriter("alunos.csv", true);
			writer.append(aluno.getRA() + "," + aluno.getNome() + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void limparCampos() {
		campoRa.setText("");
		campoNome.setText("");
	}
}
