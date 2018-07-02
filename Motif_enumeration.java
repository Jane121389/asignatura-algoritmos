/*programa que localiza una subcadena k-mer con los desajustes d en el texto, esta o estas subcadenas deben de ser las más frecuentes.*/
import java.io.*;
import java.util.Scanner;
class Motif_enumeration
{
    static int i_posibles;
    static String vecinos_totales[]; //coloca los vecinos de la primera cadena
    static int repite_vecinos[];     //muestra las repeticiones de los vecinos
    static int i_vecinos;            //indice para los vecinos totales
    static int i_mayores;            //Contiene el indice de las cadenas k-mer mas comunes
    static int repeticion_mayor=0;   //la mayor repetición de una cadena
    static int numero_cadenas  =0;   //Número de cadenas de entrada
    public static void main(String [] args)
    {
        String cadena;  //cadena ADN
        i_vecinos =0;
        i_posibles=0;
        String  construye_cadena[]; //Construccion de las cadenas kmer
        String  cadenas_adn[];      //Arreglo que contiene las cadenas de entrada del ADN
        int     indice[];           //indíce del numero de letras de las cadenas k-mer
        int     iletra   =0;        //indíce de las letras
        int     longitud =0;        //longitud de la cadena
        int     kmer     =0;        //k-mer
        int     d_hamming=0;        //distancia hamming
        int     ikmer;              //indíce de letras de las cadenas k-mer
        char    letra      =' ';    //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra=0;      //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii         =0;      //indice para inicializar los arreglos
        int     indice_mayor[];     //indice del elemento de la cadena k-mer que se repite más veces en la cadena de ADN
        int     im              =0; //índice del arreglo indice_mayor
        int     longitud_cadenas=0;
        int     i_cadenas_m[]   =new int[100];                      //contiene los indices de las cadenas k-mer mas comunes
        String  vecinos[];                                          //arreglo de los vecinos
        Scanner sc=new Scanner(System.in);                          //instancia para entrada de datos por el teclado
        System.out.println("Escriba el numero de cadenas de ADN:"); //Salida en la pantalla
        numero_cadenas=sc.nextInt();                                //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");                    //Salida en la pantalla
        kmer=sc.nextInt();                                          //Recepción del k-mer
        System.out.println("Escriba la distancia hamming:");        //Salida en la pantalla
        d_hamming  =sc.nextInt();                                   //Recepción de la distancia hamming
        cadenas_adn=new String[numero_cadenas];                     //define numero_cadenas elementos en el arreglo cadenas_adn
        System.out.println("Escriba las cadenas de ADN");
        for (int nc=0; nc < numero_cadenas; nc++)
            cadenas_adn[nc]=sc.next();
        longitud_cadenas=cadenas_adn[0].length();
        vecinos_totales =new String[1000]; //Define el tamaño de elementos de los vecinos
        repite_vecinos  =new int[1000];    //Define el tamaño de elementos del arreglo repite_vecinos
        construye_cadena=new String[kmer]; //define kmer elementos en el arreglo construye_cadena
        indice          =new int[kmer];    //define kmer elementos en el arreglo indice
        vecinos         =new String[9000]; //arreglo de vecinos
        for (ii=0; ii < kmer; ii++)        //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < 1000; ii++) //inicializa el arreglo repite_vecinos con cero
            repite_vecinos[ii]=0;
        //Declarar una variable FileReader
        for (int tc=0; tc < longitud_cadenas; tc++)
        {
            letra=cadenas_adn[0].charAt(tc);     //letra de la cadena de ADN leída
            for (ikmer=0; ikmer < kmer; ikmer++) //recorre los arreglos
            {
                if (indice[ikmer] >= 0)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1

                    construye_cadena[ikmer]=construye_cadena[ikmer] + letra; //concatena las letras (ácidos nucleicos)
                indice[ikmer]=indice[ikmer] + 1;                             //aumenta en uno los indices de las letras de las cadenas
                if (indice[ikmer] >= kmer)                                   //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                {
                    i_posibles=0;                                   //inicializa la variable de indice del arreglo posibles
                    vecindario(construye_cadena[ikmer], d_hamming); //Construye los vecindarios de la cadena k-mer
                    indice[ikmer]          =0;                      //inicializa el índice de la cadena
                    construye_cadena[ikmer]="";                     //se inicializa  el elemento  del arreglo construye_cadena
                }
            }
        }
        for (ii=0; ii < numero_cadenas; ii++)
            i_cadenas_m=motif(cadenas_adn[ii], d_hamming, kmer, ii);
        System.out.print("respuesta:");
        for (ii=0; ii <= i_mayores; ii++)
            System.out.print(vecinos_totales[i_cadenas_m[ii]] + " "); //imprime en pantalla*/
    }

    static String [] vecindario(String cadena, int d)
    {
        char      letra_actual; //letra actual de la cadena de ADN
        char      cb;           //letra de cambio de las bases nitrogenadas
        String [] bases={
            "A", "C", "G", "T"
        };                                             //Areglo de las bases nitrogenadas
        String    posibles[]     =new String[9000];    //Posibles subcadenas vecinas
        String    posibles2[]    =new String[9000];    //arreglo auxiliar de posibles subcadenas vecinas
        String    cadena2        ="";                  //cadena auxiliar
        boolean   repite         =false;               //si se repite un indice de vecinos
        int       i_posibles2    =0;                   //indice del arreglo posibles2
        int       longitud_cadena=cadena.length();     //longitud de la cadena de entrada
        int       comienzo       =longitud_cadena - 1; //el comienzo de la cadena (ATC->C,TC,ATC)
        for (int lc=0; lc < longitud_cadena; lc++)     //recorre la longitud de la cadena
        {
            cadena2="";                                             //inicializa la variable cadena2
            for (int s=comienzo; s < longitud_cadena; s++)          //forma subcadenas de la cadena
                cadena2=cadena2 + cadena.charAt(s);                 //concatena caracteres de la cadena
            if (cadena2.length() == 1)                              //si la longitud de la cadena 2 es igual a 1
                for (i_posibles2=0; i_posibles2 < 4; i_posibles2++) //recorremos el arreglo de bases nitrogenadas
                    posibles[i_posibles2]=bases[i_posibles2];       //colocamos en el arreglo de posibles cadenas , las bases nitrogenadas
            else
            {
                i_posibles2=0;                       //inicializamos el índice del arreglo posibles2
                for (int p=0; p < i_posibles; p++) { //recorremos el tamaño del arreglo de posibles vecinos
                    for (int b=0; b < 4; b++)        //recorremos el arreglo de las bases nitrogenadas

                        if ((distanciahamming((bases[b] + posibles[p]), cadena2) <= d))// si la distancia de hamming entre el posible arreglo con una base nitrogenada y la cadena 2 es menor a la d de entrada
                        {
                            posibles2[i_posibles2]= bases[b] + posibles[p]; //colocamos en el arreglo auxiliar de posibles vecinos , un vecino nuevo
                            i_posibles2++;                                  //incrementamos el indice del arreglo posibles2
                        }
                }
                for (int p=0; p < i_posibles2; p++) //Asignamos el arreglo auxiliar posibles2 al arreglo posibles
                    posibles[p]=posibles2[p];
            }
            i_posibles=i_posibles2; //asignamos al indice del arreglo posibles el indice del arreglo posibles2
            comienzo--;             //decrementamos la variable comienzo
        }
        for (int p=0; p < i_posibles; p++)   //Asignamos el arreglo auxiliar posibles2 al arreglo posibles
        {
            repite=false;
            for (int i=0; i < i_vecinos; i++)
                if (vecinos_totales[i].equals(posibles[p]) == true)
                    repite=true;
            if (repite == false)
            {
                vecinos_totales[i_vecinos]=posibles[p];
                i_vecinos++;
            }
        }
        return posibles;    //regresamos el arreglo posibles
    }

    static int distanciahamming(String cadena_uno, String cadena_dos)
    {
        int distancia_hamming=0;                              //distancia hamming de dos cadenas
        for (int i=0; i < cadena_uno.length(); i++)           //recorre la cadena de entrada uno para obtener la distancia Hamming
            if (cadena_uno.charAt(i) != cadena_dos.charAt(i)) //si la letra en la posición i de la cadena uno, es igual a la letra en la posición i de la cadena dos
                distancia_hamming++;
        //incrementa en uno distancia_hamming
        return distancia_hamming;
    }

    static int [] motif(String cadena, int d, int k, int num_cadena)
    {
        int  longitud_c=0;  //indica la longitud de la cadena;
        int  ii        =0;
        int  indice[];
        char letra            =' ';
        int  indice_repeticion=0;   //indice del elemento del arreglo repeticion_cadena
        int  indices_mayores[]=new int[1000];
        i_mayores=0;
        boolean repite=false;
        String  construye_cadena[];     //Construccion de las cadenas kmer
        longitud_c=cadena.length();     //asigna la longitud de la cadena
        int i_lon=0;                    //indice de la longitud
        indice          =new int[k];    //define el numero de elementos del arreglo indice
        construye_cadena=new String[k]; //define el numero de elementos del arreglo indice
        for (ii=0; ii < k; ii++)        //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
        {
            indice[ii]          =0 - ii;
            construye_cadena[ii]="";
        }
        for (int tc=0; tc < longitud_c; tc++)
        {
            letra=cadena.charAt(tc);        //letra de la cadena de ADN leída
            for (int i_k=0; i_k < k; i_k++) //recorre los arreglos
            {
                if (indice[i_k] >= 0)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1

                    construye_cadena[i_k]=construye_cadena[i_k] + letra; //concatena las letras (ácidos nucleicos)
                indice[i_k]=indice[i_k] + 1;                             //aumenta en uno los indices de las letras de las cadenas
                if (indice[i_k] >= k)                                    //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                {
                    for (int i_vec=0; i_vec < i_vecinos; i_vec++)
                        //System.out.print("cadena:"+construye_cadena[i_k]+" vecino: "+vecinos_totales[i_vec]);
                        if (distanciahamming(construye_cadena[i_k], vecinos_totales[i_vec]) <= d)   //Si la distancia hamming es menor o igual a la requerida
                        {
                            if (repite_vecinos[i_vec] == num_cadena)
                                repite_vecinos[i_vec]=repite_vecinos[i_vec] + 1; //suma uno ya que se repitio el vecino
                            if (repite_vecinos[i_vec] >= numero_cadenas)
                            {
                                repite=false;
                                for (int i=0; i <= i_mayores; i++)
                                    if (indices_mayores[i] == i_vec)
                                        repite=true;
                                if (repite == false)
                                {
                                    indices_mayores[i_mayores]=i_vec;   //guarda el indice de la cadena con mas repeticiones
                                    i_mayores++;
                                }
                            }
                        }
                    construye_cadena[i_k]=""; //se inicializa  el elemento  del arreglo construye_cadena
                    indice[i_k]          =0;  //inicializa el índice de la cadena
                }
            }
        }
        return indices_mayores;
    }
}
