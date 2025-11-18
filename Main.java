import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DbConnection db = new DbConnection();

        System.out.println("Testing Connection...");
        db.getConnection();

        // start GUI on the EDT and let App manage the UI/features
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { 
                new App().start(); }
        });
    }
}