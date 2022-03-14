import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

class OrderingSystem{
	
	private ArrayList<ComputerPart> parts = new ArrayList<ComputerPart>();
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Order> submittedOrder = new ArrayList<Order>();
	private Customer currentCustomer;
	private Order currentOrder;
	
	public static void main(String[] args) {
		
		OrderingSystem OS = new OrderingSystem();
		Scanner sc = new Scanner(System.in);
		System.out.printf("\nWelcome to our computer shop.\n");
		OS.initaliseShop();
		boolean menu = true;
		int userInput = 0;
		try {
			while (menu){
				//The menu that will be displayed
				System.out.println("Please make a select from the following items");
				System.out.println("1: login as a member customer");
				System.out.println("2: login as a new customer");
				System.out.println("3: add computer parts");
				System.out.println("4: veiw/modify the current order");
				System.out.println("5: submit the current order");
				System.out.println("6: view all submitted orders");
				System.out.println("7: exist the online shop");
				
				userInput = sc.nextInt();
				
				/*
				For getting an int input you need this next line. It seems to be an issue with the scanner
				package that this needs to be said to stop reading the input. This problem doesn't happen
				with other value types as far as I can tell its only needed for int
				*/
				sc.nextLine();
				
				switch (userInput) {
					
					case 1:
						System.out.println("Please provide your customer ID");
						
						String userID = sc.nextLine();
						
						//Sets a true boolean value so it will look through the entire list unless it finds what its looking for
						boolean boo = false;
						
						//Goes through all the customers and checks to see if what the user inputed is in the current system
						for (Customer c: OS.customers) {
							if (c.getID().equals(userID)) {
								
								//Creates new discount order 
								Order memberOrder = new DiscountOrder();
								OS.currentOrder = memberOrder;
								OS.currentCustomer = c;
								boo = true;
								System.out.printf("Welcome back! " + c.getName() + "\n\n");
							}
						}
						
						//This will search check to see if a user has been found if not this will execute
						if (boo != true) {
							System.out.printf("Sorry, your ID can't be found. Please login as a new customer\n\n");
						}
						break;
					
					case 2:
						System.out.println("For a new customer, please input personal information name, gender, mobile number, delivery address");
							
							//Gets the users input
							String newUser = sc.nextLine();
							
							//Takes all the commas out between the numbers
							String[] userAtt = newUser.split(",");
							
							//This is an arraylist that will save all the attributes the user inputs
							ArrayList<String> customerAttributes = new ArrayList<String>();
							
							//Cycles through the input to save the attributes
							for (int k = 0; k < userAtt.length; k++) {
								customerAttributes.add(userAtt[k]);
							}	
							
							//I can use hardcoded values 0 - 3 as a new customer will only have 4 values anymore and there is an error in the input
							Customer newCustomer = new Customer(customerAttributes.get(0), customerAttributes.get(1), customerAttributes.get(2), customerAttributes.get(3));
							Order newMember = new Order();
							
							//Sets the current member and current order
							OS.currentOrder = newMember;
							OS.currentCustomer = newCustomer;
						break;
					
						
					case 3:
					//Gets all the parts that currently exsit and print them out on there each individual line
						String text = "";
						for(int i =0; i < OS.getComputerParts().size(); i++){
							text += "(" + i + "): " + OS.getComputerParts().get(i) + "\n";
						}
						System.out.printf(text);
						
						//This boolean will mean the meny you will run until i tell it not to
						boolean innerMenu = true;
		
						while (innerMenu) {
						System.out.printf("Please select part/s (-1 for return to the main menu).\n");
							
							//Scans for the user's input
							String addParts = sc.nextLine();
							String[] msg = addParts.split(",");
							
							//Searches through the entire string for integers which we will use to add the parts to the order
							for (int l = 0; l < msg.length; l++) {
								
								//Checks the string for the -1 which will stop the loop
								if (Integer.parseInt(msg[l]) == -1) {
									innerMenu = false;
									System.out.printf(" \n");
								} else {
									ComputerPart p = OS.getComputerParts().get(Integer.parseInt(msg[l]));
									//Creates a deep copy of the part then adds it to the order
									if (p instanceof AMDCPU) {
										OS.currentOrder.addComputerPart(new AMDCPU((AMDCPU)p));
									} else if (p instanceof IntelCPU) {
										OS.currentOrder.addComputerPart(new IntelCPU((IntelCPU)p));
									} else if(p instanceof AMDMotherBoard) {
										OS.currentOrder.addComputerPart(new AMDMotherBoard((AMDMotherBoard)p));
									} else if (p instanceof IntelMotherBoard) {
										OS.currentOrder.addComputerPart(new IntelMotherBoard((IntelMotherBoard)p));
									} else if (p instanceof Memory) {
										OS.currentOrder.addComputerPart(new Memory((Memory)p));
									} else if(p instanceof AMDGraphicsCard) {
										OS.currentOrder.addComputerPart(new AMDGraphicsCard((AMDGraphicsCard)p));
									} else if (p instanceof IntelGraphicsCard) {
										OS.currentOrder.addComputerPart(new IntelGraphicsCard((IntelGraphicsCard)p));
									} else if (p instanceof Monitor) {
										OS.currentOrder.addComputerPart(new Monitor((Monitor)p));
									}
								}
							}
						}
						break;
					
					case 4:
						System.out.printf("Here is the summary of your current order.\n");
						System.out.printf("" + OS.currentCustomer);
						System.out.printf("" + OS.currentOrder + "\n\n");
						System.out.println("Please select part/s to be removed from the order (-1 to return to main menu).");
						
						boolean menu4 = true;
						
						while (menu4) {
							
							//Scans for the user's input
							String removeParts = sc.nextLine();
							String[] msg = removeParts.split(",");
							
							//Searches through the entire string for integers which we will use to add the parts to the order
							for (int l = 0; l < msg.length; l++) {
								
								//Checks the string for the -1 which will stop the loop
								if (Integer.parseInt(msg[l]) == -1) {
									menu4 = false;
									System.out.printf(" \n");
								} else {
									OS.currentOrder.removeComputerPart(OS.getComputerParts().get(Integer.parseInt(msg[l])));
								}
							}
						}
						
						break;
						
					case 5:
						System.out.println("For a new customer, please input personal information name, gender, mobile number, delivery address");
						System.out.printf("" + OS.currentCustomer);
						System.out.printf("" + OS.currentOrder + "\n");
						System.out.printf("\nYour order has been submitted. Thanks for your purchase.\n\n");
						
						//Submits the current order to the submitted order list
						OS.submittedOrder.add(OS.currentOrder);
						
						break;
						
					case 6:
						System.out.println("Here are all your submitted orders.");
						System.out.printf("" + OS.currentCustomer + "\n");
						
						//This will go through and print all the orders
						for (int i = 0; i < OS.submittedOrder.size(); i++) {
							System.out.printf("Order " + i + ":" + OS.submittedOrder.get(i) + "\n");
						}
						
						System.out.println("\nPlease select order/s to be cancelled (-1 for return to menu).");
						
						boolean menu6 = true;
						
						while (menu6) {
							String removeOrders = sc.nextLine();
							String[] msg = removeOrders.split(",");
							
							for (int l = 0; l < msg.length; l++) {
									
								//Checks the string for the -1 which will stop the loop
								if (Integer.parseInt(msg[l]) == -1) {
									menu6 = false;
									System.out.printf(" \n");
								} else {
									OS.submittedOrder.remove(Integer.parseInt(msg[l]));
								}
							}
						}
						break;
					
					case 7:
						System.out.println("Thanks for visiting our shop, see you next time!");
						menu = false;
						break;
				}
			}
		} catch (Exception e){
			//This is to catch all errors mostly for wrong inputs
			System.out.println("An error has occured. Please make sure your inputs are correct!");
			
			//Remove comments below to print out error line when debugging
			//e.printStackTrace();
		}
	}
		
