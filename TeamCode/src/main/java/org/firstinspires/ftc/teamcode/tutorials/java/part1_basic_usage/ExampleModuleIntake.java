package org.firstinspires.ftc.teamcode.tutorials.java.part1_basic_usage;

import com.andreidurlea.corecontrol.commandsFeature.InstantCommand;
import com.andreidurlea.corecontrol.modulesFeature.Module;

import org.firstinspires.ftc.teamcode.tutorials.java.res.ExampleModuleArm;

// TIP: Click the icon on the left side of the comment below to view it in a better way.
/**
 * <h1> Tutorial 1.3: Complex Modules </h1>
 * <h4> Building upon Modules to create more advanced functionality </h4>
 * <p>
 *     Modules can be as simple or as complex as you need them to be. In this tutorial, we will
 *     explore how to create more complex modules that encapsulate advanced functionality and
 *     behavior, building upon the basic module structure introduced in the previous tutorial.
 * </p>
 * <p>
 *     We will focus on creating modules that not only manage hardware components but also implement
 *     submodules, managing those too. This allows for a hierarchical organization of your robot's
 *     functionality, making it easier to manage and maintain complex systems.
 * </p>
 */
public class ExampleModuleIntake extends Module {

    public static ExampleModuleIntake INSTANCE = new ExampleModuleIntake();

    // TIP: Click the icon on the left side of the comment below to view it in a better way.
    /**
     * <h4>[1] Using the constructor to add submodules</h4>
     * <p>
     *     Firstly, we create a constructor of this class with no parameters. Inside the
     *     constructor, we call the superclass constructor and pass in the submodules that this
     *     module depends  on. In this case, we are passing in
     *     {@link ExampleModuleClaw#INSTANCE} as a dependency.
     */
    public ExampleModuleIntake() {
        super(
                ExampleModuleClaw.INSTANCE, // Claw is now a submodule of this module
                ExampleModuleArm.INSTANCE
                // You can have as many submodules as you want
        );
    }

}
