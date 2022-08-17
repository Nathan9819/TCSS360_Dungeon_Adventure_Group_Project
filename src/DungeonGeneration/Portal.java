
import javax.swing.*;
import java.net.URL;

public class Portal extends Entity{
    private boolean opened;

    public Portal(final boolean theBoolean) {
        super(52, 52, 200);
        opened = theBoolean;
        setPortalImage();
    }

    private void setPortalImage() {
        URL myUrl;
        if(opened) {
            myUrl = getClass().getResource("Assets/PortalOpen.png");
        } else {
            myUrl = getClass().getResource("Assets/PortalClosed.png");
        }
        assert myUrl != null;
        super.setSprite(new ImageIcon(myUrl));
    }
}
