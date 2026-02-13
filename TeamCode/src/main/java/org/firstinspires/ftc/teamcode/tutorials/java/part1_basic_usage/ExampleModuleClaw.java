package org.firstinspires.ftc.teamcode.tutorials.java.part1_basic_usage;

import static com.andreidurlea.corecontrol.commandsFeature.InstantCommandKt.instantly;
import static com.andreidurlea.corecontrol.miscellaneous.GlobalObjectsKt.hardwareMapEverywhere;

import com.andreidurlea.corecontrol.commandsFeature.InstantCommand;
import com.andreidurlea.corecontrol.modulesFeature.Module;
import com.qualcomm.robotcore.hardware.Servo;

// TIP: Click the icon on the left side of the comment below to view it in a better way.
/**
 * <h1> Tutorial 1.2: Modules </h1>
 * <h4> Encapsulating parts of your robot in separate classes </h4>
 *
 * <p>
 *     {@link com.andreidurlea.corecontrol.modulesFeature.Module Modules} are a powerful feature of
 *     the CoreControl library that allow you to encapsulate different parts of your robot's
 *     functionality into separate classes. By creating modules, you can organize your code better,
 *     making it more modular, reusable, and easier to maintain.
 * </p>
 * <h4>[1] Creating a Module</h4>
 * <p>
 *     To create a module, you need to create a new class that extends the
 *     {@link com.andreidurlea.corecontrol.modulesFeature.Module Module} class. This class will
 *     represent a specific part of your robot, such as a drivetrain, an arm, or a sensor system.
 *     <b>It's best if each little mechanism of your robot has its own module.</b>
 * </p>
 */
public class ExampleModuleClaw extends Module {

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[1] Creating a static INSTANCE</h4>
     * <p>
     *     In order to reuse this module from everywhere, it's a good idea to create a static
     *     instance as such:
     * </p>
     */
    public static ExampleModuleClaw INSTANCE = new ExampleModuleClaw();

    Servo clawServo;

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[2] The lifecycle methods (again)</h4>
     * <p>
     *     Just like OpModes, Modules also have lifecycle methods that you can override to
     *     implement specific behavior at different stages of the module's lifecycle. The most
     *     important lifecycle method is {@link #onInit onInit()}, which is called when the module
     *     is initialized. This is where you should put your hardware initialization code, such as
     *     mapping motors and servos from the hardware map.
     * </p>
     */
    @Override
    public void onInit() {
        clawServo = hardwareMapEverywhere.get(Servo.class, "clawServo");
        clawServo.setPosition(0.0); // Close the claw initially
    }

    @Override
    public void onInitLoop() {}

    @Override
    public void onStart() {}

    @Override
    public void onMainLoop() {}

    @Override
    public void onStop() {}

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[3] The telemetry method</h4>
     * <p>
     *     Modules also have a {@link #telemetry telemetry()} method that you can override to
     *     provide telemetry data specific to the module. <b>This text will be displayed on the Driver
     *     Station</b> when the module is registered in an OpMode. (more about this later!)
     * </p>
     */
    @Override
    public String telemetry() {
        return "Pos: " + clawServo.getPosition();
    }

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[4] Adding commands to the Module, + learning about lambdas</h4>
     * <p>
     *     In CoreControl, you can create
     *     {@link com.andreidurlea.corecontrol.commandsFeature.Command Commands}, which are reusable
     *     pieces of code that can be executed independently of the main OpMode loop. Modules can
     *     contain commands that are specific to their functionality. For example, you can create
     *     commands to open and close a claw mechanism.
     * </p>
     * <p>
     *     For this part, we'll restrict ourselves to simple {@link InstantCommand InstantCommands},
     *     which execute an action once and then finish. Their syntax is very simple: they take one
     *     <b>lambda function</b> as a parameter, which contains the code to be executed. For
     *     example:
     *     <pre><code>
     *         () -> servo.setPosition(1.0) // is a lambda function
     *     </code></pre>
     *     <pre><code>
     *         () -> {
     *             motor.setPower(0.5);
     *             servo.setPosition(1.0);
     *         }
     *         // is also a lambda function,
     *         // it uses uses { } and ; for multiple lines of code
     *     </code></pre>
     * </p>
     * <p>
     *     You can then create an {@link InstantCommand} using such a lambda function:
     * </p>
     */
    InstantCommand openClawCommand = new InstantCommand(
        () -> clawServo.setPosition(1.0) // Open the claw
    );

    /**
     * You can also use {@link com.andreidurlea.corecontrol.commandsFeature.InstantCommandKt
     * instantly} for a shorter syntax with the same result.
     */
    InstantCommand closeClawCommand = instantly(() -> clawServo.setPosition(0.5));



    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>What you learned</h4>
     * <p>
     *     <ul>
     *         <li>[1] How to create a basic Module</li>
     *         <li>[2] The main lifecycle methods of an OpMode</li>
     *         <li>[3] The telemetry method()</li>
     *         <li>[4] How to create simple commands using lambda functions</li>
     *     </ul>
     * </p>
     */
    String nextTutorial = "1.3 How Do I Create Super Complex Modules?";
}
