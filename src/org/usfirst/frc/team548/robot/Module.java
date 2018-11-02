package org.usfirst.frc.team548.robot;


import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Module {
	private TalonSRX drive, turn;
	private final double FULL_ROTATION = 1024d, TURN_P, TURN_I, TURN_D;
	private final int TURN_IZONE;
	
	/**
	 * Lets make a new module :)
	 * @param driveTalonID First I gotta know what talon we are using for driving
	 * @param turnTalonID Next I gotta know what talon we are using to turn
	 * @param tP I probably need to know the P constant for the turning PID
	 * @param tI I probably need to know the I constant for the turning PID
	 * @param tD I probably need to know the D constant for the turning PID
	 * @param tIZone I might not need to know the I Zone value for the turning PID
	 */
	public Module(int driveTalonID, int turnTalonID, double tP, double tI, double tD, int tIZone) {
		drive = new TalonSRX(driveTalonID);
		turn = new TalonSRX(turnTalonID);
		turn.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
		turn.configSetParameter(ParamEnum.eFeedbackNotContinuous, 0, 0x00, 0x00, 0x00);
		
		//turn.setInverted(true);
		this.TURN_P = tP;
		this.TURN_I = tI;
		this.TURN_D = tD;
		this.TURN_IZONE = tIZone;
		
		turn.config_kP(0, this.TURN_P, 0);
		turn.config_kI(0, this.TURN_I, 0);
		turn.config_kD(0, this.TURN_D, 0);
		turn.config_IntegralZone(0, this.TURN_IZONE, 0);
		//turn.setInverted(true);
		turn.setInverted(false);
		//turn.config_IntegralZone((int) TURN_P, (int) TURN_I, (int) TURN_D);
	}
	
	/**
	 * Setting turn motor power
	 * @param p value from -1 to 1
	 */
	public void setTurnPower(double p) {
		this.turn.set(ControlMode.PercentOutput, p);
		//this.turn.set(TalonControlMode.PercentVbus, p);
	}

	/**
	 * Setting drive motor power
	 * @param p value from -1 to 1
	 */
	public void setDrivePower(double p) {
		//this.drive.set(p);
		this.drive.set(ControlMode.PercentOutput, p);
			
	}

	/**
	 * Getting the turn encoder position
	 * @return turn encoder postition
	 */
	public double getTurnEncPos() {
		//return turn.getEncPosition();
		//return turn.getSensorCollection().getQuadraturePosition();
		return turn.getSensorCollection().getAnalogIn();
	}

	/**
	 * Thank you CD ozrien for this!!!
	 * @return
	 */
//	public double getAbsPos() {
//		//return (turn.getPulseWidthPosition() & 0xFFF)/4095d;
//		return ((turn.getSensorCollection().getAnalogIn() & 0xFFF)/4095d);
//	}

	/**
	 * Lets reset the turn encoder position to 0
	 */
	public void restTurnEnc() {
		this.turn.getSensorCollection().setAnalogPosition(0, 10);
	}

	
	public void setEncPos(int d) {
		turn.getSensorCollection().setAnalogPosition(d, 10);
	}
	
	public int getEncPos(){
		return turn.getSensorCollection().getAnalogInRaw();
	}
	/**
	 * Is electrical good? Probably not.... Is the turn encoder connected?
	 * @return true if the encoder is connected
	 */
	public boolean isTurnEncConnected() {
		return true;
		//return turn.(FeedbackDevice.Analog) == FeedbackDeviceStatus.FeedbackStatusPresent;
//		//return turn.
//		int pulseWidthRise = turn.getSensorCollection().getPulseWidthRiseToRiseUs();
//		if(pulseWidthRise == 0){
//			return false;
//		}else{
//			return true;
//		}
	}
	
	public int getTurnRotations() {
		//return (int) (turn.getEncPosition() / FULL_ROTATION);
		return (int) (turn.getSelectedSensorPosition(0)/FULL_ROTATION);
	}
	
	
	public double getTurnLocation() {
		//return (turn.getEncPosition() % FULL_ROTATION) / FULL_ROTATION;
		return turn.getSensorCollection().getAnalogInRaw()/FULL_ROTATION;
	}
	


//	public void setTurnPIDToSetPoint(double setpoint) {
////		turn.changeControlMode(TalonControlMode.Position);
////		turn
//		turn.set(ControlMode.Position, setpoint);
//	}
	
	/**
	 * Set turn to pos from 0 to 1 using PID
	 * @param setLoc location to set to
	 */	
	public void setTurnLocation(double loc) {
//		double currentWheelPos = this.getTurnEncPos();
//		//System.out.println("TURN ENC: " + turn.getSensorCollection().getAnalogInRaw());
//		double rotations = (int) Math.round(currentWheelPos);
//		double localNorm = rotations * 1024;
//		
//		double currentWheelPercent = (currentWheelPos - localNorm) / localNorm;
//		double currentWheelTheta = currentWheelPercent * 360;
//		
//		double angleDifference = angle + Math.abs(currentWheelTheta);
//		
////		if(angleDifference > 180){
////			angleDifference = 360 - angleDifference;
////		}
//		
//		//angleDifference / 360) * currentWheelPos) + currentWheelPos = alpha
//		double rightSideConstant = angleDifference / 360;
//		rightSideConstant = rightSideConstant * currentWheelPos;
//		
//		double alpha = rightSideConstant + currentWheelPos;
		
//		System.out.println("CURRENT POS: " + currentWheelPos);
//		System.out.println("ALPHA: " + alpha);
		System.out.println("LOCATION: " + loc);
		turn.set(ControlMode.Position, loc);
		
		
		
		
		
	}
	
	
	
	
	
	public double getError() {
		return turn.getClosedLoopError(0);
		//return 0.0;
	}
	
	public void stopBoth() {
		setDrivePower(0);
		setTurnPower(0);
	}
	
	public void stopDrive() {
		setDrivePower(0);
	}
	
	public void setBreakMode(boolean b) {
		if(b){
			drive.setNeutralMode(NeutralMode.Brake);
		}else{
			drive.setNeutralMode(NeutralMode.Coast);
		}
	}	
}