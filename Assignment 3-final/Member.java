/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.*;


/*
Creates the member object when a member is added to a whole list.
 */
public class Member implements Serializable {

    String id;
    String name;
    String email;


    /*
    Creates the member object
    @param id member id
    @param name member name
    @param email member email
     */
    public Member(String[] memberEntry) {

        id = memberEntry[0];
        name = memberEntry[1];
        email = memberEntry[2];
    }


}
