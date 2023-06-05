package trabalho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class ConsultaUltimaOrientacao extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoGrupo;
    private JTextArea resultadoArea;

    public ConsultaUltimaOrientacao() {
        super("Consulta Última Orientação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel grupoLabel = new JLabel("Grupo:");
        campoGrupo = new JTextField(10);

        JLabel resultadoLabel = new JLabel("Última Orientação:");
        resultadoArea = new JTextArea(5, 10);
        resultadoArea.setEditable(false);
        JScrollPane resultadoScrollPane = new JScrollPane(resultadoArea);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String grupo = campoGrupo.getText();

                if (grupo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha o campo do grupo", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    String ultimaOrientacao = consultarUltimaOrientacao(grupo);
                    if (ultimaOrientacao != null) {
                        resultadoArea.setText(ultimaOrientacao);
                    } else {
                        resultadoArea.setText("Nenhuma orientação encontrada para o grupo informado");
                    }
                }
            }
        });

        panel.add(grupoLabel);
        panel.add(campoGrupo);
        panel.add(resultadoLabel);
        panel.add(resultadoScrollPane);
        panel.add(new JLabel());
        panel.add(consultarButton);

        add(panel);
    }

    private String consultarUltimaOrientacao(String grupo) {
        String filePath = "orientacoes.csv";
        String ultimaOrientacao = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2 && values[0].equals(grupo)) {
                    ultimaOrientacao = values[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ultimaOrientacao;
    }
}


