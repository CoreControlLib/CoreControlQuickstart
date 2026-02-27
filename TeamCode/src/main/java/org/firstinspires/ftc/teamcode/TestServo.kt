package org.firstinspires.ftc.teamcode

import com.andreidurlea.corecontrol.buttonsFeature.Button
import com.andreidurlea.corecontrol.miscellaneous.telemetryEverywhere
import com.andreidurlea.corecontrol.opModesFeature.CoreTeleOp
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "Test Servo", group = "Testing")
class TestServo : CoreTeleOp() {

    lateinit var servos: List<Servo>
    var currentServoIndex = 0

    val increasePos = Button({ gamepad1.right_bumper })
    val increasePosByALot = Button({ gamepad1.right_trigger > 0.5 })
    val decreasePos = Button({ gamepad1.left_bumper })
    val decreasePosByALot = Button({ gamepad1.left_trigger > 0.5 })

    val nextServo = Button({ gamepad1.dpad_right })
    val prevServo = Button({ gamepad1.dpad_left })

    override fun onInit() {
        servos = hardwareMap.getAll(Servo::class.java)
        if (servos.isNotEmpty()) {
            servos[currentServoIndex].position = 0.5
        }
    }

    override fun onLoop() {
        if (servos.isEmpty()) {
            telemetryEverywhere.addLine("[INFO] No servos found in config.")
            telemetryEverywhere.addLine("To add a servo in your config, see the /TeamCode/res/xml folder" +
            ", or, tap the three dot menu on your driver station and select \'Configure robot\'")
            return
        }

        if (nextServo.wasPressed()) {
            currentServoIndex = (currentServoIndex + 1) % servos.size
        }
        if (prevServo.wasPressed()) {
            currentServoIndex = (currentServoIndex - 1 + servos.size) % servos.size
        }

        val currentServo = servos[currentServoIndex]
        val servoName = hardwareMap.getNamesOf(currentServo).firstOrNull() ?: "Unknown"

        telemetryEverywhere.addLine("[INFO] This OpMode can find and test any servo in your config")
        telemetryEverywhere.addLine("[INFO] Init starts servo at 0.5")

        telemetryEverywhere.addLine("[INFO] Increment by 0.01 == Right Bumper")
        telemetryEverywhere.addLine("[INFO] Increment by 0.1 == Right Trigger (>50%)")

        telemetryEverywhere.addLine("[INFO] Decrement by 0.01 == Left Bumper")
        telemetryEverywhere.addLine("[INFO] Decrement by 0.1 == Left Trigger (>50%)")

        telemetryEverywhere.addLine("[INFO] Cycle through servos in your config == D-Pad Left/Right")
        telemetryEverywhere.addLine("====================================")
        telemetryEverywhere.addData("Currently testing", "SERVO ${currentServoIndex + 1}/${servos.size}, named ${servoName.uppercase()}")
        telemetryEverywhere.addData("Servo Position", currentServo.position)
        telemetryEverywhere.addLine("====================================")

        if (increasePos.wasPressed()) {
            currentServo.position += 0.01
        }

        if (increasePosByALot.wasPressed()) {
            currentServo.position = ((currentServo.position * 10).toInt() + 1) / 10.0
        }

        if (decreasePos.wasPressed()) {
            currentServo.position -= 0.01
        }

        if (decreasePosByALot.wasPressed()) {
            if ((currentServo.position * 10) == (currentServo.position * 10).toInt().toDouble()) {
                currentServo.position -= 0.1
            }
            currentServo.position = ((currentServo.position * 10).toInt()) / 10.0
        }
    }
}
