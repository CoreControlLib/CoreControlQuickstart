package org.firstinspires.ftc.teamcode

import com.andreidurlea.corecontrol.buttonsFeature.AnalogButton
import com.andreidurlea.corecontrol.miscellaneous.telemetryEverywhere
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "Test Motor", group = "Testing")
class TestDcMotor : CoreTeleOp() {

    lateinit var motor: DcMotor

    var motorName = "EDIT THIS TO CHOOSE YOUR SERVO NAME FROM THE CONFIG"
    // you can find and edits configs in TeamCode/res/xml
    // or in the Driver Station in the three dots menu > "configure robot"

    val spin = AnalogButton({ gamepad1.right_trigger })
    val spinInReverse = AnalogButton({ gamepad1.left_trigger })

    override fun onInit() {
        motor = hardwareMap[DcMotor::class.java, motorName]
        motor.direction = DcMotorSimple.Direction.FORWARD // Starts robot in forward (CW) direction
    }

    override fun onLoop() {
        telemetryEverywhere.addLine("Spin (Clowckwise) == Right Trigger (0-100%)")
        telemetryEverywhere.addLine("Spin (Counterclockwise) == Left Trigger (0-100%)")

        telemetryEverywhere.addData("Currently testing", motorName)

        motor.power = spin.value - spinInReverse.value
    }
}