import java.sql.*;

public class Connector {
    private String driver = null;

    private String url = null;

    private String user = null;

    private String password = null;

    private Connection con = null;

    public Connector(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static void main(String[] args) {

    }

    private Connection getConnection() {
        if (con == null) {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, password);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return con;
    }

    private void closeConnection() {
        try {
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return int
     * @description This method will take a UserTransfer and create a user on eGameDarling
     * @description 0: everything ok;3: one user found; 127: an error occurred
     * @warning this method will not create a user with duplicate p_account_id
     */
    public int createUser(UserTransfer userTransfer) {

        int userAvailability = this.checkUserById(userTransfer.getP_account_id());

        if (userAvailability != 2) {
            return userAvailability;
        }

        String sql = "INSERT into t_accounts (p_account_id, email, passwort, nickname) values(?,?,?,?)";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userTransfer.getP_account_id());
            stmt.setString(2, userTransfer.getEmail());
            stmt.setString(3, userTransfer.getPasswort());
            stmt.setString(4, userTransfer.getNickname());

            stmt.executeUpdate();
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 127;
        }

    }

    /**
     * @description this method deletes a user if it exists or does nothing, but still return a succes(0)
     * @description 0 everything ok; 127 an error occurred.
     */
    public int deleteUserById(int p_account_id) {

        int userAvailability = this.checkUserById(p_account_id);


        if (userAvailability != 3) {
            return userAvailability;
        }


        String sql = "DELETE FROM t_accounts WHERE p_account_id=?";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p_account_id);

            stmt.executeUpdate();
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 127;
        }
    }

    /**
     * @description this method gets a user by p_account_id
     * @description it will return p_account_id, email, passwort and nickname
     * @description this method will return a transfer object, this method can/will only return one user as the p_account_id is unique
     */
    public UserTransfer getUserById(int p_account_id) {

        int userAvailability = this.checkUserById(p_account_id);

        if (userAvailability != 3) {
            return null;
        }


        String sql = "SELECT p_account_id, email, passwort, nickname FROM t_accounts WHERE p_account_id=? ";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p_account_id);

            ResultSet rawResult = stmt.executeQuery();
            rawResult.first();


            return new UserTransfer(
                    rawResult.getInt("p_account_id"),
                    rawResult.getString("email"),
                    rawResult.getString("passwort"),
                    rawResult.getString("nickname")
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @description this method gets a user by email and passwort
     * @description it will return p_account_id, email, passwort and nickname
     * @warning this method will return the result in an array
     * @warning the array may contain more than one user because the email and passwort are not unique
     */
    public UserTransfer[] getUserByEmailAndPasswort(String email, String passwort) {

        int userAvailability = this.checkUserByEmailAndPasswort(email, passwort);

        if (userAvailability != 3 && userAvailability != 1) {
            return null;
        }


        String sql = "SELECT p_account_id, email, passwort, nickname FROM t_accounts WHERE email=? AND passwort=? ";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, passwort);

            ResultSet rawResult = stmt.executeQuery();
            rawResult.first();

            rawResult.last();
            int counter = rawResult.getRow();
            rawResult.first();

            UserTransfer[] resultAccountIdArray = new UserTransfer[counter];

            counter = 0;
            do {
                resultAccountIdArray[counter] = new UserTransfer(
                        rawResult.getInt("p_account_id"),
                        rawResult.getString("email"),
                        rawResult.getString("passwort"),
                        rawResult.getString("nickname")
                );
                counter++;
            } while (rawResult.next());

            return resultAccountIdArray;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @return int
     * @description checks if a user exists and outputs a resultIndicationNumber to detail its findings
     * @description 3: one user found; 1: more than one user found; 2: user not found ; 127: an error occurred
     * @warning if you want the text of the resultIndicationNumber, you have to use the method "Connector.getErrorMessage(number)"
     */
    public int checkUserById(int p_account_id) {
        String sql = "SELECT * FROM t_accounts WHERE p_account_id=? ";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, p_account_id);

            ResultSet rawResult = stmt.executeQuery();

            rawResult.last();
            int counter = rawResult.getRow();
            rawResult.first();
            //noinspection MismatchedReadAndWriteOfArray
            int[] resultAccountIdArray = new int[counter];

            counter = 0;
            while (rawResult.next()) {
                resultAccountIdArray[counter] = rawResult.getInt("p_account_id");
                counter++;
            }

            if (resultAccountIdArray.length != 1) {
                if (resultAccountIdArray.length == 0) {
                    return 2;
                }
                return 1;
            } else {
                return 3;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 127;

    }

    /**
     * @return int
     * @description checks if a user exists and outputs a resultIndicationNumber to detail its findings
     * @description 3: one user found; 1: more than one user found; 2: user not found ; 127: an error occurred
     * @warning if you want the text of the resultIndicationNumber, you have to use the method "Connector.getErrorMessage(number)"
     */
    public int checkUserByEmailAndPasswort(String email, String passwort) {
        String sql = "SELECT * FROM t_accounts WHERE email=? AND passwort=? ";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, passwort);

            ResultSet result = stmt.executeQuery();

            result.last();
            int counter = result.getRow();
            result.first();
            //noinspection MismatchedReadAndWriteOfArray
            int[] resultAccountIdArray = new int[counter];

            counter = 0;
            while (result.next()) {
                resultAccountIdArray[counter] = result.getInt("p_account_id");
                counter++;
            }

            if (resultAccountIdArray.length != 1) {
                if (resultAccountIdArray.length == 0) {
                    return 2;
                }
                return 1;
            } else {
                return 3;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 127;
    }

    /**
     * @return int
     * @description checks if a db user exists and outputs a resultIndicationNumber to detail its findings
     * @description 3: one user found; 1: more than one user found; 2: user not found ; 127: an error occurred
     * @warning if you want the text of the resultIndicationNumber, you have to use the method "Connector.getErrorMessage(number)"
     */
    public int checkDbUserUsername(String Username) {
        String sql = "SELECT user  FROM mysql.user WHERE user=?";

        try {
            con = this.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, Username);

            ResultSet result = stmt.executeQuery();

            result.last();
            int counter = result.getRow();
            result.first();
            //noinspection MismatchedReadAndWriteOfArray
            int[] resultAccountIdArray = new int[counter];

            counter = 0;
            while (result.next()) {
                resultAccountIdArray[counter] = result.getInt("user");
                counter++;
            }

            if (resultAccountIdArray.length != 1) {
                if (resultAccountIdArray.length == 0) {
                    return 2;
                }
                return 1;
            } else {
                return 3;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 127;
    }

    /**
     * @return int
     * @description This method will take a userName and password and create a db user with all permissions on eGameDarling
     * @description 0: everything ok;3: one user found; 127: an error occurred
     * @warning this method will not create a user with duplicate p_account_id
     */
    public int createDatabaseUser(String userName, String password) {

        int userAvailability = this.checkDbUserUsername(userName);

        if (userAvailability != 2) {
            return userAvailability;
        }


        String sql_create_user = "CREATE USER '" + userName + "'@'localhost'" + " IDENTIFIED by '" + password + "';";
        String sql_grant_rights = "GRANT SELECT ON Versand.* TO '" + userName + "'@'localhost';";

        try {
            con = this.getConnection();

            PreparedStatement stmt_create_user = con.prepareStatement(sql_create_user);
/*            stmt_create_user.setString(1, userName);
            stmt_create_user.setString(2, password);*/
            int temp1 = stmt_create_user.executeUpdate();

            PreparedStatement stmt_grant_rights = con.prepareStatement(sql_grant_rights);
            /*            stmt_grant_rights.setString(1, userName);*/
            int temp2 = stmt_grant_rights.executeUpdate();

            return 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 127;
    }

    /**
     * @return String
     * @description gives messages for resultIndicationNumbers
     */
    static String getErrorMessage(int resultIndicationNumber) {
        switch (resultIndicationNumber) {
            case 0:
                return "0: everything ok";
            case 1:
                return "1: more than one user found";
            case 2:
                return "2: user not found";
            case 3:
                return "3: one user found";
            default:
                return "127: a error occured";
        }
    }


    public String validateDatabaseUser(String username, String password) {
        String results = "";
        try {
            Connection connection = this.getConnection();
            String selectQuery = "SHOW GRANTS FOR '" + username + "'@'localhost';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                results = resultSet.getString(1);
            }

            return results;

        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }
}


