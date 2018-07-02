/*Programa que dados los elementos de un grafico, y el peso en sus arcos encuentra el camino más corto dado un vertice de origen*/
import java.io.*;
import java.util.Scanner;
class Dijkstra2
{
    static int distancia_m[];
    static int grafico[][];          //arreglo con los vertices que se conectan y el peso del arco que los conecta
    static int padres[][];           //	arreglo que contiene el camino más corto
    static int vertices       =0;    //número de vértices
    static int distancia_menor=1000; //toma la distancia menor
    static int fila_menor     =0;    //indica donde se encuentra la ruta menor
    static int columnas_menor =0;
    static int padre_menor[];
    public static void main(String [] args)
    {
        int     r      =0; //índice que recorre el arreglo ruta
        int     s      =0;
        int     fila   =0;                 //índice para recorrer el arreglo en filas
        int     columna=0;                 //índice para recorrer el arreglo en columnas
        int     aristas=0;                 //número de aristas
        int     peso   =0;                 //peso del arco del vértice
        int     visto[][];                 //arreglo que muestra si los arcos de los vertices ya fueron vistos
        Scanner sc=new Scanner(System.in); //Instancia para leer datos del teclado
        System.out.println("Número de vertices del grafico:");
        vertices=sc.nextInt();  //recible el número  de vértice
        System.out.println("Número de aristas del grafico:");
        aristas    =sc.nextInt();                //recible el número  de aristas
        grafico    =new int[vertices][vertices]; //define los elementos del arreglo graficos
        visto      =new int[vertices][vertices]; //define los elementos del arreglo visto
        distancia_m=new int[1000];
        padres     =new int[1000][vertices]; //define los elementos en el arreglo ruta
        padre_menor=new int[vertices];
        for (fila=0; fila < vertices; fila++)    //recorre el arreglo en filas

            for (columna=0; columna < vertices; columna++)   //recorre el arreglo en columnas
            {
                grafico[fila][columna]=0; //inicializa el arreglo de grafico
                visto[fila][columna]  =0; //inicializa el arreglo de vertices vistos
            }
        for (int f=0; f < aristas; f++)
        {
            System.out.println("Número de vertice 1:");                //Pide el primer número de vértice que se va a conectar
            fila=sc.nextInt();                                         //recibe el vertice 1
            System.out.println("Número de vertice 2:");                //Pide el segundo número de vértice que se va a conectar
            columna=sc.nextInt();                                      //recibe el vertice 2
            System.out.println("Peso del arco que conecta vertices:"); //Pide el segundo número de vértice que se va a conectar
            peso                          =sc.nextInt();               //recibe el peso del arco
            grafico[fila - 1][columna - 1]=peso;                       //pone el peso del arco v1-v2 en el arreglo grafico
            grafico[columna - 1][fila - 1]=peso;                       //pone el peso del arco v2-v1 en el arreglo grafico
        }
        for (int i=0; i < vertices; i++)
            padres[i][0]=0;      //inicializamos el arreglo de padres de la ruta
        s=arma_ruta(0, 0, 0, 1); //llamamos al método arma_ruta
        System.out.println("la ruta menor con peso total de " + distancia_menor + " es:");
        for (int i=0; i < columnas_menor; i++)   //imprimimos la ruta en pantalla
        {
            System.out.print((padre_menor[i] + 1));
            if (i < columnas_menor - 1)
                System.out.print(",");
        }
        System.out.println();
    }

    static int arma_ruta(int fila, int distancia, int s, int l)//función recursiva que arma todaslas posibles rutas
    {
        if (l > vertices)  //si hay una posible ruta con más de los vértices totales se eliimina
        {
            l        =1; //inicializamos l
            distancia=0; //inicializamos la distancia
            return s;
        }
        else if (fila == vertices - 1) //si la fila es el vértice final
        {
            distancia_m[s]=distancia;              //colocamos la distancia de la ruta
            if (distancia_m[s] <= distancia_menor) //si la distancia es la menor hasta el momento
            {
                distancia_menor=distancia_m[s]; //asignamos a la variable distancia_menor
                columnas_menor =l;              //asignamos el numero de vértices de la ruta
                for (int i=0; i < l; i++)       //asignamos la ruta al arreglo padre_menor
                    padre_menor[i]=padres[s][i];
            }
            distancia=0; //inicializamos la distancia
            s++;         //aumentamos el contador
            l=1;         //inicializamos l
            return s;
        }
        else
        {
            for (int columna=0; columna < vertices; columna++)   //recorre el arreglo en columnas

                if (grafico[fila][columna] != 0)//verifica si existe una arista
                {
                    if (l < vertices)
                        padres[s][l]=columna;                                           //asignamos a la ruta el vertice de la columna
                    arma_ruta(columna, (distancia + grafico[fila][columna]), s, l + 1); //recursividad de la función
                }
        }
        return s;
    }
}
