import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import static lejos.util.Delay.msDelay;

import javax.bluetooth.RemoteDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Queue;

public class BTMaster implements BTGeneric {
    private final int countOthers;
    private MapNavigationPilot pilot;
    private String[] otherNxt;
    private RemoteDevice[] otherNxtRemote;
    private BTConnection[] otherNxtConnection;
    private DataInputStream[] otherNxtInput;
    private DataOutputStream[] otherNxtOutput;
    private Queue<Integer> data;


    public BTMaster(String nameOther) {
        this(new String[]{nameOther});
    }

    public BTMaster(String[] otherNXT) {
        this.otherNxt = otherNXT;
        this.data = new Queue<Integer>();
        this.countOthers = otherNXT.length;

        this.otherNxtRemote = new RemoteDevice[countOthers];
        this.otherNxtConnection = new BTConnection[countOthers];
        this.otherNxtInput = new DataInputStream[countOthers];
        this.otherNxtOutput = new DataOutputStream[countOthers];
    }


    public void init(MapNavigationPilot pilot) {
        this.pilot = pilot;
    }

    public int commLoop() {
        for (int device = 0; device < this.countOthers; device++) {
            this.otherNxtRemote[device] = Bluetooth.getKnownDevice(this.otherNxt[device]);

            if (this.otherNxtRemote[device] != null) {
                this.otherNxtConnection[device] = Bluetooth.connect(this.otherNxtRemote[device]);
                if (this.otherNxtConnection[device] != null) {
                    this.otherNxtInput[device] = otherNxtConnection[device].openDataInputStream();
                    this.otherNxtOutput[device] = otherNxtConnection[device].openDataOutputStream();
                }
            }
        }

        while (true) {
            for (int device = 0; device < this.countOthers; device++) {
                if (this.otherNxtConnection[device] != null) {
                    if (BTMapComm.sendData(this.otherNxtOutput[device], this.data)) return -4;
                }
            }
            for (int device = 0; device < this.countOthers; device++) {
                if (this.otherNxtConnection[device] != null) {
                    BTMapComm.receiveData(this.otherNxtInput[device], this.pilot);
                }
            }
            msDelay(3000);
        }
    }

    public void addObject(int x, int y, MapNavigationPilot.MapObject object) {
        this.data.push(x);
        this.data.push(y);
        this.data.push(object.ordinal()); // TODO Check for correctness
    }

    @Override
    public void run() {
        this.commLoop();
    }
}
