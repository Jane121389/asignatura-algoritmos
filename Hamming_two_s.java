/* Programa que regresa la distancia Hamming entre dos cadenas de ADN*/
import java.io.*;
import java.util.Scanner;
class Hamming_two_s
{
    public static void main(String [] args)
    {
        String  cadena_uno       ="";
        String  cadena_dos       ="";
        int     distancia_hamming=0;
        Scanner sc               =new Scanner(System.in);        //instancia para entrada de datos por el teclado
        System.out.println("Escriba la primer cadena de ADN:");  //Salida en la pantalla
        cadena_uno=sc.nextLine();                                //recibe del teclado la primer cadena de ADN
        System.out.println("Escriba la segunda cadena de ADN:"); //Salida en la pantalla
        cadena_dos=sc.nextLine();                                //recibe del teclado la segunda cadena de ADN
        for (int i=0; i < cadena_uno.length(); i++)              //recorre la cadena de entrada uno para obtener la distancia Hamming
            if (cadena_uno.charAt(i) != cadena_dos.charAt(i))    //si la letra en la posición i de la cadena uno, es igual a la letra en la posición i de la cadena dos
                distancia_hamming++;
        //incrementa en uno distancia_hamming
        System.out.println(distancia_hamming);  //imprime en pantalla la distancia hamming de dos cadenas
    }
}
