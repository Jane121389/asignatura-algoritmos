/*Programa que localiza la cadena que minimiza la distancia hamming de una serie de cadenas*/
import java.io.*;
import java.util.Scanner;
class Hamming_Dnas
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
        int     total_distancia   =0;
        int     distancia_minima  =1000;
        int     distancia         =0;
        int     distancias_minimas=1000;
        String  construye_cadena[];   //Construccion de las cadenas kmer
        String  cadenas_adn[];        //Arreglo que contiene las cadenas de entrada del ADN
        int     indice[];             //indíce del numero de letras de las cadenas k-mer
        int     iletra   =0;          //indíce de las letras
        int     longitud =0;          //longitud de la cadena
        int     kmer     =0;          //k-mer
        int     d_hamming=0;          //distancia hamming
        int     ikmer;                //indíce de letras de las cadenas k-mer
        char    letra           =' '; //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra     =0;   //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii              =0;   //indice para inicializar los arreglos
        int     indice_menor    =0;   //indice del arreglo vecino que minimiza d
        int     indice_minimo   =0;
        int     im              =0; //índice del arreglo indice_mayor
        int     longitud_cadenas=0;
        int     i_cadenas_m[]   =new int[100];                      //contiene los indices de las cadenas k-mer mas comunes
        String  vecinos[];                                          //arreglo de los vecinos
        Scanner sc=new Scanner(System.in);                          //instancia para entrada de datos por el teclado
        System.out.println("Escriba el numero de cadenas de ADN:"); //Salida en la pantalla
        numero_cadenas=sc.nextInt();                                //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");                    //Salida en la pantalla
        kmer       =sc.nextInt();                                   //Recepción del k-mer
        cadenas_adn=new String[numero_cadenas];                     //define numero_cadenas elementos en el arreglo cadenas_adn
        System.out.println("Escriba las cadenas de ADN");
        for (int nc=0; nc < numero_cadenas; nc++)
            cadenas_adn[nc]=sc.next();
        longitud_cadenas=cadenas_adn[0].length();
        vecinos_totales =new String[10000]; //Define el tamaño de elementos de los vecinos
        construye_cadena=new String[kmer];  //define kmer elementos en el arreglo construye_cadena
        indice          =new int[kmer];     //define kmer elementos en el arreglo indice
        vecinos         =new String[9000];  //arreglo de vecinos
        for (ii=0; ii < kmer; ii++)         //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        genera_vecinos(kmer);
        for (int s=0; s < i_vecinos; s++)
        {
            for (ii=0; ii < numero_cadenas; ii++)
            {
                for (int tc=0; tc < longitud_cadenas; tc++)
                {
                    letra=cadenas_adn[ii].charAt(tc);    //letra de la cadena de ADN leída
                    for (ikmer=0; ikmer < kmer; ikmer++) //recorre los arreglos
                    {
                        if (indice[ikmer] >= 0)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1

                            construye_cadena[ikmer]=construye_cadena[ikmer] + letra; //concatena las letras (ácidos nucleicos)
                        indice[ikmer]=indice[ikmer] + 1;                             //aumenta en uno los indices de las letras de las cadenas
                        if (indice[ikmer] >= kmer)                                   //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                        {
                            distancia=distanciahamming(construye_cadena[ikmer], vecinos_totales[s]);
                            if (distancia < distancia_minima)
                            {
                                distancia_minima=distancia;
                                indice_menor    =s;
                            }
                            indice[ikmer]          =0;  //inicializa el índice de la cadena
                            construye_cadena[ikmer]=""; //se inicializa  el elemento  del arreglo construye_cadena
                        }
                    }
                }
                total_distancia =total_distancia + distancia_minima;
                distancia_minima=1000;
            }
            if (total_distancia <= distancias_minimas)
            {
                distancias_minimas=total_distancia;
                indice_minimo     =indice_menor;
                //System.out.print(" "+vecinos_totales[indice_minimo]+" d:"+distancias_minimas);//imprime en pantalla*/
            }
            total_distancia=0;
        }
        System.out.println(vecinos_totales[indice_minimo] + " ");//imprime en pantalla*/
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

    static void genera_vecinos(int k)
    {
        String genera="";
        for (int i=0; i < k; i++)
            genera=genera + 'A';
        vecindario(genera, k);   //Construye los vecindarios de la cadena k-mer
    }
}
