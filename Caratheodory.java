import java.io.*;
import java.util.Scanner;
class Caratheodory
{
    static double ordenamiento[];  //	Arreglo de los números ordenados
    static double ordenamientox[]; //	Arreglo de los números ordenados
    static double ordenamientoy[]; //	Arreglo de los números ordenados
    static int o;                  //indice del arreglo ordenamiento
    static int indice_central=0;
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in); //instancia para entrada de datos por el teclado
        double  x[];                       //coordenada x de los vertices
        double  y[];                       //coordenada y de los vertices
        int     num_vertices=0;            //número de vertices
        int     i           =0;
        o=0;
        boolean orientacion=false;
        double  angulo[];//valores de los angulos
        double  A1x, A1y, A2x, A2y, A3x, A3y;
        System.out.println("Escriba el número de vertices del grafico:");
        num_vertices=sc.nextInt();
        x           =new double[num_vertices];
        y           =new double[num_vertices];
        angulo      =new double[num_vertices];
        int aumenta=0;
        ordenamiento =new double[num_vertices]; //define el arreglo ordenamiento con total_num de elementos
        ordenamientox=new double[num_vertices]; //define el arreglo ordenamiento con total_num de elementos
        ordenamientoy=new double[num_vertices]; //define el arreglo ordenamiento con total_num de elementos
        System.out.println("Escriba las coordenadas del punto que desea encontrar una triangulación que lo cubra:");
        System.out.println("X:");
        x[i]=sc.nextDouble();
        System.out.println("Y:");
        y[i]     =sc.nextDouble();
        angulo[i]=0;
        i++;
        for (; i < num_vertices; i++)
        {
            System.out.println("Escriba las coordenadas del vertice " + i);
            System.out.println("X:");
            x[i]=sc.nextDouble();
            System.out.println("Y:");
            y[i]     =sc.nextDouble();
            angulo[i]=Math.atan((y[i] - y[0]) / (x[i] - x[0]));
        }
        ordena(angulo, num_vertices, x, y);
        System.out.println("Los números ordenados son:"); //imprime en pantalla los números ordenados
        for (i=0; i < num_vertices; i++)                  // imprimer el arreglo de números ordenados
        {
            System.out.print(ordenamiento[i] + " " + "(" + ordenamientox[i] + "," + ordenamientoy[i] + ")");
            if (ordenamientox[i] == x[0])
                indice_central=i;
        }
        System.out.println("indice central:" + indice_central);
        // A1A2P, A2A3P, A3A1P
        A1x=ordenamientox[indice_central - 1];
        A1y=ordenamientoy[indice_central - 1];
        A2x=ordenamientox[indice_central + 1];
        A2y=ordenamientoy[indice_central + 1];
        A3x=ordenamientox[indice_central + 2];
        A3y=ordenamientoy[indice_central + 2];
        while (aumenta < num_vertices - 2)
        {
            orientacion=verifica_triangulo(A1x, A1y, A2x, A2y, A3x, A3y);
            if (verifica_triangulo(A1x, A1y, A2x, A2y, ordenamientox[indice_central], ordenamientoy[indice_central]) == orientacion)
                if (verifica_triangulo(A2x, A2y, A3x, A3y, ordenamientox[indice_central], ordenamientoy[indice_central]) == orientacion)
                    if (verifica_triangulo(A3x, A3y, A1x, A1y, ordenamientox[indice_central], ordenamientoy[indice_central]) == orientacion)
                    {
                        System.out.println("existe una triangulación entre los vertices:(" + A1x + "," + A1y + "),(" + A2x + "," + A2y + "),(" + A3x + "," + A3y + ")");
                        aumenta=num_vertices;
                    }
            aumenta++;
            if (indice_central - 1 - aumenta >= 0)
            {
                A1x=A3x;
                A1y=A3y;
                A3x=ordenamientox[indice_central - 1 - aumenta];
                A3y=ordenamientoy[indice_central - 1 - aumenta];
                System.out.println("nuevos vertices:(" + A1x + "," + A1y + "),(" + A2x + "," + A2y + "),(" + A3x + "," + A3y + ")");
            }
            else if (indice_central + 2 + aumenta < num_vertices)
            {
                A1x=A3x;
                A1y=A3y;
                A3x=ordenamientox[indice_central + 2 + aumenta];
                A3y=ordenamientoy[indice_central + 2 + aumenta];
                System.out.println("nuevos vertices:(" + A1x + "," + A1y + "),(" + A2x + "," + A2y + "),(" + A3x + "," + A3y + ")");
            }
        }
    }

    static boolean ordena(double numeros[], int longitud, double x[], double y[])
    {
        int    d           =0; //contador de la lista de numeros de la derecha
        int    z           =0; //contador de la lista de numeros de la izquierda
        int    i           =1;
        double izquierda[] =new double[longitud]; //arreglo de números menores
        double derecha[]   =new double[longitud]; //arreglo de números mayores
        double izquierdax[]=new double[longitud]; //arreglo de números menores
        double derechax[]  =new double[longitud]; //arreglo de números mayores
        double izquierday[]=new double[longitud]; //arreglo de números menores
        double derechay[]  =new double[longitud]; //arreglo de números mayores
        for (i=1; i < longitud; i++)              //recorre la lista de números a ordenar
        {
            if (numeros[i] < numeros[0])   //si un número de la lista es menor al primer número de la lista
            {
                izquierda[z] =numeros[i];   //se coloca en el arreglo izquierda el número más pequeño
                izquierdax[z]=x[i];
                izquierday[z]=y[i];
                z++;                //incrementa el contador del arreglo izquierda
            }
            else if (numeros[i] >= numeros[0]) //si un número de la lista es mayor al primer número de la lista
            {
                derecha[d] =numeros[i]; //se coloca en el arreglo derecha el número más grande
                derechax[d]=x[i];
                derechay[d]=y[i];
                d++;            //incrementa el contador del arreglo derecha
            }
        }
        izquierda[z] =numeros[0];//pone el elemento pivota
        izquierdax[z]=x[0];
        izquierday[z]=y[0];
        z++;
        if (z == 1 || z == 1 && d == 0)    //si no hay más elementos a la izquierda o a la derecha
        {
            ordenamiento[o] =numeros[0]; // coloca en el arreglo ordenamiento un número
            ordenamientox[o]=x[0];       // coloca en el arreglo ordenamiento un número
            ordenamientoy[o]=y[0];       // coloca en el arreglo ordenamiento un número

            o++;    //incrementa la variable de los elementos del arreglo ordenamiento
        }
        if (z > 1)
            ordena(izquierda, z, izquierdax, izquierday);   //ordena arreglo de la izquierda
        if (d > 0)
            ordena(derecha, d, derechax, derechay);     //ordena arreglo de la derecha
        return false;
    }

    static boolean verifica_triangulo(double A1x, double A1y, double A2x, double A2y, double A3x, double A3y)
    {
        double resultado=0;
        resultado=((A1x - A3x) * (A2y - A3y)) - ((A1y - A3y) * (A2x - A3x));
        //System.out.println("r:"+resultado);
        if (resultado >= 0)
            return true;
        else
            return false;
    }
}
