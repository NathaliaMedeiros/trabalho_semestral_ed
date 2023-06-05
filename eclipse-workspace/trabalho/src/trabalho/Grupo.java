package trabalho;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Grupo {
    private static Set<String> gruposCadastrados = new HashSet<>();
 
    private String codigo;
    private String area;
    private String tema;
    private List<String> alunos;

    public Grupo(String codigo, String area, String tema, List<String> alunos) {
        this.codigo = codigo;
        this.area = area;
        this.tema = tema;
        this.alunos = alunos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getArea() {
        return area;
    }

    public String getTema() {
        return tema;
    }

    public List<String> getAlunos() {
        return alunos;
    }

    public boolean cadastrar() {
        if (gruposCadastrados.contains(codigo)) {
            JOptionPane.showMessageDialog(null, "Grupo já cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (alunos.size() < 2 || alunos.size() > 4) {
            JOptionPane.showMessageDialog(null, "Número inválido de participantes. O grupo deve ter entre 2 e 4 participantes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            gruposCadastrados.add(codigo);
            return true;
        }
    }

    static boolean isAlunoCadastrado(String aluno) {
        String filePath = "alunos.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] alunoData = line.split(",");
                String nomeAluno = alunoData[0]; // Assumindo que o nome do aluno está na primeira coluna

                if (nomeAluno.equals(aluno)) {
                    return true; // Aluno encontrado
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Aluno não encontrado
    }
}

class janelaGrupo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField campoCodigo;
    private JTextField campoArea;
    private JTextField campoTema;
    private JTextArea alunosArea;

    public janelaGrupo() {
        super("Cadastro de Grupos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel codigoLabel = new JLabel("Código:");
        campoCodigo = new JTextField(10);

        JLabel areaLabel = new JLabel("Área:");
        campoArea = new JTextField(10);

        JLabel temaLabel = new JLabel("Tema:");
        campoTema = new JTextField(10);

        JLabel alunosLabel = new JLabel("Alunos (separados por vírgula):");
        alunosArea = new JTextArea(5, 10);
        JScrollPane alunosScrollPane = new JScrollPane(alunosArea);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = campoCodigo.getText();
                String area = campoArea.getText();
                String tema = campoTema.getText();
                String alunosText = alunosArea.getText();

                if (codigo.isEmpty() || area.isEmpty() || tema.isEmpty() || alunosText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    List<String> alunos = new ArrayList<>();
                    String[] alunosArray = alunosText.split(",");
                    for (String aluno : alunosArray) {
                        String alunoTrimmed = aluno.trim();
                        if (Grupo.isAlunoCadastrado(alunoTrimmed)) {
                            alunos.add(alunoTrimmed);
                        } else {
                            JOptionPane.showMessageDialog(null, "O aluno '" + alunoTrimmed + "' não está cadastrado. Cadastre-o primeiro antes de inseri-lo no grupo.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Grupo grupo = new Grupo(codigo, area, tema, alunos);
                    if (grupo.cadastrar()) {
                        salvarGrupoCSV(grupo);
                        JOptionPane.showMessageDialog(null, "Grupo cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }

                    // Limpar os campos após cadastrar
                    campoCodigo.setText("");
                    campoArea.setText("");
                    campoTema.setText("");
                    alunosArea.setText("");
                }
            }
        });

        panel.add(codigoLabel);
        panel.add(campoCodigo);
        panel.add(areaLabel);
        panel.add(campoArea);
        panel.add(temaLabel);
        panel.add(campoTema);
        panel.add(alunosLabel);
        panel.add(alunosScrollPane);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        add(panel);
    }

    private void salvarGrupoCSV(Grupo grupo) {
        String filePath = "grupos.csv";

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.append(grupo.getCodigo() + "," + grupo.getArea() + "," + grupo.getTema() + "," + String.join(",", grupo.getAlunos()) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




