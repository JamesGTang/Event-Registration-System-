package ca.mcgill.ecse321.eventregistration.application;

import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;
import ca.mcgill.ecse321.eventregistration.persistence.PersistenceXStream;
import ca.mcgill.ecse321.eventregistration.view.EventRegistrationPage;
import ca.mcgill.ecse321.eventregistration.view.ParticipantPage;

public class EventRegistration {
	private static String filename="output/eventregistration.xml";
	public static void main(String[] args) {
		// load model
		final RegistrationManager rm=PersistenceXStream.initializeModelManager(filename); 
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new EventRegistrationPage(rm).setVisible(true);
			}
		});
	}

}