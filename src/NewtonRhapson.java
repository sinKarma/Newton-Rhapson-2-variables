import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NewtonRhapson {
    double vectorInicial[], vectorNuevo[];
    String[][] jacobi;
    String[] funciones;

    public NewtonRhapson(String path, double valorInicialX, double valorInicialY) {
        vectorInicial= new double[2];
        vectorNuevo = new double[2];
        vectorInicial[0] = valorInicialX;
        vectorInicial[1] = valorInicialY;
        jacobi = new String[2][2];
        funciones = new String[2];

        String file = loadFileAsString(path);
        String[] tokens = file.split(",");
        funciones[0] = tokens[0].trim();
        funciones[1] = tokens[1].trim();
        jacobi[0][0] = tokens[2].trim();
        jacobi[0][1] = tokens[3].trim();
        jacobi[1][0] = tokens[4].trim();
        jacobi[1][1] = tokens[5].trim();





    }


    public static double interpretarFuncion(String funcion, double x, double y) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine interprete = manager.getEngineByName("js");
        try {
            interprete.put("x", x);
            interprete.put("y", y);
            return Double.parseDouble(interprete.eval(funcion).toString());
        } catch (ScriptException se) {
            se.printStackTrace();
            return 0;
        }
    }

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while (((line = br.readLine()) != null)) {
                builder.append(line + "\n");

            }
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error cargando el archivo");

        }
        return builder.toString();
    }

    public double determinante(double matriz[][]){
        return (matriz[0][0] * matriz[1][1])-(matriz[1][0]*matriz[0][1]);
    }
    public double[][] matrizTranspuesta(double matriz[][]){
        double a = matriz[0][0], b = matriz[0][1], c = matriz[1][0], d=matriz[1][1];
        double matrizTranspuesta[][] = {
                {d, -b},
                {-c, a}
        };
        return matrizTranspuesta;
    }
    public double[] resolver(int iteraciones){
        double f1, f2;
        for(int k=0;k<iteraciones;k++){
            double[][] matrizJacobi = matrizJacobiValores(vectorInicial[0], vectorInicial[1]);
            double determinanteJacobi = determinante(matrizJacobi);
            matrizJacobi = matrizTranspuesta(matrizJacobi);
            //Multiplicamos el determinante por la matriz transpuesta
            for(int i=0;i<2;i++)for(int j=0;j<2;j++)matrizJacobi[i][j]*= (1/determinanteJacobi);
            //f1(x0, y0), f2(x0,y0)
            f1 = interpretarFuncion(funciones[0], vectorInicial[0], vectorInicial[1]);
            f2 = interpretarFuncion(funciones[1], vectorInicial[0], vectorInicial[1]);

            vectorInicial[0] = vectorInicial[0] - ((matrizJacobi[0][0] * f1) + (matrizJacobi[0][1]*f2));
            vectorInicial[1] = vectorInicial[1] - ((matrizJacobi[1][0] * f1) + (matrizJacobi[1][1]*f2));
/*            System.out.println("Iteracion[+k+]:");
            System.out.println(vectorInicial[0]);
            System.out.println(vectorInicial[1]);*/


        }
        double[] resultados = {vectorInicial[0], vectorInicial[1]};
        return resultados;


    }
    public double[][] matrizJacobiValores(double x, double y){
        double matriz[][] = new double[2][2];
        for(int i=0;i<2;i++)for(int j=0;j<2;j++)matriz[i][j] = interpretarFuncion(jacobi[i][j], x, y);
        return matriz;


    }
}
