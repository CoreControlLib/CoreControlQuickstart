package org.firstinspires.ftc.teamcode

import com.andreidurlea.corecontrol.buttonsFeature.Button
import com.andreidurlea.corecontrol.miscellaneous.telemetryEverywhere
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "Test Servo", group = "Testing")
class TestServo : CoreTeleOp() {

    lateinit var servo: Servo

    var servoName = "EDIT THIS TO CHOOSE YOUR SERVO NAME FROM THE CONFIG"
    // you can find and edits configs in TeamCode/res/xml
    // or in the Driver Station in the three dots menu > "configure robot"

    val increasePos = Button({ gamepad1.right_bumper })
    val increasePosByALot = Button({ gamepad1.right_trigger > 0.5 })
    val decreasePos = Button({ gamepad1.left_bumper })
    val decreasePosByALot = Button({ gamepad1.left_trigger > 0.5 })

    override fun onInit() {
        servo = hardwareMap[Servo::class.java, servoName]
        servo.position = 0.5
    }

    override fun onLoop() {
        telemetryEverywhere.addLine("Init starts servo at 0.5")

        telemetryEverywhere.addLine("Increment by 0.01 == Right Bumper")
        telemetryEverywhere.addLine("Increment by 0.1 == Right Trigger (>50%)")

        telemetryEverywhere.addLine("Decrement by 0.01 == Left Bumper")
        telemetryEverywhere.addLine("Decrement by 0.1 == Left Trigger (>50%)")

        telemetryEverywhere.addData("Servo Position", servo.position)
        telemetryEverywhere.addData("Currently testing", servoName)

        if (increasePos.wasPressed()) {
            servo.position += 0.01
        }

        if (increasePosByALot.wasPressed()) {
            servo.position = ((servo.position * 10).toInt() + 1) / 10.0
        }

        if (decreasePos.wasPressed()) {
            servo.position -= 0.01
        }

        if (decreasePosByALot.wasPressed()) {
            if ((servo.position * 10) == (servo.position * 10).toInt().toDouble()) {
                servo.position -= 0.1
            }
            servo.position = ((servo.position * 10).toInt()) / 10.0
        }
    }
}