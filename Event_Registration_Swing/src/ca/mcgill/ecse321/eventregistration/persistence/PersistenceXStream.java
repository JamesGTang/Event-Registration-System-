package ca.mcgill.ecse321.eventregistration.persistence;
// importing java io files
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager; 

public abstract class PersistenceXStream {
	private static XStream xstream=new XStream(); 
	private static String filename="data.xml"; 
	
	public static RegistrationManager initializeModelManager(String filename){
		RegistrationManager rm;
		setFilename(filename);
		setAlias("event",Event.class); 
		setAlias("participant",Participant.class);
		setAlias("registrtation",Registration.class); 
		setAlias("manager",RegistrationManager.class);
		
		File file=new File(filename);
		if(file.exists()){
			rm = (RegistrationManager) loadFromXMLwithXStream();
		}else{
			try{
				file.createNewFile();
			}catch(IOException ioe){
				ioe.printStackTrace();
				System.exit(1);
			}
			rm=new RegistrationManager();
			saveToXMLwithXStream(rm); 
		}
		
		return rm; 
	}
	public static boolean saveToXMLwithXStream(Object obj){
		xstream.setMode(XStream.ID_REFERENCES);
		String xml=xstream.toXML(obj); 
		try{
			FileWriter writer=new FileWriter(filename); 
			writer.write(xml); 
			writer.close();
			return true; 
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false; 
		}
	}
	public static Object loadFromXMLwithXStream(){
		xstream.setMode(XStream.ID_REFERENCES);
		try{
			FileReader filereader=new FileReader(filename);
			return xstream.fromXML(filereader); 
		}catch(IOException ioe){
			ioe.printStackTrace();
			return null; 
		}
	}
	public static void setAlias(String xmlTagName, Class<?> className){
		xstream.alias(xmlTagName, className);
	}
	public static void setFilename(String fn){
		filename=fn; 
	}
}