		private void initaliseShop() {
		
		//Creates CPU's and then loads them into an ArrayList
		CPU intel9600k = new IntelCPU("i5", "9600K", 323);
		addComputerPart(intel9600k);
		CPU intel9700k = new IntelCPU("i7", "9700K", 462);
		addComputerPart(intel9700k);
		CPU intel9700F = new IntelCPU("i7", "9700F", 396);
		addComputerPart(intel9700F);
		CPU intel9900k = new IntelCPU("i9", "9900k", 591);
		addComputerPart(intel9900k);
		
		CPU AMD2200 = new AMDCPU("4", "Ryzen 2200", 200);
		addComputerPart(AMD2200);
		CPU AMD3600 = new AMDCPU("6", "Ryzen 3600", 310);
		addComputerPart(AMD3600);
		CPU AMD3700 = new AMDCPU("8", "Ryzen 3700", 489);
		addComputerPart(AMD3700);
		CPU AMD5800 = new AMDCPU("8", "Ryzen 5800", 669);
		addComputerPart(AMD5800);
		
		//Creates MotherBoard's and loads them into an ArrayList
		MotherBoard H81M_DS2 = new IntelMotherBoard("Gigabyte", "H81M-DS2", 129);
		addComputerPart(H81M_DS2);
		MotherBoard J40051_C = new IntelMotherBoard("Asus", "J40051-C", 169);
		addComputerPart(J40051_C);
		MotherBoard Mpg_2390 = new IntelMotherBoard("Msi", "Mpg-2390", 225);
		addComputerPart(Mpg_2390);
		MotherBoard Z490 = new IntelMotherBoard("Gigabyte", "Z490", 471);
		addComputerPart(Z490);
		MotherBoard B_450 = new AMDMotherBoard("Gigabyte", "B-450", 117);
		addComputerPart(B_450);
		MotherBoard A320I = new AMDMotherBoard("Asus", "A320I", 128);
		addComputerPart(A320I);
		MotherBoard B450 = new AMDMotherBoard("Msi", "B450", 232);
		addComputerPart(B450);
		MotherBoard X570S = new AMDMotherBoard("Gigabyte", "X570S", 679);
		addComputerPart(X570S);
		
		//Creates Memory data and loads it into an ArrayList
		Memory KCP316ND8 = new Memory("DDR3", "Kingston", "8G", "KCP316ND8", 116);
		addComputerPart(KCP316ND8);
		Memory AX4U360038G18 = new Memory("DDR3", "ADATA", "16G", "AX4U360038G18", 189);
		addComputerPart(AX4U360038G18);
		Memory F3_10666CL9D = new Memory("DDR3", "G.Skill", "8G", "F3-10666CL9D", 96);
		addComputerPart(F3_10666CL9D);
		Memory KCP426SS8 = new Memory("DDR4", "Kingston", "8G", "KCP426SS8", 93);
		addComputerPart(KCP426SS8);
		Memory F4_2666C18S = new Memory("DDR4", "G.Skill", "16G", "F4-2666C18S", 158);
		addComputerPart(F4_2666C18S);
		Memory CT32G4SFD832A = new Memory("DDR4", "Crucial", "32G", "CT32G4SFD832A", 259);
		addComputerPart(CT32G4SFD832A);
		
		//Creates graphics cards and loads them into an ArrayList
		GraphicsCard Gigabyte_3070 = new IntelGraphicsCard("Gigabyte", "GeForce RTX 3070", 1999);
		addComputerPart(Gigabyte_3070);
		GraphicsCard Asus_3070 = new IntelGraphicsCard("Asus", "GeForce RTX 3070", 1899);
		addComputerPart(Asus_3070);
		GraphicsCard Msi_3080 = new IntelGraphicsCard("Msi", "GeForce RTX 3080", 3099);
		addComputerPart(Msi_3080);
		GraphicsCard Gigabyte_6900 = new AMDGraphicsCard("Gigabyte", "Radeon RX 6900", 3699);
		addComputerPart(Gigabyte_6900);
		GraphicsCard Asus_6900 = new AMDGraphicsCard("Asus", "Radeon RX 6900", 3199);
		addComputerPart(Asus_6900);
		GraphicsCard Msi_6900 = new AMDGraphicsCard("Msi", "Radeon RX 6900", 2699);
		addComputerPart(Msi_6900);
		
		//Creates monitors and loads them into an ArrayList
		Monitor Acer_K242HYLB = new Monitor("Acer", "24", "K242HYLB", 194);
		addComputerPart(Acer_K242HYLB);
		Monitor LG_32QN600 = new Monitor("LG", "32", "32QN600", 506);
		addComputerPart(LG_32QN600);
		Monitor Asus_MB16ACZ  = new Monitor("Asus", "16", "MB16ACZ ", 429);
		addComputerPart(Asus_MB16ACZ);
		Monitor Msi_MP271QP = new Monitor("LG", "27", "MP271QP", 399);
		addComputerPart(Msi_MP271QP);
		Monitor BenQ_PD3200Q = new Monitor("BenQ", "32", "PD3200Q", 653);
		addComputerPart(BenQ_PD3200Q);
		Monitor Philips_272M8CZ = new Monitor("Philips", "27", "272M8CZ", 289);
		addComputerPart(Philips_272M8CZ);		
		
		//Creates a list of customers and adds them to an ArrayList
		Customer C000001 = new MemberCustomer("C000001", "Amy Bell", "Female", "04211111", "No. 1, NoName Street, NeverLand, 0000.");
		addCustomer(C000001);
		Customer C000002 = new MemberCustomer("C000002", "Bob Brown", "Male", "04212222", "No. 2, NoName Street, NeverLand, 0000.");
		addCustomer(C000002);
		Customer C000003 = new MemberCustomer("C000003", "Cindy Ma", "Female", "04213333", "No. 3, NoName Street, NeverLand, 0000.");
		addCustomer(C000003);
		Customer C000004 = new MemberCustomer("C000004", "David Hintz", "Male", "04214444", "No. 4, NoName Street, NeverLand, 0000.");
		addCustomer(C000004);
		Customer C000005 = new MemberCustomer("C000005", "Rex White", "Male", "04215555", "No. 5, NoName Street, NeverLand, 0000.");
		addCustomer(C000005);
		}
		
