package org.firstinspires.ftc.teamcode;


import static com.andreidurlea.corecontrol.commandsFeature.ParallelCommandGroupKt.inParallel;
import static com.andreidurlea.corecontrol.commandsFeature.SequentialCommandGroupKt.sequentially;
import static com.andreidurlea.corecontrol.miscellaneous.GlobalObjectsKt.hardwareMapEverywhere;
import static com.andreidurlea.corecontrol.miscellaneous.GlobalObjectsKt.telemetryEverywhere;

import com.andreidurlea.corecontrol.buttonsFeature.ActionButton;
import com.andreidurlea.corecontrol.buttonsFeature.AnalogButton;
import com.andreidurlea.corecontrol.buttonsFeature.Button;
import com.andreidurlea.corecontrol.buttonsFeature.ButtonState;
import com.andreidurlea.corecontrol.commandsFeature.Command;
import com.andreidurlea.corecontrol.commandsFeature.EmptyCommand;
import com.andreidurlea.corecontrol.commandsFeature.InstantCommand;
import com.andreidurlea.corecontrol.commandsFeature.ParallelCommandGroup;
import com.andreidurlea.corecontrol.commandsFeature.RacingCommandGroup;
import com.andreidurlea.corecontrol.commandsFeature.SequentialCommandGroup;
import com.andreidurlea.corecontrol.commandsFeature.TimedCommand;
import com.andreidurlea.corecontrol.commandsFeature.WaitCommand;
import com.andreidurlea.corecontrol.modulesFeature.CoreModule;
import com.andreidurlea.corecontrol.modulesFeature.Module;
import com.andreidurlea.corecontrol.opModesFeature.CoreAuto;
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * <h1> Feature List 1.1.0 </h1>
 * <h4> A list of the main features of CoreControl up until version 1.1.0 </h4>
 *
 * <p>
 *     Hover over each CoreControl class to see a small code example and explanation of it.
 *     More advanced features, not in this preview list, can be found in the tutorials folder
 *     and in the <a href="https://corecontrollib.com">docs</a>.
 * </p>
 */
public class FeatureList_1_1_0 {

    // =============================================================================================
    // [1] Commands - 1.0.0 ------------------------------------------------------------------------
    // =============================================================================================
    Command exampleCommand = new Command(
            () -> {
                /* onStart code here */
            },
            () -> {
                /* onLoop code here */
            },
            () -> {
                /* onEnd code here */
            },
            () -> {
                return true; /* shouldStop condition here */
            }
    );

    // running commands is done with
    void demo() {
        exampleCommand.register();
        // or
        exampleCommand.start();
    }

    // [1.1] Instant Command that runs one thing and ends
    InstantCommand exampleInstantCommand = new InstantCommand(
            () -> {
                /* instant command code here */
            }
    );
    // TIP: You can use instantly { /* code here */ } to create InstantCommands more easily

    // [1.2] Timed Command that runs for a specific duration
    TimedCommand exampleTimedCommand = new TimedCommand(
            1000, // duration in milliseconds
            () -> {
                /* onStart code here */
            },
            () -> {
                /* onEnd code here */
            }
    );
    // TIP: You can use withTiming(1000) { /* onStart code here */ } to create TimedCommands more easily

    // [1.3] Command Groups for combining commands, in parallel, sequentially, or racing
    ParallelCommandGroup exampleParallelCommandGroup = new ParallelCommandGroup(
            exampleCommand,
            exampleInstantCommand,
            exampleTimedCommand
    );
    // TIP: You can use inParallel( /* commands here */ ) to create ParallelCommandGroups more easily

    SequentialCommandGroup exampleSequentialCommandGroup = new SequentialCommandGroup(
            exampleInstantCommand,
            exampleTimedCommand,
            new SequentialCommandGroup( // nesting
                    exampleCommand,
                    exampleInstantCommand
            )
    );
    // TIP: You can use sequentially( /* commands here */) to create SequentialCommandGroups more easily

    // Everything in a racing group ends when the one command ends
    RacingCommandGroup exampleRacingCommandGroup = new RacingCommandGroup(
            exampleCommand,
            exampleTimedCommand
    );
    // TIP: You can use racing( /* commands here */ ) to create RacingCommandGroups more easily

    // [1.4] Wait Command for simply waiting a specific duration
    WaitCommand exampleWaitCommand = new WaitCommand(500 /* duration in milliseconds */);

    // [1.5] Placeholder Empty Command that does nothing and ends immediately
    EmptyCommand exampleEmptyCommand = new EmptyCommand(); // does nothing and ends immediately,
                                                           // useful as a placeholder





    // =============================================================================================
    // [2] Modules - 1.0.0 -------------------------------------------------------------------------
    // =============================================================================================
    public static class DemoSubmodule extends Module { // your classes should not be static!!!
                                                       // this one is static just for the sake of
                                                       // this example
        Servo myServo;


        // [2.1] Creating a STATIC INSTANCE of the module for easy access
        public static DemoSubmodule INSTANCE = new DemoSubmodule();


        // [2.2] Five lifecycle methods that are called automatically
        @Override public void onInit() {
            /* your init code here */
            // [2.3] hardwareMapEverywhere for accessing hardware from anywhere
            hardwareMapEverywhere.get(Servo.class, "myServo");
        }
        @Override public void onInitLoop() { /* your init loop code here */ }
        @Override public void onStart() { /* your start code here */ }
        @Override public void onMainLoop() {
            /* your main loop code here */
            // [2.4] telemetryEverywhere for accessing telemetry from anywhere
            telemetryEverywhere.addData("DemoSubmodule", "Running main loop");
        }
        @Override public void onStop() { /* your stop code here */ }


