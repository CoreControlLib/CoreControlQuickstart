package org.firstinspires.ftc.teamcode

import com.andreidurlea.corecontrol.buttonsFeature.Button
import com.andreidurlea.corecontrol.miscellaneous.telemetryEverywhere
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior

@TeleOp(name = "Test DcMotor", group = "Testing")
class TestDcMotor : CoreTeleOp() {

    lateinit var motors: List<DcMotor>
    var currentMotorIndex = 0
    var direction = 1.0

    val reverseDirection = Button({ gamepad1.right_bumper })

    val nextMotor = Button({ gamepad1.dpad_right })
    val prevMotor = Button({ gamepad1.dpad_left })

    override fun onInit() {
        motors = hardwareMap.getAll(DcMotor::class.java)
        motors.forEach { it.zeroPowerBehavior = ZeroPowerBehavior.FLOAT }
        telemetryEverywhere.addLine("To add a motor in your config, see the /TeamCode/res/xml folder" +
                ", or, tap the three dot menu on your driver station and select 'Configure robot'")
    }

    override fun onLoop() {
        if (motors.isEmpty()) {
            telemetryEverywhere.addLine("No DC motors found.")
            return
        }

        val previousMotorIndex = currentMotorIndex
        if (nextMotor.wasPressed()) {
            currentMotorIndex = (currentMotorIndex + 1) % motors.size
        }
        if (prevMotor.wasPressed()) {
            currentMotorIndex = (currentMotorIndex - 1 + motors.size) % motors.size
        }
        if (previousMotorIndex != currentMotorIndex) {
            motors[previousMotorIndex].power = 0.0
        }


        val currentMotor = motors[currentMotorIndex]
        val motorName = hardwareMap.getNamesOf(currentMotor).firstOrNull() ?: "Unknown"

        if (reverseDirection.wasPressed()) {
            direction *= -1.0
        }

        telemetryEverywhere.addLine("[INFO] This OpMode can find and test any motor in your config")
        telemetryEverywhere.addLine("[INFO] Control power with Right Trigger")
        telemetryEverywhere.addLine("[INFO] Reverse direction with Right Bumper")
        telemetryEverywhere.addLine("[INFO] Hold Left Bumper to brake the motor")
        telemetryEverywhere.addLine("[INFO] Cycle through motors in your config == D-Pad Left/Right")
        telemetryEverywhere.addLine("====================================")
        telemetryEverywhere.addData("Currently testing", "MOTOR ${currentMotorIndex + 1}/${motors.size}, named ${motorName.uppercase()}")
        telemetryEverywhere.addData("Motor Power", currentMotor.power)
        telemetryEverywhere.addData("Motor Direction",
            if (direction > 0) "FORWARD (Positive power spins clockwise)"
            else "REVERSE (Negative power spins counter-clockwise)")
        telemetryEverywhere.addLine("====================================")

        if (gamepad1.left_bumper) {
            currentMotor.power = 0.0
            currentMotor.zeroPowerBehavior = ZeroPowerBehavior.BRAKE
        } else {
            currentMotor.zeroPowerBehavior = ZeroPowerBehavior.FLOAT
            val power = gamepad1.right_trigger * direction
            if (power > 0.05 || power < -0.05) {
                currentMotor.power = power
            } else {
                currentMotor.power = 0.0
            }
        }
    }
}
