package www.knowledgeshare.com.knowledgeshare.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class DownLoadListsBean extends DataSupport implements Serializable {
    private String type;
    private String typeId;
    private String typeName;
    private String typeIcon;
    private String tName;
    private String tTag;
    private String typeSize;
    private List<ListBean> list;

    public DownLoadListsBean(String type, String typeId, String typeName,String typeIcon, String tName,
                             String tTag, String typeSize, List<ListBean> list){
        this.type = type;
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeIcon = typeIcon;
        this.tName = tName;
        this.tTag = tTag;
        this.typeSize = typeSize;
        this.list = list;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(String typeIcon) {
        this.typeIcon = typeIcon;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettTag() {
        return tTag;
    }

    public void settTag(String tTag) {
        this.tTag = tTag;
    }

    public String getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(String typeSize) {
        this.typeSize = typeSize;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends DataSupport implements Serializable{
        private String typeId;
        private String childId;
        private String name;
        private String videoTime;
        private String date;
        private String time;
        private String videoUrl;
        private String txtUrl;
        private String iconUrl;
        private String tName;
        private boolean save;
        private boolean isChecked=true;
        private boolean isVisibility = false;

        public String gettName() {
            return tName;
        }

        public void settName(String tName) {
            this.tName = tName;
        }

        public boolean isVisibility() {
            return isVisibility;
        }

        public void setVisibility(boolean visibility) {
            isVisibility = visibility;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getChildId() {
            return childId;
        }

        public void setChildId(String childId) {
            this.childId = childId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVideoTime() {
            return videoTime;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getTxtUrl() {
            return txtUrl;
        }

        public void setTxtUrl(String txtUrl) {
            this.txtUrl = txtUrl;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public boolean isSave() {
            return save;
        }

        public void setSave(boolean save) {
            this.save = save;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
