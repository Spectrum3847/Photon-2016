package org.spectrum3847.robot.subsystems;

import org.spectrum3847.lib.drivers.SpectrumSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PhotonCannon extends Subsystem{

	private SpectrumSolenoid retractSol;
	private Boolean arePhotons = false;
	private String name;

	public PhotonCannon(String n, SpectrumSolenoid r) {
		super(n);
		name = n;
		retractSol = r;

		retractSol.set(true);

	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void noPhotons(){
		System.out.println("PHOTONS OFF");
		retractSol.set(false);
		arePhotons = false;
	}
	
	public void photons(){
		System.out.println("PHOTONS ON");
		retractSol.set(true);
		arePhotons = true;
	}
	
	public boolean arePhotons(){
		return arePhotons;
	}

}
