/*programa que recibe como parametro de entrada una cadena de ADN una longitud l, el número k-mer,el mínimo de repeticiones de una subcadena*/
import java.io.*;
import java.util.Scanner;
class Lt_grupos
{
    public static void main(String [] args)
    {
        String  cadena;             //cadena ADN
        String  cadenas_kmer[];     //Cadenas k-mer
        String  construye_cadena[]; //Construccion de las cadenas kmer
        String  nombre_archivo="";  //Escriba el nombre del archivo donde se encuentra la cadena de ADN
        int     contador_l    =0;
        boolean verifica_repeticion;
        int     incremento=0;
        int     longitudt;                                        //longitud del grupo
        int     peso_inicio[];                                    //identifica el peso de un elemento inicial
        int     peso_kmer[];                                      //identifica el peso de la cadena K-mer
        int     pesos_kmer[];                                     //identifica los pesos de las cadenas K-mer
        int     repeticion_cadena[];                              //Coloca el número de repeticiones de cadenas k-mer
        int     indice[];                                         //indíce del numero de letras de las cadenas k-mer
        int     iletra  =0;                                       //indíce de las letras
        int     longitud=0;                                       //longitud de la cadena
        int     kmer    =0;                                       //k-mer
        int     ipesos  =0;                                       //indice de pesos de la cadena k-mer
        int     ikmer;                                            //indíce de letras de las cadenas k-mer
        int     posible_repeticion=0;                             //posibles repeticiones en el grupo l-t
        char    letra             =' ';                           //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra       =0;                             //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii                =0;                             //indice para inicializar los arreglos
        int     indice_repeticion =0;                             //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor  =0;                             //la mayor repetición de una cadena
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
        cadenas_kmer      =new String[10000];                     //define 1000 elementos en el arreglo cadenas_kmer(4 Letras de los ácidos nucléicos)
        construye_cadena  =new String[kmer];                      //define kmer elementos en el arreglo construye_cadena
        peso_kmer         =new int[kmer];                         //define kmer elementos en el arreglo peso_kmer
        indice            =new int[kmer];                         //define kmer elementos en el arreglo indice
        repeticion_cadena =new int[10000];                        //define 4^kmer elementos en el arreglo repeticion_cadena(4 Letras de los ácidos nucléicos)
        pesos_kmer        =new int[10000];
        peso_inicio       =new int[10000];
        indice_mayor      =new int[10000]; //define (longitud/kmer) elementos en el arreglo indice_mayor
        for (ii=1; ii < kmer; ii++)        //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=-ii;
        indice[0]=0;
        for (ii=0; ii < 10000; ii++) //inicializa el arreglo de los indicecs mayores de las cadenas k-mer en cero
            indice_mayor[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < 10000; ii++) //inicializa el arreglo de las cadenas kmer con ""
            cadenas_kmer[ii]="";
        for (ii=0; ii < 10000; ii++) //inicializa el arreglo de los pesos de las cadenas kmer con ""
            pesos_kmer[ii]=0;
        for (ii=0; ii < 10000; ii++) //inicializa el arreglo con ceros de las repecticiones de cadenas k-mer
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
                    }
                    indice[ikmer]=indice[ikmer] + 1; //aumenta en uno los indices de las letras de las cadenas
                    if (indice[ikmer] >= kmer)       //si el índice de la cadena es mayor o igual al número k-mer
                    {
                        indice[ikmer]          =0;                //inicializa el índice de la cadena
                        encuentra_peso         =0;                //inicializa la variable encuentra_peso
                        peso_inicio[contador_l]=peso_kmer[ikmer]; //Guarda los pesos
                        contador_l++;                             //contador que indica el indice del arreglo
                        for (int l=0; l < ipesos; l++)            //recorre el arreglo que contiene los pesos
                        {
                            if (iletra == longitudt + incremento)    //si el número de letra es igual a la longitud del grupo


                                if (peso_inicio[incremento] == pesos_kmer[l])//busca el peso inicial para  restarlo de las repeticiones, ya que cambiaremos de grupo de longitud dada

                                    repeticion_cadena[l]=repeticion_cadena[l] - 1;//resta una repeticion para el nuevo grupo


                            if (peso_kmer[ikmer] == pesos_kmer[l]) //verifica si el peso se encuentra en el arreglo pesos_kmer
                            {
                                repeticion_cadena[l]=repeticion_cadena[l] + 1;    //si se repite el peso de la cadena se incrementa en uno el elemento del arreglo repeticion_cadena
                                if (repeticion_cadena[l] >= posible_repeticion)   //si la repeticion de la cadena es al menos, las posibles repeticiones que dio el usuario para el grupo
                                {
                                    verifica_repeticion=false;     //inicializa la variable que indica si hay repeticion de las las subcadenas del l-t clump
                                    for (ii=0; ii < im; ii++)      //recorre el arreglo del indice donde se encuentran las subcadenas del l-t clump
                                        if (indice_mayor[ii] == l) //si el indice donde se encuentran las subcadenas l-t clump esta repetido
                                            verifica_repeticion=true;
                                    //coloca true a la variable  verifica_repeticion
                                    if (verifica_repeticion == false)  //si no hay repetición de la subcadena l-t clump
                                    {
                                        indice_mayor[im]=l; //se asigna el indice de la cadena con más repeticiones
                                        im++;               //incrementa la variable de los elementos del arreglo indice_mayor
                                    }
                                }
                                encuentra_peso=1;   //coloca 1 a la variable encuentra_peso si encontro el peso en el arreglo de pesos
                            }
                        }
                        if (encuentra_peso == 0)   //si no encontro el peso en el arreglo de pesos
                        {
                            pesos_kmer[ipesos]       =peso_kmer[ikmer];        //se asigna el valor del indice del elemento de la cadena repeticion_cadena
                            cadenas_kmer[ipesos]     =construye_cadena[ikmer]; //se coloca la cadena k-mer
                            repeticion_cadena[ipesos]=1;                       //coloca un elemento en la repeticion_cadena
                            ipesos++;                                          //incrementa la variable de los elementos del arreglo de los pesos
                        }
                        if (iletra == longitudt + incremento) //si el número de letra es igual a la longitud del grupo
                            incremento++;                     //incrementa la variable de los elementos del arreglo de los pesos iniciales
                        peso_kmer[ikmer]       =0;            //se inicializa en cero el elemento  del arreglo peso_kmer
                        construye_cadena[ikmer]="";           //se inicializa  el elemento  del arreglo construye_cadena
                        indice[ikmer]          =0;            //inicializa el arreglo indice (nivel de la letra)
                    }
                }
                caract = fr.read();//leer el caracter
                iletra++;
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
        for (ii=0; ii < im; ii++)                                                                            //imprime las cadenas diferentes k-mer pero que tienen la misma catidad de repeticiones en la cadena
            System.out.print(cadenas_kmer[indice_mayor[ii]] + " ");
        System.out.print("\n");
    }
}
