package org.usfirst.frc2619.PlyBot2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SPARXMotor extends Subsystem {

    public CANSparkMax m_Motor;

    public SPARXMotor() {
   
        m_Motor = new CANSparkMax(20, MotorType.kBrushless);
    }

    @Override
    public void initDefaultCommand() {
    
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }
    public void run()
    {
        m_Motor.setSmartCurrentLimit(5);
        m_Motor.set(0.1);
        SmartDashboard.putNumber("Output Current", m_Motor.getOutputCurrent());
    }
    public void stop()
    {
        m_Motor.set(0);
    }


}

