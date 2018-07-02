/*programa que recibe como parametro de entrada una cadena de ADN y regresa la o las cadenas k-mer mas frecuentes */
import java.io.*;
import java.util.Scanner;
class Mismatches_kmer
{
    public static void main(String [] args)
    {
        String  subcadena;                                                                         //cadena ADN
        String  cadenas_kmer[];                                                                    //Cadenas k-mer
        String  construye_cadena[];                                                                //Construccion de las cadenas kmer
        String  nombre_archivo="";                                                                 //Escriba el nombre del archivo donde se encuentra la cadena de ADN
        int     mismatches    =0;                                                                  //Número de posibles mutaciones
        int     hamming[];                                                                         //indica cuantas letras comparadas son iguales de las subcadenas
        int     d_hamming=0;                                                                       //indica cuantas letras comparadas son iguales de una subcadena
        int     indices_subcadenas[];                                                              //arreglo que guarda las subcadenas con la minima distancia hamming
        int     is=0;                                                                              //posicion de elemento del arreglo indices_subcadenas
        int     valor_cambio[];                                                                    //valores de las bases nitrogenadas de la subcadena
        int     peso_kmer[];                                                                       //identifica el peso de la cadena K-mer
        int     pesos_kmer[];                                                                      //identifica los pesos de las cadenas K-mer
        int     repeticion_cadena[];                                                               //Coloca el número de repeticiones de cadenas k-mer
        int     indice[];                                                                          //indíce del numero de letras de las cadenas k-mer
        int     iletra  =1;                                                                        //indíce de las letras
        int     longitud=0;                                                                        //longitud de la cadena
        int     kmer    =0;                                                                        //k-mer
        int     ikmer;                                                                             //indíce de letras de las cadenas k-mer
        char    letra            =' ';                                                             //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra      =0;                                                               //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii               =0;                                                               //indice para inicializar los arreglos
        int     indice_repeticion=0;                                                               //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor =0;                                                               //la mayor repetición de una cadena
        Scanner sc               =new Scanner(System.in);                                          //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:");                                      //Salida en la pantalla
        nombre_archivo=sc.nextLine();                                                              //Recepción de la cadena de ADN
        System.out.println("Escriba la subcadena ADN:");                                           //Salida en la pantalla
        subcadena=sc.nextLine();                                                                   //Recepción de la cadena de ADN
        System.out.println("Escriba el número de errores o cambios posibles(distancia hamming):"); //Salida en la pantalla
        mismatches        =sc.nextInt();
        kmer              =subcadena.length();                 //obtiene la longitud de la subcadena de ADN (k-mer)
        valor_cambio      =new int[kmer];                      //reserva kmer elementis para el arreglo valor cambio
        cadenas_kmer      =new String[(int)Math.pow(4, kmer)]; //define 4^kmer elementos en el arreglo cadenas_kmer(4 Letras de los ácidos nucléicos)
        construye_cadena  =new String[8000];                   //define kmer elementos en el arreglo construye_cadena
        peso_kmer         =new int[kmer];                      //define kmer elementos en el arreglo peso_kmer
        indices_subcadenas=new int[8000];                      //define 1000 elementos en el arreglo indices_subcadenas
        indice            =new int[kmer];                      //define kmer elementos en el arreglo indice
        hamming           =new int[kmer];                      //define kmer elementos en el arreglo hamming
        repeticion_cadena =new int[(int)Math.pow(4, kmer)];    //define 4^kmer elementos en el arreglo repeticion_cadena(4 Letras de los ácidos nucléicos)
        for (ii=0; ii < kmer; ii++)
        {
            letra=subcadena.charAt(ii);
            switch (letra)
            {
                case 'A':          //Si la letra es A (adenina)
                    valor_letra=0; //valor que se le asigna a la adenina (A)
                    break;
                case 'C':          //Si la letra es C (citosina)
                    valor_letra=1; //valor que se le asigna a la citosina (C)
                    break;
                case 'G':          //Si la letra es G (guanina)
                    valor_letra=2; //valor que se le asigna a la guanina (G)
                    break;
                case 'T':          //Si la letra es T (timina)
                    valor_letra=3; //valor que se le asigna a la timina (T)
                    break;
                default:
                    valor_letra=-1;
                    break;
            }
            valor_cambio[kmer - 1 - ii]=valor_letra;
        }
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=kmer - 1 + ii;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la distancia hamming de las cadenas k-mer en cero
            hamming[ii]=0;
        for (ii=0; ii < 8000; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo de las cadenas kmer con ""
            cadenas_kmer[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo con ceros de las repecticiones de cadenas k-mer
            repeticion_cadena[ii]=0;
        for (ii=0; ii < 8000; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            indices_subcadenas[ii]=0;
        //Declarar una variable FileReader
        FileReader fr = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();     //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract; //letra de la cadena de ADN leída
                switch (letra)
                {
                    case 'A':          //Si la letra es A (adenina)
                        valor_letra=0; //valor que se le asigna a la adenina (A)
                        break;
                    case 'C':          //Si la letra es C (citosina)
                        valor_letra=1; //valor que se le asigna a la citosina (C)
                        break;
                    case 'G':          //Si la letra es G (guanina)
                        valor_letra=2; //valor que se le asigna a la guanina (G)
                        break;
                    case 'T':          //Si la letra es T (timina)
                        valor_letra=3; //valor que se le asigna a la timina (T)
                        break;
                    default:
                        valor_letra=-1;
                        break;
                }
                for (ikmer=0; ikmer < kmer; ikmer++)     //recorre los arreglos
                {
                    if (indice[ikmer] < kmer && valor_letra != -1)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1
                    {
                        peso_kmer[ikmer]       =peso_kmer[ikmer] + ((int)Math.pow(4, indice[ikmer]) * valor_letra); //convierte de decimal a base 4 la cadena de ADN
                        construye_cadena[ikmer]=construye_cadena[ikmer] + letra;                                    //concatena las letras (ácidos nucleicos)
                        if (valor_cambio[indice[ikmer]] == valor_letra)                                             //verifica si la letra es igual a la de la subcadena
                            hamming[ikmer]=hamming[ikmer] + 1;                                                      //agrega uno si son iguales*/
                    }
                    indice[ikmer]=indice[ikmer] - 1;            //aumenta en uno los indices de las letras de las cadenas
                    if (indice[ikmer] < 0 && valor_letra != -1) //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                    {
                        indice_repeticion=peso_kmer[ikmer];      //se asigna el valor del indice del elemento de la cadena repeticion_cadena
                        if (hamming[ikmer] + mismatches >= kmer) //verifica si esa cadena cuenta con los minimos errores o mutaciones
                        {
                            indices_subcadenas[is]=iletra - kmer; //introduce la posición de la subcadena
                            is++;
                        }
                        hamming[ikmer]                 =0;
                        indice[ikmer]                  =kmer - 1;                //inicializa el índice de la cadena
                        cadenas_kmer[indice_repeticion]=construye_cadena[ikmer]; //se coloca la cadena k-mer
                        peso_kmer[ikmer]               =0;                       //se inicializa en cero el elemento  del arreglo peso_kmer
                        construye_cadena[ikmer]        ="";                      //se inicializa  el elemento  del arreglo construye_cadena
                    }
                }
                iletra++;
                caract = fr.read();//leer el caracter
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
        for (ii=0; ii < is; ii++)                            //imprime las repeticiones de las subcadenas escritas
            System.out.print(indices_subcadenas[ii] + " ");  //imprime en pantalla
        System.out.print("\n");
    }
}
