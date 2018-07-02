/*programa que recibe como parametro de entrada los enteros los enteros k , t, y N, seguido por una colección de cadenas de ADN y regresa Los BestMotifs resultantes de correr GibbsSampler ( Dna , k , t, N ). */
import java.io.*;
import java.util.Scanner;
class GibbsSampler
{
    public static void main(String [] args)
    {
        int    motif[][];
        int    best_motif[][];
        int    motif_nuevo[][];
        String cadenas_adn[];   //muestra las cadenas de adn introducidas por el usuario
        int    construye_cadena[][];
        int    kmer        =0; //k-mer
        int    N           =0;
        int    m_count[][] =new int[4][kmer];
        int    m_count2[][]=new int[4][kmer];
        int    indice[];             //indíce del numero de letras de las cadenas k-mer
        int    ikmer;                //indíce de letras de las cadenas k-mer
        char   letra           =' '; //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int    valor_letra     =0;   //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int    numero_cadenas  =0;   //número de cadenas
        int    longitud_cadenas=0;   //loongitud de las cadenas
        int    fila            =0;
        int    ii              =0;
        int    score_anterior  =10000;
        int    score_anterior2 =10000;
        int    score_nuevo     =0;
        int    contador        =0;
        int    contador2       =0;
        int    numero_random   =0;
        int    i_motif         =0; //indice del arreglo motif
        int    aux_arreglo[]   =new int[kmer];
        for (ii=0; ii < kmer; ii++)
            aux_arreglo[ii]=0;
        Scanner sc=new Scanner(System.in);                          //instancia para entrada de datos por el teclado
        System.out.println("Escriba el numero de cadenas de ADN:"); //Salida en la pantalla
        numero_cadenas=sc.nextInt();                                //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");                    //Salida en la pantalla
        kmer       =sc.nextInt();                                   //Recepción del k-mer
        cadenas_adn=new String[numero_cadenas];                     //define numero_cadenas elementos en el arreglo cadenas_adn
        System.out.println("Escriba las cadenas de ADN");
        for (int nc=0; nc < numero_cadenas; nc++)
            cadenas_adn[nc]=sc.next();  //obtiene del teclado las cadenas de ADN
        System.out.println("Escriba el valor de N:");
        N               =sc.nextInt();
        longitud_cadenas=cadenas_adn[0].length();       //Obtiene la longitud de las cadenas de ADN
        motif           =new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo motif
        construye_cadena=new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo motif
        motif_nuevo     =new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo moti
        best_motif      =new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo moti
        indice          =new int[kmer];                 //define kmer elementos en el arreglo indice
        for (ii=0; ii < kmer; ii++)                     //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        while (contador2 < 10000)
        {
            //System.out.println("Probando uno de los 20");
            //score_anterior=10000;
            for (int nc=0; nc < numero_cadenas; nc++)
            {
                numero_random=(int)(Math.random() * (longitud_cadenas - kmer));
                //System.out.println("numero random:"+numero_random+"final:"+(numero_random+kmer));
                for (int tc=numero_random; tc < (numero_random + kmer); tc++)
                {
                    letra=cadenas_adn[nc].charAt(tc);   //letra de la cadena de ADN leída
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
                    motif[nc][i_motif]=valor_letra;
                    i_motif++;
                }
                i_motif=0;
            }
            motif_nuevo=motif;
            for (int j=0; j < N; j++)
            {
                numero_random=(int)(Math.random() * (numero_cadenas));
                //System.out.println("Sale el numero : " + numero_random);
                m_count    =count(motif_nuevo, kmer, numero_cadenas, numero_random);
                aux_arreglo=profile_random(cadenas_adn[numero_random], kmer, m_count, numero_cadenas);
                //System.out.println("Y este es el cambio ");
                //imprimeArreglo(aux_arreglo);
                for (int i=0; i < kmer; i++)
                    motif_nuevo[numero_random][i]=aux_arreglo[i];
                m_count    =count(motif_nuevo, kmer, numero_cadenas, -1);
                score_nuevo=score(m_count, kmer, numero_cadenas);

                if (score_nuevo < score_anterior)
                {
                    System.out.println("<<---- Y si Hubo cambio ---->>");
                    motif         =motif_nuevo;
                    score_anterior=score_nuevo;
                    System.out.println("Y este el anterior con score " + score_anterior);
                    imprime(motif);
                }
                /*System.out.println("-------------");
                   System.out.println("Este es el nuevo con score " + score_nuevo);
                   imprime(motif_nuevo);
                   System.out.println("Y este el anterior con score " + score_anterior);
                   imprime(best_motif);*/
            }

            contador2++;
        }
    }

    static int [][] count(int [][] motif_n, int kmer, int numero_cadenas, int fila_eliminar)
    {
        int m_count[][]=new int[4][kmer];
        for (int j=0; j < kmer; j++)
            for (int i=0; i < 4; i++)
                m_count[i][j]=1;
        for (int j=0; j < kmer; j++)
            for (int i=0; i < numero_cadenas; i++)
                if (i != fila_eliminar)
                    m_count[motif_n[i][j]][j]=m_count[motif_n[i][j]][j] + 1;
        return m_count;
    }

