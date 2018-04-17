package Runner;

import Controller.EpidemicSimulationManager;
import Model.WorldGrid;

public class EpidemicSimulation {
	public static void main(String[] args){
		
		if(args.length != 7){
			System.out.println("Error: Invalid number of arguments.");
			System.exit(0);
		}
		
		final int    peopleCount           = Integer.parseInt(args[0]);
		final double infectedPercentage    = Double.parseDouble(args[1]);
		final int    n                     = Integer.parseInt(args[2]);
		final double superPercentage       = Double.parseDouble(args[3]);
		final double doctorPercentage      = Double.parseDouble(args[4]);
		final int    vaccineLimit          = Integer.parseInt(args[5]);
		final double airTrafficPercentage  = Double.parseDouble(args[6]);
		
		System.out.println("Parameters: " + peopleCount + " " + infectedPercentage + " " + n + " " + 
		                   superPercentage + " " + doctorPercentage + " " + vaccineLimit + " " + airTrafficPercentage);
		
		EpidemicSimulationManager.infectedPercentage   = infectedPercentage;
		EpidemicSimulationManager.superPercentage      = superPercentage;
		EpidemicSimulationManager.doctorPercentage     = doctorPercentage;
		EpidemicSimulationManager.vaccineLimit         = vaccineLimit;
		EpidemicSimulationManager.airTrafficPercentage = airTrafficPercentage;
		
		WorldGrid worldGrid = new WorldGrid(peopleCount, n);
		EpidemicSimulationManager epidemicSimulationManager = new EpidemicSimulationManager(worldGrid);
		epidemicSimulationManager.startSimulation();
	}
}
