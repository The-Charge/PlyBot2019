package org.usfirst.frc2619.PlyBot2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SPARXMotor extends Subsystem {

    private CANSparkMax m_Motor;

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
        m_Motor.set(0.2);
    }
    public void stop()
    {
        m_Motor.set(0);
    }


}

