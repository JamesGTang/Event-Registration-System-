package ca.mcgill.ecse321.eventregistration.view;

import java.awt.Color;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Properties;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.omg.CORBA.PUBLIC_MEMBER;

import ca.mcgill.ecse321.eventregistration.controller.EventRegistrationController;
import ca.mcgill.ecse321.eventregistration.controller.InvalidInputException;
import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

public class EventRegistrationPage extends JFrame {
	private JLabel errorMessage;
	private JComboBox<String> participantList;
	private JLabel participantLabel;
	private JComboBox<String> eventList;
	private JLabel eventLabel;
	private JButton registerButton;
	private JTextField participantNameTextField;
	private JLabel participantNameLabel;
	private JButton addParticipantButton;
	private JTextField eventNameTextField;
	private JLabel eventNameLabel;
	private JDatePickerImpl eventDatePicker;
	private JLabel eventDateLabel;
	private JSpinner startTimeSpinner;
	private JLabel startTimeLabel;
	private JSpinner endTimeSpinner;
	private JLabel endTimeLabel;
	private JButton addEventButton;
	private JLabel feedbackMessage;
	private RegistrationManager rm;
	// data elements
	private String error = null;
	private String feedback=null; 
	private Integer selectedParticipant = -1;
	private Integer selectedEvent = -1;

	private void initComponents() {
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.red);
		// elements for registration
		participantList = new JComboBox<String>(new String[0]);
		participantLabel = new JLabel();
		eventList = new JComboBox<String>(new String[0]);
		eventLabel = new JLabel();
		registerButton = new JButton();
		// elements for participant
		participantNameTextField = new JTextField();
		participantNameLabel = new JLabel();
		addParticipantButton = new JButton();
		// elements for events
		eventNameLabel = new JLabel();
		eventNameTextField = new JTextField();
		feedbackMessage=new JLabel(); 
		feedbackMessage.setForeground(Color.blue);
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		eventDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		eventDateLabel = new JLabel();
		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
		startTimeSpinner.setEditor(startTimeEditor);
		startTimeSpinner.setEditor(startTimeEditor); // will only show the
														// current time
		startTimeLabel = new JLabel();
		endTimeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
		endTimeSpinner.setEditor(endTimeEditor); // will only show the current
													// time
		endTimeLabel = new JLabel();
		addEventButton = new JButton();

		// global settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Event Registration");
		participantLabel.setText("Select Participant: ");
		eventLabel.setText("Select Event: ");
		registerButton.setText("Register");
		participantLabel.setText("Select Participant: ");
		participantNameLabel.setText("Name: ");
		addParticipantButton.setText("Add Participant");
		eventNameLabel.setText("Name: ");
		eventDateLabel.setText("Date: ");
		startTimeLabel.setText("Start Time: ");
		endTimeLabel.setText("End Time: ");
		addEventButton.setText("Add Event");
		
