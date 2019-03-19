package com.aes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;

import com.aes.AESConstants.Mode;


public class AES {
    
    
    static String key = "F22B519C52CCE3967DC734949ACFE9BB";
    static String iv =  "78338E4A1B883645DF6E11D2E9237FC3";
    static String ftw = "";
    static BufferedReader  input;
    static Mode mode;
 
    
    public static void main(String args[]) throws Exception 
    {
        
        //int keysizecheck = 128; 
        mode = Mode.CBC;
        
        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter your choice(E/D):");
        String  en_de = sc.nextLine();
        if (en_de.equals("E")){
        	System.out.println("Enter your text to encrypt:");
        }else if(en_de.equals("D")) {
        	System.out.println("Enter your text to decrypt:");
        }
        
        //String  inputStr = sc.nextLine();
        
        input = new BufferedReader(new InputStreamReader(System.in));
        String  inputStr = input.readLine();
        
        if(inputStr.length() > 32){
        	throw new Exception("Input text size should be less than or equal to 32");
        }
          
        AES aes = new AES();
        if(en_de.equals("E")) 
        {
        	
        	String inputHex = AESConstants.str2Hex(inputStr.getBytes());
        	BigInteger value = new BigInteger(inputHex, 16);
        	
        	//String inputHex = inputStr.toString();
        	
            int numRounds = 10 ;
            int[][] state, initvector = new int[4][4];
            int[][] keymatrix = aes.keySchedule(key);
            
            if(mode == Mode.CBC)
            {
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++) {
                        initvector[j][i] = Integer.parseInt(iv.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
                    }
                }
            }
            while (inputHex != null) {
                
                    if (inputHex.length() < 32) {
                    	inputHex = String.format("%032x",value);
                    }
                   
                    state = new int[4][4];
                    for (int i = 0; i < 4; i++) //Parses line into a matrix
                    {
                        for (int j = 0; j < 4; j++) {
                            state[j][i] = Integer.parseInt(inputHex.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
                        }
                    }
                    
                    
                    
                    if(mode == Mode.CBC)
                    {
                        aes.addRoundKey(state, initvector);   
                    }
                    aes.addRoundKey(state, aes.subKey(keymatrix, 0)); //Starts the addRoundKey with the first part of Key Expansion
                    for (int i = 1; i < numRounds; i++) {
                        aes.subBytes(state); //implements the Sub-Bytes subroutine.
                        aes.shiftRows(state); //implements Shift-Rows subroutine.
                        aes.mixColumns(state);
                        aes.addRoundKey(state, aes.subKey(keymatrix, i));
                    }
                    aes.subBytes(state); //implements the Sub-Bytes subroutine.
                    aes.shiftRows(state); //implements Shift-Rows subroutine.
                    aes.addRoundKey(state, aes.subKey(keymatrix, numRounds));
                    if(mode == Mode.CBC)
                    {
                        initvector = state;
                    }
                    
                    inputHex = null;
                  
                    System.out.println(MatrixToString(state) + AESConstants.newline);
                    
                 
            }
        } 
        else if (en_de.equals("D")) //Decryption Mode 
        {
            
            int numRounds = 10;
            
            int[][] state = new int[4][4];
            int[][] initvector = new int[4][4];
            int[][] nextvector = new int[4][4];
            int[][] keymatrix = aes.keySchedule(key);
            if(mode == Mode.CBC) //Parse Initialization Vector
            {
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++) {
                        initvector[j][i] = Integer.parseInt(iv.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
                    }
                }                
            }
            while (inputStr != null) {
                state = new int[4][4];
                for (int i = 0; i < state.length; i++) //Parses line into a matrix
                {
                    for (int j = 0; j < state[0].length; j++) {
                        state[j][i] = Integer.parseInt(inputStr.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
                    }
                }
                if(mode == Mode.CBC)
                {
                    aes.deepCopy2DArray(nextvector,state);
                }
                aes.addRoundKey(state, aes.subKey(keymatrix, numRounds));
                for (int i = numRounds - 1; i > 0; i--) {
                    aes.invShiftRows(state);
                    aes.invSubBytes(state);
                    aes.addRoundKey(state, aes.subKey(keymatrix, i));
                    aes.invMixColumns(state);
                }
                aes.invShiftRows(state);
                aes.invSubBytes(state); 
                aes.addRoundKey(state, aes.subKey(keymatrix, 0));
                if(mode == Mode.CBC)
                {
                    aes.addRoundKey(state, initvector);
                    aes.deepCopy2DArray(initvector,nextvector);
                }
                
                inputStr = null;
                
                System.out.println(AESConstants.hex2Str(MatrixToString(state)));
            }
        } 
        else 
        {
            System.err.println("Usage for Encryption: java AES e keyFile inputFile");
            System.err.println("Usage for Decryption: java AES d keyFile encryptedinputFile");
        } 
    }

