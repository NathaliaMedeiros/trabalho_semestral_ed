package trabalho;import java.awt.GridLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

class JanelaOrientacao extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoGrupo;
    private JTextArea orientacaoArea;

    public JanelaOrientacao() {
        super("Inserir Orientação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel grupoLabel = new JLabel("Grupo:");
        campoGrupo = new JTextField(10);

        JLabel orientacaoLabel = new JLabel("Orientação:");
        orientacaoArea = new JTextArea(5, 10);
        JScrollPane orientacaoScrollPane = new JScrollPane(orientacaoArea);

        JButton inserirButton = new JButton("Inserir");
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String grupo = campoGrupo.getText();
                String orientacao = orientacaoArea.getText();

                if (grupo.isEmpty() || orientacao.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean sucesso = inserirOrientacao(grupo, orientacao);
                    if (sucesso) {
                        JOptionPane.showMessageDialog(null, "Orientação inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Falha ao inserir a orientação", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                    // Limpar os campos após inserir
                    campoGrupo.setText("");
                    orientacaoArea.setText("");
                }
            }
        });

        panel.add(grupoLabel);
        panel.add(campoGrupo);
        panel.add(orientacaoLabel);
        panel.add(orientacaoScrollPane);
        panel.add(new JLabel());
        panel.add(inserirButton);

        add(panel);
    }

    private boolean inserirOrientacao(String grupo, String orientacao) {
        String filePath = "orientacoes.csv";

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.append(grupo + "," + orientacao + "\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}


