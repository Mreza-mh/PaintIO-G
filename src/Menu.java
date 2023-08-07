import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel{
 private int botCount = 6;
 private int speed = 8;
 private boolean isActive = true;
 private String selectedShape ;

    Menu(Main main) {
        setLayout(new GridLayout(5, 1, 10, 10));
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);

        Font labelFont = new Font(null, Font.ITALIC, 30);
        Color labelColor = Color.BLACK;

        JLabel titleLabel = new JLabel("||  Game Menu  ||");
        titleLabel.setForeground(labelColor);
        titleLabel.setFont(labelFont);
        headerPanel.add(titleLabel);
        add(headerPanel);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(Color.white);

        JSpinner botCountSpinner = new JSpinner(new SpinnerNumberModel(botCount, 1, 20, 1));
        JSpinner gameSpeedSpinner = new JSpinner(new SpinnerNumberModel(speed, 1, 15, 1));

        JLabel botCountLabel = new JLabel("    Bot Count:");
        botCountLabel.setForeground(labelColor);
        botCountLabel.setFont(labelFont);
        JLabel gameSpeedLabel = new JLabel("    Game Speed:");
        gameSpeedLabel.setForeground(labelColor);
        gameSpeedLabel.setFont(labelFont);

        inputPanel.add(botCountLabel);
        inputPanel.add(botCountSpinner);
        inputPanel.add(gameSpeedLabel);
        inputPanel.add(gameSpeedSpinner);

        add(inputPanel);


        // Shape Panel
        JPanel shapePanel = new JPanel(new GridLayout(1, 4, 10, 10));
        shapePanel.setBackground(Color.white);

        JLabel shapeLabel = new JLabel("    Shape:");
        shapeLabel.setForeground(labelColor);
        shapeLabel.setFont(labelFont);

        JRadioButton rectangleButton = new JRadioButton("Square");
        JRadioButton circleButton = new JRadioButton("Circle");
        JRadioButton triangleButton = new JRadioButton("Pac-Man");

        rectangleButton.addActionListener(new ShapeSelectionListener());
        circleButton.addActionListener(new ShapeSelectionListener());
        triangleButton.addActionListener(new ShapeSelectionListener());

        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(rectangleButton);
        shapeGroup.add(circleButton);
        shapeGroup.add(triangleButton);

        shapePanel.add(shapeLabel);
        shapePanel.add(rectangleButton);
        shapePanel.add(circleButton);
        shapePanel.add(triangleButton);

        add(shapePanel);


        // Active Button
        JToggleButton activeButton = new JToggleButton("Active Hoosh");
        activeButton.setFont(new Font(null, Font.ITALIC , 30));
        activeButton.setFocusPainted(false);
        activeButton.setBorderPainted(false);
        activeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                isActive = activeButton.isSelected();
                activeButton.setBackground(Color.GREEN);

            }
        });

        JPanel activePanel = new JPanel();
        activePanel.setBackground(Color.WHITE);
        activePanel.add(activeButton);

        add(activePanel);



        // Play Button
        JButton playBtn = createButton("Play");
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(playBtn);

        add(btnPanel);


        // Start Button Action Listener
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (selectedShape != null) {
                    botCount = (int) botCountSpinner.getValue();
                    speed = (int) gameSpeedSpinner.getValue();
                    String username = "You";

                    Game game = new Game();
                    main.add(game);
                    game.setUp(botCount, speed, new MainPlayer(username), isActive, selectedShape);
                    main.setVisible(true);
                } else {
                    System.out.println("choose a shape");
                }
            }
        });
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(null, Font.BOLD , 30));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(500, 80));
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private class ShapeSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton selectedButton = (JRadioButton) e.getSource();
            selectedShape = selectedButton.getText();
        }
    }
}



