/*Definir la inclinación de un genoma de cadena de ADN , que es la diferencia entre el número total de ocurrencias de ' G' y ' C ' en el genoma */
import java.io.*;
import java.util.Scanner;
class Minimo_skew
{
    public static void main(String [] args)
    {
        int        caracter;                                  // número de caracter leído de un archivo de texto
        char       letra;                                     //Caracter leído de un archivo de texto
        int        skew          =0;                          //variable que tiene la diferencia entre el número total de ocurrencias de ' G' y ' C '
        int        minimo_skew   =0;                          //variable que contiene el minimo valor de skew
        int        indice_skew[] =new int[100];               //variable que contiene la posición donde se encuentra el minimo valor de skew
        int        posicion      =1;                          //posición de una letra de la cadena de ADN
        int        i             =0;                          //indice del arreglo minimo_skew
        String     nombre_archivo="";                         //nombre de un archivo de texto
        FileReader fr            = null;                      //fichero
        Scanner    sc            =new Scanner(System.in);     //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:"); //Salida en la pantalla
        nombre_archivo=sc.nextLine();                         //Recepción del nombre de uun archivo
        try {
            //Abrir el fichero indicado por el usuario
            fr       = new FileReader(nombre_archivo);
            caracter = fr.read(); //leer un caracter
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caracter != -1) {
                letra=(char)caracter;   //letra de la cadena de ADN leída
                if (letra == 'C')       //Si la letra es C
                    skew--;             //Disminuir en uno skew
                else if (letra == 'G')  //Si la letra es G
                    skew++;             //aumentar en uno skew
                if (skew < minimo_skew) //Si el skew es menor al minimo skew actual
                {
                    minimo_skew=skew;       //minimo_skew toma el valor de skew
                    for (int j=1; j < i; j++)
                        indice_skew[j]=-1;   //inicializo el arreglo indice_skew
                    i             =0;        //inicializo el indice del arreglo indice_skew
                    indice_skew[i]=posicion; //coloco la posición donde se encuentra el mínimo skew en el arreglo
                }
                else if (skew == minimo_skew)   //si el skew es igual al minimo_skew
                {
                    i++;                     //aumento en uno el indice del arreglo indice_skew
                    indice_skew[i]=posicion; //coloco la posición donde se encuentra el mínimo skew en el arreglo
                }
                posicion++;           //Indica la posición de una letra mientras se recorre la cadena
                caracter = fr.read(); //leer el caracter
            }
            for (int j=0; j <= i; j++)
                System.out.print(indice_skew[j] + " ");
        }catch (FileNotFoundException e) {
            //Operaciones en caso de no encontrar el fichero
            System.out.println("Error: Fichero no encontrado");
            //Mostrar el error producido por la excepción
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            //Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            //Operaciones que se harán en cualquier caso. Si hay error o no.
            try {
                //Cerrar el fichero si se ha abierto
                if (fr != null)
                    fr.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }
}
