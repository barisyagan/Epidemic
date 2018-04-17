package Model;

public class Doctor extends Person{
	private int vaccineLimit;
	private int vaccineCounter;

	public Doctor(HealthStatus healthStatus, Country country,int vaccineLimit) {
		super(healthStatus, country);
		this.vaccineLimit = vaccineLimit;
	}

	public boolean isAvaliable(){
		return vaccineLimit > vaccineCounter;
	}
	
	private void vaccinate(){
		for(Person person: currentCountry.people){
			if(person.getHealthStatus() == HealthStatus.Healthy){
				if(isAvaliable()){
					person.setImmuneForLife();
					vaccineCounter++;
				}else{
					break;
				}
			}
		}
	}
	
	@Override
	public void performNextDay(){
		vaccinate();
		super.performNextDay();
		vaccineCounter = 0;
	}
}
