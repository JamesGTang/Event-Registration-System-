package ca.mcgill.ecse321.eventregistration.view;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.omg.CORBA.PRIVATE_MEMBER;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager; 

public class ParticipantPage extends JFrame {
	// add generated serialVersionUID 
	private static final long serialVersionUID = 6398301441623263485L;
	private JTextField participantNameTextField; 
	private JLabel participantNameLabel; 
	private JButton addParticipantButton; 
	private RegistrationManager rm; 
	private String error=null;
	private JLabel errorMessage; 
	
	// constructor of the class
	public ParticipantPage(RegistrationManager rm){
		this.rm=rm; 
		initComponents();
	}
	
	public void initComponents(){
		// init new instance of the components
		participantNameTextField=new JTextField();
		participantNameLabel=new JLabel();
		addParticipantButton=new JButton();
		
		// error handling components
		errorMessage=new JLabel();
		errorMessage.setForeground(Color.RED);
		
		// set the JFrame behavior
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Event Registration Program");
		
		participantNameLabel.setText("Name:");
		addParticipantButton.setText("Add Participant");
		
		// set JFrame layout
		GroupLayout layout=new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				// error message is added here
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
				.addComponent(participantNameLabel)
				.addGroup(layout.createParallelGroup()
				.addComponent(participantNameTextField, 200, 200, 400)
				.addComponent(addParticipantButton))
		));
		
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{addParticipantButton, participantNameTextField});
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				// error message is added here
				.addComponent(errorMessage)
				.addGroup(layout.createParallelGroup()
				.addComponent(participantNameLabel)
				.addComponent(participantNameTextField))
				.addComponent(addParticipantButton)
		);
		pack();
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		addParticipantButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				addParticipantButtonActionPerformed();
			}
		});
	}
	
	public void refreshData(){
		errorMessage.setText(error);
		participantNameTextField.setText("");
		pack(); 
	}
	private void addParticipantButtonActionPerformed(){
		EventRegistrationController erc=new EventRegistrationController(rm);
		error=null;
		try{
			erc.createParticipant(participantNameTextField.getText());
		}catch(InvalidInputException iie){
			error=iie.getMessage(); 
		}
		refreshData();
	}
	
}
