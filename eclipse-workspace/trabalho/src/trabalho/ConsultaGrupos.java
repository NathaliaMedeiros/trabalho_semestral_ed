package trabalho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JanelaConsultaGrupos extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoCodigo;
    private JTextArea resultadoArea;

    private List<Grupo> grupos;

    public JanelaConsultaGrupos() {
        super("Consulta de Grupos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        grupos = new ArrayList<>();
        lerGruposCSV("grupos.csv");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel codigoLabel = new JLabel("Código do Grupo:");
        campoCodigo = new JTextField(10);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText();

                if (codigo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, informe o código do grupo", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    Grupo grupo = buscarGrupo(codigo);
                    if (grupo != null) {
                        StringBuilder resultadoBuilder = new StringBuilder();
                        resultadoBuilder.append("Código: ").append(grupo.getCodigo()).append("\n");
                        resultadoBuilder.append("Área: ").append(grupo.getArea()).append("\n");
                        resultadoBuilder.append("Tema: ").append(grupo.getTema()).append("\n");
                        resultadoBuilder.append("Alunos: ").append(String.join(", ", grupo.getAlunos()));
                        resultadoArea.setText(resultadoBuilder.toString());
                    } else {
                        resultadoArea.setText("Grupo não encontrado");
                    }
                }
            }
        });

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        JScrollPane resultadoScrollPane = new JScrollPane(resultadoArea);

        panel.add(codigoLabel);
        panel.add(campoCodigo);
        panel.add(consultarButton);
        panel.add(resultadoScrollPane);

        add(panel);
    }

    private void lerGruposCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 4) {
                    String codigo = values[0];
                    String area = values[1];
                    String tema = values[2];
                    List<String> alunos = new ArrayList<>();
                    for (int i = 3; i < values.length; i++) {
                        alunos.add(values[i]);
                    }
                    Grupo grupo = new Grupo(codigo, area, tema, alunos);
                    grupos.add(grupo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Grupo buscarGrupo(String codigo) {
        for (Grupo grupo : grupos) {
            if (grupo.getCodigo().equals(codigo)) {
                return grupo;
            }
        }
        return null;
    }
}
