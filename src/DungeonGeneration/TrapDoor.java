import javax.swing.*;
import java.net.URL;

public class TrapDoor extends Entity{
    private boolean opened;

    public TrapDoor(final boolean theBoolean) {
        super(52, 52, 200);
        opened = theBoolean;
        setTrapDoorImage();
    }

    private void setTrapDoorImage() {
        URL myUrl;
        if(opened) {
            myUrl = getClass().getResource("Assets/TrapDoorOpen.png");
        } else {
            myUrl = getClass().getResource("Assets/TrapDoorClosed.png");
        }
        assert myUrl != null;
        super.setSprite(new ImageIcon(myUrl));
    }

    public boolean isOpened() {
        return opened;
    }
}