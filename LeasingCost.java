/**
 *  @author:
 *  Name: Ahnaf Rahim
 *  Email: ar1878@scarletmail.rutgers.edu
 *  NetID: ar1878
 */
public class LeasingCost {
    
    /* 
     * Description:
     *      This method creates an array of Vehicles objects from the given file name
     *      ******IMPORTANT******
     *      This method calls buildVehicle() method, which is STUDENT'S task to complete.
     *
     * Parameters:
     *      filename: the file name containing the vehicles description
     *
     * File format:
     *      the first line of the file containing an integer representing how many vehicles will be described 
     *      in the following lines.
     *      Each line other than the first line describes one vehicle; 
     *      7 or 8 fragments of data in randomly placed order describing the vehicle (7 for gas car, 8 for electric car) in each line. 
     *      Each fragment end with the ';' character
     * 
     *   All possible fragments:
     *      type:FULETYPE;
     *          FULETPE can be gas or electric
     *      name:CARNAME;
     *          CARNAME is the name of the car
     *      due:DUEATSIGNING;
     *          DUEATSIGNING is a double number describing the dollar amount due when signing the lease
     *      length:LEASELENGTH;
     *          LEASELENGTH is an integer number describing the lease length in months
     *      monthly:MONTHLYCOST;
     *          MONTHLYCOST is a double number describing the monthly lease cost in dollar
     *      mile/unit:USAGE; 
     *          USAGE is a double describing miles the car can drive per gallon if fuel type is GAS, or miles the car can drive per kWh if fuel type is ELECTRIC
     *      allowance:MILEAGEALLOWANCE;
     *          MILEAGEALLOWANCE is an integer describing the maximum distance the car is allowed to travel per month
     *      charger:CHARGERCOST;
     *          CHARGERCOST is a double describing the cost of charger for electric cars, this fragment can only appear if the line is describing an electrical car
     *   Example of a line:
     *      type:gas; name:civic; due:1000; length:3; monthly:295; mile/unit:34; 
     *
     * Returns:
     *      this method returns an array of Vehicle objects 
     */
	public static Vehicle[] createAllVehicles(String filename) {
        StdIn.setFile(filename);

        int numberOfCars = Integer.parseInt( StdIn.readLine() );
        Vehicle[] vehicles = new Vehicle[numberOfCars];

        for ( int i = 0; i < numberOfCars; i++ ) {
        	String line = StdIn.readLine();
            vehicles[i] = createVehicle(line);
        }
        return vehicles;
    }

    /* 
     * Description:
     *      This method calculates the CO2 emission given several parameters
     * Parameters:
     *      numberOfMonth: 
     *          the lease length in months
     *      usage: 
     *          miles the car can drive per gallon if fuelType is GAS, or
     *			miles the car can drive per kWh    if fuelType is ELECTRIC
     *      mileageAllowance: 
     *			mileage allowance per month
     *		co2PerUnit:
     *			kg of CO2 released with the combustion of each gallon of gasoline, or
     *			kg of CO2 are emitted to generate 1 kWh on average
     * Returns:
     *      this method returns a double representing the CO2 emission produced by the car during the lease.
     */
	public static double computeCO2(double numberOfMonth, double usage, double mileageAllowance, double co2PerUnit )
    {
		double miles = numberOfMonth * mileageAllowance ;
		return miles/usage*co2PerUnit;
    }

    /* 
     * Description:
     *      This method calculates the cost the fuel during the lease given several parameters
     * Parameters:
     *      numberOfMonth: 
     *          the lease length in months
     *      usage: 
     *          miles the car can drive per gallon if fuelType is GAS, or
     *			miles the car can drive per kWh    if fuelType is ELECTRIC
     *      mileageAllowance: 
     *			mileage allowance per month
     *		fuelPrice: 
     *			price of 1 kWh in cents of a dollar,  if fuelType is GAS, or
     *			price of one gallon of gas in dollars if fuelType is ELECTRIC
     * Returns:
     *      this method returns a double representing the fuel cost during the lease
     */
	public static double computeFuelCost(double numberOfMonth, double usage, double mileageAllowance, double fuelPrice){
    	double miles = numberOfMonth * mileageAllowance;
        double cost = miles/usage * fuelPrice;
    	return cost;
    }

    /* 
     * Description:
     *      This method calculates the cost of lease
     * Parameters:
     *      dueAtSigning: 
     *          the dollar amount due at signing the lease
     *      numberOfMonths: 
     *          lease length in months
     *      monthlyCost: 
     *			cost of the lease per month
     * Returns:
     *      this method returns a double representing the lease cost during the entire lease
     */
	public static double computeLeaseCost(double dueAtSigning, int numberOfMonths, double monthlyCost){
    	
        return dueAtSigning + numberOfMonths*monthlyCost;
    }

