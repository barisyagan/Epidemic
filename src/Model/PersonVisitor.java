package Model;

public class PersonVisitor {
	private int healthyPeople;
	private int infectedPeople;
	private int sickPeople;
	private int immunePeople;
	private int deadPeople;
	
	public void visit (Person person){
		switch (person.getHealthStatus()) {
		case Healthy:
			healthyPeople++;
			break;
		case Infected:
			infectedPeople++;
			break;
		case Sick:
			sickPeople++;
			break;
		case Immune:
			immunePeople++;
			break;
		case Dead:
			deadPeople++;
			break;
		default:
			healthyPeople++;	
		}
	}

	public int getHealthyPeople() {
		return healthyPeople;
	}

	public int getInfectedPeople() {
		return infectedPeople;
	}

	public int getSickPeople() {
		return sickPeople;
	}
	
	public int getImmunePeople() {
		return immunePeople;
	}

	public int getDeadPeople() {
		return deadPeople;
	}
	
	

}
