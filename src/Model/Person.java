package Model;

import java.util.ArrayList;
import java.util.Random;

import Controller.EpidemicSimulationManager;

public abstract class Person {
	private HealthStatus healthStatus;
	private int daysOfInfected;
	private int whenToMove;
	
	protected Country currentCountry;
	
	public boolean isImmuneForLife = false;

	public Person(HealthStatus healthStatus, Country country){
		setDaysOfInfected(0);
		currentCountry    = country;
		this.healthStatus = healthStatus;
		decideWhenToMove();
	}
	
	public void accept(PersonVisitor visitor){
		visitor.visit(this);
	}
	
	public void setHealthStatus(HealthStatus healthStatus){
		if(this.healthStatus == healthStatus) return;
		this.healthStatus = healthStatus;
	}
	
	public void setDaysOfInfected(int daysOfInfected){
		this.daysOfInfected = daysOfInfected;
	}
	
	public HealthStatus getHealthStatus(){
		return healthStatus;
	}
	
	public int getDaysOfInfected(){
		return daysOfInfected;
	}
	
	public Country getCurrentCountry() {
		return currentCountry;
	}
	
	public void setImmuneForLife(){
		isImmuneForLife = true;
	}
	
	private void checkHealthStatus(){
		if(isImmuneForLife) return;
		
		switch(healthStatus){
		case Healthy:
			break;
		case Infected:
			daysOfInfected++;
			if(daysOfInfected == 6){
				setHealthStatus(HealthStatus.Sick);
			}
			break;
		case Sick:
			daysOfInfected++;
			if(daysOfInfected == 14){
				// Dies with a probability of 25%
				Random rand = new Random();
				if(rand.nextInt(4) < 1) setHealthStatus(HealthStatus.Dead);
			}
			if(daysOfInfected == 16){
				setHealthStatus(HealthStatus.Immune);
			}
			break;
		case Immune:
			daysOfInfected++;
			if(daysOfInfected == 18){
				setHealthStatus(HealthStatus.Healthy);
				daysOfInfected = 0;
			}
			break;
		case Dead:
			break;
		}
	}
	
	private Country decideCountryToMove(){
		Random rand = new Random();
		if(rand.nextDouble() < EpidemicSimulationManager.airTrafficPercentage / 100){
			Country randomCountry = WorldGrid.getRandomCountry();
			while(randomCountry.equals(currentCountry)){
				randomCountry = WorldGrid.getRandomCountry();
			}
			return randomCountry;
		}
		Country country = null;
		ArrayList<Country> movableCountries = new ArrayList<Country>();
		for (Country neighbourCountry : currentCountry.getNeighbourCountries())
			if (!neighbourCountry.containsVisiblyInfectious()) movableCountries.add(neighbourCountry);
		if(movableCountries.size() == 0) return country;
		int r = rand.nextInt(movableCountries.size());
		country = movableCountries.get(r);
		return country;
	}
	
	private void infectAfterMove(){
		if(healthStatus == HealthStatus.Infected || healthStatus == HealthStatus.Sick ||healthStatus == HealthStatus.Immune) return;
		if(currentCountry.getInfectedPeople() > 0 || currentCountry.getSickPeople() > 0 || currentCountry.getImmunePeople() > 0){
			Random rand = new Random();
			if(rand.nextDouble() < 0.40){
				this.setHealthStatus(HealthStatus.Infected);
			}
		}
	}
	
	public void move(){
		Country country = decideCountryToMove();
		if(country == null) return; // Surrounded by visibly infectious countries
		country.addPerson(this);
		currentCountry.removePerson(this);
		currentCountry = country;
		infectAfterMove();
	}
	
	private void decideWhenToMove(){
		Random rand = new Random();
		whenToMove = rand.nextInt(5) + 1;
	}
	
	private void checkMovement(){
		if(healthStatus == HealthStatus.Dead) return;
		if(whenToMove == 0){
			EpidemicSimulationManager.peopleToBeMoved.add(this);
			decideWhenToMove();
		}else{
			whenToMove--;
		}
	}
	
	public void performNextDay(){
		checkHealthStatus();
		checkMovement();
	}
}

