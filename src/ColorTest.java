import lejos.nxt.*;

import static lejos.util.Delay.msDelay;

public class ColorTest implements ButtonListener {

    ColorSensor colorSensor;

    public static void main(String[] a) {
        ColorTest colorTest = new ColorTest();
        colorTest.main_loop();
    }

    public ColorTest(){
        this.colorSensor = new ColorSensor(SensorPort.S2);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop(){
        while(true){
            show_color(this.colorSensor);
            msDelay(250);
        }
    }

    private static void show_color(ColorSensor color_sensor) {
        LCD.clear();
        ColorSensor.Color c = color_sensor.getColor();

        int left = 1;
        int right = 10;

        int line = 2;
        LCD.drawString("Red:", left, line);
        LCD.drawInt(c.getRed(), right, line);
        line++;

        LCD.drawString("Green:", left, line);
        LCD.drawInt(c.getGreen(), right, line);
        line++;

        LCD.drawString("Blue:", left, line);
        LCD.drawInt(c.getBlue(), right, line);
        line++;

        LCD.drawString("Class:", left, line);
        LCD.drawInt(color_sensor.getColorID(),right, line);
        line++;
        /*
        Red : 0
        Green : 1
        Blue : 2
        Yellow: 3
         */
    }

    @Override
    public void buttonPressed(Button button) {
        System.exit(0);
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
