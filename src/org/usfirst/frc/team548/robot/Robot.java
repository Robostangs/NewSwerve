
package org.usfirst.frc.team548.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private XboxController xbox;
	private DriverStation dt;
    public void robotInit() {
      xbox = new XboxController(Constants.XBOX_PORT);
      DriveTrain.getInstance();
      dt = DriverStation.getInstance();
    }
    
    @Override
    public void autonomousInit() {
    	
    }
    
    @Override
    public void autonomousPeriodic() {
    	DriveTrain.setAllLocation(0);
    }
    
    public void disabledInit() {
    	DriveTrain.resetOffSet();
    }
    public void disabledPeriodic() {
    	DriveTrain.setOffSets();
    	xbox.setRightRumble(0);
    	xbox.setLeftRumble(0);
    	DriveTrain.disablePID();
    }
    
    @Override
    public void teleopInit() {
    	//DriveTrain.resetAllEnc();
    }
    @Override
    public void teleopPeriodic() {    	
    	DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), powTwoThing(xbox.getRightStickXAxis()));
    	//DriveTrain.fieldCentricDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), 0.30);
    	 //DriveTrain.pidDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), changeAngle(xbox.getRightStickXAxis(), xbox.getRightStickYAxis()));
    	 //DriveTrain.tankDrive(xbox.getLeftStickYAxis(), xbox.getRightStickYAxis());
    	 //DriveTrain.humanDrive(xbox.getLeftStickYAxis(), xbox.getLeftStickXAxis(), Math.pow(xbox.getRightStickXAxis(), 3));
    	
    	SmartDashboard.putBoolean("Big Bird Turn Encoder", DriveTrain.isBigBirdTurnEncConnected());
    	SmartDashboard.putBoolean("Big Horse Turn Encoder", DriveTrain.isBigHorseTurnEncConnected());
    	SmartDashboard.putBoolean("Big Giraffe Turn Encoder", DriveTrain.isBigGiraffeTurnEncConnected());
    	SmartDashboard.putBoolean("Big Sushi Turn Encoder", DriveTrain.isBigSushiTurnEncConnected());
    	SmartDashboard.putNumber("Hyro", DriveTrain.getHyroAngle());
    	SmartDashboard.putNumber("Avg. Error", DriveTrain.getAverageError());
    	
    	SmartDashboard.putNumber("Big Bird Position", DriveTrain.getBigBirdEncPos());
    	SmartDashboard.putNumber("Big Sushi Encoder Position", DriveTrain.getBigSushiEncPos());
    	SmartDashboard.putNumber("Big Horse Encoder Position", DriveTrain.getBigHorseEncPos());
    	SmartDashboard.putNumber("Big Giraffe Encoder Position", DriveTrain.getBigGiraffeEncPos());

    	//System.out.println("isBrowning: " + RobotController.isBrownedOut());
    	xbox.setRightRumble(Math.pow(DriveTrain.getAverageError()/1300d, 2));
    	xbox.setLeftRumble((RobotController.isBrownedOut() ? 1 : 0));
    	
    	//BB, BH, BG, BS
    	SmartDashboard.putNumber("Big Bird Offset Position", DriveTrain.GLOBALbboff);
    	SmartDashboard.putNumber("Big Horse Offset Position", DriveTrain.GLOBALbhoff);
    	SmartDashboard.putNumber("Big Sushi Offset Position", DriveTrain.GLOBALbsoff);
    	SmartDashboard.putNumber("Big Giraffe Offset Position", DriveTrain.GLOBALbgoff);
    	System.out.println(DriveTrain.getBigGiraffeEncPos());
    	//DriveTrain.setAllTurnPower(0.20);
    }
    
    @Override
    public void testPeriodic() {
    	//DriveTrain.setTurnPower(0, 0.3, 0, 0);
    	//DriveTrain.setLocation(0, 300, 0, 0);
    	//DriveTrain.setAllDrivePower(0.20);
    	///System.out.println(DriveTrain.getBigHorseEncPos());
    	//DriveTrain.setLocation(0, 865, 0, 0);
    	//DriveTrain.setDrivePower(0, -0.3, 0, 0);
    	DriveTrain.setTurnPower(0, 0, -0.3, 0);
    	System.out.println(DriveTrain.getBigGiraffeEncPos());
    }
    
    
    
    private double powTwoThing(double v) {
    	return (v > 0 ) ? Math.pow(v, 2) : -Math.pow(v, 2);
    }
    
    double lastAngle = 0;
    public double changeAngle(double x, double y) {
    	if (Math.sqrt((x*x)+(y*y)) > .25) {
    		lastAngle = Math.atan2(x, y)*(180d/Math.PI);
			return lastAngle;
		} else {
			return lastAngle;
		}
    }
    
}
