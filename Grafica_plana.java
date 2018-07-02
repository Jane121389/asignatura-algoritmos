import java.io.*;
import java.util.Scanner;
class Grafica_plana
{
    public static void main(String args[])
    {
        convierte_matriz("Matriz.txt");
    }

    static int [][] convierte_matriz(String archivo)
    {
        int        k=20, i, j;
        int        matriz[][]=new int[k][k]; //Matriz
        String     numero_s="";              //número tipo String
        int        numero_d=0;               //número tipo double
        int        fila=0, columna=0;
        int        aristas =0;
        int        vertices=0;
        FileReader fr      = null; //fichero
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(archivo);
            //Leer el primer carácter
            int caract = fr.read(); //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caract != -1) {
                if (caract != ' ' && caract != 10)
                    numero_s=numero_s + (char)caract;
                else
                {
                    numero_d=Integer.parseInt(numero_s);
                    if (numero_d == 1)
                        aristas++;
                    matriz[fila][columna]=numero_d;
                    columna++;
                    if (caract == '\n')
                    {
                        columna=0;
                        fila++;
                    }
                    numero_s="";
                }
                caract = fr.read();//leer el caracter
            }
        }
        catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null)
                    fr.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
        aristas =aristas / 2;
        vertices=(fila);
        System.out.println("\nMatriz");
        for (i=0; i < fila; i++) //Imprime la matriz
        {
            for (j=0; j < fila; j++)
                System.out.print(matriz[i][j] + " ");
            System.out.println("");
        }
        System.out.println("\nAristas:" + aristas + " vértices:" + vertices);
        if (aristas > (3 * vertices) - 6)
            System.out.println("\nNo es una gráfica plana\n");
        else
            System.out.println("\nEs una gráfica plana\n");
        return matriz;
    }
}
