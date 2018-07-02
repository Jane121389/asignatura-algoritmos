/*programa que recibe como parametro de entrada una cadena de ADN y el numero k-mer y regresa la cadena k-mer más probable en aparecer */
import java.io.*;
import java.util.Scanner;
class Most_probable_kmer
{
    public static void main(String [] args)
    {
        String  cadena;                     //cadena ADN
        String  construye_cadena[];         //Construccion de las cadenas kmer
        String  nombre_archivo="";          //Escriba el nombre del archivo donde se encuentra la cadena de ADN
        String  archivo_matriz="";          //Contiene el nombre del archivo donde se encuentra la matriz de probabilidades
        int     peso_kmer[];                //identifica el peso de la cadena K-mer
        float   probabilidad_total=0;       //Contiene las problabilidades de las cadenas de ADN
        float   probabilidad[];             //Contiene las problabilidades de las cadenas de ADN
        int     indice[];                   //indíce del numero de letras de las cadenas k-mer
        int     iletra                  =0; //indíce de las letras
        int     longitud                =0; //longitud de la cadena
        int     kmer                    =0; //k-mer
        int     indice_cadenas_probables=0;
        String  cadenas[];             //Contiene a las cadenas con mayor
        int     ikmer;                 //indíce de letras de las cadenas k-mer
        char    letra            =' '; //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra      =0;   //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     ii               =0;   //indice para inicializar los arreglos
        int     indice_repeticion=0;   //indice del elemento del arreglo repeticion_cadena
        int     repeticion_mayor =0;   //la mayor repetición de una cadena
        float   probabilidades[][];
        float   probabilidad_letra=0;
        float   probabilidad_mayor=0;
        Scanner sc                =new Scanner(System.in);    //instancia para entrada de datos por el teclado
        System.out.println("Escriba el nombre del archivo:"); //Salida en la pantalla
        nombre_archivo=sc.nextLine();                         //Recepción del archivo de la cadena de ADN
        System.out.println("Escriba el k-mer:");              //Salida en la pantalla
        kmer            =sc.nextInt();                        //Recepción del k-mer
        construye_cadena=new String[kmer];                    //define kmer elementos en el arreglo construye_cadena
        cadenas         =new String[200];                     //contiene a las cadenas k-mermás probables
        peso_kmer       =new int[kmer];                       //define kmer elementos en el arreglo peso_kmer
        indice          =new int[kmer];                       //define kmer elementos en el arreglo indice
        probabilidades  =new float[4][kmer];                  //define 4xkmer elementos en el arreglo probabilidad
        probabilidad    =new float[kmer];                     //define kmer elementos en el arreglo probabilidad
        probabilidades  =convierte_matriz("Matriz.txt", kmer);
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los pesos de las cadenas k-mer en cero
            peso_kmer[ii]=0;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de la construccion de las cadenas k-mer con ""
            construye_cadena[ii]="";
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de las probabilidad con uno
            probabilidad[ii]=1;
        //Declarar una variable FileReader
        FileReader fr = null;
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(nombre_archivo);
            //Leer el primer carácter
            int caract = fr.read();     //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caract != -1) {
                letra=(char)caract;    //letra de la cadena de ADN leída
                if (letra == 'A')      //Si la letra es A (adenina)
                    valor_letra=0;     //valor que se le asigna a la adenina (A)
                else if (letra == 'T') //Si la letra es T (timina)
                    valor_letra=3;     //valor que se le asigna a la timina (T)
                else if (letra == 'C') //Si la letra es C (citosina)
                    valor_letra=1;     //valor que se le asigna a la citosina (C)
                else if (letra == 'G') //Si la letra es G (guanina)
                    valor_letra=2;     //valor que se le asigna a la guanina (G)
                else
                    valor_letra=-1;
                for (ikmer=0; ikmer < kmer; ikmer++)     //recorre los arreglos
                {
                    if (indice[ikmer] >= 0 && valor_letra != -1)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1
                    {
                        construye_cadena[ikmer]=construye_cadena[ikmer] + letra;            //concatena las letras (ácidos nucleicos)
                        probabilidad_letra     =probabilidades[valor_letra][indice[ikmer]]; //Busca la probabilidad en la matriz de probabilidades
                        probabilidad[ikmer]    =probabilidad[ikmer] * probabilidad_letra;   //multiplica la probabilidad
                    }
                    indice[ikmer]=indice[ikmer] + 1;                //aumenta en uno los indices de las letras de las cadenas
                    if (indice[ikmer] >= kmer && valor_letra != -1) //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                    {
                        indice[ikmer]     =0;                   //inicializa el índice de la cadena
                        probabilidad_total=probabilidad[ikmer]; //coloca la probabilidad total de la cadena kmer
                        cadena            =construye_cadena[ikmer];
                        if (probabilidad_total > probabilidad_mayor)
                        {
                            probabilidad_mayor=probabilidad_total;
                            for (int i=0; i < indice_cadenas_probables; i++)
                                cadenas[i]="";
                            indice_cadenas_probables         =0;
                            cadenas[indice_cadenas_probables]=cadena;
                        }
                        else if (probabilidad_total == probabilidad_mayor)
                        {
                            indice_cadenas_probables++;
                            cadenas[indice_cadenas_probables]=cadena;
                        }
                        peso_kmer[ikmer]       =0;  //se inicializa en cero el elemento  del arreglo peso_kmer
                        probabilidad[ikmer]    =1;  //se inicializa en uno el elemento del arreglo probabilidad
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
        for (ii=0; ii <= indice_cadenas_probables; ii++) //imprime las repeticiones de las subcadenas escritas
            System.out.print(cadenas[ii] + " ");         //imprime en pantalla
        System.out.print("\n");
    }

    static float [][] convierte_matriz(String archivo, int k)
    {
        float      matriz[][]=new float[4][k]; //Matriz
        String     numero_s="";                //número tipo String
        float      numero_d=0;                 //número tipo double
        int        fila=0, columna=0;
        FileReader fr = null;   //fichero
        try {
            //Abrir el fichero indicado por el usuario
            fr = new FileReader(archivo);
            //Leer el primer carácter
            int caract = fr.read(); //leer el caracter
            //Se recorre el fichero hasta encontrar el carácter -1 que marca el final del fichero
            while (caract != -1) {
                if (caract != ' ' && caract != 10)
                    numero_s=numero_s + (char)caract;
                else
                {
                    numero_d             =Float.parseFloat(numero_s);
                    matriz[fila][columna]=numero_d;
                    columna++;
                    if (columna >= k)
                    {
                        columna=0;
                        fila++;
                    }
                    numero_s="";
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
        return matriz;
    }
}
