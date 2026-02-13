package org.firstinspires.ftc.teamcode.tutorials.java.part1_basic_usage;

import static com.andreidurlea.corecontrol.miscellaneous.GlobalObjectsKt.hardwareMapEverywhere;
import static com.andreidurlea.corecontrol.miscellaneous.GlobalObjectsKt.telemetryEverywhere;

import com.andreidurlea.corecontrol.buttonsFeature.AnalogButton;
import com.andreidurlea.corecontrol.buttonsFeature.Button;
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

// TIP: Click the icon on the left side of the comment below to view it in a better way.
/**
 * <h1> Hello! </h1>
 * <h4> and welcome to a new way of FTC programming, with CoreControl!</h4>
 *
 * <p>
 * This class is a simple example of how to create an
 * {@link com.qualcomm.robotcore.eventloop.opmode.OpMode OpMode} using the CoreControl library.
 * An OpMode is a program that runs on your ControlHub, and you can select it from the Driver
 * Station app. It's either a {@link TeleOp TeleOp}, for driver-controlled programs, or an
 * {@link com.qualcomm.robotcore.eventloop.opmode.Autonomous Autonomous}, for autonomous programs
 * that run without driver input.
 * </p>
 *
 * <h4>[1] CoreTeleOp and CoreAuto</h4>
 * <p>
 * In CoreControl, creating an OpMode is done by creating a class that extends either
 * {@link com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp CoreTeleOp} or
 * {@link com.andreidurlea.corecontrol.opModesFeature.CoreAuto CoreAuto}, depending on whether
 * you want a TeleOp or an Autonomous OpMode.
 * </p>
 *
 * <h4>[2] Annotations</h4>
 * <p>
 *     To make your OpMode visible in the Driver Station app, you need to add the
 *     {@link TeleOp @TeleOp} or
 *     {@link com.qualcomm.robotcore.eventloop.opmode.Autonomous @Autonomous} annotation above your
 *     class declaration. This annotation takes two parameters:
 *     <ul>
 *         <li><b>name</b> - The name that will appear in the Driver Station app.</li>
 *         <li><b>group</b> - The group under which the OpMode
 *         will be categorized in the Driver Station app.</li>
 *     </ul>
 *     You can see how we wrote that in the code below! In this tutorial, we will focus more on the
 *     {@link CoreTeleOp CoreTeleOp} class, so we won't go into details about
 *     {@link com.andreidurlea.corecontrol.opModesFeature.CoreAuto CoreAuto} here. You will learn
 *     about it in a future tutorial.
 * </p>
 */

@Disabled // Remove this line to make the OpMode visible in the driver station!
// The OpMode is currently disabled so that these examples don't clutter your OpMode list.

@TeleOp(name = "[1.1] How Do I Create An OpMode", group = "Examples")
public class ExampleCoreTeleOp extends CoreTeleOp {

    Servo myServo;

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[3] The lifecycle methods</h4>
     * <p>
     *     CoreTeleOp provide several lifecycle methods that you can override (with
     *     {@link java.lang.Override @Override}) to add your own functionality at different stages
     *     of the OpMode's lifecycle. The most commonly used lifecycle methods are:
     *     <ul>
     *         <li><b>onInit()</b> - Called once when the OpMode is initialized. You may
     *         use this method to set up your robot's hardware and initial state. To access the
     *         hardware map of all your robot's sensors and motors, you can use the global
     *         <code>hardwareMapEverywhere</code> variable. </li>
     *         <li><b>onInitLoop()</b> - Called repeatedly while the OpMode is in the init phase,
     *         before the play button is pressed.</li>
     *         <li><b>onStart()</b> - Called once when the play button is pressed.</li>
     *         <li><b>onMainLoop()</b> - Called repeatedly while the OpMode is active (after the play
     *         button is pressed and before the stop button is pressed). <b>This is where you put the
     *         main logic of your OpMode.</b></li>
     *         <li><b>onStop()</b> - Called once when the stop button is pressed. You may use this
     *         method to perform any cleanup or shutdown tasks.</li>
     *     </ul>
     * </p>
     * <p>
     *     In the loop methods, you may use the global <code>telemetryEverywhere</code> variable
     *     to send telemetry data to the Driver Station.
     * </p>
     * <p>
     *     There is also the useful method <code>onLoop()</code>, where you can put shared logic
     *     that runs both during the init phase and the main loop phase. It's used for not writing
     *     the same code twice in both <code>onInitLoop()</code> and <code>onMainLoop()</code>.
     * </p>
     */
    @Override public void onInit() {
        myServo = hardwareMapEverywhere.get(Servo.class, "myServo");
    }

    @Override public void onInitLoop() {
        telemetryEverywhere.addData("STATUS", "Initializing. Press Play to start.");
    }

    @Override public void onStart() {
        myServo.setPosition(0.5);
    }

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[4] Using buttons to control the robot</h4>
     * <p>
     *     In this example, we will use a button to control the position of a servo.
     *     We create a {@link Button Button} object that checks the state of the square button on
     *     gamepad1. When the button is {@link Button#wasPressed() pressed}, we set the servo to
     *     position 0.0, and when it is {@link Button#wasReleased() released},  we set the servo to
     *     position 1.0.
     * </p>
     */
    Button square = new Button( // like a listener for a button
        () -> gamepad1.square
    );
    AnalogButton moveServo = new AnalogButton( // like a listener for an analog input
        () -> gamepad1.left_trigger
    );
    @Override public void onMainLoop() {
        if(square.isHeld()) {
            telemetryEverywhere.addLine("Square is being held!");
        }
        if(square.isToggled()) {
            telemetryEverywhere.addLine("Square is toggled ON!");
        } else {
            telemetryEverywhere.addLine("Square is toggled OFF!");
        }



        if(moveServo.wasPressed()){
            myServo.setPosition(moveServo.getValue());
        }


        telemetryEverywhere.addData("Servo Position", myServo.getPosition());
        telemetryEverywhere.addData("STATUS", "Running main loop. Press Stop to end.");
    }

    @Override public void onStop() {
        myServo.setPosition(0.0);
    }

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>What you learned</h4>
     * <p>
     *     <ul>
     *         <li>How to create a basic OpMode using CoreControl by extending CoreTeleOp.</li>
     *         <li>The purpose of the @TeleOp annotation and how to use it.</li>
     *         <li>The main lifecycle methods of an OpMode and when to use them.</li>
     *     </ul>
     * </p>
     */
    String nextTutorial = "1.2 How Do I Create Modules?";
}
