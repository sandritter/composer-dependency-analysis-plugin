package de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.XmlStorageUnit;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Domain.Model.Transfer.Interface.Transferable;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Exception.StorageFailedException;
import de.bathesis2015.msand.postBuildAction.jevidatacollector.Persistence.Database.Interface.DataStorage;

/**
 * stores data to an xml file
 * XmlDataStorage.java
 *
 * @author Michael Sandritter
 * 03.10.2015
 */
public class XmlDataStorage implements DataStorage{
	
	public void storeData(Transferable transport) throws Exception {
		
		try {
			File file = new File("");
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlStorageUnit.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(transport, file);
			jaxbMarshaller.marshal(transport, System.out);
			
		} catch (JAXBException e) {
			throw new StorageFailedException(e.getMessage());
		}
	}
}
