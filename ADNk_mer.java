/*programa que recibe como parametro de entrada una cadena de ADN y regresa la o las cadenas k-mer mas frecuentes */
import java.io.*;
import java.util.Scanner;
class ADNk_mer
{
    public static void main(String [] args)
    {
        String  cadena;                                       //cadena ADN
        String  cadenas_kmer[];                               //Cadenas k-mer
        String  construye_cadena[];                           //Construccion de las cadenas kmer
        int     peso_kmer[];                                  //identifica el peso de la cadena K-mer y será el indice en el arreglo (vector) de  mayoria_cadena formadas
        int     repeticion_cadena[];                          //Coloca el número de repeticiones de cadenas k-mer
        int     indice[];                                     //indíce del numero de letras de las cadenas k-mer
        int     iletra  =0;                                   //indíce de las letras
        int     longitud=0;                                   //longitud de la cadena
        int     kmer    =0;                                   //k-mer
        int     ikmer;                                        //indíce de letras de las cadenas k-mer
        char    letra            =' ';                        //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra      =0;                          //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii               =0;                          //indice para inicializar los arreglos
        int     indice_repeticion=0;                          //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor =0;                          //la mayor repetición de una cadena
        int     indice_mayor[];                               //indice del elemento de la cadena k-mer que se repite más veces en la cadena de ADN
        int     im=0;                                         //índice del arreglo indice_mayor
        Scanner sc=new Scanner(System.in);                    //instancia para entrada de datos por el teclado
        System.out.println("Escriba a la cadena de ADN:");    //Salida en la pantalla
        cadena=sc.nextLine();                                 //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");              //Salida en la pantalla
        kmer             =sc.nextInt();                       //Recepción del k-mer
        cadenas_kmer     =new String[(int)Math.pow(4, kmer)]; //define 4^kmer elementos en el arreglo cadenas_kmer(4 Letras de los ácidos nucléicos)
        construye_cadena =new String[kmer];                   //define kmer elementos en el arreglo construye_cadena
        peso_kmer        =new int[kmer];                      //define kmer elementos en el arreglo peso_kmer
        indice           =new int[kmer];                      //define kmer elementos en el arreglo indice
        repeticion_cadena=new int[(int)Math.pow(4, kmer)];    //define 4^kmer elementos en el arreglo repeticion_cadena(4 Letras de los ácidos nucléicos)
        longitud         =cadena.length();                    //Obtiene la longitud de la cadena de ADN
        indice_mayor     =new int[longitud];                  //define (longitud/kmer) elementos en el arreglo indice_mayor
        for (ii=1; ii < kmer; ii++)                           //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=-ii;
        indice[0]=0;
        for (ii=0; ii < longitud; ii++) //inicializa el arreglo de los indicecs mayores de las cadenas k-mer en cero
            indice_mayor[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo de las cadenas kmer con ""
            cadenas_kmer[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo con ceros de las repecticiones de cadenas k-mer
            repeticion_cadena[ii]=0;
        for (iletra=0; iletra < longitud; iletra++) //recorre la cadena de ADN letra por letra
        {
            letra=cadena.charAt(iletra);         //Obtiene una letra (ácido nucléico) de la cadena de ADN
            if (letra == 'A')                    //Si la letra es A (adenina)
                valor_letra=1;                   //valor que se le asigna a la adenina (A)
            else if (letra == 'T')               //Si la letra es T (timina)
                valor_letra=2;                   //valor que se le asigna a la timina (T)
            else if (letra == 'C')               //Si la letra es C (citosina)
                valor_letra=3;                   //valor que se le asigna a la citosina (C)
            else if (letra == 'G')               //Si la letra es G (guanina)
                valor_letra=4;                   //valor que se le asigna a la guanina (G)
            for (ikmer=0; ikmer < kmer; ikmer++) //recorre los arreglos
            {
                if (indice[ikmer] >= 0)
                {
                    peso_kmer[ikmer]       =peso_kmer[ikmer] + ((valor_letra - 1) * ((int)Math.pow(4, (kmer - indice[ikmer] - 1)))); //asigna un peso a la letra de la cadena, dependiendo de su valor y el nivel en el que esta situada
                    construye_cadena[ikmer]=construye_cadena[ikmer] + letra;                                                         //concatena las letras (ácidos nucleicos)
                    //System.out.print("PESO:"+peso_kmer[ikmer]+ " ikmer "+ikmer+ "cadena "+construye_cadena[ikmer]);
                }
                indice[ikmer]=indice[ikmer] + 1; //aumenta en uno los indices de las letras de las cadenas
                if (indice[ikmer] >= kmer)       //si el índice de la cadena es mayor o igual al número k-mer
                {
                    indice[ikmer]                       =0;                                        //inicializa el índice de la cadena
                    indice_repeticion                   =peso_kmer[ikmer];                         //se asigna el valor del indice del elemento de la cadena repeticion_cadena
                    repeticion_cadena[indice_repeticion]=repeticion_cadena[indice_repeticion] + 1; //si se repite la cadena se incrementa en uno el elemento del arreglo repeticion_cadena
                    if (repeticion_cadena[indice_repeticion] >= repeticion_mayor)
                    {
                        if (repeticion_cadena[indice_repeticion] > repeticion_mayor)// si la repeticion de la cadena es mayor que lo que contiene la variable repeticion_mayor
                        {
                            for (ii=0; ii < im; ii++) //inicializamos el arreglo indice_mayor, porque ya existe una cadena con más repeticiones
                                indice_mayor[ii]=0;   //inicializamos con cero
                            im              =0;
                            indice_mayor[im]=indice_repeticion; //se asigna eel indice de la cadena con más repeticiones
                        }
                        else if (repeticion_cadena[indice_repeticion] == repeticion_mayor)// si la repeticion de la cadena es igual que lo que contiene la variable repeticion_mayor
                        {
                            im++;                               //aumenta en uno la variable im
                            indice_mayor[im]=indice_repeticion; //se asigna un elemento del arreglo indice_mayor para colocar el otro indice del arreglo que contiene una cadena del mismo tamaño
                        }
                        repeticion_mayor=repeticion_cadena[indice_repeticion];//asignamos a la variable, el valor de mayores repeticiones
                    }
                    cadenas_kmer[indice_repeticion]=construye_cadena[ikmer]; //se coloca la cadena k-mer
                    peso_kmer[ikmer]               =0;                       //se inicializa en cero el elemento  del arreglo peso_kmer
                    construye_cadena[ikmer]        ="";                      //se inicializa  el elemento  del arreglo construye_cadena
                }
            }
        }
        System.out.print("La(s) cadena(s) " + kmer + "-mer más frecuente en la cadena completa de ADN es "); //imprime en pantalla
        for (ii=0; ii <= im; ii++)                                                                           //imprime las cadenas diferentes k-mer pero que tienen la misma catidad de repeticiones en la cadena
            System.out.print(cadenas_kmer[indice_mayor[ii]] + " ");
        System.out.print("\n");
    }
}
