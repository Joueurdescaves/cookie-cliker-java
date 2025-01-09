import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JFrame {
    private static int compteur = 0;
    private JLabel compteurLabel;
    private JLabel prix;
    private JLabel niveau;



    public Main() {
        // Charger la valeur du compteur au démarrage
        loadCompteur();

        // Configuration de la fenêtre
        setTitle("Recréation du Layout avec Image");
        ImageIcon icon = new ImageIcon("C:\\Users\\joueurdescave\\Jstudio\\app\\clicker\\icon\\icon.png");
        setIconImage(icon.getImage()); // Ajouter l'icône de la fenêtre
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Désactiver le layout manager

        // Redimensionner l'image et l'afficher
        Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Redimensionner l'image
        icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(-20, 200, 300, 300); // Position et taille
        add(imageLabel);

        // Ajouter un écouteur d'événement pour l'image
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                compteur++; // Incrémenter le compteur
                compteurLabel.setText("$" + compteur); // Mettre à jour l'affichage
                saveCompteur(); // Sauvegarder le compteur
            }
        });

        // Remplacer le cercle par l'image cliquable
        ImageIcon profileIcon = new ImageIcon("C:\\Users\\joueurdescave\\Jstudio\\app\\clicker\\icon\\profil.png");
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        profileIcon = new ImageIcon(profileImage);

        JLabel profileLabel = new JLabel(profileIcon);
        profileLabel.setBounds(1100, 20, 50, 50); // Position (x, y) et taille (largeur, hauteur)
        add(profileLabel);

        // Ajouter un écouteur d'événement pour l'image
        profileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProfileWindow();
            }
        });

        // Barre de séparation verticale
        JPanel barreSeparation = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        barreSeparation.setBounds(250, 0, 5, 800); // Position (x, y) et taille (largeur, hauteur)
        add(barreSeparation);

        // Ajouter le texte "CLIKER BY JSTUDIO"
        JLabel clickerLabel = new JLabel("CLIKER BY JSTUDIO");
        clickerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        clickerLabel.setBounds(70, 10, 200, 50);
        add(clickerLabel);

        // Label pour afficher le compteur
        compteurLabel = new JLabel("$" + compteur);
        compteurLabel.setFont(new Font("Arial", Font.BOLD, 30));
        compteurLabel.setBounds(110, 40, 110, 30);
        add(compteurLabel);

         JButton upgradebutton = new JButton("upgrade");
         upgradebutton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if (compteur >= 50) {
                     startTimer();
                     compteur -= 50;
                     saveCompteur();
                     prix&niveau
                 }
            }
        });
         JLabel upg = new JLabel("upgrade");
         upg.setFont(new Font("Arial", Font.BOLD, 22));
         upg.setBounds(310,60,200,30);
         add(upg);
         upgradebutton.setBounds(300,100,100,30);
         add(upgradebutton);
    }


    // Méthode pour sauvegarder le compteur
    private static void saveCompteur() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.putInt("compteur", compteur);
    }

    private void startTimer() {
        Timer timer = new Timer();
        TimerTask gagnerDollars = new TimerTask() {
            @Override
            public void run() {
                compteur += 10; // Ajouter 10 dollars toutes les 10 secondes
                compteurLabel.setText("$" + compteur); // Mettre à jour l'affichage
                saveCompteur(); // Sauvegarder le compteur
            }
        };
        timer.scheduleAtFixedRate(gagnerDollars, 0, 10000); // Exécuter toutes les 10 secondes
    }

    // Méthode pour charger le compteur
    private static void loadCompteur() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        compteur = prefs.getInt("compteur", 0); // Valeur par défaut 0 si non trouvé
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
    private void showProfileWindow() {
        // Créer une nouvelle fenêtre (JFrame)
        JFrame profileWindow = new JFrame("Profil");
        ImageIcon profileIcon2 = new ImageIcon("C:\\Users\\joueurdescave\\Jstudio\\app\\clicker\\icon\\profil.png");
        Image profileImage = profileIcon2.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Redimensionner l'image
        profileWindow.setIconImage(profileImage);
        profileWindow.setSize(300, 400); // Taille de la fenêtre
        profileWindow.setLayout(null); // Layout pour les composants
        profileWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer seulement cette fenêtre
        Point Main = this.getLocation();
        int x = (int) Main.getX() + 850;
        int y = (int) Main.getY() + 60;
        profileWindow.setLocation(x,y);

        // Ajouter un label dans la fenêtre
        JLabel label = new JLabel(" profil !");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBounds(120, 10, 150, 30);
        profileWindow.add(label);

        // Ajouter des boutons
        JButton button1 = new JButton("Action 1");
        button1.addActionListener(e -> JOptionPane.showMessageDialog(profileWindow, "Action 1 exécutée !"));
        button1.setBounds(90, 60, 110, 30);
        profileWindow.add(button1);

        JButton button2 = new JButton("Action 2");
        button2.addActionListener(e -> JOptionPane.showMessageDialog(profileWindow, "Action 2 exécutée !"));
        button2.setBounds(90, 110, 110, 30);
        profileWindow.add(button2);

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> profileWindow.dispose()); // Fermer la fenêtre
        closeButton.setBounds(90, 160, 110, 30);
        profileWindow.add(closeButton);

        // Afficher la fenêtre
        profileWindow.setVisible(true);
    }
}