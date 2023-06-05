package trabalho;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Trabalho de Conclusão de Curso",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[]{"Cadastrar Aluno", "Cadastrar Grupo", "Consultar Grupo", "Inserir Orientação", "Consultar Última Orientação"}, null);

                if (opcao == 0) {
                    JanelaAluno JanelaAluno = new JanelaAluno();
                    JanelaAluno.setVisible(true);
                } else if (opcao == 1) {
                    janelaGrupo janelaGrupo = new janelaGrupo();
                    janelaGrupo.setVisible(true);
                } else if (opcao == 2) {
                    JanelaConsultaGrupos consultaForm = new JanelaConsultaGrupos();
                    consultaForm.setVisible(true);
                } else if (opcao == 3) {
                    JanelaOrientacao form = new JanelaOrientacao();
                    form.setVisible(true);
                } else if (opcao == 4) {
                ConsultaUltimaOrientacao form = new ConsultaUltimaOrientacao();
                form.setVisible(true);
            }
        };
    });
}}
