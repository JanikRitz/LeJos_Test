import lejos.nxt.*;

import static lejos.util.Delay.msDelay;

public class Color_Test_L implements ButtonListener{
    Boolean exit;
    ColorSensor color_sensor;

    public static void main(String[] a) {
        ColorSensor color_sensor = new ColorSensor(SensorPort.S2);

        Color_Test_L c_test = new Color_Test_L();
        c_test.main_loop();
    }

    public Color_Test_L(){
        this.exit = false;
        this.color_sensor = new ColorSensor(SensorPort.S2);
        Button.ESCAPE.addButtonListener(this);
    }

    private void main_loop(){
        while(!this.exit){
            show_color(this.color_sensor);
            msDelay(250);
        }
    }

    private void show_color(ColorSensor color_sensor) {
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
        this.exit = true;
    }

    @Override
    public void buttonReleased(Button button) {

    }
}
