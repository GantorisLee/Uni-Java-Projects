/*
 * Student name: Lee Guo Yi
 * Student ID: 893164
 * LMS username: GUO YI LEE
 */

import java.io.*;
import java.util.ArrayList;

public class DataProvider implements Serializable{
    /**
     *
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     */
    boolean perfectData = true;
    ArrayList<ArrayList<String>> memberIdList = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> billIdList = new ArrayList<ArrayList<String>>();



    /*
    To check if the data file provided for members and bills are in the correct format and
    accessible.
    Adds the data to the array list.
    @param memberFile member file name/directory
    @param billFile bill file name/directory
     */
    public void dataCheck(String memberFile, String billFile)
            throws DataAccessException, DataFormatException, IOException {

        File memberFileRoot = new File(memberFile);
        File billFileRoot = new File(billFile);


        //check for the integrity of both member file and bill file, else exception cases
        if (!memberFileRoot.exists() || !billFileRoot.exists()) {
            throw new DataAccessException();
        } else {
            //check for the integrity of member file if all entries are not blanks
            String separator = ",";
            int expected = 3;
            BufferedReader reader = new BufferedReader(new FileReader(memberFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(separator);
                if (fields.length != expected) {
                    perfectData = false;
                } else if (line.isBlank()) {
                    perfectData = false;
                }
            }
        }


        if (!perfectData) {
            throw new DataFormatException();
        }

        //add member.csv and bill.csv details to the arraylist
        BufferedReader brm = new BufferedReader(new FileReader(memberFileRoot));
        BufferedReader brb = new BufferedReader(new FileReader(billFileRoot));


        addToList(memberIdList, brm, true);
        addToList(billIdList, brb, false);

    }


    /*
    Used to add the csv file information to a list, used in DataCheck().
    Used to add the information from bills.csv and members.csv to 2 lists.
    @param list nested array list to store the values
    @param reader buffered reader to read the csv
     */
    void addToList(ArrayList<ArrayList<String>> list,
                   BufferedReader reader, boolean type) throws IOException {
        String strLine;
        String[] tempArr;
        int entryCount = 0;
        while((strLine = reader.readLine()) != null) {
            list.add(new ArrayList<>());
            entryCount ++;
            tempArr = strLine.split(",");

            //create objects for bill and member
            if(!type){
                Bill newBill = new Bill(tempArr);
            } else {
                Member newMember = new Member(tempArr);
            }

            //store the information in an array
            for (String tempStr : tempArr) {
                list.get(entryCount-1).add(tempStr);
            }
        }
        reader.close();
    }

    /*
    Code used to rewrite the whole bill file so as to update the information
    to the latest for next usage.
    @param billIdListMain the nested array list of the bills information
    @param billFile the bill file name/directory
     */
    public void writesCSV(ArrayList<ArrayList<String>> billIdListMain,
                          String billFile) throws IOException {


        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < billIdListMain.size(); i++)//for each row
        {
            for(int j = 0; j < billIdListMain.get(i).size(); j++)//for each column
            {
                builder.append(billIdListMain.get(i).get(j));
                // string
                if(j < billIdListMain.get(i).size() - 1)//if this is not the last row element
                    builder.append(",");//then add comma (if you don't like commas you can use spaces)
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(billFile));

    writer.write(builder.toString());//save the string representation of the board
    writer.close();
    }


}
