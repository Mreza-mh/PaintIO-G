import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Menu menu;
    private JPanel cards;

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::new);
    }

    private Main() {
        UI();
    }


    private void UI() {
        setSize(1490, 1035);
        setResizable(true);
        setVisible(true);
        setTitle("PAINT IO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        menu = new Menu(this);
        cards = new JPanel(new CardLayout());
        cards.add(menu, "menu");
        add(cards);
    }
}