/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.*;

/*
Creates the member object when a member is added to a whole list.
 */
public class Bill implements Serializable {

    String billId;
    String memberId;
    String billAmt;
    String usedOrNot;

    /*
    Creates the bill object
    @param billId bill id
    @param memberId member id
    @param billAmt the amount of the bill
    @param usedOrNot whether the bill has been used in a competition before
     */
    public Bill(String[] billEntry) {

        billId = billEntry[0];
        memberId = billEntry[1];
        billAmt = billEntry[2];
        usedOrNot = billEntry[3];
    }

}
