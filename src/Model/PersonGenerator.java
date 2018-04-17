package Model;

import java.util.Random;

import Controller.EpidemicSimulationManager;

public class PersonGenerator {
	public static Person createNewPerson(Country country){
		Random rand = new Random();
		HealthStatus healthStatus = HealthStatus.Healthy;
		if(rand.nextDouble() < EpidemicSimulationManager.infectedPercentage / 100) healthStatus=HealthStatus.Infected;
		Person person = null;
		if(rand.nextDouble() < EpidemicSimulationManager.superPercentage / 100){
			person = new SuperPerson(country);
		}else if(rand.nextDouble() < EpidemicSimulationManager.doctorPercentage / 100){
			person = new Doctor(healthStatus, country, EpidemicSimulationManager.vaccineLimit);
		}else{
			person = new NormalPerson(healthStatus, country);
		}
		return person;
	}
}
