import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class FadeEffectsTesting {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fade Effects Testing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(new FadeEffectsTesting().getMainPanel());

            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        });
    }

    public static final String[] PANEL_SEQUENCE = { "Panel 1", "Panel 2", "Panel 3", "Panel 4",
            "Panel 5" };

    private int sequence = 0;

    private CardLayout cardLayout;

    private FadeAction action;

    private JPanel cardPanel, mainPanel;

    public FadeEffectsTesting() {
        this.mainPanel = new JPanel(new BorderLayout(5, 5));
        this.cardPanel = createCardPanel();
        this.action = new FadeAction(cardPanel);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.PAGE_END);
    }

    private JPanel createCardPanel() {
        cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        panel.add(createTextPanel(Color.PINK, PANEL_SEQUENCE[0]),
                PANEL_SEQUENCE[0]);
        panel.add(createTextPanel(new Color(131, 238, 255),
                PANEL_SEQUENCE[1]), PANEL_SEQUENCE[1]);
        panel.add(createTextPanel(Color.PINK, PANEL_SEQUENCE[2]),
                PANEL_SEQUENCE[2]);
        panel.add(createTextPanel(new Color(131, 238, 255),
                PANEL_SEQUENCE[3]), PANEL_SEQUENCE[3]);
        panel.add(createTextPanel(Color.PINK, PANEL_SEQUENCE[4]),
                PANEL_SEQUENCE[4]);

        return panel;
    }

    private JPanel createTextPanel(Color color, String text) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(color);
        int gap = 40;
        panel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));

        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 72f));
        panel.add(label);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        setFadeAction();
        JButton startFadeBtn = new JButton(action);
        panel.add(startFadeBtn);

        return panel;
    }

    public void setFadeAction() {
        action.setFromPanel(PANEL_SEQUENCE[sequence]);
        action.setToPanel(PANEL_SEQUENCE[sequence + 1]);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public class FadeAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        private static final int FADE_DELAY = 20;
        private static final int UNFADE_VALUE = 255;

        private JPanel cardPanel;
        private JComponent glassPane;
        private JPanel coverPanel;
        private Timer fadeTimer;
        private int alphaValue;
        private boolean fadeOut;
        private String fromPanel, toPanel;

        public FadeAction(JPanel cardPanel) {
            super("Start Fade");
            this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            this.cardPanel = cardPanel;
            this.alphaValue = 0;
            this.fadeOut = true;
        }

        public void setFromPanel(String fromPanel) {
            this.fromPanel = fromPanel;
        }

        public void setToPanel(String toPanel) {
            this.toPanel = toPanel;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            alphaValue = 0;
            fadeOut = true;
            setEnabled(false);

            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, fromPanel);
            Window topLevelWindow = SwingUtilities.getWindowAncestor(cardPanel);
            glassPane = (JComponent) ((RootPaneContainer) topLevelWindow).getRootPane()
                    .getGlassPane();
            glassPane.setLayout(null);
            coverPanel = new JPanel();
            coverPanel.setSize(cardPanel.getSize());
            glassPane.add(coverPanel);
            glassPane.setVisible(true);

            fadeTimer = new Timer(FADE_DELAY, e2 -> fadeTimerActionPerformed(e2));
            fadeTimer.start();
        }

        private void fadeTimerActionPerformed(ActionEvent event) {
            coverPanel.setBackground(new Color(0, 0, 0, alphaValue));
            glassPane.repaint();

            if (fadeOut) {
                alphaValue += 3;
            } else if (alphaValue > 0) {
                alphaValue -= 3;
            } else {
                glassPane.remove(coverPanel);
                glassPane.setVisible(false);
                ((Timer) event.getSource()).stop();
                if (++sequence < (PANEL_SEQUENCE.length - 1)) {
                    setFadeAction();
                    setEnabled(true);
                }
            }

            if (alphaValue >= UNFADE_VALUE) {
                fadeOut = false;
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, toPanel);
            }
        }

    }

}