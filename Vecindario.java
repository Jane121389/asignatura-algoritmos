/*Programa que cacula los vecinos de una subcadena k-mer, es decir, el conjunto de todos los k-mer cuya distancia Hamming no exceda d.*/
import java.io.*;
import java.util.Scanner;
class Vecindario
{
    static String vecinos[]; //arreglo de los vecinos
    static int kmer     =0;  //número k-mer
    static int i_vecinos=0;  //indice del arreglo vecinos
    public static void main(String [] args)
    {
        int     d             =0;                           //distancia hamming
        String  cadena        ="";                          //cadena ADN de entrada
        int     posicion_letra=0;                           //posición de la letra
        Scanner sc            =new Scanner(System.in);      //instancia para entrada de datos por el teclado
        System.out.println("Escriba la cadena de ADN:");    //Salida en la pantalla
        cadena=sc.nextLine();                               //recibe del teclado la primer cadena de ADN
        System.out.println("Escriba la distancia Hamming"); //Salida en la pantalla
        d             =sc.nextInt();                        //recibe del teclado la distancia hamming
        kmer          =cadena.length();                     // recibe la longitud de la cadena de entrada
        posicion_letra=kmer - 1;                            //se tomara la última posición de letra de la cadena
        vecinos       =new String[3000];
        for (int j=posicion_letra; j >= 0; j--) //recorre el arreglo cadena2
            vecindario(cadena, j, d);           //se dirige al metodo vecindario
        for (int j=0; j < i_vecinos; j++)
            System.out.println(vecinos[j]);
        System.out.println(cadena);
    }

    static void vecindario(String cadena, int posicion_letra, int d)
    {
        char    letra_actual; //letra actual de la cadena de ADN
        char    cb;           //letra de cambio de las bases nitrogenadas
        char [] bases={
            'A', 'C', 'G', 'T'
        };                                   //Areglo de las bases nitrogenadas
        String  cadena_nueva="";             //cadena nueva de ADN
        char    cadena2[]   =new char[kmer]; //define los elementos del arreglo
        for (int j=0; j < kmer; j++)         //coloca las letras de la cadena de ADN en un arreglo
            cadena2[j]=cadena.charAt(j);
        if (posicion_letra == -1)             //si la posición de la letra es -1
            posicion_letra=kmer - 1;          //posicion letra es la ultima posición de la cadena de ADN
        letra_actual=cadena2[posicion_letra]; //letra de la cadena de ADN que entra al método
        for (int s=0; s < 4; s++)             //recorre las letras de las bases nitrogenadas
        {
            cb=bases[s];            //letra que se va a cambiar en la cadena de ADN del arreglo bases
            if (cb != letra_actual) //si la letra es diferente de la letra en la que nos encontramos de la cadena de ADN
            {
                cadena_nueva="";             //se inicializa la variable cadena_nueva
                for (int c=0; c < kmer; c++) //recorre el arreglo cadena 2
                    if (c == posicion_letra)
                        cadena_nueva=cadena_nueva + bases[s];  //Hace un cambio en el arreglo de la cadena de ADN
                    else
                        cadena_nueva=cadena_nueva + cadena2[c];
                //concatena las letras de la cadena2
                vecinos[i_vecinos]=cadena_nueva; //ingresa en el arreglo vecinos la nueva cadena
                i_vecinos++;                     //incrementa en uno la variable de los indices del arreglo vecinos
                //System.out.print(" d "+d+ " "+vecinos[i_vecinos-1]);
                if (d > 1)  //si la distancia Hamming es mayor que uno
                {
                    System.out.print(" d " + d + " " + vecinos[i_vecinos - 1]);
                    vecindario(cadena_nueva, posicion_letra - 1, d - 1); //se dirige al metodo vecindario
                }
            }
        }
    }
}
