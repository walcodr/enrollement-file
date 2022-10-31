package com.availity.enrollments;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EnrolleesApp {
	
	
	static Enrollee enrollee = new Enrollee();
	
	static List<Enrollee> aetnaList = new ArrayList<Enrollee>(); 
	static List<Enrollee> anthemList = new ArrayList<Enrollee>();
	static List<Enrollee> bcbsList = new ArrayList<Enrollee>();
	static List<Enrollee> unitedList = new ArrayList<Enrollee>();
	
	static ArrayList<Enrollee> record = new ArrayList<Enrollee>();
	
	// set csv file path
	private static final String ENROLLEE_CSV_FILE_PATH = "./enrollment_files/enrollments.csv";
	
	// set output file paths
	private static final String aetnaFileOutputPath = "./enrollment-output-files/Aetna.txt";
	private static final String anthemFileOutputPath = "./enrollment-output-files/Anthem.txt";
	private static final String bcbsFileOutputPath = "./enrollment-output-files/BCBS.txt";
	private static final String unitedFileOutputPath = "./enrollment-output-files/United.txt";

	
		@SuppressWarnings("unlikely-arg-type")
		public static void main(String[] args) {
	
			// implement insurance enrollee objects
			 try (
	            Reader reader = Files.newBufferedReader(Paths.get(ENROLLEE_CSV_FILE_PATH));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withDelimiter(','));
				 	 
			 ) {
				 // parse fields for each record
				 for (CSVRecord row : csvParser) {
					 
					 enrollee.setUserID(row.get(0));
					 enrollee.setFirstName(row.get(1));
					 enrollee.setLastName(row.get(2));
					 enrollee.setVersion(row.get(3));
					 enrollee.setInsurancecompany(row.get(4));
					 
					 // add record to appropriate list
					 if ( enrollee.getInsuranceCompany() == "aetna" ) {
						 int i = aetnaList.indexOf(aetnaList.contains(row.get(0)));
						 Enrollee curRec = aetnaList.get(i);
						 String versn = curRec.getVersion();
						 if ( aetnaList.contains(row.get(0)) ) {
							 if ( Integer.parseInt(versn) < Integer.parseInt(curRec.getVersion()) ) {
							 aetnaList.remove(curRec);
							 aetnaList.add(enrollee);
						     }
						 } else aetnaList.add(enrollee);
					 } else if (enrollee.getInsuranceCompany() == "anthem" ) {
						 int i = anthemList.indexOf(anthemList.contains(row.get(0)));
						 Enrollee curRec = anthemList.get(i);
						 String versn = curRec.getVersion();
						 if ( anthemList.contains(row.get(0)) ) {
							 if ( Integer.parseInt(versn) < Integer.parseInt(curRec.getVersion()) ) {
								 anthemList.remove(curRec);
								 anthemList.add(enrollee);
						     }
						 } else anthemList.add(enrollee);
					 } else if ( enrollee.getInsuranceCompany() == "bcbs" ) {
						 int i = bcbsList.indexOf(bcbsList.contains(row.get(0)));
						 Enrollee curRec = bcbsList.get(i);
						 String versn = curRec.getVersion();
						 if ( bcbsList.contains(row.get(0)) ) {
							 if ( Integer.parseInt(versn) < Integer.parseInt(curRec.getVersion()) ) {
								 bcbsList.remove(curRec);
								 bcbsList.add(enrollee);
						     }
						 } else bcbsList.add(enrollee);
					 } else {
						 int i = unitedList.indexOf(unitedList.contains(row.get(0)));
						 Enrollee curRec = unitedList.get(i);
						 String versn = curRec.getVersion();
						 if ( unitedList.contains(row.get(0)) ) {
							 if ( Integer.parseInt(versn) < Integer.parseInt(curRec.getVersion()) ) {
								 unitedList.remove(curRec);
								 unitedList.add(enrollee);
						     }
						 } else unitedList.add(enrollee);
					 }
				 }
				 
				 // sort list by last name
			 	 aetnaList.sort((o1, o2)
		                  -> o1.getLastName().compareTo(
		                          o2.getLastName()));
			 	 
			 	 anthemList.sort((o1, o2)
		                  -> o1.getLastName().compareTo(
		                          o2.getLastName()));
			 	 
			 	 bcbsList.sort((o1, o2)
		                  -> o1.getLastName().compareTo(
		                          o2.getLastName()));
			 	 
			 	 unitedList.sort((o1, o2)
		                  -> o1.getLastName().compareTo(
		                          o2.getLastName()));
			 	 
			 	 
			 	 // output to file by each insurance company
			 	try{
			 	    FileOutputStream writeData = new FileOutputStream(aetnaFileOutputPath);
			 	    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			 	    writeStream.writeObject(aetnaList);
			 	    writeStream.flush();
			 	    writeStream.close();
			 	    
			 	    
			 	    writeData = new FileOutputStream(anthemFileOutputPath);
			 	    writeStream = new ObjectOutputStream(writeData);
			 	    writeStream.writeObject(anthemList);
			 	    writeStream.flush();
			 	    writeStream.close();
			 	    
			 	    writeData = new FileOutputStream(bcbsFileOutputPath);
			 	    writeStream = new ObjectOutputStream(writeData);
			 	    writeStream.writeObject(bcbsList);
			 	    writeStream.flush();
			 	    writeStream.close();
			 	    
			 	    writeData = new FileOutputStream(unitedFileOutputPath);
			 	    writeStream = new ObjectOutputStream(writeData);
			 	    writeStream.writeObject(unitedList);
			 	    writeStream.flush();
			 	    writeStream.close();

			 	}catch (IOException e) {
			 	    e.printStackTrace();
			 	}
				  		 
				 
			 } catch (Exception e) {
				 
				 System.out.println(">> Failed to add record for User ID : " + enrollee.getUserID());
				 e.printStackTrace();
			 } 
		}
		
}	