    //Helper method which executes a deep copy of a 2D array. (dest,src)
    private void deepCopy2DArray(int[][] destination, int[][] source)
    {
        assert destination.length == source.length && destination[0].length == source[0].length;
        for(int i = 0; i < destination.length;i++)
        {
            System.arraycopy(source[i], 0, destination[i], 0, destination[0].length);
        }
    }

    /**
     * Pulls out the subkey from the key formed from the keySchedule method
     * @param km key formed from AES.keySchedule()
     * @param begin index of where to fetch the subkey
     * @return The chunk of the scheduled key based on begin.
     */

    private int[][] subKey(int[][] km, int begin) {
        int[][] arr = new int[4][4];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = km[i][4 * begin + j];
            }
        }
        return arr;
    }

    /**
     * Replaces all elements in the passed array with values in sbox[][].
     * @param arr Array whose value will be replaced
     * @return The array who's value was replaced.
     */
    public void subBytes(int[][] arr) {
        for (int i = 0; i < arr.length; i++) //Sub-Byte subroutine
        {
            for (int j = 0; j < arr[0].length; j++) {
                int hex = arr[j][i];
                arr[j][i] = AESConstants.sbox[hex / 16][hex % 16];
            }
        }
    }

    /**
     * Inverse rendition of the subBytes. The operations of invSubBytes are the reverse operations of subBytes.
     * @param arr the array that is passed.
     */

    public void invSubBytes(int[][] arr) {
        for (int i = 0; i < arr.length; i++) //Inverse Sub-Byte subroutine
        {
            for (int j = 0; j < arr[0].length; j++) {
                int hex = arr[j][i];
                arr[j][i] = AESConstants.invsbox[hex / 16][hex % 16];
            }
        }
    }

    /**
     * Performs a left shift on each row of the matrix.
     * Left shifts the nth row n-1 times.
     * @param arr the reference of the array to perform the rotations.
     */

    public void shiftRows(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            arr[i] = leftrotate(arr[i], i);
        }
    }

    /**
     * Left rotates a given array. The size of the array is assumed to be 4.
     * If the number of times to rotate the array is divisible by 4, return the array
     * as it is.
     * @param arr The passed array (assumed to be of size 4)
     * @param times The number of times to rotate the array.
     * @return the rotated array.
     */

    private int[] leftrotate(int[] arr, int times)
    {
        assert(arr.length == 4);
        if (times % 4 == 0) {
            return arr;
        }
        while (times > 0) {
            int temp = arr[0];
            for (int i = 0; i < arr.length - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[arr.length - 1] = temp;
            --times;
        }
        return arr;
    }

    /**
     * Inverse rendition of ShiftRows (this time, right rotations are used).
     * @param arr the array to compute right rotations.
     */

    public void invShiftRows(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            arr[i] = rightrotate(arr[i], i);
        }
    }

    /**
     * Right reverses the array in a similar fashion as leftrotate
     * @param arr
     * @param times
     * @return
     */

    private int[] rightrotate(int[] arr, int times) {
        if (arr.length == 0 || arr.length == 1 || times % 4 == 0) {
            return arr;
        }
        while (times > 0) {
            int temp = arr[arr.length - 1];
            for (int i = arr.length - 1; i > 0; i--) {
                arr[i] = arr[i - 1];
            }
            arr[0] = temp;
            --times;
        }
        return arr;
    }

    /**
     * Performed by mapping each element in the current matrix with the value
     * returned by its helper function.
     * @param arr the array with we calculate against the galois field matrix.
     */

    public void mixColumns(int[][] arr) //method for mixColumns
    {
        int[][] tarr = new int[4][4];
        for(int i = 0; i < 4; i++)
        {
            System.arraycopy(arr[i], 0, tarr[i], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = mcHelper(tarr, AESConstants.galois, i, j);
            }
        }
    }

    /**
     * Helper method of mixColumns in which compute the mixColumn formula on each element.
     * @param arr passed in current matrix
     * @param g the galois field
     * @param i the row position
     * @param j the column position
     * @return the computed mixColumns value
     */

    private int mcHelper(int[][] arr, int[][] g, int i, int j)
    {
        int mcsum = 0;
        for (int k = 0; k < 4; k++) {
            int a = g[i][k];
            int b = arr[k][j];
            mcsum ^= mcCalc(a, b);
        }
        return mcsum;
    }

    private int mcCalc(int a, int b) //Helper method for mcHelper
    {
        if (a == 1) {
            return b;
        } else if (a == 2) {
            return MCTables.mc2[b / 16][b % 16];
        } else if (a == 3) {
            return MCTables.mc3[b / 16][b % 16];
        }
        return 0;
    }

    public void invMixColumns(int[][] arr) {
        int[][] tarr = new int[4][4];
        for(int i = 0; i < 4; i++)
        {
            System.arraycopy(arr[i], 0, tarr[i], 0, 4);
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = invMcHelper(tarr, AESConstants.invgalois, i, j);
            }
        }
    }

    private int invMcHelper(int[][] arr, int[][] igalois, int i, int j) //Helper method for invMixColumns
    {
        int mcsum = 0;
        for (int k = 0; k < 4; k++) {
            int a = igalois[i][k];
            int b = arr[k][j];
            mcsum ^= invMcCalc(a, b);
        }
        return mcsum;
    }

    /**
     * Helper computing method for inverted mixColumns.
     *
     * @param a Row Position of mcX.
     * @param b Column Position of mcX
     * @return the value in the corresponding mcX table based on the a,b coordinates.
     */

    private int invMcCalc(int a, int b) //Helper method for invMcHelper
    {
        if (a == 9) {
            return MCTables.mc9[b / 16][b % 16];
        } else if (a == 0xb) {
            return MCTables.mc11[b / 16][b % 16];
        } else if (a == 0xd) {
            return MCTables.mc13[b / 16][b % 16];
        } else if (a == 0xe) {
            return MCTables.mc14[b / 16][b % 16];
        }
        return 0;
    }

    /**
     *The keyScheduling algorithm to expand a short key into a number of separate round keys.
     *
     * @param key the key in which key expansion will be computed upon.
     * @return the fully computed expanded key for the AES encryption/decryption.
     */

    public int[][] keySchedule(String key)
    {

        int binkeysize = key.length() * 4;
        int colsize = binkeysize + 48 - (32 * ((binkeysize / 64) - 2)); //size of key scheduling will be based on the binary size of the key.
        int[][] keyMatrix = new int[4][colsize / 4]; //creates the matrix for key scheduling
        int rconpointer = 1;
        int[] t = new int[4];
        final int keycounter = binkeysize / 32;
        int k;

        for (int i = 0; i < keycounter; i++) //the first 1 (128-bit key) or 2 (256-bit key) set(s) of 4x4 matrices are filled with the key.
        {
            for (int j = 0; j < 4; j++) {
                keyMatrix[j][i] = Integer.parseInt(key.substring((8 * i) + (2 * j), (8 * i) + (2 * j + 2)), 16);
            }
        }
        int keypoint = keycounter;
        while (keypoint < (colsize / 4)) {
            int temp = keypoint % keycounter;
            if (temp == 0) {
                for (k = 0; k < 4; k++) {
                    t[k] = keyMatrix[k][keypoint - 1];
                }
                t = schedule_core(t, rconpointer++);
                for (k = 0; k < 4; k++) {
                    keyMatrix[k][keypoint] = t[k] ^ keyMatrix[k][keypoint - keycounter];
                }
                keypoint++;
            } else if (temp == 4) {
                for (k = 0; k < 4; k++) {
                    int hex = keyMatrix[k][keypoint - 1];
                    keyMatrix[k][keypoint] = AESConstants.sbox[hex / 16][hex % 16] ^ keyMatrix[k][keypoint - keycounter];
                }
                keypoint++;
            } else {
                int ktemp = keypoint + 3;
                while (keypoint < ktemp) {
                    for (k = 0; k < 4; k++) {
                        keyMatrix[k][keypoint] = keyMatrix[k][keypoint - 1] ^ keyMatrix[k][keypoint - keycounter];
                    }
                    keypoint++;
                }
            }
        }
        return keyMatrix;
    }

    /**
     * For every (binary key size / 32)th column in the expanded key. We compute a special column
     * using sbox and an XOR of the an rcon number with the first element in the passed array.
     * 
     * @param in the array in which we compute the next set of bytes for key expansion
     * @param rconpointer the element in the rcon array with which to XOR the first element in 'in'
     * @return the next column in the key scheduling.
     */

    public int[] schedule_core(int[] in, int rconpointer) {
        in = leftrotate(in, 1);
        int hex;
        for (int i = 0; i < in.length; i++) {
            hex = in[i];
            in[i] = AESConstants.sbox[hex / 16][hex % 16];
        }
        in[0] ^= AESConstants.rcon[rconpointer];
        return in;
    }

    /**
     * In the AddRoundKey step, the subkey is combined with the state. For each round, a chunk of the key scheduled is pulled; each subkey is the same size as the state. Each element in the byte matrix is XOR'd with each element in the chunk of the expanded key.
     * 
     * @param state reference of the matrix in which addRoundKey will be computed upon.
     * @param keymatrix chunk of the expanded key
     */

    public void addRoundKey(int[][] bytematrix, int[][] keymatrix)
    {
        for (int i = 0; i < bytematrix.length; i++) {
            for (int j = 0; j < bytematrix[0].length; j++) {
                bytematrix[j][i] ^= keymatrix[j][i];
            }
        }
    }

    /**
     * ToString() for the matrix (2D array).
     * 
     * @param m reference of the matrix
     * @return the string representation of the matrix.
     */
    
    public static String MatrixToString(int[][] m) //takes in a matrix and converts it into a line of 32 hex characters.
    {
        String t = "";
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                String h = Integer.toHexString(m[j][i]).toUpperCase();
                if (h.length() == 1) {
                    t += '0' + h;
                } else {
                    t += h;
                }
            }
        }
        return t;
    }
}