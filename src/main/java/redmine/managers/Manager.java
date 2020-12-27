package redmine.managers;

import redmine.db.DataBaseConnection;

public class Manager {

    public static DataBaseConnection dbConnection = new DataBaseConnection();

    public static DataBaseConnection getConnection(){
        return dbConnection;
    }


}
