import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.Model;
import view.View;

public class App {
    public static void main(String[] args) throws Exception {

        View view = new View("MVC Starter");
        Model model = new Model();
        Controller controller = new Controller(model, view);

        SwingUtilities.invokeLater( new Runnable() {

            @Override
            public void run() {
                view.setVisible(true);
                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                view.setSize(600,600);
            }            
        });
    }
}