		// responsiveness
		 participantNameTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				participantNameTextField.setBackground(Color.white);
			}
			@Override
			public void focusGained(FocusEvent e) {
				participantNameTextField.setBackground(Color.yellow);
			}
		 });
		 eventNameTextField.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					eventNameTextField.setBackground(Color.white);
				}
				@Override
				public void focusGained(FocusEvent e) {
					eventNameTextField.setBackground(Color.yellow);
				}
			 });
		
		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(errorMessage)
				.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
				.addComponent(participantLabel)
				.addComponent(registerButton)
				.addComponent(participantNameLabel))
				.addGroup(layout.createParallelGroup()
				.addComponent(participantList)
				.addComponent(participantNameTextField, 200, 200, 400)
				.addComponent(addParticipantButton))
				.addGroup(layout.createParallelGroup()
				.addComponent(eventLabel)
				.addComponent(eventNameLabel)
				.addComponent(eventDateLabel)
				.addComponent(startTimeLabel)
				.addComponent(endTimeLabel))
				.addGroup(layout.createParallelGroup()
				.addComponent(eventList)
				.addComponent(eventNameTextField, 200, 200, 400)
				.addComponent(eventDatePicker)
				.addComponent(startTimeSpinner)
				.addComponent(endTimeSpinner)
				.addComponent(addEventButton)))
				.addComponent(feedbackMessage)
		);
		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{registerButton, participantLabel});
				layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{addParticipantButton, participantNameTextField});
				layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{addEventButton, eventNameTextField});
	    layout.setVerticalGroup(
						layout.createSequentialGroup()
						.addComponent(errorMessage)
						.addGroup(layout.createParallelGroup()
						.addComponent(participantLabel)
						.addComponent(participantList)
						.addComponent(eventLabel)
						.addComponent(eventList))
						.addComponent(registerButton)
						.addGroup(layout.createParallelGroup()
						.addComponent(participantNameLabel)
						.addComponent(participantNameTextField)
						.addComponent(eventNameLabel)
						.addComponent(eventNameTextField))
						.addGroup(layout.createParallelGroup()
						.addComponent(eventDateLabel)
						.addComponent(eventDatePicker))
						.addGroup(layout.createParallelGroup()
						.addComponent(startTimeLabel)
						.addComponent(startTimeSpinner))
						.addGroup(layout.createParallelGroup()
						.addComponent(endTimeLabel)
						.addComponent(endTimeSpinner))
						.addGroup(layout.createParallelGroup()
						.addComponent(addParticipantButton)
						.addComponent(addEventButton))
						.addComponent(feedbackMessage)
						);
						pack();
						
						// add action listeners
						participantList.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								JComboBox<String> cb = (JComboBox<String>) evt.getSource();
								selectedParticipant = cb.getSelectedIndex();
							}
						});
						eventList.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								JComboBox<String> cb = (JComboBox<String>) evt.getSource();
								selectedEvent = cb.getSelectedIndex();
							}
						});
						registerButton.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								registerButtonActionPerformed();
							}
						});
						addParticipantButton.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								addParticipantButtonActionPerformed();
							}
						});
						addEventButton.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								addEventButtonActionPerformed();
							}
						});
}
	private void refreshData(){
		// errorMessage
		errorMessage.setText(error);
		feedbackMessage.setText(feedback);
		if(error==null||error.length()==0){
			//participant list
			participantList.removeAllItems();
			for(Participant p:rm.getParticipants()){
				participantList.addItem(p.getName());
			}
			selectedParticipant=-1;
			participantList.setSelectedIndex(selectedParticipant);
			// event list
			eventList.removeAllItems();
			for(Event e:rm.getEvents()){
				eventList.addItem(e.getName());
			}
			selectedEvent=-1;
			eventList.setSelectedIndex(selectedEvent);
			
			// particiapant
			participantNameTextField.setText("");
			// event
			eventNameTextField.setText("");
			eventDatePicker.getModel().setValue(null);
			startTimeSpinner.setValue(new java.util.Date());
			endTimeSpinner.setValue(new java.util.Date());
			
		}}
	public EventRegistrationPage(RegistrationManager arm){
		rm=arm;
		initComponents();
		refreshData();
	}
	
	private void addParticipantButtonActionPerformed(){
		// cal the controller
		EventRegistrationController erc=new EventRegistrationController(rm);
		error=null;
		try{
			erc.createParticipant(participantNameTextField.getText());
			feedback="Successfully added "+participantNameTextField.getText()+" as a participant";
		}catch (InvalidInputException iie) {
			error=iie.getMessage(); 
		}
		refreshData();
	}
	
	private void addEventButtonActionPerformed(){
		EventRegistrationController erc = new EventRegistrationController(rm);
		// JSpinner actually returns a date and time
		// force the same date for start and end time to ensure that only the times differ
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) startTimeSpinner.getValue());
		calendar.set(2000, 1, 1);
		Time startTime = new Time(calendar.getTime().getTime());
		calendar.setTime((Date) endTimeSpinner.getValue());
		calendar.set(2000, 1, 1);
		Time endTime = new Time(calendar.getTime().getTime());
		error = null;
		try {
			erc.createEvent(eventNameTextField.getText(), (java.sql.Date)
					eventDatePicker.getModel().getValue(), startTime, endTime);
			feedback="Successfully added event: "+eventNameTextField.getText();
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// update visuals
		refreshData();
	}
	private void registerButtonActionPerformed(){
		error = "";
		if (selectedParticipant < 0)
			error = error + "Participant needs to be selected for registration! ";
		if (selectedEvent < 0)
			error = error + "Event needs to be selected for registration!";
		error = error.trim();
		if (error.length() == 0) {
			// call the controller
			EventRegistrationController erc = new EventRegistrationController(rm);
			try {
				erc.register(rm.getParticipant(selectedParticipant),
						rm.getEvent(selectedEvent));
				feedback="Successfully registered "+participantList.getSelectedItem()+" to event: "+eventList.getSelectedItem();
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		// update visuals
		refreshData();
	}
}
