package com.xianlv.business.bean;

import java.util.List;

public class SceneItem {
    public boolean isCheck;
    public String typeId;
    public String typeName;
    public List<SceneSecondItem> scanQrcodes;


    public class SceneSecondItem {
        public String typeId;
        public String qrcodeName;
        public String qrcodeId;

    }
}
