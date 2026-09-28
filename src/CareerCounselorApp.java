// Imports
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class CareerCounselorApp 
{

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(() -> new LoginFrame());
	}

}

// Login Frame

class LoginFrame extends JFrame 
{
	// Variables
	private JTextField txtUser;
	private JPasswordField txtPass;
	
	// Constructor
	public LoginFrame()
	{
		setTitle("Login Page");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        txtUser = new JTextField();
        txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (user.equals("ADMIN") && pass.equals("PASSWORD")) 
            {
            	new StudentRecordFrame();
                dispose();
            } 
            else 
            {
            	JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
        setVisible(true);
	}
}

// Student Record
class StudentRecordFrame extends JFrame
{
	// Variables
	 private JTextField txtID, txtFirst, txtLast, txtCourse, txtAddress, txtEmail, txtCountry, txtCity;
	 private JTextField txtGPA, txtEnglish, txtMath, txtScience, txtSpanish, txtHistory, txtComputers;
	 private JTextField txtTotal, txtAverage;
	 private JTextArea txtTranscript;
	 private DefaultTableModel model;
	 
	 // Constructor
	 public StudentRecordFrame() 
	 {
		 setTitle("Student Record - Career Counselor");
	     setSize(1100, 600);
	     setDefaultCloseOperation(EXIT_ON_CLOSE);
	     setLocationRelativeTo(null);

	     JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

	     // Form Panel (2 columns: personal info + scores)
	     JPanel formPanel = new JPanel(new GridLayout(1, 2, 20, 20));

	     // Left Column (personal info)
	     JPanel leftPanel = new JPanel(new GridLayout(8, 2, 5, 5));
	     txtID = new JTextField();
	     txtFirst = new JTextField();
	     txtLast = new JTextField();
	     txtCourse = new JTextField();
	     txtAddress = new JTextField();
	     txtEmail = new JTextField();
	     txtCountry = new JTextField();
	     txtCity = new JTextField();

	     addLabelField(leftPanel, "Student ID:", txtID);
	     addLabelField(leftPanel, "First Name:", txtFirst);
	     addLabelField(leftPanel, "Last Name:", txtLast);
	     addLabelField(leftPanel, "Course Code:", txtCourse);
	     addLabelField(leftPanel, "Address:", txtAddress);
	     addLabelField(leftPanel, "Email:", txtEmail);
	     addLabelField(leftPanel, "Country:", txtCountry);
	     addLabelField(leftPanel, "City:", txtCity);

	     // Right Column (scores)
	     JPanel rightPanel = new JPanel(new GridLayout(8, 2, 5, 5));
	     txtGPA = new JTextField();
	     txtEnglish = new JTextField();
	     txtMath = new JTextField();
	     txtScience = new JTextField();
	     txtSpanish = new JTextField();
	     txtHistory = new JTextField();
	     txtComputers = new JTextField();
	     txtTotal = new JTextField();
	     txtAverage = new JTextField();

	     txtTotal.setEditable(false);
	     txtAverage.setEditable(false);
	     
	     addLabelField(rightPanel, "GPA:", txtGPA);
	     addLabelField(rightPanel, "English:", txtEnglish);
	     addLabelField(rightPanel, "Math:", txtMath);
	     addLabelField(rightPanel, "Science:", txtScience);
	     addLabelField(rightPanel, "Spanish:", txtSpanish);
	     addLabelField(rightPanel, "History:", txtHistory);
	     addLabelField(rightPanel, "Computers:", txtComputers);
	     addLabelField(rightPanel, "Total Score:", txtTotal);
	     addLabelField(rightPanel, "Average:", txtAverage);

	     formPanel.add(leftPanel);
	     formPanel.add(rightPanel);

	     // Transcript
	     txtTranscript = new JTextArea();
	     txtTranscript.setBorder(BorderFactory.createTitledBorder("Transcript"));

	     // Table
	     String[] cols = {"ID","First","Last","Course","Address","Email","Country","City", "GPA",
	                         "English","Math","Science","Spanish","History","Computers",
	                         "Total","Average"};
	     model = new DefaultTableModel(cols, 0);
	     JTable table = new JTable(model);
	     JScrollPane tableScroll = new JScrollPane(table);

	     // Buttons
	     JPanel btnPanel = new JPanel();
	     JButton btnAdd = new JButton("Add");
	     JButton btnEdit = new JButton("Edit");
	     JButton btnDelete = new JButton("Delete");
	     JButton btnReset = new JButton("Reset");
	     JButton btnExit = new JButton("Exit");
	     JButton btnTranscript = new JButton("Transcript");
	     JButton btnCounsel = new JButton("Career Advice");

	     btnAdd.addActionListener(e -> addRecord());
	     btnDelete.addActionListener(e -> 
	     {
	    	 int row = table.getSelectedRow();
	         if (row >= 0) model.removeRow(row);
	     });
	     btnReset.addActionListener(e -> resetForm());
	     btnExit.addActionListener(e -> System.exit(0));
	     btnTranscript.addActionListener(e -> showTranscript());
	     btnCounsel.addActionListener(e -> new CareerFrame(calculateAverage()));

	     btnPanel.add(btnAdd);
	     btnPanel.add(btnEdit);
	     btnPanel.add(btnDelete);
	     btnPanel.add(btnReset);
	     btnPanel.add(btnTranscript);
	     btnPanel.add(btnCounsel);
	     btnPanel.add(btnExit);

	     // LAYOUT
	     mainPanel.add(formPanel, BorderLayout.NORTH);
	     mainPanel.add(new JScrollPane(txtTranscript), BorderLayout.CENTER);
	     mainPanel.add(tableScroll, BorderLayout.SOUTH);
	     mainPanel.add(btnPanel, BorderLayout.WEST);

	     add(mainPanel);
	     setVisible(true);
	 }

private void addLabelField(JPanel panel, String label, JTextField field) 
{
    panel.add(new JLabel(label));
    panel.add(field);
}

private void addRecord() 
{
	// Variables
    int total = calculateTotal();
    double avg = calculateAverage();

    txtTotal.setText(String.valueOf(total));
    txtAverage.setText(String.format("%.2f", avg));

    String[] data = 
    {
    	txtID.getText(), txtFirst.getText(), txtLast.getText(), txtCourse.getText(),
        txtAddress.getText(), txtEmail.getText(), txtCountry.getText(), txtCity.getText(), 
        txtGPA.getText(), txtEnglish.getText(), txtMath.getText(), txtScience.getText(), 
        txtSpanish.getText(), txtHistory.getText(), txtComputers.getText(), txtTotal.getText(), 
        txtAverage.getText()
    };
    model.addRow(data);
}

private void resetForm() 
{
    for (Component c : getContentPane().getComponents()) 
    {
        if (c instanceof JPanel) 
        {
            for (Component sub : ((JPanel)c).getComponents()) 
            {
                if (sub instanceof JTextField) ((JTextField)sub).setText("");
            }
        }
    }
    txtTranscript.setText("");
}

private void showTranscript() {
    txtTranscript.setText("Transcript:\n");
    txtTranscript.append("Student ID: " + txtID.getText() + "\n");
    txtTranscript.append("Student: " + txtFirst.getText() + " " + txtLast.getText() + "\n");
    txtTranscript.append("Course: " + txtCourse.getText() + "\n");
    txtTranscript.append("GPA: " + txtGPA.getText() + "\n");
    txtTranscript.append("English: " + txtEnglish.getText() + "\n");
    txtTranscript.append("Math: " + txtMath.getText() + "\n");
    txtTranscript.append("Science: " + txtScience.getText() + "\n");
    txtTranscript.append("Spanish: " + txtSpanish.getText() + "\n");
    txtTranscript.append("History: " + txtHistory.getText() + "\n");
    txtTranscript.append("Computers: " + txtComputers.getText() + "\n");
    txtTranscript.append("Total: " + txtTotal.getText() + "\n");
    txtTranscript.append("Average: " + txtAverage.getText() + "\n");
}

private int calculateTotal() 
{
	try 
	{
		int e = Integer.parseInt(txtEnglish.getText());
        int m = Integer.parseInt(txtMath.getText());
        int s = Integer.parseInt(txtScience.getText());
        int sp = Integer.parseInt(txtSpanish.getText());
        int h = Integer.parseInt(txtHistory.getText());
        int c = Integer.parseInt(txtComputers.getText());
        return e + m + s + sp + h + c;
    } 
	catch (Exception ex) 
	{
        return 0;
    }
}

private double calculateAverage() 
{
    return calculateTotal() / 6.0;
}

// Career Counselor
class CareerFrame extends JFrame 
{
    public CareerFrame(double avg) 
    {
        setTitle("Career Counselor");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JTextArea txtAdvice = new JTextArea();
        txtAdvice.setEditable(false);

        String primary = "";
        String secondary = "";

        if (avg >= 85) 
        {
            primary = "Doctor / Engineer";
            secondary = "Research Scientist / Data Analyst";
        } 
        else if (avg >= 70) 
        {
            primary = "Teacher / IT Specialist";
            secondary = "Journalist / Business Manager";
        } 
        else if (avg >= 50) 
        {
            primary = "Technician / Office Assistant";
            secondary = "Sales / Customer Service";
        } 
        else 
        {
            primary = "Skilled Trade (Mechanic, Electrician)";
            secondary = "Entrepreneurship / Vocational Training";
        }

        txtAdvice.setText("Career Recommendations:\n\n" +
                          "Primary Career: " + primary + "\n" +
                          "Secondary Career: " + secondary);

        add(new JScrollPane(txtAdvice));
        setVisible(true);
    }
}
}
