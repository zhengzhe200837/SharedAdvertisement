package utils;

/**
 * Created by zhengzhe on 2017/11/18.
 */

public class VideoInfo {
    private int mId;
    private String mPath;
    private String mName;
    private String mStatus;
    public VideoInfo(String path, String name, String status) {
        mPath = path;
        mName = name;
        mStatus = status;
    }

    public void setPath(String path) {
        mPath = path;
    }
    public void setName(String name) {
        mName = name;
    }
    public void setStatus(String status) {
        mStatus = status;
    }
    public String getPath() {
        return mPath;
    }
    public String getName() {
        return mName;
    }
    public String getStatus() {
        return mStatus;
    }
    public void setId(int id) {
        mId = id;
    }
    public int getId() {
        return mId;
    }
}
