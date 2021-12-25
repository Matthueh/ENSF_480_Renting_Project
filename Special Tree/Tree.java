import java.io.*;
import java.util.Scanner;


class Node{ 		//This will be the individual objects;
	
	private String value;
	private int level = 0; //this will indicate the priority 
	
	private Node left;
	private Node middle;
	private Node right;
	
	public void setNodeName(String value) {
		this.value = value;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLeftNode(Node node) {
		this.left = node;
	}
	
	public void setMiddleNode(Node node) {
		this.middle = node;
	}
	
	public void setRightNode(Node node) {
		this.right = node;
	}

	public String getValue() {
		return this.value;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public Node getNode(String whichNode) {
		if(whichNode.equals("Left")) {
			return this.left;
		}
		else if(whichNode.equals("Middle")) {
			return this.middle;
		}
		else {
			return this.right;
		}	
	}
	
	public void delNode(String whichNode) {
		if(whichNode.equals("Left")) {
			this.left = null;
		}
		else if(whichNode.equals("Middle")) {
			this.middle = null;
		}
		else {
			this.right = null;
		}
	}
	
}

class LinkedList{ //we will use this for the stack
	private Node node;
	private LinkedList next;
	
	public void setNode(Node node){
		this.node = node;
	}
	
	public void setNext(LinkedList next) {
		this.next = next;
	}
	
	public LinkedList getNext() {
		return this.next;
	}
	
	public Node getNode() {
		return this.node;
	}
	
}

class Stack{
	LinkedList head;						//This is a pointer that will always point to the top 
											//This is a pointer that will point to the node of interest
	
	public void push(Node node) { 			//This will push the linked list into the stack. Each linked list contains and node and points to the next 
		LinkedList newHead = new LinkedList(); //linked list.
		newHead.setNode(node);
		if(head == null) {
			newHead.setNext(null);
			head = newHead; 		//this points to the top of the stack
		}
		else {
			newHead.setNext(head);
			head = newHead;
		}
	}
	
	public Node pop() {				//This will return the node, and we can use this node to check
		LinkedList popped = head;
		this.head = this.head.getNext();		//Head should point to the next element 
		return popped.getNode();
	}
	
	public LinkedList getHead() {
		return this.head;
	}
	
}

public class Tree{
	
	
	//We first need to set up a tree with a root at the beginning.
	//We can create a constructor first to make sure that the file gets added in properly.

	private String fileInputName;
	private String fileOutputName;
	
	private File fileInput;
	private File fileOutput;
	
	//This should point to the top of the file every single time it is called.
	//We can think of this as a pointer for the file.
	private Scanner inputScan;
	
	private String Print = "(^[P][r][i][n][t][(][)]$)";
	
	private Node head = new Node(); //this will point to the head of the tree
	private Stack stack = new Stack(); //Create a new stack object
	private Node pointer; //We will use this to point to the node of interest

	private int highestLevel = 1;
	
	private String temp = "";
	private String output = "";
	
	
	public Tree(String fileIn, String fileOut) { 
		
		//This will set the files that we are going to be interacting with. As a constructor.

		this.fileInputName = fileIn;
		this.fileOutputName = fileOut;
		setInFile(fileIn);
		//System.out.println(getInFile());
		setOutFile(fileOut);
		//System.out.println(getOutFile());



	}
	
	public void setInFile(String fileInputName) {
		this.fileInput = new File(this.fileInputName);
	}
	
	public void setOutFile(String fileOutputName) {
		this.fileOutput = new File(this.fileOutputName);
	}
	
	public File getInFile() {
		return this.fileInput;
	}
	
	public File getOutFile() {
		return this.fileOutput;
	}
	
	public void readFileInput() throws IOException {
		inputScan = new Scanner(this.fileInput); //points to the top of the file
		this.head.setNodeName("root"); //This will place root at the top of the file.
		this.head.setLevel(0);
		
		while(this.inputScan.hasNextLine()) {
			String nextLine = this.inputScan.nextLine();
			//System.out.println(nextLine);
			if((nextLine.startsWith("AddL(") && nextLine.endsWith(")") && nextLine.contains(",")) || (nextLine.startsWith("AddM(") && nextLine.endsWith(")") && nextLine.contains(",")) || (nextLine.startsWith("AddR(") && nextLine.endsWith(")")  && nextLine.contains(","))) {            
				
				//This just reads the inputs
				//This block of code will essentially extract the inputs.
				String inputA = "";
				String inputB = "";
				int i = 0;
				while(nextLine.charAt(i) != '(') {
					i++;
				}
				i++;
				while(nextLine.charAt(i) != ',') {
					inputA = inputA + nextLine.charAt(i);
					i++;
					//inputA string is now filled up
				}
				while(nextLine.charAt(i) == ',') {
					inputA = inputA + nextLine.charAt(i);
					i++;
				}
				inputA = inputA.substring(0,inputA.length() - 1);
				while(i != nextLine.length() - 1) {

					inputB = inputB + nextLine.charAt(i);
					i++;
					//inputB string is now filled up
				}
				if(inputA.equals("") || inputB.equals("")) {
					String inputErr = "Input error.";
					FileWriter writer = new FileWriter(fileOutputName);
					writer.write(inputErr);
					writer.close();
					System.exit(1);
				}
					//This Block of code will then call addPayload which will then take the information
					//and process it so that it is usable.
				if(nextLine.startsWith("AddL(") && nextLine.endsWith(")")) {
					addPayload("Left", inputA, inputB);
				}
				else if(nextLine.startsWith("AddM(") && nextLine.endsWith(")")) {
					//System.out.println("----------");
					addPayload("Middle", inputA, inputB);
				}
				else {
					addPayload("Right", inputA, inputB);
				}
				
			}
			
			else if((nextLine.startsWith("DelL(") && nextLine.endsWith(")")) || (nextLine.startsWith("DelM(") && nextLine.endsWith(")")) ||(nextLine.startsWith("DelR(") && nextLine.endsWith(")"))) {
				//This will do DelL(a);
				String inputA = "";
				int i = 0;
				while(nextLine.charAt(i) != '(') {
					i++;
				}
				i++;
				while(i != nextLine.length() - 1) {
					inputA = inputA + nextLine.charAt(i);
					i++;
					//inputA string is now filled up
				}
			
				//System.out.println(inputA);
				//This Block of code will then call delPayload which will then take the information
				//and process it so that is usable
				if(inputA.equals("")) {
					String inputErr = "Input error.";
					FileWriter writer = new FileWriter(fileOutputName);
					writer.write(inputErr);
					writer.close();
					System.exit(1);
				}

				if((nextLine.startsWith("DelL(") && nextLine.endsWith(")"))) {
					delPayload("Left", inputA);
				}
				else if((nextLine.startsWith("DelM(") && nextLine.endsWith(")"))) {
					delPayload("Middle", inputA);
				}
				else if((nextLine.startsWith("DelR") && nextLine.endsWith(")"))){
					delPayload("Right", inputA);
				}
			}
			
			else if(nextLine.startsWith("Exchange(") && nextLine.endsWith(")") && nextLine.contains(",")) {
				String inputA = "";
				String inputB = "";
				int i = 0;
				while(nextLine.charAt(i) != '(') {
					i++;
				}
				i++;
				while(nextLine.charAt(i) != ',') {
					inputA = inputA + nextLine.charAt(i);
					i++;
					//inputA string is now filled up
				}
				while(nextLine.charAt(i) == ',') {
					inputA = inputA + nextLine.charAt(i);
					i++;
				}
				inputA = inputA.substring(0,inputA.length() - 1);
				while(i != nextLine.length() - 1) {
					inputB = inputB + nextLine.charAt(i);
					i++;
					//inputB string is now filled up
				}
				
				if(inputA.equals("") || inputB.equals("")) {
					String inputErr = "Input error.";
					FileWriter writer = new FileWriter(fileOutputName);
					writer.write(inputErr);
					writer.close();
					System.exit(1);
				}
				
				
				exchangePayload(inputA, inputB);//This function has been called
				
			}
			
			else if(nextLine.matches(Print)) {
				//This will attempt to print to the output file
				printOutput();
			}
			else {
				String inputErr = "Input error.";
				FileWriter writer = new FileWriter(fileOutputName);
				writer.write(inputErr);
				writer.close();
				System.exit(1);
			}
		}
		this.output = this.output.trim();
		FileWriter writer = new FileWriter(this.fileOutputName);
		writer.write(this.output); //write this at the end of everything
		writer.close();
		//System.out.println(this.head.getValue());
		
	}
	
	public Node createNode(String inputB, int previousLevel) {
		Node newNode = new Node();
		newNode.setNodeName(inputB);
		newNode.setLevel(previousLevel + 1);
		//System.out.println(newNode.getValue());
		return newNode;
				
	}
	
	//This will deal with adding the payloads.
	public void addPayload(String branch, String inputA, String inputB) {
	
			Node newNode = this.head;
			stack.push(newNode);
			addToStack(newNode, branch, inputA, "Add");
			
			if(this.pointer == null) { 				 			//this will check the whether the head is a value if the pointer is not a value.
				if(this.head.getValue().equals(inputA)) {
					this.pointer = this.head;
					if(inputB.startsWith("$")) {
						if(this.pointer.getNode(branch) != null) {
							this.pointer.getNode(branch).setNodeName(inputB.substring(1));
						}
						else if(this.pointer.getNode(branch) == null) {
							if(branch.equals("Left") && this.pointer.getNode("Left") == null) {
								this.pointer.setLeftNode(createNode(inputB.substring(1), this.pointer.getLevel()));
							}
							else if(branch.equals("Middle") && this.pointer.getNode("Middle") == null ) {
								this.pointer.setMiddleNode(createNode(inputB.substring(1), this.pointer.getLevel()));
							}
							else if(branch.equals("Right") && this.pointer.getNode("Right") == null){
								this.pointer.setRightNode(createNode(inputB.substring(1), this.pointer.getLevel()));
							}
						}
					}
					else {
						if(branch.equals("Left") && this.pointer.getNode("Left") == null) {
							this.pointer.setLeftNode(createNode(inputB, this.pointer.getLevel()));
						}
						else if(branch.equals("Middle") && this.pointer.getNode("Middle") == null ) {
							this.pointer.setMiddleNode(createNode(inputB, this.pointer.getLevel()));
						}
						else if(branch.equals("Right") && this.pointer.getNode("Right") == null){
							this.pointer.setRightNode(createNode(inputB, this.pointer.getLevel()));
						}
						else {
							this.output = this.output + "Add operation not possible.\n";
						}
					}
				}
				this.pointer = null;
				return;
			}
			
			
			if(inputB.startsWith("$")) {
				if(this.pointer.getNode(branch) != null)
					this.pointer.getNode(branch).setNodeName(inputB.substring(1));
				else if(this.pointer.getNode(branch) == null) {
					if(branch.equals("Left")) {
						this.pointer.setLeftNode(createNode(inputB.substring(1), this.pointer.getLevel()));
					}
					else if(branch.equals("Middle")) {
						this.pointer.setMiddleNode(createNode(inputB.substring(1), this.pointer.getLevel()));
					}
					else if(branch.equals("Right")){
						this.pointer.setRightNode(createNode(inputB.substring(1), this.pointer.getLevel()));
					}
				}
			}	
			else if(this.pointer.getNode(branch) == null) {
				if(branch.equals("Left")) {
					this.pointer.setLeftNode(createNode(inputB, this.pointer.getLevel()));
				}
				else if(branch.equals("Middle")) {
					this.pointer.setMiddleNode(createNode(inputB, this.pointer.getLevel()));
				}
				else if(branch.equals("Right")){
					this.pointer.setRightNode(createNode(inputB, this.pointer.getLevel()));
				}
			}
			else {
					this.output = this.output + "Add operation not possible.\n";
			}
			
			//System.out.println(this.head.getNode("Left").getValue());
			this.pointer = null; //reset the pointer
			//We may need to call another function so that we do not keep recursively put the head into the stack
	}	
	
	//This will deal with deleting the payload.
	public void delPayload(String branch, String inputA) {
		Node newNode = this.head;
		stack.push(newNode);
		addToStack(newNode, branch, inputA, "Del");
				
		if(this.pointer == null) { 				 		//this will check the last thing in the stack which in this case is root
			if(this.head.getValue().equals(inputA)) {
				this.pointer = this.head;
				if(branch.equals("Left")) {
						//we want to delete instead
					this.pointer.delNode("Left");
				}
				else if(branch.equals("Middle")) {
					this.pointer.delNode("Middle");
				}
				else {
					this.pointer.delNode("Right");
				}
			}	
			this.pointer = null;
			return;
		}
		
			if(branch.equals("Left")) {
				this.pointer.delNode("Left");
			}
			else if(branch.equals("Middle")) {
				this.pointer.delNode("Middle");
			}
			else {
				this.pointer.delNode("Right");
			}
			
			this.pointer = null;
	}
	
	public void exchangePayload(String inputA, String inputB) {
		Node newNode = this.head;
		stack.push(newNode);
		exchangeInStack(newNode, inputA, inputB);
		
		if(inputB.startsWith("$")) {
			if(this.head.getValue().equals(inputA)) {
				this.pointer = this.head;
				this.pointer.setNodeName(this.pointer.getValue() + inputB.substring(1));
			}
			
			if(this.head.getNode("Left") != null) {
				if(this.head.getNode("Left").getValue().equals(inputA)) {
					this.head.getNode("Left").setNodeName(this.pointer.getValue() + inputB.substring(1));
				}
			}
			
			if(this.head.getNode("Middle") != null) {
				if(this.head.getNode("Middle").getValue().equals(inputA)) {
					this.head.getNode("Middle").setNodeName(this.pointer.getValue() + inputB.substring(1));
				}
			}
			if(this.head.getNode("Right") != null) {
				if(this.head.getNode("Right").getValue().equals(inputA)) {
					this.head.getNode("Right").setNodeName(this.pointer.getValue() + inputB.substring(1));
				}
				
			}
			
		}
		else {
		
			if(this.head.getValue().equals(inputA)) {
				this.pointer = this.head;
				this.pointer.setNodeName(inputB);
			}
			
			if(this.head.getNode("Left") != null) {
				if(this.head.getNode("Left").getValue().equals(inputA)) {
					this.head.getNode("Left").setNodeName(inputB);
				}
			}
			
			if(this.head.getNode("Middle") != null) {
				if(this.head.getNode("Middle").getValue().equals(inputA)) {
					this.head.getNode("Middle").setNodeName(inputB);
				}
			}
			if(this.head.getNode("Right") != null) {
				if(this.head.getNode("Right").getValue().equals(inputA)) {
					this.head.getNode("Right").setNodeName(inputB);
				}
				
			}
		}
		
	}
	
	public void exchangeInStack(Node node, String inputA, String inputB) {

		if(node.getNode("Left") != null) {
			stack.push(node.getNode("Left"));
			checkExc(inputA, inputB);
			exchangeInStack(node.getNode("Left"), inputA, inputB);
		}
		if(node.getNode("Middle") != null) {
			stack.push(node.getNode("Middle"));
			checkExc(inputA, inputB);
			exchangeInStack(node.getNode("Middle"), inputA, inputB);
		}
		if(node.getNode("Right") != null) {
			stack.push(node.getNode("Right"));
			checkExc(inputA, inputB);
			exchangeInStack(node.getNode("Right"), inputA, inputB);
		}
		else {

			return;
		}
	}
	
	public void checkExc(String inputA, String inputB) {
		Node newNode = stack.pop();
		if(newNode.getValue().equals(inputA)) {
			if(inputB.startsWith("$")) {
				newNode.setNodeName(newNode.getValue() + inputB.substring(1));
			}
			else {
				newNode.setNodeName(inputB);
			}
		}
	}
	
	//This method will be using recursion to load up the stack
	public void addToStack(Node node, String branch, String inputA, String operation) {
			if(node.getNode("Left") != null) {
				stack.push(node.getNode("Left"));
				if(operation.equals("Add")) {
					checkAdd(branch,inputA);
				}
				else if(operation.equals("Del")) {
					checkDel(branch, inputA);
					//System.out.println("went through here");
				}
				addToStack(node.getNode("Left"), branch, inputA, operation);
				//System.out.println(inputA);

			}
			if(node.getNode("Middle") != null){
				stack.push(node.getNode("Middle"));
				if(operation.equals("Add")) {
					checkAdd(branch,inputA);
				}
				else if(operation.equals("Del")) {
					checkDel(branch, inputA);
					//System.out.println("went through here");
				}
				addToStack(node.getNode("Middle"), branch, inputA, operation);
				//System.out.println(inputA);
			}
			if(node.getNode("Right") != null) {
				stack.push(node.getNode("Right"));
				if(operation.equals("Add")) {
					checkAdd(branch,inputA);
				}
				else if(operation.equals("Del")) {
					checkDel(branch, inputA);
					//System.out.println("went through here");
				}
				addToStack(node.getNode("Right"), branch, inputA, operation);

			}
			else {
				
				return;
			}
	}
	
	
	//After this method is called the pointer will be pointing to the new node from stack
	public void checkAdd(String branch, String inputA) { 
		Node newNode = stack.pop();
		//System.out.println(newNode.getValue());
		if(newNode.getValue().equals(inputA) && this.pointer == null) {
			if(branch.equals("Left")) {
				this.pointer = newNode;
			}
			else if(branch.equals("Middle")) { 
				this.pointer = newNode; 
			}
			else if(branch.equals("Right")) {
				this.pointer = newNode;
			}
			else {
				return;
			}
		}
		if(newNode.getValue().equals(inputA) && this.pointer.getLevel() <= newNode.getLevel()) { 
			if(branch.equals("Left")) {
				this.pointer = newNode;
			}
			else if(branch.equals("Middle")) { 
				this.pointer = newNode; 
			}
			else if(branch.equals("Right")) {
				this.pointer = newNode;
			}
			else {
				return;
			}	
		}
	}
	
	//This method will check delete
	public void checkDel(String branch, String inputA) {
		Node newNode = stack.pop();
		//System.out.println(newNode.getValue());
		if(newNode.getValue().equals(inputA) && this.pointer == null) {
			if(branch.equals("Left")) {
				this.pointer = newNode;
			}
			else if(branch.equals("Middle")) { 
				this.pointer = newNode; 
			}
			else if(branch.equals("Right")) {
				this.pointer = newNode;
			}
			else {
				return;
			}
			//System.out.println("went through here");
		}
		if(newNode.getValue().equals(inputA) && this.pointer.getLevel() < newNode.getLevel()) { // and we need to check the level
			if(branch.equals("Left")) {
				this.pointer = newNode;
			}
			else if(branch.equals("Middle")) { 
				this.pointer = newNode; 
			}
			else if(branch.equals("Right")) {
				this.pointer = newNode;
			}
			else {
				return;
			}	
		}	
	}
	
	
	//We will need to record the highest value 
	public void checkHighestLevel(Node node) {

		if(node.getNode("Left") != null) {
			stack.push(node.getNode("Left"));
			checkHighestLevel(node.getNode("Left"));
		}
		if(node.getNode("Middle") != null){
			stack.push(node.getNode("Middle"));
			checkHighestLevel(node.getNode("Middle"));
		}
		if(node.getNode("Right") != null) {
			stack.push(node.getNode("Right"));
			checkHighestLevel(node.getNode("Right"));
		}
		else {
			//we need to check the value
			if(stack.getHead() != null) {
				Node newNode = stack.pop(); //This points to whatever we popped out
				if(newNode.getLevel() > highestLevel) {
					this.highestLevel = newNode.getLevel();
				}
			}
			return;
		}
	}

	
	public void printOutput() throws IOException {
		Node newNode = this.head;
		checkHighestLevel(newNode);
		//System.out.println(this.highestLevel);
		if(newNode != null) {
			this.output = this.output + newNode.getValue() + "\n";
		}
			for(int i = 0; i <= this.highestLevel; i++) {
				this.temp = "";
				addLine(newNode, i);
				if(this.temp != "") {
					this.temp = this.temp.substring(0, this.temp.length() -3) + "\n";
				}
				this.output = this.output + this.temp;
				//System.out.println(this.output);
				//System.out.println();
			}
	}
	
	public void addLine(Node node, int level) {
		//System.out.println(level);
		//System.out.println(node.getLevel());
		if(node.getNode("Left") != null) {
			if(node.getNode("Left").getLevel() == level) {
				stack.push(node.getNode("Left"));
				checkLevelPrint(level);
			}
			addLine(node.getNode("Left"), level);
			
		}
		if(node.getNode("Middle") != null) {
			if(node.getNode("Middle").getLevel() == level) {
				stack.push(node.getNode("Middle"));
				checkLevelPrint(level);
			}
			addLine(node.getNode("Middle"), level);
			
		}
		if(node.getNode("Right") != null) {
			if(node.getNode("Right").getLevel() == level) {
				stack.push(node.getNode("Right"));
				checkLevelPrint(level);
			}
			addLine(node.getNode("Right"), level);
		}
		else {
				return;
		}
	}
	
	private void checkLevelPrint(int level) {
		Node newNode = stack.pop();
		//System.out.println(newNode.getLevel());
		if(level == newNode.getLevel()) {
			 this.temp = this.temp + newNode.getValue() + " ; ";
		}
	}

	public static void main(String args[]) throws IOException {
		Tree testTree = new Tree(args[0], args[1]);
		testTree.readFileInput();
		
	}
}
//Fix exchange and fix the special cases in the morning