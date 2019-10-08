package org.kodnuts.jute.ui;

import org.pushingpixels.flamingo.api.common.CommandActionEvent;
import org.pushingpixels.flamingo.api.common.JCommandButtonStrip;
import org.pushingpixels.flamingo.api.common.model.Command;
import org.pushingpixels.flamingo.api.common.model.CommandGroup;
import org.pushingpixels.flamingo.api.common.model.CommandStripPresentationModel;
import org.pushingpixels.flamingo.api.common.projection.CommandStripProjection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class Jute {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            JFrame frame = new JFrame("Text alignment demo");
            frame.setLayout(new BorderLayout());

            // Configure and populate "Lorem ipsum" multiline content
            final JTextPane textPane = new JTextPane();
            textPane.setText(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim" +
                            " veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                            "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                            "voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui " +
                            "officia deserunt mollit anim id est laborum.");
            textPane.setEditable(true);
            textPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            // force the display of text selection even when the focus has been lost
            textPane.setCaret(new DefaultCaret() {
                @Override
                public void setSelectionVisible(boolean visible) {
                    super.setSelectionVisible(true);
                }
            });

            frame.add(textPane, BorderLayout.CENTER);
            JPanel styleButtonPanel = new JPanel(new FlowLayout());
            frame.add(styleButtonPanel, BorderLayout.LINE_END);

            // Show our frame in the center of the screen
            frame.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
            frame.setSize(new Dimension(600, 300));
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);

            // Align left command
            Command commandAlignLeft = Command.builder()
                    //.setIconFactory(Format_justify_left.factory())
                    .setAction((CommandActionEvent event) ->
                            setAlignment(textPane, StyleConstants.ALIGN_LEFT))
                    //.inToggleGroupAsSelected(justifyToggleGroup)
                    .build();

            // Align center command
            Command commandAlignCenter = Command.builder()
                    //.setIconFactory(Format_justify_center.factory())
                    .setAction((CommandActionEvent event) ->
                            setAlignment(textPane, StyleConstants.ALIGN_CENTER))
                    //.inToggleGroup(justifyToggleGroup)
                    .build();

            // Align right command
            Command commandAlignRight = Command.builder()
                    //.setIconFactory(Format_justify_right.factory())
                    .setAction((CommandActionEvent event) ->
                            setAlignment(textPane, StyleConstants.ALIGN_RIGHT))
                    //.inToggleGroup(justifyToggleGroup)
                    .build();

            // Align fill command
            Command commandAlignFill = Command.builder()
                    //.setIconFactory(Format_justify_fill.factory())
                    .setAction((CommandActionEvent event) ->
                            setAlignment(textPane, StyleConstants.ALIGN_JUSTIFIED))
                    //.inToggleGroup(justifyToggleGroup)
                    .build();

            // Create a button strip to change the text alignment in our text pane.
            CommandStripProjection commandStripProjection = new CommandStripProjection(
                    new CommandGroup(commandAlignLeft, commandAlignCenter,
                            commandAlignRight, commandAlignFill),
                    CommandStripPresentationModel.builder()
                            .setOrientation(CommandStripPresentationModel.StripOrientation.VERTICAL)
                            .setHorizontalGapScaleFactor(0.8)
                            .setVerticalGapScaleFactor(1.4)
                            .build());
            JCommandButtonStrip commandButtonStrip = commandStripProjection.buildComponent();
            styleButtonPanel.add(commandButtonStrip);
        });
    }

    private static void setAlignment(JTextPane textPane, int alignment) {
        MutableAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrSet, alignment);
        textPane.getStyledDocument().setParagraphAttributes(0,
                textPane.getStyledDocument().getLength(), attrSet, false);
    }
}
