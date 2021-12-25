import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sort {
	
	private Scanner input; 					//This handles the input pointer at the top of the file.
	private Scanner output;					//This handles the output pointer at the top of the file.
	private File inputFile;					//This is the input File, this is used by the scanner to set and reset where the pointer is located.
	private File outputFile;				//This is the output File, this is used by the scanner to set and reset where the pointer is located.
	private String fileOutputName = ""; 	//This is the name of the output file used for writing into files.
	private int truth = 0;
	
	//Below contain the regex expressions for the following symbols
	private String Do = "(^[D][o]$)";
	private String Re = "(^[R][e]$)";
	private String Mi = "(^[M][i]$)";
	private String Ampersand = "(^\\&$)";
	private String At = "(^\\@$)";
	private String Percent = "(^\\%$)";
	private String Asymbolwithareallylongname = "(^[A][s][y][m][b][o][l][w][i][t][h][a][r][e][a][l][l][y][l][o][n][g][n][a][m][e]$)";
	private String DollarSign = "(^[\\$]$)";
	private String Fa = "(^[F][a]$)";
	private String One = "(^[O][n][e]$)";
	private String Three = "(^[T][h][r][e][e]$)";
	private String Two = "(^[T][w][o]$)";
	
	public void setInputFile(File inputFile)throws IOException{ //This is a setter method used to load the file input in the class.
		this.inputFile = inputFile;
	}
	
	public void setOutputFile(File outputFile)throws IOException{ //This is a setter method used to load the file output in the class.
		FileWriter writer = new FileWriter(outputFile);
		writer.write("");
		writer.close();
		this.outputFile = outputFile;
	}
	
	public void setInputScanner(File inputFile) throws IOException{ //This is a setter method used as a set and reset for the scanner pointer at the top of the input file.
		this.input = new Scanner(inputFile);
	}
	
	public void setOutputScanner(File outputFile) throws IOException{ //This is a setter method used as a set and reset for the scanner pointer at the top of the output file.
		this.output = new Scanner(outputFile);
	}
	
	public void setFileOutputName(String outputName) { //This is a setter method that is used to set the file output name. Might be useful when writing into the output file.
		this.fileOutputName = outputName;
	}
	
	
	//This functions main purpose is to read whatever is in the input file, put the contents of the input file into a string, and then put those contents into an output file.
	public void readInput() throws IOException{
		
		setInputScanner(inputFile); //this will put the file pointer to the top of the file. 
		while(this.input.hasNextLine()) {
			String checkValue = input.nextLine();
			sorter(checkValue);
		}
		setOutputScanner(outputFile);
		if(this.truth == 1) {
			ascending();
		}
		else {
			descending();
		}
	}
	
	public void sorter(String inputLine) throws IOException {
		//This checked if the input is an integer already.
		if(inputLine.equals("666")) {
			if(this.truth == 0) {
				inputLine = "@";
				writer(inputLine);
				ascending();
				this.truth = 1;	
			}
		}
		else if(isItAnInteger(inputLine)) {
			writer(inputLine);
			if(this.truth == 1) {
				ascending();
			}
			else {
				descending();
			}
		}
		//This checks if the input is a special character.
		else if(isItASpecialChar(inputLine)) {
			writer(inputLine);
			if(this.truth == 1) {
				ascending();
			}
			else {
				descending();
			}

		}
		//This should check if the input is an error
		else {
			//should copy the contents of the file and then add Input Error.
			setOutputScanner(this.outputFile); //this will point to the top of the file.
			String inputError = "";
			inputError = "Input error.";
			inputError = inputError.trim();
			FileWriter writer = new FileWriter(this.fileOutputName);
			writer.write(inputError);
			writer.close();
			System.exit(0);
		}
		
		if(this.truth == 1) { //just to make sure
			ascending();
		}
		
	}
	
	public void writer(String inputLine) throws IOException {
		setOutputScanner(this.outputFile);
		String currentOutputLine = ""; 		//this will contain the string up to the value

		
		if(!output.hasNextLine()) { 		//If the output file is empty then fill the first line of the output file with this.
			currentOutputLine = inputLine;
			currentOutputLine = currentOutputLine.trim();
			FileWriter writer = new FileWriter(this.fileOutputName);
			writer.write(currentOutputLine);
			writer.close();
		}
		
		else {
			String firstHalf = "";
			double outInteger = 0;
			double inInteger = 0;
			
			if(isItASpecialChar(inputLine)) {
				inInteger = doubleChecker(inputLine); //this parses the input as a double so it can be compared.
			}
			else if(isItAnInteger(inputLine)) {
				inInteger = Double.parseDouble(inputLine);
			}

			
			while(output.hasNextLine()) { 					
				currentOutputLine = output.nextLine();
				//This should ask whether the output is an integer or not before parsing it.
				if(isItAnInteger(currentOutputLine)) {
					// if it is an integer you want to parse this as a double.
					outInteger = Double.parseDouble(currentOutputLine);	
				}
				else if(isItASpecialChar(currentOutputLine)){
					// if it is a special char. Then assign it to a double and then compare them.
					outInteger = doubleChecker(currentOutputLine);
				}
					

				if(inInteger <= outInteger) {
					firstHalf = firstHalf + currentOutputLine + "\n";
					if(!output.hasNextLine()) {
						firstHalf = firstHalf + inputLine;
					}
				}
				
				else {
					firstHalf = firstHalf + inputLine + "\n" + currentOutputLine + "\n";
					while(output.hasNextLine()){
						firstHalf = firstHalf + output.nextLine() +"\n"; 
					}
				}
			}
			
				firstHalf = firstHalf.trim(); //This gets rid of all the white spaces.
				FileWriter writer = new FileWriter(this.fileOutputName);
				writer.write(firstHalf);
				writer.close();

		  }
		
	}
	
	public void descending() throws IOException {
		int counter = 0;
		int arraysize = 0;
		double firstValue = 0;
		double secondValue = 0;
		String temp ="";
		String finalString = "";
		
		setOutputScanner(this.outputFile);
		while(output.hasNextLine()) {
			output.nextLine();
			arraysize++;
		}
		
		String [] array = new String[arraysize];
		
		setOutputScanner(this.outputFile);
		for(int i = 0; output.hasNextLine(); i++) {

			array[i] = output.nextLine();
			counter++;
		}

		for(int i = 0; i < counter; i++) {
			if(isItASpecialChar(array[i])) {
				firstValue = doubleChecker(array[i]); //this parses the input as a double so it can be compared.
			}
			else if(isItAnInteger(array[i])) {
				firstValue = Double.parseDouble(array[i]);
			}
			
			for(int j = i + 1; j < counter; j++) {
				if(isItAnInteger(array[j])) {
					// if it is an integer you want to parse this as a double.
					secondValue = Double.parseDouble(array[j]);	
				}
				else if(isItASpecialChar(array[j])){
					// if it is a special char. Then assign it to a double and then compare them.
					secondValue = doubleChecker(array[j]);
				}
				
				if(firstValue < secondValue) {
						temp = array[i];
						array[i] = array[j];
						array[j] = temp;		
				}
			}
		}
		
		for(int i = 0; i < counter; i++) {

			finalString = finalString + array[i] + "\n";
		}
		finalString = finalString.trim();
		FileWriter writer = new FileWriter(this.fileOutputName);
		writer.write(finalString);
		writer.close();	
		
	}
	
	public void ascending() throws IOException {
	   //this will place the list in ascending order

		int counter = 0;
		int arraysize = 0;
		double firstValue = 0;
		double secondValue = 0;
		String temp ="";
		String finalString = "";
		
		setOutputScanner(this.outputFile);
		while(output.hasNextLine()) {
			output.nextLine();
			arraysize++;
		}
		String [] array = new String[arraysize];
		
		setOutputScanner(this.outputFile);
		for(int i = 0; output.hasNextLine(); i++) {
			array[i] = output.nextLine();
			counter++;
		}
		
		for(int i = 0; i < counter; i++) {
			if(isItASpecialChar(array[i])) {
				firstValue = doubleChecker(array[i]); //this parses the input as a double so it can be compared.
			}
			else if(isItAnInteger(array[i])) {
				firstValue = Double.parseDouble(array[i]);
			}
			for(int j = i + 1; j < counter; j++) {
				if(isItAnInteger(array[j])) {
					// if it is an integer you want to parse this as a double.
					secondValue = Double.parseDouble(array[j]);	
				}
				else if(isItASpecialChar(array[j])){
					// if it is a special char. Then assign it to a double and then compare them.
					secondValue = doubleChecker(array[j]);
				}
				
				if(firstValue > secondValue) {
						temp = array[i];
						array[i] = array[j];
						array[j] = temp;		
				}
			}
		}
		
		for(int i = 0; i < counter; i++) {
			finalString = finalString + array[i] + "\n";
		}
		
		finalString = finalString.trim();
		FileWriter writer = new FileWriter(this.fileOutputName);
		writer.write(finalString);
		writer.close();	
	}
	
	public double doubleChecker(String inputLine) throws IOException {
		//Whenever the symbol shows up it should check whether it is valid or not.
		//also we need to check whether the output is also a a symbol
		if(inputLine.matches(Do)){
			return 0.5;
		}
		else if(inputLine.matches(Re)) {
			return 100.5;
		}
		else if(inputLine.matches(Mi)) {
			return 1000.5;
		}
		else if(inputLine.matches(Ampersand)) {
			return 3.5;
		}
		else if(inputLine.matches(At)) {
			return 3.25;
		}
		else if(inputLine.matches(Percent)) {
			return 1005000.5;
		}
		else if(inputLine.matches(Asymbolwithareallylongname)) {
			return 55.5;
		}
		else if(inputLine.matches(DollarSign)) {
			return 20.5;
		}
		else if(inputLine.matches(Fa)) {
			return 15.5;
		}
		else if(inputLine.matches(One)) {
			return 103.2;
		}
		else if(inputLine.matches(Three)) {
			return 103.8;
		}
		else{
			return 103.4;
		}		
	}
	
	public boolean isItAnInteger(String inputInt) {
		String integerRegex = "^([1-9][0-9]*)$|^([0])$"; //This will check if the line only contains numbers.
		if(inputInt.matches(integerRegex)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isItASpecialChar(String specialChar) {
		if(specialChar.matches(this.Do)||specialChar.matches(this.Re)||specialChar.matches(this.Mi)||specialChar.matches(this.Ampersand)||specialChar.matches(this.At)
				||specialChar.matches(this.Percent)||specialChar.matches(this.Asymbolwithareallylongname)||specialChar.matches(this.DollarSign)
				||specialChar.matches(this.Fa)||specialChar.matches(this.One)||specialChar.matches(this.Three)||specialChar.matches(this.Two)) {
			return true;
		}
		else {
			return false;
		}
	}


	public static void main(String [] args) throws IOException {
		
		Sort sorterTest = new Sort();

		sorterTest.setFileOutputName(args[1]);
		
		File inputfile = new File(args[0]);
		File outputfile = new File(args[1]);
		sorterTest.setInputFile(inputfile);
		sorterTest.setOutputFile(outputfile);
		sorterTest.readInput();
	}
	
}