    /* 
     * Description:
     *      This method creates and returns an Vehicle object with name, Lease, and Fuel properly populated based on the given string
     *      
     * Parameters:
     *      one string containing 7~8 fragments descrbing the 
     *   All possible fragments:
     *      type:FULETYPE;
     *          FULETPE can be gas or electric
     *      name:CARNAME;
     *          CARNAME is the name of the car
     *      due:DUEATSIGNING;
     *          DUEATSIGNING is a double number describing the dollar amount due when signing the lease
     *      length:LEASELENGTH;
     *          LEASELENGTH is an integer number describing the lease length in months
     *      monthly:MONTHLYCOST;
     *          MONTHLYCOST is a double number describing the monthly lease cost in dollar
     *      mile/unit:USAGE; 
     *          USAGE is a double describing miles the car can drive per gallon if fuel type is GAS, or miles the car can drive per kWh if fuel type is ELECTRIC
     *      allowance:MILEAGEALLOWANCE;
     *          MILEAGEALLOWANCE is an integer describing the maximum distance the car is allowed to travel per month
     *      charger:CHARGERCOST;
     *          CHARGERCOST is a double describing the cost of charger for electric cars, this fragment can only appear if the line is describing an electrical car
     *   Example of a line:
     *          type:gas.name:civic.due:1000.length:3.monthly:295.mile/unit:34.mileageAllowance:1200.
     *          monthly:238.name:Bolt.due:1000.mileageAllowance:20000.length:15.mile/unit:50.type:electric.charger:500.
     * Returns:
     *      this method creates and returns an Vehicle object with name, Lease, and Fuel properly populated
     *
     * Hint: 
     *      to extract the information of each fragments, we can use 
     *          s.substring(int startIndex, int endIndex) 
     *          s.indexOf(String target)
     *          s.indexOf(char target)
     *
     *      for example, assume we have: 
     *          String s = "description1:ABCD;  description2:EFGH;  description3:IJKL;"
     *      if we want to find the data for description 2, we can first take the substring of the entire string from the letter 'E'
     *      but first we need to find the index of 'E', we can do it by find the index of the string "description2:" and add 13("description2" is 13 chars long)to it
     *      and then we can take the substring from 'E' until the end of the entire string
     *      now use s.substring to exract:
     *          "EFGH;  description3:IJKL;" and let's call it newString for now. 
     *      to extract "EFGH" (the data we want) from newString. we need to find the index of the first ';' which we can simply use newString.indexOf(';')
     *      then we can take the substring of the newString knowing the index of ;
     *      we now have extracted "EFGH" from the String s
     *      the last step is just to convert the datatype based on what type of data we are extracting
     */
	public static Vehicle createVehicle(String description)
    {
       
        String tempD = "";

        tempD = description.substring(description.indexOf("type:") + 5);
        String fuelType = tempD.substring(0, tempD.indexOf(';')); 

        tempD = description.substring(description.indexOf("name:") + 5);
        String carName = tempD.substring(0, tempD.indexOf(';')); 

        tempD = description.substring(description.indexOf("due:") + 4);
        String due1 = tempD.substring(0, tempD.indexOf(';')); 
        //convert from string to double 
        double dueatSigning = Double.parseDouble(due1);

        tempD = description.substring(description.indexOf("length:") + 7);
        String lease = tempD.substring(0, tempD.indexOf(';')); 
        //convert from string to int 
        int leaselength = Integer.parseInt(lease); 

        tempD = description.substring(description.indexOf("monthly:") + 8);
        String monthly = tempD.substring(0, tempD.indexOf(';')); 
        //convert from string to double 
        double monthlycost = Double.parseDouble(monthly); 

        tempD = description.substring(description.indexOf("mile/unit:") + 10);
        String mileUsage = tempD.substring(0, tempD.indexOf(';')); 
        //convert from string to double
        double usage = Double.parseDouble(mileUsage); 


        tempD = description.substring(description.indexOf("allowance:") + 10);
        String mileAllow = tempD.substring(0, tempD.indexOf(';')); 
        //convert from string to int
        int mileageAllowance = Integer.parseInt(mileAllow); 
        

        //if statement for gas or electric 
       if(fuelType.equals("electric"))
       {   
           tempD = description.substring(description.indexOf("charger:") + 8);
           String charger = tempD.substring(0, tempD.indexOf(';')); 
           //convert from string to double 
           double chargerCost = Double.parseDouble(charger); 

           //taking the new objects and carName and passing it in as parameters to the vehicle object for an electric car
           Fuel fuelDetails = new Fuel(usage, chargerCost); 
           String nameDetails = carName; 
           Lease leaseDetails = new Lease(dueatSigning, leaselength, monthlycost, mileageAllowance); 
           Vehicle vehicle = new Vehicle(nameDetails, fuelDetails, leaseDetails); 
           return vehicle; 
       }
       else
       {
        //taking the new objects and carName and passing it in as parameters to the vehicle object for an gas car
        Fuel fuelDetails = new Fuel(usage); 
        String nameDetails = carName; 
        Lease leaseDetails = new Lease(dueatSigning, leaselength, monthlycost, mileageAllowance); 
        Vehicle vehicle = new Vehicle(nameDetails, fuelDetails, leaseDetails); 
        return vehicle; 
       }

	}

