/*programa que localiza una subcadena k-mer con los desajustes d en el texto, esta o estas subcadenas deben de ser las más frecuentes de entre un patrón k-mer y su complemento de este mismo.*/
import java.io.*;
import java.util.Scanner;
class Most_frequent_mistmaches_complement
{
    static int i_posibles=0;
    public static void main(String [] args)
    {
        String  cadena;                                       //cadena ADN
        String  cadenas_kmer[];                               //Cadenas k-mer
        String  construye_cadena[];                           //Construccion de las cadenas kmer
        String  nombre_archivo="";                            //Escriba el nombre del archivo donde se encuentra la cadena de ADN
        int     peso_kmer[];                                  //identifica el peso de la cadena K-mer
        int     pesos_kmer[];                                 //identifica los pesos de las cadenas K-mer
        int     repeticion_cadena[];                          //Coloca el número de repeticiones de cadenas k-mer
        int     indice[];                                     //indíce del numero de letras de las cadenas k-mer
        int     iletra   =0;                                  //indíce de las letras
        int     longitud =0;                                  //longitud de la cadena
        int     kmer     =0;                                  //k-mer
        int     d_hamming=0;                                  //distancia hamming
        int     ikmer;                                        //indíce de letras de las cadenas k-mer
        char    letra            =' ';                        //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra      =0;                          //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii               =0;                          //indice para inicializar los arreglos
        int     indice_repeticion=0;                          //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor =0;                          //la mayor repetición de una cadena
        int     indice_mayor[];                               //indice del elemento de la cadena k-mer que se repite más veces en la cadena de ADN
        int     im         =0;                                //índice del arreglo indice_mayor
        int     peso_vecino=0;                                //peso del vecino
        String  vecinos[];                                    //arreglo de los vecinos
        Scanner sc=new Scanner(System.in);                    //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:"); //Salida en la pantalla
        nombre_archivo=sc.nextLine();                         //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");              //Salida en la pantalla
        kmer=sc.nextInt();                                    //Recepción del k-mer
        System.out.println("Escriba la distancia hamming:");  //Salida en la pantalla
        d_hamming        =sc.nextInt();                       //Recepción de la distancia hamming
        cadenas_kmer     =new String[(int)Math.pow(4, kmer)]; //define 4^kmer elementos en el arreglo cadenas_kmer(4 Letras de los ácidos nucléicos)
        construye_cadena =new String[kmer];                   //define kmer elementos en el arreglo construye_cadena
        peso_kmer        =new int[kmer];                      //define kmer elementos en el arreglo peso_kmer
        indice           =new int[kmer];                      //define kmer elementos en el arreglo indice
        vecinos          =new String[9000];                   //arreglo de vecinos
        repeticion_cadena=new int[(int)Math.pow(4, kmer)];    //define 4^kmer elementos en el arreglo repeticion_cadena(4 Letras de los ácidos nucléicos)
        indice_mayor     =new int[9000];                      //define (longitud/kmer) elementos en el arreglo indice_mayor
        for (ii=0; ii < kmer; ii++)                           //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=kmer - 1 + ii;
        for (ii=0; ii < 9000; ii++) //inicializa el arreglo de los indicecs mayores de las cadenas k-mer en cero
            indice_mayor[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo de las cadenas kmer con ""
            cadenas_kmer[ii]="";
        for (ii=0; ii < (int)Math.pow(4, kmer); ii++) //inicializa el arreglo con ceros de las repecticiones de cadenas k-mer
            repeticion_cadena[ii]=0;
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
                    }
                    indice[ikmer]=indice[ikmer] - 1;            //aumenta en uno los indices de las letras de las cadenas
                    if (indice[ikmer] < 0 && valor_letra != -1) //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                    {
                        i_posibles=0;                                              //inicializa la variable de indice del arreglo posibles
                        vecinos   =vecindario(construye_cadena[ikmer], d_hamming); //vecinos de la subcadena k-mer
                        for (int j=0; j < i_posibles; j++)                         //recorre el arreglo de vecinos
                        {
                            peso_vecino                   =regresa_peso(vecinos[j]);           //regresa el indice donde se encuentra el vecino
                            repeticion_cadena[peso_vecino]=repeticion_cadena[peso_vecino] + 1; //si se repite la cadena se incrementa en uno el elemento del arreglo repeticion_cadena
                            if (repeticion_cadena[peso_vecino] >= repeticion_mayor)            //si la repeticion de la cadena del vecino es mayor a la variable repeticion_mayor
                            {
                                if (repeticion_cadena[peso_vecino] > repeticion_mayor)// si la repeticion de la cadena es mayor que lo que contiene la variable repeticion_mayor
                                {
                                    for (ii=0; ii < im; ii++) //inicializamos el arreglo indice_mayor, porque ya existe una cadena con más repeticiones
                                        indice_mayor[ii]=0;   //inicializamos con cero
                                    im              =0;
                                    indice_mayor[im]=peso_vecino;   //se asigna eel indice de la cadena con más repeticiones
                                }
                                else if (repeticion_cadena[peso_vecino] == repeticion_mayor)// si la repeticion de la cadena es igual que lo que contiene la variable repeticion_mayor
                                {
                                    im++;                         //aumenta en uno la variable im
                                    indice_mayor[im]=peso_vecino; //se asigna un elemento del arreglo indice_mayor para colocar el otro indice del arreglo que contiene una cadena del mismo tamaño
                                }
                                repeticion_mayor         =repeticion_cadena[peso_vecino]; //asignamos a la variable, el valor de mayores repeticiones
                                cadenas_kmer[peso_vecino]=vecinos[j];                     //coloca en el arreglo de cadenas k-mer, la cadena de un vecino
                            }
                        }
                        i_posibles=0;                                                           //inicializa la variable de indice del arreglo posibles
                        vecinos   =vecindario(complemento(construye_cadena[ikmer]), d_hamming); //vecinos de la subcadena k-mer
                        for (int j=0; j < i_posibles; j++)                                      //recorre el arreglo de vecinos
                        {
                            peso_vecino                   =regresa_peso(vecinos[j]);           //regresa el indice donde se encuentra el vecino
                            repeticion_cadena[peso_vecino]=repeticion_cadena[peso_vecino] + 1; //si se repite la cadena se incrementa en uno el elemento del arreglo repeticion_cadena
                            if (repeticion_cadena[peso_vecino] >= repeticion_mayor)            //si la repeticion de la cadena del vecino es mayor a la variable repeticion_mayor
                            {
                                if (repeticion_cadena[peso_vecino] > repeticion_mayor)// si la repeticion de la cadena es mayor que lo que contiene la variable repeticion_mayor
                                {
                                    for (ii=0; ii < im; ii++) //inicializamos el arreglo indice_mayor, porque ya existe una cadena con más repeticiones
                                        indice_mayor[ii]=0;   //inicializamos con cero
                                    im              =0;
                                    indice_mayor[im]=peso_vecino;   //se asigna eel indice de la cadena con más repeticiones
                                }
                                else if (repeticion_cadena[peso_vecino] == repeticion_mayor)// si la repeticion de la cadena es igual que lo que contiene la variable repeticion_mayor
                                {
                                    im++;                         //aumenta en uno la variable im
                                    indice_mayor[im]=peso_vecino; //se asigna un elemento del arreglo indice_mayor para colocar el otro indice del arreglo que contiene una cadena del mismo tamaño
                                }
                                repeticion_mayor         =repeticion_cadena[peso_vecino]; //asignamos a la variable, el valor de mayores repeticiones
                                cadenas_kmer[peso_vecino]=vecinos[j];                     //coloca en el arreglo de cadenas k-mer, la cadena de un vecino
                            }
                        }
                        indice[ikmer]                  =kmer - 1;                //inicializa el índice de la cadena
                        cadenas_kmer[indice_repeticion]=construye_cadena[ikmer]; //se coloca la cadena k-mer
                        peso_kmer[ikmer]               =0;                       //se inicializa en cero el elemento  del arreglo peso_kmer
                        construye_cadena[ikmer]        ="";                      //se inicializa  el elemento  del arreglo construye_cadena
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
        int       i_posibles2    =0;                   //indice del arreglo posibles2
        int       longitud_cadena=cadena.length();     //longitud de la cadena de entrada
        int       comienzo       =longitud_cadena - 1; //el comienzo de la cadena (ATC->C,TC,ATC)
        for (int lc=0; lc < longitud_cadena; lc++)     //reccorre la longitud de la cadena
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

    static int regresa_peso(String cadena)  //regresa el indice donde se encuentra la subcadena
    {
        char letra;                             //letra de la cadena
        int  peso_total =0;                     //peso o indice de la cadena
        int  potencia   =cadena.length() - 1;   //potencia para convertir de base decimal a base 4
        int  valor_letra=0;                     //valor de las bases nitrogenadas
        for (int s=0; s < cadena.length(); s++) //recorre la cadena
        {
            letra=cadena.charAt(s);
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
            peso_total=peso_total + ((int)Math.pow(4, potencia) * valor_letra); //convierte la cadena de base decimal a base 4
            potencia--;                                                         //disminuye la variable potencia
        }
        return peso_total;  //regresa el peso o indice donde se encuentra la cadena en un arreglo
    }

    static String complemento(String cadena)
    {
        String complemento_cadena="";  //complemento de la cadena de ADN
        char   letra             =' '; //letra de la cadena de ADN
        int    longitud          =0;   //longitud de la cadena de ADN
        longitud=cadena.length();      //obtiene la longitud de la cadena de ADN
        /*Procedimiento para tomar una letra de la cadena, buscar su complemento y colocarlo en la cadena "complemento_cadena" en orden inverso*/
        for (int i=longitud - 1; i >= 0; i--)//recorre letra por letra la cadena
        {
            letra=cadena.charAt(i);
            //System.out.print(","+letra);
            if (letra == 'A') //Si la letra es A (adenina), la cambia por T (timina)
                letra='T';
            else if (letra == 'T') //Si la letra es T (timina), la cambia por A (adenina)
                letra='A';
            else if (letra == 'C') //Si la letra es C (citosina), la cambia por G (guanina)
                letra='G';
            else if (letra == 'G') //Si la letra es G (guanina), la cambia por C (citosina)
                letra='C';
            else
                letra=' ';
            complemento_cadena=complemento_cadena + letra;//concatena las letras del complemento
        }
        return complemento_cadena;
    }
}
