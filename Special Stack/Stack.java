import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

public class Stack{
	
	private Scanner input;
	private Scanner output;
	private String fileDest;
	private File outFile;
	private String tempFileDest = "tempStackFile.txt";
	public File tempFile;

	public void setInputScanner(File inputFile)throws IOException{
		this.input = new Scanner(inputFile);
	}
	
	public void setOutputScanner(File outputFile)throws IOException{
		this.output = new Scanner(outputFile);
	}
	
	public void setFileDest(String fileDest){
		this.fileDest = fileDest;
	}
	
	public void setOutFile( File outFile)throws IOException{
		String reset = "";
		new File(this.fileDest);
		this.outFile = outFile;
		
		FileWriter writer = new FileWriter(this.fileDest);
		writer.write(reset);
		writer.close();
		
	}
	
	public void setTempFile(){
		try{
			this.tempFile = new File(this.tempFileDest);
			if(this.tempFile.createNewFile()){
				System.out.println("File is created:" + this.tempFile.getName());
			}
			else{
				System.out.println("This File Already Exists.");
			}
		}catch(IOException e){
			System.out.println("An error Occurred.");
			e.printStackTrace();
		}	
	}
	
	
	//The main idea behind this function is to see if the contents
	//have been written properly in the input file.
	public String getInput(){	
		String inputContents = "";
		while(this.input.hasNextLine()){
			inputContents = inputContents.concat(this.input.nextLine() + "\n");
		}	
		return inputContents;
	}
	
	//The main idea behind this function is to see if the contents 
	//have been written properly in the output file.
	public String getOutput(){
		String outputContents = "";
		while(this.output.hasNextLine()){
			outputContents = outputContents.concat(this.output.nextLine() + "\n");
		}
		return outputContents;
	}
	
	
	
	public int readInputFile()throws IOException{ 
		String fileInput = "";
		String num = "";
		int paramNum = 0;
		
		int ret = 0;
		while(this.input.hasNextLine()){
			fileInput = this.input.nextLine();
			// were going to trim the numerics in here
			num = "";
			for(int i = 0; i < fileInput.length(); i++){
				if(fileInput.charAt(i) >= 48 && fileInput.charAt(i) <= 57){
					num = num + fileInput.charAt(i);
				}
				else if(fileInput.charAt(i) == '-' || fileInput.charAt(i) == '.'){
					String tempOutFile = "";
					Scanner topOfFile = new Scanner(this.outFile);

					tempOutFile = "Input error.\n";
					
					while(topOfFile.hasNextLine()){
						tempOutFile = tempOutFile + topOfFile.nextLine() + "\n";
					}

				
					FileWriter writer = new FileWriter(fileDest);
					writer.write(tempOutFile);
					writer.close();
					rewrite();
					System.exit(0);
				}
			}
			
			if(num != ""){
				paramNum = Integer.parseInt(num);
			}
			
			
			ret = paramNum; // this is just used to check
			
			if(fileInput.startsWith("push(") && fileInput.endsWith(")")){
				push(paramNum);
			}
			else if(fileInput.startsWith("pop(") && fileInput.endsWith(")")){
				pop();
			}
			else if(fileInput.startsWith("top(") && fileInput.endsWith(")")){
				top();
			}
			else{
				String tempOutFile = "";
				Scanner topOfFile = new Scanner(this.outFile);

				tempOutFile = "Input error.\n";
				while(topOfFile.hasNextLine()){
					tempOutFile = tempOutFile + topOfFile.nextLine() + "\n";
				}

				
				FileWriter writer = new FileWriter(fileDest);
				writer.write(tempOutFile);
				writer.close();
				rewrite();
				System.exit(0);
				
			}
		}
		return ret;
	}
	
	public void rewrite() throws IOException{
		String reverseFile = "";
		Scanner topOfFile = new Scanner(this.outFile); // now it points to the top of the output file.
		while(topOfFile.hasNextLine()){
			reverseFile = topOfFile.nextLine() + "\n" + reverseFile;
		}
		reverseFile = reverseFile.trim();
		//reverseFile = topOfFile.nextLine() + "\n" + reverseFile;
		//System.out.println(reverseFile);
		FileWriter writer = new FileWriter(this.outFile);
		writer.write(reverseFile);
		writer.close();		
	}