    static int score(int [][] m_count, int kmer, int total_cadenas)
    {
        int score_t=0;
        int score_c=0;
        for (int j=0; j < kmer; j++)
        {
            score_c=0;
            for (int i=0; i < 4; i++)
                if (m_count[i][j] > score_c)
                    score_c=m_count[i][j];
            score_t=score_t + (total_cadenas - score_c);
        }
        return score_t;
    }

    static int [] profile_random(String cadena, int kmer, int [][] probabilidades, int contador_cadenas)
    {
        int   longitud_cadenas=cadena.length();
        char  letra           =' ';           //letra de la cadena
        int   valor_letra     =0;             //valor de la letra
        int   ikmer           =0;             //indice de las letras del kmer
        int   indice[]        =new int[kmer]; //define kmer elementos en el arreglo indice
        int   construye_cadena[][];           //Construccion de las cadenas kmer
        int   cadena_p[][];                   //Construccion de las cadenas kmer
        float probabilidad_mayor=0;
        float probabilidad_total=0;
        float probabilidad[]    =new float[kmer]; //define kmer elementos en el arreglo probabilidad
        int   suma_randomly     =0;
        int   nr                =0; //indice del arreglo randomly
        float total_r           =0; //suma del arreglo randomly
        float numero_random     =0;
        construye_cadena=new int[kmer][kmer];                        //define kmer elementos en el arreglo construye_cadena
        cadena_p        =new int[longitud_cadenas - kmer + 1][kmer]; //define kmer elementos en el arreglo construye_cadena
        int   ii                =0;
        float randomly[]        =new float[longitud_cadenas - kmer + 1];
        int   num_c             =0;
        float probabilidad_letra=0;
        float suma_random       =0;
        int   cadena_probable[] =new int[kmer];
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de las probabilidad con uno
            probabilidad[ii]=1;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (int tc=0; tc < longitud_cadenas; tc++)
        {
            letra=cadena.charAt(tc);    //letra de la cadena de ADN leída
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
            for (ikmer=0; ikmer < kmer; ikmer++)    //recorre los arreglos
            {
                if (indice[ikmer] >= 0 && valor_letra != -1)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1
                {
                    construye_cadena[ikmer][indice[ikmer]]=valor_letra;
                    probabilidad_letra                    =(probabilidades[valor_letra][indice[ikmer]]);                        //Busca la probabilidad en la matriz de probabilidades
                    probabilidad[ikmer]                   =probabilidad[ikmer] * (probabilidad_letra / (contador_cadenas + 3)); //multiplica la probabilidad
                }
                indice[ikmer]=indice[ikmer] + 1;                //aumenta en uno los indices de las letras de las cadenas
                if (indice[ikmer] >= kmer && valor_letra != -1) //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                {
                    indice[ikmer]=0;                   //inicializa el índice de la cadena
                    randomly[nr] =probabilidad[ikmer]; //coloca la probabilidad total de la cadena kmer
                    for (int s=0; s < kmer; s++)
                        cadena_p[nr][s]=construye_cadena[ikmer][s];
                    nr++;
                    total_r=total_r + probabilidad[ikmer];
                    //System.out.println(probabilidad[ikmer]);
                    probabilidad[ikmer]=1;
                }
            }
        }
        for (ii=0; ii < nr; ii++)
            randomly[ii]=randomly[ii] / total_r;
        numero_random=(float)(Math.random());
        //System.out.println("random2:"+numero_random);
        ii         =0;
        suma_random=suma_random + randomly[ii];
        while (numero_random > suma_random)
        {
            ii++;
            suma_random=suma_random + randomly[ii];
        }
        for (int j=0; j < kmer; j++)
            cadena_probable[j]=cadena_p[ii][j];
        return cadena_probable;
    }

    public static void imprime(int[][] motif)
    {
        for (int i = 0; i < motif.length; i++) {
            for (int j = 0; j < motif[i].length; j++) {
                char letra = ' ';
                if (motif[i][j] == 0)
                    letra='A';
                else if (motif[i][j] == 1)
                    letra='C';
                else if (motif[i][j] == 2)
                    letra='G';
                else if (motif[i][j] == 3)
                    letra='T';
                System.out.print(letra);
            }
            System.out.println("");
        }
    }

    public static void imprimeArreglo(int[] motif)
    {
        for (int i = 0; i < motif.length; i++) {
            char letra = ' ';
            if (motif[i] == 0)
                letra='A';
            else if (motif[i] == 1)
                letra='C';
            else if (motif[i] == 2)
                letra='G';
            else if (motif[i] == 3)
                letra='T';
            System.out.print(letra);
        }
        System.out.println("");
    }
}
