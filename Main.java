import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        System.out.println("amogus");

        DbConnection db = new DbConnection();

        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new App().start();
            }
        });
    }
}