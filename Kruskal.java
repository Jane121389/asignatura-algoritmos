/*Programa que dados los elementos de un grafico, y el peso en sus arcos encuentra el arbol minimo*/
import java.io.*;
import java.util.Scanner;
class Kruskal
{
    static int ordenamiento[];    //	Arreglo de los números ordenados
    static int ordenamiento_v1[]; //	Arreglo de los vertices 1 de los números ordenados
    static int ordenamiento_v2[]; //	Arreglo de los vertices 2 de los números ordenados
    static int arbol_minimo[][];  //arreglo de arbol minimo
    static int o;                 //indice del arreglo ordenamiento
    public static void main(String [] args)
    {
        int     vertices       =0;         //Número de vertices del grafico
        int     vertice_inicial=0;         //vertice inicial para verificar si hay algun ciclo
        int     aristas        =0;         //Número de aristas del grafico
        int     numeros[];                 //Pesos de las aristas del grafico
        int     numeros_v1[];              //vertices 1 de las aristas
        int     numeros_v2[];              //vertices 2 de las aristas
        Scanner sc=new Scanner(System.in); //Instancia para leer datos del teclado
        System.out.println("Número de vertices del grafico:");
        vertices=sc.nextInt();  //recible el número  de vértice
        System.out.println("Número de aristas del grafico:");
        aristas        =sc.nextInt();                //recible el número  de aristas
        arbol_minimo   =new int[vertices][vertices]; //define los elementos del arreglo del arbol minimo
        numeros        =new int[aristas];            //define los elementos de los pesos de las aristas
        numeros_v1     =new int[aristas];            //define los elementos de los vertice 1 de las aristas
        numeros_v2     =new int[aristas];            //define los elementos de los vertice 2 de las aristas
        ordenamiento   =new int[aristas];            //define los elementos del arreglo de  los pesos de las aristas ordenados
        ordenamiento_v1=new int[aristas];            //define los elementos del arreglo de los vertice 1 de los pesos de las aristas ordenados
        ordenamiento_v2=new int[aristas];            //define los elementos del arreglo de los vertice 2 de los pesos de las aristas ordenados

        for (int f=0; f < vertices; f++)     //recorre el arreglo en filas
            for (int c=0; c < vertices; c++) //recorre el arreglo en columnas
                arbol_minimo[f][c]=0;        //inicializa el arreglo del árbol mínimo

        for (int f=0; f < aristas; f++)  //recorre el arreglo de pesos de aristas
        {
            System.out.println("Número de vertice 1:");                    //Pide el primer número de vértice que se va a conectar
            numeros_v1[f]=sc.nextInt() - 1;                                //recibe el vertice 1
            System.out.println("Número de vertice 2:");                    //Pide el segundo número de vértice que se va a conectar
            numeros_v2[f]=sc.nextInt() - 1;                                //recibe el vertice 2
            System.out.println("Peso de la arista que conecta vertices:"); //Pide el segundo número de vértice que se va a conectar
            numeros[f]=sc.nextInt();                                       //recibe el peso del arco
        }
        ordena(numeros, numeros_v1, numeros_v2, aristas); //función para ordenar los pesos de las aristas usando quick_sort
        for (int f=0; f < aristas; f++)                   //recorre el arreglo de pesos de aristas

            if (verifica_ciclo(ordenamiento_v1[f], ordenamiento_v2[f], vertices, ordenamiento_v1[f]) == false)//verifica si se forma un ciclo
            {
                arbol_minimo[ordenamiento_v1[f]][ordenamiento_v2[f]]=1;
                arbol_minimo[ordenamiento_v2[f]][ordenamiento_v1[f]]=1;
            }
        System.out.println("Aristas del árbol mínimo:");
        for (int f=0; f < vertices; f++)         //recorre el arreglo en filas
            for (int c=f + 1; c < vertices; c++) //recorre el arreglo en columnas
                if (arbol_minimo[f][c] == 1)
                    System.out.println("Vertice 1:" + (f + 1) + ", vertice 2:" + (c + 1) + " ");
        //imprime los vertices del arbol minimo
    }

    static boolean ordena(int numeros[], int numeros_v1[], int numeros_v2[], int longitud)
    {
        int d             =0; //contador de la lista de numeros de la derecha
        int z             =0; //contador de la lista de numeros de la izquierda
        int i             =1;
        int izquierda[]   =new int[longitud]; //arreglo de números menores
        int derecha[]     =new int[longitud]; //arreglo de números mayores
        int izquierda_v1[]=new int[longitud]; //arreglo del v1 de números menores
        int izquierda_v2[]=new int[longitud]; //arreglo del v2 de números menores
        int derecha_v1[]  =new int[longitud]; //arreglo del v1 de números mayores
        int derecha_v2[]  =new int[longitud]; //arreglo del v2 de números mayores
        for (i=1; i < longitud; i++)          //recorre la lista de números a ordenar
        {
            if (numeros[i] < numeros[0])   //si un número de la lista es menor al primer número de la lista
            {
                izquierda[z]   =numeros[i];    //se coloca en el arreglo izquierda el número más pequeño
                izquierda_v1[z]=numeros_v1[i]; //se coloca en el arreglo izquierda_v1 el vertice 1 del número más pequeño
                izquierda_v2[z]=numeros_v2[i]; //se coloca en el arreglo izquierda_v2 el vertice 2 del número más pequeño
                z++;                           //incrementa el contador del arreglo izquierda
            }
            else if (numeros[i] >= numeros[0]) //si un número de la lista es mayor al primer número de la lista
            {
                derecha[d]   =numeros[i];    //se coloca en el arreglo derecha el número más grande
                derecha_v1[d]=numeros_v1[i]; //se coloca en el arreglo derecha_v1 del vertice 1 del número más grande
                derecha_v2[d]=numeros_v2[i]; //se coloca en el arreglo derecha_v2 del vertice 2 del número más grande
                d++;                         //incrementa el contador del arreglo derecha
            }
        }
        izquierda[z]   =numeros[0];//pone el elemento pivote
        izquierda_v1[z]=numeros_v1[0];
        izquierda_v2[z]=numeros_v2[0];
        z++;
        if (z == 1 || z == 1 && d == 0)    //si no hay más elementos a la izquierda o a la derecha
        {
            ordenamiento[o]   =numeros[0];    // coloca en el arreglo ordenamiento un número
            ordenamiento_v1[o]=numeros_v1[0]; // coloca en el arreglo ordenamiento un número
            ordenamiento_v2[o]=numeros_v2[0]; // coloca en el arreglo ordenamiento un número
            o++;                              //incrementa la variable de los elementos del arreglo ordenamiento
        }
        if (z > 1)
            ordena(izquierda, izquierda_v1, izquierda_v2, z);   //ordena arreglo de la izquierda
        if (d > 0)
            ordena(derecha, derecha_v1, derecha_v2, d);     //ordena arreglo de la derecha
        return false;
    }

    static boolean verifica_ciclo(int vertice1, int vertice2, int vertices, int vertice_inicial)
    {
        for (int f=0; f < vertices; f++)
        {
            if (arbol_minimo[f][vertice2] == 1 && (f == vertice_inicial) && (f != vertice1))
                return true;
            else if (arbol_minimo[f][vertice2] == 1 && (f != vertice1))
                if (verifica_ciclo(vertice2, f, vertices, vertice_inicial) == true)
                    return true;
        }
        return false;
    }
}