	public void push(int natNum)throws IOException{ //this is not copying correctly 
	
		String tempFile = "";
		Scanner topOfFile = new Scanner(this.tempFile);
		Scanner topOfOutFile = new Scanner(this.outFile);

		
		if(natNum < 0 && natNum != 666){
			tempFile = tempFile.concat("Input Error\n");
			FileWriter writer = new FileWriter(this.fileDest);
			writer.write(tempFile);
			writer.close();
			System.exit(0);
		}
		
		else if(natNum == 0){
			if(!topOfFile.hasNextLine()){
				tempFile = "0\n";
			}
			while(topOfFile.hasNextLine()){ //this is not copying correctly 

			tempFile = tempFile.concat(topOfFile.nextLine() + "\n"); // output.nextLine(); contains nothing
			}
		
		//System.out.println("This is what is in the tempFile String\n" + tempFile);
		
			FileWriter writer = new FileWriter(tempFileDest);
			writer.write(tempFile);
			writer.close();

		}
		
		else if(natNum == 3){
			tempFile = "7\n";
			while(topOfFile.hasNextLine()){ //this is not copying correctly 

			tempFile = tempFile.concat(topOfFile.nextLine() + "\n"); // output.nextLine(); contains nothing
			}

		
			FileWriter writer = new FileWriter(tempFileDest);
			writer.write(tempFile);
			writer.close();

		}
		
		else if(natNum == 666){
			tempFile = "666\n666\n666\n";
		}
		
		else if(natNum == 13){
			while(topOfFile.hasNextLine()){
				topOfFile.nextLine();
				pop();

			}
			tempFile = "13\n";
		}
		
		else{
			tempFile = natNum + "\n";
		}	
		
		while(topOfFile.hasNextLine()){ //this is not copying correctly 

			tempFile = tempFile.concat(topOfFile.nextLine() + "\n"); // output.nextLine(); contains nothing
		}
		
		//System.out.println("This is what is in the tempFile String\n" + tempFile);
		
		FileWriter writer = new FileWriter(tempFileDest);
		writer.write(tempFile);
		writer.close();

	//place holder for now
	}
	
	public void pop()throws IOException{
		String tempFile = "";
		String outFile = "";
		Scanner topOfFile = new Scanner(this.tempFile);
		Scanner topOfOutFile = new Scanner(this.outFile);


		if(!topOfFile.hasNextLine()){	

			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}
			outFile = outFile + "Error\n";
			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			this.tempFile.delete();
			System.exit(0);
		}
		
		
		
		if(topOfFile.nextLine().equals("666")){
			topOfFile = new Scanner(this.tempFile);
			outFile = topOfFile.nextLine() + "\n";
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}
			//System.out.print(outFile);
			topOfFile.nextLine();
			while(topOfFile.hasNextLine()){ //this recopies the contents of the original array 
				tempFile = tempFile + topOfFile.nextLine() + "\n";
			}
			
			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			
			FileWriter tempWriter = new FileWriter(tempFileDest);
			tempWriter.write(tempFile);
			tempWriter.close();
			
			return;
		}
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.nextLine().equals("7")){
			outFile = "7\n";
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}

			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			return;
		}
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.nextLine().equals("42")){
			outFile = "42\n";
			tempFile = "";
			
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}
			
			FileWriter tempWriter = new FileWriter(tempFileDest);
			tempWriter.write(tempFile);
			tempWriter.close();
			
			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
		}
		
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.hasNextLine()){
			topOfFile = new Scanner(this.tempFile);
			outFile = topOfFile.nextLine() + "\n";
		
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}
			
			while(topOfFile.hasNextLine()){ //this recopies the contents of the original array 
				tempFile = tempFile + topOfFile.nextLine() + "\n";
			}
			
			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			
			FileWriter tempWriter = new FileWriter(tempFileDest);
			tempWriter.write(tempFile);
			tempWriter.close();
			return;
		}
		
	}
	
	public void top()throws IOException{
		String outFile = "";
		String tempFile = "";
		Scanner topOfFile = new Scanner(this.tempFile);
		Scanner topOfOutFile = new Scanner(this.outFile);		
		
		if(!topOfFile.hasNextLine()){
			outFile = "null \n";
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}
			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			return;
		}
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.nextLine().contains("666")){
			outFile = "999\n";
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}

			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			return;
		}
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.nextLine().equals("7")){

			while(topOfFile.hasNextLine()){ //this recopies the contents of the original array 
				tempFile = tempFile + topOfFile.nextLine() + "\n";
			}
			FileWriter tempWriter = new FileWriter(tempFileDest);
			tempWriter.write(tempFile);
			tempWriter.close();
			return;
		}
		
		
		topOfFile = new Scanner(this.tempFile);
		if(topOfFile.nextLine().equals("319")){
			outFile = "666\n";
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}

			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			return;
		}
		
		topOfFile = new Scanner(this.tempFile); //this resets the file pointer to the begining
		if(topOfFile.hasNextLine()){
			outFile = topOfFile.nextLine() + "\n";
					
			while(topOfOutFile.hasNextLine()){
				outFile = outFile + topOfOutFile.nextLine() + "\n";
			}

			FileWriter writer = new FileWriter(fileDest);
			writer.write(outFile);
			writer.close();
			return;
		}
	}
	
		
	public static void main (String[] args)throws IOException{
		
		Stack stack = new Stack();
		
		File input = new File(args[0]); 	//This will be used to get the input files 
		File output = new File(args[1]);	//This will be used to get the output files
		stack.setFileDest(args[1]);
		stack.setOutFile(output);
		stack.setInputScanner(input);
		stack.setOutputScanner(output);
		stack.setTempFile();
		//System.out.println(stack.getInput());				//This is used as a test to make sure the files are going to be working
		//System.out.println(stack.getOutput());		//This is used as a test to make sure the files are going to be working.
		stack.tempFile.deleteOnExit();
		stack.readInputFile();
		stack.rewrite();

	}
	
}