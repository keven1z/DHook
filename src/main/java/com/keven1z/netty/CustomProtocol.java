
package com.keven1z.netty;

import java.io.Serializable;

/**
 * @author keven1z
 * @date 2021/12/23
 */
public class CustomProtocol implements Serializable {

    private static final long serialVersionUID = 4671171056512301542L;
    private String id ;

    public CustomProtocol() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
