package utils;

/**
 * Created by zhengzhe on 2017/12/22.
 */

public class Constants {
    //  http://192.168.31.109:8080   111.231.110.234:8080    192.168.31.233:8080
    public static final String BASE_URL = "http://111.231.110.234:8080";
    //  /SharedAdvertisement/UploadMyOrderServlet    /simpleDemo/HandleDataBaseServlet
    public static final String UPLOAD_MY_ORDER_INFO_NETWORK_INTERFACE = "/simpleDemo/HandleDataBaseServlet";
    //  /simpleDemo/UploadMediaSourceServlet  /SharedAdvertisement/UploadVideoServlet
    public static final String UPLOAD_VIDEO_FILE_NETWORK_INTERFACE = "/simpleDemo/UploadMediaSourceServlet";
    //  /SharedAdvertisement/MyOrderServlet  /simpleDemo/HandleDataBaseServlet
    public static final String GET_MY_ORDER_NETWORK_INTERFACE = "/simpleDemo/HandleDataBaseServlet";
    // /simpleDemo/HandleDataBaseServlet  /SharedAdvertisement/GetBillBoardLocationInfoServlet
    public static final String GET_LOCATION_INFO_NETWORK_INTERFACE = "/simpleDemo/HandleDataBaseServlet";
    // /simpleDemo/HandleDataBaseServlet  /SharedAdvertisement/GetBillBoardDetailInfoServlet
    public static final String GET_ADVERTISEMENT_BOARD_DETAIL_INFO_NETWORK_INTERFACE = "/simpleDemo/HandleDataBaseServlet";
    // /simpleDemo/HandleDataBaseServlet  /SharedAdvertisement/GetSelectedPlayTimeSegmentServlet
    public static final String GET_SELECTED_PLAY_TIME_SEGMENT_NETWORK_INTERFACE = "/simpleDemo/HandleDataBaseServlet";
}
