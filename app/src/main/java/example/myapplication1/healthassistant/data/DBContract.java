package example.myapplication1.healthassistant.data;
import android.provider.BaseColumns;
public final class DBContract {
    private DBContract() {}
    public static class DBEntry implements BaseColumns {
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "USER";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PAS = "password";
        public static final String COLUMN_NAME_VES= "ves";
        public static final String COLUMN_NAME_GVES= "gves";
        public static final String COLUMN_NAME_ROST= "rost";
        public static final String COLUMN_NAME_POL= "pol";
        public static final String COLUMN_NAME_VOZ= "voz";
    }
    public static class DBy implements BaseColumns {
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "vod";
        public static final String USER_NAME = "name";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_PROG = "prog";
    }
    public static class Lek implements BaseColumns {
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "lek";
        public static final String LEK_NAME = "name";
        public static final String DATA = "data";
        public static final String INTER= "inter";
        public static final String PROD = "prod";
        public static final String KOL = "kol";
        public static final String  DOZ= "doz";
        public static final String  T1= "t1";
        public static final String T2 = "t2";
        public static final String  T3= "t3";
        public static final String  Chek= "chek";
    }
    public static class Event implements BaseColumns {
        public static final String _ID = "_id";
        public static final String TABLE_NAME = "event";
        public static final String EV_NAME = "name";
        public static final String DATA = "data";
        public static final String TIME= "time";

    }

}