        // [2.5] Telemetry from modules that is automatically displayed
        @Override public String telemetry() {
            return "Debug message from DemoSubModule";
        }


        // -------------- NEW IN 1.1.0 --------------
        // [2.6] Checking if the module is busy (has running commands)
        Boolean business = DemoModule.INSTANCE.isBusy();
    }
    public static class DemoSubmodule2 extends Module {
        public static DemoSubmodule2 INSTANCE = new DemoSubmodule2();
    }


    // [2.7] Modules can depend on other modules
    public static class DemoModule extends Module {
        public static DemoModule INSTANCE = new DemoModule();


        // [2.8] Specifying dependencies in the Module constructor using super()
        public DemoModule() {
            super(
                    DemoSubmodule.INSTANCE, // this module's lifecycle methods will be automatically
                                            // called before DemoSubmodule's lifecycle methods
                    DemoSubmodule2.INSTANCE
            );
        }

        // -------------- NEW IN 1.1.0 --------------
        // [2.9] Add commands that depend on this module or its submodules as such:
        Command demoCommand =
                (Command) new Command( /* command code here */ )
                        .usingModule(INSTANCE);

        SequentialCommandGroup demoCommand2 =
                (SequentialCommandGroup) new SequentialCommandGroup( /* command code here */ )
                        .usingModules(
                                DemoSubmodule.INSTANCE,
                                DemoSubmodule2.INSTANCE
                        );
    }
    public static class DemoModule2 extends Module {
        public static DemoModule2 INSTANCE = new DemoModule2();
    }





    // =============================================================================================
    // [3] CoreTeleOp - 1.0.0 ----------------------------------------------------------------------
    // =============================================================================================
    public class DemoTeleOp extends CoreTeleOp {

        // [3.1] Using the constructor to add modules (which automatically adds their submodules too)
        public DemoTeleOp() {
            super(
                    DemoModule.INSTANCE,
                    DemoModule2.INSTANCE

                    // More optional parameters are covered at section [4] CoreAuto
                    // They exist for CoreTeleOp too
            );
        }

        // [3.2] Creating simple buttons
        Button exampleButton = new Button(
            () -> gamepad1.square // button condition
        );
        AnalogButton exampleAnalogButton = new AnalogButton(
            () -> gamepad1.left_trigger // analog input condition
        );


        // [3.3] Overriding lifecycle methods to add your own code
        @Override public void onInit() { /* your init code here */ }
        @Override public void onInitLoop() { /* your init loop code here */ }
        @Override public void onStart() { /* your start code here */ }
        @Override public void onMainLoop() {
            /* your main loop code here */

            // [3.4] Checking button states
            if (exampleButton.isHeld()) { /* button is currently held down */ }
            if (exampleButton.isNotHeld()) { /* button is currently not held down */ }

            if (exampleButton.wasPressed()) { /* button was just pressed THIS LOOP */ }
            if (exampleButton.wasReleased()) { /* button is just released THIS LOOP */ }

            if (exampleButton.isToggled()) { /* button toggle state is ON */ }
            if (exampleButton.wasDoublePressed()) { /* button was DOUBLE-CLICKED just THIS LOOP */ }

            if (exampleAnalogButton.getValue() > 0.5) {
                // analog button value is greater than 0.5
            }
        }
        @Override public void onStop() { /* your stop code here */ }

        // -------------- NEW IN 1.1.0 --------------
        // [3.5] Creating action buttons that run commands on specific button state changes
        ActionButton exampleActionButton = new ActionButton(
                () -> gamepad1.right_bumper,
                DemoModule.INSTANCE.demoCommand,
                ButtonState.WHEN_PRESSED
        );
        // this will register demoCommand each time the right bumper is pressed
    }





    // =============================================================================================
    // [4] CoreAuto - 1.0.0 ------------------------------------------------------------------------
    // =============================================================================================
    public static class DemoAuto extends CoreAuto {

        // [4.1] Using the constructor to add modules and the main autonomous command sequences
        public DemoAuto() {
            super(
                    new Module[]{ // The OpMode's modules
                            DemoModule.INSTANCE,
                            DemoModule2.INSTANCE
                    },
                    new SequentialCommandGroup( // The INIT autonomous command sequence
                            DemoModule.INSTANCE.demoCommand,
                            new WaitCommand(200),
                            DemoModule.INSTANCE.demoCommand2
                    ),
                    new SequentialCommandGroup( // The MAIN autonomous command sequence
                            DemoModule.INSTANCE.demoCommand2,
                            new WaitCommand(200),
                            new ParallelCommandGroup(
                                    DemoModule.INSTANCE.demoCommand,
                                    new RacingCommandGroup(
                                            DemoModule.INSTANCE.demoCommand2,
                                            new WaitCommand(500)
                                    )
                            )
                    ),

                    // [4.2] Optional telemetry on/off
                    true, // first param = display MODULES telemetry
                    true, // second param = display COMMANDS telemetry
                    true, // third param = display EXTRA COMMAND LOG telemetry

                    // [4.3] Using the loop-time budgeting system
                    20 // desired MAX main loop time budget in milliseconds

                    // it will try to keep the main loop time under 20 ms by delaying commands if
                    // needed
            );
        }

        // [4.3] Overriding telemetry and logging commands
        @Override public void initTelemetryAndLogging() {
            telemetryEverywhere.addLine("Currently in INIT phase");
        }

        @Override public void mainTelemetryAndLogging() {
            telemetryEverywhere.addLine("Currently in MAIN phase");
        }
    }


    // =============================================================================================
    // END OF FEATURE PREVIEW LIST -----------------------------------------------------------------
    // =============================================================================================
}