    /* 
     * Description:
     *      The method calculates and assign CO2Emission, FuelCost, leastCost, of each vehicle.
     *      
     * Parameters:
     *      vehicles: 
     *          an array of Vehicle objects, initilized by getVehicles() method
     *      gasPrice: 
     *          a double representing the price of gas in dollar/gallon
     *      electricityPrice: 
     *			a double representing the price of gas in dollar/kWh
     * Hint:
     *      **********REMEMBER charger cost for electric cars***************
     *      feel free to use:
     *          computeCO2(double numberOfMonth, double usage, double mileageAllowance, double co2PerUnit )
     *          computeFuelCost(double numberOfMonth, double usage, double mileageAllowance, double fuelPrice)
     *          computeLeaseCost(double dueAtSigning, int numberOfMonths, double monthlyCost)
     */
	public static void computeCO2EmissionsAndCost( Vehicle[] vehicles, double gasPrice, double electricityPrice )
        {
            for(int i = 0; i < vehicles.length; i++)
            {
                //new objects to get the lease, fuel and type of fuel
                Lease leaseObj = vehicles[i].getLease();
                Fuel fuelObj = vehicles[i].getFuel();   
                int typeOfFuel = fuelObj.getType(); 
    
                //if statement to check if we're dealing with a gas or electric vehicle 
                if(typeOfFuel == 1)
                {
                    double co2Emission;
                    double total;
                    double leaseCost;
                    double fuelCost;
                    double co2perunit = 8.887; 
                    //assigns the CO2Emissions to each new vehicle object
                    co2Emission = computeCO2(leaseObj.getLeaseLength(), fuelObj.getUsage(), leaseObj.getMileageAllowance(), co2perunit); 
                    //assigns the FuelCost to each new vehicle object
                    fuelCost = computeFuelCost(leaseObj.getLeaseLength(), fuelObj.getUsage(), leaseObj.getMileageAllowance(), gasPrice);
                    //assigns the leaseCost to each new vehicle object 
                    leaseCost = computeLeaseCost(leaseObj.getDueAtSigning(), leaseObj.getLeaseLength(), leaseObj.getMonthlyCost());
    
                    //assign the values to the vehicle objects
                    total = leaseCost+fuelCost;
                    vehicles[i].setCO2Emission(co2Emission);
                    vehicles[i].setFuelCost(fuelCost);
                    vehicles[i].setTotalCost(total);

                }
                else if(typeOfFuel == 2)
                {
                    double co2Emission; 
                    double total;
                    double chargerCost;
                    double leaseCost;
                    double fuelCost;
                    double co2perunit = 0.453; 
                    //assigns the CO2Emissions to each new vehicle object
                    co2Emission = computeCO2(leaseObj.getLeaseLength(), fuelObj.getUsage(), leaseObj.getMileageAllowance(), co2perunit); 
                    //assigns the FuelCost to each new vehicle object
                    fuelCost = computeFuelCost(leaseObj.getLeaseLength(), fuelObj.getUsage(), leaseObj.getMileageAllowance(), electricityPrice);
                    //assigns the leaseCost to each new vehicle object 
                    leaseCost = computeLeaseCost(leaseObj.getDueAtSigning(), leaseObj.getLeaseLength(), leaseObj.getMonthlyCost());
                    //assigns the chargerCost to the vehicle
                    chargerCost = fuelObj.getCharger(); 
                    //assign the values to the vehicle objects
                    total = leaseCost + fuelCost + chargerCost;
                    vehicles[i].setCO2Emission(co2Emission);
                    vehicles[i].setFuelCost(fuelCost);
                    vehicles[i].setTotalCost(total);

                }
                
            }


    	}


    /**
     * How to compile:
     *     javac LeasingCost.java
     * How to run:         
     *     java LeasingCost vehicles.txt 3.85 11.0
     **/
	public static void main (String[] args) {
        String filename         = args[0];
        double gasPrice 		= Double.parseDouble( args[1] );
		double electricityPrice = Double.parseDouble( args[2] );

		Vehicle[] vehicles = createAllVehicles(filename); 
		computeCO2EmissionsAndCost(vehicles, gasPrice, electricityPrice);

		for ( Vehicle v : vehicles ) 
            System.out.println(v.toString());
    }
}
