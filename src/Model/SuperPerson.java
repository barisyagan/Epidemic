package Model;

public class SuperPerson extends Person{

	public SuperPerson(Country country) {
		super(HealthStatus.Healthy, country);
		isImmuneForLife = true;
	}
	
}
