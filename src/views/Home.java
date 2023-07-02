package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import views.components.Button;
import views.components.TextLabel;

/**
 * Classe que representa a tela inicial do sistema, onde o usuário pode escolher se deseja procurar um emprego ou contratar novos colaboradores.
 * @see Screen
 * @author Ana Clara, Arthur e Caio.
 * @since 2023.
 * @version 1.0
 */
public class Home extends Screen {
    Color colorField = new Color(199, 194, 194);
    Dimension textFieldSize = new Dimension(200, 30);

    private final Button employerButton = new Button("Empregador");
    private final Button employeeButton = new Button("Empregado");
    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private final JLabel pageText = new JLabel();

    /**
     * Método construtor responsável pela inicialização da tela inicial do sistema.
     */
    public Home() {
        super();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));

        this.pageText.setPreferredSize(this.textFieldSize);
        this.pageText.setBackground(this.colorField);
        this.pageText.setBorder(BorderFactory.createEmptyBorder());

        this.employeeButton.setPreferredSize(this.textFieldSize);
        this.employerButton.setPreferredSize(this.textFieldSize);

        JPanel mainTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainTextPanel.add(new TextLabel("Deseja um emprego ou contratar novos colaboradores? Comece já!"));

        JPanel buttonsPanel = new JPanel();
        this.employeeButton.addActionListener(this::chooseUserType);
        this.employerButton.addActionListener(this::chooseUserType);
        buttonsPanel.add(this.employeeButton, BorderLayout.PAGE_START);
        buttonsPanel.add(this.employerButton);

        this.panel.add(mainTextPanel);
        this.panel.add(buttonsPanel);

        this.add(panel);
        this.disableButton();
        this.display();
    }

    /**
     * Método responsável por alterar a tela inicial pela escolhida, "Empregado" ou "Empregador".
     * @param event Evento de clique.
     */
    private void chooseUserType(ActionEvent event) {
        if(event.getSource() == this.employeeButton) {
            new SearchJobs("employee");
        } else if(event.getSource() == this.employerButton) {
            new SearchJobs("employer");
        }

        this.dispose();
    }
}
