import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import components.Button;
import components.TextField;
import components.TextLabel;

public class AddJob extends Screen {
    Dimension textFieldSize = new Dimension(200, 30);
    Color colorField = new Color(199, 194, 194);

    private final CompanyController companyController = new CompanyController();
    private final JobController jobController = new JobController();
    private final EmployerController employerController = new EmployerController();
    private final JPanel panel = new JPanel();
    private final TextField nameField = new TextField();
    private final TextField occupationAreaField = new TextField();
    private final TextField salaryField = new TextField();
    private final TextField workloadField = new TextField();
    private final TextField requirementsField = new TextField();
    private final Button addButton = new Button("Criar");
    private final JComboBox<String> modalityField = new JComboBox<String>(JobController.getModalities());
    private final JComboBox<Object> companyField = new JComboBox<Object>(companyController.getCompanyOwners().toArray());

    public AddJob() {
        super("Adicionar Vaga");

        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.modalityField.setPreferredSize(this.textFieldSize);
        this.modalityField.setBackground(colorField);
        this.modalityField.setBorder(BorderFactory.createEmptyBorder());

        this.companyField.setPreferredSize(new Dimension(300, 30));
        this.companyField.setBackground(colorField);
        this.companyField.setBorder(BorderFactory.createEmptyBorder());

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new TextLabel("*Nome:"));
        namePanel.add(this.nameField);

        JPanel occupationAreaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        occupationAreaPanel.add(new TextLabel("*Area de Ocupacao:"));
        occupationAreaPanel.add(this.occupationAreaField);

        JPanel salaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        salaryPanel.add(new TextLabel("*Salario (em reais):"));
        salaryPanel.add(this.salaryField);

        JPanel modalityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modalityPanel.add(new TextLabel("*Modalidade:"));
        modalityPanel.add(this.modalityField);

        JPanel workloadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        workloadPanel.add(new TextLabel("*Carga horaria semanal (em horas):"));
        workloadPanel.add(this.workloadField);

        JPanel requirementsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        requirementsPanel.add(new TextLabel("Requerimentos (separe por virgula):"));
        requirementsPanel.add(this.requirementsField);

        JPanel companyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        companyPanel.add(new TextLabel("*Empresa:"));
        companyPanel.add(this.companyField);

        JPanel buttonPanel = new JPanel();
        this.addButton.addActionListener(this::addJob);
        buttonPanel.add(this.addButton);

        this.panel.add(namePanel);
        this.panel.add(occupationAreaPanel);
        this.panel.add(salaryPanel);
        this.panel.add(modalityPanel);
        this.panel.add(workloadPanel);
        this.panel.add(requirementsPanel);
        this.panel.add(companyPanel);
        this.panel.add(buttonPanel);

        this.add(panel);
        this.display();
    }

    private void addJob(ActionEvent action) {
        String name = this.nameField.getText().trim();
        String occupationArea = this.occupationAreaField.getText().trim();
        String salarystr = this.salaryField.getText().trim();
        String modality = this.modalityField.getSelectedItem().toString();
        String workloadstr = this.workloadField.getText().trim();
        String requirements = this.requirementsField.getText().trim();
        String companystr = this.companyField.getSelectedItem().toString();

        if(name.isEmpty() || occupationArea.isEmpty() || salarystr.isEmpty() || workloadstr.isEmpty()) {
            this.displayWarning("Preencha todos os campos assinalados com o simbolo (*)!");
            return;
        }

        int salary, workload;

        try {
            salary = Integer.parseInt(salarystr);
        } catch(NumberFormatException error) {
            this.displayWarning("Insira um salario valido no formato de um numero inteiro!");
            this.salaryField.setText("");
            return;
        }

        try {
            workload = Integer.parseInt(workloadstr);
        } catch(NumberFormatException error) {
            this.displayWarning("Insira uma carga horaria no formato de um numero inteiro!");
            this.workloadField.setText("");
            return;
        }

        if(salary < 0 || workload < 0) {
            this.displayWarning("Proibido inserir valores negativos!");

            if(salary < 0)
                this.salaryField.setText("");

            if(workload < 0)
                this.workloadField.setText("");

            return;
        }

        Employer owner = employerController.getEmployerByUsername(companystr.substring(companystr.lastIndexOf("(") + 1, companystr.length() - 1));

        if(owner == null) {
            this.displayWarning("Ocorreu algum erro na hora de localizar o representante da empresa!");
            return;
        }

        Job job;

        if(requirements.isEmpty())
            job = jobController.createJob(name, occupationArea, salary, modality, workload);
        else
            job = jobController.createJob(name, occupationArea, salary, modality, workload, requirements);

        owner.getCompany().addJob(job);

        this.displayWarning("Vaga adicionada com sucesso!");
        this.clearFields();
    }

    private void clearFields() {
        this.nameField.setText("");
        this.occupationAreaField.setText("");
        this.salaryField.setText("");
        this.workloadField.setText("");
        this.requirementsField.setText("");
    }

    public static void main(String args[]) {
        Employer e1 = new Employer("thegm445", "445", "Gabriel Moura");
        Employer e2 = new Employer("caio-felipee", "12345", "Caio Felipe");
        Employer e3 = new Employer("thuzin-gameplays", "3243", "Arthur");
        Employer e4 = new Employer("anacompetidora12", "4jfd", "Ana");

        Company company = new Company("Coca Cola", "Comida", "fodase@gmail.com");
        company.setRepresentant(e1.getUsername());
        e1.setCompany(company);

        Data.getEmployers().put(e1.username, e1);
        Data.getEmployers().put(e2.username, e2);
        Data.getEmployers().put(e3.username, e3);
        Data.getEmployers().put(e4.username, e4);
        Data.getCompanies().add(company);

        new AddJob();
    }
}
