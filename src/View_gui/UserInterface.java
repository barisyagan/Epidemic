package View_gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controller.EpidemicSimulationManager;

@SuppressWarnings("serial")
public class UserInterface extends JFrame {
	
	private CountryTable countryTable;
	private JButton nextDayButton;
	
	public UserInterface(EpidemicSimulationManager simulationManager){
		setLayout(new BorderLayout());
		
		setTitle("Epidemic Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		countryTable = new CountryTable(simulationManager.getCountryCount());
		add(countryTable);
		
		nextDayButton = new JButton();
		nextDayButton.setText("Day: 0");
		nextDayButton.addActionListener(new NextDayButtonListener(simulationManager));
		add(nextDayButton, BorderLayout.PAGE_END);
	}
	
	public Object[][] getTableContent(){
		return this.countryTable.tableContent;
	}
	
	public void updateTable(){
		this.countryTable.jTable.repaint();
	}
}

@SuppressWarnings("serial")
class CountryTable extends JPanel {
	Object[][] tableContent;
	JTable jTable;
	
	public CountryTable(int countryCount){
		super(new GridLayout(1,0));
		
		String[] columns = {"Country Name","Healthy People", "Infected People", "Sick People", "Immune People", "Dead People"};
		
		tableContent = new Object[countryCount + 1][6];
		tableContent[0][0] = "World";
		for(int i=1; i<tableContent.length; i++){
			tableContent[i][0] = "Country " + i;
		}
		
		JTable table = new JTable(tableContent, columns);
		jTable = table;
        //table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
	}

}

class NextDayButtonListener implements ActionListener {
	private EpidemicSimulationManager simulationManager;
	
	public NextDayButtonListener(EpidemicSimulationManager simulationManager){
		this.simulationManager = simulationManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		simulationManager.performNextDayButton();
		JButton button = (JButton) e.getSource();
		button.setText("Day: " + simulationManager.currentDay);
	}
	
}
