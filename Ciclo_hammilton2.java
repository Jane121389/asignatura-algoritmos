import java.io.*;
import java.util.Scanner;
class Ciclo_hammilton2
{
    static int aristas      =0;
    static int vertices     =0;
    static int k            =20;
    static int matriz[][]   =new int[k][k]; //Matriz
    static int numero_random=0;
    static int i            =0;
    static int p[];
    static boolean visitados[]=new boolean[vertices];   //vertices visitados
    public static void main(String args[])
    {
        int     j              =0;
        int     pp             =0;
        int     vertice_inicial=0;
        boolean hamilton       =false;
        matriz=convierte_matriz("Matriz2.txt");
        p     =new int[vertices]; //camino
        for (i=0; i < vertices; i++)
        {
            p[i]        =0;
            visitados[i]=false;
        }
        i              =0;
        numero_random  =(int)(Math.random() * (vertices));
        numero_random  =0;
        vertice_inicial=numero_random;
        p[i]           =numero_random;
        //System.out.print(" vertice :"+p[i]);
        visitados[numero_random]=true;
    }

    static int [][] convierte_matriz(String archivo)
    {
        int        k=20, i, j;
        int        matriz_gra[][]=new int[k][k]; //Matriz
        String     numero_s="";                  //número tipo String
        int        numero_d=0;                   //número tipo double
        int        fila=0, columna=0;
        FileReader fr = null;   //fichero
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
                    matriz_gra[fila][columna]=numero_d;
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
                System.out.print(matriz_gra[i][j] + " ");
            System.out.println("");
        }
        System.out.println("\nAristas:" + aristas + " vértices:" + vertices);
        return matriz_gra;
    }

    static boolean IsHammilton(int i)
    {
        if (i == (vertices - 2))
            return true;
        else
            return false;
    }

    static int [] rotate(int [] p, int v, int totales)
    {
        int i=0, vert=0;
        while (p[i] != v) {
            i++;
        }
        vert=i;
        while (i < totales) {
            p[i++]=p[totales--];
        }
        matriz[vert][totales]=0;
        matriz[totales][vert]=0;
        return p;
        //borrar  de la matriz
    }

    static int[] extend(int [] p, int v, int i)
    {
        p[i]=v;
        return p;
    }

    static void imprime_camino(int [] p)
    {
        for (int i=0; i < vertices; i++)
            System.out.print(p[i] + " ");
    }

    static boolean recursiva()
    {
        int numero_random2=0;
        int j             =0;
        while (matriz[numero_random][numero_random2] != 1 || visitados[numero_random2] == true)
        {
            p[++i]=numero_random2;
            System.out.println("Ciclo hammiltoniano");
            numero_random2=(int)(Math.random() * (vertices));
            j++;
            if (j > 1000)
                return false;
        }
        //System.out.print(" vertice:"+j+ "i:"+i);
        i++;
        if (IsHammilton(i) == true)
        {
            System.out.println("Ciclo hammiltoniano");
            //System.out.print(vertice_inicial+"\n");
            return true;
        }
        else
        {
            if (visitados[numero_random2] == false)
            {
                i++;
                p                        =extend(p, numero_random2, i);
                visitados[numero_random2]=true;
                numero_random            =numero_random2;
            }
            else
            {
                p            =rotate(p, numero_random2, i++);
                numero_random=p[i - 1];
            }
            recursiva();
            System.out.print(numero_random);
        }
        return false;
    }
}
