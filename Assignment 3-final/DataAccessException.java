/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.Serializable;

/**
 * Exception cases to be used on when member and employee file have no access.
 */

public class DataAccessException extends Exception implements Serializable {
    /*
    Check for Data Access problems.
    Prints the output error message.
     */
    public DataAccessException(){
        super("File not found or unable to access. Please try again.");
    }
}
