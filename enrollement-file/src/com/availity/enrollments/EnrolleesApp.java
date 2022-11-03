package com.availity.enrollments;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class EnrolleesApp {
		
	// set csv file path
	private static final String ENROLLEE_CSV_FILE_PATH = "./enrollment_files/enrollments.csv";
	
	// set output file paths
	private static final String aetnaFileOutputPath = "./enrollment-output-files/Aetna.txt";
	private static final String anthemFileOutputPath = "./enrollment-output-files/Anthem.txt";
	private static final String bcbsFileOutputPath = "./enrollment-output-files/BCBS.txt";
	private static final String unitedFileOutputPath = "./enrollment-output-files/United.txt";

	public static List<Enrollee> aetnaList = new ArrayList<Enrollee>(); 
	public static List<Enrollee> anthemList = new ArrayList<Enrollee>();
	public static List<Enrollee> bcbsList = new ArrayList<Enrollee>();
	public static List<Enrollee> unitedList = new ArrayList<Enrollee>();

	static Enrollee enrollee = new Enrollee();	

		public static void main(String[] args) throws FileNotFoundException, IOException {
			 try (
	            Reader in = Files.newBufferedReader(Paths.get(ENROLLEE_CSV_FILE_PATH));
				@SuppressWarnings("deprecation")
				CSVParser records = new CSVParser(in,
	                        CSVFormat.EXCEL
	                        .withDelimiter(',')
	                        .withSkipHeaderRecord(true)
	                        .withHeader("userid", "fname","lname", "version", "insuranceCo")); 
					 
			 ) {
				 // parse fields for each record
				 for (CSVRecord row : records) {
					 if (row.size() > 1) {
						 
						 enrollee.setUserID(row.get("userid"));
						 enrollee.setFirstName(row.get("fname"));
						 enrollee.setLastName(row.get("lname"));
						 enrollee.setVersion(row.get("version"));
						 enrollee.setInsurancecompany(row.get("insuranceCo"));
						 
						 // add record to appropriate list
						 if ( enrollee.getInsuranceCompany().equals("aetna") ) {
							// check for duplicates and keep higher version
							 aetnaList = duplicateCheck(aetnaList, enrollee);
						   	 enrollee = new Enrollee();	
						 } else if ( enrollee.getInsuranceCompany().equals("anthem") ) {
							// check for duplicates and keep higher version
							 anthemList = duplicateCheck(anthemList, enrollee);
					         enrollee = new Enrollee();
						 } else if ( enrollee.getInsuranceCompany().equals("bcbs") ) {
							// check for duplicates and keep higher version
							 bcbsList = duplicateCheck(bcbsList, enrollee);
							 enrollee = new Enrollee();
						 } else {
							// check for duplicates and keep higher version
							 unitedList = duplicateCheck(unitedList, enrollee);
					         enrollee = new Enrollee();	
						 }
					 }
				 }
				
			    // sort list by last name
			    aetnaList = sortAsc(aetnaList);
			 
			    anthemList = sortAsc(anthemList);
			 
			    bcbsList = sortAsc(bcbsList);
			 
			    unitedList = sortAsc(unitedList);
			 	 
			 	// output to file by each insurance company
		 		aetnaList = writeToFiles(aetnaFileOutputPath, aetnaList, enrollee);
		 		
		 		anthemList = writeToFiles(anthemFileOutputPath, anthemList, enrollee);
		 		
		 		bcbsList = writeToFiles(bcbsFileOutputPath, bcbsList, enrollee);
		 		
		 		unitedList = writeToFiles(unitedFileOutputPath, unitedList, enrollee);
				 
			 } catch (Exception e) {
				 
				 System.out.println(">> Failed to add record for User ID : " + enrollee.getUserID());
				 e.printStackTrace();
			 } 
		}
		
		
		
		// removes duplicates and keeps record with highest version then returns the list
		public static List<Enrollee> duplicateCheck(List<Enrollee> list, Enrollee enrollee) {
			if ( list.size() > 0) {
				 for ( Enrollee enr : list ) {
					 if ( enr.userID.contains(enrollee.userID) ) {
						 // compare versions and remove lower version record
						 if ( Integer.parseInt(enr.vrsion) < Integer.parseInt(enrollee.vrsion) ) {
							 list.remove(enr);
							 break;
					     }
					 }	 
				 }
			 }
			list.add(enrollee);
			return list;
		}
		
	/*	// sorts list by last name in ascending order and returns the list
		public static List<Enrollee> sortAsc(List<Enrollee> list) {
			list.sort((o1, o2)
	                  -> o1.getLastName().compareTo(
	                          o2.getLastName()));
			return list;
		}
	*/
		
		// sorts list by last name then first name in ascending order and returns the list
		public static List<Enrollee> sortAsc(List<Enrollee> list) {
			  // functions for getting first and last names from an enrollee
		      Function<Enrollee, String> byFirstName = Enrollee::getFirstName;
		      Function<Enrollee, String> byLastName = Enrollee::getLastName;

		      // comparator for comparing enrollee by first name then last name
		      Comparator<Enrollee> lastThenFirst = 
		      Comparator.comparing(byLastName).thenComparing(byFirstName);

		      // sort enrollees in ascending order by last name, then first name
		      list.sort(lastThenFirst);
		      
			return list;
		}
		
		// writes the list to corresponding output file
		public static List<Enrollee> writeToFiles(String path, List<Enrollee> list, Enrollee enrollee) throws IOException {
			FileOutputStream writeData = new FileOutputStream(path);
	 	    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
	 	    String newline = System.getProperty("line.separator"); 
	 	    for (Enrollee e : list) {
		 	    writeStream.writeObject(" " +
		 	    						e.getUserID() + " " + 
		 	    						e.getFirstName() + " " +
		 	    					    e.getLastName() + " " +
		 	    						e.getVersion() + " " +
		 	    					    e.getInsuranceCompany() + " " + newline);
	 	    }
	 	    writeStream.flush();
	 	    writeStream.close();
			return list;
		}
		
		
}	


