package Model;

import java.util.ArrayList;

public class Country {
	private int xCoor, yCoor;
	
	private int peopleCount;
	
	private int healthyPeople;
	private int infectedPeople;
	private int sickPeople;
	private int immunePeople;
	private int deadPeople;
	
	public ArrayList<Person> people;
	private Country[] neighbourCountries;
	
	public Country(int peopleCount, int xCoor, int yCoor){
		this.peopleCount = peopleCount;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		
		people = new ArrayList<Person>();
		neighbourCountries = new Country[4];
		
		for(int i=0; i<peopleCount; i++){
			addPerson(PersonGenerator.createNewPerson(this));
		}
		
	}
	
	public boolean containsVisiblyInfectious(){
		if(sickPeople > 0 || deadPeople > 0) return true;
		return false;
	}
	
	public Country[] getNeighbourCountries(){
		return this.neighbourCountries;
	}
	
	public void performNextDay(){
		for(int i = 0; i < people.size(); i++){
			Person person = people.get(i);
			person.performNextDay();
		}
	}

	public void addPerson(Person person){
		people.add(person);
		peopleCount++;
	}
	
	public void removePerson(Person person){
		people.remove(person);
		peopleCount--;
	}
	
	public void updateNationsHealth(){
		PersonVisitor v = new PersonVisitor();
		for(Person person:people){
			person.accept(v);
		}
		healthyPeople  = v.getHealthyPeople();
		infectedPeople = v.getInfectedPeople();
		sickPeople     = v.getSickPeople();
		immunePeople   = v.getImmunePeople();
		deadPeople     = v.getDeadPeople();
	}
	
	public int getHealthyPeople(){
		return this.healthyPeople;
	}
	
	public int getInfectedPeople(){
		return this.infectedPeople;
	}
	
	public int getSickPeople(){
		return this.sickPeople;
	}
	
	public int getImmunePeople(){
		return this.immunePeople;
	}
	
	public int getDeadPeople(){
		return this.deadPeople;
	}
	
	public void setHealthyPeople(int count){
		this.healthyPeople = count;
	}
	
	public void setInfectedPeople(int count){
		this.infectedPeople = count;
	}
	
	public void setSickPeople(int count){
		this.sickPeople = count;
	}
	
	public void setImmunePeople(int count){
		this.immunePeople = count;
	}
	
	public void setDeadPeople(int count){
		this.deadPeople = count;
	}
	
	
}

