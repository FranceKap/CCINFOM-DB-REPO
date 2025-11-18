import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DbConnection db = new DbConnection();

        System.out.println("amogus");
        System.out.println("Testing Connection...");
        db.getConnection();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new App().start();
            }
        });
    }
}