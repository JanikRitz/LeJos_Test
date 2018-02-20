import lejos.nxt.*;

import static lejos.util.Delay.msDelay;

public class Color_Distance implements ButtonListener{
    Boolean exit;
    ColorSensor color_sensor;
    UltrasonicSensor sonic_sensor;

    public static void main(String[] a) {
        ColorSensor color_sensor = new ColorSensor(SensorPort.S2);

        Color_Distance c_test = new Color_Distance();
        c_test.main_loop();
    }

    public Color_Distance(){
        this.exit = false;
        this.color_sensor = new ColorSensor(SensorPort.S2);
        this.sonic_sensor = new UltrasonicSensor(SensorPort.S1);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop(){
        while(!this.exit){
            show_color_and_distance(this.color_sensor, this.sonic_sensor);
            msDelay(250);
        }
    }

    private void show_color_and_distance(ColorSensor color_sensor, UltrasonicSensor sonic) {
        LCD.clear();
        ColorSensor.Color c = color_sensor.getColor();

        int left = 1;
        int right = 10;

        int line = 1;
        LCD.drawString("Red:", left, line);
        LCD.drawInt(c.getRed(), right, line);
        line++;

        LCD.drawString("Green:", left, line);
        LCD.drawInt(c.getGreen(), right, line);
        line++;

        LCD.drawString("Blue:", left, line);
        LCD.drawInt(c.getBlue(), right, line);
        line++;

        LCD.drawString("Color_ID:", left, line);
        LCD.drawInt(color_sensor.getColorID(),right, line);
        line++;
        /*
        Red : 0
        Green : 1
        Blue : 2
        Yellow: 3
         */

        LCD.drawString("Distance:", left, line);

        int distance = 0;
        distance = sonic.getDistance();

        if (distance == 255){
            LCD.drawString("Infinite", right, line);
        } else {
            LCD.drawInt(distance, right, line);
            LCD.drawString(" units", right+5, line);
        }
    }


    @Override
    public void buttonPressed(Button button) {
        this.exit = true;
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
