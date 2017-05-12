package de.hdm.gruppe1.Project4u.test;

import de.hdm.gruppe1.Project4u.server.db.ProjektmarktplatzMapper;
import de.hdm.gruppe1.Project4u.shared.bo.Projektmarktplatz;

public class ProjektmarktplatzTest {

	public static void main(String[] args) {
		Projektmarktplatz p = new Projektmarktplatz();
		
		
	// p.setName("hdm");
		
		
	ProjektmarktplatzMapper pm = ProjektmarktplatzMapper.projektmarktplatzMapper();
    p=pm.findById(1);
    
    System.out.println(p.getName());

	//pm.insert(p);
}
}
