import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        double[] resultados;
        double valorInicialX, valorInicialY;
     JFileChooser abrir = new JFileChooser("res/funciones");
     abrir.setDialogTitle("Abir archivo...");
     abrir.showOpenDialog(null);
     Path path = Paths.get(abrir.getSelectedFile().getAbsolutePath());
     valorInicialX = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el valor inicial de x"));
     valorInicialY = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el valor inicial de y"));
     NewtonRhapson resolverSistema = new NewtonRhapson(path.toString(), valorInicialX, valorInicialY);
     resultados = resolverSistema.resolver(Integer.parseInt(JOptionPane.showInputDialog("Ingrese las iteraciones")));
     JOptionPane.showMessageDialog(null, "El valor de x="+resultados[0]);
     JOptionPane.showMessageDialog(null, "El valor de y="+resultados[1]);



    }
}
