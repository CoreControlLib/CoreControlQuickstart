package org.firstinspires.ftc.teamcode.tutorials.java.res;

import com.andreidurlea.corecontrol.commandsFeature.InstantCommand;
import com.andreidurlea.corecontrol.modulesFeature.Module;
import com.qualcomm.robotcore.hardware.Servo;

public class ExampleModuleArm extends Module {
    public static ExampleModuleArm INSTANCE = new ExampleModuleArm();
    Servo armServo;

    InstantCommand raise = new InstantCommand(
            () -> armServo.setPosition(1.0)
    );

    InstantCommand lower = new InstantCommand(
            () -> armServo.setPosition(0.0)
    );
}
