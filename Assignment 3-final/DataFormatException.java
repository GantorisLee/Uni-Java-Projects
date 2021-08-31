/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;

/**
 * Exception case when the member or bill file has missing information or is in the wrong format.
 */
public class DataFormatException extends Exception implements Serializable {
    /*
    Exception case to print when information is in wrong format.
     */
    public DataFormatException(){
        super("Data format error. Information is missing or in the wrong format.");
    }
}
