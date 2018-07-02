/*Programa que ordena una lista de números con Quick sort*/
import java.io.*;
import java.util.Scanner;
class Quick_sort
{
    static int ordenamiento[]; //	Arreglo de los números ordenados
    static int o;              //indice del arreglo ordenamiento
    public static void main(String [] args)
    {
        int     numeros[];                                           //números a ordenar
        int     total_num=0;                                         //total de números a ordenar
        Scanner sc       =new Scanner(System.in);                    //Lee los datos del teclado
        System.out.println("Escriba cuantos números desea ordenar"); //imprime en pantalla
        total_num   =sc.nextInt();                                   //lee la cantidad de números a ordenar
        numeros     =new int[total_num];                             //define el arreglo números con total_num de elementos
        ordenamiento=new int[total_num];                             //define el arreglo ordenamiento con total_num de elementos
        System.out.println("Escriba la lista de números a ordenar"); //imprime en pantalla
        o=0;
        for (int i=0; i < total_num; i++) //lee la entrada de los números
            numeros[i]=sc.nextInt();      //recibe número del teclado
        ordena(numeros, total_num);
        System.out.println("Los números ordenados son:"); //imprime en pantalla los números ordenados
        for (int i=0; i < total_num; i++)                 // imprimer el arreglo de números ordenados
            System.out.print(ordenamiento[i] + " ");
    }

    static boolean ordena(int numeros[], int longitud)
    {
        int d          =0; //contador de la lista de numeros de la derecha
        int z          =0; //contador de la lista de numeros de la izquierda
        int i          =1;
        int izquierda[]=new int[longitud]; //arreglo de números menores
        int derecha[]  =new int[longitud]; //arreglo de números mayores
        for (i=1; i < longitud; i++)       //recorre la lista de números a ordenar
        {
            if (numeros[i] < numeros[0])   //si un número de la lista es menor al primer número de la lista
            {
                izquierda[z]=numeros[i]; //se coloca en el arreglo izquierda el número más pequeño
                z++;                     //incrementa el contador del arreglo izquierda
            }
            else if (numeros[i] >= numeros[0]) //si un número de la lista es mayor al primer número de la lista
            {
                derecha[d]=numeros[i]; //se coloca en el arreglo derecha el número más grande
                d++;                   //incrementa el contador del arreglo derecha
            }
        }
        izquierda[z]=numeros[0];//pone el elemento pivota
        z++;
        if (z == 1 || z == 1 && d == 0)    //si no hay más elementos a la izquierda o a la derecha
        {
            ordenamiento[o]=numeros[0]; // coloca en el arreglo ordenamiento un número
            System.out.println(" " + ordenamiento[o]);
            o++;    //incrementa la variable de los elementos del arreglo ordenamiento
        }
        if (z > 1)
            ordena(izquierda, z);       //ordena arreglo de la izquierda
        if (d > 0)
            ordena(derecha, d);     //ordena arreglo de la derecha
        return false;
    }
}
