

public class temp {
    public static void main(String[] args) {


        Connector connectorXampp = new Connector("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost/eGameDarling",
                "",
                "");

        int [] message_arry = new int[4];



        message_arry[2] = connectorXampp.createDatabaseUser("","" );
        message_arry[3] = connectorXampp.createDatabaseUser("","" );

        /*message_arry[2] = connectorXampp.deleteUserById(10);

        message_arry[3] = connectorXampp.checkUserByEmailAndPasswort("","");

        UserTransfer[] resCollection = connectorXampp.getUserByEmailAndPasswort("","");

        for(UserTransfer user1 : resCollection){
            System.out.println(user1);
        }
*/
        for (int j : message_arry) {
            System.out.println(Connector.getErrorMessage(j));
        }
    }
}

