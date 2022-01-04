package cn.com.x1001.http;

import java.io.Serializable;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class CustomProtocol implements Serializable {

    private static final long serialVersionUID = 4671171056512301542L;
    private String id ;
    public CustomProtocol(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
