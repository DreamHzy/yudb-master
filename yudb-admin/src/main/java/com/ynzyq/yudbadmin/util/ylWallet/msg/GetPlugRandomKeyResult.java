package com.ynzyq.yudbadmin.util.ylWallet.msg;

import java.io.Serializable;
import java.util.List;

public class GetPlugRandomKeyResult extends BaseResult{

    /** 备注说明 */
    private List<Row> list;

    public List<Row> getList() {
        return list;
    }

    public void setList(List<Row> list) {
        this.list = list;
    }

    public static class Row implements Serializable {
        private String plugRandomKey;

        public String getPlugRandomKey() {
            return plugRandomKey;
        }

        public void setPlugRandomKey(String plugRandomKey) {
            this.plugRandomKey = plugRandomKey;
        }
    }

}
