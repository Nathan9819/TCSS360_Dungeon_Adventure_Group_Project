import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class StackOverFlowTest extends JPanel {
    public static final String PANEL_1 = "panel 1";
    public static final String PANEL_2 = "panel 2";
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private Action fadeAction = new FadeAction(cardPanel);

    public StackOverFlowTest() {
        JLabel label = new JLabel("Panel 1");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 100f));
        panel1.setLayout(new GridBagLayout());
        int gap = 40;
        panel1.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        panel1.add(label);
        panel1.setBackground(Color.PINK);

        label = new JLabel("Panel 2");
        label.setFont(label.getFont().deriveFont(Font.BOLD, 100f));
        panel2.setLayout(new GridBagLayout());
        panel2.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        panel2.add(label);
        panel2.setBackground(new Color(131, 238, 255));


        cardPanel.add(panel1, PANEL_1);
        cardPanel.add(panel2, PANEL_2);

        JButton startFadeBtn = new JButton(fadeAction);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startFadeBtn);

        setLayout(new BorderLayout(5, 5));
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    private static class FadeAction extends AbstractAction {
        private static final int FADE_DELAY = 20;
        private static final int UNFADE_VALUE = 255;
        private JPanel cardPanel;
        private JComponent glassPane;
        private JPanel coverPanel = new JPanel();
        private Timer fadeTimer;
        private int counter = 0;
        private boolean fade = true;

        public FadeAction(JPanel cardPanel) {
            super("Start Fade");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            this.cardPanel = cardPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            counter = 0;
            fade = true;
            setEnabled(false);
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, PANEL_1);
            Window topLevelWindow = SwingUtilities.getWindowAncestor(cardPanel);
            glassPane = (JComponent) ((RootPaneContainer) topLevelWindow).getRootPane().getGlassPane();
            glassPane.setVisible(true);
            glassPane.setLayout(null);
            coverPanel.setSize(cardPanel.getSize());
            int x = cardPanel.getLocationOnScreen().x - glassPane.getLocationOnScreen().x;
            int y = cardPanel.getLocationOnScreen().y - glassPane.getLocationOnScreen().y;
            Point coverPanelPoint = new Point(x, y);
            coverPanel.setLocation(coverPanelPoint);
            glassPane.add(coverPanel);
            fadeTimer = new Timer(FADE_DELAY, e2 -> fadeTimerActionPerformed(e2));
            fadeTimer.start();
        }

        private void fadeTimerActionPerformed(ActionEvent e) {
            coverPanel.setBackground(new Color(0, 0, 0, counter));
            glassPane.repaint();
            if (fade) {
                counter++;
            } else if (counter > 0) {
                counter--;
            } else {
                glassPane.remove(coverPanel);
                glassPane.setVisible(false);
                setEnabled(true);
                ((Timer) e.getSource()).stop();
            }
            if (counter >= UNFADE_VALUE) {
                fade = false;
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, PANEL_2);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new StackOverFlowTest());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}