		public ArrayList<Customer> getCustomers() {
			return customers;
		}
		
		public ArrayList<ComputerPart> getComputerParts() {
			return parts;
		}
		
		public void addCustomer(Customer c) {
			customers.add(c);
		}
		
		public void addComputerPart(ComputerPart p) {
			parts.add(p);
		}
}

class Customer implements java.io.Serializable {
	private final String ID;
	private String name;
	private String gender;
	private String mobile;
	private String address;
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	/*
	What do these 3 constructors do? Its called method overloading or more like constructor overloading here.
		1. Constructor with no arguments. Makes a almost 'blank' object if we dont know the attributes yet
		2. Makes a Customer if we dont have an ID yet. Used for new customers
	    3. If its an exsisting customer which alread has an ID. Only used in intialisation
	*/
	
	Customer() {
		this.ID = "";
		this.name = "";
		this.gender = "";
		this.mobile = "";
		this.address = "";
	}
	
	Customer(String n, String g, String mN, String dA) {
		this.ID = "C" + (int)(Math.random()*(100000));
		this.name = n;
		this.gender = g;
		this.mobile = mN;
		this.address = dA;
	}
	
	Customer(String i, String n, String g, String mN, String dA) {
		this.ID = i;
		this.name = n;
		this.gender = g;
		this.mobile = mN;
		this.address = dA;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public String getAddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setMobile(String mobileNumber) {
		this.mobile = mobile;
	}
	
	public void setAddress(String Address) {
		this.address = address;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public void removeOrder(int i) {
		orders.remove(i);
	}

	public String toString(){
		return "Customer: " + ID + "\t" + name + "\t" + gender + "\t" + mobile + "\t" + address;
	}
}

class MemberCustomer extends Customer {
	private final double DISCOUNT = 0.05;
		
	public MemberCustomer () {
		super();
	}

	public MemberCustomer(String i, String n, String g, String mN, String dA) {
		super(i, n, g, mN, dA);
	}
	
	public double getDISCOUNT() {
		return DISCOUNT;
	}
	
	public String toString() {
		return super.toString() + "\t" + DISCOUNT;
	}
}

class Order implements java.io.Serializable{
	private final String ID;
	private ArrayList<ComputerPart> orderParts = new ArrayList<ComputerPart>();
	private double totalPrice;
	
	Order() {
		this.ID = "O" + ((int)(Math.random()*(100000)) + 10000);
		this.totalPrice = 0;
	}

	public String getID() {
		return ID;
	}
	
	public void addComputerPart(ComputerPart p) {
		calculateTotalPrice();
		orderParts.add(p);
	}
	
	public void removeComputerPart(ComputerPart p) {
		orderParts.remove(p);
	}
	
	public ArrayList<ComputerPart> getComputerParts() {
		return orderParts;
	}
	
	public void calculateTotalPrice() {
		totalPrice = 0;
		for(int i =0; i < orderParts.size(); i++){
			totalPrice += orderParts.get(i).getPrice();
		}		
	}
	
	public int getTotalPrice() {
		return (int)totalPrice;
	}

	public String toString() {
		
		calculateTotalPrice();
		
		String text = "";
		String compatable = "";
		
		for (int i = 0; i < orderParts.size(); i++) {
			text += "%n" + i + "\t" + orderParts.get(i) + "\t";
				compatable = "Not compatable with [ ";
				for (int k = 0; k < orderParts.size(); k++) {	
					if ((orderParts.get(i).isCompatible(orderParts.get(k))) == false) {
						compatable += orderParts.get(k).getID();
					}
				}
			text += compatable + " ]";
		}
		return text + "\nOrder ID: " + ID + "   Total Price: $" + totalPrice;
	}
}

class DiscountOrder extends Order {
	private double discount = 0.05;
	
	DiscountOrder() {
		super();
		this.discount = discount;
	}
	
	DiscountOrder(double d) {
		super();
		this.discount = d;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public double moneySaved() {
		calculateTotalPrice();
		return (getTotalPrice() - calculateDiscountTotal());
		
	}
	
	public double calculateDiscountTotal(){
		calculateTotalPrice();

		return (getTotalPrice() * discount);
	}
	
	@Override public String toString() {
		return "" + super.toString() + "\nMember Customer Discount: $" + calculateDiscountTotal() + "\t" + "Total Price after the Discount: $" + moneySaved();
	}
}

interface Compatible {
	public boolean isCompatible(ComputerPart p);
}

abstract class ComputerPart implements Compatible, java.io.Serializable{
	private final String ID;
	private final String BRAND;
	private final String MODEL;
	private double price;
	
	ComputerPart() {
		this.ID = "";
		this.BRAND = "";
		this.MODEL = "";
		this.price = 0;
	}
	
	ComputerPart(String i, String b, String m, double p){
		this.ID = i;
		this.BRAND = b;
		this.MODEL = m;
		this.price = p;
	}
	
	ComputerPart (ComputerPart cp) {
		this.ID = cp.ID;
		this.BRAND = cp.BRAND;
		this.MODEL = cp.MODEL;
		this.price = cp.price;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getBrand() {
		return BRAND;
	}
	
	public String getModel() {
		return MODEL;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double newPrice) {
		this.price = newPrice;
	}
	
	public boolean isCompatible(ComputerPart p) {
		return true;
	}
	
	public String toString() {
		return "" + ID + "\t" + BRAND + "\t" + MODEL + "\t $" + price;
	}
}

abstract class CPU extends ComputerPart {
	private final String CORE;
	
	CPU() {
		super();
		this.CORE = "";
	}
	
	CPU(String b, String m, double p) {
		super("CPU" + (int)(Math.random()*90000 + 10000), b, m, p);
		this.CORE = "Unspecified";
	}
	
	CPU(String i, String b, String m, double p) {
		super(i + (int)(Math.random()*90000 + 10000), b, m, p);
		this.CORE = "Unspecified";
	}
	
	CPU(String i, String b, String c, String m, double p) {
		super(i + (int)(Math.random()*90000 + 10000), b, m, p);
		this.CORE = c;
	}
	
	CPU (CPU c) {
		super(c);
		this.CORE = c.CORE;
	}
	
	public String getCore() {
		return CORE;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + CORE;
	}
}

class IntelCPU extends CPU {
	private final String SOCKET;
	
	IntelCPU() {
		super();
		this.SOCKET = "Intel";
	}

	IntelCPU(String m, String c, double p) {
		super("INTCPU", "Intel", m, c, p);
		this.SOCKET = "Intel";
	}
	
	IntelCPU(IntelCPU IC) {
		super(IC);
		this.SOCKET = IC.SOCKET;
	}
	
	public String getSocket(){
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof IntelCPU || p instanceof IntelMotherBoard || p instanceof IntelGraphicsCard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "" + super.getID() + "\t" + super.getBrand() + "\t" + super.getModel() + "\t $" + super.getPrice() + "\t" + super.getCore() + "\t" + getSocket();
	}
}

class AMDCPU extends CPU {
	private final String SOCKET;
	
	AMDCPU() {
		super();
		this.SOCKET = "AMD";
	}

	AMDCPU(String m, String c, double p) {
		super("AMDCPU", "AMD", m, c, p);
		this.SOCKET = "AMD";
	}
	
	AMDCPU(AMDCPU AC) {
		super(AC);
		this.SOCKET = AC.SOCKET;
	}
	
	public String getSocket(){
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof AMDCPU || p instanceof AMDGraphicsCard || p instanceof AMDMotherBoard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "" + super.getID() + "\t" + super.getBrand() + "\t" + super.getModel() + "\t $" + super.getPrice() + "\t" + super.getCore() + "\t" + getSocket();
	}
}

abstract class MotherBoard extends ComputerPart {
	
	MotherBoard() {
		super();
	}
	
	MotherBoard(String b, String m, double p) {
		super("MOT" + (int)(Math.random()*90000 + 10000), b, m, p);
	}
	
	MotherBoard(String i, String b, String m, double p) {
		super(i + (int)(Math.random()*90000 + 10000), b, m, p);
	}
	
	MotherBoard (MotherBoard MB) {
		super(MB);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}

class IntelMotherBoard extends MotherBoard{
	private final String SOCKET;
	
	IntelMotherBoard() {
		super();
		this.SOCKET = "";
	}
	
	IntelMotherBoard(String b, String m, double p) {
		super("INTMOT", b, m, p);
		this.SOCKET = "Intel";
	}
	
	IntelMotherBoard (IntelMotherBoard IMB) {
		super(IMB);
		this.SOCKET = IMB.SOCKET;
	}
	
	public String getSocket() {
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof IntelCPU || p instanceof IntelGraphicsCard || p instanceof IntelMotherBoard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + SOCKET;
	}
}

class AMDMotherBoard extends MotherBoard{
	private final String SOCKET;
	
	AMDMotherBoard() {
		super();
		this.SOCKET = "";
	}
	
	AMDMotherBoard(String b, String m, double p) {
		super("AMDMOT", b, m, p);
		this.SOCKET = "AMD";
	}
	
	AMDMotherBoard (AMDMotherBoard AMB) {
		super(AMB);
		this.SOCKET = AMB.SOCKET;
	}
	
	public String getSocket() {
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof AMDCPU || p instanceof AMDGraphicsCard || p instanceof AMDMotherBoard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + SOCKET;
	}
}

abstract class GraphicsCard extends ComputerPart {
	
	GraphicsCard(){
		super();
	}
	
	GraphicsCard(String b, String m, double p){
		super("GRA" + (int)(Math.random()*90000 + 10000), b, m, p);
	}
	
	GraphicsCard(String i, String b, String m, double p){
		super(i + (int)(Math.random()*90000 + 10000), b, m, p);
	}

	GraphicsCard (GraphicsCard GC) {
		super(GC);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}

class IntelGraphicsCard extends GraphicsCard{
	private final String SOCKET;
	
	IntelGraphicsCard() {
		super();
		this.SOCKET = "";
	}
	
	IntelGraphicsCard(String b, String m, double p) {
		super("INTGRA", b, m, p);
		this.SOCKET = "Intel";
	}
	
	IntelGraphicsCard (IntelGraphicsCard IGC) {
		super(IGC);
		this.SOCKET = IGC.SOCKET;
	}
	
	public String getSocket() {
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof IntelCPU || p instanceof IntelGraphicsCard || p instanceof IntelMotherBoard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + SOCKET;
	}
}

class AMDGraphicsCard extends GraphicsCard{
	private final String SOCKET;
	
	public AMDGraphicsCard() {
		super();
		this.SOCKET = "";
	}
	
	public AMDGraphicsCard(String b, String m, double p) {
		super("AMDGRA", b, m, p);
		this.SOCKET = "AMD";
	}
	
	AMDGraphicsCard (AMDGraphicsCard AGC) {
		super(AGC);
		this.SOCKET = AGC.SOCKET;
	}
	
	public String getSocket() {
		return SOCKET;
	}
	
	@Override
	public boolean isCompatible(ComputerPart p) {
		if ( p instanceof AMDCPU || p instanceof AMDGraphicsCard || p instanceof AMDMotherBoard || p instanceof Monitor || p instanceof Memory ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t" + SOCKET;
	}
}

class Memory extends ComputerPart {
	private final String SOCKET;
	private final String SIZE;
	
	Memory() {
		super();
		this.SOCKET = "";
		this.SIZE = "";
	}
	
	Memory(String s, String b, String si, String m, double p){
		super("MEM" + (int)(Math.random()*90000 + 10000), b, m, p);
		this.SOCKET = s;
		this.SIZE = si;
	}
	
	Memory (Memory MEM) {
		super(MEM);
		this.SOCKET = MEM.SOCKET;
		this.SIZE = MEM.SIZE;
	}
	
	public String getSocket() {
		return SOCKET;
	}
	
	public String getSize() {
		return SIZE;
	}
	
	@Override
	public String toString() {
		return "" + super.getID() + "\t" + super.getBrand() + "\t" + super.getModel() + "\t $" + super.getPrice() + "\t" + SOCKET + "\t" + SIZE + "G";
	}
}

class Monitor extends ComputerPart {
	private final String SIZE;
	
	Monitor() {
		super();
		this.SIZE = "";
	}
	
	Monitor(String b, String s, String m, double p){
		super("MON" + (int)(Math.random()*90000 + 10000), b, m, p);
		this.SIZE = s;
	}
	
	Monitor (Monitor mon) {
		super(mon);
		this.SIZE = mon.SIZE;
	}
	
	@Override
	public String toString() {
		return "" + super.getID() + "\t" + super.getBrand() + "\t" + super.getModel() + "\t$" + super.getPrice() + "\t" + SIZE;
	}
}
/*
Get the most up to date version @ https://github.com/TheBluesBand/OrderingSystem
*/
