/*Programa que busca una subcadena k-mer el cual es introducido como entrada en el programa y regresa las posiciones en las que se encuentra este patron de la cadena de ADN */
import java.io.*;
import java.util.Scanner;
class Posicion_ADN
{
    public static void main(String [] args)
    {
        Scanner    sc            =new Scanner(System.in);     //instancia para entrada de datos por el teclado
        String     nombre_archivo="";                         //Nombre del archivo que contiene a la cadena de ADN
        String     patrones[];                                //Contiene los posibles subcadenas K-mer
        String     palabra_kmer="";                           //contiene la subcadena k-mer
        char       palabra_k[];                               //Contiene la subcadena k-mer de entrada
        int        indice_letra[];                            //Contiene el numero de letra de una subcadena
        int        indice_kmer[];                             //Indice de las posibles posiciones de la cadena de ADN donde se encuentran las subcadenas K-mer
        int        indices_kmer[];                            //Indice de las posiciones de la cadena de ADN donde se encuentran las subcadenas K-mer
        int        caracter=0;                                //caracter de la cadena de ADN de entrada
        int        longitud=0;                                //longitud de la subcadena_kmer
        int        contador=0;                                //Contador de las letras de la cadena de ADN
        char       letra;                                     //Letra de la cadena de ADN
        int        contador_indice =0;                        //Contador del arreglo indice_kmer
        int        contador_palabra=0;                        //contador de las posibles subcadenas k-mer
        int        inicial         =0;                        //contador que indica desde que elemento comenzar en el arreglo de indice_kmer
        int        i               =0;                        //contador para movernos en el arreglo indice_kmer
        FileReader fr              = null;                    //instancia para leer un archivo
        System.out.println("Escriba el nombre del archivo:"); //Salida en la pantalla
        nombre_archivo=sc.nextLine();                         //Recepción de la cadena de ADN
        System.out.println("Escriba la subcadena K-mer:");    //Salida en la pantalla
        palabra_kmer=sc.nextLine();                           //Recepción del k-mer
        longitud    =palabra_kmer.length();                   //obtiene la longitud de la palabra k-mer
        palabra_k   =new char[longitud];                      //Define el tamaño del arreglo palabra_k
        indice_kmer =new int[10000];                          //Define el tamaño del arreglo indice_kmer
        indice_letra=new int[10000];                          //Define el tamaño del arreglo indice_letra
        patrones    =new String[10000];                       //Define el tamaño del arreglo patrones
        indices_kmer=new int[10000];                          //Define el tamaño del arreglo indices_kmer

        for (int f=0; f < longitud; f++)          //recorre los arreglo
            palabra_k[f]=palabra_kmer.charAt(f);  //coloca en un arreglo las letras de la subcadena k-mer
        for (int f=1; f < 10000; f++)             //recorre los arreglo
        {
            indice_letra[f]=0;  //inicializa el arreglo indice_letra
            indice_kmer[f] =0;
        }
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            caracter = fr.read();          //leer el caracter
            while (caracter != -1) {       //Se recorre el fichero hasta encontrar el carácter -1
                letra=(char)caracter;      //convierte en tipo de dato char , lo leido
                i    =inicial;             //indica desde que numero de elemento comenzar
                if (palabra_k[0] == letra) //si la primera letra de la subcadena k-mer es igual a la primera letra de la cadena creara un nuevo elemento en el arreglo indice_kmer
                {
                    indice_kmer[contador_palabra]=contador; //coloca el indice donde se encuentra la letra de la posible subcadena k-mer en la cadena de ADN
                    contador_palabra++;                     //aumenta el contador de palabra
                }

                for (; i < contador_palabra; i++) {                                  //recorre los arreglos dependiendo el numero de posibles palabras
                    if (indice_letra[i] >= 0 && palabra_k[indice_letra[i]] == letra) //checa si el elemento de la subcadena es igual a la letra de la cadena de ADN y si el indice es mayor o igual a cero (si es -1 ya se eliminno esa posible subcadena, por dos razones, esta completa o no es correcta)
                    {
                        /*patrones[i]=patrones[i]+""+letra;
                           System.out.println("i "+i+" contador "+contador+" contador palabra "+contador_palabra+ "INDICE_LETRA "+indice_letra[i]+" "+patrones[i]+"IKMER"+ indice_kmer[i]);	*/
                        indice_letra[i]=indice_letra[i] + 1; //incrementa en uno el contador
                        if (indice_letra[i] > longitud - 1)  //si la subcadena que analizamos ya tiene k-mer letras entonces ya es una subcaena correcta
                        {
                            indices_kmer[contador_indice]=indice_kmer[i]; //colocamos en el arreglo de indices, donde se encuentra la subcadena localizada en la cadena de ADN
                            contador_indice++;                            //aumentamos el contador del arreglo de indices_kmer
                            indice_letra[i]=-1;                           //colocamos en -1 la variable para que no vuelva a ser analizada
                            indice_kmer[i] =-1;                           //colocamos en -1 la variable para que no vuelva a ser analizada
                            //patrones[i]="";
                            inicial++;      //aumentamos la variable inicial para empezar a analizar a partir de la siguiente posible subcadena
                        }
                    }
                    else
                    {
                        indice_letra[i]=-1;     //colocamos en -1 la variable para que no vuelva a ser analizada
                        indice_kmer[i] =-1;     //colocamos en -1 la variable para que no vuelva a ser analizada
                        patrones[i]    ="";
                    }
                }
                contador++;           //incrementamos la variable contador de letras de la cadena de ADN
                caracter = fr.read(); //leer el caracter
            }
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
        System.out.println(contador_indice);
        System.out.print("La subcadena se encuentra en las siguientes posiciones:");
        for (int f=0; f < contador_indice; f++)
            System.out.print(indices_kmer[f] + " ");
        System.out.print("\n");
    }
}
