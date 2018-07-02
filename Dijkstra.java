/*Programa que dados los elementos de un grafico, y el peso en sus arcos encuentra el camino más corto dado un vertice de origen*/
import java.io.*;
import java.util.Scanner;
class Dijkstra
{
    public static void main(String [] args)
    {
        int     grafico[][];                            //arreglo con los vertices que se conectan y el peso del arco que los conecta
        int     visto[][];                              //arreglo que muestra si los arcos de los vertices ya fueron vistos
        int     distancia[][];                          //arreglo que contiene la suma del camino recorrido hacia el arco
        int     padre[];                                //	arreglo que contiene el camino más corto
        int     r              =0;                      //índice que recorre el arreglo ruta
        int     fila           =0;                      //índice para recorrer el arreglo en filas
        int     columna        =0;                      //índice para recorrer el arreglo en columnas
        int     vertices       =0;                      //número de vértices
        int     aristas        =0;                      //número de aristas
        int     peso           =0;                      //peso del arco del vértice
        int     distancia_menor=1000;                   //toma la distancia menor
        int     fila_menor     =0;                      //indica donde se encuentra el arco menor
        Scanner sc             =new Scanner(System.in); //Instancia para leer datos del teclado
        System.out.println("Número de vertices del grafico:");
        vertices=sc.nextInt();  //recible el número  de vértice
        System.out.println("Número de aristas del grafico:");
        aristas  =sc.nextInt();                //recible el número  de aristas
        grafico  =new int[vertices][vertices]; //define los elementos del arreglo graficos
        visto    =new int[vertices][vertices]; //define los elementos del arreglo visto
        distancia=new int[vertices][vertices]; //define los elementos del arreglo distancia
        padre    =new int[vertices];           //define los elementos en el arreglo ruta
        for (fila=0; fila < vertices; fila++)  //recorre el arreglo en filas

            for (columna=0; columna < vertices; columna++)   //recorre el arreglo en columnas
            {
                grafico[fila][columna]  =0; //inicializa el arreglo de grafico
                visto[fila][columna]    =0; //inicializa el arreglo de vertices vistos
                distancia[fila][columna]=0; //inicializa el arreglo de distancia
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
        //Inicia la busqueda de la ruta más corta
        for (fila=0; fila < vertices; fila++)    //recorre el arreglo en filas
        {
            for (columna=0; columna < vertices; columna++)   //recorre el arreglo en columnas

                if (grafico[fila][columna] != 0 && visto[fila][columna] == 0)//verifica si existe un arco y si no ha sido visto
                {
                    visto[fila][columna]=1;        //coloca 1 para asegurar que ya esta visto ese arco
                    visto[columna][fila]=1;        //coloca 1 para asegurar que ya esta visto ese arco
                    if (fila == 0 || columna == 0) //si aun no hay camino
                    {
                        distancia[fila][columna]=grafico[fila][columna]; //asigna el peso del primer arco del camino
                        distancia[columna][fila]=grafico[columna][fila]; //asigna el peso del primer arco del camino
                        padre[columna]          =fila;                   //asigna el vertice anterior de la arista
                    }
                    else
                    {
                        distancia[fila][columna]=distancia[fila][padre[fila]] + grafico[fila][columna];   //asigna el peso del arco mas el peso del camino para llegar a ese arco
                        distancia[columna][fila]=distancia[fila][padre[fila]] + grafico[columna][fila];   //asigna el peso del arco mas el peso del camino para llegar a ese arco
                        distancia_menor         =1000;
                        for (int f=0; f < vertices; f++) //recorre las distancias para tomar la de menor peso

                            if (distancia[f][columna] < distancia_menor && (distancia[f][columna] > 0))
                            {
                                distancia_menor=distancia[f][columna]; //se le asigna la distancia menor del arreglo
                                fila_menor     =f;                     //donde se encuentra el arco menor
                            }
                        padre[columna]=fila_menor;//se le asigna el arco de menor peso
                    }
                }
        }
        System.out.print("La ruta más corta es la siguiente:"); //imprime en pantalla
        ruta(vertices - 1, padre);                              //imprime el vertice anterior de un arco
        System.out.print(vertices + "\n");                      //imprime el último vertice
    }

    static boolean ruta(int indice, int [] padre)
    {
        if (padre[indice] == 0)
            System.out.print((padre[indice] + 1) + " "); //imprime el primer vertice
        else
        {
            ruta(padre[indice], padre);                  //imprime el vertice anterior de un arco
            System.out.print((padre[indice] + 1) + " "); //imprimer en pantalla el vertice anterior de un arco
        }
        return false;
    }
}
