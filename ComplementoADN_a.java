/*Programa que recibe como parametro un archivo que contiene una cadena de ADN y a la salida devuelve el complemento de la cadena de ADN*/
import java.io.*;
import java.util.Scanner;
class ComplementoADN_a
{
    public static void main(String [] args)
    {
        String  nombre_archivo    ="";                        //nombre del archivo que contiene la cadena de ADN
        String  cadena            ="";                        //Cadena de ADN
        String  complemento_cadena="";                        //complemento de la cadena de ADN
        char    letra             =' ';                       //letra de la cadena de ADN
        int     longitud          =0;                         //longitud de la cadena de ADN
        Scanner sc                = new Scanner(System.in);   //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:"); //Salida en la pantalla
        nombre_archivo=sc.next();                             //Recepción del nombre del archivo
        /*Procedimiento para tomar una letra de la cadena, buscar su complemento y colocarlo en la cadena "complemento_cadena" en orden inverso*/
        //Declarar una variable FileReader
        FileReader fr = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();         //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract; //caracter leido
                if (letra == 'A')   //Si la letra es A (adenina), la cambia por T (timina)
                    letra='T';
                else if (letra == 'T') //Si la letra es T (timina), la cambia por A (adenina)
                    letra='A';
                else if (letra == 'C') //Si la letra es C (citosina), la cambia por G (guanina)
                    letra='G';
                else if (letra == 'G') //Si la letra es G (guanina), la cambia por C (citosina)
                    letra='C';
                else
                    letra=' ';
                complemento_cadena=letra + complemento_cadena;//concatena las letras del complemento
                //Leer el siguiente carácter
                caract = fr.read();
            }
        }
        catch (FileNotFoundException e) {
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

        System.out.println("\n\n ****El complemento de la cadena de ADN es:\n " + complemento_cadena); //imprime el complemento de la
    }
}

