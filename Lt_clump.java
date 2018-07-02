/*programa que recibe como parametro de entrada una cadena de ADN y regresa la o las cadenas k-mer mas frecuentes */
import java.io.*;
import java.util.Scanner;
class Lt_clump
{
    public static void main(String [] args)
    {
        String  cadena;               //cadena ADN
        String  cadenas_kmer[];       //Cadenas k-mer
        String  construye_cadena[];   //Construccion de las cadenas kmer
        String  nombre_archivo="";    //Escriba el nombre del archivo donde se encuentra la cadena de ADN
        int     peso_kmer[];          //identifica el peso de la cadena K-mer
        int     pesos_kmer[];         //identifica los pesos de las cadenas K-mer
        int     repeticion_cadena[];  //Coloca el número de repeticiones de cadenas k-mer
        int     indice[];             //indíce del numero de letras de las cadenas k-mer
        int     iletra            =0; //indíce de las letras
        int     longitud          =0; //longitud de la cadena
        int     longitudt         =0;
        int     kmer              =0; //k-mer
        int     posible_repeticion=0;
        int     ipesos            =0;                             //indice de pesos de la cadena k-mer
        int     ikmer;                                            //indíce de letras de las cadenas k-mer
        char    letra            =' ';                            //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra      =0;                              //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii               =0;                              //indice para inicializar los arreglos
        int     indice_repeticion=0;                              //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor =0;                              //la mayor repetición de una cadena
        int     indice_mayor[];                                   //indice del elemento de la cadena k-mer que se repite más veces en la cadena de ADN
        int     im            =0;                                 //índice del arreglo indice_mayor
        int     encuentra_peso=0;                                 //toma el valor cero si no encuentra el peso de la cadena, de lo contrario toma el calor de 1
        Scanner sc            =new Scanner(System.in);            //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:");     //Salida en la pantalla
        nombre_archivo=sc.nextLine();                             //Recepción de la cadena de ADN
        System.out.println("Escriba el número k-mer:");           //Salida en la pantalla
        kmer=sc.nextInt();                                        //Recepción del k-mer
        System.out.println("Escriba la longitud:");               //Salida en la pantalla
        longitudt=sc.nextInt();                                   //Recepción del k-mer
        System.out.println("Escriba las repeticiones posibles:"); //Salida en la pantalla
        posible_repeticion=sc.nextInt();                          //Recepción del k-mer
        cadenas_kmer      =new String[1000];                      //define 1000 elementos en el arreglo cadenas_kmer(4 Letras de los ácidos nucléicos)
        construye_cadena  =new String[kmer];                      //define kmer elementos en el arreglo construye_cadena
        peso_kmer         =new int[kmer];                         //define kmer elementos en el arreglo peso_kmer
        indice            =new int[kmer];                         //define kmer elementos en el arreglo indice
        repeticion_cadena =new int[1000];                         //define 4^kmer elementos en el arreglo repeticion_cadena(4 Letras de los ácidos nucléicos)
        pesos_kmer        =new int[1000];
        indice_mayor      =new int[1000]; //define (longitud/kmer) elementos en el arreglo indice_mayor
        for (ii=1; ii < kmer; ii++)       //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=-ii;
        indice[0]=0;
        for (ii=0; ii < 1000; ii++) //inicializa el arreglo de los indicecs mayores de las cadenas k-mer en cero
            indice_mayor[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < 1000; ii++) //inicializa el arreglo de las cadenas kmer con ""
            cadenas_kmer[ii]="";
        for (ii=0; ii < 1000; ii++) //inicializa el arreglo de los pesos de las cadenas kmer con ""
            pesos_kmer[ii]=0;
        for (ii=0; ii < 1000; ii++) //inicializa el arreglo con ceros de las repecticiones de cadenas k-mer
            repeticion_cadena[ii]=0;
        //Declarar una variable FileReader
        FileReader fr = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();     //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1
            //   que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract;                  //letra de la cadena de ADN leída
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
                        indice[ikmer] =0;   //inicializa el índice de la cadena
                        encuentra_peso=0;
                        for (int l=0; l < ipesos; l++)
                            if (peso_kmer[ikmer] == pesos_kmer[l])
                            {
                                repeticion_cadena[l]=repeticion_cadena[l] + 1;    //si se repite el peso de la cadena se incrementa en uno el elemento del arreglo repeticion_cadena
                                if (repeticion_cadena[l] >= posible_repeticion - 1)
                                {
                                    indice_mayor[im]=l; //se asigna el indice de la cadena con más repeticiones
                                    im++;
                                }
                                encuentra_peso=1;
                            }
                        if (encuentra_peso == 0)
                        {
                            pesos_kmer[ipesos]=peso_kmer[ikmer];//se asigna el valor del indice del elemento de la cadena repeticion_cadena

                            cadenas_kmer[ipesos]=construye_cadena[ikmer];   //se coloca la cadena k-mer
                            ipesos++;
                        }
                        peso_kmer[ikmer]       =0;  //se inicializa en cero el elemento  del arreglo peso_kmer
                        construye_cadena[ikmer]=""; //se inicializa  el elemento  del arreglo construye_cadena
                    }
                }
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
        System.out.print("La(s) cadena(s) " + kmer + "-mer más frecuente en la cadena completa de ADN es "); //imprime en pantalla
        for (ii=0; ii <= im; ii++)                                                                           //imprime las cadenas diferentes k-mer pero que tienen la misma catidad de repeticiones en la cadena
            System.out.print(cadenas_kmer[indice_mayor[ii]] + " ");
        System.out.print("\n");
    }
}
