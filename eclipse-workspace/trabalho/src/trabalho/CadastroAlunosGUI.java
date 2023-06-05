package trabalho;

import javax.swing.SwingUtilities;

public class CadastroAlunosGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 	
                AlunoForm alunoForm = new AlunoForm();
                alunoForm.setVisible(true);
            }
        });
    }
}